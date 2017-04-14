package com.ulfric.commons.reflect;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public enum ClassUtils {

	;

	private static final Map<Class<?>, List<Class<?>>> HEIRARCHIES = new IdentityHashMap<>();

	public static List<Class<?>> getHeirarchy(Class<?> type)
	{
		return ClassUtils.HEIRARCHIES.computeIfAbsent(type,
				key ->
				{
					List<Class<?>> allClasses = org.apache.commons.lang3.ClassUtils.getAllSuperclasses(key);
					if (!allClasses.isEmpty())
					{
						allClasses.remove(allClasses.size() - 1);
						Collections.reverse(allClasses);
					}
					allClasses.add(key);
					return allClasses;
				});
	}

}