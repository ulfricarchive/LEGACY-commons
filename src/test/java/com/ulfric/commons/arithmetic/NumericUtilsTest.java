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
	public void testIsNumericValid()
	{
		String textual = "300";
		Verify.that(NumericUtils.isNumeric(textual)).isTrue();
	}
	
	@Test
	public void testIsNumericInvalid()
	{
		String textual = "aaa";
		Verify.that(NumericUtils.isNumeric(textual)).isFalse();
	}
	
	@Test
	public void testIsNumericDecimalValid()
	{
		String textual = "34.3";
		Verify.that(NumericUtils.isNumeric(textual)).isTrue();
	}
	
	@Test
	public void testIsNumericDecimalInvalid()
	{
		String textual = "a.dre";
		Verify.that(NumericUtils.isNumeric(textual)).isFalse();
	}
	
}
