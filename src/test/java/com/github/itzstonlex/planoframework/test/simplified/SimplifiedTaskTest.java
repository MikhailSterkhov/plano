package com.github.itzstonlex.planoframework.test.simplified;

import com.github.itzstonlex.planoframework.PlanoScheduler;
import com.github.itzstonlex.planoframework.PlanoTask;
import com.github.itzstonlex.planoframework.factory.PlanoCalendars;
import com.github.itzstonlex.planoframework.simplified.SimplifiedScheduledTask;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

public class SimplifiedTaskTest {

  private PlanoScheduler scheduler;

  @Before
  public void setUp() {
    scheduler = PlanoCalendars.createSingleThreadCalendar().getScheduler();
  }

  @Test
  public void processTest() {
    SimplifiedScheduledTask scheduledTask = new SimplifiedScheduledTask(scheduler) {
      @Override
      public void processAction() {
        System.out.println("Hello world!");
      }
    };

    scheduledTask.schedule(5, TimeUnit.SECONDS);

    PlanoTask<?> planoTask = scheduler.getCalendar().findScheduledTask(scheduledTask.getPlan());
    if (planoTask != null)
      planoTask.awaitTermination();
  }
}
