package com.ulfric.commons.convert;

import java.util.function.Supplier;

final class SimpleProducer<R> extends Producer<R> {

	SimpleProducer(Class<R> to, Supplier<? extends R> supplier)
	{
		super(to);
		this.supplier = supplier;
	}

	private final Supplier<? extends R> supplier;

	@Override
	public R get()
	{
		return this.supplier.get();
	}

}