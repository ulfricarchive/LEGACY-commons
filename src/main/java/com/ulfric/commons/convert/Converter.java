package com.ulfric.commons.convert;

import java.util.Objects;
import java.util.function.Function;

public abstract class Converter<T> implements Function<MultiObject, T> {

	public Converter(MultiType from)
	{
		Objects.requireNonNull(from);
		this.from = from;
		this.to = MultiType.of(TypeBridge.<T>of(this.getClass()).getType());
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
	public abstract T apply(MultiObject from);

}