package com.ulfric.commons.object;

public enum Serializability {

	SERIALIZABLE(true),
	NOT_SERIALIZABLE(false),
	NULL(false);

	Serializability(boolean serializable)
	{
		this.serializable = serializable;
	}

	private final boolean serializable;

	public boolean isSerializable()
	{
		return this.serializable;
	}

	public boolean isNotSerializable()
	{
		return !this.serializable;
	}

}