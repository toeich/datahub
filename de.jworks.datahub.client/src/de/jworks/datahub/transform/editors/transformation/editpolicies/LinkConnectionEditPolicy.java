package de.jworks.datahub.transform.editors.transformation.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.jworks.datahub.transform.editors.transformation.commands.LinkDeleteCommand;
import de.jworks.datahub.transform.editors.transformation.editparts.LinkEditPart;

public class LinkConnectionEditPolicy extends ConnectionEditPolicy {
	
	@Override
	protected Command getDeleteCommand(GroupRequest request) {
		LinkDeleteCommand command = new LinkDeleteCommand();
		command.setTransformation(getLinkEditPart().getTransformation());
		command.setLink(getLinkEditPart().getModel());
		return command;
	}
	
	private LinkEditPart getLinkEditPart() {
		return (LinkEditPart) getHost();
	}
	
}
