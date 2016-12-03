package com.ulfric.commons.convert;

import java.util.Objects;
import java.util.function.Function;

public abstract class Converter<R> implements Function<MultiObject, R> {

	public Converter(MultiType from)
	{
		Objects.requireNonNull(from);

		this.from = from;
		this.to = MultiType.of(TypeBridge.<R>of(this.getClass()).getType());
	}

	public Converter(MultiType from, MultiType to)
	{
		Objects.requireNonNull(from);
		Objects.requireNonNull(to);

		this.from = from;
		this.to = to;
	}

	private final MultiType from;
	private final MultiType to;

	public final MultiType getFrom()
	{
		return this.from;
	}

	public final MultiType getTo()
	{
		return this.to;
	}

	@Override
	public abstract R apply(MultiObject from);

}