package com.github.itzstonlex.planoframework.task.process.response;

import com.github.itzstonlex.planoframework.exception.PlanoNonResponseException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompletableResponse<R> {

  R response;
  Throwable exception;

  private void postComplete(@Nullable R response, @Nullable Throwable exception) {
    this.response = response;
    this.exception = exception;
  }

  public R getResponse() throws PlanoNonResponseException {
    if (response == null) {
      if (exception != null) {
        throw new PlanoNonResponseException(exception);
      }

      throw new PlanoNonResponseException();
    }

    return response;
  }

  public boolean containsData() {
    return response != null || exception != null;
  }

  public void complete(@Nullable R response) {
    postComplete(response, null);
  }

  public void completeExceptionally(@NotNull Throwable exception) {
    postComplete(null, exception);
  }
}
