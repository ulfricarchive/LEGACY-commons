package com.ulfric.commons.cdi;

import java.util.function.Supplier;

public interface Binding<T> {

	void to(Supplier<? extends T> supplier);

}