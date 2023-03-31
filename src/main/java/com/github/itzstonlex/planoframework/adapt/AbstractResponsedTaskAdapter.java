package com.github.itzstonlex.planoframework.adapt;

import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.exception.PlanoAwaitTimeoutException;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.task.process.ResponsedTaskProcess;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractResponsedTaskAdapter<R> extends AbstractTaskAdapter
    implements ResponsedTaskProcess<R> {

  public AbstractResponsedTaskAdapter(@NotNull TaskPlan plan) {
    super(plan);
  }

  @SuppressWarnings("unchecked")
  private PlanoTask<R> getCurrentTaskObj() {
    TaskPlan plan = getPlan();

    PlanoCalendar calendar = plan.getScheduler().getCalendar();
    return ((PlanoTask<R>) calendar.getScheduledTask(plan));
  }

  @Nullable
  public final synchronized R awaitResponse() throws PlanoNonResponseException, PlanoAwaitTimeoutException {
    return getCurrentTaskObj().awaitResponse();
  }

  @Nullable
  public final synchronized R awaitResponse(long timeout, @NotNull TimeUnit unit) throws PlanoNonResponseException, PlanoAwaitTimeoutException {
    return getCurrentTaskObj().awaitResponse(timeout, unit);
  }

}
