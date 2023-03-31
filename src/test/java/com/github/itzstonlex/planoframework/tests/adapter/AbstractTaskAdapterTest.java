package com.github.itzstonlex.planoframework.tests.adapter;

import com.github.itzstonlex.planoframework.factory.PlanoCalendars;
import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.adapter.AbstractTaskAdapter;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCacheBuilder;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

public class AbstractTaskAdapterTest {

  private PlanoScheduler scheduler;

  @Before
  public void setUp() {
    scheduler = PlanoCalendars.createSingleThreadCalendar().getScheduler();
  }

  @Test
  public void testSchedule_abstractTaskAdapter() {
    TaskPlan plan = scheduler.configurePlan(
        TaskParamCacheBuilder.create()
            .set(TaskParams.TASK_TIME_UNIT, TimeUnit.MILLISECONDS)
            .set(TaskParams.TASK_DELAY, 1650L)
            .set(TaskParams.TASK_REPEAT_DELAY, 500L)
            .set(TaskParams.INTERRUPT_ON_CANCEL, false)
            .build()
    );

    AbstractTaskAdapter process = new AbstractTaskAdapter(plan) {

      @Override
      public void processAction() {
        System.out.println(getPlan());

        boolean isCancelled = cancel();
        System.out.println("Cancellation state: " + isCancelled);
      }
    };

    PlanoTask<?> scheduledTask = scheduler.schedule(process);
    scheduledTask.whenCompleted((Runnable) () -> {

      System.out.println(scheduledTask);
      System.out.println("Task completed");
    });

    scheduledTask.awaitTermination();
  }
}
