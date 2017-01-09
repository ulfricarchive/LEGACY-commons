package com.ulfric.commons.version;

import java.util.Objects;
import java.util.Optional;

public interface Versioned {

	default int getVersion()
	{
		return Versioned.getVersionFromAnnotation(this);
	}

	static Integer getVersionFromAnnotation(Versioned versioned)
	{
		return Versioned.tryToGetVersionFromAnnotation(versioned).orElseThrow(VersionMissingException::new);
	}

	static Optional<Integer> tryToGetVersionFromAnnotation(Versioned versioned)
	{
		Objects.requireNonNull(versioned);

		Version version = versioned.getClass().getAnnotation(Version.class);

		return version == null ? Optional.empty() : Optional.of(version.value());
	}

}