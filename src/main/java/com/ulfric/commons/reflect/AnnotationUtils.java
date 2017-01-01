package com.ulfric.commons.reflect;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

import com.ulfric.commons.api.UtilInstantiationException;
import com.ulfric.commons.collect.SetUtils;

public class AnnotationUtils {

	public static Annotation getLeafAnnotation(Class<?> holder, Class<? extends Annotation> seed)
	{
		Objects.requireNonNull(holder);
		Objects.requireNonNull(seed);

		return new AnnotationLookup<>(holder, seed).getLeaf();
	}

	public static <T extends Annotation> T getRootAnnotation(Class<?> holder, Class<T> seed)
	{
		Objects.requireNonNull(holder);
		Objects.requireNonNull(seed);

		return new AnnotationLookup<>(holder, seed).getRoot();
	}

	private static final class AnnotationLookup<T extends Annotation>
	{
		AnnotationLookup(Class<?> holder, Class<T> seed)
		{
			this.holder = holder;
			this.seed = seed;
			this.checked = SetUtils.newIdentityHashSet();
		}

		private final Class<?> holder;
		private final Class<T> seed;
		private final Set<Class<?>> checked;

		public T getRoot()
		{
			Class<T> seed = this.seed;
			Set<Class<?>> checked = this.checked;
			for (Annotation annotation : this.holder.getAnnotations())
			{
				Class<? extends Annotation> annotationType = annotation.annotationType();

				if (!checked.add(annotationType))
				{
					continue;
				}

				if (annotationType == seed)
				{
					@SuppressWarnings("unchecked")
					T root = (T) annotation;
					return root;
				}

				T root = AnnotationUtils.getRootAnnotation(annotationType, seed);
				if (root != null)
				{
					return root;
				}
			}

			return null;
		}

		public Annotation getLeaf()
		{
			Class<T> seed = this.seed;
			Set<Class<?>> checked = this.checked;
			for (Annotation annotation : this.holder.getAnnotations())
			{
				Class<? extends Annotation> annotationType = annotation.annotationType();

				if (!checked.add(annotationType))
				{
					continue;
				}

				if (annotationType == seed)
				{
					return annotation;
				}

				if (AnnotationUtils.getLeafAnnotation(annotationType, seed) != null)
				{
					return annotation;
				}
			}

			return null;
		}
	}

	private AnnotationUtils()
	{
		throw new UtilInstantiationException();
	}

}