package de.jworks.datahub.business.datasets.entity;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.jworks.datahub.business.common.entity.Project;

@Entity
@XmlRootElement(name = "collection")
@XmlAccessorType(XmlAccessType.NONE)
public class DatasetGroup {

	@Id
	@GeneratedValue
	@XmlAttribute
	private Long id;

	@XmlAttribute
	private String name;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "locale")
	@Column(name = "name")
	private Map<String, String> localizedNames = new HashMap<String, String>();
	
	@XmlElement
	private String description;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "locale")
	@Column(name = "description")
	private Map<String, String> localizedDescriptions = new HashMap<String, String>();
	
	@OneToMany
	private List<Project> projects = new ArrayList<Project>();
	
	@Lob
	private String schemaData;
	
	private transient DatasetSchema schema;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<DatasetField> fields = new ArrayList<DatasetField>();
	
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

	public List<Project> getProjects() {
		return projects;
	}
	
	public String getSchemaData() {
		return schemaData;
	}
	
	public DatasetSchema getSchema() {
		if (schema == null) {
			try {
				StringReader stringReader = new StringReader(schemaData);
				schema = JAXB.unmarshal(stringReader, DatasetSchema.class);
				schema.updateParents();
			} catch (Exception e) {
				schema = new DatasetSchema();
			}
		}
		return schema;
	}
	
	public List<DatasetField> getFields() {
		return fields;
	}
	
	public void setFields(List<DatasetField> fields) {
		this.fields = fields;
	}
	
	@PrePersist
	public void updateData() {
		if (schema != null) {
			StringWriter stringWriter = new StringWriter();
			JAXB.marshal(schema, stringWriter);
			schemaData = stringWriter.toString();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
