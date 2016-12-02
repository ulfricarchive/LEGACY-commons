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
	void testConvert()
	{
		Verify.that(() -> this.service.convert(new Object(), Integer.class)).doesThrow(UnsupportedOperationException.class);
	}

}