package com.ulfric.commons.exception;

@FunctionalInterface
public interface CheckedConsumer<T> {

	void accept(T value) throws Throwable;

}