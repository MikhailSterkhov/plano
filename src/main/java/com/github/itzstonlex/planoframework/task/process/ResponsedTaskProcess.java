package com.github.itzstonlex.planoframework.task.process;

import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import org.jetbrains.annotations.NotNull;

/**
 * Исполняемый процесс запланированной в календаре
 * и планировщике задачи, подразумевающий возвращение
 * какого-либо ответа.
 */
public interface ResponsedTaskProcess<V> extends TaskProcess {

  /**
   * Исполняется после {@link TaskProcess#processAction()}
   * <p>
   * Заполнение респонса от исполняемой задачи.
   *
   * @param response - респонс.
   */
  void after(@NotNull CompletableResponse<V> response);

  @Override
  default void processAction() {
    // override me.
  }
}
