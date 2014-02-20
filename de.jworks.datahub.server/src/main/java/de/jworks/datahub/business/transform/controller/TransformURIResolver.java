package de.jworks.datahub.business.transform.controller;

import java.io.StringReader;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import de.jworks.datahub.business.transform.entity.Transformation;

@Stateless
@Named
public class TransformURIResolver implements URIResolver {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Source resolve(String href, String base) throws TransformerException {
		String scheme = StringUtils.substringBefore(href, "-");
		String path = StringUtils.substringAfter(href, "-");
		if ("xslt".equals(scheme)) {
			return createXsltSource(path);
		}
		if ("lookup".equals(scheme)) {
			return createLookupSource(path);
		}
		return null;
	}

	private Source createXsltSource(String path) {
		try {
			long transformationId = Long.parseLong(path);

			Transformation transformation = entityManager.find(Transformation.class, transformationId);

			Source stylesheet = StylesheetBuilder.buildStylesheet(transformation);

			return stylesheet;
		} catch (Exception e) {
			LoggerFactory.getLogger(TransformURIResolver.class).error("error generating stylesheet, using identity stylesheet: ", e);

			String stylesheet = 
					"<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n" +
							"<xsl:stylesheet version='2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>\n" +
							" <xsl:template match='@*|node()'>\n" +
							"  <xsl:copy>\n" +
							"   <xsl:apply-templates select='@*|node()'/>\n" +
							"  </xsl:copy>\n" +
							" </xsl:template>\n" +
							"</xsl:stylesheet>\n";

			return new StreamSource(new StringReader(stylesheet));
		}
	}
	
	private Source createLookupSource(String path) {
		return new StreamSource(new StringReader("<customer firstName='test'/>"));
	}
	
}