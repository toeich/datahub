package de.jworks.datahub.transform.editors.transformation.editparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.jworks.datahub.business.transform.entity.Constant;
import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Filter;
import de.jworks.datahub.business.transform.entity.Function;
import de.jworks.datahub.business.transform.entity.Link;
import de.jworks.datahub.business.transform.entity.Lookup;
import de.jworks.datahub.business.transform.entity.Operation;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationDefinition;

public class TransformEditPartFactory implements EditPartFactory {
	
	private Transformation transformation;
	
	public TransformEditPartFactory(Transformation transformation) {
		this.transformation = transformation;
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof TransformationDefinition) {
			return new TransformationDefinitionEditPart((TransformationDefinition) model, transformation);
		}
		if (model instanceof Datasource) {
			return new DatasourceEditPart((Datasource) model, transformation);
		}
		if (model instanceof Datasink) {
			return new DatasinkEditPart((Datasink) model, transformation);
		}
		if (model instanceof Constant) {
			return new ConstantEditPart((Constant) model, transformation);
		}
		if (model instanceof Function) {
			return new FunctionEditPart((Function) model, transformation);
		}
		if (model instanceof Operation) {
			return new OperationEditPart((Operation) model, transformation);
		}
		if (model instanceof Filter) {
			return new FilterEditPart((Filter) model, transformation);
		}
		if (model instanceof Lookup) {
			return new LookupEditPart((Lookup) model, transformation);
		}
		if (model instanceof Link) {
			return new LinkEditPart((Link) model, transformation);
		}
		return null;
	}
}