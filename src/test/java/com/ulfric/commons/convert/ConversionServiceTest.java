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
	void testConvertIntegerToString()
	{
		this.registerObjectToStringUmbrealla();
		Integer sample = 5;
		this.service.convert(sample).to(String.class);
	}

	@Test
	void testConvertMultiOfSameTypeToString()
	{
		this.registerObjectToStringUmbrealla();
		Integer sample1 = 1;
		Integer sample2 = 2;
		Verify.that(this.service.convert(sample1, sample2).to(String.class)).isEqualTo("[1, 2]");
	}

	@Test
	void testConvertMultiOfDifferentTypesToString()
	{
		this.registerObjectToStringUmbrealla();
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

	@Test
	void testRegisterStringToInteger()
	{
		this.service.register(Converter.single(String.class, Integer.class, Integer::parseInt));
		String sample = "5";
		Verify.that(this.service.convert(sample).to(Integer.class)).isEqualTo(5);
	}

	@Test
	void testRegisterStringToIntegerAndConvertStringToNumber()
	{
		this.service.register(Converter.single(String.class, Integer.class, Integer::parseInt));
		String sample = "5";
		Verify.that(this.service.convert(sample).to(Number.class)).isEqualTo(5);
	}

	private void registerObjectToStringUmbrealla()
	{
		this.service.register(new Converter<String>(MultiType.of(Object.class))
		{
			@Override
			public String apply(MultiObject from)
			{
				return from.toString();
			}
		});
	}

}