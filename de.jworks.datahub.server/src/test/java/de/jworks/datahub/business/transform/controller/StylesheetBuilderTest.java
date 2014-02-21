package de.jworks.datahub.business.transform.controller;

import java.io.InputStream;

import javax.xml.bind.JAXB;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import static org.custommonkey.xmlunit.XMLAssert.*;

import de.jworks.datahub.business.transform.entity.TransformationDefinition;

public class StylesheetBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBuildStylesheetTransformationDefinition1() throws Exception {
		String expected = IOUtils.toString(getResource("/query1.transformation.result"));
		TransformationDefinition transformationDefinition = JAXB.unmarshal(getResource("/query1.transformation"), TransformationDefinition.class);
		String actual = StylesheetBuilder.buildStylesheet(transformationDefinition);
		assertXMLEqual(expected, actual);
	}

	@Test
	public void testBuildStylesheetTransformationDefinition2() throws Exception {
		String expected = IOUtils.toString(getResource("/query2.transformation.result"));
		TransformationDefinition transformationDefinition = JAXB.unmarshal(getResource("/query2.transformation"), TransformationDefinition.class);
		String actual = StylesheetBuilder.buildStylesheet(transformationDefinition);
		assertXMLEqual(expected, actual);
	}

	private InputStream getResource(String name) {
		return StylesheetBuilderTest.class.getResourceAsStream(name);
	}

}
