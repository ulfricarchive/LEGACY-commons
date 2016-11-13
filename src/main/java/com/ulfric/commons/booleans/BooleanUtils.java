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

		if (parse.equals("true"))
		{
			return true;
		}

		if (parse.equals("false"))
		{
			return false;
		}

		throw new BooleanFormatException(value);
	}

	private BooleanUtils()
	{
		Util.onConstruct();
	}

}