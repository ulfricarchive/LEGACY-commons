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
		return new TestBean(3, 4, new int[] { 5, 6, 7 });
	}

	@Test
	void testBeanEquals_againstSelf()
	{
		Verify.that(this.bean.equals(this.bean)).isTrue();
		Verify.that(this.bean).isEqualTo(this.bean);
	}

	@Test
	void testBeanEquals_againstCopy()
	{
		Verify.that(this.bean.equals(this.createBean())).isTrue();
		Verify.that(this.bean).isEqualTo(this.createBean());
	}

	@Test
	void testBeanEquals_againstOtherClass()
	{
		Verify.that(this.bean.equals(this)).isFalse();
		Verify.that(this.bean).isNotEqualTo(this);
	}

	@Test
	void testBeanEquals_againstNull()
	{
		Verify.that(this.bean.equals(null)).isFalse();
		Verify.that(this.bean).isNotEqualTo(null);
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
	void testBeanToString_againstSelf()
	{
		Verify.that(this.bean.toString()).isEqualTo(this.bean.toString());
	}

	@Test
	void testBeanToString_againstCopy()
	{
		Verify.that(this.bean.toString()).isEqualTo(this.createBean().toString());
	}

	@SuppressWarnings({"unused", "serial"})
	private static class TestBean extends Bean
	{
		private final int foo;
		private int bar;
		public int[] baz;
		public Bean nullValue = null;

		public TestBean(int foo, int bar, int[] baz)
		{
			this.foo = foo;
			this.bar = bar;
			this.baz = baz;
		}
	}

}
