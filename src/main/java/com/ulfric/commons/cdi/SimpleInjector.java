package com.ulfric.commons.cdi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.ulfric.commons.convert.ConversionService;
import com.ulfric.commons.convert.Producer;
import com.ulfric.commons.exception.Failure;
import com.ulfric.commons.function.Result;

final class SimpleInjector implements Injector {

	SimpleInjector()
	{
		this.service = ConversionService.newInstance();
	}

	final ConversionService service;

	@Override
	public <T> T create(Class<T> request)
	{
		Result<T> createdResult = this.tryCreate(request);

		// TODO
		createdResult.ifFailure(Failure::raise);

		return createdResult.value();
	}

	private <T> Result<T> tryCreate(Class<T> request)
	{
		Result<T> createdResult = this.tryRequest(request);
		createdResult.ifSuccess(this::performInjection);
		if (createdResult.isFailure())
		{
			try
			{
				Constructor<T> defaultConstructor = request.getDeclaredConstructor();
				defaultConstructor.setAccessible(true);
				T created = defaultConstructor.newInstance();
				this.performInjection(created);
				return Result.of(created);
			}
			catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
					IllegalArgumentException | InvocationTargetException exception)
			{
				exception.printStackTrace();
				return createdResult;
			}
		}

		return createdResult;
	}

	private void performInjection(Object object)
	{
		Class<?> parentType = object.getClass();
		List<Field> fields = FieldUtils.getAllFieldsList(parentType);
		for (Field field : fields)
		{
			if (!field.isAnnotationPresent(Inject.class))
			{
				continue;
			}

			field.setAccessible(true);

			Class<?> type = field.getType();
			if (type == parentType)
			{
				try
				{
					field.set(object, object);
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					throw new RuntimeException(e);
				}

				continue;
			}

			try
			{
				Result<?> created = this.tryCreate(field.getType());

				if (created.isFailure())
				{
					if (field.get(object) != null)
					{
						continue;
					}

					Failure.raise(created.thrown());
				}

				field.set(object, created.value());
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	private <T> Result<T> tryRequest(Class<T> request)
	{
		try
		{
			return Result.of(this.request(request));
		}
		catch (Failure failure)
		{
			return Result.ofThrown(failure);
		}
	}

	private <T> T request(Class<T> request)
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
		public void toSelf()
		{
			this.to(this.request);
		}

		@Override
		public void to(Class<? extends T> provider)
		{
			Producer<T> producer = Producer.newInstance(this.request, () ->
			{
				try
				{
					return provider.newInstance();
				}
				catch (InstantiationException | IllegalAccessException e)
				{
					throw new RuntimeException(e);
				}
			});
			SimpleInjector.this.service.register(producer);
		}
	}

}