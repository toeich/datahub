package de.jworks.datahub.business.transform.boundary;

import java.util.List;

import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Transformation;

public interface TransformationService {

	public List<Transformation> getTransformations();
	
	public Transformation getTransformation(long transformationId);
	
	public List<Transformation> getQueries();
	
	public void addTransformation(Transformation transformation);

	public void updateTransformation(Transformation transformation);
	
	public void removeTransformation(Transformation transformation);
	
    public List<Datasource> getDatasources();

    public Datasource findDatasourceByName(String name);

    public List<Datasink> getDatasinks();

    public Datasink findDatasinkByName(String name);

}