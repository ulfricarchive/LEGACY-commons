package com.ulfric.commons.except;

import com.ulfric.commons.api.UtilInstantiationException;

public final class TryUtils {

	public static void sneaky(SneakyRunnable runnable)
	{
		try
		{
			runnable.run();
		}
		catch (Throwable throwable)
		{
			throw new SneakyException(throwable);
		}
	}

	public static <T> T sneaky(SneakySupplier<T> supplier)
	{
		try
		{
			return supplier.supply();
		}
		catch (Throwable throwable)
		{
			throw new SneakyException(throwable);
		}
	}

	private static final class SneakyException extends RuntimeException {

		public SneakyException(Throwable throwable)
		{
			super(throwable);
		}

	}

	private TryUtils()
	{
		throw new UtilInstantiationException();
	}

}
