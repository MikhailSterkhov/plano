package com.github.itzstonlex.planoframework.impl;

import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.adapt.AbstractResponsedTaskAdapter;
import com.github.itzstonlex.planoframework.adapt.AbstractTaskAdapter;
import com.github.itzstonlex.planoframework.executor.wrapper.WrapperPlanoThreadExecutor;
import com.github.itzstonlex.planoframework.param.TaskParamKey;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCache;
import com.github.itzstonlex.planoframework.task.GeneralTaskImpl;
import com.github.itzstonlex.planoframework.task.WrapperScheduledFuture;
import com.github.itzstonlex.planoframework.task.process.ResponsedTaskProcess;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class HashMapCalendarSchedulerImpl implements PlanoScheduler {

  private final WrapperPlanoThreadExecutor wrappedExecutor;
  private final HashMapCalendarImpl calendarImpl;

  protected final PlanoTask<?> schedulePlanoTask(@NotNull TaskPlan plan, @NotNull TaskProcess process) {
    WrapperScheduledFuture wrapper = wrappedExecutor.schedule(plan, process);
    return new GeneralTaskImpl<>(plan, process, wrapper);
  }

  @Override
  public void shutdown() {
    wrappedExecutor.shutdown();
  }

  @Override
  public @NotNull PlanoCalendar getCalendar() {
    return calendarImpl;
  }

  @Override
  public @NotNull TaskPlan configurePlan(@NonNull Map<TaskParamKey<?>, Object> cache) {
    return new TaskPlan(this, new TaskParamCache(cache));
  }

  @Override
  public @NotNull TaskPlan configurePlan(@NonNull TaskParamCache taskParamCache) {
    return configurePlan((Map<TaskParamKey<?>, Object>) taskParamCache);
  }

  @Override
  public @NotNull PlanoTask<?> schedule(@NotNull AbstractTaskAdapter abstractTaskAdapter) {
    return schedule(abstractTaskAdapter.getPlan(), abstractTaskAdapter);
  }

  @Override
  public @NotNull <R> PlanoTask<R> scheduleResponsed(@NotNull AbstractResponsedTaskAdapter<R> abstractTaskAdapter) {
    return scheduleResponsed(abstractTaskAdapter.getPlan(), abstractTaskAdapter);
  }

  @Override
  public @NotNull PlanoTask<?> schedule(@NotNull TaskPlan plan, @NotNull Runnable runnable) {
    return schedule(plan, (TaskProcess) runnable::run);
  }

  @Override
  public @NotNull PlanoTask<?> schedule(@NotNull TaskPlan plan, @NotNull TaskProcess process) {
    PlanoTask<?> planoTask = schedulePlanoTask(plan, process);
    calendarImpl.getHashmap().put(plan, planoTask);
    return planoTask;
  }

  @SuppressWarnings("unchecked")
  @Override
  public @NotNull <R> PlanoTask<R> scheduleResponsed(@NotNull TaskPlan plan, @NotNull ResponsedTaskProcess<R> responseCompleter) {
    return (PlanoTask<R>) schedule(plan, responseCompleter);
  }
}
