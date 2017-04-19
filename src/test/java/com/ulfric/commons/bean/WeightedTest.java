package com.ulfric.commons.bean;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class WeightedTest {

	@Test
	void testCompareTo()
	{
		WeightedImpl weighted1 = new WeightedImpl(1);
		WeightedImpl weighted2 = new WeightedImpl(2);

		Verify.that(weighted1.compareTo(weighted2)).isEqualTo(-1);
		Verify.that(weighted2.compareTo(weighted1)).isEqualTo(1);
		Verify.that(weighted1.compareTo(weighted1)).isEqualTo(0);
		Verify.that(weighted2.compareTo(weighted2)).isEqualTo(0);
	}

	static final class WeightedImpl implements Weighted<WeightedImpl>
	{
		private final int weight;

		WeightedImpl(int weight)
		{
			this.weight = weight;
		}

		@Override
		public int getWeight()
		{
			return this.weight;
		}
	}

}