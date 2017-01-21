package com.ulfric.commons.exception;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(Try.class)
class TryTest extends UtilTestBase {

	@Test
	void testTo_TrySupplier_RunsSupplier()
	{
		TrySupplier<Object> supplier = Object::new;
		Verify.that(() -> Try.to(supplier)).valueIsNotNull();
	}

	@Test
	void testTo_TrySupplierThrowsNPE_DoesThrowNPE()
	{
		TrySupplier<Object> supplier = () -> { throw new NullPointerException(); };
		Verify.that(() -> Try.to(supplier)).doesThrow(NullPointerException.class);
	}

	@Test
	void test_tryRunnable_runsRunnable()
	{
		final boolean[] run = {false};

		TryRunnable runnable = () -> { run[0] = true; };

		Try.to(runnable);

		Verify.that(run[0]).isTrue();
	}

	@Test
	void test_tryRunnable_rethrowsExceptions()
	{
		TryRunnable tryRunnable = () -> { throw new Exception(); };
		Verify.that(() -> Try.to(tryRunnable)).doesThrow(RuntimeException.class);
	}

	@Test
	void test_tryBlindRunnable_runs()
	{
		final boolean[] run = {false};

		TryRunnable runnable = () -> { run[0] = true; };

		Try.blind(runnable);

		Verify.that(run[0]).isTrue();
	}

	@Test
	void test_tryBlindRunnable_ignoresExceptions()
	{
		Verify.that(() -> Try.blind(() ->
		{
			if (true)
			{
				throw new Exception();
			}
			return;
		})).runsWithoutExceptions();
	}

	@Test
	void test_tryBlindSupplier_returnsValues()
	{
		Verify.that(() -> Try.blind(this::getValue)).valueIsEqualTo(5);
	}

	private int getValue() throws Exception
	{
		return 5;
	}

	@Test
	void test_tryBlindSupplier_ignoresExceptions()
	{
		Verify.that(() ->
		{
			Try.blind(() ->
			{
				if (true)
				{
					throw new Exception();
				}

				return 5;
			});
		}).runsWithoutExceptions();
	}

	@Test
	void test_tryBlindSupplier_returnsNull()
	{
		Verify.that(() ->
		{
			return Try.blind(this::throwException);
		}).valueIsNull();
	}

	private int throwException() throws Exception
	{
		throw new Exception();
	}

}