package com.ulfric.commons.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@DisplayName("Util")
@RunWith(JUnitPlatform.class)
final class UtilTest {

	@Test
	@DisplayName("Util.onConstruct() throws UtilInstantiationException")
	void testOnConstructThrowsUtilInstantiationException()
	{
		Assertions.expectThrows(UtilInstantiationException.class, Util::onConstruct);
	}

}