package com.ulfric.commons.reflect;

import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.Objects;

import com.ulfric.commons.exception.Try;

public enum CodeSourceUtils {

	;

	public static FileSystem getFileSystemFromClass(Class<?> clazz)
	{
		Objects.requireNonNull(clazz);

		return CodeSourceUtils.getFileSystem(CodeSourceUtils.getCodeSource(clazz));
	}

	public static FileSystem getFileSystem(CodeSource source)
	{
		Objects.requireNonNull(source);

		URL sourceLocation = source.getLocation();
		URI sourceLocationURI = Try.to(sourceLocation::toURI);

		return Paths.get(sourceLocationURI).getFileSystem();
	}

	public static CodeSource getCodeSource(Class<?> clazz)
	{
		Objects.requireNonNull(clazz);

		return clazz.getProtectionDomain().getCodeSource();
	}

}