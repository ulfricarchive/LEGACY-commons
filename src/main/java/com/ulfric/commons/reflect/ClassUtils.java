package com.ulfric.commons.reflect;

import java.util.Optional;

import com.ulfric.commons.api.Util;

public class ClassUtils implements Util {

	public static Optional<Class<?>> getClass(String className)
	{
		try
		{
			return Optional.of(Class.forName(className));
		}
		catch (ClassNotFoundException ignore)
		{
			return Optional.empty();
		}
	}

	public static boolean isDataClass(Class<?> clazz)
	{
		return clazz != null && clazz != Object.class && !clazz.isInterface();
	}

	private ClassUtils()
	{
		Util.onConstruct();
	}

}
