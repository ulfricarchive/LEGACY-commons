package com.ulfric.commons.api;

public interface Util {

	static void onConstruct() throws UtilInstantiationException
	{
		throw new UtilInstantiationException();
	}

}