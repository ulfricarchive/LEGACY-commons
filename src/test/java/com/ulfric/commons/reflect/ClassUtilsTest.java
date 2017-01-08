package com.ulfric.commons.reflect;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(ClassUtils.class)
class ClassUtilsTest extends UtilTestBase {

	@Test
	void testGetLocationURI_null_throwsNPE()
	{
		Verify.that(() -> ClassUtils.getLocationURI(null)).doesThrow(NullPointerException.class);
	}

	@Test
	void testGetLocationURI_this_isCorrect() throws URISyntaxException
	{
		Verify.that(ClassUtils.getLocationURI(this.getClass()))
			.isEqualTo(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
	}

}