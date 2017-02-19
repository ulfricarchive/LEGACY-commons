package com.ulfric.commons.bean;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.google.gson.Gson;
import com.ulfric.commons.exception.Try;
import com.ulfric.commons.reflect.HandleUtils;

public abstract class Bean<T extends Bean<T>> implements Serializable, Cloneable {

	private static final Gson GSON = new Gson();
	private static final Map<Class<? extends Bean>, List<MethodHandle>> FIELDS = new HashMap<>();
	private static final long serialVersionUID = 0L;

	@Override
	public final boolean equals(Object that)
	{
		if (this == that)
		{
			return true;
		}

		if (that == null || this.getClass() != that.getClass())
		{
			return false;
		}

		return this.generateIfEmptyAndGet()
				.stream()
				.allMatch(handle -> this.valuesEqual(handle, that));
	}

	private boolean valuesEqual(MethodHandle handle, Object that)
	{
		Object thisValue = Try.to(() -> handle.invokeExact((Object) this));
		Object thatValue = Try.to(() -> handle.invokeExact(that));

		return Objects.deepEquals(thisValue, thatValue);
	}

	@Override
	public final int hashCode()
	{
		int result = 17;

		for (MethodHandle handle : this.generateIfEmptyAndGet())
		{
			Object value = Try.to(() -> handle.invokeExact((Object) this));

			if (value != null)
			{
				int add;

				if (value.getClass().isArray())
				{
					add = Arrays.deepHashCode(new Object[] { value });
				}
				else
				{
					add = value.hashCode();
				}
				result = 37 * result + add;
			}
		}

		return result;
	}

	@Override
	public final String toString()
	{
		return Bean.GSON.toJson(this);
	}

	@Override
	public final T clone()
	{
		@SuppressWarnings("unchecked")
		T clone = (T) SerializationUtils.clone(this);
		return clone;
	}

	private List<MethodHandle> generateIfEmptyAndGet()
	{
		return Bean.FIELDS.computeIfAbsent(this.getClass(), key ->
				FieldUtils.getAllFieldsList(key)
						.stream()
						.filter(this::isPartOfBean)
						.peek(field -> field.setAccessible(true))
						.map(HandleUtils::createGenericGetter)
						.collect(Collectors.toList()));
	}

	private boolean isPartOfBean(Field field)
	{
		int modifiers = field.getModifiers();
		return !Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers);
	}

}