package com.ulfric.commons.exception;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
	void test_tryFuture_runsFuture()
	{
		final boolean[] run = {false};

		Future<String> future = new SimpleFuture<String>() {
			@Override
			public String get() throws InterruptedException, ExecutionException
			{
				run[0] = true;
				return "hello";
			}
		};

		Try.to(future);

		Verify.that(run[0]).isTrue();
	}

	@Test
	void test_tryFuture_rethrowsExceptions()
	{
		Future<String> thrw = new SimpleFuture<String>() {
			@Override
			public String get() throws InterruptedException, ExecutionException
			{
				throw new RuntimeException();
			}
		};
		Verify.that(() -> Try.to(thrw)).doesThrow(RuntimeException.class);
	}

	private abstract class SimpleFuture<T> implements Future<T>
	{
		public SimpleFuture()
		{
			
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning)
		{
			throw new IllegalStateException();
		}

		@Override
		public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
		{
			throw new IllegalStateException();
		}

		@Override
		public boolean isCancelled()
		{
			throw new IllegalStateException();
		}

		@Override
		public boolean isDone()
		{
			throw new IllegalStateException();
		}
	}

}