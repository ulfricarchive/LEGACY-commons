package com.ulfric.commons.convert;

import java.util.function.Supplier;

public abstract class Producer<R> extends Converter<R> implements Supplier<R> {

	public Producer()
	{
		super(MultiType.empty());
	}

	public Producer(Class<R> to)
	{
		super(MultiType.empty(), MultiType.of(to));
	}

	@Override
	public final R apply(MultiObject from)
	{
		return this.get();
	}

}