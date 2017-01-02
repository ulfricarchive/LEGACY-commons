package com.ulfric.commons.text;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.api.Util;
import com.ulfric.commons.api.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(FormatUtils.class)
class FormatUtilsTest extends UtilTestBase {

	@Test
	void testFormatLong100()
	{
		Verify.that(FormatUtils.formatLong(100)).isEqualTo("100");
	}

	@Test
	void testFormatLong1000()
	{
		Verify.that(FormatUtils.formatLong(1000)).isEqualTo("1,000");
	}

	@Test
	void testFormatLong0000()
	{
		Verify.that(FormatUtils.formatLong(0000)).isEqualTo("0");
	}

	@Test
	void testFormatLongNegative1000()
	{
		Verify.that(FormatUtils.formatLong(-1000)).isEqualTo("-1,000");
	}

	@Test
	void testFormatLong9999999999()
	{
		Verify.that(FormatUtils.formatLong(9999999999L)).isEqualTo("9,999,999,999");
	}

	@Test
	void testFormatMilliseconds1000()
	{
		Verify.that(FormatUtils.formatMilliseconds(1000)).isEqualTo("1,000ms");
	}

	@Test
	void testFormatMilliseconds33()
	{
		Verify.that(FormatUtils.formatMilliseconds(33)).isEqualTo("33ms");
	}

	@Test
	void testFormatMillisecondsNegative57()
	{
		Verify.that(FormatUtils.formatMilliseconds(-57)).isEqualTo("57ms");
	}

}