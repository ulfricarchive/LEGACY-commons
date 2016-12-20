package com.ulfric.commons.api;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
class UtilInstantiationExceptionTest {

	@Test
	void testConstructorDoesNotThrowExceptions()
	{
		Verify.that(() -> new UtilInstantiationException()).runsWithoutExceptions();
	}

}