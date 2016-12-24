package com.ulfric.commons.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.IdentityHashMap;
import java.util.Map;

import com.ulfric.commons.result.Result;

final class ExceptionFactory<X extends Throwable> {

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

	public <DUMMY> DUMMY raise(Class<? extends Throwable> exception)
	{
		try
		{
			Constructor<? extends Throwable> constr = exception.getDeclaredConstructor();
			constr.setAccessible(true);
			return this.raise(constr.newInstance());
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
				NoSuchMethodException | SecurityException | InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}

	public <DUMMY> DUMMY raise(Class<? extends Throwable> throwable, String message)
	{
		try
		{
			Constructor<? extends Throwable> constructor = throwable.getDeclaredConstructor(String.class);
			constructor.setAccessible(true);
			Throwable thrw = constructor.newInstance(message);
			return this.raise(thrw);
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException |
				IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public <DUMMY> DUMMY raise(Throwable throwable)
	{
		this.sneakyThrow(throwable);
		return null;
	}

	private void sneakyThrow(Throwable throwable)
	{
		Result.tryCall(() -> this.constructor.newInstance(throwable)).ifSuccess(ExceptionFactory::sneaky);
	}

	@SuppressWarnings("unchecked")
	private static <T extends Throwable> void sneaky(Throwable throwable) throws T
	{
		throw (T) throwable;
	}

}