package com.ulfric.commons.func;

@FunctionalInterface
public interface CheckedSupplier<T> {

	T get() throws Throwable;

}