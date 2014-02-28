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
	protected String id;
	
	@XmlAttribute
	protected String label;
	
    @XmlAttribute
	protected int[] location = { 100, 100 };

	@XmlElement
	protected ComponentSchema schema;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		firePropertyChange("id", this.id, this.id = id);
	}

	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		firePropertyChange("label", this.label, this.label = label);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
