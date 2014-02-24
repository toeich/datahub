package de.jworks.datahub.transform.editors.transformation.editparts;

import org.eclipse.draw2d.IFigure;

import de.jworks.datahub.business.transform.entity.Filter;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.figures.FilterFigure;

public class FilterEditPart extends TransformationComponentEditPart {

	public FilterEditPart(Filter model, Transformation context) {
		super(model, context);
	}
	
	public Filter getFilter() {
		return (Filter) getModel();
	}
	
	@Override
	protected IFigure createFigure() {
		return new FilterFigure(getFilter().getLabel(), getInputSpecs(), getOutputSpecs());
	}
}