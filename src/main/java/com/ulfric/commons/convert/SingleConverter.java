package com.ulfric.commons.convert;

import com.ulfric.commons.exception.Failure;

public abstract class SingleConverter<T> extends Converter<T> {

	public SingleConverter(Class<?> from)
	{
		super(MultiType.of(from));
	}

	@Override
	public final T apply(MultiObject from)
	{
		if (from instanceof MultiObject.MultiObjectSingle)
		{
			MultiObject.MultiObjectSingle fromSingle = (MultiObject.MultiObjectSingle) from;
			return this.convert(fromSingle.value);
		}

		return Failure.raise(ConversionException.class);
	}

	public abstract T convert(Object object);

}