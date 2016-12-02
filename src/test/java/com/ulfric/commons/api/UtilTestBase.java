package com.ulfric.commons.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ulfric.verify.Verify;

public abstract class UtilTestBase<T extends Util> {

	@DisplayName("Instantiation Failure")
	@Test
	final void testInstantiationFails() throws Exception
	{
		Type type = this.getClass().getGenericSuperclass();

		Verify.that(type).isInstanceOf(ParameterizedType.class);

		ParameterizedType param = (ParameterizedType) type;

		Type actualType = param.getActualTypeArguments()[0];

		Verify.that(actualType).isInstanceOf(Class.class);

		Class<?> clazz = (Class<?>) actualType;

		Verify.that(clazz).isAssignableTo(Util.class);

		Constructor<?> constr = clazz.getDeclaredConstructor();

		Verify.that(constr).isNotAccessible();

		constr.setAccessible(true);

		Verify.that(() -> constr.newInstance()).doesThrow(UtilInstantiationException.class);
	}

}