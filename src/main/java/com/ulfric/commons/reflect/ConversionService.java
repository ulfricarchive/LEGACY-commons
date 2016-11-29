package com.ulfric.commons.reflect;

public final class ConversionService {

	public static ConversionService newInstance()
	{
		return new ConversionService();
	}

	private ConversionService() { }

	public <T> T convert(Object object, T to)
	{
		return convert(Token.of(object), to);
	}

	public <T> T convert(Token token, T to)
	{
		throw new UnsupportedOperationException();
	}

}