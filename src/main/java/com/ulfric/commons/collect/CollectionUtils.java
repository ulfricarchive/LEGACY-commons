package com.ulfric.commons.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ulfric.commons.api.UtilInstantiationException;

public class CollectionUtils {

	public static <T> List<T> copyToList(Iterable<T> iterable)
	{
		if (iterable instanceof Collection)
		{
			return new ArrayList<>((Collection<T>) iterable);
		}

		List<T> returnValue = new ArrayList<>();
		iterable.forEach(returnValue::add);
		return returnValue;
	}

	private CollectionUtils()
	{
		throw new UtilInstantiationException();
	}

}