package com.ulfric.commons.booleans;

import com.ulfric.commons.api.Util;

public class BooleanUtils implements Util {

	public static boolean parseBoolean(String value)
	{
		if (value == null)
		{
			throw new BooleanFormatException();
		}

		String parse = value.toLowerCase();

		if (BooleanUtils.isTrue(parse))
		{
			return true;
		}

		if (BooleanUtils.isFalse(parse))
		{
			return false;
		}

		throw new BooleanFormatException(value);
	}

	private static boolean isTrue(String value)
	{
		return value.equals("true");
	}

	private static boolean isFalse(String value)
	{
		return value.equals("false");
	}

	private BooleanUtils()
	{
		Util.onConstruct();
	}

}