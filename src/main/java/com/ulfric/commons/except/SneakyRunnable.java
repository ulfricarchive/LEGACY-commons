package com.ulfric.commons.except;

@FunctionalInterface
public interface SneakyRunnable {

	void run() throws Exception;

}
