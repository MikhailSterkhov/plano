package com.github.itzstonlex.planoframework;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Календарь, на котором планируются
 * возможные исполняемые задачи.
 */
public interface PlanoCalendar {

  /**
   * Принудительное закрытие всех активных
   * задач и потоков.
   */
  void close();

  /**
   * Получение планировщика задач, где также есть
   * возможность конфигурации планов и их параметров.
   */
  @NotNull
  PlanoScheduler getScheduler();

  /**
   * Получение запланированной исполняемой задачи
   * по указанному плану, который был уже ранее
   * запланирован в календаре.
   *
   * @param plan - план необходимой для поиска задачи
   */
  @Nullable
  <R> PlanoTask<R> findScheduledTask(@NotNull TaskPlan plan);
}
