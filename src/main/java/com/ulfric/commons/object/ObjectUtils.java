package com.ulfric.commons.object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import com.ulfric.commons.api.Util;
import com.ulfric.commons.reflect.MethodUtils;

public class ObjectUtils implements Util {

	public static String generateString(Object object)
	{
		Objects.requireNonNull(object);

		StringBuilder result = new StringBuilder();

		Class<?> type = object.getClass();

		result.append(type.getSimpleName());
		result.append('[');

		StringJoiner joiner = new StringJoiner(", ");
		for (Field field : ObjectUtils.getFieldsRecursively(type))
		{
			if (field.isSynthetic())
			{
				continue;
			}

			int modifiers = field.getModifiers();
			if (Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers))
			{
				continue;
			}

			field.setAccessible(true);

			Object get;

			try
			{
				get = field.get(object);
			}
			catch (IllegalArgumentException | IllegalAccessException ignore)
			{
				continue;
			}

			StringBuilder internal = new StringBuilder();
			internal.append(field.getName());
			internal.append('=');

			if (get == null)
			{
				internal.append("<null>");
			}
			else if (get == object)
			{
				internal.append("<recursive>");
			}
			else
			{
				Optional<Method> method = MethodUtils.getDeclaredMethod(get.getClass(), "toString");

				if (method.isPresent())
				{
					if (method.get().getDeclaringClass() != Object.class)
					{
						internal.append(get.toString());
					}
					else
					{
						internal.append(ObjectUtils.generateString(get));
					}
				}
				else
				{
					internal.append(ObjectUtils.generateString(get));
				}
			}

			joiner.add(internal);
		}

		result.append(joiner.toString());

		result.append(']');

		return result.toString();
	}

	private static List<Field> getFieldsRecursively(Class<?> clazz)
	{
		if (clazz == null || clazz == Object.class)
		{
			return Collections.emptyList();
		}

		Field[] fields = clazz.getDeclaredFields();

		List<Field> allFields = new ArrayList<>(fields.length);

		for (Field field : fields)
		{
			allFields.add(field);
		}

		allFields.addAll(ObjectUtils.getFieldsRecursively(clazz.getSuperclass()));

		return allFields;
	}

	private ObjectUtils()
	{
		Util.onConstruct();
	}

}