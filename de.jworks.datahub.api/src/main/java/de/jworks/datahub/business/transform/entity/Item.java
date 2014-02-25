package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({Input.class, Output.class})
public abstract class Item extends Notifier {

	private static final long serialVersionUID = 1L;
	
    @XmlAttribute
    protected String name;
    
    @XmlAttribute
    protected ItemType type;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        firePropertyChange("name", this.name, this.name = name);
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        firePropertyChange("type", this.type, this.type = type);
    }

	@Override
	public String toString() {
		return "Item [name=" + name + ", type=" + type + "]";
	}
    
}
