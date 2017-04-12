package com.ulfric.commons.identity;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class IdentityTest {

	@Test
	void testOf_null_throwsNullPointerException()
	{
		Verify.that(() -> Identity.of(null)).doesThrow(NullPointerException.class);
	}

	@Test
	void testOf_identity_returnsSelf()
	{
		Identity identity = Identity.of(new Object());
		Verify.that(() -> Identity.of(identity)).suppliesNonUniqueValues();
	}

	@Test
	void testEquals_null()
	{
		Identity identity = Identity.of(new Object());
		Verify.that(identity.equals(null)).isFalse();
	}

	@Test
	void testEquals_self()
	{
		Identity identity = Identity.of(new Object());
		Verify.that(identity.equals(identity)).isTrue();
	}

	@Test
	void testEquals_equivalent()
	{
		Object dummy = new Object();
		Identity identity1 = Identity.of(dummy);
		Identity identity2 = Identity.of(dummy);
		Verify.that(identity1).isEqualTo(identity2);
	}

	@Test
	void testHashCode()
	{
		Object dummy = new Object();
		Identity identity = Identity.of(dummy);
		Verify.that(identity.hashCode()).isEqualTo(dummy.hashCode());
	}

	@Test
	void testToString()
	{
		Object dummy = new Object();
		Identity identity = Identity.of(dummy);
		Verify.that(identity.toString()).isEqualTo(dummy.toString());
	}

}