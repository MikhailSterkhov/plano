package com.github.itzstonlex.planoframework.task.process;

import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import org.jetbrains.annotations.NotNull;

public interface ResponsedTaskProcess<V> extends TaskProcess {

  void after(@NotNull CompletableResponse<V> response);

  @Override
  default void processAction() {
    // override me.
  }
}
