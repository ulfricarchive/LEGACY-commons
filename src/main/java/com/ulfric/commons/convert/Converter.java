package com.ulfric.commons.convert;

import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ulfric.commons.reflect.MultiObject;
import com.ulfric.commons.reflect.MultiType;
import com.ulfric.commons.reflect.TypeBridge;

public abstract class Converter<R> implements Function<MultiObject, R> {

	public static <T, R> Converter<R> single(Class<T> from, Class<R> to, Function<T, R> function)
	{
		return new SingleConverter<T, R>(from, to)
		{
			@Override
			public R convert(T from)
			{
				return function.apply(from);
			}
		};
	}

	public Converter(MultiType from)
	{
		Objects.requireNonNull(from);

		this.from = from;
		this.to = MultiType.of(TypeBridge.of(this.getClass()).getTypes());
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

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}