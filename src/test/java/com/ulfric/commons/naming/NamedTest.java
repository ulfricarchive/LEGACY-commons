package com.ulfric.commons.naming;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
class NamedTest {

	@Test
	void testGetNameThrowsNameMissingExceptionByDefault()
	{
		Verify.that(() -> new Named() {}.getName()).doesThrow(NameMissingException.class);
	}

	@Test
	void testGetNameReturnsNameAnnotationValue()
	{
		Verify.that(new NamedImpl().getName()).isEqualTo("HelloWorld");
	}

	@Test
	void testGetNameFromAnnotationThrowsNameMissingExceptionByDefault()
	{
		Verify.that(() -> Named.getNameFromAnnotation(new Named() {})).doesThrow(NameMissingException.class);
	}

	@Test
	void testGetNameFromAnnotationReturnsNameAnnotationValue()
	{
		Verify.that(Named.getNameFromAnnotation(new NamedImpl())).isEqualTo("HelloWorld");
	}

	@Name("HelloWorld")
	static final class NamedImpl implements Named { }

}