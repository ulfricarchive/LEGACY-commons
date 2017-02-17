package com.ulfric.commons.exception;

@FunctionalInterface
public interface CheckedFunction<T, R> {

	R get(T t) throws Throwable;

}