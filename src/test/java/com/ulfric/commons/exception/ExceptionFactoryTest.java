package com.ulfric.commons.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@DisplayName("Exception Factory")
@RunWith(JUnitPlatform.class)
class ExceptionFactoryTest {

	private final ExceptionFactory<Ex> factory = ExceptionFactory.of(Ex.class);

	@Test
	void testIsNotNull()
	{
		Verify.that(this.factory).isNotNull();
	}

	@Test
	void testIsNotUnique()
	{
		Verify.that(() -> ExceptionFactory.of(Ex.class)).suppliesNonUniqueValues();
	}

	@Test
	void testThrowNullPointer()
	{
		this.expect(() -> this.factory.raise(NullPointerException.class));
	}

	@Test
	void testThrowNullPointerCauseIsNullPointer()
	{
		try
		{
			this.factory.raise(NullPointerException.class);
			throw new RuntimeException("This should never happen!");
		}
		catch (Ex e)
		{
			Verify.that(e).wasCausedBy(NullPointerException.class);
		}
	}

	private void expect(Executable executable)
	{
		Assertions.assertThrows(Ex.class, executable);
	}

	@SuppressWarnings("serial")
	static class Ex extends RuntimeException
	{
		Ex(Throwable throwable)
		{
			super(throwable);
		}
	}

}