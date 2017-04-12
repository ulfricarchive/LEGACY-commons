package com.ulfric.commons.identity;

import java.util.Objects;

public final class Identity {

	public static Identity of(Object identity)
	{
		Objects.requireNonNull(identity);

		if (identity instanceof Identity)
		{
			return (Identity) identity;
		}

		return new Identity(identity);
	}

	private final Object identity;

	private Identity(Object identity)
	{
		this.identity = identity;
	}

	@Override
	public boolean equals(Object that)
	{
		if (this == that)
		{
			return true;
		}

		if (that instanceof Identity)
		{
			Object thatIdentity = ((Identity) that).identity;
			return this.identity.equals(thatIdentity);
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		return this.identity.hashCode();
	}

	@Override
	public String toString()
	{
		return this.identity.toString();
	}
}