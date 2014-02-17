package de.jworks.datahub.business.transform.boundary;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.jworks.datahub.business.transform.boundary.TransformationService;
import de.jworks.datahub.business.transform.controller.CamelController;
import de.jworks.datahub.business.transform.entity.Transformation;

@Stateless
public class TransformationServiceBean implements TransformationService {

    @PersistenceContext
    EntityManager entityManager;
    
    @Inject
    CamelController camelController;

    @Override
    public List<Transformation> getTransformations() {
        List<Transformation> transformations = entityManager
                .createQuery("select t from Transformation t", Transformation.class)
                .getResultList();
        return transformations;
    }

    @Override
    public void addTransformation(Transformation transformation) {
        entityManager.persist(transformation);
        camelController.updateCamelContext();
    }

    @Override
    public void updateTransformation(Transformation transformation) {
        entityManager.merge(transformation);
        camelController.updateCamelContext();
    }
    @Override
    public void removeTransformation(Transformation transformation) {
    	entityManager.remove(entityManager.merge(transformation));
    	camelController.updateCamelContext();
    }
    
}
