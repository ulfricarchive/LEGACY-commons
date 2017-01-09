package com.ulfric.commons.service;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.version.Version;
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

	@Test
	void testGetVersion_UnversionedTestService()
	{
		Service service = new UnversionedTestService();
		Verify.that(service.getVersion()).isEqualTo(1);
	}

	@Test
	void testGetVersion_VersionedTestService()
	{
		Service service = new VersionedTestService();
		Verify.that(service.getVersion()).isEqualTo(37);
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

	static class UnversionedTestService implements Service
	{
		
	}

	@Version(37)
	static class VersionedTestService implements Service
	{
		
	}

}