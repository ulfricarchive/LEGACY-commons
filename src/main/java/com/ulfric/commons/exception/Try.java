package com.ulfric.commons.exception;

import java.util.concurrent.Future;

import com.ulfric.commons.func.CheckedFunction;
import com.ulfric.commons.func.CheckedRunnable;
import com.ulfric.commons.func.CheckedSupplier;

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

	public static <T> T to(CheckedSupplier<T> supplier)
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

	public static <T, R> R to(CheckedFunction<T, R> function, CheckedSupplier<T> supplier)
	{
		try
		{
			return function.get(supplier.get());
		}
		catch (Throwable throwable)
		{
			throw Try.getPropogated(throwable);
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
			throw Try.getPropogated(throwable);
		}
	}

	private static RuntimeException getPropogated(Throwable throwable)
	{
		return (throwable instanceof RuntimeException) ? (RuntimeException) throwable : new RuntimeException(throwable);
	}

}