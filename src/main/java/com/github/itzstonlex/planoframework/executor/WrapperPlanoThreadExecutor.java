package com.github.itzstonlex.planoframework.executor;

import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.exception.PlanoParamNotFoundException;
import com.github.itzstonlex.planoframework.param.TaskParamKey;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.task.WrapperScheduledFuture;
import com.github.itzstonlex.planoframework.task.process.ResponsedTaskProcess;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class WrapperPlanoThreadExecutor {

  private final PlanoScheduledThreadPoolExecutor impl;

  @NotNull
  public final List<Runnable> shutdown() {
    return impl.shutdownNow();
  }

  @NotNull
  protected final <V> V getValidatedParameterValue(@NotNull TaskPlan plan, @NotNull TaskParamKey<V> key) {
    V value = plan.getParameter(key);
    if (value == null) {
      throw new PlanoParamNotFoundException(key);
    }

    return value;
  }

  protected final boolean isRepeatable(@NotNull TaskPlan plan) {
    Long repeatDelayParameter = plan.getParameter(TaskParams.TASK_REPEAT_DELAY);
    return repeatDelayParameter != null && repeatDelayParameter >= 0;
  }

  @SuppressWarnings("unchecked")
  protected Runnable wrapRunnable(@NotNull TaskPlan plan, @NotNull CompletableResponse<Object> response, @NotNull TaskProcess process) {
    return () -> {
      try {
        process.processAction();
        if (response.containsData() && !plan.getParameter(TaskParams.TASK_REPEAT_RESPONSE_HANDLING)) {
          return;
        }

        if (process instanceof ResponsedTaskProcess) {
          ((ResponsedTaskProcess<Object>) process).after(response);

          try {
            response.complete(response.getResponse());
          } catch (PlanoNonResponseException ex) {
            response.completeExceptionally(ex);
          }
        }
      } catch (Throwable exception) {
        response.completeExceptionally(exception);
      }
    };
  }

  @SuppressWarnings("unchecked")
  public WrapperScheduledFuture schedule(@NotNull TaskPlan plan, @NotNull TaskProcess process) {
    CompletableResponse<Object> response = new CompletableResponse<>();

    Runnable runnable = wrapRunnable(plan, response, process);

    long delay = getValidatedParameterValue(plan, TaskParams.TASK_DELAY);
    TimeUnit unit = getValidatedParameterValue(plan, TaskParams.TASK_TIME_UNIT);

    ScheduledFuture<Object> scheduledFuture;

    if (isRepeatable(plan)) {
      long repeatDelay = getValidatedParameterValue(plan, TaskParams.TASK_REPEAT_DELAY);
      scheduledFuture = (ScheduledFuture<Object>) impl.scheduleAtFixedRate(runnable, delay, repeatDelay, unit);
    } else {
      scheduledFuture = (ScheduledFuture<Object>) impl.schedule(runnable, delay, unit);
    }

    return new WrapperScheduledFuture(scheduledFuture, response);
  }

}
