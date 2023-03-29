package com.github.itzstonlex.planoframework.tests;

import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.PlanoCalendars;
import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCacheBuilder;
import com.github.itzstonlex.planoframework.task.process.TaskProcess;
import java.util.concurrent.TimeUnit;

public class PlanoSimpleTest {

  public static void main(String[] args) {
    PlanoCalendar calendar = PlanoCalendars.createSingleHashMapCalendar();
    PlanoScheduler scheduler = calendar.getScheduler();

    TaskPlan plan = scheduler.configurePlan(
        TaskParamCacheBuilder.create()
            .set(TaskParams.TASK_TIME_UNIT, TimeUnit.SECONDS)
            .set(TaskParams.TASK_DELAY, 3L)
            .set(TaskParams.INTERRUPT_ON_CANCEL, false)
            .build());

    PlanoTask<?> task = scheduler.schedule(plan, new GreetingScheduledTask());
    System.out.println(task.hashCode());
  }

  private static class GreetingScheduledTask implements TaskProcess {

    @Override
    public void processAction() {
      System.out.println("Hello world!");
    }
  }
}
