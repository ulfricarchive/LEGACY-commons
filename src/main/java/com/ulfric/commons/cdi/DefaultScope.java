package com.ulfric.commons.cdi;

import java.lang.annotation.Annotation;

enum DefaultScope implements Default {

	INSTANCE;

	@Override
	public Class<? extends Annotation> annotationType()
	{
		return Default.class;
	}

}