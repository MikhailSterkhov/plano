package com.github.itzstonlex.planoframework.exception;

import org.jetbrains.annotations.NotNull;

public class PlanoAwaitTimeoutException extends RuntimeException {

  public PlanoAwaitTimeoutException() {
    super();
  }

  public PlanoAwaitTimeoutException(@NotNull Throwable exception) {
    super(exception);
  }
}
