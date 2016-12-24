package com.ulfric.commons.result;

import java.util.function.Consumer;

import com.ulfric.commons.function.ValueMissingException;

enum EmptyResult implements Result<Object> {

	INSTANCE;

	@Override
	public Object value()
	{
		throw new ValueMissingException();
	}

	@Override
	public Throwable thrown()
	{
		throw new ValueMissingException();
	}

	@Override
	public boolean isSuccess()
	{
		return false;
	}

	@Override
	public boolean isFailure()
	{
		return false;
	}

	@Override
	public Result<Object> ifSuccess(Consumer<Object> run)
	{
		return this;
	}

	@Override
	public Result<Object> ifFailure(Consumer<Throwable> run)
	{
		return this;
	}

	@Override
	public Result<Object> forceRun(Consumer<Object> run)
	{
		return this;
	}

}