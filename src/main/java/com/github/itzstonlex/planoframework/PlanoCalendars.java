package com.github.itzstonlex.planoframework;

import com.github.itzstonlex.planoframework.impl.HashMapCalendarImpl;

public final class PlanoCalendars {

  public static PlanoCalendar createHashMapCalendar(int corePoolSize) {
    return new HashMapCalendarImpl(corePoolSize);
  }

  public static PlanoCalendar createSingleHashMapCalendar() {
    return createHashMapCalendar(1);
  }
}
