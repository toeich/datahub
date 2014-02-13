package de.jworks.datahub.business.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.boundary.UserGroupService;
import de.jworks.datahub.business.common.boundary.UserService;
import de.jworks.datahub.business.common.entity.Project;
import de.jworks.datahub.business.common.entity.UserGroup;
import de.jworks.datahub.business.common.entity.Role;
import de.jworks.datahub.business.common.entity.User;
import de.jworks.datahub.business.datasets.entity.ColumnDefinition;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.business.datasets.entity.Element;
import de.jworks.datahub.business.systems.entity.System;
import de.jworks.datahub.business.transform.boundary.DatasinkService;
import de.jworks.datahub.business.transform.boundary.DatasourceService;
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
	UserGroupService userGroupService;
	
	@Inject
	DatasourceService datasourceService;
	
	@Inject
	DatasinkService datasinkService;
	
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
		userGroupService.addGroup(users);

		// Group "admins"
		UserGroup admins = new UserGroup();
		admins.setName("Admins");
		admins.getRoles().add(Role.ADMIN);
		admins.getUsers().add(admin);
		userGroupService.addGroup(admins);

		// Project "Catalogs"
		{
			Project catalogs = new Project();
			catalogs.setName("Catalogs");
			entityManager.persist(catalogs);
		}
		
		// System "cmi24"
		{
			System cmi24 = new System();
			cmi24.setName("cmi24");
			
			// Datasource "Products"
			{
				Datasource cmi24Products = new Datasource();
				cmi24Products.setName("Products");
				cmi24Products.getSchema().addOutput(
						new Output("products", "products", ItemType.XML_ELEMENT,
								new Output("product", "product", ItemType.XML_ELEMENT,
										new Output("ean", "@ean", ItemType.XML_ATTRIBUTE),
										new Output("name", "@name", ItemType.XML_ATTRIBUTE),
										new Output("description", "@description", ItemType.XML_ATTRIBUTE))));
				cmi24Products.setRouteSpec("<from uri='file:/home/te/temp/connector-work/systems/cmi24/datasources/Products' />");
				cmi24.getSchema().getDatasources().add(cmi24Products);
			}
			
			// Datasink "Publications"
			{
				Datasink cmi24Publications = new Datasink();
				cmi24Publications.setName("Publications");
				cmi24Publications.getSchema().addInput(
						new Input("Publication", "publication", ItemType.XML_ELEMENT,
								new Input("Key", "@_key", ItemType.XML_ATTRIBUTE),
								new Input("Name", "@_name", ItemType.XML_ATTRIBUTE),
								new Input("Chapter", "chapter", ItemType.XML_ELEMENT,
										new Input("Key", "@_key", ItemType.XML_ATTRIBUTE),
										new Input("Name", "@_name", ItemType.XML_ATTRIBUTE),
										new Input("Product", "product", ItemType.XML_ELEMENT,
												new Input("Key", "@_key", ItemType.XML_ATTRIBUTE),
												new Input("Name", "@_name", ItemType.XML_ATTRIBUTE),
												new Input("Master", "@_master", ItemType.XML_ATTRIBUTE),
												new Input("Price", "@price", ItemType.XML_ATTRIBUTE)))));
				cmi24Publications.setRouteSpec("<to uri='file:/home/te/temp/connector-work/systems/cmi24/datasinks/Publications' />");
				cmi24.getSchema().getDatasinks().add(cmi24Publications);
			}
			
			// Datasource "Publications"
			{
				Datasource cmi24Publications = new Datasource();
				cmi24Publications.setName("Publications");
				cmi24Publications.getSchema().addOutput(
						new Output("Publication", "publication", ItemType.XML_ELEMENT,
								new Output("Name", "@name", ItemType.XML_ATTRIBUTE),
								new Output("URL", "@url", ItemType.XML_ATTRIBUTE)));
				cmi24Publications.setRouteSpec("<from uri='file:/home/te/temp/connector-work/systems/cmi24/datasources/Publications' />");
				cmi24.getSchema().getDatasources().add(cmi24Publications);
			}
			
			entityManager.persist(cmi24);
		}
		
		// System "SAP"
		{
			System sap = new System();
			sap.setName("SAP");
			
			// Datasource "Products"
			{
				Datasource sapProducts = new Datasource();
				sapProducts.setName("Products");
				sapProducts.getSchema().addOutput(
						new Output("products", "products", ItemType.XML_ELEMENT,
								new Output("product", "product", ItemType.XML_ELEMENT,
										new Output("ean", "@ean", ItemType.XML_ATTRIBUTE),
										new Output("name", "@name", ItemType.XML_ATTRIBUTE),
										new Output("price", "@price", ItemType.XML_ATTRIBUTE))));
				sapProducts.setRouteSpec("<from uri='file:/home/te/temp/connector-work/systems/SAP/datasources/Products' />");
				sap.getSchema().getDatasources().add(sapProducts);
			}
			
			entityManager.persist(sap);
		}
		
		// System "FTP"
		{
			System ftp = new System();
			ftp.setName("FTP");
			
			// Datasink "Files"
			{
				Datasink files = new Datasink();
				files.setName("Files");
				files.getSchema().addInput(
						new Input("File", "file", ItemType.XML_ELEMENT,
								new Input("URL", "@url", ItemType.XML_ATTRIBUTE)));
				files.setRouteSpec("<to uri='file:/home/te/temp/connector-work/systems/FTP/datasinks/Files' />");
				ftp.getSchema().getDatasinks().add(files);
			}
			
			entityManager.persist(ftp);
		}

		// Collection "Customers"
		DatasetGroup collection = new DatasetGroup();
		collection.setName("Customers");
		Element rootElement = collection.getSchema().getRootElement();
		rootElement.setName("customer");
		rootElement.setLabel("Kunde");
		rootElement.addAttribute("firstName").setLabel("Vorname");
		rootElement.addAttribute("lastName").setLabel("Nachname");
		collection.getColumns().add(new ColumnDefinition("Name", "{firstName} {lastName}"));
		entityManager.persist(collection);
		
		// Collection "cmi24_Products"
		DatasetGroup cmi24Products = new DatasetGroup();
		cmi24Products.setName("cmi24_Products");
		Element cmi24Product = cmi24Products.getSchema().getRootElement();
		cmi24Product.setName("product");
		cmi24Product.setLabel("Produkt");
		cmi24Product.addAttribute("id").setLabel("ID");
		cmi24Product.addAttribute("name").setLabel("Name");
		cmi24Product.addAttribute("description").setLabel("Bescheibung");
		cmi24Products.getColumns().add(new ColumnDefinition("ID", "{id}"));
		cmi24Products.getColumns().add(new ColumnDefinition("Name", "{name}"));
		entityManager.persist(cmi24Products);
		
		// Collection "SAP_Products"
		DatasetGroup sapProducts = new DatasetGroup();
		sapProducts.setName("SAP_Products");
		Element sapProduct = sapProducts.getSchema().getRootElement();
		sapProduct.setName("product");
		sapProduct.setLabel("Produkt");
		sapProduct.addAttribute("id").setLabel("ID");
		sapProduct.addAttribute("name").setLabel("Name");
		sapProduct.addAttribute("price").setLabel("Preis");
		sapProducts.getColumns().add(new ColumnDefinition("ID", "{id}"));
		sapProducts.getColumns().add(new ColumnDefinition("Name", "{name}"));
		entityManager.persist(sapProducts);

		// Collection "Catalogs"
		DatasetGroup catalogs = new DatasetGroup();
		catalogs.setName("Catalogs");
		Element catalog = catalogs.getSchema().getRootElement();
		catalog.setName("catalog");
		catalog.setLabel("Katalog");
		catalog.addAttribute("name").setLabel("Name");
		catalog.addAttribute("customer").setLabel("Kunde");
		catalog.addAttribute("template").setLabel("Template");
		Element chapter = catalog.addElement("chapter");
		chapter.setLabel("Kapitel");
		chapter.setPluralLabel("Kapitel");
		chapter.setMinOccurs(null); // 0
		chapter.setMaxOccurs(null); // unbounded
		chapter.addAttribute("title").setLabel("Titel");
		Element product = chapter.addElement("product");
		product.setLabel("Produkt");
		product.setPluralLabel("Produkte");
		product.setMinOccurs(null); // 0
		product.setMaxOccurs(null); // unbounded
		product.addAttribute("product").setLabel("Produkt");
		product.addAttribute("price").setLabel("Preis");
		product.addAttribute("template").setLabel("Template");
		catalogs.getColumns().add(new ColumnDefinition("Name", "{name}"));
		entityManager.persist(catalogs);
		
		// Transformation "import cmi24 products"
		Transformation transformation1 = new Transformation();
		transformation1.setName("import cmi24 products");
		transformation1.getDefinition().setDatasource(datasourceService.findDatasourceByName("cmi24::Products"));
		transformation1.getDefinition().setDatasink(datasinkService.findDatasinkByName("cmi24_Products"));
		entityManager.persist(transformation1);
		
//		// Transformation "import sap products"
//		Transformation transformation2 = new Transformation();
//		transformation2.setName("import sap products");
//		entityManager.persist(transformation2);
	}

}
