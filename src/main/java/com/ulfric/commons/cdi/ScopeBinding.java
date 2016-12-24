package com.ulfric.commons.cdi;

import java.util.function.Function;

public interface ScopeBinding {

	void to(Function<Class<?>, ?> instanceCreator);

}