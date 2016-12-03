package com.ulfric.commons.convert;

final class ConverterToString extends Converter<String> {

	public static final Converter<String> INSTANCE = new ConverterToString();

	private ConverterToString()
	{
		super(MultiType.of(Object.class));
	}

	@Override
	public String apply(MultiObject from)
	{
		return from.toString();
	}

}