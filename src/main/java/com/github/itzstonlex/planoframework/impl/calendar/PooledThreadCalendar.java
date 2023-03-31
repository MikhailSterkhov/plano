package com.github.itzstonlex.planoframework.impl.calendar;

import com.github.itzstonlex.planoframework.impl.AbstractThreadPoolCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PooledThreadCalendar extends AbstractThreadPoolCalendar {

  private final int corePoolSize;

  @Override
  protected ScheduledExecutorService newScheduledExecutorService() {
    return Executors.newScheduledThreadPool(corePoolSize);
  }
}
