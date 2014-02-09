package de.jworks.datahub.transform.editors.transformation.editparts;

import org.eclipse.gef.EditPolicy;

import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationComponent;
import de.jworks.datahub.transform.editors.transformation.editpolicies.TransformationComponentComponentEditPolicy;

public abstract class TransformationComponentEditPart extends ComponentEditPart {

	public TransformationComponentEditPart(TransformationComponent model, Transformation context) {
		super(model, context);
	}
	
	@Override
	public TransformationComponent getModel() {
		return (TransformationComponent) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new TransformationComponentComponentEditPolicy());
	}
}