package com.github.itzstonlex.planoframework.param.cache;

import com.github.itzstonlex.planoframework.param.TaskParamKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskParamCacheBuilder {

  public static TaskParamCacheBuilder create() {
    return new TaskParamCacheBuilder();
  }

  private final TaskParamCache cache = new TaskParamCache();

  public <V> TaskParamCacheBuilder set(@NotNull TaskParamKey<V> key, @Nullable V value) {
    cache.put(key, value);
    return this;
  }

  public TaskParamCacheBuilder unset(@NotNull TaskParamKey<?> key) {
    return set(key, null);
  }

  public TaskParamCache build() {
    return cache;
  }
}
