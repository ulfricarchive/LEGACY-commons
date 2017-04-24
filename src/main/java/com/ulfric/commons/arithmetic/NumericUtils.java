package com.ulfric.commons.arithmetic;

import java.util.regex.Pattern;

public enum NumericUtils {
	
	;
	
	private static final Pattern NUMERIC_STRIPPER = Pattern.compile("-?\\d+(\\.\\d+)?");
	
	public static boolean isNumeric(String content)
	{
		return NumericUtils.NUMERIC_STRIPPER.matcher(content).matches();
	}
	
}
