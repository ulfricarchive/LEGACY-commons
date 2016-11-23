package com.ulfric.commons.object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import com.ulfric.commons.reflect.MemberUtils;
import com.ulfric.commons.reflect.MethodUtils;

final class ToString extends ObjectHelper {

	public static String toString(Object object)
	{
		Objects.requireNonNull(object);

		return new ToString(object).toString();
	}

	private ToString(Object object)
	{
		super(object);
	}

	private final StringJoiner joiner = new StringJoiner(", ");

	private String buildString()
	{
		this.getFields().forEach(this::addFieldToString);

		return this.joiner.toString();
	}

	private void addFieldToString(Field field)
	{
		String element = this.serializeField(field);

		if (element == null)
		{
			return;
		}

		this.joiner.add(element);
	}

	private String serializeField(Field field)
	{
		if (MemberUtils.getSerializability(field).isNotSerializable())
		{
			return null;
		}

		return this.composeField(field);
	}

	private String composeField(Field field)
	{
		return field.getName() + '=' + this.getStringRepresentation(field);
	}

	private String getStringRepresentation(Field field)
	{
		try
		{
			Object get = field.get(this.object);

			return this.getStringRepresentation(get);
		}
		catch (IllegalArgumentException | IllegalAccessException ignore)
		{
			return null;
		}
	}

	private String getStringRepresentation(Object object)
	{
		String basicString = this.getBasicString(object);

		return basicString == null ? ObjectUtils.generateString(object) : basicString;
	}

	private String getBasicString(Object object)
	{
		String preGet = this.preGetObject(object);
		if (preGet != null)
		{
			return preGet;
		}

		String methodGet = this.methodGetObject(object);

		return methodGet;
	}

	private String methodGetObject(Object object)
	{
		Optional<Method> methodLookup = MethodUtils.getDeclaredMethod(object.getClass(), "toString");

		if (methodLookup.isPresent())
		{
			Method method = methodLookup.get();

			if (this.isOverriden(method))
			{
				return object.toString();
			}
		}

		return null;
	}

	private String preGetObject(Object object)
	{
		if (object == null)
		{
			return this.nullLookup();
		}

		if (this.isRecursive(object))
		{
			return this.recursiveLookup();
		}

		return null;
	}

	private String nullLookup()
	{
		return "<null>";
	}

	private String recursiveLookup()
	{
		return "<recursive>";
	}

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append(this.type.getSimpleName());
		result.append('[');
		result.append(this.buildString());
		result.append(']');

		return result.toString();
	}

}