package com.ulfric.commons.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.IdentityHashMap;
import java.util.Map;

import com.ulfric.commons.api.Base;

public final class ExceptionFactory<X extends Throwable> extends Base {

	private static final Map<Class<? extends Throwable>, ExceptionFactory<?>> FACTORIES = new IdentityHashMap<>();

	public static <X extends Throwable> ExceptionFactory<X> of(Class<X> type)
	{
		@SuppressWarnings("unchecked")
		ExceptionFactory<X> factory =
			(ExceptionFactory<X>) ExceptionFactory.FACTORIES.computeIfAbsent(type, ExceptionFactory::newInstance);
		return factory;
	}

	private static <X extends Throwable> ExceptionFactory<X> newInstance(Class<X> type)
	{
		return new ExceptionFactory<>(ExceptionFactory.getConstructor(type));
	}

	private static <X extends Throwable> Constructor<X> getConstructor(Class<X> type)
	{
		try
		{
			Constructor<X> constructor = type.getDeclaredConstructor(Throwable.class);
			constructor.setAccessible(true);
			return constructor;
		}
		catch (NoSuchMethodException | SecurityException exception)
		{
			throw new IllegalArgumentException(exception);
		}
	}

	private ExceptionFactory(Constructor<X> constructor)
	{
		this.constructor = constructor;
	}

	private final transient Constructor<X> constructor;

	public <DUMMY, T extends Throwable> DUMMY raise(Class<T> exception)
	{
		this.sneakyThrow(exception);
		return null;
	}

	public <DUMMY, T extends Throwable> DUMMY raise(Class<T> exception, String message)
	{
		this.sneakyThrow(exception, message);
		return null;
	}

	private <T extends Throwable> void sneakyThrow(Class<T> exception)
	{
		try
		{
			this.sneakyThrow(exception.newInstance());
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	private <T extends Throwable> void sneakyThrow(Class<T> exception, String message)
	{
		try
		{
			Constructor<T> constructor = exception.getConstructor(String.class);
			this.sneakyThrow(constructor.newInstance(message));
		}
		catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
				SecurityException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	private void sneakyThrow(Throwable throwable)
	{
		try
		{
			Throwable exception = this.constructor.newInstance(throwable);
			ExceptionFactory.<RuntimeException>sneaky(exception);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new ShouldNeverHappenException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends Throwable> void sneaky(Throwable throwable) throws T
	{
		throw (T) throwable;
	}

}