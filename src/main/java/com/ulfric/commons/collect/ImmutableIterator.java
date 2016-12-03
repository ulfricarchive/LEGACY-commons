package com.ulfric.commons.collect;

import java.util.Iterator;
import java.util.Objects;

public final class ImmutableIterator<T> implements Iterator<T> {

	public static <T> ImmutableIterator<T> of(Iterable<T> iterable)
	{
		return ImmutableIterator.of(iterable.iterator());
	}

	public static <T> ImmutableIterator<T> of(Iterator<T> iterator)
	{
		Objects.requireNonNull(iterator);

		return new ImmutableIterator<>(iterator);
	}

	private ImmutableIterator(Iterator<T> iterator)
	{
		this.iterator = iterator;
	}

	private final Iterator<T> iterator;

	@Override
	public boolean hasNext()
	{
		return this.iterator.hasNext();
	}

	@Override
	public T next()
	{
		return this.iterator.next();
	}

}