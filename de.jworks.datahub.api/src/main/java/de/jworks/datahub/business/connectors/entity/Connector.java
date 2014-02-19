package de.jworks.datahub.business.connectors.entity;

import java.io.StringReader;
import java.io.StringWriter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.xml.bind.JAXB;

import de.jworks.datahub.business.common.entity.Project;

@Entity
public class Connector {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String name;
	
	private String description;
	
	@ManyToOne
	private Project project;
	
	@Lob
	private String schemaData;
	
	private transient ConnectorSchema schema;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public String getSchemaData() {
		return schemaData;
	}

	public void setSchemaData(String schemaData) {
		this.schemaData = schemaData;
		this.schema = null;
	}

	public ConnectorSchema getSchema() {
		if (schema == null) {
			try {
				StringReader stringReader = new StringReader(schemaData);
				schema = JAXB.unmarshal(stringReader, ConnectorSchema.class);
				schema.resolve();
			} catch (Exception e) {
				schema = new ConnectorSchema();
			}
		}
		return schema;
	}
	
	public ConnectorSchema getSchemaDetached() {
		updateData();
		try {
			StringReader stringReader = new StringReader(schemaData);
			ConnectorSchema schema = JAXB.unmarshal(stringReader, ConnectorSchema.class);
			schema.resolve();
			return schema;
		} catch (Exception e) {
			return new ConnectorSchema();
		}
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
