package com.ulfric.commons.convert.converter;

import java.util.Optional;

import com.ulfric.commons.convert.ConversionException;
import com.ulfric.commons.reflect.MultiObject;
import com.ulfric.commons.reflect.MultiType;

public abstract class SingleConverter<T, R> extends Converter<R> {

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
		Optional<?> firstMatch = from.firstMatch(this.getFrom());

		if (firstMatch.isPresent())
		{
			@SuppressWarnings("unchecked")
			T get = (T) firstMatch.get();
			return this.convert(get);
		}

		throw new ConversionException();
	}

	public abstract R convert(T from);

}