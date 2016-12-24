package com.ulfric.commons.cdi;

public interface Injector {

	public static Injector newInstance()
	{
		return new SimpleInjector();
	}

	<T> T request(Class<T> request);


	<T> Binding<T> bind(Class<T> request);

}