package com.ulfric.commons.collect;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

public enum MapUtils {

	;

	public static <K, V> Map<K, V> newSynchronizedIdentityHashMap()
	{
		return Collections.synchronizedMap(new IdentityHashMap<>());
	}

}