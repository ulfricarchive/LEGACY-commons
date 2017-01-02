package com.ulfric.commons.except;

@FunctionalInterface
public interface SneakySupplier<T> {

	T supply() throws Throwable;

}
