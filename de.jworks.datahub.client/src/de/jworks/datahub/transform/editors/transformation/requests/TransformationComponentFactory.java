package de.jworks.datahub.transform.editors.transformation.requests;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

import org.eclipse.gef.requests.CreationFactory;

import de.jworks.datahub.business.transform.entity.TransformationComponent;

public class TransformationComponentFactory implements CreationFactory {

	public static long nextId = 1;
	
	private TransformationComponent prototype;
	
	public TransformationComponentFactory(TransformationComponent prototype) {
		this.prototype = prototype;
	}

	@Override
	public Object getObjectType() {
		return TransformationComponent.class;
	}

	@Override
	public Object getNewObject() {
		TransformationComponent component = copy(prototype);
		component.setName(component.getName() + "." + nextId++);
		return component;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T copy(T prototype) {
		StringWriter writer = new StringWriter();
		JAXB.marshal(prototype, writer);
		StringReader reader = new StringReader(writer.toString());
		T copy = (T) JAXB.unmarshal(reader, prototype.getClass());
		return copy;
	}

}
