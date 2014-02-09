package de.jworks.datahub.transform.editors.transformation.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.jworks.datahub.transform.editors.transformation.commands.TransformationComponentDeleteCommand;
import de.jworks.datahub.transform.editors.transformation.editparts.TransformationComponentEditPart;

public class TransformationComponentComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		TransformationComponentDeleteCommand command = new TransformationComponentDeleteCommand();
		command.setComponent(getTransformationComponentEditPart().getModel());
		command.setTransformation(getTransformationComponentEditPart().getContext());
		return command;
	}
	
	private TransformationComponentEditPart getTransformationComponentEditPart() {
		return (TransformationComponentEditPart) getHost();
	}
	
}
