package com.ulfric.commons.exception;

import org.junit.jupiter.api.DisplayName;
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
		Verify.that(() -> this.factory.raise(NullPointerException.class)).doesThrow(NullPointerException.class);
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