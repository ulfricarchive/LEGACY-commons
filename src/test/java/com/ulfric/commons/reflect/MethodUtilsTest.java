package com.ulfric.commons.reflect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.api.UtilTestBase;
import com.ulfric.verify.Verify;

@DisplayName("Method Utils")
@RunWith(JUnitPlatform.class)
final class MethodUtilsTest extends UtilTestBase<MethodUtils> {

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on Object#toString is present")
	void testGetDeclaredMethodToStringOnClassObject()
	{
		Verify.that(MethodUtils.getDeclaredMethod(Object.class, "toString")).isPresent();
	}

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on Object#abcdefg is empty")
	void testGetDeclaredMethodAbcdefgOnClassObject()
	{
		Verify.that(MethodUtils.getDeclaredMethod(Object.class, "abcdefg")).isEmpty();
	}

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on <null>#<empty> throws NullPointerException")
	void testGetDeclaredMethodOnNullClass()
	{
		Verify.that(() -> MethodUtils.getDeclaredMethod(null, "")).doesThrow(NullPointerException.class);
	}

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on Object#<null> throws NullPointerException")
	void testGetDeclaredMethodNullOnClassObject()
	{
		Verify.that(() -> MethodUtils.getDeclaredMethod(Object.class, null)).doesThrow(NullPointerException.class);
	}

	@Test
	@DisplayName("MethodUtils.getDeclaredMethod(Class, String) on <null>#<null> throws NullPointerException")
	void testGetDeclaredMethodNullOnNullClass()
	{
		Verify.that(() -> MethodUtils.getDeclaredMethod(null, null)).doesThrow(NullPointerException.class);
	}

}