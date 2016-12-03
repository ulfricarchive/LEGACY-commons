package com.ulfric.commons.exception;

@SuppressWarnings("serial")
public final class Failure extends RuntimeException {

	private static final ExceptionFactory<Failure> FACTORY = 
			ExceptionFactory.of(Failure.class);

	public static <DUMMY> DUMMY raise(Throwable throwable)
	{
		return Failure.FACTORY.raise(throwable);
	}

	public static <DUMMY> DUMMY raise(Class<? extends Throwable> throwable)
	{
		return Failure.FACTORY.raise(throwable);
	}

	public static <DUMMY> DUMMY raise(Class<? extends Throwable> throwable, String message)
	{
		return Failure.FACTORY.raise(throwable, message);
	}

	private Failure() { }

	private Failure(String message)
	{
		super(message);
	}

	private Failure(Throwable throwable)
	{
		super(throwable);
	}

}