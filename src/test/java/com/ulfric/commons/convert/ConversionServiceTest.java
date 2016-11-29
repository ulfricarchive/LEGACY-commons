package com.ulfric.commons.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.google.common.truth.Truth;
import com.ulfric.commons.convert.ConversionService;

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
		Truth.assertThat(this.service).isNotNull();
	}

	@Test
	void testNewInstanceIsUnique()
	{
		Truth.assertThat(this.service).isNotSameAs(ConversionService.newInstance());
	}

	@Test
	void testConvert()
	{
		Assertions.assertThrows(UnsupportedOperationException.class, () -> this.service.convert(new Object(), Integer.class));
	}

}