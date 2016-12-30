package com.ulfric.commons.reflect;

import java.lang.annotation.Annotation;

import com.ulfric.commons.api.UtilInstantiationException;

public class AnnotationUtils {

	// TODO these names are bad

	public static Annotation getLeafAnnotation(Class<?> holder, Class<? extends Annotation> seed)
	{
		for (Annotation annotation : holder.getAnnotations())
		{
			Class<? extends Annotation> annotationType = annotation.annotationType();

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

	public static <T extends Annotation> T getRootAnnotation(Class<?> holder, Class<T> seed)
	{
		for (Annotation annotation : holder.getAnnotations())
		{
			Class<? extends Annotation> annotationType = annotation.annotationType();

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

	private AnnotationUtils()
	{
		throw new UtilInstantiationException();
	}

}