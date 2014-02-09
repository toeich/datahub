package de.jworks.datahub.transform.editors.transformation.editparts;

import org.eclipse.draw2d.IFigure;

import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.figures.DatasourceFigure;

public class DatasourceEditPart extends ComponentEditPart {

	public DatasourceEditPart(Datasource model, Transformation context) {
		super(model, context); 
	}
	
	@Override
	public Datasource getModel() {
		return (Datasource) super.getModel();
	}

	@Override
	public DatasourceFigure getFigure() {
		return (DatasourceFigure) super.getFigure();
	}
	
	@Override
	protected IFigure createFigure() {
		return new DatasourceFigure(getModel().getName(), getInputSpecs(), getOutputSpecs());
	}
}