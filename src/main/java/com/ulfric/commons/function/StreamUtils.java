package com.ulfric.commons.function;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.ulfric.commons.api.UtilInstantiationException;

public class StreamUtils {

	public static <T> Stream<T> stream(Iterator<T> iterator)
	{
		Spliterator<T> spliterator =
				Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED & Spliterator.DISTINCT);
		return StreamUtils.stream(spliterator);
	}

	public static <T> Stream<T> stream(Spliterator<T> spliterator)
	{
		return StreamSupport.stream(spliterator, false);
	}

	private StreamUtils()
	{
		throw new UtilInstantiationException();
	}

}