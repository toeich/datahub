package de.jworks.datahub.business.transform.boundary;

import de.jworks.datahub.business.transform.entity.Transformation;

import java.util.List;

public interface TransformationService {

	public List<Transformation> getTransformations();

	public void addTransformation(Transformation transformation);

	public void updateTransformation(Transformation transformation);
	
	public void removeTransformation(Transformation transformation);
}