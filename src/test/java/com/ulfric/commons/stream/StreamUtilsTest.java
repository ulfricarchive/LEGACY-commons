package com.ulfric.commons.stream;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(StreamUtils.class)
public class StreamUtilsTest extends UtilTestBase {

	@Test
	void testCut_clearsOldStreamAndGivesNew()
	{
		Stream<String> original = Arrays.asList("hello").stream();
		Stream<String> replacer = StreamUtils.cut(original);
		Verify.that(original).isClosed();
		Verify.that(replacer).contains("hello");
	}

}