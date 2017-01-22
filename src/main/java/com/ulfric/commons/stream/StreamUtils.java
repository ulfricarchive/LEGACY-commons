package com.ulfric.commons.stream;

import java.util.stream.BaseStream;
import java.util.stream.Stream;

import org.apache.commons.collections4.IteratorUtils;

public enum StreamUtils {

	;

	public static <T> Stream<T> cut(BaseStream<T, ?> stream)
	{
		return IteratorUtils.toList(stream.iterator()).stream();
	}

}
