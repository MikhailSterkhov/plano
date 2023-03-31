package com.github.itzstonlex.planoframework.factory;

import com.github.itzstonlex.planoframework.PlanoCalendar;
import com.github.itzstonlex.planoframework.impl.calendar.PooledThreadCalendar;
import com.github.itzstonlex.planoframework.impl.calendar.SingleThreadCalendar;

/**
 * Фабрика для создания календарей с
 * разными подходами в реализации
 * <p>
 * Каждая фабрика возвращает единый
 * пользовательский интерфейс {@link PlanoCalendar}
 */
public final class PlanoCalendars {

  /**
   * Создание календаря, использующий пул
   * потоков для реализации запланированных
   * задач.
   *
   * @param corePoolSize - количество используемых ядер для пула.
   */
  public static PlanoCalendar createPooledThreadCalendar(int corePoolSize) {
    return new PooledThreadCalendar(corePoolSize);
  }

  /**
   * Создание календаря, использующий однопоточный
   * исполнитель для реализации запланированных
   * задач.
   */
  public static PlanoCalendar createSingleThreadCalendar() {
    return new SingleThreadCalendar();
  }
}
