package com.github.itzstonlex.planoframework.exception;

import org.jetbrains.annotations.NotNull;

public class PlanoNonResponseException extends Exception {

  public PlanoNonResponseException() {
    super();
  }

  public PlanoNonResponseException(@NotNull Throwable ex) {
    super(ex);
  }
}
