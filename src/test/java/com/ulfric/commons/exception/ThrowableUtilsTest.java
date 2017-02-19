package com.ulfric.commons.exception;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(ThrowableUtils.class)
public class ThrowableUtilsTest extends UtilTestBase {

	@Test
	void testGetPropogated_returnsRuntimeException()
	{
		Exception exception = new Exception();

		RuntimeException runtimeException = ThrowableUtils.getPropogated(exception);

		Verify.that(runtimeException.getCause()).isEqualTo(exception);
	}

	@Test
	void testGetPropogated_doesntWrapRuntime()
	{
		RuntimeException exception = new RuntimeException();

		RuntimeException runtimeException = ThrowableUtils.getPropogated(exception);

		Verify.that(runtimeException.getCause()).isNull();
	}

}
