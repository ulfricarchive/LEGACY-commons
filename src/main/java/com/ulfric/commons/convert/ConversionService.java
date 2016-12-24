package com.ulfric.commons.convert;

import com.ulfric.commons.convert.converter.Converter;

public interface ConversionService {

	public static ConversionService newInstance()
	{
		return new SimpleConversionService();
	}

	<T> T produce(Class<T> to);

	Object produce(Class<?>... to);

	Conversion convert(Object from);

	Conversion convert(Object... from);

	void register(Converter<?> converter);

}