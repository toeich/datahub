package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Function extends TransformationComponent {

	private static final long serialVersionUID = 1L;
	
    @XmlAttribute
    private String xpathFunction;

    public String getXpathFunction() {
        return xpathFunction;
    }

    public void setXpathFunction(String xpathFunction) {
        firePropertyChange("xpathFunction", this.xpathFunction, this.xpathFunction = xpathFunction);
    }
}
