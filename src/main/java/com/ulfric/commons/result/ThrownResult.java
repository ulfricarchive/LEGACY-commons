package com.ulfric.commons.result;

import java.util.Optional;
import java.util.function.Consumer;

final class ThrownResult<R> implements Result<R> {

	ThrownResult(Throwable thrown)
	{
		this.thrown = thrown;
	}

	private final Throwable thrown;

	@Override
	public R value()
	{
		throw new ValueMissingException();
	}

	@Override
	public Throwable thrown()
	{
		return this.thrown;
	}

	@Override
	public Optional<R> toOptional()
	{
		return Optional.empty();
	}

	@Override
	public Result<R> ifSuccess(Consumer<R> run)
	{
		return this;
	}

	@Override
	public Result<R> ifFailure(Consumer<Throwable> run)
	{
		run.accept(this.thrown);
		return this;
	}

	@Override
	public Result<R> forceRun(Consumer<Object> run)
	{
		run.accept(this.thrown);
		return this;
	}

	@Override
	public boolean isSuccess()
	{
		return false;
	}

	@Override
	public boolean isFailure()
	{
		return true;
	}

}