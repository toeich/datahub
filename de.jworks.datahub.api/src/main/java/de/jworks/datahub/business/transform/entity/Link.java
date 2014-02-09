package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Link extends Notifier {

	private static final long serialVersionUID = 1L;
	
    @XmlAttribute
    private String source;
    
    @XmlAttribute
    private String target;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        firePropertyChange("source", this.source, this.source = source);
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        firePropertyChange("target", this.target, this.target = target);
    }

    @Override
    public String toString() {
        return "Link [source=" + source + ", target=" + target + "]";
    }
}
