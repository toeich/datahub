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
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Input;
import de.jworks.datahub.business.transform.entity.ItemType;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationDefinition;

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
	private String transformationData;
	
	private transient Transformation transformation;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTransformationData() {
		return transformationData;
	}

	public void setTransformationData(String transformationData) {
		this.transformationData = transformationData;
	}

	@XmlElement
	public Transformation getTransformation() {
		if (transformation == null) {
			try {
				StringReader stringReader = new StringReader(transformationData);
				transformation = JAXB.unmarshal(stringReader, Transformation.class);
			} catch (Exception e) {
				transformation = new Transformation();
				
				TransformationDefinition definition = transformation.getDefinition();
				
				Datasource datasource = new Datasource();
				datasource.setName("params");
				definition.setDatasource(datasource);
				
				Datasink datasink = new Datasink();
				datasink.setName("result");
				datasink.getSchema().addInput(new Input("result", "result", ItemType.XML_ELEMENT));
				definition.setDatasink(datasink);
			}
		}
		return transformation;
	}

	@PrePersist
	public void updateData() {
		if (transformation != null) {
			StringWriter stringWriter = new StringWriter();
			JAXB.marshal(transformation, stringWriter);
			transformationData = stringWriter.toString();
		}
	}
	
}
