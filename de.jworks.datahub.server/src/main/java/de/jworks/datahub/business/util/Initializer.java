package de.jworks.datahub.business.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.boundary.UserService;
import de.jworks.datahub.business.common.entity.Project;
import de.jworks.datahub.business.common.entity.Role;
import de.jworks.datahub.business.common.entity.User;
import de.jworks.datahub.business.common.entity.UserGroup;
import de.jworks.datahub.business.connectors.entity.Connector;
import de.jworks.datahub.business.datasets.entity.AttributeType;
import de.jworks.datahub.business.datasets.entity.ColumnDefinition;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.business.datasets.entity.Element;
import de.jworks.datahub.business.transform.boundary.TransformationService;
import de.jworks.datahub.business.transform.controller.CamelController;
import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Input;
import de.jworks.datahub.business.transform.entity.ItemType;
import de.jworks.datahub.business.transform.entity.Output;
import de.jworks.datahub.business.transform.entity.Transformation;

@Singleton
@Startup
public class Initializer {

	@Inject
	EntityManager entityManager;
	
	@Inject
	CamelController camelController;
	
	@Inject
	UserService userService;

	@Inject
	TransformationService transformationService;
	
	@PostConstruct
	public void init() {
		initSampleData();
		
		camelController.updateCamelContext();
	}

	private void initSampleData() {
		if (userService.getUsers().size() > 0) return;
		
		// User "admin"
		User admin = new User();
		admin.setName("admin");
		admin.setFullName("Tobias der Admin");
		admin.setEmail("te@eggheads.de");
		admin.setPassword("passme");
		userService.addUser(admin);

		// User "te"
		User te = new User();
		te.setName("te");
		te.setFullName("Tobias Eichhorn");
		te.setEmail("te@eggheads.de");
		te.setPassword("passme");
		userService.addUser(te);

		// Group "users"
		UserGroup users = new UserGroup();
		users.setName("Users");
		users.getRoles().add(Role.USER);
		users.getUsers().add(admin);
		users.getUsers().add(te);
		userService.addUserGroup(users);

		// Group "admins"
		UserGroup admins = new UserGroup();
		admins.setName("Admins");
		admins.getRoles().add(Role.ADMIN);
		admins.getUsers().add(admin);
		userService.addUserGroup(admins);

		// Project "Catalogs"
		{
			Project catalogs = new Project();
			catalogs.setName("Catalogs");
			catalogs.setDescription("Description of Catalogs");
			entityManager.persist(catalogs);
		}
		
		// Connector "cmi24"
		{
			Connector cmi24 = new Connector();
			cmi24.setName("cmi24");
			cmi24.setDescription("Description of cmi24");
			
			// Datasource "products (cmi24)"
			{
				Datasource cmi24Products = new Datasource();
				cmi24Products.setName("products");
				cmi24Products.setLabel("Products");
				cmi24Products.getSchema().addOutput(
						new Output("products", ItemType.XML_ELEMENT,
								new Output("product", ItemType.XML_ELEMENT,
										new Output("ean", ItemType.XML_ATTRIBUTE),
										new Output("name", ItemType.XML_ATTRIBUTE),
										new Output("description", ItemType.XML_ATTRIBUTE))));
				cmi24Products.setRouteSpec("<from uri='file:/home/te/temp/datahub-work/cmi24/products' />");
				cmi24.getSchema().getDatasources().add(cmi24Products);
			}
			
			// Datasink "publications (cmi24)"
			{
				Datasink cmi24Publications = new Datasink();
				cmi24Publications.setName("publications");
				cmi24Publications.setLabel("Publications");
				cmi24Publications.getSchema().addInput(
						new Input("publication", ItemType.XML_ELEMENT,
								new Input("_key", ItemType.XML_ATTRIBUTE),
								new Input("_name", ItemType.XML_ATTRIBUTE),
								new Input("chapter", ItemType.XML_ELEMENT,
										new Input("_key", ItemType.XML_ATTRIBUTE),
										new Input("_name", ItemType.XML_ATTRIBUTE),
										new Input("product", ItemType.XML_ELEMENT,
												new Input("_key", ItemType.XML_ATTRIBUTE),
												new Input("_name", ItemType.XML_ATTRIBUTE),
												new Input("_master", ItemType.XML_ATTRIBUTE),
												new Input("price", ItemType.XML_ATTRIBUTE)))));
				cmi24Publications.setRouteSpec("<to uri='file:/home/te/temp/datahub-work/cmi24/publications-1' />");
				cmi24.getSchema().getDatasinks().add(cmi24Publications);
			}
			
			// Datasource "publications (cmi24)"
			{
				Datasource cmi24Publications = new Datasource();
				cmi24Publications.setName("publications");
				cmi24Publications.setLabel("Publications");
				cmi24Publications.getSchema().addOutput(
						new Output("publication", ItemType.XML_ELEMENT,
								new Output("name", ItemType.XML_ATTRIBUTE),
								new Output("url", ItemType.XML_ATTRIBUTE)));
				cmi24Publications.setRouteSpec("<from uri='file:/home/te/temp/datahub-work/cmi24/publications-2' />");
				cmi24.getSchema().getDatasources().add(cmi24Publications);
			}
			
			entityManager.persist(cmi24);
		}
		
		// Connector "SAP"
		{
			Connector sap = new Connector();
			sap.setName("SAP");
			sap.setDescription("Description of SAP");
			
			// Datasource "products (SAP)"
			{
				Datasource sapProducts = new Datasource();
				sapProducts.setName("products");
				sapProducts.setLabel("Products");
				sapProducts.getSchema().addOutput(
						new Output("products", ItemType.XML_ELEMENT,
								new Output("product", ItemType.XML_ELEMENT,
										new Output("ean", ItemType.XML_ATTRIBUTE),
										new Output("name", ItemType.XML_ATTRIBUTE),
										new Output("price", ItemType.XML_ATTRIBUTE))));
				sapProducts.setRouteSpec("<from uri='file:/home/te/temp/datahub-work/SAP/products' />");
				sap.getSchema().getDatasources().add(sapProducts);
			}
			
			entityManager.persist(sap);
		}
		
		// Connector "FTP"
		{
			Connector ftp = new Connector();
			ftp.setName("FTP");
			ftp.setDescription("Description of FTP");
			
			// Datasink "files (FTP)"
			{
				Datasink files = new Datasink();
				files.setName("files");
				files.setLabel("Files");
				files.getSchema().addInput(
						new Input("file", ItemType.XML_ELEMENT,
								new Input("url", ItemType.XML_ATTRIBUTE)));
				files.setRouteSpec("<to uri='file:/home/te/temp/datahub-work/FTP/files' />");
				ftp.getSchema().getDatasinks().add(files);
			}
			
			entityManager.persist(ftp);
		}

		// Dataset Group "Customers"
		DatasetGroup customers = new DatasetGroup();
		customers.setName("Customers");
		customers.setDescription("Description of Customers");
		Element rootElement = customers.getSchema().getRootElement();
		rootElement.setName("customer");
		rootElement.setLabel("Kunde");
		rootElement.addAttribute("firstName", AttributeType.STRING, null).setLabel("Vorname");
		rootElement.addAttribute("lastName", AttributeType.STRING, null).setLabel("Nachname");
		customers.getColumns().add(new ColumnDefinition("Name", "concat(/customer/@firstName, ' ', /customer/@lastName)"));
		customers.getColumns().add(new ColumnDefinition("Description", "concat('Description of ', /customer/@firstName, ' ', /customer/@lastName)"));
		entityManager.persist(customers);
		
		// Dataset Group "cmi24_Products"
		DatasetGroup cmi24Products = new DatasetGroup();
		Element cmi24Product = cmi24Products.getSchema().getRootElement();
		cmi24Products.setName("cmi24 Products");
		cmi24Products.setDescription("Description of cmi24 Products");
		cmi24Product.setName("product");
		cmi24Product.setLabel("Produkt");
		cmi24Product.addAttribute("id", AttributeType.STRING, null).setLabel("ID");
		cmi24Product.addAttribute("name", AttributeType.STRING, null).setLabel("Name");
		cmi24Product.addAttribute("description", AttributeType.STRING, null).setLabel("Bescheibung");
		cmi24Products.getColumns().add(new ColumnDefinition("Name", "/product/@name"));
		cmi24Products.getColumns().add(new ColumnDefinition("Description", "/product/@description"));
		entityManager.persist(cmi24Products);
		
		// Dataset Group "SAP_Products"
		DatasetGroup sapProducts = new DatasetGroup();
		sapProducts.setName("SAP Products");
		sapProducts.setDescription("Description of SAP Products");
		Element sapProduct = sapProducts.getSchema().getRootElement();
		sapProduct.setName("product");
		sapProduct.setLabel("Produkt");
		sapProduct.addAttribute("id", AttributeType.STRING, null).setLabel("ID");
		sapProduct.addAttribute("name", AttributeType.STRING, null).setLabel("Name");
		sapProduct.addAttribute("price", AttributeType.STRING, null).setLabel("Preis");
		sapProducts.getColumns().add(new ColumnDefinition("Name", "/product/@name"));
		sapProducts.getColumns().add(new ColumnDefinition("Description", "concat('Description of ', /product/@description)"));
		entityManager.persist(sapProducts);

		// Dataset Group "Catalogs"
		DatasetGroup catalogs = new DatasetGroup();
		catalogs.setName("Catalogs");
		catalogs.setDescription("Description of Catalogs");
		Element catalog = catalogs.getSchema().getRootElement();
		catalog.setName("catalog");
		catalog.setLabel("Katalog");
		catalog.addAttribute("name", AttributeType.STRING, null).setLabel("Name");
		catalog.addAttribute("customer", AttributeType.STRING, null).setLabel("Kunde");
		catalog.addAttribute("template", AttributeType.STRING, null).setLabel("Template");
		Element chapter = catalog.addElement("chapter");
		chapter.setLabel("Kapitel");
		chapter.setPluralLabel("Kapitel");
		chapter.addAttribute("title", AttributeType.STRING, null).setLabel("Titel");
		Element product = chapter.addElement("product");
		product.setLabel("Produkt");
		product.setPluralLabel("Produkte");
		product.addAttribute("product", AttributeType.STRING, null).setLabel("Produkt");
		product.addAttribute("price", AttributeType.STRING, null).setLabel("Preis");
		product.addAttribute("template", AttributeType.STRING, null).setLabel("Template");
		catalogs.getColumns().add(new ColumnDefinition("Name", "/catalog/@name"));
		catalogs.getColumns().add(new ColumnDefinition("Description", "concat('Description of ', /catalog/@name)"));
		entityManager.persist(catalogs);
		
		// Import "Import cmi24 Products"
		Transformation importCmi24Products = Transformation.createImport("Import cmi24 Products", 
				transformationService.findDatasourceByName("products (cmi24)"),
				transformationService.findDatasinkByName("cmi24 Products"));
		entityManager.persist(importCmi24Products);
		
		// Import "Import SAP Products"
		Transformation importSapProducts = Transformation.createImport("Import SAP Products", 
				transformationService.findDatasourceByName("products (SAP)"),
				transformationService.findDatasinkByName("SAP Products"));
		entityManager.persist(importSapProducts);
		
		// Export "Export Catalogs"
		Transformation exportCatalogs = Transformation.createExport("Export Catalogs",
				transformationService.findDatasinkByName("publications (cmi24)"));
		entityManager.persist(exportCatalogs);
		
		// External Dataflow "Publish Publications"
		Transformation publishPublications = Transformation.createExternalDataflow("Publish Publications",
				transformationService.findDatasourceByName("publications (cmi24)"),
				transformationService.findDatasinkByName("files (FTP)"));
		entityManager.persist(publishPublications);
		
		// Query "Sample Query"
		Transformation sampleQuery = Transformation.createQuery("Sample Query");
		sampleQuery.getDefinition().getDatasink().getSchema().getInputs().get(0).addInput(
						new Input("person", ItemType.XML_ELEMENT,
								new Input("name", ItemType.XML_ATTRIBUTE)));
		entityManager.persist(sampleQuery);
	}

}
