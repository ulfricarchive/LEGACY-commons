package com.ulfric.commons.cdi;

import java.util.Objects;
import java.util.function.Supplier;

import com.ulfric.commons.convert.ConversionService;
import com.ulfric.commons.convert.Producer;

public final class Injector {

	public static Injector newInstance()
	{
		return new Injector();
	}

	private Injector()
	{
		this.service = ConversionService.newInstance();
	}

	final ConversionService service;

	public <T> T request(Class<T> request)
	{
		return this.service.produce().to(request);
	}

	public <T> Binding<T> bind(Class<T> request)
	{
		Objects.requireNonNull(request);
		return new Binding<>(request);
	}

	public final class Binding<T>
	{
		Binding(Class<T> request)
		{
			this.request = request;
		}

		private final Class<T> request;

		public void to(Supplier<? extends T> supplier)
		{
			Producer<T> producer = Producer.newInstance(this.request, supplier);
			Injector.this.service.register(producer);
		}
	}

}