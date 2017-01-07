package com.ulfric.commons.service;

import com.ulfric.commons.naming.Named;

public interface Service extends Named {

	@Override
	default String getName()
	{
		return Named.tryToGetNameFromAnnotation(this).orElseGet(() ->
		{
			String name = this.getClass().getSimpleName();
			if (name.endsWith("Service"))
			{
				name = name.substring(0, name.length() - "Service".length());
			}
			return name;
		});
	}

}