package com.github.itzstonlex.planoframework.impl;

import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.thread.WrapperPlanoThreadExecutor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@EqualsAndHashCode
public abstract class AbstractThreadPoolCalendar implements PlanoCalendar {

  private final Map<TaskPlan, PlanoTask<?>> hashmap = new ConcurrentHashMap<>();
  private PlanoScheduler scheduler;

  protected abstract ScheduledExecutorService newScheduledExecutorService();

  private PlanoScheduler newSchedulerInstance() {
    WrapperPlanoThreadExecutor wrapperExecutor = new WrapperPlanoThreadExecutor(newScheduledExecutorService());
    return new SimpleScheduler(wrapperExecutor, this);
  }

  public void addTask(@NotNull PlanoTask<?> planoTask) {
    hashmap.put(planoTask.getPlan(), planoTask);
  }

  @Override
  public void close() {
    hashmap.values().forEach(planoTask -> {

      planoTask.getPlan().interruptOnCancel(true);
      planoTask.cancel();
    });

    scheduler.shutdown();
  }

  @Override
  public @NotNull PlanoScheduler getScheduler() {
    if (scheduler == null) {
      scheduler = newSchedulerInstance();
    }
    return scheduler;
  }

  @Override
  public @Nullable PlanoTask<?> findScheduledTask(@NotNull TaskPlan plan) {
    return hashmap.get(plan);
  }
}
