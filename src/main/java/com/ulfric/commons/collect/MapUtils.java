package com.ulfric.commons.collect;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

import com.ulfric.commons.api.UtilInstantiationException;

public class MapUtils {

	public static <K, V> Map<K, V> newSynchronizedIdentityHashMap()
	{
		return Collections.synchronizedMap(new IdentityHashMap<>());
	}

	private MapUtils()
	{
		throw new UtilInstantiationException();
	}

}