package com.ulfric.commons.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Objects;

import com.ulfric.commons.api.UtilInstantiationException;

public class HandleUtils {

	public static MethodHandle createSetter(Field field)
	{
		Objects.requireNonNull(field);

		try
		{
			return MethodHandles.lookup().unreflectSetter(field);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	private HandleUtils()
	{
		throw new UtilInstantiationException();
	}

}