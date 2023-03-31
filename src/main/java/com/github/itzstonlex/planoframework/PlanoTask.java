package com.github.itzstonlex.planoframework;

import com.github.itzstonlex.planoframework.exception.PlanoAwaitTimeoutException;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlanoTask<R> extends Cancellable {

  @NotNull
  TaskPlan getPlan();

  @NotNull
  TaskProcess getProcess();

  @Nullable
  R awaitResponse() throws PlanoNonResponseException, PlanoAwaitTimeoutException;

  @Nullable
  R awaitResponse(long timeout, @NotNull TimeUnit unit) throws PlanoNonResponseException, PlanoAwaitTimeoutException;

  void awaitTermination() throws PlanoAwaitTimeoutException;

  void awaitTermination(long timeout, @NotNull TimeUnit unit) throws PlanoAwaitTimeoutException;

  void whenCompleted(@NotNull Runnable process);

  void whenCompleted(@NotNull TaskProcess process);
}
