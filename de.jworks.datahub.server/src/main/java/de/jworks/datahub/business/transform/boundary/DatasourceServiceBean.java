package de.jworks.datahub.business.transform.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;

import de.jworks.datahub.business.documents.entity.Attribute;
import de.jworks.datahub.business.documents.entity.DocumentCollection;
import de.jworks.datahub.business.documents.entity.Element;
import de.jworks.datahub.business.systems.entity.System;
import de.jworks.datahub.business.transform.boundary.DatasourceService;
import de.jworks.datahub.business.transform.controller.CamelController;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Output;

@Stateless
public class DatasourceServiceBean implements DatasourceService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    CamelController camelController;

    @Override
    public List<Datasource> getDatasources() {
    	List<Datasource> result = new ArrayList<Datasource>();

    	// collect datasources from systems
    	List<System> systems = entityManager
    			.createQuery("SELECT s FROM System s", System.class)
    			.getResultList();
    	for (System system : systems) {
    		for (Datasource datasource : system.getSchemaDetached().getDatasources()) {
    			datasource.setName(system.getName() + "::" + datasource.getName());
    			result.add(datasource);
    		}
    	}
    	
    	// collect datasources from collections
    	List<DocumentCollection> collections = entityManager
    			.createQuery("SELECT dc FROM DocumentCollection dc", DocumentCollection.class)
    			.getResultList();
    	for (DocumentCollection collection : collections) {
    		result.add(createDatasource(collection));
    	}
    	
        return result;
    }
    
    private Datasource createDatasource(DocumentCollection collection) {
    	Datasource datasource = new Datasource();
    	datasource.setName(collection.getName());
    	datasource.getSchema().addOutput(createOutput(collection.getSchema().getRootElement()));
    	datasource.setRouteSpec("<from uri='file:/home/te/temp/connector-work/collections/" + collection.getName() + "/datasource' />");
    	return datasource;
    }

	private Output createOutput(Element element) {
		Output output = new Output(element.getLabel(), element.getName(), null);
		for (Element e : element.getElements()) {
			output.addOutput(createOutput(e));
		}
		for (Attribute a : element.getAttributes()) {
			output.addOutput(createOutput(a));
		}
		return output;
	}

    private Output createOutput(Attribute attribute) {
    	return new Output(attribute.getLabel(), attribute.getName(), null);
	}

	@Override
    public Datasource findDatasourceByName(String name) {
    	try {
    		if (StringUtils.contains(name, "::")) {
    			// system datasource
    			String systemName = StringUtils.substringBefore(name, "::");
    			String datasourceName = StringUtils.substringAfter(name, "::");
    			System system = entityManager
    					.createQuery("SELECT s FROM System s WHERE s.name = :name", System.class)
    					.setParameter("name", systemName)
    					.getSingleResult();
    			for (Datasource datasource : system.getSchemaDetached().getDatasources()) {
    				if (StringUtils.equals(datasourceName, datasource.getName())) {
    					datasource.setName(system.getName() + "::" + datasource.getName());
    					return datasource;
    				}
    			}
    		} else {
    			// collection datasource
    			DocumentCollection collection = entityManager
    					.createQuery("SELECT dc FROM DocumentCollection dc WHERE dc.name = :name", DocumentCollection.class)
    					.setParameter("name", name)
    					.getSingleResult();
    			return createDatasource(collection);
    		}
    		return null;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    @Override
    public void addDatasouce(Datasource datasource) {
        if (findDatasourceByName(datasource.getName()) != null) {
            throw new RuntimeException();
        }
        
        entityManager.persist(datasource);

        camelController.updateCamelContext();
    }

    @Override
    public void updateDatasource(Datasource datasource) {
        datasource = entityManager.merge(datasource);
        
        entityManager.persist(datasource);
        
        camelController.updateCamelContext();
    }
}
