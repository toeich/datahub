package de.jworks.datahub.transform.editors.transformation.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationDefinition;
import de.jworks.datahub.transform.editors.transformation.editpolicies.TransformationDefinitionLayoutEditPoliy;
import de.jworks.datahub.transform.editors.transformation.figures.TransformationDefinitionFigure;

public class TransformationDefinitionEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

	private Transformation transformation;

	public TransformationDefinitionEditPart(TransformationDefinition model, Transformation transformation) {
		this.transformation = transformation;
		setModel(model);
	}

	public Transformation getTransformation() {
		return transformation;
	}
	
	@Override
	public TransformationDefinition getModel() {
		return (TransformationDefinition) super.getModel();
	}
	
	@Override
	protected List<?> getModelChildren() {
		List<Object> children = new ArrayList<Object>();
		children.add(getModel().getDatasource());
		children.add(getModel().getDatasink());
		children.addAll(getModel().getComponents());
		return children;
	}
	
	@Override
	protected IFigure createFigure() {
		return new TransformationDefinitionFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new TransformationDefinitionLayoutEditPoliy());
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
		for (Object child : getChildren()) {
			((EditPart) child).refresh();
		}
	}
	
}
