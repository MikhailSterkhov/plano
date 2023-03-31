package com.github.itzstonlex.planoframework.test.adapter;

import com.github.itzstonlex.planoframework.factory.PlanoCalendars;
import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.adapter.AbstractResponsedTaskAdapter;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCacheBuilder;
import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

public class AbstractResponsedTaskAdapterTest {

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
            .build()
    );

    AbstractResponsedTaskAdapter<Integer> process = new AbstractResponsedTaskAdapter<Integer>(plan) {

      @Override
      public void after(@NotNull CompletableResponse<Integer> response) {
        Random random = ThreadLocalRandom.current();
        response.complete(random.nextInt());
      }
    };

    PlanoTask<Integer> scheduledTask = scheduler.scheduleResponsed(process);
    scheduledTask.whenCompleted((Runnable) () -> System.out.println("Task completed"));

    try {
      Integer response = scheduledTask.awaitResponse();
      System.out.println("Response: " + response);

    } catch (PlanoNonResponseException e) {
      throw new RuntimeException(e);
    }
  }
}
