package com.ulfric.commons.convert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ulfric.commons.exception.Failure;
import com.ulfric.commons.function.MappingUtils;

public final class ConversionService {

	public static ConversionService newInstance()
	{
		return new ConversionService();
	}

	private ConversionService() { }

	final Map<MultiType, Map<MultiType, Converter<?>>> converters = new HashMap<>();
	final Map<MultiType, Map<MultiType, Converter<?>>> resolved = new HashMap<>();

	public Conversion convert(Object from)
	{
		return new Conversion(MultiObject.of(from));
	}

	public Conversion convert(Object... from)
	{
		return new Conversion(MultiObject.of(from));
	}

	public void register(Converter<?> converter)
	{
		Objects.requireNonNull(converter);

		this.registerConverter(converter);
		this.rebuildCache();
	}

	void cacheConverter(Converter<?> converter)
	{
		this.putConverter(this.resolved, converter);
	}

	private void rebuildCache()
	{
		this.resolved.clear();
		this.resolved.putAll(this.converters);
	}

	private void registerConverter(Converter<?> converter)
	{
		this.putConverter(this.converters, converter);
	}

	private void putConverter(Map<MultiType, Map<MultiType, Converter<?>>> map, Converter<?> converter)
	{
		map.computeIfAbsent(converter.getTo(), MappingUtils::newMap).put(converter.getFrom(), converter);
	}

	public final class Conversion
	{
		Conversion(MultiObject from)
		{
			this.from = from;
		}

		private final MultiObject from;

		public <T> T to(Class<T> to)
		{
			return this.convert(MultiType.of(to));
		}

		public <T> T to(Class<?>... to)
		{
			return this.convert(MultiType.of(to));
		}

		private <T> T convert(MultiType to)
		{
			try
			{
				return this.earlyResolve(to);
			}
			catch (Failure failure)
			{
				Throwable cause = failure.getCause();

				if (cause instanceof EarlyResolveException)
				{
					return this.resolve(to);
				}

				return Failure.raise(cause);
			}
		}

		private <T> T resolve(MultiType to)
		{
			try
			{
				Map<MultiType, Converter<?>> converters = this.getValue(ConversionService.this.converters, to, false);
				Converter<?> converter = this.getValue(converters, this.from.toType(), true);

				ConversionService.this.cacheConverter(converter);
				return this.runConverter(converter);
			}
			catch (Failure failure)
			{
				Throwable cause = failure.getCause();

				if (cause instanceof ValueMissingException)
				{
					return this.selfMatch(to);
				}

				return Failure.raise(cause);
			}
		}

		private <T> T selfMatch(MultiType to)
		{
			@SuppressWarnings("unchecked")
			T result = (T) this.from.firstMatch(to).orElseGet(() ->
				Failure.raise(ConversionException.class, "Unable to convert " + this.from.toType() + " to " + to));
			return result;
		}

		private <T> T earlyResolve(MultiType to)
		{
			Map<MultiType, Converter<?>> earlyConverters = ConversionService.this.resolved.get(to);
			if (earlyConverters == null)
			{
				return Failure.raise(EarlyResolveException.class);
			}

			Converter<?> converter = earlyConverters.get(this.from.toType());
			if (converter == null)
			{
				return Failure.raise(EarlyResolveException.class);
			}

			return this.runConverter(converter);
		}

		@SuppressWarnings("unchecked")
		private <T> T runConverter(Converter<?> converter)
		{
			return (T) converter.apply(this.from);
		}

		private <V> V getValue(Map<MultiType, V> map, MultiType type, boolean againstKey)
		{
			V value = map.get(type);

			if (value != null)
			{
				return value;
			}

			for (MultiType key : map.keySet())
			{
				if (againstKey ? key.isAssignableFrom(type) : type.isAssignableFrom(key))
				{
					return map.get(key);
				}
			}

			return Failure.raise(ValueMissingException.class);
		}
	}

}