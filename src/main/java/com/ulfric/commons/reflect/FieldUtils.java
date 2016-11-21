package com.ulfric.commons.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ulfric.commons.api.Util;

public class FieldUtils implements Util {

	public static Optional<Field> getDeclaredField(Class<?> clazz, String name)
	{
		Objects.requireNonNull(clazz);
		Objects.requireNonNull(name);

		try
		{
			return Optional.of(clazz.getDeclaredField(name));
		}
		catch (NoSuchFieldException | SecurityException ignore)
		{
			return Optional.empty();
		}
	}

	public static List<Field> getAllFields(Class<?> clazz)
	{
		Objects.requireNonNull(clazz);

		List<Field> fields = new ArrayList<>();
		for (Class<?> owner = clazz; ClassUtils.isDataClass(owner); owner = owner.getSuperclass())
		{
			for (Field field : owner.getDeclaredFields())
			{
				fields.add(field);
			}
		}
		return fields;
	}

	private FieldUtils()
	{
		Util.onConstruct();
	}

}