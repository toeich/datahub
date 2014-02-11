package de.jworks.datahub.business.queries.entity;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
	
	private String schemaData;
	
	private transient QuerySchema schema;

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchemaData() {
		return schemaData;
	}

	public void setSchemaData(String schemaData) {
		this.schemaData = schemaData;
	}

	@XmlElement
	public QuerySchema getSchema() {
		if (schema == null) {
			try {
				StringReader stringReader = new StringReader(schemaData);
				schema = JAXB.unmarshal(stringReader, QuerySchema.class);
			} catch (Exception e) {
				schema = new QuerySchema();
			}
		}
		return schema;
	}

	@PrePersist
	public void updateData() {
		if (schema != null) {
			StringWriter stringWriter = new StringWriter();
			JAXB.marshal(schema, stringWriter);
			schemaData = stringWriter.toString();
		}
	}
	
}
