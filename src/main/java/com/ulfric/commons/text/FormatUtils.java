package com.ulfric.commons.text;

import java.text.NumberFormat;

import com.ulfric.commons.api.UtilInstantiationException;

public class FormatUtils {

	public static String formatLong(long value)
	{
		return NumberFormat.getNumberInstance().format(value);
	}

	public static String formatMilliseconds(long value)
	{
		return FormatUtils.formatLong(Math.abs(value)) + "ms";
	}

	public FormatUtils()
	{
		throw new UtilInstantiationException();
	}

}