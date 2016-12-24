package com.ulfric.commons.reflect.proxy;

import java.lang.reflect.Proxy;
import java.util.Objects;

import com.ulfric.commons.api.UtilInstantiationException;

public class ProxyUtils {

	public static <T> T newDeadProxy(Class<T> proxyClass)
	{
		ProxyUtils.validateProxyClass(proxyClass);

		@SuppressWarnings("unchecked")
		T proxy = (T) Proxy.newProxyInstance(proxyClass.getClassLoader(),
				new Class<?>[] {proxyClass}, DeadInvocationHandler.INSTANCE);

		return proxy;
	}

	private static void validateProxyClass(Class<?> proxyClass)
	{
		Objects.requireNonNull(proxyClass);

		if (proxyClass.isInterface())
		{
			return;
		}

		throw new IllegalArgumentException("Proxies must be instances of interfaces");
	}

	private ProxyUtils()
	{
		throw new UtilInstantiationException();
	}

}