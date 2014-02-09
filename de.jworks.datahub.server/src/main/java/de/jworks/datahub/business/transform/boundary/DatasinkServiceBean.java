package de.jworks.datahub.business.transform.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;

import de.jworks.datahub.business.documents.entity.Attribute;
import de.jworks.datahub.business.documents.entity.DatasetGroup;
import de.jworks.datahub.business.documents.entity.Element;
import de.jworks.datahub.business.systems.entity.System;
import de.jworks.datahub.business.transform.boundary.DatasinkService;
import de.jworks.datahub.business.transform.controller.CamelController;
import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Input;

@Stateless
public class DatasinkServiceBean implements DatasinkService {

    @PersistenceContext
    EntityManager entityManager;
    
    @Inject
    CamelController camelController;

    @Override
    public List<Datasink> getDatasinks() {
    	List<Datasink> result = new ArrayList<Datasink>();
    	
    	List<DatasetGroup> collections = entityManager
    			.createQuery("SELECT dc FROM DocumentCollection dc", DatasetGroup.class)
    			.getResultList();
    	for (DatasetGroup collection : collections) {
    		result.add(createDatasink(collection));
    	}
    	
        return result;
    }

    private Datasink createDatasink(DatasetGroup collection) {
    	Datasink datasink = new Datasink();
    	datasink.setName(collection.getName());
    	datasink.getSchema().addInput(createInput(collection.getSchema().getRootElement()));
    	datasink.setRouteSpec("<to uri='file:/home/te/temp/connector-work/collections/" + collection.getName() + "/datasink' />");
    	return datasink;
    }

	private Input createInput(Element element) {
		Input output = new Input(element.getLabel(), element.getName(), null);
		for (Element e : element.getElements()) {
			output.addInput(createInput(e));
		}
		for (Attribute a : element.getAttributes()) {
			output.addInput(createInput(a));
		}
		return output;
	}
    
    private Input createInput(Attribute attribute) {
    	return new Input(attribute.getLabel(), attribute.getName(), null);
	}

    @Override
    public Datasink findDatasinkByName(String name) {
    	try {
    		if (StringUtils.contains(name, "::")) {
    			String systemName = StringUtils.substringBefore(name, "::");
    			String datasinkName = StringUtils.substringAfter(name, "::");
    			System system = entityManager
    					.createQuery("SELECT s FROM System s WHERE s.name = :name", System.class)
    					.setParameter("name", systemName)
    					.getSingleResult();
    			for (Datasink datasink : system.getSchema().getDatasinks()) {
    				if (StringUtils.equals(datasinkName, datasink.getName())) {
    					return datasink;
    				}
    			}
    		} else {
    			DatasetGroup collection = entityManager
    					.createQuery("SELECT dc FROM DocumentCollection dc WHERE dc.name = :name", DatasetGroup.class)
    					.setParameter("name", name)
    					.getSingleResult();
    			return createDatasink(collection);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    @Override
    public void addDatasink(Datasink datasink) {
        if (findDatasinkByName(datasink.getName()) != null) {
            throw new RuntimeException();
        }

        entityManager.persist(datasink);

        camelController.updateCamelContext();
    }

    @Override
    public void updateDatasink(Datasink datasink) {
        datasink = entityManager.merge(datasink);

        entityManager.persist(datasink);

        camelController.updateCamelContext();
    }
}
