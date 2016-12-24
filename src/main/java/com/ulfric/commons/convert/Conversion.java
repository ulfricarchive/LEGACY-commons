package com.ulfric.commons.convert;

public interface Conversion {

	<T> T to(Class<T> to);

	Object to(Class<?>... to);

}