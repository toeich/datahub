package de.jworks.datahub.transform.editors.transformation.editparts;

import org.eclipse.draw2d.IFigure;

import de.jworks.datahub.business.transform.entity.Constant;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.figures.ConstantFigure;

public class ConstantEditPart extends TransformationComponentEditPart {

	public ConstantEditPart(Constant model, Transformation context) {
		super(model, context);
	}
	
	public Constant getConstant() {
		return (Constant) getModel();
	}
	
	@Override
	protected IFigure createFigure() {
		return new ConstantFigure(getConstant().getLabel(), getInputSpecs(), getOutputSpecs());
	}
	
}