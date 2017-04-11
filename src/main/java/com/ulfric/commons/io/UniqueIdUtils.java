package com.ulfric.commons.io;

import java.util.UUID;

public enum UniqueIdUtils {

	;

	public static UUID parseUniqueId(String uniqueId)
	{
		try
		{
			return UUID.fromString(uniqueId);
		}
		catch (IllegalArgumentException ignore)
		{
			return null;
		}
	}

}