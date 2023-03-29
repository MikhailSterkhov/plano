package com.github.itzstonlex.planoframework.task;

import com.github.itzstonlex.planoframework.task.process.response.CompletableResponse;
import java.util.concurrent.ScheduledFuture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class WrapperScheduledFuture {

  private final ScheduledFuture<?> scheduled;
  private final CompletableResponse<?> completer;

  public boolean cancel(boolean mayInterruptIfRunning) {
    return scheduled.cancel(mayInterruptIfRunning);
  }
}
