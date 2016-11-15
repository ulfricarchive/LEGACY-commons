package com.ulfric.commons.booleans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;
import com.ulfric.commons.api.UtilTestBase;

@DisplayName("BooleanUtils")
final class BooleanUtilsTest extends UtilTestBase<BooleanUtils> {

	@Test
	@DisplayName("Parse Boolean \"true\" is true")
	void testParseBooleanStringtrueToTrue()
	{
		Truth.assertThat(BooleanUtils.parseBoolean("true")).isTrue();
	}

	@Test
	@DisplayName("Parse Boolean \"TRUE\" is true")
	void testParseBooleanStringTRUEToTrue()
	{
		Truth.assertThat(BooleanUtils.parseBoolean("TRUE")).isTrue();
	}

	@Test
	@DisplayName("Parse Boolean \"True\" is true")
	void testParseBooleanStringTrueToTrue()
	{
		Truth.assertThat(BooleanUtils.parseBoolean("True")).isTrue();
	}

	@Test
	@DisplayName("Parse Boolean \"false\" is false")
	void testParseBooleanStringfalseToFalse()
	{
		Truth.assertThat(BooleanUtils.parseBoolean("false")).isFalse();
	}

	@Test
	@DisplayName("Parse Boolean \"FALSE\" is false")
	void testParseBooleanStringFALSEToFalse()
	{
		Truth.assertThat(BooleanUtils.parseBoolean("FALSE")).isFalse();
	}

	@Test
	@DisplayName("Parse Boolean \"False\" is false")
	public void testParseBooleanStringFalseToFalse()
	{
		Truth.assertThat(BooleanUtils.parseBoolean("False")).isFalse();
	}

	@Test
	@DisplayName("Parse Boolean null throws BooleanFormatException")
	public void testParseBooleanNullThrowsBooleanFormatException()
	{
		Assertions.expectThrows(BooleanFormatException.class, () -> BooleanUtils.parseBoolean(null));
	}

	@Test
	@DisplayName("Parse Boolean \"null\" throws BooleanFormatException")
	public void testParseBooleanStringNullThrowsBooleanFormatException()
	{
		Assertions.expectThrows(BooleanFormatException.class, () -> BooleanUtils.parseBoolean("null"));
	}

	@Test
	@DisplayName("Parse Boolean \"\" (empty String) throws BooleanFormatException")
	public void testParseBooleanEmptyStringThrowsBooleanFormatException()
	{
		Assertions.expectThrows(BooleanFormatException.class, () -> BooleanUtils.parseBoolean(""));
	}

	@Test
	@DisplayName("Parse Boolean \"Yes\" throws BooleanFormatException")
	public void testParseBooleanStringYesThrowsBooleanFormatException()
	{
		Assertions.expectThrows(BooleanFormatException.class, () -> BooleanUtils.parseBoolean("Yes"));
	}

	@Test
	@DisplayName("Parse Boolean \"No\" throws BooleanFormatException")
	public void testParseBooleanStringNoThrowsBooleanFormatException()
	{
		Assertions.expectThrows(BooleanFormatException.class, () -> BooleanUtils.parseBoolean("No"));
	}

}