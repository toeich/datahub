package de.jworks.datahub.business.documents.entity;

public class ColumnDefinition {
	
	private String name;
	
	private String format;

	public ColumnDefinition() {
	}

	public ColumnDefinition(String name, String format) {
		this.name = name;
		this.format = format;
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

}
