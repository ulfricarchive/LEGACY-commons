package com.ulfric.commons.api;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;

import com.ulfric.verify.Verify;

public class UtilTestBase {

	protected UtilTestBase()
	{
		this.util = this.getClass().getAnnotation(Util.class).value();
		try
		{
			this.constructor = this.util.getDeclaredConstructor();
			this.constructor.setAccessible(true);
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			throw new RuntimeException(e);
		}
	}

	private final Class<?> util;
	private final Constructor<?> constructor;

	@Test
	void testHasOneConstructor()
	{
		Verify.that(this.util.getDeclaredConstructors().length).isEqualTo(1);
	}

	@Test
	void testConstructorIsPrivate()
	{
		Verify.that(this.constructor).isPrivate();
	}

	@Test
	void testConstructorThrowsUtilInstantiationException()
	{
		Verify.that(() -> this.constructor.newInstance()).doesThrow(UtilInstantiationException.class);
	}

}