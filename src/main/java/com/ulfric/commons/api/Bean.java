package com.ulfric.commons.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class Bean {

	@Override
	public final String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public final int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public final boolean equals(Object object)
	{
		return EqualsBuilder.reflectionEquals(this, object);
	}

}