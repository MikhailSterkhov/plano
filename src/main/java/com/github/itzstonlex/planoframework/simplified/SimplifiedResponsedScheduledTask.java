package com.github.itzstonlex.planoframework.simplified;

import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCache;
import com.github.itzstonlex.planoframework.task.process.ResponsedTaskProcess;
import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public abstract class SimplifiedResponsedScheduledTask<R> extends SimplifiedScheduledTask
    implements ResponsedTaskProcess<R> {

  private final CompletableResponse<R> completableResponse = new CompletableResponse<>();

  public SimplifiedResponsedScheduledTask(@NotNull PlanoScheduler scheduler) {
    super(scheduler);
  }

  protected abstract void processAction(@NotNull CompletableResponse<R> response);

  @Override
  public final void processAction() {
    processAction(completableResponse);
  }

  @Override
  public final void after(@NotNull CompletableResponse<R> response) {
    try {
      response.complete(completableResponse.getResponse());
    } catch (PlanoNonResponseException exception) {
      response.completeExceptionally(exception.getCause());
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  protected PlanoTask<R> schedule(@NotNull TaskParamCache newParams) {
    this.reconfigurePlan(newParams);
    return scheduler.scheduleResponsed(plan, this);
  }

  @SuppressWarnings("unchecked")
  @Override
  public @NotNull PlanoTask<R> schedule(long delay, @NotNull TimeUnit unit) {
    return (PlanoTask<R>) super.schedule(delay, unit);
  }

  @SuppressWarnings("unchecked")
  @Override
  public @NotNull PlanoTask<R> scheduleRepeated(long delay, long repeatPeriod, @NotNull TimeUnit unit) {
    return (PlanoTask<R>) super.scheduleRepeated(delay, repeatPeriod, unit);
  }
}
