package de.jworks.datahub.business.datasets.entity;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.StringWriter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

@Entity
public class Dataset implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private DatasetGroup group;

	@Lob
	private String content;
	
	private transient Document document;

	public Long getId() {
		return id;
	}

	public DatasetGroup getGroup() {
		return group;
	}

	public void setGroup(DatasetGroup group) {
		this.group = group;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Document getDocument() {
		if (document == null) {
			try {
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				if (content == null) {
					content = "<" + group.getSchema().getRootElement().getName() + "/>";
				}
				document = documentBuilder.parse(new ByteArrayInputStream(content.getBytes()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return document;
	}
	
	@PrePersist
	public void updateData() {
		if (document != null) {
			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				StringWriter stringWriter = new StringWriter();
				transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
				content = stringWriter.toString();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}
	}
	
}
