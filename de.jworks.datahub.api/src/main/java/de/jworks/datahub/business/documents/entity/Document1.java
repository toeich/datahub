package de.jworks.datahub.business.documents.entity;

import java.io.Serializable;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Document1 implements Serializable {

	@Id
	@GeneratedValue
	private Long id = new Random().nextLong();

	@Lob
	private String content = "<xml/>";

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
