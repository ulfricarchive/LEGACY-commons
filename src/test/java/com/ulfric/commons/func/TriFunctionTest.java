package com.ulfric.commons.func;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class TriFunctionTest {

	@Test
	public void testApply()
	{
		TriFunction<Integer, Integer, Integer, Integer> function = (t, u, x) ->
				t + u + x;

		Verify.that(function.apply(1, 2, 3)).isEqualTo(1 + 2 + 3);

		TriFunction<Integer, Integer, Integer, Integer> function2 =
				function.andThen(x -> x * 2);

		Verify.that(function2.apply(1, 2, 3)).isEqualTo((1 + 2 + 3) * 2);
	}

}
