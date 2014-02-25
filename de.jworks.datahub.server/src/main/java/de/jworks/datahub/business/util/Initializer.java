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
import de.jworks.datahub.business.datasets.entity.ColumnDefinition;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.business.datasets.entity.Element;
import de.jworks.datahub.business.datasets.entity.AttributeType;
import de.jworks.datahub.business.transform.boundary.TransformationService;
import de.jworks.datahub.business.transform.controller.CamelController;
import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Input;
import de.jworks.datahub.business.transform.entity.ItemType;
import de.jworks.datahub.business.transform.entity.Output;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationType;

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
			
			// Datasource "Products"
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
				cmi24Products.setRouteSpec("<from uri='file:/home/te/temp/connector-work/systems/cmi24/datasources/Products' />");
				cmi24.getSchema().getDatasources().add(cmi24Products);
			}
			
			// Datasink "Publications"
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
				cmi24Publications.setRouteSpec("<to uri='file:/home/te/temp/connector-work/systems/cmi24/datasinks/Publications' />");
				cmi24.getSchema().getDatasinks().add(cmi24Publications);
			}
			
			// Datasource "Publications"
			{
				Datasource cmi24Publications = new Datasource();
				cmi24Publications.setName("publications");
				cmi24Publications.setLabel("Publications");
				cmi24Publications.getSchema().addOutput(
						new Output("publication", ItemType.XML_ELEMENT,
								new Output("name", ItemType.XML_ATTRIBUTE),
								new Output("url", ItemType.XML_ATTRIBUTE)));
				cmi24Publications.setRouteSpec("<from uri='file:/home/te/temp/connector-work/systems/cmi24/datasources/Publications' />");
				cmi24.getSchema().getDatasources().add(cmi24Publications);
			}
			
			entityManager.persist(cmi24);
		}
		
		// Connector "SAP"
		{
			Connector sap = new Connector();
			sap.setName("SAP");
			
			// Datasource "Products"
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
				sapProducts.setRouteSpec("<from uri='file:/home/te/temp/connector-work/systems/SAP/datasources/Products' />");
				sap.getSchema().getDatasources().add(sapProducts);
			}
			
			entityManager.persist(sap);
		}
		
		// Connector "FTP"
		{
			Connector ftp = new Connector();
			ftp.setName("FTP");
			
			// Datasink "Files"
			{
				Datasink files = new Datasink();
				files.setName("files");
				files.setLabel("Files");
				files.getSchema().addInput(
						new Input("file", ItemType.XML_ELEMENT,
								new Input("url", ItemType.XML_ATTRIBUTE)));
				files.setRouteSpec("<to uri='file:/home/te/temp/connector-work/systems/FTP/datasinks/Files' />");
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
		entityManager.persist(customers);
		
		// Dataset Group "cmi24_Products"
		DatasetGroup cmi24Products = new DatasetGroup();
		Element cmi24Product = cmi24Products.getSchema().getRootElement();
		cmi24Products.setName("cmi24_Products");
		cmi24Product.setName("product");
		cmi24Product.setLabel("Produkt");
		cmi24Product.addAttribute("id", AttributeType.STRING, null).setLabel("ID");
		cmi24Product.addAttribute("name", AttributeType.STRING, null).setLabel("Name");
		cmi24Product.addAttribute("description", AttributeType.STRING, null).setLabel("Bescheibung");
		cmi24Products.getColumns().add(new ColumnDefinition("Name", "/product/@name"));
		entityManager.persist(cmi24Products);
		
		// Dataset Group "SAP_Products"
		DatasetGroup sapProducts = new DatasetGroup();
		sapProducts.setName("SAP_Products");
		Element sapProduct = sapProducts.getSchema().getRootElement();
		sapProduct.setName("product");
		sapProduct.setLabel("Produkt");
		sapProduct.addAttribute("id", AttributeType.STRING, null).setLabel("ID");
		sapProduct.addAttribute("name", AttributeType.STRING, null).setLabel("Name");
		sapProduct.addAttribute("price", AttributeType.STRING, null).setLabel("Preis");
		sapProducts.getColumns().add(new ColumnDefinition("Name", "/product/@name"));
		entityManager.persist(sapProducts);

		// Dataset Group "Catalogs"
		DatasetGroup catalogs = new DatasetGroup();
		catalogs.setName("Catalogs");
		Element catalog = catalogs.getSchema().getRootElement();
		catalog.setName("catalog");
		catalog.setLabel("Katalog");
		catalog.addAttribute("name", AttributeType.STRING, null).setLabel("Name");
		catalog.addAttribute("customer", AttributeType.STRING, null).setLabel("Kunde");
		catalog.addAttribute("template", AttributeType.STRING, null).setLabel("Template");
		Element chapter = catalog.addElement("chapter");
		chapter.setLabel("Kapitel");
		chapter.setPluralLabel("Kapitel");
		chapter.setMinOccurs(null); // 0
		chapter.setMaxOccurs(null); // unbounded
		chapter.addAttribute("title", AttributeType.STRING, null).setLabel("Titel");
		Element product = chapter.addElement("product");
		product.setLabel("Produkt");
		product.setPluralLabel("Produkte");
		product.setMinOccurs(null); // 0
		product.setMaxOccurs(null); // unbounded
		product.addAttribute("product", AttributeType.STRING, null).setLabel("Produkt");
		product.addAttribute("price", AttributeType.STRING, null).setLabel("Preis");
		product.addAttribute("template", AttributeType.STRING, null).setLabel("Template");
		catalogs.getColumns().add(new ColumnDefinition("Name", "/catalog/@name"));
		entityManager.persist(catalogs);
		
		// Import "Import cmi24 Products"
		Transformation import1 = Transformation.createImport("Import cmi24 Products", 
				transformationService.findDatasourceByName("cmi24__products"),
				transformationService.findDatasinkByName("cmi24_Products"));
		entityManager.persist(import1);
		
		// Query "Sample Query"
		Transformation query1 = Transformation.createQuery("Sample Query");
		query1.getDefinition().getDatasink().getSchema().getInputs().get(0).addInput(
						new Input("person", ItemType.XML_ELEMENT,
								new Input("name", ItemType.XML_ATTRIBUTE)));
		entityManager.persist(query1);
	}

}
