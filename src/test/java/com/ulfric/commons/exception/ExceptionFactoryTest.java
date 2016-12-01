package com.ulfric.commons.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.google.common.truth.Truth;

@DisplayName("Exception Factory")
@RunWith(JUnitPlatform.class)
class ExceptionFactoryTest {

	private final ExceptionFactory<Ex> factory = ExceptionFactory.of(Ex.class);

	@Test
	void testIsNotNull()
	{
		Truth.assertThat(this.factory).isNotNull();
	}

	@Test
	void testIsNotUnique()
	{
		Truth.assertThat(this.factory).isSameAs(ExceptionFactory.of(Ex.class));
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
			Truth.assertThat(e.getCause()).isInstanceOf(NullPointerException.class);
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