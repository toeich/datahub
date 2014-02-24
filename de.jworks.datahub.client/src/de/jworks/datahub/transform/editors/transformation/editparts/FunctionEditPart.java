package de.jworks.datahub.transform.editors.transformation.editparts;

import org.eclipse.draw2d.IFigure;

import de.jworks.datahub.business.transform.entity.Function;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.figures.FunctionFigure;

public class FunctionEditPart extends TransformationComponentEditPart {

	public FunctionEditPart(Function model, Transformation context) {
		super(model, context);
	}

	@Override
	protected IFigure createFigure() {
		return new FunctionFigure(getModel().getLabel(), getInputSpecs(), getOutputSpecs());
	}

}
