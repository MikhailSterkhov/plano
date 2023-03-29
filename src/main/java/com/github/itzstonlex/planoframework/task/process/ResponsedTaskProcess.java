package com.github.itzstonlex.planoframework.task.process;

import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import org.jetbrains.annotations.NotNull;

public interface ResponsedTaskProcess<V> extends TaskProcess {

  void withResponse(@NotNull CompletableResponse<V> response);
}
