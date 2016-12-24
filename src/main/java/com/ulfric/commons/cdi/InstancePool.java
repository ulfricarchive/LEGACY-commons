package com.ulfric.commons.cdi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class InstancePool {

	InstancePool(InstanceFactory factory)
	{
		this.factory = factory;
	}

	private final InstanceFactory factory;
	private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

	public <T> T getOrCreate(Class<T> request)
	{
		@SuppressWarnings("unchecked")
		T instance = (T) this.instances.computeIfAbsent(request, this.factory::createInstance);
		return instance;
	}

}