package com.github.itzstonlex.planoframework.exception;

import com.github.itzstonlex.planoframework.param.TaskParamKey;
import org.jetbrains.annotations.NotNull;

public class PlanoParamNotFoundException extends RuntimeException {

  public PlanoParamNotFoundException(@NotNull TaskParamKey<?> key) {
    super(String.format("Value of parameter '%s' isn`t found!", key.getName()));
  }
}
