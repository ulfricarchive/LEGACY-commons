package com.ulfric.commons.text;

import com.ulfric.commons.api.UtilInstantiationException;

public class StringUtils {

	public static boolean startsWithAndEndsWith(String test, String check)
	{
		if (test == null)
		{
			return check == null;
		}

		if (check == null)
		{
			return false;
		}

		return test.startsWith(check) && test.endsWith(check);
	}

	private StringUtils()
	{
		throw new UtilInstantiationException();
	}

}