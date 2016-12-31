package com.ulfric.commons.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Objects;

import org.apache.commons.lang3.exception.ExceptionUtils;

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
			return ExceptionUtils.rethrow(e);
		}
	}

	private HandleUtils()
	{
		throw new UtilInstantiationException();
	}

}