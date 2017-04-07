package com.ulfric.commons.io;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.ulfric.verify.Verify;

@RunWith(JUnitPlatform.class)
public class YamlFilterTest {

	@Test
	void testTest_yml_isYaml()
	{
		Verify.that(YamlFilter.INSTANCE.test(Paths.get(".yml"))).isTrue();
	}

	@Test
	void testTest_yaml_isYaml()
	{
		Verify.that(YamlFilter.INSTANCE.test(Paths.get(".yaml"))).isTrue();
	}

	@Test
	void testTest_xml_isNotYaml()
	{
		Verify.that(YamlFilter.INSTANCE.test(Paths.get(".xml"))).isFalse();
	}

	@Test
	void testTest_json_isNotYaml()
	{
		Verify.that(YamlFilter.INSTANCE.test(Paths.get(".json"))).isFalse();
	}

	@Test
	void testTest_html_isNotYaml()
	{
		Verify.that(YamlFilter.INSTANCE.test(Paths.get(".html"))).isFalse();
	}

}