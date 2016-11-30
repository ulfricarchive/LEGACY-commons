package com.ulfric.commons.exception;

@SuppressWarnings("serial")
final class ShouldNeverHappenException extends RuntimeException {

	public ShouldNeverHappenException(Throwable cause) {
		super(cause);
	}

}