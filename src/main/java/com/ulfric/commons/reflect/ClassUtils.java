package com.ulfric.commons.reflect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.ulfric.commons.api.UtilInstantiationException;

public class ClassUtils {

	// TODO change to getCommonClasses
	public static Class<?> getCommonClass(Class<?>... classes)
	{
		return ClassUtils.getCommonClass(Arrays.asList(classes));
	}

	// TODO change to getCommonClasses
	public static Class<?> getCommonClass(Iterable<Class<?>> classes)
	{
		Objects.requireNonNull(classes);
		classes.forEach(Objects::requireNonNull);

		Iterator<Class<?>> classesIterator = classes.iterator();

		if (!classesIterator.hasNext())
		{
			throw new IllegalArgumentException("No classes to get the common value from");
		}

		Set<Class<?>> related = ClassUtils.getAssignableTo(classesIterator.next());

		while (classesIterator.hasNext())
		{
			related.retainAll(ClassUtils.getAssignableTo(classesIterator.next()));
		}

		if (related.isEmpty())
		{
			return Object.class;
		}

		return related.iterator().next();
	}

	public static Set<Class<?>> getAssignableTo(Class<?> clazz)
	{
		Objects.requireNonNull(clazz);

		Set<Class<?>> classes = new LinkedHashSet<>();
		Set<Class<?>> nextLayer = new LinkedHashSet<>();
		nextLayer.add(clazz);

		do
		{
			List<Class<?>> thisLevel = new ArrayList<>(nextLayer);
			classes.addAll(nextLayer);
			nextLayer.clear();

			for (Class<?> each : thisLevel)
			{
				Class<?> superClass = each.getSuperclass();
				if (superClass != null && superClass != Object.class)
				{
					nextLayer.add(superClass);
				}
				for (Class<?> eachInt : each.getInterfaces())
				{
					nextLayer.add(eachInt);
				}
			}
		}
		while (!nextLayer.isEmpty());

		return classes;
	}

	private ClassUtils()
	{
		throw new UtilInstantiationException();
	}

}