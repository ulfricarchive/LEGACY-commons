package com.ulfric.commons.reflect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.api.UtilTestBase;
import com.ulfric.verify.Verify;

@DisplayName("Field Utils")
@RunWith(JUnitPlatform.class)
class FieldUtilsTest extends UtilTestBase<FieldUtils> {

	final Object testField = new Object();

	@Test
	void testGetDeclaredFieldNullClass()
	{
		Verify.that(() -> FieldUtils.getDeclaredField(null, "")).doesThrow(NullPointerException.class);
	}

	@Test
	void testGetDeclaredFieldNullName()
	{
		Verify.that(() -> FieldUtils.getDeclaredField(Object.class, null)).doesThrow(NullPointerException.class);
	}

	@Test
	void testGetDeclaredFieldBlankClassAndName()
	{
		Verify.that(FieldUtils.getDeclaredField(Object.class, "")).isEmpty();
	}

	@Test
	void testGetDeclaredField()
	{
		Verify.that(FieldUtils.getDeclaredField(this.getClass(), "testField")).isPresent();
	}

}