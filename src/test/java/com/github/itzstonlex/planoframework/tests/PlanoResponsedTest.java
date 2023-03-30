package com.github.itzstonlex.planoframework.tests;

import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.PlanoCalendars;
import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCacheBuilder;
import com.github.itzstonlex.planoframework.task.process.ResponsedTaskProcess;
import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public class PlanoResponsedTest {

  public static void main(String[] args) {
    PlanoCalendar calendar = PlanoCalendars.createSingleHashMapCalendar();
    PlanoScheduler scheduler = calendar.getScheduler();

    TaskPlan plan = scheduler.configurePlan(
        TaskParamCacheBuilder.create()
            .set(TaskParams.TASK_TIME_UNIT, TimeUnit.SECONDS)
            .set(TaskParams.TASK_DELAY, 10L)
            .set(TaskParams.TASK_REPEAT_DELAY, 1L)
            .set(TaskParams.INTERRUPT_ON_CANCEL, true)
            .build());

    PlanoTask<String> task = scheduler.scheduleResponsed(plan, new GreetingResponsedTask());
    testGreetingResponse(task);
  }

  private static void testGreetingResponse(PlanoTask<String> planoTask) {
    try {
      System.out.println("Response: " + planoTask.awaitResponse());
    }
    catch (PlanoNonResponseException exception) {
      exception.printStackTrace();
    }
  }

  private static class GreetingResponsedTask implements ResponsedTaskProcess<String> {

    private static final String HELLO_WORLD_RESPONSE = "Hello world!";

    @Override
    public void after(@NotNull CompletableResponse<String> response) {
      response.complete(HELLO_WORLD_RESPONSE);
      System.out.println("complete response ok");
    }

    @Override
    public void processAction() {
      System.out.println(HELLO_WORLD_RESPONSE);
    }
  }
}
