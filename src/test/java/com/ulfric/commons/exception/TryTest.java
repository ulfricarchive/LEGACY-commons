package com.ulfric.commons.exception;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(Try.class)
class TryTest extends UtilTestBase {

	@Test
	void testTo_TrySupplier_RunsSupplier()
	{
		TrySupplier<Object> supplier = Object::new;
		Verify.that(() -> Try.to(supplier)).valueIsNotNull();
	}

	@Test
	void testTo_TrySupplierThrowsNPE_DoesThrowNPE()
	{
		TrySupplier<Object> supplier = () -> { throw new NullPointerException(); };
		Verify.that(() -> Try.to(supplier)).doesThrow(NullPointerException.class);
	}

}