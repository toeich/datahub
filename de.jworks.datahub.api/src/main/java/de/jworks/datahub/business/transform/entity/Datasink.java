package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Datasink extends Component {

	private static final long serialVersionUID = 1L;
	
	@XmlElement
	protected String routeSpec;

    public String getRouteSpec() {
        return routeSpec;
    }

    public void setRouteSpec(String routeSpec) {
        this.routeSpec = routeSpec;
    }
    
    @Override
    public String toString() {
        return "Datasink [id=" + id + "]";
    }
}
