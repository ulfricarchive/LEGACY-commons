package com.ulfric.commons.convert;

@SuppressWarnings("serial")
public class ConversionException extends RuntimeException {

	ConversionException() { }

	ConversionException(String message) { super(message); }

	ConversionException(Throwable throwable) { super(throwable); }

}