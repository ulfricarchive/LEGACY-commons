package com.ulfric.commons.exception;

import java.util.concurrent.Callable;

import com.ulfric.commons.api.UtilInstantiationException;
import com.ulfric.commons.function.Result;

public class CallableUtils {

	public static <R> Result<R> tryCall(Callable<R> callable)
	{
		try
		{
			return Result.of(callable.call());
		}
		catch (Exception failure)
		{
			return Result.ofThrown(failure);
		}
	}

	private CallableUtils()
	{
		throw new UtilInstantiationException();
	}

}