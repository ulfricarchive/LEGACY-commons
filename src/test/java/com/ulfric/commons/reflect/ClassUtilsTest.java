package com.ulfric.commons.reflect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;
import com.google.common.truth.Truth8;
import com.ulfric.commons.api.UtilTestBase;

class ClassUtilsTest extends UtilTestBase<ClassUtils> {

	@Test
	@DisplayName("getClass(\"java.lang.Object\"")
	void testGetClassObject()
	{
		Truth8.assertThat(ClassUtils.getClass("java.lang.Object")).isPresent();
	}

	@Test
	@DisplayName("getClass(\"RandomString123\"")
	void testGetClassRandomString()
	{
		Truth8.assertThat(ClassUtils.getClass("RandomString123")).isEmpty();
	}

	@Test
	@DisplayName("getClass(\"LoadClass\"")
	void testGetClassLoadClass()
	{
		Truth8.assertThat(ClassUtils.getClass("com.ulfric.commons.reflect.LoadClass")).isPresent();
	}

	@Test
	@DisplayName("isImplementedClass(null)")
	void testIsImplementedNull()
	{
		Truth.assertThat(ClassUtils.isDataClass(null)).isFalse();
	}

	@Test
	@DisplayName("isImplementedClass(Object.class)")
	void testIsImplementedObject()
	{
		Truth.assertThat(ClassUtils.isDataClass(Object.class)).isFalse();
	}

	@Test
	@DisplayName("isImplementedClass(Iface.class)")
	void testIsImplementedIface()
	{
		Truth.assertThat(ClassUtils.isDataClass(Iface.class)).isFalse();
	}

	@Test
	@DisplayName("isImplementedClass(Aclass.class)")
	void testIsImplementedAclass()
	{
		Truth.assertThat(ClassUtils.isDataClass(Aclass.class)).isTrue();
	}

	@Test
	@DisplayName("isImplementedClass(getClass())")
	void testIsImplementedThis()
	{
		Truth.assertThat(ClassUtils.isDataClass(this.getClass())).isTrue();
	}

	interface Iface { }

	abstract class Aclass { }

}