package com.ulfric.commons.cdi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import com.ulfric.commons.api.UtilInstantiationException;
import com.ulfric.commons.result.Result;
import com.ulfric.commons.result.ValueMissingException;

final class InstanceMaker {

	public static <T> T forceCreate(Class<T> request)
	{
		return InstanceMaker.createInstance(request).orElseThrow(ValueMissingException::new);
	}

	public static <T> Optional<T> createInstance(Class<T> request)
	{
		if (request.isInterface())
		{
			T instance = InstanceMaker.createInstanceFromInterface(request);
			return Optional.ofNullable(instance);
		}

		Result<T> instance = InstanceMaker.createInstanceFromDefaultConstructor(request);

		if (instance.isSuccess())
		{
			return instance.toOptional();
		}

		// TODO unsafe instance making

		return Optional.empty();
	}

	private static <T> T createInstanceFromInterface(Class<T> request)
	{
		throw new UnsupportedOperationException();
	}

	private static <T> Result<T> createInstanceFromDefaultConstructor(Class<T> request)
	{
		try
		{
			// TODO cache constructors
			Constructor<T> defaultConstructor = request.getDeclaredConstructor();
			defaultConstructor.setAccessible(true);
			T instance = defaultConstructor.newInstance((Object[]) null);
			return Result.of(instance);
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException |
				IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			return Result.ofThrown(e);
		}
	}

	private InstanceMaker()
	{
		throw new UtilInstantiationException();
	}

}