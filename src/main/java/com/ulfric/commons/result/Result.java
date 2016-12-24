package com.ulfric.commons.result;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public interface Result<R> {

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

	public static <R> Result<R> empty()
	{
		@SuppressWarnings("unchecked")
		Result<R> result = (Result<R>) EmptyResult.INSTANCE;
		return result;
	}

	public static <R> Result<R> of(R value)
	{
		return new ValueResult<>(value);
	}

	public static <R> Result<R> ofThrown(Throwable failure)
	{
		Objects.requireNonNull(failure);

		return new ThrownResult<>(failure);
	}

	R value();

	Throwable thrown();

	boolean isSuccess();

	boolean isFailure();

	Result<R> ifSuccess(Consumer<R> run);

	Result<R> ifFailure(Consumer<Throwable> run);

	Result<R> forceRun(Consumer<Object> run);

}