package com.ulfric.commons.cdi;

public interface Injector {

	public static Injector newInstance()
	{
		return new SimpleInjector();
	}

	<T> T create(Class<T> request);

	void injectValues(Object object);

	<T> Binding<T> bind(Class<T> request);

}