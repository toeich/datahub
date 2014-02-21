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
    protected String step;
    
    @XmlAttribute
    protected ItemType type;
    
    @XmlAttribute
    protected String valueType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        firePropertyChange("name", this.name, this.name = name);
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        firePropertyChange("path", this.step, this.step = step);
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        firePropertyChange("type", this.type, this.type = type);
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        firePropertyChange("valueType", this.valueType, this.valueType = valueType);
    }

	@Override
	public String toString() {
		return "Item [name=" + name + ", step=" + step + ", type=" + type + "]";
	}
    
}
