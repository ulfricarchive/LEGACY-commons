package com.ulfric.commons.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.ulfric.commons.convert.ConversionService;
import com.ulfric.commons.convert.Producer;
import com.ulfric.commons.exception.Failure;
import com.ulfric.commons.result.Result;

final class SimpleInjector implements Injector {

	SimpleInjector()
	{
		this.service = ConversionService.newInstance();
		this.scopes = new IdentityHashMap<>();
		this.sharedInstances = new InstancePool();

		this.bindScope(Default.class).to(InstanceMaker::forceCreate);
		this.bindScope(Shared.class).to(this.sharedInstances::getOrCreate);
	}

	final ConversionService service;
	final Map<Class<? extends Annotation>, Function<Class<?>, ?>> scopes;
	final InstancePool sharedInstances;

	@Override
	public <T> T create(Class<T> request)
	{
		Objects.requireNonNull(request);

		Result<T> createdResult = this.tryCreate(request);
		createdResult.ifFailure(Failure::raise);
		return createdResult.value();
	}

	private <T> Result<T> tryCreate(Class<T> request)
	{
		Result<T> createdResult = this.tryRequest(request);
		createdResult.ifSuccess(this::injectValues);
		if (createdResult.isFailure())
		{
			return this.tryCreateFromConstructor(request);
		}

		return createdResult;
	}

	private <T> Result<T> tryCreateFromConstructor(Class<T> request)
	{
		try
		{
			Constructor<T> defaultConstructor = request.getDeclaredConstructor();
			defaultConstructor.setAccessible(true);
			T created = defaultConstructor.newInstance();
			this.injectValues(created);
			return Result.of(created);
		}
		catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
				IllegalArgumentException | InvocationTargetException exception)
		{
			Result.ofThrown(exception);
		}

		return Result.empty();
	}

	@Override
	public void injectValues(Object object)
	{
		Objects.requireNonNull(object);

		Class<?> parentType = object.getClass();
		FieldUtils.getAllFieldsList(parentType)
			.stream()
			.filter(this::isInjectable)
			.forEach(field ->
			{
				field.setAccessible(true);

				Class<?> type = field.getType();
				if (type == parentType)
				{
					this.setField(object, field, object);
					return;
				}

				this.injectField(object, field);
			});
	}

	private boolean isInjectable(Field field)
	{
		return field.isAnnotationPresent(Inject.class);
	}

	private void injectField(Object owner, Field field)
	{
		try
		{
			Result<?> created = this.tryCreate(field.getType());

			if (created.isFailure())
			{
				if (field.get(owner) != null)
				{
					return;
				}

				Failure.raise(created.thrown());
			}

			field.set(owner, created.value());
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void setField(Object owner, Field field, Object value)
	{
		try
		{
			field.set(owner, value);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
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
	public Class<? extends Annotation> getScope(Class<?> producer)
	{
		for (Annotation annotation : producer.getAnnotations())
		{
			Class<? extends Annotation> scope = this.getScopeFromAnnotation(annotation.annotationType());

			if (scope != null)
			{
				return scope;
			}
		}

		return Default.class;
	}

	private Class<? extends Annotation> getScopeFromAnnotation(Class<? extends Annotation> annotation)
	{
		if (this.isDirectScope(annotation))
		{
			return annotation;
		}

		for (Annotation present : annotation.getAnnotations())
		{
			Class<? extends Annotation> presentType = present.annotationType();

			if (presentType == annotation)
			{
				continue;
			}

			Class<? extends Annotation> scope = this.getScopeFromAnnotation(presentType);

			if (scope != null)
			{
				return scope;
			}
		}

		return null;
	}

	private boolean isDirectScope(Class<? extends Annotation> annotation)
	{
		return annotation.isAnnotationPresent(Scope.class);
	}

	@Override
	public ScopeBinding bindScope(Class<? extends Annotation> scope)
	{
		Objects.requireNonNull(scope);

		if (!scope.isAnnotationPresent(Scope.class))
		{
			throw new AnnotationNotScopeException();
		}

		return new SimpleScopeBinding(scope);
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
			this.toWithoutValidation(this.request);
		}

		@Override
		public void to(Class<? extends T> provider)
		{
			Objects.requireNonNull(provider);
			this.toWithoutValidation(provider);
		}

		private void toWithoutValidation(Class<? extends T> provider)
		{
			Class<? extends Annotation> scope = SimpleInjector.this.getScope(provider);
			Function<Class<?>, ?> creator = SimpleInjector.this.scopes.get(scope);
			Producer<T> producer = Producer.newInstance(this.request, () ->
			{
				@SuppressWarnings("unchecked")
				T instance = (T) creator.apply(provider);
				return instance;
			});
			SimpleInjector.this.service.register(producer);
		}
	}

	final class SimpleScopeBinding implements ScopeBinding
	{
		SimpleScopeBinding(Class<? extends Annotation> scope)
		{
			this.scope = scope;
		}

		private final Class<? extends Annotation> scope;

		@Override
		public void to(Function<Class<?>, ?> instanceCreator)
		{
			this.putFunction(instanceCreator);
		}

		private void putFunction(Function<Class<?>, ?> function)
		{
			Objects.requireNonNull(function);
			SimpleInjector.this.scopes.put(this.scope, function);
		}
	}

}