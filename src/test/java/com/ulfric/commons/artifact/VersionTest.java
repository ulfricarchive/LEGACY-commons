package com.ulfric.commons.artifact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class VersionTest {

	private Version version;

	@BeforeEach
	void init()
	{
		this.version = Version.builder().setMajor(1).setMinor(2).setSecurity(3).build();
	}

	@Test
	void testGetters()
	{
		Verify.that(this.version.getMajor()).isEqualTo(1);
		Verify.that(this.version.getMinor()).isEqualTo(2);
		Verify.that(this.version.getSecurity()).isEqualTo(3);
	}

	@Test
	void testFullVersion()
	{
		Verify.that(this.version.getFull()).isEqualTo("1.2.3");
	}

	@Test
	void testBuilderNegativeMajor()
	{
		Verify.that(() -> Version.builder().setSecurity(-1)).doesThrow(IllegalArgumentException.class);
	}

	@Test
	void testBuilderNegativeMinor()
	{
		Verify.that(() -> Version.builder().setMinor(-1)).doesThrow(IllegalArgumentException.class);
	}

	@Test
	void testBuilderNegativeSecurity()
	{
		Verify.that(() -> Version.builder().setSecurity(-1)).doesThrow(IllegalArgumentException.class);
	}

	@Test
	void testCompareToZero()
	{
		Verify.that(this.version.compareTo(Version.ZERO)).isEqualTo(-1);
	}

	@Test
	void testZeroCompareTo()
	{
		Verify.that(Version.ZERO.compareTo(this.version)).isEqualTo(1);
	}

	@Test
	void testZeroCompareToSame()
	{
		Verify.that(Version.ZERO.compareTo(Version.ZERO)).isZero();
	}

	@Test
	void testZeroCompareToEqual()
	{
		Verify.that(Version.ZERO.compareTo(Version.builder().build())).isZero();
	}

	@Test
	void testCompareToMinor()
	{
		Verify.that(Version.builder()
				.setMinor(1)
				.build().compareTo(Version.builder()
						.setMinor(2)
						.build())).isEqualTo(1);
	}

	@Test
	void testCompareToNull()
	{
		Verify.that(Version.ZERO.compareTo(null)).isEqualTo(1);
	}

}