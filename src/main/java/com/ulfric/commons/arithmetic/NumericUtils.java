package com.ulfric.commons.arithmetic;

import java.util.regex.Pattern;

public enum NumericUtils {
	
	;
	
	private static final Pattern NUMERIC_STRIPPER = Pattern.compile("-?\\d+(\\.\\d+)?");
	
	public static boolean isNumeric(String content)
	{
		return NumericUtils.NUMERIC_STRIPPER.matcher(content).matches();
	}
	
	public static boolean isInteger(String content)
	{
		try
		{
			Integer.parseInt(content);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
	
	public static boolean isLong(String content)
	{
		try
		{
			Long.valueOf(content);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
	
	public static boolean isFloat(String content)
	{
		try
		{
			Float.valueOf(content);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
	
	public static boolean isDouble(String content)
	{
		try
		{
			Double.valueOf(content);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
	
}
