package com.ulfric.commons.reflect;

import java.security.CodeSource;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.testing.Util;
import com.ulfric.testing.UtilTestBase;
import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
@Util(CodeSourceUtils.class)
class CodeSourceUtilsTest extends UtilTestBase {

	private final Class<?> clazz = CodeSourceUtils.class;

	@Test
	void testGetCodeSource_UtilClass()
	{
		Verify.that(CodeSourceUtils.getCodeSource(this.clazz))
			.isEqualTo(this.clazz.getProtectionDomain().getCodeSource());
	}

	@Test
	void testGetCodeSource_null_throwsNPE()
	{
		Verify.that(() -> CodeSourceUtils.getCodeSource(null)).doesThrow(NullPointerException.class);
	}

	@Test
	void testGetFileSystem_UtilClass()
	{
		CodeSource source = CodeSourceUtils.getCodeSource(this.clazz);
		Verify.that(CodeSourceUtils.getFileSystem(source)).isNotNull();
	}

	@Test
	void testGetFileSystem_null_throwsNPE()
	{
		Verify.that(() -> CodeSourceUtils.getFileSystem(null)).doesThrow(NullPointerException.class);
	}

	@Test
	void testGetFileSystemFromClass_UtilClass()
	{
		Verify.that(CodeSourceUtils.getFileSystemFromClass(this.clazz)).isNotNull();
	}

	@Test
	void testGetFileSystemFromClass_null_throwsNPE()
	{
		Verify.that(() -> CodeSourceUtils.getFileSystemFromClass(null)).doesThrow(NullPointerException.class);
	}

}