package de.jworks.datahub.transform.editors.transformation.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationComponent;

public class TransformationComponentAddCommand extends Command {

	private Transformation transformation;
	
	private TransformationComponent component;
	
	private Point location;

	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}

	public void setComponent(TransformationComponent component) {
		this.component = component;
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}
	
	@Override
	public void execute() {
		component.setLocation(new int[] { location.x, location.y });
		transformation.getDefinition().addComponent(component);
	}

}
