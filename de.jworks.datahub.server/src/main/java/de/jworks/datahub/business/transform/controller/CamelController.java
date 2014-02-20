package de.jworks.datahub.business.transform.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Asynchronous;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.camel.cdi.CdiCamelContext;
import org.apache.camel.model.RoutesDefinition;
import org.slf4j.LoggerFactory;

import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationType;

public class CamelController {
	
	@Inject
	Logger logger;
	
	@Inject
	EntityManager entityManager;
	
	@Inject
	Instance<CdiCamelContext> camelContexts;

	@Asynchronous
	public void updateCamelContext() {
		try {
			CdiCamelContext camelContext = camelContexts.iterator().next();
			
			String routesDefinition = buildRoutesDefinition();

			logger.info("new camel routes:\n" + routesDefinition);
			
			try {
				camelContext.stop();
				camelContext.start();
			} catch (Exception ex) {
				camelContext = null;
				throw ex;
			}
			
			InputStream inputStream = new ByteArrayInputStream(routesDefinition.getBytes("UTF-8"));
			RoutesDefinition routes = camelContext.loadRoutesDefinition(inputStream);
			camelContext.addRouteDefinitions(routes.getRoutes());

		} catch (Exception e) {
			LoggerFactory.getLogger(CamelController.class).error(e.getMessage(), e);
		}
	}

	private String buildRoutesDefinition() {
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n");
		builder.append("<routes xmlns='http://camel.apache.org/schema/spring'>\n\n");

		List<Transformation> transformations = entityManager.createQuery(
				"SELECT t FROM Transformation t WHERE t.type in :types", Transformation.class)
				.setParameter("types", Arrays.asList(TransformationType.Import, TransformationType.ExternalDataflow))
				.getResultList();
		Set<Datasource> datasources = new HashSet<Datasource>();
		for (Transformation transformation : transformations) {
			datasources.add(transformation.getDefinition().getDatasource());
		}
		for (Datasource datasource : datasources) {
			builder.append("<route id='datasource-" + datasource.getName() + "'>\n");
			builder.append(datasource.getRouteSpec() + "\n");
			builder.append("<multicast>\n");
			for (Transformation transformation : transformations) {
				if (datasource.equals(transformation.getDefinition().getDatasource())) {
					builder.append("<to uri='direct:transformation-" + transformation.getId() + "'/>\n");
				}
			}
			builder.append("</multicast>\n");
			builder.append("</route>\n\n");
		}

		Set<Datasink> datasinks = new HashSet<Datasink>();
		for (Transformation transformation : transformations) {
			datasinks.add(transformation.getDefinition().getDatasink());
		}
		for (Datasink datasink : datasinks) {
			builder.append("<route id='datasink-" + datasink.getName() + "'>\n");
			builder.append("<from uri='direct:datasink-" + datasink.getName() + "'/>\n");
			builder.append(datasink.getRouteSpec() + "\n");
			builder.append("</route>\n\n");
		}

		for (Transformation transformation : transformations) {
			builder.append("<route id='transformation-" + transformation.getId() + "'>\n");
			builder.append("<from uri='direct:transformation-" + transformation.getId() + "'/>\n");
			builder.append("<to uri='xslt:xslt:" + transformation.getId() + "?uriResolver=transformURIResolver'/>\n");
			builder.append("<to uri='direct:datasink-" + transformation.getDefinition().getDatasink().getName() + "'/>\n");
			builder.append("</route>\n\n");
		}

		builder.append("</routes>\n");

		return builder.toString();
	}
}
