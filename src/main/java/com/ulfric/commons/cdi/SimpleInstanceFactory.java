package com.ulfric.commons.cdi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.ulfric.commons.reflect.proxy.ProxyUtils;
import com.ulfric.commons.result.Result;

final class SimpleInstanceFactory implements InstanceFactory {

	SimpleInstanceFactory(Injector injector)
	{
		this.injector = injector;
	}

	private final Injector injector;
	private final InstancePool pool = new InstancePool(this);

	@Override
	public <T> T getOrCreateInstance(Class<T> request)
	{
		return this.pool.getOrCreate(request);
	}

	@Override
	public <T> T createInstance(Class<T> request)
	{
		return this.tryToCreateInstance(request).value();
	}

	@Override
	public <T> Result<T> tryToCreateInstance(Class<T> request)
	{
		if (request.isInterface())
		{
			T instance = this.createInstanceFromInterface(request);
			return Result.of(instance);
		}

		Result<T> instance = this.createInstanceFromDefaultConstructor(request);

		if (instance.isSuccess())
		{
			this.injector.injectValues(instance.value());
			return instance;
		}

		// TODO unsafe instance making

		return Result.empty();
	}

	private <T> T createInstanceFromInterface(Class<T> request)
	{
		return ProxyUtils.newDeadProxy(request);
	}

	private <T> Result<T> createInstanceFromDefaultConstructor(Class<T> request)
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

}