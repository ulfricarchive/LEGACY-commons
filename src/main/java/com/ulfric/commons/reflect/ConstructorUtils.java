package com.ulfric.commons.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.ulfric.commons.api.UtilInstantiationException;

public final class ConstructorUtils {

	public static <T> Constructor<T> getDeclaredConstructor(Class<T> targetClass, Class<?>... parameters)
	{
		try
		{
			Constructor<T> constructor = targetClass.getDeclaredConstructor(parameters);
			constructor.setAccessible(true);
			return constructor;
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static <T> T create(Constructor<T> constructor, Object... parameters)
	{
		constructor.setAccessible(true);
		try
		{
			return constructor.newInstance(parameters);
		}
		catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e)
		{
			throw new RuntimeException(e);
		}
	}

	private ConstructorUtils()
	{
		throw new UtilInstantiationException();
	}

}
