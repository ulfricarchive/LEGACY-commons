package com.ulfric.commons.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(AnnotationUtils.class)
class AnnotationUtilsTest extends UtilTestBase {

	@Test
	void testGetLeafAnnotationsWithHolderNull()
	{
		Verify.that(() -> AnnotationUtils.getLeafAnnotations(null, Util.class)).doesThrow(NullPointerException.class);
	}

	@Test
	void testGetLeafAnnotationsWithSeedNull()
	{
		Verify.that(() ->
			AnnotationUtils.getLeafAnnotations(this.getClass(), null)).doesThrow(NullPointerException.class);
	}

	@Test
	void testGetLeafAnnotationsOnThis()
	{
		Verify.that(AnnotationUtils.getLeafAnnotations(this.getClass(), Util.class)).isNotEmpty();
	}

	@Test
	void testGetLeafAnnotationsOnThisWithExample()
	{
		Verify.that(AnnotationUtils.getLeafAnnotations(Ex.class, Leaf.class)).isNotEmpty();
	}

	@Test
	void testGetRootAnnotationsWithHolderNull()
	{
		Verify.that(() -> AnnotationUtils.getRootAnnotation(null, Util.class)).doesThrow(NullPointerException.class);
	}

	@Test
	void testGetRootAnnotationsWithSeedNull()
	{
		Verify.that(() ->
			AnnotationUtils.getRootAnnotation(this.getClass(), null)).doesThrow(NullPointerException.class);
	}

	@Test
	void testGetRootAnnotationsOnExWithExample()
	{
		Verify.that(AnnotationUtils.getRootAnnotation(Ex.class, Example.class)).isNotNull();
	}

	@Test
	void testGetRootAnnotationsOnExWithUtil()
	{
		Verify.that(AnnotationUtils.getRootAnnotation(Ex.class, Util.class)).isNull();
	}

	@Test
	void testGetRootAnnotationsOnExWithTinyLeaf()
	{
		Verify.that(AnnotationUtils.getRootAnnotation(Ex.class, TinyLeaf.class)).isNotNull();
	}

	@Example
	private class Ex
	{
		
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.ANNOTATION_TYPE)
	private @interface TinyLeaf { }

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.ANNOTATION_TYPE)
	@TinyLeaf
	private @interface Leaf { }

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Leaf
	private @interface Example { }

}