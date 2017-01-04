package com.ulfric.commons.collect;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(SetUtils.class)
class SetUtilsTest extends UtilTestBase {

	@Test
	void testNewIdentityHashSetSuppliesUniqueValues()
	{
		Verify.that(() -> SetUtils.newIdentityHashSet()).suppliesUniqueValues();
	}

	@Test
	void testNewIdentityHashSetWorksAsIntended()
	{
		Set<Object> set = SetUtils.newIdentityHashSet();

		Object o1 = new EqualsButNotIdentityEquals(1);
		Object o2 = new EqualsButNotIdentityEquals(1);

		Verify.that(o1).isEqualTo(o2);
		Verify.that(o1).isNotSameAs(o2);

		set.add(o1);
		Verify.that(set).contains(o1);
		Verify.that(set).doesNotContain(o2);

		set.add(o2);
		Verify.that(set).contains(o1);
		Verify.that(set).contains(o2);
	}

	private static class EqualsButNotIdentityEquals
	{
		EqualsButNotIdentityEquals(int value)
		{
			this.value = value;
		}

		private final int value;

		@Override
		public boolean equals(Object object)
		{
			EqualsButNotIdentityEquals that = (EqualsButNotIdentityEquals) object;
			return this.value == that.value;
		}

		@Override
		public int hashCode()
		{
			return Integer.hashCode(this.value);
		}
	}

}