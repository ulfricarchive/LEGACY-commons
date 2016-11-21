package com.ulfric.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;
import com.ulfric.commons.api.UtilTestBase;
import com.ulfric.commons.object.Serializability;

class MemberUtilsTest extends UtilTestBase<MemberUtils> {

	private static final Set<String> FIELDS = new HashSet<>();

	@BeforeAll
	static void before()
	{
		for (Field field : FieldUtils.getAllFields(Fields.class))
		{
			MemberUtilsTest.FIELDS.add(field.getName());
		}
	}

	@AfterAll
	static void after()
	{
		Truth.assertThat(MemberUtilsTest.FIELDS).isEmpty();
	}

	@Test
	@DisplayName("getSerializability(null) == NULL")
	void testGetSerializabilityOnNull()
	{
		Truth.assertThat(MemberUtils.getSerializability(null)).isSameAs(Serializability.NULL);
	}

	@Test
	@DisplayName("getSerializability(Object#toString) == SERIALIZABLE")
	void testGetSerializabilityOnObjectClass()
	{
		Method toString = MethodUtils.getDeclaredMethod(Object.class, "toString").get();
		Truth.assertThat(MemberUtils.getSerializability(toString)).isSameAs(Serializability.SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicNormal()
	{
		this.testSerializability("publicNormal", Serializability.SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicTransient()
	{
		this.testSerializability("publicTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateNormal()
	{
		this.testSerializability("privateNormal", Serializability.SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateTransient()
	{
		this.testSerializability("privateTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicFinal()
	{
		this.testSerializability("publicFinal", Serializability.SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicFinalTransient()
	{
		this.testSerializability("publicFinalTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateFinal()
	{
		this.testSerializability("privateFinal", Serializability.SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateFinalTransient()
	{
		this.testSerializability("privateFinalTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicStatic()
	{
		this.testSerializability("publicStatic", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicStaticTransient()
	{
		this.testSerializability("publicStaticTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateStatic()
	{
		this.testSerializability("privateStatic", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateStaticTransient()
	{
		this.testSerializability("privateStaticTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicStaticFinal()
	{
		this.testSerializability("publicStaticFinal", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicStaticFinalTransient()
	{
		this.testSerializability("publicStaticFinalTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateStaticFinal()
	{
		this.testSerializability("privateStaticFinal", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateStaticFinalTransient()
	{
		this.testSerializability("privateStaticFinalTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicVolatile()
	{
		this.testSerializability("publicVolatile", Serializability.SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicVolatileTransient()
	{
		this.testSerializability("publicVolatileTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateVolatile()
	{
		this.testSerializability("privateVolatile", Serializability.SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateVolatileTransient()
	{
		this.testSerializability("privateVolatileTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicStaticVolatile()
	{
		this.testSerializability("publicStaticVolatile", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPublicStaticVolatileTransient()
	{
		this.testSerializability("publicStaticVolatileTransient", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateStaticVolatile()
	{
		this.testSerializability("privateStaticVolatile", Serializability.NOT_SERIALIZABLE);
	}

	@Test
	void testGetSerializabilityOnPrivateStaticVolatileTransient()
	{
		this.testSerializability("privateStaticVolatileTransient", Serializability.NOT_SERIALIZABLE);
	}

	private void testSerializability(String name, Serializability expected)
	{
		Field f = FieldUtils.getDeclaredField(Fields.class, name).get();
		Truth.assertThat(MemberUtils.getSerializability(f)).isSameAs(expected);

		MemberUtilsTest.FIELDS.remove(name);
	}

	@SuppressWarnings("unused")
	private static final class Fields
	{
		public String publicNormal;
		public transient String publicTransient;
		private String privateNormal;
		private transient String privateTransient;

		public final String publicFinal = null;
		public final transient String publicFinalTransient = null;
		private final String privateFinal = null;
		private final transient String privateFinalTransient = null;

		public static String publicStatic;
		public static transient String publicStaticTransient;
		private static String privateStatic;
		private static transient String privateStaticTransient;

		public static final String publicStaticFinal = null;
		public static final transient String publicStaticFinalTransient = null;
		private static final String privateStaticFinal = null;
		private static final transient String privateStaticFinalTransient = null;

		public volatile String publicVolatile;
		public volatile transient String publicVolatileTransient;
		private volatile String privateVolatile;
		private volatile transient String privateVolatileTransient;

		public static volatile String publicStaticVolatile;
		public static volatile transient String publicStaticVolatileTransient;
		private static volatile String privateStaticVolatile;
		private static volatile transient String privateStaticVolatileTransient;
	}

}