package de.jworks.datahub.business.transform.entity;

import java.io.StringReader;
import java.io.StringWriter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.xml.bind.JAXB;

import de.jworks.datahub.business.common.entity.Project;

@Entity
public class Transformation extends Notifier {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;
	
	private TransformationType type;
	
	@ManyToOne
	private Project project;
	
	@Transient
	private TransformationDefinition definition;

	@Lob
	private String definitionData;

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
	
	public TransformationType getType() {
		return type;
	}

	public void setType(TransformationType type) {
		this.type = type;
	}

	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public TransformationDefinition getDefinition() {
		if (definition == null) {
			try {
				StringReader stringReader = new StringReader(definitionData);
				definition = JAXB.unmarshal(stringReader, TransformationDefinition.class);
			} catch (Exception e) {
				definition = new TransformationDefinition();
			}
		}
		return definition;
	}

	@PrePersist
	public void updateData() {
		if (definition != null) {
			StringWriter stringWriter = new StringWriter();
			JAXB.marshal(definition, stringWriter);
			definitionData = stringWriter.toString();
		}
	}

	@Override
	public String toString() {
		return "Transformation [id=" + id + ", name=" + name + "]";
	}
}
