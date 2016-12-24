package com.ulfric.commons.result;

import java.util.function.Consumer;

import com.ulfric.commons.function.ValueMissingException;

final class ValueResult<R> implements Result<R> {

	ValueResult(R value)
	{
		this.value = value;
	}

	private final R value;

	@Override
	public R value()
	{
		return this.value;
	}

	@Override
	public Throwable thrown()
	{
		throw new ValueMissingException();
	}

	@Override
	public Result<R> ifSuccess(Consumer<R> run)
	{
		run.accept(this.value);
		return this;
	}

	@Override
	public Result<R> ifFailure(Consumer<Throwable> run)
	{
		return this;
	}

	@Override
	public Result<R> forceRun(Consumer<Object> run)
	{
		run.accept(this.value);
		return this;
	}

	@Override
	public boolean isSuccess()
	{
		return true;
	}

	@Override
	public boolean isFailure()
	{
		return false;
	}

}