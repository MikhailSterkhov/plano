package com.github.itzstonlex.planoframework.param.cache;

import com.github.itzstonlex.planoframework.exception.PlanoParamNotFoundException;
import com.github.itzstonlex.planoframework.param.TaskParamKey;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor
public class TaskParamCache extends ConcurrentHashMap<TaskParamKey<?>, Object>
    implements Map<TaskParamKey<?>, Object> {

  public TaskParamCache(Map<TaskParamKey<?>, Object> other) {
    super(other);
  }

  @SuppressWarnings("unchecked")
  private <V> WeakReference<V> cast(@Nullable V obj) {
    return ((WeakReference<V>) obj);
  }

  private <V> WeakReference<V> wrap(@Nullable V obj) {
    if (obj == null)
      return null;

    return new WeakReference<>(obj);
  }

  private <V> V unwrap(@Nullable V obj) {
    if (obj == null)
      return null;

    if (!(obj instanceof WeakReference))
      return obj;

    return cast(obj).get();
  }

  public <V> V get(TaskParamKey<V> key) {
    return getOrDefault(key, key.getValue());
  }

  @Override
  public Object get(Object key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsValue(Object value) {
    return super.containsValue(wrap(value));
  }

  @SuppressWarnings("unchecked")
  public <V> V put(@NotNull TaskParamKey<V> key, @Nullable V value) {
    return (V) unwrap(super.put(key, wrap(value)));
  }

  @Override
  public Object remove(@NotNull Object key) {
    return super.remove(key);
  }

  @Override
  public Collection<Object> values() {
    return super.values().stream().map(this::unwrap).collect(Collectors.toSet());
  }

  @SuppressWarnings("unchecked")
  public <V> V getOrDefault(TaskParamKey<V> key, V defaultValue) {
    V value = (V) unwrap(super.get(key));
    return value == null ? defaultValue : value;
  }

  @Override
  public Object getOrDefault(Object key, Object defaultValue) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void forEach(BiConsumer<? super TaskParamKey<?>, ? super Object> action) {
    super.forEach((scheduleParamKey, o) -> action.accept(scheduleParamKey, unwrap(o)));
  }
}
