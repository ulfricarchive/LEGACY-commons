package com.ulfric.commons.exception;

@FunctionalInterface
public interface CheckedFunction<T, R> {

	R apply(T t) throws Throwable;

}