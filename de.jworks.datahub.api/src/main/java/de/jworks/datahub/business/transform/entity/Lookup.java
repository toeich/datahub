package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Lookup extends TransformationComponent {

	private static final long serialVersionUID = 1L;
	
    @XmlAttribute
    private String datasourceSpec;

    public String getDatasourceSpec() {
        return datasourceSpec;
    }

    public void setDatasourceSpec(String datasource) {
        this.datasourceSpec = datasource;
    }
}
