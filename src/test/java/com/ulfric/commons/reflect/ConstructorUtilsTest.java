package com.ulfric.commons.reflect;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.api.Util;
import com.ulfric.commons.api.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(ConstructorUtils.class)
public class ConstructorUtilsTest extends UtilTestBase {

	private static final Constructor<NoArgConstructor> NO_ARG_CONSTRUCTOR;
	private static final Constructor<OneArgConstructor> ONE_ARG_CONSTRUCTOR;

	static
	{
		try
		{
			NO_ARG_CONSTRUCTOR = NoArgConstructor.class.getDeclaredConstructor();
			NO_ARG_CONSTRUCTOR.setAccessible(true);
			ONE_ARG_CONSTRUCTOR = OneArgConstructor.class.getDeclaredConstructor(Object.class);
			ONE_ARG_CONSTRUCTOR.setAccessible(true);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void testFindsNoArgConstructor()
	{
		Verify.that(ConstructorUtils.getDeclaredConstructor(NoArgConstructor.class)).isEqualTo(NO_ARG_CONSTRUCTOR);
	}

	@Test
	void testFindsOneArgConstructor()
	{
		Verify.that(ConstructorUtils.getDeclaredConstructor(OneArgConstructor.class, Object.class)).isEqualTo(ONE_ARG_CONSTRUCTOR);
	}

	@Test
	void testConstructNoArgThrowsException()
	{
		Verify.that(() ->
		{
			ConstructorUtils.getDeclaredConstructor(NoArgConstructor.class, Object.class);
		}).doesThrow(RuntimeException.class);
	}

	@Test
	void testConstructOneArgThrowsException()
	{
		Verify.that(() ->
		{
			ConstructorUtils.getDeclaredConstructor(OneArgConstructor.class);
		}).doesThrow(RuntimeException.class);
	}

	@Test
	void testCreateNoArgWorks()
	{
		Verify.that(
				ConstructorUtils.create(NO_ARG_CONSTRUCTOR)
		).isExactType(NoArgConstructor.class);
	}

	@Test
	void testCreateOneArgWorks()
	{
		Verify.that(
				ConstructorUtils.create(ONE_ARG_CONSTRUCTOR, new Object())
		).isExactType(OneArgConstructor.class);
	}

	@Test
	void testCreateNoArgFails()
	{
		Verify.that(() ->
		{
			ConstructorUtils.create(NO_ARG_CONSTRUCTOR, new Object());
		}).doesThrow(RuntimeException.class);
	}

	@Test
	void testCreateOneArgFails()
	{
		Verify.that(() ->
		{
			ConstructorUtils.create(ONE_ARG_CONSTRUCTOR);
		}).doesThrow(RuntimeException.class);
	}

	private static final class NoArgConstructor {

		private NoArgConstructor()
		{

		}

	}

	private static final class OneArgConstructor {

		private OneArgConstructor(Object object)
		{

		}

	}

}
