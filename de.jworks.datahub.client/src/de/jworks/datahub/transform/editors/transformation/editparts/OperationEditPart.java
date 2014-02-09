package de.jworks.datahub.transform.editors.transformation.editparts;

import org.eclipse.draw2d.IFigure;

import de.jworks.datahub.business.transform.entity.Operation;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.figures.OperationFigure;

public class OperationEditPart extends TransformationComponentEditPart {

	public OperationEditPart(Operation model, Transformation context) {
		super(model, context);
	}

	@Override
	protected IFigure createFigure() {
		return new OperationFigure(getModel().getName(), getInputSpecs(), getOutputSpecs());
	}
}