package com.ulfric.commons.text;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public enum FormatUtils {

	;

	public static String formatDouble(double value)
	{
		return NumberFormat.getNumberInstance().format(value);
	}

	public static String formatLong(long value)
	{
		return NumberFormat.getNumberInstance().format(value);
	}

	public static String formatMilliseconds(long value)
	{
		return FormatUtils.formatLong(Math.abs(value)) + "ms";
	}
	
	public static String formatRemaining(long value)
	{
		if (value <= 0)
		{
			return "0m 0s";
		}
		
		long days = TimeUnit.MILLISECONDS.toDays(value);
		long hours = TimeUnit.MILLISECONDS.toHours(value) % 24;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(value) % 60;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(value) % 60;
		
		StringBuilder builder = new StringBuilder();
		
		if (days > 0)
		{
			builder.append(days).append("d ");
		}
		
		if (days > 0 || hours > 0)
		{
			builder.append(hours).append("h ");
		}
		
		builder.append(minutes).append("m ");
		builder.append(seconds).append("s");
		
		return builder.toString();
	}

}