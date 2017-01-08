package com.ulfric.commons.exception;

public enum Try {

	;

	public static <T> T to(TrySupplier<T> supplier)
	{
		try
		{
			return supplier.get();
		}
		catch (Throwable throwable)
		{
			throw new RuntimeException(throwable);
		}
	}

}