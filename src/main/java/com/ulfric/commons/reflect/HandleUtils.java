package com.ulfric.commons.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import com.ulfric.commons.exception.Try;

public enum HandleUtils {

	;

	public static MethodHandle createGenericSetter(Field field)
	{
		MethodHandle setter = HandleUtils.createSetter(field);
		return HandleUtils.convertToGenerics(setter);
	}

	public static MethodHandle createSetter(Field field)
	{
		Objects.requireNonNull(field);

		return Try.to(() -> MethodHandles.lookup().unreflectSetter(field));
	}

	public static MethodHandle getGenericMethod(Method method)
	{
		MethodHandle handle = HandleUtils.getMethod(method);
		return HandleUtils.convertToGenerics(handle);
	}

	public static MethodHandle getMethod(Method method)
	{
		Objects.requireNonNull(method);

		return Try.to(() -> MethodHandles.lookup().unreflect(method));
	}

	private static MethodHandle convertToGenerics(MethodHandle handle)
	{
		return handle.asType(handle.type().erase());
	}

}