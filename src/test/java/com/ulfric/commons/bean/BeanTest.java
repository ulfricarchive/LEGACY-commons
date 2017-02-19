package com.ulfric.commons.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class BeanTest {

	private TestBean bean;

	@BeforeEach
	void init()
	{
		this.bean = this.createBean();
	}

	private TestBean createBean()
	{
		return new TestBean(3, 4, new int[]{ 5, 6, 7 });
	}

	@Test
	void testBeanEquals_againstSelf()
	{
		Verify.that(this.bean).isEqualTo(this.bean);
	}

	@Test
	void testBeanEquals_againstCopy()
	{
		Verify.that(this.bean).isEqualTo(this.createBean());
	}

	@Test
	void testBeanEquals_againstClone()
	{
		Verify.that(this.bean).isEqualTo(this.bean.clone());
	}

	@Test
	void testBeanHashcode_againstSelf()
	{
		Verify.that(this.bean.hashCode()).isEqualTo(this.bean.hashCode());
	}

	@Test
	void testBeanHashcode_againstCopy()
	{
		Verify.that(this.bean.hashCode()).isEqualTo(this.createBean().hashCode());
	}

	@Test
	void testBeanHashcode_againstClone()
	{
		Verify.that(this.bean.hashCode()).isEqualTo(this.bean.clone().hashCode());
	}

	@Test
	void testBeanToString_againstSelf()
	{
		Verify.that(this.bean.toString()).isEqualTo(this.bean.toString());
	}

	@Test
	void testBeanToString_againstCopy()
	{
		Verify.that(this.bean.toString()).isEqualTo(this.createBean().toString());
	}

	@Test
	void testBeanToString_againstClone()
	{
		Verify.that(this.bean.toString()).isEqualTo(this.bean.clone().toString());
	}

	private static class TestBean extends Bean
	{
		private final int foo;
		private int bar;
		public int[] baz;

		public TestBean(int foo, int bar, int[] baz)
		{
			this.foo = foo;
			this.bar = bar;
			this.baz = baz;
		}
	}

}
