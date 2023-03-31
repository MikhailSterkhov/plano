package com.github.itzstonlex.planoframework.impl;

import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.executor.PlanoScheduledThreadPoolExecutor;
import com.github.itzstonlex.planoframework.executor.wrapper.WrapperPlanoThreadExecutor;
import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class HashMapCalendarImpl implements PlanoCalendar {

  private final int corePoolSize;
  private final Map<TaskPlan, PlanoTask<?>> hashmap = new HashMap<>();

  private PlanoScheduler scheduler;

  protected PlanoScheduler createSchedulerImpl() {
    PlanoScheduledThreadPoolExecutor poolExecutorService = new PlanoScheduledThreadPoolExecutor(corePoolSize);
    WrapperPlanoThreadExecutor wrapperExecutor = new WrapperPlanoThreadExecutor(poolExecutorService);

    return new HashMapCalendarSchedulerImpl(wrapperExecutor, this);
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
      scheduler = createSchedulerImpl();
    }
    return scheduler;
  }

  @Override
  public @Nullable PlanoTask<?> getScheduledTask(@NotNull TaskPlan plan) {
    return hashmap.get(plan);
  }
}
