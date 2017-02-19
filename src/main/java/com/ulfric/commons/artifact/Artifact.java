package com.ulfric.commons.artifact;

import com.ulfric.commons.bean.Bean;

public final class Artifact extends Bean {

	private String group;
	private String artifact;
	private Version version;

	public Artifact(String group, String artifact, Version version)
	{
		this.group = group;
		this.artifact = artifact;
		this.version = version;
	}

	public String getGroup()
	{
		return this.group;
	}

	public String getArtifact()
	{
		return this.artifact;
	}

	public Version getVersion()
	{
		return this.version;
	}

}
