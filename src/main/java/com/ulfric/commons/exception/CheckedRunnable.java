package com.ulfric.commons.exception;

@FunctionalInterface
public interface CheckedRunnable {

	void run() throws Throwable;

}
