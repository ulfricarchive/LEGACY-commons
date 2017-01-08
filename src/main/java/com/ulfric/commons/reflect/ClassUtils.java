package com.ulfric.commons.reflect;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import com.ulfric.commons.exception.Try;

public enum ClassUtils {

	;

	public static URI getLocationURI(Class<?> clazz)
	{
		Objects.requireNonNull(clazz);

		return Try.to(() -> ClassUtils.getLocationURIThrows(clazz));
	}

	private static URI getLocationURIThrows(Class<?> clazz) throws URISyntaxException
	{
		return clazz.getProtectionDomain().getCodeSource().getLocation().toURI();
	}

}