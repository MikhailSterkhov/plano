package com.github.itzstonlex.planoframework;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlanoCalendar {

  void close();

  @NotNull
  PlanoScheduler getScheduler();

  @Nullable
  PlanoTask<?> getScheduledTask(@NotNull TaskPlan plan);
}
