package com.ulfric.commons.convert;

import java.util.function.Function;

import com.ulfric.commons.exception.Failure;

public abstract class SingleConverter<T, R> extends Converter<R> {

	public static <T, R> Converter<R> of(Class<T> from, Class<R> to, Function<T, R> function)
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

	public SingleConverter(Class<T> from)
	{
		super(MultiType.of(from));
	}

	public SingleConverter(Class<T> from, Class<R> to)
	{
		super(MultiType.of(from), MultiType.of(to));
	}

	@Override
	public final R apply(MultiObject from)
	{
		if (from instanceof MultiObject.MultiObjectSingle)
		{
			MultiObject.MultiObjectSingle fromSingle = (MultiObject.MultiObjectSingle) from;
			@SuppressWarnings("unchecked")
			T fromValue = (T) fromSingle.value;
			return this.convert(fromValue);
		}

		return Failure.raise(ConversionException.class);
	}

	public abstract R convert(T from);

}