package com.ulfric.commons.reflect;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ulfric.commons.api.UtilInstantiationException;

public class ClassUtils {

	private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_OBJECT = new IdentityHashMap<>();
	private static final Map<Class<?>, Class<?>> OBJECT_TO_PRIMITIVE = new IdentityHashMap<>();

	static
	{
		ClassUtils.PRIMITIVE_TO_OBJECT.put(void.class, Void.class);
		ClassUtils.PRIMITIVE_TO_OBJECT.put(boolean.class, Boolean.class);
		ClassUtils.PRIMITIVE_TO_OBJECT.put(byte.class, Byte.class);
		ClassUtils.PRIMITIVE_TO_OBJECT.put(char.class, Character.class);
		ClassUtils.PRIMITIVE_TO_OBJECT.put(short.class, Short.class);
		ClassUtils.PRIMITIVE_TO_OBJECT.put(int.class, Integer.class);
		ClassUtils.PRIMITIVE_TO_OBJECT.put(long.class, Long.class);
		ClassUtils.PRIMITIVE_TO_OBJECT.put(float.class, Float.class);
		ClassUtils.PRIMITIVE_TO_OBJECT.put(double.class, Double.class);
		ClassUtils.PRIMITIVE_TO_OBJECT.put(null, Object.class);

		ClassUtils.PRIMITIVE_TO_OBJECT.forEach((k, v) -> ClassUtils.OBJECT_TO_PRIMITIVE.put(v, k));
	}

	public static Class<?> box(Class<?> clazz)
	{
		return ClassUtils.PRIMITIVE_TO_OBJECT.getOrDefault(clazz, clazz);
	}

	public static Class<?> unbox(Class<?> clazz)
	{
		return ClassUtils.OBJECT_TO_PRIMITIVE.getOrDefault(clazz, clazz);
	}

	public static Set<Class<?>> getCommonClasses(Class<?>... classes)
	{
		return ClassUtils.getCommonClasses(Arrays.asList(classes));
	}

	public static Set<Class<?>> getCommonClasses(Iterable<Class<?>> classes)
	{
		Objects.requireNonNull(classes);
		classes.forEach(Objects::requireNonNull);

		Set<Class<?>> related = ClassUtils.getRelatedClasses(classes.iterator());

		ClassUtils.removeDuplicateEntries(related);
		ClassUtils.ensureNotEmpty(related);

		return related;
	}

	private static Set<Class<?>> getRelatedClasses(Iterator<Class<?>> classes)
	{
		ClassUtils.validateClassIterator(classes);

		Set<Class<?>> related = ClassUtils.getAssignableTo(classes.next());

		while (classes.hasNext())
		{
			related.retainAll(ClassUtils.getAssignableTo(classes.next()));
		}

		return related;
	}

	private static void validateClassIterator(Iterator<Class<?>> classes)
	{
		if (classes.hasNext())
		{
			return;
		}

		throw new IllegalArgumentException("No classes to get the common value from");
	}

	private static void ensureNotEmpty(Set<Class<?>> classes)
	{
		if (!classes.isEmpty())
		{
			return;
		}

		classes.add(Object.class);
	}

	private static void removeDuplicateEntries(Set<Class<?>> classes)
	{
		Iterator<Class<?>> iterator = classes.iterator();

		while (iterator.hasNext())
		{
			Class<?> current = iterator.next();

			for (Class<?> inner : classes)
			{
				if (current ==  inner)
				{
					continue;
				}

				if (current.isAssignableFrom(inner))
				{
					iterator.remove();
					break;
				}
			}
		}
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
				Arrays.stream(each.getInterfaces()).forEach(nextLayer::add);
			}
		}
		while (!nextLayer.isEmpty());

		return classes;
	}

	public static boolean isAbstract(Class<?> clazz)
	{
		Objects.requireNonNull(clazz);
		return Modifier.isAbstract(clazz.getModifiers());
	}

	private ClassUtils()
	{
		throw new UtilInstantiationException();
	}

}