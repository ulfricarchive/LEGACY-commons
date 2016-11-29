package com.ulfric.commons.reflect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.google.common.truth.Truth8;
import com.ulfric.commons.api.UtilTestBase;

@DisplayName("Method Utils")
@RunWith(JUnitPlatform.class)
final class MethodUtilsTest extends UtilTestBase<MethodUtils> {

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on Object#toString is present")
	void testGetDeclaredMethodToStringOnClassObject()
	{
		Truth8.assertThat(MethodUtils.getDeclaredMethod(Object.class, "toString")).isPresent();
	}

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on Object#abcdefg is empty")
	void testGetDeclaredMethodAbcdefgOnClassObject()
	{
		Truth8.assertThat(MethodUtils.getDeclaredMethod(Object.class, "abcdefg")).isEmpty();
	}

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on <null>#<empty> throws NullPointerException")
	void testGetDeclaredMethodOnNullClass()
	{
		Assertions.expectThrows(NullPointerException.class, () -> MethodUtils.getDeclaredMethod(null, ""));
	}

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on Object#<null> throws NullPointerException")
	void testGetDeclaredMethodNullOnClassObject()
	{
		Assertions.expectThrows(NullPointerException.class, () -> MethodUtils.getDeclaredMethod(Object.class, null));
	}

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on <null>#<null> throws NullPointerException")
	void testGetDeclaredMethodNullOnNullClass()
	{
		Assertions.expectThrows(NullPointerException.class, () -> MethodUtils.getDeclaredMethod(null, null));
	}

}