package com.github.itzstonlex.planoframework;

/**
 * Наследуя данный интерфейс, реализация будет
 * означать возможность отмены действия, зарегистрированного
 * в рамках самой реализации:
 * <pre>{@code
 * Cancellable cancellable = ...;
 *
 * boolean isCancelled = cancellable.cancel();
 * System.out.printf("Process cancellation state: %s%n", isCancelled);
 * }</pre>
 */
@FunctionalInterface
public interface Cancellable {

  /**
   * Отмена зарегистрированного действия
   * в рамках реализации
   *
   * @return - результат отмены действия.
   */
  boolean cancel();
}
