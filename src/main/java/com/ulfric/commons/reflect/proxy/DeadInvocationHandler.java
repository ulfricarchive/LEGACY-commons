package com.ulfric.commons.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public enum DeadInvocationHandler implements InvocationHandler {

	INSTANCE;

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable
	{
		throw new ProxyInvocationException();
	}

}