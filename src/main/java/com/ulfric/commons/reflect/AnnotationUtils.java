package com.ulfric.commons.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.ulfric.commons.api.UtilInstantiationException;
import com.ulfric.commons.collect.SetUtils;

public class AnnotationUtils {

	public static List<Annotation> getLeafAnnotations(AnnotatedElement holder, Class<? extends Annotation> seed)
	{
		Objects.requireNonNull(holder);
		Objects.requireNonNull(seed);

		return new AnnotationLookup<>(seed).getLeaves(holder);
	}

	public static <T extends Annotation> T getRootAnnotation(AnnotatedElement holder, Class<T> seed)
	{
		Objects.requireNonNull(holder);
		Objects.requireNonNull(seed);

		return new AnnotationLookup<>(seed).getRoot(holder);
	}

	private static final class AnnotationLookup<T extends Annotation>
	{
		AnnotationLookup(Class<T> seed)
		{
			this.seed = seed;
			this.checked = SetUtils.newIdentityHashSet();
		}

		private final Class<T> seed;
		private final Set<Class<?>> checked;
		private final List<Annotation> results = new ArrayList<>();

		public T getRoot(AnnotatedElement holder)
		{
			Class<T> seed = this.seed;
			Set<Class<?>> checked = this.checked;
			for (Annotation annotation : holder.getAnnotations())
			{
				Class<? extends Annotation> annotationType = annotation.annotationType();

				if (!checked.add(annotationType))
				{
					continue;
				}

				if (annotationType == seed)
				{
					this.results.add(annotation);
					continue;
				}

				T root = this.getRoot(annotationType);
				if (root != null)
				{
					return root;
				}
			}

			return null;
		}

		public List<Annotation> getLeaves(AnnotatedElement holder)
		{
			Class<T> seed = this.seed;
			Set<Class<?>> checked = this.checked;
			for (Annotation annotation : holder.getAnnotations())
			{
				Class<? extends Annotation> annotationType = annotation.annotationType();

				if (!checked.add(annotationType))
				{
					continue;
				}

				if (annotationType == seed)
				{
					this.results.add(annotation);
					continue;
				}

				if (annotationType.isAnnotationPresent(seed))
				{
					this.results.add(annotation);
					continue;
				}

				this.getLeaves(annotationType);
			}

			return this.results;
		}
	}

	private AnnotationUtils()
	{
		throw new UtilInstantiationException();
	}

}