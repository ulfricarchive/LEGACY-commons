package com.ulfric.commons.arithmetic;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@Util(NumericUtils.class)
public class NumericUtilsTest extends UtilTestBase {
	
	@Test
	void testIsNumericValid()
	{
		String textual = "300";
		Verify.that(NumericUtils.isNumeric(textual)).isTrue();
	}
	
	@Test
	void testIsNumericInvalid()
	{
		String textual = "aaa";
		Verify.that(NumericUtils.isNumeric(textual)).isFalse();
	}
	
	@Test
	void testIsNumericDecimalValid()
	{
		String textual = "34.3";
		Verify.that(NumericUtils.isNumeric(textual)).isTrue();
	}
	
	@Test
	void testIsNumericDecimalInvalid()
	{
		String textual = "a.dre";
		Verify.that(NumericUtils.isNumeric(textual)).isFalse();
	}
	
	@Test
	void testIsIntegerValid()
	{
		String textual = "10000";
		Verify.that(NumericUtils.isInteger(textual)).isTrue();
	}
	
	@Test
	void testIsIntegerInvalid()
	{
		String textual = "abd";
		Verify.that(NumericUtils.isInteger(textual)).isFalse();
	}
	
	@Test
	void testIsLongValid()
	{
		String textual = "10000000000000";
		Verify.that(NumericUtils.isLong(textual)).isTrue();
	}
	
	@Test
	void testIsLongInvalid()
	{
		String textual = "adf";
		Verify.that(NumericUtils.isLong(textual)).isFalse();
	}
	
	@Test
	void testIsFloatValid()
	{
		String textual = "0.1";
		Verify.that(NumericUtils.isFloat(textual)).isTrue();
	}
	
	@Test
	void testIsFloatInvalid()
	{
		String textual = "a.bd";
		Verify.that(NumericUtils.isFloat(textual)).isFalse();
	}
	
	@Test
	void testIsDoubleValid()
	{
		String textual = "10.0";
		Verify.that(NumericUtils.isDouble(textual)).isTrue();
	}
	
	@Test
	void testIsDoubleInvalid()
	{
		String textual = "ad.f";
		Verify.that(NumericUtils.isDouble(textual)).isFalse();
	}
	
}
