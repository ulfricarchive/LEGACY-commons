package com.ulfric.commons.exception;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(Try.class)
class TryTest extends UtilTestBase {

	@Test
	void testTo_checkedSupplier_RunsSupplier()
	{
		CheckedSupplier<Object> supplier = Object::new;
		Verify.that(() -> Try.to(supplier)).valueIsNotNull();
	}

	@Test
	void testTo_checkedSupplierThrowsNPE_DoesThrowNPE()
	{
		CheckedSupplier<Object> supplier = () -> { throw new NullPointerException(); };
		Verify.that(() -> Try.to(supplier)).doesThrow(NullPointerException.class);
	}

	@Test
	void test_checkedRunnable_runsRunnable()
	{
		final boolean[] run = {false};

		CheckedRunnable runnable = () -> { run[0] = true; };

		Try.to(runnable);

		Verify.that(run[0]).isTrue();
	}

	@Test
	void test_checkedRunnable_rethrowsExceptions()
	{
		CheckedRunnable CheckedRunnable = () -> { throw new Exception(); };
		Verify.that(() -> Try.to(CheckedRunnable)).doesThrow(RuntimeException.class);
	}

	@Test
	void test_tryFuture_runsFuture()
	{
		final boolean[] run = {false};

		Future<String> future = new SimpleFuture<String>() {
			@Override
			public String get()
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
			public String get()
			{
				throw new RuntimeException();
			}
		};
		Verify.that(() -> Try.to(thrw)).doesThrow(RuntimeException.class);
	}

	@Test
	void test_tryFunction_runsFunction()
	{
		CheckedFunction<Boolean, Boolean> function = x -> x;

		Verify.that(Try.to(function, () -> true)).isTrue();
	}

	@Test
	void test_tryFunction_rethrowsExceptions()
	{
		CheckedFunction<Boolean, Boolean> function = x ->
		{
			throw new RuntimeException();
		};

		Verify.that(() -> Try.to(function, () -> true)).doesThrow(RuntimeException.class);
	}

	@Test
	void test_tryConsumer_runsConsumer() throws Throwable
	{
		CheckedConsumer<?> consumer = Mockito.mock(CheckedConsumer.class);
		Try.to(consumer, null);
		Mockito.verify(consumer, Mockito.atLeastOnce()).accept(Mockito.any());
	}

	@Test
	void test_tryConsumer_rethrowsExceptions()
	{
		CheckedConsumer<?> consumer = ignore ->
		{
			throw new RuntimeException();
		};

		Verify.that(() -> Try.to(consumer, null)).doesThrow(RuntimeException.class);
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
		public T get(long timeout, TimeUnit unit)
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