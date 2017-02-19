package com.ulfric.commons.artifact;

public final class Version {

	private final int major;
	private final int minor;
	private final int security;
	private final String full;

	public Version(int major, int minor, int security)
	{
		this.major = major;
		this.minor = minor;
		this.security = security;
		this.full = major + "." + minor + "." + security;
	}

	public int getMajor()
	{
		return major;
	}

	public int getMinor()
	{
		return minor;
	}

	public int getSecurity()
	{
		return security;
	}

	public String getFull()
	{
		return full;
	}

}
