package com.ulfric.commons.artifact;

import com.ulfric.commons.bean.Bean;

public final class Version extends Bean implements Comparable<Version> {

	public static final Version ZERO = Version.builder().build();

	public static Builder builder()
	{
		return new Builder();
	}

	public static final class Builder implements org.apache.commons.lang3.builder.Builder<Version>
	{
		private int major;
		private int minor;
		private int security;

		Builder() { }

		@Override
		public Version build()
		{
			return new Version(this.major, this.minor, this.security);
		}

		public Builder setMajor(int major)
		{
			this.validateVersionPart(major);
			this.major = major;
			return this;
		}

		public Builder setMinor(int minor)
		{
			this.validateVersionPart(minor);
			this.minor = minor;
			return this;
		}

		public Builder setSecurity(int security)
		{
			this.validateVersionPart(security);
			this.security = security;
			return this;
		}

		private void validateVersionPart(int part)
		{
			if (part < 0)
			{
				throw new IllegalArgumentException("Version parts must not be negative!");
			}
		}
	}

	private final int major;
	private final int minor;
	private final int security;
	private final String full;

	private Version(int major, int minor, int security)
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

	@Override
	public int compareTo(Version that)
	{
		if (that == null)
		{
			return 1;
		}

		if (that == this)
		{
			return 0;
		}

		int comparison = Integer.compare(that.major, this.major);

		if (comparison == 0)
		{
			comparison = Integer.compare(that.minor, this.minor);

			if (comparison == 0)
			{
				comparison = Integer.compare(that.security, this.security);
			}
		}

		return comparison;
	}

}