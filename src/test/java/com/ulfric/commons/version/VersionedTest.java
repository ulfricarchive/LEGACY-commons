package com.ulfric.commons.version;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
class VersionedTest {

	@Test
	void testGetVersionThrowsVersionMissingExceptionByDefault()
	{
		Verify.that(() -> new Versioned() {}.getVersion()).doesThrow(VersionMissingException.class);
	}

	@Test
	void testGetVersionReturnsVersionAnnotationValue()
	{
		Verify.that(new VersionedImpl().getVersion()).isEqualTo(3);
	}

	@Test
	void testGetVersionFromAnnotationThrowsVersionMissingExceptionByDefault()
	{
		Verify.that(() -> Versioned.getVersionFromAnnotation(new Versioned() {})).doesThrow(VersionMissingException.class);
	}

	@Test
	void testGetVersionFromAnnotationReturnsVersionAnnotationValue()
	{
		Verify.that(Versioned.getVersionFromAnnotation(new VersionedImpl())).isEqualTo(3);
	}

	@Version(3)
	static final class VersionedImpl implements Versioned { }

}