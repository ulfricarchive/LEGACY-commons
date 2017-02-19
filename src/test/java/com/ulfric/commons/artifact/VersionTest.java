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
		this.version = new Version(1, 2, 3);
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

}
