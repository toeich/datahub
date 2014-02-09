package de.jworks.datahub.business.documents.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import de.jworks.datahub.business.documents.controller.DBObjectProvider;

public class DBObjectProviderTest {

	private DBObjectProvider cut;
	
	@Before
	public void setUp() {
		cut = new DBObjectProvider();
	}
	
	@Test
	public void testDBObjectToXml() throws Exception {
		DBObject dbObject = new BasicDBObject();
		dbObject.put("_id", "ID");
		dbObject.put("_name", "catalog");
		dbObject.put("_collection", "catalogs");
		dbObject.put("title", "Katalog");
		dbObject.put("chapter", Arrays.asList(new BasicDBObject().append("title", "Kapitel 1"), new BasicDBObject().append("title", "Kapitel 2")));
		
		ByteArrayOutputStream entityStream = new ByteArrayOutputStream();
		cut.writeTo(dbObject, null, null, null, null, null, entityStream);
		String xml = entityStream.toString();
		
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><catalog _collection=\"catalogs\" _id=\"ID\" title=\"Katalog\"><chapter title=\"Kapitel 1\"/><chapter title=\"Kapitel 2\"/></catalog>";
		
		assertEquals(expected, xml);
	}
	
	@Test
	public void testXmlToDBObject() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><catalog _collection=\"catalogs\" _id=\"ID\" title=\"Katalog\"><chapter title=\"Kapitel 1\"/><chapter title=\"Kapitel 2\"/></catalog>";

		ByteArrayInputStream entityStream  = new ByteArrayInputStream(xml.getBytes());
		DBObject dbObject = cut.readFrom(null, null, null, null, null, entityStream);
		
		DBObject expected = new BasicDBObject();
		expected.put("_id", "ID");
		expected.put("_name", "catalog");
		expected.put("_collection", "catalogs");
		expected.put("title", "Katalog");
		expected.put("chapter", Arrays.asList(new BasicDBObject().append("title", "Kapitel 1"), new BasicDBObject().append("title", "Kapitel 2")));
		
		assertEquals(expected, dbObject);
	}
	
}
