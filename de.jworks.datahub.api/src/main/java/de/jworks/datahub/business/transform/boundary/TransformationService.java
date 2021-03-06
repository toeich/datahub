package de.jworks.datahub.business.transform.boundary;

import java.util.List;

import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Lookup;
import de.jworks.datahub.business.transform.entity.Transformation;

public interface TransformationService {

	public List<Transformation> getTransformations();
	
	public Transformation getTransformation(long transformationId);
	
	public List<Transformation> getQueries();
	
	public void addTransformation(Transformation transformation);

	public void updateTransformation(Transformation transformation);
	
	public void removeTransformation(Transformation transformation);
	
    public List<Datasource> getDatasources();

    public Datasource findDatasource(String name);

    public List<Datasink> getDatasinks();

    public Datasink findDatasink(String name);

    public List<Lookup> getLookups();

    public Lookup findLookup(String name);

}