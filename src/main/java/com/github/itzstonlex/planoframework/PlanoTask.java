package com.github.itzstonlex.planoframework;

import com.github.itzstonlex.planoframework.exception.PlanoAwaitTimeoutException;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Исполняемая запланированная задача
 *
 * @param <R> - возвращаемый тип респонса.
 */
public interface PlanoTask<R> extends Cancellable {

  /**
   * План, по которому действует
   * исполняемая запланированная задача.
   */
  @NotNull
  TaskPlan getPlan();

  /**
   * Процесс, который выполняет
   * исполняемая запланированная задача.
   */
  @NotNull
  TaskProcess getProcess();

  /**
   * Блокирует поток, в котором был вызван данный
   * метод до момента получения ожидаемого респонса.
   *
   * @throws PlanoNonResponseException - выбрасывается когда задача не подразумевает возвращаемый респонс.
   * @throws PlanoAwaitTimeoutException - выбрасывается когда время ожидания респонса истекло.
   */
  @Nullable
  R awaitResponse() throws PlanoNonResponseException, PlanoAwaitTimeoutException;

  /**
   * Блокирует поток, в котором был вызван данный
   * метод до момента получения ожидаемого респонса.
   *
   * @param timeout - кастомный таймаут ожидания респонса.
   * @param unit - единица измерения для таймаута.
   *
   * @throws PlanoNonResponseException - выбрасывается когда задача не подразумевает возвращаемый респонс.
   * @throws PlanoAwaitTimeoutException - выбрасывается когда время ожидания респонса истекло.
   */
  @Nullable
  R awaitResponse(long timeout, @NotNull TimeUnit unit) throws PlanoNonResponseException, PlanoAwaitTimeoutException;

  /**
   * Блокирует поток, в котором был вызван данный
   * метод до момента полного исполнения процесса задачи.
   *
   * @throws PlanoAwaitTimeoutException - выбрасывается когда время ожидания респонса истекло.
   */
  void awaitTermination() throws PlanoAwaitTimeoutException;

  /**
   * Блокирует поток, в котором был вызван данный
   * метод до момента полного исполнения процесса задачи.
   *
   * @param timeout - кастомный таймаут ожидания завершения задачи.
   * @param unit - единица измерения для таймаута.
   *
   * @throws PlanoAwaitTimeoutException - выбрасывается когда время ожидания респонса истекло.
   */
  void awaitTermination(long timeout, @NotNull TimeUnit unit) throws PlanoAwaitTimeoutException;

  /**
   * Асинхронное выполнения определенного процесса
   * после завершения процесса задачи без
   * блокировки потока, в котором был вызван
   * данный метод.
   *
   * @param process - последующий после завершения задачи процесс.
   */
  void whenCompleted(@NotNull Runnable process);

  /**
   * Асинхронное выполнения определенного процесса
   * после завершения процесса задачи без
   * блокировки потока, в котором был вызван
   * данный метод.
   *
   * @param process - последующий после завершения задачи процесс.
   */
  void whenCompleted(@NotNull TaskProcess process);
}
