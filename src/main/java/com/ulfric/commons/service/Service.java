package com.ulfric.commons.service;

import com.ulfric.commons.naming.Named;
import com.ulfric.commons.version.Versioned;

public interface Service extends Named, Versioned {

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

	@Override
	default int getVersion()
	{
		return Versioned.tryToGetVersionFromAnnotation(this).orElse(1);
	}

}