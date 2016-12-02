package com.ulfric.commons.convert;

import java.util.HashMap;
import java.util.Map;

import com.ulfric.commons.exception.Failure;

public final class ConversionService {

	public static ConversionService newInstance()
	{
		return new ConversionService();
	}

	private ConversionService() { }

	private final Map<MultiType, Map<MultiType, Converter<?>>> converters = new HashMap<>();

	public <T> T convert(Class<T> to, Object from)
	{
		return this.convert(MultiType.of(to), Token.of(from));
	}

	private <T> T convert(MultiType to, Token from)
	{
		// TODO fast lookups

		Map<MultiType, Converter<?>> converters = this.getValue(this.converters, to);

		if (converters != null)
		{
			Converter<?> converter = this.getValue(converters, from.toTypeHash());

			if (converter != null)
			{
				@SuppressWarnings("unchecked")
				T result = (T) converter.apply(from);
				return result;
			}
		}

		@SuppressWarnings("unchecked")
		T result = (T) from.firstMatch(to).orElseGet(() ->
			Failure.raise(ConversionException.class, "Unable to convert " + from.toTypeHash() + " to " + to));
		return result;
	}

	private <V> V getValue(Map<MultiType, V> map, MultiType type)
	{
		return map.get(type);
	}

}