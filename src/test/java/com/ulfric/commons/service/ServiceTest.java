package com.ulfric.commons.service;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.naming.Name;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class ServiceTest {

	@Test
	void testGetName_UnnamedTestService()
	{
		Service service = new UnnamedTestService();
		Verify.that(service.getName()).isEqualTo("UnnamedTest");
	}

	@Test
	void testGetName_UnnamedTest()
	{
		Service service = new UnnamedTest();
		Verify.that(service.getName()).isEqualTo("UnnamedTest");
	}

	@Test
	void testGetName_NamedTestService()
	{
		Service service = new NamedTestService();
		Verify.that(service.getName()).isEqualTo("Hello");
	}

	static class UnnamedTestService implements Service
	{
		
	}

	static class UnnamedTest implements Service
	{
		
	}

	@Name("Hello")
	static class NamedTestService implements Service
	{
		
	}

}