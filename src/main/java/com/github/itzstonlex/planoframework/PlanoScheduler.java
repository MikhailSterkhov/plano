package com.github.itzstonlex.planoframework;

import com.github.itzstonlex.planoframework.adapter.AbstractResponsedTaskAdapter;
import com.github.itzstonlex.planoframework.adapter.AbstractTaskAdapter;
import com.github.itzstonlex.planoframework.param.TaskParamKey;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCache;
import com.github.itzstonlex.planoframework.task.process.ResponsedTaskProcess;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import java.util.Map;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

/**
 * Планировщик и конфигуратор планов
 * для дальнейшего исполнения запланированных
 * в календаре задач.
 */
public interface PlanoScheduler {

  /**
   * Принудительно остановить и очистить
   * потоки и процессы, запущенные для
   * исполнения запланированных задач.
   */
  void shutdown();

  /**
   * Получение календаря, к которому
   * принадлежит данный планировщик задач.
   */
  @NotNull
  PlanoCalendar getCalendar();

  /**
   * Конфигурация плана на основе указанных
   * пользователем параметров в сигнатуре.
   *
   * @param cache - кеш параметров планируемой задачи.
   */
  @NotNull
  TaskPlan configurePlan(@NonNull Map<TaskParamKey<?>, Object> cache);

  /**
   * Конфигурация плана на основе указанных
   * пользователем параметров в сигнатуре.
   *
   * @param cache - кеш параметров планируемой задачи.
   */
  @NotNull
  TaskPlan configurePlan(@NonNull TaskParamCache cache);

  /**
   * Перевести запланированную задачу в статус
   * исполнения по сконфигурируемому ранее плану.
   *
   * @param plan - план, по которому будет исполняться задача.
   * @param runnable - процесс задачи
   *
   * @return - обертка задачи и ее процессов.
   */
  @NotNull
  PlanoTask<?> schedule(@NotNull TaskPlan plan, @NotNull Runnable runnable);

  /**
   * Перевести запланированную задачу в статус
   * исполнения по сконфигурируемому ранее плану.
   *
   * @param plan - план, по которому будет исполняться задача.
   * @param process - процесс задачи
   *
   * @return - обертка задачи и ее процессов.
   */
  @NotNull
  PlanoTask<?> schedule(@NotNull TaskPlan plan, @NotNull TaskProcess process);

  /**
   * Перевести запланированную задачу с потенциально
   * ожидаемым респонсом в статус исполнения по
   * сконфигурируемому ранее плану.
   *
   * @param plan - план, по которому будет исполняться задача.
   * @param responsedProcess - процесс задачи c перспективами на респонс.
   *
   * @return - обертка задачи и ее процессов.
   */
  @NotNull
  <R> PlanoTask<R> scheduleResponsed(@NotNull TaskPlan plan, @NotNull ResponsedTaskProcess<R> responsedProcess);

  /**
   * Перевести запланированную задачу в статус
   * исполнения по сконфигурируемому ранее плану.
   *
   * @param abstractTaskAdapter - адаптер процесса планируемой задачи.
   * @return - обертка задачи и ее процессов.
   */
  @NotNull
  PlanoTask<?> schedule(@NotNull AbstractTaskAdapter abstractTaskAdapter);

  /**
   * Перевести запланированную задачу с потенциально
   * ожидаемым респонсом в статус исполнения по
   * сконфигурируемому ранее плану.
   *
   * @param abstractTaskAdapter - адаптер процесса планируемой задачи
   *                            c перспективами на респонс.
   * @return - обертка задачи и ее процессов.
   */
  @NotNull
  <R> PlanoTask<R> scheduleResponsed(@NotNull AbstractResponsedTaskAdapter<R> abstractTaskAdapter);
}
