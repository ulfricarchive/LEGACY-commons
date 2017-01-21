package com.ulfric.commons.exception;

public enum Try {

	;

	public static <T> T to(TrySupplier<T> supplier)
	{
		try
		{
			return supplier.get();
		}
		catch (Throwable throwable)
		{
			throw new RuntimeException(throwable);
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
			throw new RuntimeException(throwable);
		}
	}

	public static void blind(TryRunnable runnable)
	{
		try
		{
			runnable.run();
		}
		catch (Throwable ignored)
		{}
	}

	public static <T> T blind(TrySupplier<T> supplier)
	{
		try
		{
			return supplier.get();
		}
		catch (Throwable ignored)
		{}

		return null;
	}

}