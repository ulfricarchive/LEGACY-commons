package com.ulfric.commons.collect;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(MapUtils.class)
class MapUtilsTest extends UtilTestBase {

	@Test
	void testNewSynchronizedIdentityHashMapSuppliesUniqueValues()
	{
		Verify.that(() -> MapUtils.newSynchronizedIdentityHashMap()).suppliesUniqueValues();
	}

}