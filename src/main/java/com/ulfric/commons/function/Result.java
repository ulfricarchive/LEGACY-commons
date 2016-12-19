package com.ulfric.commons.function;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class Result<R> {

	public static <R> Result<R> of(R value)
	{
		return new ValueResult<>(value);
	}

	public static <R> Result<R> ofThrown(Throwable failure)
	{
		Objects.requireNonNull(failure);

		return new ThrownResult<>(failure);
	}

	public abstract R value();

	public abstract Throwable thrown();

	public abstract Result<R> ifSuccess(Consumer<R> run);

	public abstract Result<R> forceRun(Consumer<Object> run);

	private static final class ValueResult<R> extends Result<R>
	{
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
		public Result<R> forceRun(Consumer<Object> run)
		{
			run.accept(this.value);
			return this;
		}
	}

	private static final class ThrownResult<R> extends Result<R>
	{
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
		public Result<R> ifSuccess(Consumer<R> run)
		{
			return this;
		}

		@Override
		public Result<R> forceRun(Consumer<Object> run)
		{
			run.accept(this.thrown);
			return this;
		}
	}

}