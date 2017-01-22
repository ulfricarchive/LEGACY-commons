package com.ulfric.commons.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class ForTest {

	@Test
	public void testEach_null_throwsNPE()
	{
		Verify.that(() -> For.each((Collection<Object>) null)).doesThrow(NullPointerException.class);
		Verify.that(() -> For.each((Stream<Object>) null)).doesThrow(NullPointerException.class);
		Verify.that(() -> For.each((Object[]) null)).doesThrow(NullPointerException.class);
	}

	@Test
	public void testEach_static_equal()
	{
		new StaticTest().test();
	}

	@Test
	public void testEach_array_equal()
	{
		new ArrayTest().test();
	}

	@Test
	public void testEach_collection_equal()
	{
		new CollectionTest().test();
	}

	@Test
	public void testEach_baseStream_equal()
	{
		new BaseStreamTest().test();
	}

	@Test
	public void testEach_stream_equal()
	{
		new StreamTest().test();
	}

	static class StaticTest extends ForEachTest
	{
		@Override
		protected void test()
		{
			For.each(new Integer[] { 0, 1, 2, 3, 4 })
					.collect(x -> x)
					.each(this.forEachValues::add);

			for (int i = 0; i < 5; i++)
			{
				this.forLoopValues.add(i);
			}

			Verify.that(this.compare()).isTrue();
		}
	}

	static class ArrayTest extends ForEachTest
	{
		private Integer[] getArray()
		{
			return new Integer[] { 0, 1, 2, 3, 4 };
		}

		@Override
		protected void test()
		{
			For.each(this.getArray())
					.arrayCollect(i -> this.getArray())
					.each(this.forEachValues::add);

			for (int i = 0; i < 5; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					this.forLoopValues.add(j);
				}
			}

			Verify.that(this.compare()).isTrue();
		}
	}

	static class CollectionTest extends ForEachTest
	{
		private Collection<Integer> getCollection()
		{
			return Arrays.asList(0, 1, 2, 3, 4);
		}

		@Override
		protected void test()
		{
			For.each(this.getCollection())
					.multiCollect(i -> this.getCollection())
					.each(this.forEachValues::add);

			for (int i = 0; i < 5; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					this.forLoopValues.add(j);
				}
			}

			Verify.that(this.compare()).isTrue();
		}
	}

	static class BaseStreamTest extends ForEachTest
	{
		@Override
		protected void test()
		{
			For.each(IntStream.range(0, 5))
					.baseStreamCollect(i -> IntStream.range(0, 5))
					.each(this.forEachValues::add);

			for (int i = 0; i < 5; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					this.forLoopValues.add(j);
				}
			}

			Verify.that(this.compare()).isTrue();
		}
	}

	static class StreamTest extends ForEachTest
	{
		private Stream<Integer> getStream()
		{
			return StreamUtils.cut(IntStream.range(0, 5));
		}

		@Override
		protected void test()
		{
			For.each(this.getStream())
					.streamCollect(i -> this.getStream())
					.each(this.forEachValues::add);

			for (int i = 0; i < 5; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					this.forLoopValues.add(j);
				}
			}

			Verify.that(this.compare()).isTrue();
		}
	}

	static abstract class ForEachTest
	{
		protected final List<Integer> forEachValues = new ArrayList<>();
		protected final List<Integer> forLoopValues = new ArrayList<>();

		protected boolean compare()
		{
			return Arrays.equals(this.forEachValues.toArray(), this.forLoopValues.toArray());
		}

		protected abstract void test();
	}

}
