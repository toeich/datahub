package de.jworks.datahub.business.transform.boundary;

import java.util.List;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.caucho.hessian.server.HessianServlet;

import de.jworks.datahub.business.transform.boundary.TransformationService;
import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Transformation;

@Alternative
public class TransformationServiceHessian extends HessianServlet implements TransformationService {

	@Inject
	TransformationService transformationService;

	@Override
	public List<Transformation> getTransformations() {
		return transformationService.getTransformations();
	}

	@Override
	public Transformation getTransformation(long transformationId) {
		return transformationService.getTransformation(transformationId);
	}
	
	@Override
	public List<Transformation> getQueries() {
		return transformationService.getQueries();
	}

	@Override
	public void addTransformation(Transformation transformation) {
		transformationService.addTransformation(transformation);
	}

	@Override
	public void updateTransformation(Transformation transformation) {
		transformationService.updateTransformation(transformation);
	}

	@Override
	public void removeTransformation(Transformation transformation) {
		transformationService.removeTransformation(transformation);
	}

    @Override
    public List<Datasource> getDatasources() {
        return transformationService.getDatasources();
    }

    @Override
    public Datasource findDatasourceByName(String name) {
        return transformationService.findDatasourceByName(name);
    }

    @Override
    public List<Datasink> getDatasinks() {
        return transformationService.getDatasinks();
    }

    @Override
    public Datasink findDatasinkByName(String name) {
        return transformationService.findDatasinkByName(name);
    }

}
