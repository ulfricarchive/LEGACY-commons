package com.ulfric.commons.artifact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class ArtifactTest {

	private Artifact artifact;

	@BeforeEach
	void init()
	{
		this.artifact = Artifact.builder()
				.setGroup("com.example")
				.setName("Example")
				.setVersion(Version.builder()
						.setMajor(1)
						.build())
				.build();
	}

	@Test
	void testGetters()
	{
		Verify.that(this.artifact.getGroup()).isEqualTo("com.example");
		Verify.that(this.artifact.getName()).isEqualTo("Example");
		Verify.that(this.artifact.getVersion().getMajor()).isEqualTo(1);
	}

	@Test
	void testBuilderEmpty()
	{
		Verify.that(() -> Artifact.builder().build()).doesThrow(NullPointerException.class);
	}

	@Test
	void testBuilderNoNameButGroup()
	{
		Verify.that(() -> Artifact.builder().setGroup("com.example").build()).doesThrow(NullPointerException.class);
	}

	@Test
	void testBuilderNoNameButVersion()
	{
		Verify.that(() -> Artifact.builder().setVersion(Version.builder().setMajor(1).build()).build()).doesThrow(NullPointerException.class);
	}

	@Test
	void testCompareToNull()
	{
		Verify.that(this.artifact.compareTo(null)).isEqualTo(1);
	}

	@Test
	void testCompareToSame()
	{
		Verify.that(this.artifact.compareTo(this.artifact)).isZero();
	}

	@Test
	void testCompareToIsReflective()
	{
		Artifact abc = Artifact.builder().setName("abc").setGroup("alphabet").setVersion(Version.ZERO).build();
		Verify.that(this.artifact.compareTo(abc)).isEqualTo(2);
		Verify.that(abc.compareTo(this.artifact)).isEqualTo(-2);
	}

	@Test
	void testCompareToEqualGroupDifferentName()
	{
		Artifact aaa = Artifact.builder().setGroup("alphabet").setName("aaa").setVersion(Version.ZERO).build();
		Artifact bbb = Artifact.builder().setGroup("alphabet").setName("bbb").setVersion(Version.ZERO).build();
		Verify.that(aaa.compareTo(bbb)).isEqualTo(-1);
		Verify.that(bbb.compareTo(aaa)).isEqualTo(1);
	}

	@Test
	void testCompareToEqualGroupAndNameDifferentVersion()
	{
		Artifact low = Artifact.builder().setGroup("alphabet").setName("aaa").setVersion(Version.ZERO).build();
		Artifact high = Artifact.builder().setGroup("alphabet").setName("aaa").setVersion(Version.builder().setMajor(1).build()).build();
		Verify.that(low.compareTo(high)).isEqualTo(1);
		Verify.that(high.compareTo(low)).isEqualTo(-1);
	}

}