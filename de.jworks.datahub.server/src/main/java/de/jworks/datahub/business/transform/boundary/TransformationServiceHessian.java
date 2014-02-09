package de.jworks.datahub.business.transform.boundary;

import java.util.List;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.caucho.hessian.server.HessianServlet;

import de.jworks.datahub.business.transform.boundary.TransformationService;
import de.jworks.datahub.business.transform.entity.Transformation;

@Alternative
public class TransformationServiceHessian extends HessianServlet implements TransformationService {

	private static final long serialVersionUID = 1L;
	
	@Inject
    TransformationService transformationService;

    @Override
    public List<Transformation> getTransformations() {
        return transformationService.getTransformations();
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
    
}
