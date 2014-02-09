package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Operation extends TransformationComponent {

	private static final long serialVersionUID = 1L;
	
    @XmlAttribute
    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String xpathFunction) {
        firePropertyChange("operator", this.operator, this.operator = xpathFunction);
    }
}