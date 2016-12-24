package com.ulfric.commons.convert;

public interface ConversionService {

	public static ConversionService newInstance()
	{
		return new SimpleConversionService();
	}

	Conversion produce();

	Conversion convert(Object from);

	Conversion convert(Object... from);

	void register(Converter<?> converter);

}