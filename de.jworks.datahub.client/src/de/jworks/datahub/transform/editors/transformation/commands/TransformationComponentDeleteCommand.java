package de.jworks.datahub.transform.editors.transformation.commands;

import org.eclipse.gef.commands.Command;

import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationComponent;

public class TransformationComponentDeleteCommand extends Command {

	private TransformationComponent component;
	private Transformation transformation;

	public void setComponent(TransformationComponent component) {
		this.component = component;
	}
	
	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}
	
	@Override
	public void execute() {
		transformation.getDefinition().removeComponent(component);
	}

}
