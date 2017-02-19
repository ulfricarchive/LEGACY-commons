package com.ulfric.commons.artifact;

public final class Artifact {

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
		return group;
	}

	public String getArtifact()
	{
		return artifact;
	}

	public Version getVersion()
	{
		return version;
	}

}
