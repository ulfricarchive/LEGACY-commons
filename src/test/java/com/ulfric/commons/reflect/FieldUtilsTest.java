package com.ulfric.commons.reflect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.google.common.truth.Truth8;
import com.ulfric.commons.api.UtilTestBase;

@DisplayName("Field Utils")
@RunWith(JUnitPlatform.class)
class FieldUtilsTest extends UtilTestBase<FieldUtils> {

	final Object testField = new Object();

	@Test
	void testGetDeclaredFieldNullClass()
	{
		Assertions.assertThrows(NullPointerException.class, () -> FieldUtils.getDeclaredField(null, ""));
	}

	@Test
	void testGetDeclaredFieldNullName()
	{
		Assertions.assertThrows(NullPointerException.class, () -> FieldUtils.getDeclaredField(Object.class, null));
	}

	@Test
	void testGetDeclaredFieldBlankClassAndName()
	{
		Truth8.assertThat(FieldUtils.getDeclaredField(Object.class, "")).isEmpty();
	}

	@Test
	void testGetDeclaredField()
	{
		Truth8.assertThat(FieldUtils.getDeclaredField(this.getClass(), "testField")).isPresent();
	}

}