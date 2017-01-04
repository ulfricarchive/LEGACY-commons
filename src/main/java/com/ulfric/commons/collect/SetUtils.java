package com.ulfric.commons.collect;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public enum SetUtils {

	;

	public static <V> Set<V> newIdentityHashSet()
	{
		return Collections.newSetFromMap(new IdentityHashMap<>());
	}

}