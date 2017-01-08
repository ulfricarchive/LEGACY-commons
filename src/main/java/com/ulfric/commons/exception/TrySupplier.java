package com.ulfric.commons.exception;

public interface TrySupplier<T> {

	T get() throws Throwable;

}