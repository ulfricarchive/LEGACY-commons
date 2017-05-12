package com.ulfric.commons.text;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
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
	void testFormatDouble20_0()
	{
		Verify.that(FormatUtils.formatDouble(20.0)).isEqualTo("20");
	}

	@Test
	void testFormatDouble20D()
	{
		Verify.that(FormatUtils.formatDouble(20D)).isEqualTo("20");
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
	
	@Test
	void testFormatRemainingPositive()
	{
		Verify.that(FormatUtils.formatRemaining(630000L)).isEqualTo("10m 30s");
	}
	
	@Test
	void testFormatRemainingHours()
	{
		Verify.that(FormatUtils.formatRemaining(3960000L)).isEqualTo("1h 6m 0s");
	}
	
	@Test
	void testFormatRemainingDays()
	{
		Verify.that(FormatUtils.formatRemaining(86700000L)).isEqualTo("1d 0h 5m 0s");
	}
	
	@Test
	void testFormatRemainingEmpty()
	{
		Verify.that(FormatUtils.formatRemaining(-1L)).isEqualTo("0m 0s");
	}

}