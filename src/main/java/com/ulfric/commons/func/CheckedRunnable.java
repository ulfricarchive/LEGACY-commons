package com.ulfric.commons.func;

@FunctionalInterface
public interface CheckedRunnable {

	void run() throws Throwable;

}
