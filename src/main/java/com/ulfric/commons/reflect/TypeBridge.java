package com.ulfric.commons.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public abstract class TypeBridge {

	public static TypeBridge of(Class<?> resolve)
	{
		return new TypeBridge(resolve) { };
	}

	public static Set<Class<?>> getClasses(Type type)
	{
		if (type instanceof Class)
		{
			return Collections.singleton((Class<?>) type);
		}

		if (type instanceof ParameterizedType)
		{
			ParameterizedType ptype = (ParameterizedType) type;

			Class<?>[] types = Arrays.stream(ptype.getActualTypeArguments())
									 .map(TypeBridge::getClasses)
									 .flatMap(Set::stream)
									 .toArray(Class[]::new);

			return TypeBridge.getClasses(MultiType.of(types));
		}

		if (type instanceof MultiType)
		{
			MultiType mtype = (MultiType) type;
			return mtype.getCommonTypes();
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
	private Iterable<Class<?>> result;

	public Iterable<Class<?>> getTypes()
	{
		if (this.result != null)
		{
			return this.result;
		}
		if (this.resolve != null)
		{
			return this.result =  TypeBridge.getClasses(this.resolve.getGenericSuperclass());
		}
		return this.result = TypeBridge.getClasses(this.getClass().getGenericSuperclass());
	}

}