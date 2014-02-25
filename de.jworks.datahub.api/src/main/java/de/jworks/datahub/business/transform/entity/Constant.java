package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Constant extends TransformationComponent {

	private static final long serialVersionUID = 1L;
	
    @XmlAttribute
    private String value;
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        firePropertyChange("value", this.value, this.value = value);
    }

    @Override
    public ComponentSchema getSchema() {
    	if (schema == null) {
    		schema = new ComponentSchema();
    		schema.addOutput(new Output("value", null));
    	}
    	return schema;
    }
    
    @Override
    public String toString() {
        return "Constant [name=" + name + ", value=" + value + "]";
    }
    
}
