package de.jworks.datahub.transform.editors.transformation.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

import de.jworks.datahub.business.transform.entity.Link;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.editpolicies.LinkConnectionEditPolicy;
import de.jworks.datahub.transform.editors.transformation.editpolicies.LinkConnectionEndpointsEditPolicy;
import de.jworks.datahub.transform.editors.transformation.figures.LinkFigure;

public class LinkEditPart extends AbstractConnectionEditPart implements PropertyChangeListener {

	private Transformation transformation;
	
	public LinkEditPart(Link model, Transformation transformation) {
		this.transformation = transformation;		
		setModel(model);
	}

	public Link getLink() {
		return (Link) getModel();
	}
	
	public Transformation getTransformation() {
		return transformation;
	};
	
	@Override
	public Link getModel() {
		return (Link) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		return new LinkFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new LinkConnectionEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new LinkConnectionEndpointsEditPolicy());
	}

	@Override
	public void activate() {
		super.activate();
		getModel().addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getModel().removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}

}
