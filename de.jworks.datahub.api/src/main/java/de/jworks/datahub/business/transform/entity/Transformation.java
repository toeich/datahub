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
	
	@Lob
	private String definitionData;
	
	@Transient
	private TransformationDefinition definition;

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
	
	public String getDefinitionData() {
		return definitionData;
	}

	public void setDefinitionData(String definitionData) {
		this.definitionData = definitionData;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transformation other = (Transformation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transformation [id=" + id + ", name=" + name + "]";
	}
	
}
