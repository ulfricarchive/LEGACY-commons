package com.ulfric.commons.collect;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.api.Util;
import com.ulfric.commons.api.UtilTestBase;
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