package com.github.itzstonlex.planoframework.impl.calendar;

import com.github.itzstonlex.planoframework.impl.AbstractThreadPoolCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SingleThreadCalendar extends AbstractThreadPoolCalendar {

  @Override
  protected ScheduledExecutorService newScheduledExecutorService() {
    return Executors.newSingleThreadScheduledExecutor();
  }
}
