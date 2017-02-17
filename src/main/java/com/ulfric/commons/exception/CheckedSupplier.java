package com.ulfric.commons.exception;

@FunctionalInterface
public interface CheckedSupplier<T> {

	T get() throws Throwable;

}