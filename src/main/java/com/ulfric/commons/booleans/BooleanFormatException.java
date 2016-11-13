package com.ulfric.commons.booleans;

@SuppressWarnings("serial")
public final class BooleanFormatException extends IllegalArgumentException {

	public BooleanFormatException() { }

	public BooleanFormatException(String value)
	{
		super("Illegal value: " + value);
	}

}