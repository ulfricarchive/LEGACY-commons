package com.ulfric.commons.reflect;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

import com.ulfric.commons.api.Util;

public class MethodUtils implements Util {

	public static Optional<Method> getDeclaredMethod(Class<?> clazz, String name)
	{
		Objects.requireNonNull(clazz);
		Objects.requireNonNull(name);

		try
		{
			return Optional.of(clazz.getDeclaredMethod(name));
		}
		catch (NoSuchMethodException | SecurityException ignore)
		{
			return Optional.empty();
		}
	}

	private MethodUtils()
	{
		Util.onConstruct();
	}

}