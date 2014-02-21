package de.jworks.datahub.business.transform.controller;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import de.jworks.datahub.business.datasets.boundary.DatasetService;
import de.jworks.datahub.business.datasets.entity.Dataset;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.business.transform.boundary.TransformationService;
import de.jworks.datahub.business.transform.entity.Transformation;

@Stateless
@Named
public class TransformURIResolver implements URIResolver {

	@PersistenceContext
	EntityManager entityManager;
	
	@Inject
	TransformationService transformationService;
	
	@Inject
	DatasetService datasetService;

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

			Transformation transformation = transformationService.getTransformation(transformationId);

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
		long datasetGroupId = Long.parseLong(path);
		
		DatasetGroup datasetGroup = datasetService.getDatasetGroup(datasetGroupId);
		List<Dataset> datasets = datasetService.getDatasets(datasetGroup);
		
		final Iterator<Dataset> iterator = datasets.iterator();

		return new StreamSource(new Reader() {
			
			private StringBuffer buffer = new StringBuffer();
			
			private boolean startAdded = false;
			private boolean endAdded = false;
			
			@Override
			public int read(char[] cbuf, int off, int len) throws IOException {
				if (!startAdded) {
					buffer.append("<result>");
					startAdded = true;
				}
				while (buffer.length() < len && iterator.hasNext()) {
					Dataset dataset = iterator.next();
					String content = dataset.getContent();
					if (content.startsWith("<?")) {
						content = content.substring(content.indexOf("?>") + 2);
					}
					buffer.append(content);
				}
				if (!iterator.hasNext() && !endAdded) {
					buffer.append("</result>");
					endAdded = true;
				}
				if (buffer.length() == 0) return -1;
				int length = Math.min(buffer.length(), len);
				buffer.getChars(0, length, cbuf, off);
				buffer.delete(0, length);
				return length;
			}
			
			@Override
			public void close() throws IOException {
			}
		});
	}
	
}