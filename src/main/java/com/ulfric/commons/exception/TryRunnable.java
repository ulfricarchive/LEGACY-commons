package com.ulfric.commons.exception;

@FunctionalInterface
public interface TryRunnable {

	void run() throws Throwable;

}
