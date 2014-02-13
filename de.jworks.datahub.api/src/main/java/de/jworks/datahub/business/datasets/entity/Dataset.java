package de.jworks.datahub.business.datasets.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

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
	
}
