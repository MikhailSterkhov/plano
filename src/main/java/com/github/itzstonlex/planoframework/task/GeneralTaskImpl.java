package com.github.itzstonlex.planoframework.task;

import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.exception.PlanoAwaitTimeoutException;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.task.process.ResponsedTaskProcess;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class GeneralTaskImpl<R> implements PlanoTask<R> {

  private final Object lock = new Object();

  @Getter
  private final TaskPlan plan;

  @Getter
  private final TaskProcess process;

  private final WrapperScheduledFuture wrapper;

  private void preconditionResponsedProcessCast() throws PlanoNonResponseException {
    if (!(process instanceof ResponsedTaskProcess)) {
      throw new PlanoNonResponseException();
    }
  }

  @SuppressWarnings("unchecked")
  private R getResponseNow() throws PlanoNonResponseException {
    synchronized (lock) {
      return ((R) wrapper.getCompleter().getResponse());
    }
  }

  @Override
  public @Nullable R awaitResponse()
      throws PlanoNonResponseException, PlanoAwaitTimeoutException {

    preconditionResponsedProcessCast();
    awaitTermination();

    return getResponseNow();
  }

  @Override
  public @Nullable R awaitResponse(long timeout, @NotNull TimeUnit unit)
      throws PlanoNonResponseException, PlanoAwaitTimeoutException {

    preconditionResponsedProcessCast();
    awaitTermination(timeout, unit);

    return getResponseNow();
  }

  @Override
  public void awaitTermination() throws PlanoAwaitTimeoutException {
    TimeUnit unit = TimeUnit.MILLISECONDS;
    Long terminationTimeout;

    synchronized (lock) {

      long delay = plan.getDelay(unit);
      long repeatDelay = plan.getRepeatDelay(unit);

      terminationTimeout = plan.getParameter(TaskParams.AWAIT_TERMINATION_TIMEOUT);

      if (terminationTimeout == null)
        terminationTimeout = (delay * 2L);
      else if (terminationTimeout < delay || terminationTimeout < repeatDelay) {
        terminationTimeout = Math.max(delay, repeatDelay);
      }
    }

    awaitTermination(terminationTimeout, unit);
  }

  @Override
  public void awaitTermination(long timeout, @NotNull TimeUnit unit)
      throws PlanoAwaitTimeoutException {
    try {
      long breakpoint = System.currentTimeMillis()
          + TimeUnit.MILLISECONDS.convert(timeout, unit);

      while (!wrapper.getCompleter().containsData()) {
        synchronized (lock) {
          lock.wait(27L);

          if (System.currentTimeMillis() > breakpoint) {
            throw new PlanoAwaitTimeoutException();
          }
        }
      }
    } catch (Throwable exception) {
      throw new PlanoAwaitTimeoutException(exception);
    }
  }

  @Override
  public void whenCompleted(@NotNull Runnable process) {
    whenCompleted((TaskProcess) process::run);
  }

  @Override
  public void whenCompleted(@NotNull TaskProcess process) {
    synchronized (lock) {
      wrapper.submitAfterTask(process);
    }
  }

  @Override
  public boolean cancel() {
    synchronized (lock) {
      boolean interruptFlag = plan.getParameter(TaskParams.INTERRUPT_ON_CANCEL);

      return wrapper.cancel(interruptFlag);
    }
  }
}
