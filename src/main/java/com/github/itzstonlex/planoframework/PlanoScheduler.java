package com.github.itzstonlex.planoframework;

import com.github.itzstonlex.planoframework.param.TaskParamKey;
import com.github.itzstonlex.planoframework.task.process.ResponsedTaskProcess;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import java.util.Map;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public interface PlanoScheduler {

  void shutdown();

  @NotNull
  TaskPlan configurePlan(@NonNull Map<TaskParamKey<?>, Object> cache);

  @NotNull
  PlanoTask<?> schedule(@NotNull TaskPlan plan, @NotNull Runnable runnable);

  @NotNull
  PlanoTask<?> schedule(@NotNull TaskPlan plan, @NotNull TaskProcess process);

  @NotNull
  <R> PlanoTask<R> scheduleResponsed(@NotNull TaskPlan plan, @NotNull ResponsedTaskProcess<R> responseCompleter);
}
