package de.jworks.datahub.transform.editors.transformation.editparts;

import org.eclipse.draw2d.IFigure;

import de.jworks.datahub.business.transform.entity.Lookup;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.figures.LookupFigure;

public class LookupEditPart extends TransformationComponentEditPart {

	public LookupEditPart(Lookup model, Transformation context) {
		super(model, context);
	}

	@Override
	protected IFigure createFigure() {
		return new LookupFigure(getModel().getName(), getInputSpecs(), getOutputSpecs());
	}
}