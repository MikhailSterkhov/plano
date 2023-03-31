package com.github.itzstonlex.planoframework.task;

import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class WrapperScheduledFuture {

  private final List<TaskProcess> afterProcessTasksList =
      Collections.synchronizedList(new ArrayList<>());

  private final ScheduledFuture<?> scheduled;
  private final CompletableResponse<?> completer;

  public boolean cancel(boolean mayInterruptIfRunning) {
    return scheduled.cancel(mayInterruptIfRunning);
  }

  public void submitAfterTask(@NotNull TaskProcess afterTaskProcess) {
    afterProcessTasksList.add(afterTaskProcess);
  }

  public void fireAfterTasks() {
    afterProcessTasksList.forEach(TaskProcess::processAction);
  }
}
