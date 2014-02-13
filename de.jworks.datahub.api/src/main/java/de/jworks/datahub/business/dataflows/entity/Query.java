package de.jworks.datahub.business.dataflows.entity;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.jworks.datahub.business.transform.entity.Datasink;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Query implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@XmlAttribute
	private Long id;

	@XmlAttribute
	private String name;
	
	@Lob
	private String datasinkData;
	
	private transient Datasink datasink;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatasinkData() {
		return datasinkData;
	}

	public void setDatasinkData(String datasinkData) {
		this.datasinkData = datasinkData;
	}

	@XmlElement
	public Datasink getDatasink() {
		if (datasink == null) {
			try {
				StringReader stringReader = new StringReader(datasinkData);
				datasink = JAXB.unmarshal(stringReader, Datasink.class);
			} catch (Exception e) {
				datasink = new Datasink();
			}
		}
		return datasink;
	}

	@PrePersist
	public void updateData() {
		if (datasink != null) {
			StringWriter stringWriter = new StringWriter();
			JAXB.marshal(datasink, stringWriter);
			datasinkData = stringWriter.toString();
		}
	}
	
}
