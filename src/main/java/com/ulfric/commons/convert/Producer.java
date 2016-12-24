package com.ulfric.commons.convert;

import java.util.function.Supplier;

public abstract class Producer<R> extends Converter<R> implements Supplier<R> {

	public static <R> Producer<R> newInstance(Class<R> to, Supplier<? extends R> supplier)
	{
		return new SimpleProducer<>(to, supplier);
	}

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