package de.jworks.datahub.business.transform.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.connectors.entity.Connector;
import de.jworks.datahub.business.datasets.entity.Attribute;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.business.datasets.entity.Element;
import de.jworks.datahub.business.transform.controller.CamelController;
import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Input;
import de.jworks.datahub.business.transform.entity.ItemType;
import de.jworks.datahub.business.transform.entity.Lookup;
import de.jworks.datahub.business.transform.entity.Output;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationType;

@Stateless
public class TransformationServiceBean implements TransformationService {
	
	@Inject
	Logger logger;

    @Inject
    EntityManager entityManager;
    
    @Inject
    CamelController camelController;

    @Override
    public List<Transformation> getTransformations() {
        return entityManager
                .createQuery("select t from Transformation t", Transformation.class)
                .getResultList();
    }

    @Override
    public Transformation getTransformation(long transformationId) {
    	return entityManager
    			.find(Transformation.class, transformationId);
    }
    
    @Override
    public List<Transformation> getQueries() {
        return getTransformations(TransformationType.Query);
    }
    
    private List<Transformation> getTransformations(TransformationType type) {
    	return entityManager
                .createQuery("SELECT t FROM Transformation t WHERE t.type = :type", Transformation.class)
                .setParameter("type", type)
                .getResultList();
    }
    
    @Override
    public void addTransformation(Transformation transformation) {
        entityManager.persist(transformation);
        camelController.updateCamelContext();
    }

    @Override
    public void updateTransformation(Transformation transformation) {
    	transformation.updateData();
        entityManager.merge(transformation);
        camelController.updateCamelContext();
    }
    
    @Override
    public void removeTransformation(Transformation transformation) {
    	entityManager.remove(entityManager.merge(transformation));
    	camelController.updateCamelContext();
    }
    
    @Override
    public List<Datasource> getDatasources() {
    	List<Datasource> result = new ArrayList<Datasource>();

    	List<Connector> connectors = entityManager
    			.createQuery("SELECT c FROM Connector c", Connector.class)
    			.getResultList();
    	for (Connector connector : connectors) {
    		for (Datasource datasource : connector.getSchemaDetached().getDatasources()) {
    			datasource.setId(datasource.getId() + " (" + connector.getName() + ")");
    			datasource.setLabel(datasource.getLabel() + " (" + connector.getName() + ")");
    			result.add(datasource);
    		}
    	}
    	
    	List<DatasetGroup> datasetGroups = entityManager
    			.createQuery("SELECT dg FROM DatasetGroup dg", DatasetGroup.class)
    			.getResultList();
    	for (DatasetGroup datasetGroup : datasetGroups) {
    		result.add(createDatasource(datasetGroup));
    	}
    	
        return result;
    }
    
	@Override
    public Datasource findDatasource(String id) {
		for (Datasource datasource : getDatasources()) {
			if (datasource.getId().equals(id)) {
				return datasource;
			}
		}
		return null;
    }

    private Datasource createDatasource(DatasetGroup datasetGroup) {
    	Datasource datasource = new Datasource();
    	datasource.setId(datasetGroup.getName());
    	datasource.setLabel(datasetGroup.getName());
    	datasource.getSchema().addOutput(createOutput(datasetGroup.getSchema().getRootElement()));
    	datasource.setRouteSpec("<from uri='file:/home/te/temp/datahub-work/" + datasetGroup.getName() + "/datasource' />");
    	return datasource;
    }

    @Override
    public List<Datasink> getDatasinks() {
    	List<Datasink> result = new ArrayList<Datasink>();
    	
    	List<Connector> connectors = entityManager
    			.createQuery("SELECT c FROM Connector c", Connector.class)
    			.getResultList();
    	for (Connector connector : connectors) {
    		for (Datasink datasink : connector.getSchemaDetached().getDatasinks()) {
    			datasink.setId(datasink.getId() + " (" + connector.getName() + ")");
    			datasink.setLabel(datasink.getLabel() + " (" + connector.getName() + ")");
    			result.add(datasink);
    		}
    	}
    	
    	List<DatasetGroup> datasetGroups = entityManager
    			.createQuery("SELECT dg FROM DatasetGroup dg", DatasetGroup.class)
    			.getResultList();
    	for (DatasetGroup datasetGroup : datasetGroups) {
    		result.add(createDatasink(datasetGroup));
    	}
    	
        return result;
    }

    @Override
    public Datasink findDatasink(String id) {
    	for (Datasink datasink : getDatasinks()) {
    		if (datasink.getId().equals(id)) {
    			return datasink;
    		}
    	}
    	return null;
    }

    private Datasink createDatasink(DatasetGroup datasetGroup) {
    	Datasink datasink = new Datasink();
    	datasink.setId(datasetGroup.getName());
    	datasink.setLabel(datasetGroup.getName());
    	datasink.getSchema().addInput(createInput(datasetGroup.getSchema().getRootElement()));
    	datasink.setRouteSpec(
    			"<setHeader headerName='datasetGroup'>\n <constant>" + datasetGroup.getName() + "</constant>\n</setHeader>\n" +
    			"<to uri='bean:transformBean' />");
    	return datasink;
    }

    @Override
    public List<Lookup> getLookups() {
    	List<Lookup> result = new ArrayList<Lookup>();
    	
//    	List<Connector> connectors = entityManager
//    			.createQuery("SELECT c FROM Connector c", Connector.class)
//    			.getResultList();
//    	for (Connector connector : connectors) {
//    		for (Lookup lookup : connector.getSchemaDetached().getDLookups()) {
//    			lookup.setName(lookup.getName() + " (" + connector.getName() + ")");
//				lookup.setLabel(lookup.getLabel() + " (" + connector.getName() + ")");
//    			result.add(lookup);
//    		}
//    	}
    	
    	List<DatasetGroup> datasetGroups = entityManager
    			.createQuery("SELECT dg FROM DatasetGroup dg", DatasetGroup.class)
    			.getResultList();
    	for (DatasetGroup datasetGroup : datasetGroups) {
    		result.add(createLookup(datasetGroup));
    	}
    	
        return result;
    }
    
    @Override
    public Lookup findLookup(String id) {
    	for (Lookup lookup : getLookups()) {
    		if (lookup.getId().equals(id)) {
    			return lookup;
    		}
    	}
    	return null;
    }

    private Lookup createLookup(DatasetGroup datasetGroup) {
    	Lookup lookup = new Lookup();
    	lookup.setId(datasetGroup.getName());
    	lookup.setLabel(datasetGroup.getName());
//    	lookup.getSchema().addInput(createInput(datasetGroup.getSchema().getRootElement()));
    	lookup.getSchema().addOutput(new Output("result", ItemType.XML_ELEMENT, createOutput(datasetGroup.getSchema().getRootElement())));
    	lookup.setDatasourceSpec("lookup-" + datasetGroup.getId()); // TODO
    	return lookup;
    }

	private Output createOutput(Element element) {
		Output output = new Output(element.getName(), ItemType.XML_ELEMENT);
		for (Element e : element.getElements()) {
			output.addOutput(createOutput(e));
		}
		for (Attribute a : element.getAttributes()) {
			output.addOutput(createOutput(a));
		}
		return output;
	}

    private Output createOutput(Attribute attribute) {
    	return new Output(attribute.getName(), ItemType.XML_ATTRIBUTE);
	}

	private Input createInput(Element element) {
		Input output = new Input(element.getName(), ItemType.XML_ELEMENT);
		for (Element e : element.getElements()) {
			output.addInput(createInput(e));
		}
		for (Attribute a : element.getAttributes()) {
			output.addInput(createInput(a));
		}
		return output;
	}
    
    private Input createInput(Attribute attribute) {
    	return new Input(attribute.getName(), ItemType.XML_ATTRIBUTE);
	}

}
