package de.jworks.datahub.business.documents.entity;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.jworks.datahub.business.projects.entity.Project;

@Entity
@XmlRootElement(name = "collection")
@XmlAccessorType(XmlAccessType.NONE)
public class DocumentCollection {

	@Id
	@GeneratedValue
	@XmlAttribute
	private Long id;

	@XmlAttribute
	private String name;
	
	@XmlElement
	private String description;
	
	@ManyToOne
	private Project project;
	
	@Lob
	private String schemaData;
	
	private transient DocumentSchema schema;
	
	@Lob
	private String columnsData;
	
	private transient List<ColumnDefinition> columns;

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
	
	public DocumentSchema getSchema() {
		if (schema == null) {
			try {
				StringReader stringReader = new StringReader(schemaData);
				schema = JAXB.unmarshal(stringReader, DocumentSchema.class);
				schema.updateParents();
			} catch (Exception e) {
				schema = new DocumentSchema();
			}
		}
		return schema;
	}
	
	public List<ColumnDefinition> getColumns() {
		if (columns == null) {
			try {
				columns = new ArrayList<ColumnDefinition>();
				StringReader stringReader = new StringReader(columnsData);
				BufferedReader bufferedReader = new BufferedReader(stringReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					String[] keyValue = line.split("=", 2);
					columns.add(new ColumnDefinition(keyValue[0], keyValue[1]));
				}
			} catch (Exception e) {
				columns = new ArrayList<ColumnDefinition>();
			}
		}
		return columns;
	}
	
	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}
	
	@PrePersist
	public void updateData() {
		if (schema != null) {
			StringWriter stringWriter = new StringWriter();
			JAXB.marshal(schema, stringWriter);
			schemaData = stringWriter.toString();
		}
		if (columns != null) {
			StringWriter stringWriter = new StringWriter();
			for (ColumnDefinition column : columns) {
				stringWriter.write(column.getName()+ "=" + column.getFormat() + "\n");
			}
			columnsData = stringWriter.toString();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
