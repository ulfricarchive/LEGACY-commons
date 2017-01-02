package com.ulfric.commons.convert;

@SuppressWarnings("serial")
public class ConversionException extends RuntimeException {

	public ConversionException()
	{

	}

	public ConversionException(String message)
	{
		super(message);
	}

	public ConversionException(Throwable throwable)
	{
		super(throwable);
	}

}