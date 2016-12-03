package com.ulfric.commons.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@DisplayName("Conversion Service")
@RunWith(JUnitPlatform.class)
class ConversionServiceTest {

	private ConversionService service;

	@BeforeEach
	void init()
	{
		this.service = ConversionService.newInstance();
	}

	@Test
	void testNewInstanceNotNull()
	{
		Verify.that(this.service).isNotNull();
	}

	@Test
	void testNewInstanceIsUnique()
	{
		Verify.that(ConversionService::newInstance).producesUniqueValues();
	}

	@Test
	void testConvertSame()
	{
		Object sample = new Object();
		Verify.that(this.service.convert(sample).to(Object.class)).isSameAs(sample);
	}

	@Test
	void testConvertToString()
	{
		Integer sample = 5;
		Verify.that(this.service.convert(sample).to(String.class)).isEqualTo(sample.toString());
	}

	@Test
	void testConvertMultiOfSameTypeToString()
	{
		Integer sample1 = 1;
		Integer sample2 = 2;
		Verify.that(this.service.convert(sample1, sample2).to(String.class)).isEqualTo("[1, 2]");
	}

	@Test
	void testConvertMultiOfDifferentTypesToString()
	{
		Integer sample1 = 1;
		Double sample2 = 2D;
		Verify.that(this.service.convert(sample1, sample2).to(String.class)).isEqualTo("[1, 2.0]");
	}

	@Test
	void testConvertSubclassToSuperclass()
	{
		Integer sample = 5;
		Verify.that(this.service.convert(sample).to(Number.class)).isSameAs(sample);
	}

}