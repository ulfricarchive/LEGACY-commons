package com.ulfric.commons.object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.ulfric.commons.reflect.FieldUtils;

class ObjectHelper {

	ObjectHelper(Object object)
	{
		this.object = object;
		this.type = object.getClass();
	}

	final Object object;
	final Class<?> type;

	final boolean isOverriden(Method method)
	{
		return method.getDeclaringClass() != Object.class;
	}

	final boolean isRecursive(Object object)
	{
		return object == this.object;
	}

	final List<Field> getFields()
	{
		List<Field> fields = FieldUtils.getAllFields(this.type);
		fields.forEach(this::setupField);
		return fields;
	}

	private void setupField(Field field)
	{
		field.setAccessible(true);
	}

}