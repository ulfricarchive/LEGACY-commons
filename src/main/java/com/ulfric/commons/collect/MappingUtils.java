package com.ulfric.commons.collect;

import java.util.HashMap;
import java.util.Map;

import com.ulfric.commons.api.Util;

public class MappingUtils implements Util {

	public static <K, V> Map<K, V> newMap(@SuppressWarnings("unused") K ignore)
	{
		return new HashMap<>();
	}

	private MappingUtils()
	{
		Util.onConstruct();
	}

}