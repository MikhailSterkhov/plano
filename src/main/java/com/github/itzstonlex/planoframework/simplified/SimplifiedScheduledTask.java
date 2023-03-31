package com.github.itzstonlex.planoframework.simplified;

import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.adapter.AbstractTaskAdapter;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCache;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCacheBuilder;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public abstract class SimplifiedScheduledTask extends AbstractTaskAdapter {

  @Getter
  protected final PlanoScheduler scheduler;

  public SimplifiedScheduledTask(@NotNull PlanoScheduler scheduler) {
    super(null);
    this.scheduler = scheduler;
  }

  protected final void reconfigurePlan(@NotNull TaskParamCache params) {
    super.plan = scheduler.configurePlan(params);
  }

  @SuppressWarnings("unchecked")
  protected <R> PlanoTask<R> schedule(@NotNull TaskParamCache newParams) {
    this.reconfigurePlan(newParams);
    return (PlanoTask<R>) scheduler.schedule(this);
  }

  @NotNull
  public PlanoTask<?> schedule(long delay, @NotNull TimeUnit unit) {
    return schedule(
        TaskParamCacheBuilder.create()
          .set(TaskParams.TASK_DELAY, delay)
          .set(TaskParams.TASK_TIME_UNIT, unit)
          .build()
    );
  }

  @NotNull
  public PlanoTask<?> scheduleRepeated(long delay, long repeatPeriod, @NotNull TimeUnit unit) {
    return schedule(
        TaskParamCacheBuilder.create()
            .set(TaskParams.TASK_DELAY, delay)
            .set(TaskParams.TASK_REPEAT_DELAY, repeatPeriod)
            .set(TaskParams.TASK_TIME_UNIT, unit)
            .build()
    );
  }

  @Override
  public final TaskPlan getPlan() {
    if (plan == null)
      throw new UnsupportedOperationException();

    return plan;
  }
}
