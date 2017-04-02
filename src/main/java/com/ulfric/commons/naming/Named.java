package com.ulfric.commons.naming;

import java.util.Objects;
import java.util.Optional;

public interface Named {

	default String getName()
	{
		return Named.getNameFromAnnotation(this);
	}

	static String getNameFromAnnotation(Named named)
	{
		Objects.requireNonNull(named);

		return Named.getNameFromAnnotation(named.getClass());
	}

	static Optional<String> tryToGetNameFromAnnotation(Named named)
	{
		Objects.requireNonNull(named);

		return Named.tryToGetNameFromAnnotation(named.getClass());
	}

	static String getNameFromAnnotation(Class<? extends Named> named)
	{
		Objects.requireNonNull(named);

		return Named.tryToGetNameFromAnnotation(named).orElseThrow(NameMissingException::new);
	}

	static Optional<String> tryToGetNameFromAnnotation(Class<? extends Named> named)
	{
		Objects.requireNonNull(named);

		Name name = named.getAnnotation(Name.class);

		return name == null ? Optional.empty() : Optional.of(name.value());
	}

}