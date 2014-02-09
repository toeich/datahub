package de.jworks.datahub.transform.editors.transformation.editpolicies;

import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import de.jworks.datahub.transform.editors.transformation.figures.LinkFigure;

public class LinkConnectionEndpointsEditPolicy extends ConnectionEndpointEditPolicy {

	@Override
	protected void addSelectionHandles() {
		super.addSelectionHandles();
		getLinkFigure().setLineWidth(2);
	}
	
	@Override
	protected void removeSelectionHandles() {
		super.removeSelectionHandles();
		getLinkFigure().setLineWidth(0);
	}
	
	private LinkFigure getLinkFigure() {
		return (LinkFigure) getHostFigure();
	}
	
}
