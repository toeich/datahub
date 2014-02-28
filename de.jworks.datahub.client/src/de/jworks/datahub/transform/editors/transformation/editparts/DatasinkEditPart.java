package de.jworks.datahub.transform.editors.transformation.editparts;

import org.eclipse.draw2d.IFigure;

import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.figures.DatasinkFigure;

public class DatasinkEditPart extends ComponentEditPart {

	public DatasinkEditPart(Datasink model, Transformation context) {
		super(model, context);
	}
	
	@Override
	public Datasink getModel() {
		return (Datasink) super.getModel();
	}
	
	@Override
	public DatasinkFigure getFigure() {
		return (DatasinkFigure) super.getFigure();
	}
	
	@Override
	protected IFigure createFigure() {
		return new DatasinkFigure(getModel().getLabel(), getInputSpecs(), getOutputSpecs());
	}
	
}