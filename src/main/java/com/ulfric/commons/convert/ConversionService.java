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

	private ConversionService()
	{
		this.registerConverter(ConverterToString.INSTANCE);
	}

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
		this.resolved.clear();
	}

	private void registerConverter(Converter<?> converter)
	{
		this.converters.computeIfAbsent(converter.getTo(), MappingUtils::newMap).put(converter.getFrom(), converter);
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
			Map<MultiType, Converter<?>> converters = this.getValue(ConversionService.this.converters, to);

			if (converters != null)
			{
				MultiType from = this.from.toType();
				Converter<?> converter = this.getValue(converters, from);

				if (converter != null)
				{
					converters.put(from, converter);
					return this.runConverter(converter);
				}

				for (Map.Entry<MultiType, Converter<?>> entry : converters.entrySet())
				{
					if (!entry.getKey().isAssignableFrom(from))
					{
						continue;
					}
					converter = entry.getValue();

					converters.put(from, converter);
					return this.runConverter(converter);
				}
			}

			return this.selfMatch(to);
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

		private <V> V getValue(Map<MultiType, V> map, MultiType type)
		{
			return map.get(type);
		}
	}

}