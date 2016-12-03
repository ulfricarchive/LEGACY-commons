package com.ulfric.commons.reflect;

import java.util.IdentityHashMap;
import java.util.Map;

import com.ulfric.commons.api.Util;

public class PrimitiveUtils implements Util {

	private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_OBJECT = new IdentityHashMap<>();

	static
	{
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(void.class, Void.class);
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(boolean.class, Boolean.class);
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(byte.class, Byte.class);
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(char.class, Character.class);
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(short.class, Short.class);
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(int.class, Integer.class);
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(long.class, Long.class);
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(float.class, Float.class);
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(double.class, Double.class);
		PrimitiveUtils.PRIMITIVE_TO_OBJECT.put(null, Object.class);
	}

	public static Class<?> box(Class<?> clazz)
	{
		return PrimitiveUtils.PRIMITIVE_TO_OBJECT.getOrDefault(clazz, clazz);
	}

	private PrimitiveUtils()
	{
		Util.onConstruct();
	}

}