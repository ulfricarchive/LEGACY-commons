package com.ulfric.commons.io;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class UniqueIdUtilsTest {

	@Test
	void testParseUniqueIdFromValidUniqueId()
	{
		UUID original = UUID.randomUUID();
		String textual = original.toString();
		Verify.that(UniqueIdUtils.parseUniqueId(textual)).isEqualTo(original);
	}

	@Test
	void testParseUniqueIdFromInvalidUniqueId()
	{
		String textual = "abc";
		Verify.that(UniqueIdUtils.parseUniqueId(textual)).isNull();
	}

}