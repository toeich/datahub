package de.jworks.datahub.business.transform.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Library {

	@XmlElement(name = "group")
	private List<Group> groups = new ArrayList<Group>();
	
	public List<Group> getGroups() {
		return groups;
	}
}
