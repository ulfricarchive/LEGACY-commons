package com.ulfric.commons.reflect;

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
	void testGetHeirarchy_object_isEmpty()
	{
		Verify.that(ClassUtils.getHeirarchy(Object.class)).isSize(1);
	}

	@Test
	void testGetHeirarchy_extendsObject_isEmpty()
	{
		Verify.that(ClassUtils.getHeirarchy(Example1.class)).isSize(1);
	}

	class Example1
	{
		
	}

	class Example2 extends Example1
	{
		
	}

}