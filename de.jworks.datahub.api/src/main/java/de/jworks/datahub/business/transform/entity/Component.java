package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ Datasource.class, Datasink.class, TransformationComponent.class })
public abstract class Component extends Notifier {

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	protected String name;
	
    @XmlAttribute
	protected int[] location = { 100, 100 };

	@XmlElement
	protected ComponentSchema schema;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		firePropertyChange("name", this.name, this.name = name);
	}

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        firePropertyChange("location", this.location, this.location = location);
    }

	public ComponentSchema getSchema() {
		if (schema == null) {
			schema = new ComponentSchema();
		}
		return schema;
	}

	public void setSchema(ComponentSchema schema) {
		firePropertyChange("schema", this.schema, this.schema = schema);
	}

    public void resolve() {
    	getSchema().resolve();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
