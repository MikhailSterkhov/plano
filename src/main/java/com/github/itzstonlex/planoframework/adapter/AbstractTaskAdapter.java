package com.github.itzstonlex.planoframework.adapter;

import com.github.itzstonlex.planoframework.Cancellable;
import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public abstract class AbstractTaskAdapter implements TaskProcess, Cancellable {

  @Getter
  protected TaskPlan plan;

  @Override
  public final synchronized boolean cancel() {
    PlanoCalendar calendar = plan.getScheduler().getCalendar();

    PlanoTask<?> scheduledTask = calendar.findScheduledTask(plan);
    if (scheduledTask != null) {
      return scheduledTask.cancel();
    }

    return false;
  }
}
