package com.ulfric.commons.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SingletonIterator<T> implements Iterator<T> {

	public SingletonIterator(T value)
	{
		this.value = value;
	}

	private final T value;
	private boolean hasNext = true;

	@Override
	public boolean hasNext()
	{
		return this.hasNext;
	}

	@Override
	public T next()
	{
		if (!this.hasNext)
		{
			throw new NoSuchElementException();
		}

		this.hasNext = false;

		return this.value;
	}

}