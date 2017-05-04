package com.ulfric.commons.io;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class LangFilterTest {

	@Test
	void testTest_lang_isLang()
	{
		Verify.that(LangFilter.INSTANCE.test(Paths.get(".lang"))).isTrue();
	}

	@Test
	void testTest_xml_isNotLang()
	{
		Verify.that(LangFilter.INSTANCE.test(Paths.get(".xml"))).isFalse();
	}

	@Test
	void testTest_json_isNotLang()
	{
		Verify.that(LangFilter.INSTANCE.test(Paths.get(".json"))).isFalse();
	}

	@Test
	void testTest_html_isNotLang()
	{
		Verify.that(LangFilter.INSTANCE.test(Paths.get(".html"))).isFalse();
	}

}