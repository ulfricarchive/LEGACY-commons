package com.ulfric.commons.cdi;

public interface Binding<T> {

	void toSelf();

	void to(Class<? extends T> provider);

}