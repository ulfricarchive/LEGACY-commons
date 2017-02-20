package com.ulfric.commons.artifact;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.ulfric.commons.bean.Bean;

public final class Artifact extends Bean<Artifact> implements Comparable<Artifact> {

	private static final long serialVersionUID = 1L;

	public static Builder builder()
	{
		return new Builder();
	}

	public static final class Builder implements org.apache.commons.lang3.builder.Builder<Artifact>
	{
		private String group = "";
		private String name;
		private Version version;

		Builder() { }

		@Override
		public Artifact build()
		{
			Objects.requireNonNull(this.name);
			Objects.requireNonNull(this.version);

			return new Artifact(this.group, this.name, this.version);
		}

		public Builder setGroup(String group)
		{
			this.group = group.trim();
			return this;
		}

		public Builder setName(String name)
		{
			Validate.notBlank(name);
			this.name = name.trim();
			return this;
		}

		public Builder setVersion(Version version)
		{
			Objects.requireNonNull(version);
			this.version = version;
			return this;
		}
	}

	private final String group;
	private final String name;
	private final Version version;

	private Artifact(String group, String name, Version version)
	{
		this.group = group;
		this.name = name;
		this.version = version;
	}

	public String getGroup()
	{
		return this.group;
	}

	public String getName()
	{
		return this.name;
	}

	public Version getVersion()
	{
		return this.version;
	}

	@Override
	public int compareTo(Artifact that)
	{
		if (that == null)
		{
			return 1;
		}

		if (that == this)
		{
			return 0;
		}

		int comparison = this.group.compareToIgnoreCase(that.group);

		if (comparison == 0)
		{
			comparison = this.name.compareToIgnoreCase(that.name);

			if (comparison == 0)
			{
				comparison = this.version.compareTo(that.version);
			}
		}

		return comparison;
	}

}