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

public abstract class Bean implements Serializable, Cloneable {

	private static final Gson GSON = new Gson();
	private static final Map<Class<? extends Bean>, List<MethodHandle>> FIELDS = new HashMap<>();
	private static final long serialVersionUID = 0L;

	@Override
	public final boolean equals(Object object)
	{
		if (this == object)
		{
			return true;
		}

		if (object == null || this.getClass() != object.getClass())
		{
			return false;
		}

		Bean that = (Bean) object;

		for (MethodHandle handle : this.generateIfEmptyAndGet())
		{
			if (! Try.to(() -> Objects.deepEquals(handle.invokeExact((Object) this), (handle.invokeExact(that)))) )
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public final int hashCode()
	{
		int result = 17;

		for (MethodHandle handle : this.generateIfEmptyAndGet())
		{
			int add = Try.to(() ->
			{
				Object value = handle.invokeExact((Object) this);

				if (value == null)
				{
					return 0;
				}

				if (value.getClass().isArray())
				{
					return Arrays.deepHashCode((Object[]) value);
				}

				return value.hashCode();
			});

			result = 37 * result + add;
		}

		return result;
	}

	@Override
	public final String toString()
	{
		return GSON.toJson(this);
	}

	@Override
	public final Bean clone()
	{
		return SerializationUtils.clone(this);
	}

	private List<MethodHandle> generateIfEmptyAndGet()
	{
		return Bean.FIELDS.computeIfAbsent(this.getClass(), ignored ->
				FieldUtils.getAllFieldsList(this.getClass())
						.stream()
						.filter(field -> ! this.isTransientOrStatic(field))
						.peek(field -> field.setAccessible(true))
						.map(HandleUtils::createGenericGetter)
						.collect(Collectors.toList()));
	}

	private boolean isTransientOrStatic(Field field)
	{
		int modifiers = field.getModifiers();

		return Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers);
	}

}