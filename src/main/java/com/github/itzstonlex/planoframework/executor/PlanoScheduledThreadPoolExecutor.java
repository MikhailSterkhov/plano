package com.github.itzstonlex.planoframework.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public final class PlanoScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

  public PlanoScheduledThreadPoolExecutor(int corePoolSize) {
    super(corePoolSize);
  }

  @Override
  public ScheduledFuture<?> schedule(@NotNull Runnable command, long delay, @NotNull TimeUnit unit) {
    return super.schedule(command, delay, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(@NotNull Runnable command, long initialDelay, long period,
      @NotNull TimeUnit unit) {
    return super.scheduleAtFixedRate(command, initialDelay, period, unit);
  }

  @Override
  public <V> ScheduledFuture<V> schedule(@NotNull Callable<V> callable, long delay, @NotNull TimeUnit unit) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ScheduledFuture<?> scheduleWithFixedDelay(@NotNull Runnable command, long initialDelay, long delay,
      @NotNull TimeUnit unit) {
    throw new UnsupportedOperationException();
  }
}
