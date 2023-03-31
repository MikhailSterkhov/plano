package com.github.itzstonlex.planoframework.thread;

import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.task.process.ResponsedTaskProcess;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
final class WrapperPlanoExecutorRunnable implements Runnable {

  private final List<Runnable> afterActionProcessesList = new ArrayList<>();

  private final TaskPlan plan;
  private final TaskProcess process;

  private final CompletableResponse<Object> response;

  public void afterAction(@NotNull Runnable after) {
    afterActionProcessesList.add(after);
  }

  private void fireAfterActionProcesses() {
    afterActionProcessesList.forEach(Runnable::run);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void run() {
    try {
      process.processAction();
      fireAfterActionProcesses();

      boolean isResponsed = (process instanceof ResponsedTaskProcess);
      if (!isResponsed)
        response.complete(new Object());

      if (response.containsData() && !plan.getParameter(TaskParams.TASK_REPEAT_RESPONSE_HANDLING))
        return;

      if (isResponsed) {
        ((ResponsedTaskProcess<Object>) process).after(response);

        try {
          response.complete(response.getResponse());
        } catch (PlanoNonResponseException ex) {
          response.completeExceptionally(ex);
        }
      }
    } catch (Throwable exception) {
      response.completeExceptionally(exception);
    }
  }
}
