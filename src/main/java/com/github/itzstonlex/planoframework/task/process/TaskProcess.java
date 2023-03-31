package com.github.itzstonlex.planoframework.task.process;

/**
 * Исполняемый процесс запланированной в календаре
 * и планировщике задачи.
 */
@FunctionalInterface
public interface TaskProcess {

  void processAction();
}
