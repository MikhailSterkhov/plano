package com.github.itzstonlex.planoframework;

import com.github.itzstonlex.planoframework.param.TaskParamKey;
import com.github.itzstonlex.planoframework.param.TaskParams;
import com.github.itzstonlex.planoframework.param.cache.TaskParamCache;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;

@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskPlan {

  TaskParamCache params;

  @Getter
  @NonFinal
  String id, description;

  public final void interruptOnCancel(boolean flag) {
    params.put(TaskParams.INTERRUPT_ON_CANCEL, flag);
  }

  public final <V> V getParameter(@NotNull TaskParamKey<V> key) {
    return params.get(key);
  }

  public final long getDelay(@NotNull TimeUnit unit) {
    TimeUnit sourceUnit = getParameter(TaskParams.TASK_TIME_UNIT);
    Long sourceDelay = getParameter(TaskParams.TASK_DELAY);

    return unit.convert(sourceDelay, sourceUnit);
  }

  public final long getRepeatDelay(@NotNull TimeUnit unit) {
    TimeUnit sourceUnit = getParameter(TaskParams.TASK_TIME_UNIT);
    Long sourceDelay = getParameter(TaskParams.TASK_REPEAT_DELAY);

    return unit.convert(sourceDelay, sourceUnit);
  }
}
