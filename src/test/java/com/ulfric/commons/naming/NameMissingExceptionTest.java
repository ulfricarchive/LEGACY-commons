package com.ulfric.commons.naming;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
class NameMissingExceptionTest {

	@Test
	void testConstructorDoesNotThrowExceptions()
	{
		Verify.that(() -> new NameMissingException()).runsWithoutExceptions();
	}

}