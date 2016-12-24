package com.ulfric.commons.cdi;

import java.util.Objects;
import java.util.function.Supplier;

import com.ulfric.commons.convert.ConversionService;
import com.ulfric.commons.convert.Producer;

final class SimpleInjector implements Injector {

	SimpleInjector()
	{
		this.service = ConversionService.newInstance();
	}

	final ConversionService service;

	@Override
	public <T> T request(Class<T> request)
	{
		return this.service.produce().to(request);
	}

	@Override
	public <T> Binding<T> bind(Class<T> request)
	{
		Objects.requireNonNull(request);
		return new SimpleBinding<>(request);
	}

	final class SimpleBinding<T> implements Binding<T>
	{
		SimpleBinding(Class<T> request)
		{
			this.request = request;
		}

		private final Class<T> request;

		@Override
		public void to(Supplier<? extends T> supplier)
		{
			Producer<T> producer = Producer.newInstance(this.request, supplier);
			SimpleInjector.this.service.register(producer);
		}
	}

}