package com.ulfric.commons.reflect;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.api.UtilTestBase;
import com.ulfric.verify.Verify;

@DisplayName("Class Utils")
@RunWith(JUnitPlatform.class)
class ClassUtilsTest extends UtilTestBase<ClassUtils> {

	@Test
	@DisplayName("getClass(\"java.lang.Object\"")
	void testGetClassObject()
	{
		Verify.that(ClassUtils.getClass("java.lang.Object")).isPresent();
	}

	@Test
	@DisplayName("getClass(\"RandomString123\"")
	void testGetClassRandomString()
	{
		Verify.that(ClassUtils.getClass(UUID.randomUUID().toString())).isEmpty();
	}

	@Test
	@DisplayName("isImplementedClass(null)")
	void testIsImplementedNull()
	{
		Verify.that(ClassUtils.isDataClass(null)).isFalse();
	}

	@Test
	@DisplayName("isImplementedClass(Object.class)")
	void testIsImplementedObject()
	{
		Verify.that(ClassUtils.isDataClass(Object.class)).isFalse();
	}

	@Test
	@DisplayName("isImplementedClass(Iface.class)")
	void testIsImplementedIface()
	{
		Verify.that(ClassUtils.isDataClass(Iface.class)).isFalse();
	}

	@Test
	@DisplayName("isImplementedClass(Aclass.class)")
	void testIsImplementedAclass()
	{
		Verify.that(ClassUtils.isDataClass(Aclass.class)).isTrue();
	}

	@Test
	@DisplayName("isImplementedClass(getClass())")
	void testIsImplementedThis()
	{
		Verify.that(ClassUtils.isDataClass(this.getClass())).isTrue();
	}

	interface Iface { }

	abstract class Aclass { }

}