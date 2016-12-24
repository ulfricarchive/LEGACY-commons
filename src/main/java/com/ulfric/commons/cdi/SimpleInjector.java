package com.ulfric.commons.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.ulfric.commons.convert.ConversionService;
import com.ulfric.commons.convert.converter.Producer;
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

	final Set<Class<?>> bindings = Collections.newSetFromMap(new IdentityHashMap<>());
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
		this.ensureBinding(request);
		Result<T> createdResult = this.tryProduce(request);

		if (createdResult.isFailure())
		{
			createdResult = InstanceMaker.createInstance(request);
		}

		return createdResult;
	}

	private void ensureBinding(Class<?> request)
	{
		if (this.bindings.contains(request))
		{
			return;
		}

		this.bind(request).toSelf();
	}

	private <T> Result<T> tryProduce(Class<T> request)
	{
		try
		{
			return Result.of(this.produce(request));
		}
		catch (Failure failure)
		{
			return Result.ofThrown(failure);
		}
	}

	private <T> T produce(Class<T> request)
	{
		return this.service.produce(request);
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

	@Override
	public Annotation getScope(Class<?> producer)
	{
		for (Annotation annotation : producer.getAnnotations())
		{
			if (this.isScope(annotation))
			{
				return annotation;
			}
		}

		return DefaultScope.INSTANCE;
	}

	private boolean isScope(Annotation annotation)
	{
		return this.scopes.containsKey(annotation.annotationType());
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
			Annotation scope = SimpleInjector.this.getScope(provider);
			Function<Class<?>, ?> creator = SimpleInjector.this.scopes.get(scope.annotationType());
			Producer<T> producer = Producer.newInstance(this.request, () ->
			{
				@SuppressWarnings("unchecked")
				T instance = (T) creator.apply(provider);
				return instance;
			});
			SimpleInjector.this.service.register(producer);
			SimpleInjector.this.bindings.add(provider);
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