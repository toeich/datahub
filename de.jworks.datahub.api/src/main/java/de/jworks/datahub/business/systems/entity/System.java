package de.jworks.datahub.business.systems.entity;

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
public class System {

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
	
	private transient SystemSchema schema;

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

	public SystemSchema getSchema() {
		if (schema == null) {
			try {
				StringReader stringReader = new StringReader(schemaData);
				schema = JAXB.unmarshal(stringReader, SystemSchema.class);
				schema.resolve();
			} catch (Exception e) {
				schema = new SystemSchema();
			}
		}
		return schema;
	}
	
	public SystemSchema getSchemaDetached() {
		updateData();
		try {
			StringReader stringReader = new StringReader(schemaData);
			SystemSchema schema = JAXB.unmarshal(stringReader, SystemSchema.class);
			schema.resolve();
			return schema;
		} catch (Exception e) {
			return new SystemSchema();
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
