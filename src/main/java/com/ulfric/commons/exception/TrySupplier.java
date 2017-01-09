package com.ulfric.commons.exception;

@FunctionalInterface
public interface TrySupplier<T> {

	T get() throws Throwable;

}