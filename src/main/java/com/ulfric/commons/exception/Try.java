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
			throw Try.getPropogated(throwable);
		}
	}

	public static <T> T to(TrySupplier<T> supplier)
	{
		try
		{
			return supplier.get();
		}
		catch (Throwable throwable)
		{
			throw Try.getPropogated(throwable);
		}
	}

	public static void to(TryRunnable runnable)
	{
		try
		{
			runnable.run();
		}
		catch (Throwable throwable)
		{
			throw Try.getPropogated(throwable);
		}
	}

	private static RuntimeException getPropogated(Throwable throwable)
	{
		return (throwable instanceof RuntimeException) ? (RuntimeException) throwable : new RuntimeException(throwable);
	}

}