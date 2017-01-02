package com.ulfric.commons.reflect;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.api.Util;
import com.ulfric.commons.api.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(HandleUtils.class)
class HandleUtilsTest extends UtilTestBase {

	@Test
	void testCreateSetterFromNull()
	{
		Verify.that(() -> HandleUtils.createSetter(null)).doesThrow(NullPointerException.class);
	}

	@Test
	void testCreateSetterFromPrivateField()
	{
		Verify.that(() -> HandleUtils.createSetter(this.field1)).doesThrow(IllegalAccessException.class);
	}

	@Test
	void testCreateSetterFromPublicField()
	{
		Verify.that(() -> HandleUtils.createSetter(this.field2)).suppliesUniqueValues();
	}

	private Field field1 = this.getField("field1");
	public Field field2 = this.getField("field2");

	private Field getField(String name)
	{
		try
		{
			return this.getClass().getDeclaredField(name);
		}
		catch (NoSuchFieldException | SecurityException e)
		{
			throw new RuntimeException(e);
		}
	}

}