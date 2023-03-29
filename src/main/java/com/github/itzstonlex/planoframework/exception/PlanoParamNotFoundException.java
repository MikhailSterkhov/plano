package com.github.itzstonlex.planoframework.exception;

import com.github.itzstonlex.planoframework.param.TaskParamKey;

public class PlanoParamNotFoundException extends RuntimeException {

  public PlanoParamNotFoundException(TaskParamKey<?> key) {
    super(String.format("Value of parameter '%s' isn`t found!", key.getName()));
  }
}
