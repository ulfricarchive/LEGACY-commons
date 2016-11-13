package com.ulfric.commons.object;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;
import com.ulfric.commons.api.UtilTestBase;

final class ObjectUtilsTest extends UtilTestBase<ObjectUtils> {

	@Test
	@DisplayName("ObjectUtils.generateString(Object) on null throws NullPointerException")
	void testGenerateStringOnNullThrowsNullPointerException()
	{
		Assertions.expectThrows(NullPointerException.class, () -> ObjectUtils.generateString(null));
	}

	@Test
	@DisplayName("ObjectUtils.generateString(Object) on new Object() equals \"Object[]\"")
	void testGenerateStringOnObject()
	{
		Truth.assertThat(ObjectUtils.generateString(new Object())).isEqualTo("Object[]");
	}

	@Test
	@DisplayName("ObjectUtils.generateString(Object) on populated object works as expected")
	void testGenerateStringWorksAsExpected()
	{
		Truth.assertThat(new A().toString()).isEqualTo("A[b=B[ten=10, hello=Hello!, nothing=<null>], c=C[dis=<recursive>]]");
	}

	private class Gen
	{
		Gen() { }

		@Override
		public final String toString()
		{
			return ObjectUtils.generateString(this);
		}
	}

	@SuppressWarnings("unused")
	private final class A extends Gen
	{
		A() { }

		private final B b = new B();
		private final C c = new C();
	}

	@SuppressWarnings("unused")
	private final class B extends Gen
	{
		B() { }

		private final int ten = 10;
		private final String hello = "Hello!";
		private final Void nothing = null;
	}

	@SuppressWarnings("unused")
	private final class C extends Gen
	{
		C() { }

		private final transient int zero = 0;
		private final transient int one = 1;
		private final transient int two = 2;
		private final C dis = this;
	}

}