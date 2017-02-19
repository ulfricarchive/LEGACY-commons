package com.ulfric.commons.artifact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class ArtifactTest {

	private Version version;
	private Artifact artifact;

	@BeforeEach
	void init()
	{
		this.version = new Version(1, 2, 3);
		this.artifact = new Artifact("group", "artifact", this.version);
	}

	@Test
	void testGetters()
	{
		Verify.that(this.artifact.getGroup()).isEqualTo("group");
		Verify.that(this.artifact.getArtifact()).isEqualTo("artifact");
		Verify.that(this.artifact.getVersion()).isEqualTo(this.version);
	}

}
