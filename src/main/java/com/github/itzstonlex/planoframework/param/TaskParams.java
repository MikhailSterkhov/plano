package com.github.itzstonlex.planoframework.param;

import com.github.itzstonlex.planoframework.PlanoTask;
import java.util.concurrent.TimeUnit;

public interface TaskParams {

  /**
   * Параметр отвечает за остановку активного
   * процесса запланированной задачи во время ее отмены.
   */
  TaskParamKey<Boolean> INTERRUPT_ON_CANCEL = new TaskParamKey<>("INTERRUPT_ON_CANCEL", Boolean.TRUE);

  /**
   * Параметр отвечает за единицу измерения
   * времени, по которому будет идти отсчет
   * запланированной задачи.
   */
  TaskParamKey<TimeUnit> TASK_TIME_UNIT = new TaskParamKey<>("TASK_TIME_UNIT");

  /**
   * Время, в течении которого запланированная
   * задача выполнит свой первый шаг.
   */
  TaskParamKey<Long> TASK_DELAY = new TaskParamKey<>("TASK_DELAY");

  /**
   * Во-первых, данный параметр добавляет запланированной
   * задаче цикличное выполнение одного и того
   * же процесса.
   * <p>
   * Во-вторых, данный параметр отвечает за переодичность
   * циклично выполняемых процессов запланированной
   * задачи.
   */
  TaskParamKey<Long> TASK_REPEAT_DELAY = new TaskParamKey<>("TASK_REPEAT_DELAY", 1L);

  /**
   * Параметр дает разрешение на цикличное выполнение
   * обработки ответа из процесса запланированной задачи
   * (Работает вместе с TASK_REPEAT_DELAY)
   */
  TaskParamKey<Boolean> TASK_REPEAT_RESPONSE_HANDLING = new TaskParamKey<>("TASK_REPEAT_RESPONSE_HANDLING", Boolean.FALSE);

  /**
   * Устанавливает максимальное время ожидания ответа
   * из процесса запланированной задачи при вызове
   * метода {@link PlanoTask#awaitTermination()}
   */
  TaskParamKey<Long> AWAIT_TERMINATION_TIMEOUT = new TaskParamKey<>("AWAIT_TERMINATION_TIMEOUT", 15_000L);
}
