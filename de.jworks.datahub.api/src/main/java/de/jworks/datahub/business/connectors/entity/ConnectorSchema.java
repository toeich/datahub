package de.jworks.datahub.business.connectors.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;

@XmlRootElement(name = "system-schema")
@XmlAccessorType(XmlAccessType.NONE)
public class ConnectorSchema {

	@XmlElement(name = "datasource")
	@XmlElementWrapper(name = "datasources")
	private List<Datasource> datasources = new ArrayList<Datasource>();

	@XmlElement(name = "datasink")
	@XmlElementWrapper(name = "datasinks")
	private List<Datasink> datasinks = new ArrayList<Datasink>();

	public List<Datasource> getDatasources() {
		return datasources;
	}

	public void setDatasources(List<Datasource> datasources) {
		this.datasources = datasources;
	}

	public List<Datasink> getDatasinks() {
		return datasinks;
	}
	
	public void setDatasinks(List<Datasink> datasinks) {
		this.datasinks = datasinks;
	}
	
}
