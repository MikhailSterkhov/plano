package com.github.itzstonlex.planoframework.param;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.Nullable;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class TaskParamKey<V> implements Cloneable {

  String name;

  @Setter
  @NonFinal
  V value;

  @NonFinal
  boolean requiredParameter;

  public TaskParamKey(@NonNull String name) {
    this.name = name;
    markRequired();
  }

  public TaskParamKey(@NonNull String name, @Nullable V value) {
    this.name = name;
    this.value = value;
  }

  private void markRequired() {
    this.requiredParameter = Boolean.TRUE;
  }

  @Override
  public TaskParamKey<V> clone() {
    TaskParamKey<V> taskParamKey = new TaskParamKey<>(name, value);

    if (isRequiredParameter()) {
      taskParamKey.markRequired();
    }

    return taskParamKey;
  }
}
