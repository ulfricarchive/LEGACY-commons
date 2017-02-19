package com.ulfric.commons.artifact;

import com.ulfric.commons.bean.Bean;

public final class Version extends Bean {

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
		return this.major;
	}

	public int getMinor()
	{
		return this.minor;
	}

	public int getSecurity()
	{
		return this.security;
	}

	public String getFull()
	{
		return this.full;
	}

}
