package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.NONE)
public class Filter extends TransformationComponent {

	private static final long serialVersionUID = 1L;

	@Override
	public ComponentSchema getSchema() {
		if (schema == null) {
			schema = new ComponentSchema();
			schema.addInput(new Input("node", ItemType.CONTEXT));
			schema.addInput(new Input("bool", null));
			schema.addOutput(new Output("on-true", null));
			schema.addOutput(new Output("on-false", null));
		}
		return schema;
	}
}
