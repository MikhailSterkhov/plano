package com.github.itzstonlex.planoframework.test;

import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.TaskPlan;
import com.github.itzstonlex.planoframework.adapter.AbstractResponsedTaskAdapter;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.factory.PlanoCalendars;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCacheBuilder;
import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public class Test {

  public static void main(String[] args) {
    PlanoCalendar calendar = PlanoCalendars.createSingleThreadCalendar();
    PlanoScheduler scheduler = calendar.getScheduler();

    TaskPlan plan = scheduler.configurePlan(
        TaskParamCacheBuilder.create()
            .set(TaskParams.TASK_TIME_UNIT, TimeUnit.SECONDS)
            .set(TaskParams.TASK_DELAY, 3L)
            .build()
    );

    PlanoTask<String> helloWorldResponsedTask = scheduler.scheduleResponsed(new AbstractResponsedTaskAdapter<String>(plan) {

      @Override
      public void after(@NotNull CompletableResponse<String> response) {
        response.complete("hello world");
      }
    });

    try {
      String response = helloWorldResponsedTask.awaitResponse();
      System.out.println(response);
    }
    catch (PlanoNonResponseException exception) {
      exception.printStackTrace();
    }

    calendar.close();
    System.out.println("calendar closed");
  }
}
