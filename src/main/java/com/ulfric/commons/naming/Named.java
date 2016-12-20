package com.ulfric.commons.naming;

import java.util.Objects;

public interface Named {

	default String getName()
	{
		return Named.getNameFromAnnotation(this);
	}

	static String getNameFromAnnotation(Named named)
	{
		Objects.requireNonNull(named);

		Name name = named.getClass().getAnnotation(Name.class);

		if (name == null)
		{
			throw new NameMissingException();
		}

		return name.value();
	}

}