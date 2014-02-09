package de.jworks.datahub.transform.editors.transformation.editpolicies;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import de.jworks.datahub.business.transform.entity.Link;
import de.jworks.datahub.transform.editors.transformation.commands.LinkCreateCommand;
import de.jworks.datahub.transform.editors.transformation.commands.LinkSetSourceCommand;
import de.jworks.datahub.transform.editors.transformation.commands.LinkSetTargetCommand;
import de.jworks.datahub.transform.editors.transformation.editparts.ComponentEditPart;
import de.jworks.datahub.transform.editors.transformation.figures.ComponentFigure;

public class ComponentGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		ConnectionAnchor anchor = getComponentEditPart().getSourceConnectionAnchor(request);
		if (anchor != null) {
			LinkCreateCommand command = new LinkCreateCommand();
			command.setTransformation(getComponentEditPart().getContext());
			command.setSource(getComponentFigure().getUri(anchor));
			request.setStartCommand(command);
			return command;
		}
		return null;
	}

	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		ConnectionAnchor anchor = getComponentEditPart().getTargetConnectionAnchor(request);
		if (anchor != null) {
			LinkCreateCommand command = (LinkCreateCommand) request.getStartCommand();
			command.setTarget(getComponentFigure().getUri(anchor));
			return command;
		}
		return null;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ConnectionAnchor anchor = getComponentEditPart().getSourceConnectionAnchor(request);
		if (anchor != null) {
			LinkSetSourceCommand command = new LinkSetSourceCommand();
			command.setLink((Link) request.getConnectionEditPart().getModel());
			command.setSource(getComponentFigure().getUri(anchor));
			return command;
		}		
		return null;
	}
	
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ConnectionAnchor anchor = getComponentEditPart().getTargetConnectionAnchor(request);
		if (anchor != null) {
			LinkSetTargetCommand command = new LinkSetTargetCommand();
			command.setLink((Link) request.getConnectionEditPart().getModel());
			command.setTarget(getComponentFigure().getUri(anchor));
			return command;
		}		
		return null;
	}

	private ComponentEditPart getComponentEditPart() {
		return (ComponentEditPart) getHost();
	}

	private ComponentFigure getComponentFigure() {
		return (ComponentFigure) getHostFigure();
	}

}
