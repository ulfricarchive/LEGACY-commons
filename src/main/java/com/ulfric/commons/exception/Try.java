package com.ulfric.commons.exception;

import java.util.concurrent.Future;

public enum Try {

	;

	public static <T> T to(Future<T> future)
	{
		try
		{
			return future.get();
		}
		catch (Throwable throwable)
		{
			throw ThrowableUtils.getPropogated(throwable);
		}
	}

	public static <T> T to(CheckedSupplier<T> supplier)
	{
		try
		{
			return supplier.get();
		}
		catch (Throwable throwable)
		{
			throw ThrowableUtils.getPropogated(throwable);
		}
	}

	public static <T> void to(CheckedConsumer<T> consumer, T value)
	{
		try
		{
			consumer.accept(value);
		}
		catch (Throwable throwable)
		{
			throw ThrowableUtils.getPropogated(throwable);
		}
	}

	public static <T, R> R to(CheckedFunction<T, R> function, CheckedSupplier<T> supplier)
	{
		try
		{
			return function.get(supplier.get());
		}
		catch (Throwable throwable)
		{
			throw ThrowableUtils.getPropogated(throwable);
		}
	}

	public static void to(CheckedRunnable runnable)
	{
		try
		{
			runnable.run();
		}
		catch (Throwable throwable)
		{
			throw ThrowableUtils.getPropogated(throwable);
		}
	}

}