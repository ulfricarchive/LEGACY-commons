package com.ulfric.commons.convert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public abstract class TypeBridge<T> {

	public static <T> TypeBridge<T> of(Class<?> resolve)
	{
		return new TypeBridge<T>(resolve) { };
	}

	public static Class<?> getClass(Type type)
	{
		System.out.println(type);
		if (type instanceof Class)
		{
			return (Class<?>) type;
		}

		if (type instanceof ParameterizedType)
		{
			ParameterizedType ptype = (ParameterizedType) type;

			Class<?>[] types = Arrays.stream(ptype.getActualTypeArguments())
									 .map(TypeBridge::getClass)
									 .toArray(Class[]::new);

			return TypeBridge.getClass(MultiType.of(types));
		}

		if (type instanceof MultiType)
		{
			MultiType mtype = (MultiType) type;
			return mtype.getSharedType();
		}

		throw new UnsupportedOperationException("Bad type: " + type.getClass());
	}

	public TypeBridge()
	{
		this(null);
	}

	TypeBridge(Class<?> resolve)
	{
		this.resolve = resolve;
	}

	private final Class<?> resolve;
	private Class<T> result;

	@SuppressWarnings("unchecked")
	public Class<T> getType()
	{
		if (this.result != null)
		{
			return this.result;
		}
		if (this.resolve != null)
		{
			return this.result =  (Class<T>) TypeBridge.getClass(this.resolve.getGenericSuperclass());
		}
		return this.result = (Class<T>) TypeBridge.getClass(this.getClass().getGenericSuperclass());
	}

}