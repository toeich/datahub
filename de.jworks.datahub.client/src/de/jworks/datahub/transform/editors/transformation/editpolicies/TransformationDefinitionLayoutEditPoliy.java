package de.jworks.datahub.transform.editors.transformation.editpolicies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.jworks.datahub.business.transform.entity.Component;
import de.jworks.datahub.business.transform.entity.TransformationComponent;
import de.jworks.datahub.transform.editors.transformation.commands.ComponentMoveCommand;
import de.jworks.datahub.transform.editors.transformation.commands.TransformationComponentAddCommand;
import de.jworks.datahub.transform.editors.transformation.editparts.TransformationDefinitionEditPart;

public class TransformationDefinitionLayoutEditPoliy extends XYLayoutEditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request.getNewObjectType() == TransformationComponent.class) {
			TransformationComponentAddCommand command = new TransformationComponentAddCommand();
			command.setTransformation(getTransformationDefinitionEditPart().getTransformation());
			command.setComponent((TransformationComponent) request.getNewObject());
			command.setLocation(request.getLocation());
			return command;
		}
		return null;
	}

	@Override
	protected Command createChangeConstraintCommand(ChangeBoundsRequest request, EditPart child, Object constraint) {
		if (child.getModel() instanceof Component) {
			ComponentMoveCommand command = new ComponentMoveCommand();
			command.setComponent((Component) child.getModel());
			command.setLocation(((Rectangle) constraint).getLocation());
			return command;
		}
		return null;
	}
	
	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new NonResizableEditPolicy();
	}
	
	private TransformationDefinitionEditPart getTransformationDefinitionEditPart() {
		return (TransformationDefinitionEditPart) getHost();
	}
	
}
