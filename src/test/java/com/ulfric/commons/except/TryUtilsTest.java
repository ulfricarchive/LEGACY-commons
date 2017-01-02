package com.ulfric.commons.except;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.api.Util;
import com.ulfric.commons.api.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(TryUtils.class)
@SuppressWarnings("unchecked")
public final class TryUtilsTest extends UtilTestBase {

	private static final Class<? extends Exception> SNEAKY_EXCEPTION;
	private static final int VALUE = 1;

	static
	{
		try
		{
			SNEAKY_EXCEPTION = (Class<? extends Exception>)
					Class.forName("com.ulfric.commons.except.TryUtils$SneakyException");
		}
		catch (ClassNotFoundException | ClassCastException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void testSneakyRunnablesWork()
	{
		Verify.that(() ->
		{
			TryUtils.sneaky(() -> {});
		}).runsWithoutExceptions();
	}

	@Test
	void testSneakySuppliersWork()
	{
		Verify.that(() ->
		{
			TryUtils.sneaky(() -> null);
		}).runsWithoutExceptions();
	}

	@Test
	void testSneakyRunnablesThrowExceptions()
	{
		Verify.that(() ->
		{
			TryUtils.sneaky(() ->
			{
				throw new Exception();
			});
		}).doesThrow(SNEAKY_EXCEPTION);
	}

	@Test
	void testSneakySuppliersThrowExceptions()
	{
		Verify.that(() ->
		{
			TryUtils.sneaky(() ->
			{
				throw new Exception();
			});
		}).doesThrow(SNEAKY_EXCEPTION);
	}

	void testSneakySuppliersSupplyValues()
	{
		Verify.that(() ->
		{
			return TryUtils.sneaky(TryUtilsTest::getValue);
		}).isEqualTo(VALUE);
	}

	private static int getValue() throws Exception
	{
		return VALUE;
	}

}