package com.ulfric.commons.text;

import java.text.NumberFormat;

public enum FormatUtils {

	;

	public static String formatLong(long value)
	{
		return NumberFormat.getNumberInstance().format(value);
	}

	public static String formatMilliseconds(long value)
	{
		return FormatUtils.formatLong(Math.abs(value)) + "ms";
	}

}