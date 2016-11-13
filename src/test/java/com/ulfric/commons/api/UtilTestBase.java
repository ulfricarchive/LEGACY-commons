package com.ulfric.commons.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;
import com.google.common.truth.TruthJUnit;

public abstract class UtilTestBase<T extends Util> {

	@DisplayName("Instantiation Failure")
	@Test
	public final void testInstantiationFails() throws Exception
	{
		Type type = this.getClass().getGenericSuperclass();

		TruthJUnit.assume().that(type).isInstanceOf(ParameterizedType.class);

		ParameterizedType param = (ParameterizedType) type;

		Type actualType = param.getActualTypeArguments()[0];

		TruthJUnit.assume().that(actualType).isInstanceOf(Class.class);

		Class<?> clazz = (Class<?>) actualType;

		TruthJUnit.assume().that(clazz).isAssignableTo(Util.class);

		Constructor<?> constr = clazz.getDeclaredConstructor();

		Truth.assertThat(constr.isAccessible()).isFalse();

		constr.setAccessible(true);

		Throwable caught = null;
		try
		{
			constr.newInstance();
		}
		catch (InvocationTargetException exception)
		{
			caught = exception.getTargetException();
		}
		catch (UtilInstantiationException exception)
		{
			caught = exception;
		}
		Truth.assertThat(caught).isNotNull();
		Truth.assertThat(caught).isInstanceOf(UtilInstantiationException.class);
	}

}