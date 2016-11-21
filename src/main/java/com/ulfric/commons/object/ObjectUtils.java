package com.ulfric.commons.object;

import com.ulfric.commons.api.Util;

public class ObjectUtils implements Util {

	public static String generateString(Object object)
	{
		return ToString.toString(object);
	}

	private ObjectUtils()
	{
		Util.onConstruct();
	}

}