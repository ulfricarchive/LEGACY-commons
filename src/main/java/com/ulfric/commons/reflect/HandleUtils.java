package com.ulfric.commons.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Objects;

import com.ulfric.commons.exception.Try;

public enum HandleUtils {

	;

	public static MethodHandle createGenericSetter(Field field)
	{
		MethodHandle setter = HandleUtils.createSetter(field);
		return setter.asType(setter.type().erase());
	}

	public static MethodHandle createSetter(Field field)
	{
		Objects.requireNonNull(field);

		return Try.to(() -> MethodHandles.lookup().unreflectSetter(field));
	}

}