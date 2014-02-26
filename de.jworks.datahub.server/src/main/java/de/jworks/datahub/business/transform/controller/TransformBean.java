package de.jworks.datahub.business.transform.controller;

import javax.inject.Named;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.w3c.dom.Document;

import de.jworks.datahub.business.util.XMLUtil;

@Named
public class TransformBean {
	
	@Handler
	public void process(@Body Exchange exchange) {
		System.out.println("TransformBean.process() datasetGroup=" + exchange.getIn().getHeader("datasetGroup"));
		Document document = XMLUtil.parse(exchange.getIn().getBody(String.class));
		System.out.println(document);
	}

}
