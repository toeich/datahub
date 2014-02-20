package de.jworks.datahub.business.transform.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

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
    			datasource.setName(connector.getName() + "__" + datasource.getName());
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
    public Datasource findDatasourceByName(String name) {
    	try {
    		if (StringUtils.contains(name, "__")) {
    			// connector datasource
    			String connectorName = StringUtils.substringBefore(name, "__");
    			String datasourceName = StringUtils.substringAfter(name, "__");
    			Connector connector = entityManager
    					.createQuery("SELECT c FROM Connector c WHERE c.name = :name", Connector.class)
    					.setParameter("name", connectorName)
    					.getSingleResult();
    			for (Datasource datasource : connector.getSchemaDetached().getDatasources()) {
    				if (StringUtils.equals(datasourceName, datasource.getName())) {
    					datasource.setName(connector.getName() + "__" + datasource.getName());
    					return datasource;
    				}
    			}
    		} else {
    			// dataset group datasource
    			DatasetGroup datasetGroup = entityManager
    					.createQuery("SELECT dg FROM DatasetGroup dg WHERE dg.name = :name", DatasetGroup.class)
    					.setParameter("name", name)
    					.getSingleResult();
    			return createDatasource(datasetGroup);
    		}
    		return null;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    private Datasource createDatasource(DatasetGroup datasetGroup) {
    	Datasource datasource = new Datasource();
    	datasource.setName(datasetGroup.getName());
    	datasource.getSchema().addOutput(createOutput(datasetGroup.getSchema().getRootElement()));
    	datasource.setRouteSpec("<from uri='file:/home/te/temp/connector-work/collections/" + datasetGroup.getName() + "/datasource' />");
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
    			datasink.setName(connector.getName() + "__" + datasink.getName());
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
    public Datasink findDatasinkByName(String name) {
    	try {
    		if (StringUtils.contains(name, "__")) {
    			String connectorName = StringUtils.substringBefore(name, "__");
    			String datasinkName = StringUtils.substringAfter(name, "__");
    			Connector connector = entityManager
    					.createQuery("SELECT c FROM Connector c WHERE c.name = :name", Connector.class)
    					.setParameter("name", connectorName)
    					.getSingleResult();
    			for (Datasink datasink : connector.getSchema().getDatasinks()) {
    				if (StringUtils.equals(datasinkName, datasink.getName())) {
    					return datasink;
    				}
    			}
    		} else {
    			DatasetGroup datasetGroup = entityManager
    					.createQuery("SELECT dg FROM DatasetGroup dg WHERE dg.name = :name", DatasetGroup.class)
    					.setParameter("name", name)
    					.getSingleResult();
    			return createDatasink(datasetGroup);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    private Datasink createDatasink(DatasetGroup datasetGroup) {
    	Datasink datasink = new Datasink();
    	datasink.setName(datasetGroup.getName());
    	datasink.getSchema().addInput(createInput(datasetGroup.getSchema().getRootElement()));
    	datasink.setRouteSpec("<to uri='file:/home/te/temp/connector-work/collections/" + datasetGroup.getName() + "/datasink' />");
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
//    			lookup.setName(connector.getName() + "__" + lookup.getName());
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
    public Lookup findLookupkByName(String name) {
    	try {
    		if (StringUtils.contains(name, "__")) {
//    			String connectorName = StringUtils.substringBefore(name, "__");
//    			String datasinkName = StringUtils.substringAfter(name, "__");
//    			Connector connector = entityManager
//    					.createQuery("SELECT c FROM Connector c WHERE c.name = :name", Connector.class)
//    					.setParameter("name", connectorName)
//    					.getSingleResult();
//    			for (Lookup lookup : connector.getSchema().getLookups()) {
//    				if (StringUtils.equals(datasinkName, lookup.getName())) {
//    					return lookup;
//    				}
//    			}
    		} else {
    			DatasetGroup datasetGroup = entityManager
    					.createQuery("SELECT dg FROM DatasetGroup dg WHERE dg.name = :name", DatasetGroup.class)
    					.setParameter("name", name)
    					.getSingleResult();
    			return createLookup(datasetGroup);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    private Lookup createLookup(DatasetGroup datasetGroup) {
    	Lookup lookup = new Lookup();
    	lookup.setName(datasetGroup.getName());
//    	lookup.getSchema().addInput(createInput(datasetGroup.getSchema().getRootElement()));
    	lookup.getSchema().addOutput(createOutput(datasetGroup.getSchema().getRootElement()));
    	lookup.setDatasourceSpec("lookup-" + datasetGroup.getId()); // TODO
    	return lookup;
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
    	return new Output(attribute.getLabel(), "@" + attribute.getName(), null);
	}

	private Input createInput(Element element) {
		Input output = new Input(element.getLabel(), element.getName(), ItemType.XML_ELEMENT);
		for (Element e : element.getElements()) {
			output.addInput(createInput(e));
		}
		for (Attribute a : element.getAttributes()) {
			output.addInput(createInput(a));
		}
		return output;
	}
    
    private Input createInput(Attribute attribute) {
    	return new Input(attribute.getLabel(), "@" + attribute.getName(), ItemType.XML_ELEMENT);
	}

}
