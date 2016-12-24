package com.ulfric.commons.cdi;

import java.lang.annotation.Annotation;

public interface Injector {

	public static Injector newInstance()
	{
		return new SimpleInjector();
	}

	<T> T create(Class<T> request);

	void injectValues(Object object);

	<T> Binding<T> bind(Class<T> request);

	ScopeBinding bindScope(Class<? extends Annotation> scope);

	Annotation getScope(Class<?> provider);

}