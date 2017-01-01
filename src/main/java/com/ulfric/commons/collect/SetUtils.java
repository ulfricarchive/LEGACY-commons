package com.ulfric.commons.collect;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import com.ulfric.commons.api.UtilInstantiationException;

public class SetUtils {

	public static <V> Set<V> newIdentityHashSet()
	{
		return Collections.newSetFromMap(new IdentityHashMap<>());
	}

	private SetUtils()
	{
		throw new UtilInstantiationException();
	}

}