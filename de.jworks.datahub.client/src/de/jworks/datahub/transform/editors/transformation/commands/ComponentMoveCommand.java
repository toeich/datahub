package de.jworks.datahub.transform.editors.transformation.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import de.jworks.datahub.business.transform.entity.Component;

public class ComponentMoveCommand extends Command {

	private Component component;

	private Point location;

	public void setComponent(Component component) {
		this.component = component;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	@Override
	public void execute() {
		component.setLocation(new int[] { location.x, location.y });
	}

}
