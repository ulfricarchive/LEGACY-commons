package com.ulfric.commons.cdi;

import com.ulfric.commons.result.Result;

public interface InstanceFactory {

	<T> T createInstance(Class<T> request);

	<T> Result<T> tryToCreateInstance(Class<T> request);

	<T> T getOrCreateInstance(Class<T> request);

}