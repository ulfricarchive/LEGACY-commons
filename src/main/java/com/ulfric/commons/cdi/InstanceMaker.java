package com.ulfric.commons.cdi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.ulfric.commons.api.UtilInstantiationException;
import com.ulfric.commons.reflect.proxy.ProxyUtils;
import com.ulfric.commons.result.Result;

final class InstanceMaker {

	public static <T> T forceCreate(Class<T> request)
	{
		return InstanceMaker.createInstance(request).value();
	}

	public static <T> Result<T> createInstance(Class<T> request)
	{
		if (request.isInterface())
		{
			T instance = InstanceMaker.createInstanceFromInterface(request);
			return Result.of(instance);
		}

		Result<T> instance = InstanceMaker.createInstanceFromDefaultConstructor(request);

		if (instance.isSuccess())
		{
			return instance;
		}

		// TODO unsafe instance making

		return Result.empty();
	}

	private static <T> T createInstanceFromInterface(Class<T> request)
	{
		return ProxyUtils.newDeadProxy(request);
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