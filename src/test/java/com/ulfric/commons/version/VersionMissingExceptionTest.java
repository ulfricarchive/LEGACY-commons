package com.ulfric.commons.version;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
class VersionMissingExceptionTest {

	@Test
	void testConstructorDoesNotThrowExceptions()
	{
		Verify.that(VersionMissingExceptionTest::new).runsWithoutExceptions();
	}

}