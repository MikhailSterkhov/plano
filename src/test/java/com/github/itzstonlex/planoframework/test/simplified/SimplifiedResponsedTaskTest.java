package com.github.itzstonlex.planoframework.test.simplified;

import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import com.github.itzstonlex.planoframework.factory.PlanoCalendars;
import com.github.itzstonlex.planoframework.simplified.SimplifiedResponsedScheduledTask;
import com.github.itzstonlex.planoframework.simplified.SimplifiedScheduledTask;
import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

public class SimplifiedResponsedTaskTest {

  private PlanoScheduler scheduler;

  @Before
  public void setUp() {
    scheduler = PlanoCalendars.createSingleThreadCalendar().getScheduler();
  }

  @Test
  public void processTest() {
    SimplifiedResponsedScheduledTask<String> scheduledTask = new SimplifiedResponsedScheduledTask<String>(scheduler) {
      @Override
      public void processAction(CompletableResponse<String> response) {
        response.complete("Hello world!");
      }
    };

    PlanoTask<String> task = scheduledTask.schedule(5, TimeUnit.SECONDS);

    try {
      System.out.println(task.awaitResponse());
    }
    catch (PlanoNonResponseException e) {
      throw new RuntimeException(e);
    }
  }
}
