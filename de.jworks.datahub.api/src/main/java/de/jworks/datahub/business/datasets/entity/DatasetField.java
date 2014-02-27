package de.jworks.datahub.business.datasets.entity;

import javax.persistence.Embeddable;

@Embeddable
public class DatasetField {
	
	private String name;
	
	private String format;
	
	private boolean visible;

	public DatasetField() {
	}

	public DatasetField(String name, String format, boolean visible) {
		this.name = name;
		this.format = format;
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
