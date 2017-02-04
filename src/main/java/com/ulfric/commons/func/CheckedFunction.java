package com.ulfric.commons.func;

@FunctionalInterface
public interface CheckedFunction<T, R> {

	R get(T t) throws Throwable;

}