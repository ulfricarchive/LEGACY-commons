package com.ulfric.commons.cdi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class InstancePool {

	private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

	public <T> T getOrCreate(Class<T> request)
	{
		@SuppressWarnings("unchecked")
		T instance = (T) this.instances.computeIfAbsent(request, InstanceMaker::forceCreate);
		return instance;
	}

}