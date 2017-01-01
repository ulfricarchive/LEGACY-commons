package com.ulfric.commons.text;

import java.text.NumberFormat;

import com.ulfric.commons.api.UtilInstantiationException;

public class FormatUtils {

	public static String formatLong(long value)
	{
		return NumberFormat.getNumberInstance().format(value);
	}

	public FormatUtils()
	{
		throw new UtilInstantiationException();
	}

}