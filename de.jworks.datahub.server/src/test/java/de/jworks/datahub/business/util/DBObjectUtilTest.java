package de.jworks.datahub.business.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import de.jworks.datahub.business.util.DBObjectUtil;

public class DBObjectUtilTest {

	private static final String XML = 
			"<customer _id='012345678901234567890123' _collection='customers'>" +
			" <firstName>Tobias</firstName>" +
			" <lastName>Eichhorn</lastName>" +
			" <address>" +
			"  <city>Hagen</city>" +
			" </address>" +
			" <address>" +
			"  <city>Bochum</city>" +
			" </address>" +
			"</customer>";
	
	private static final DBObject OBJECT =
			new BasicDBObject()
			.append("_name", "customer")
			.append("_id", "012345678901234567890123")
			.append("_collection", "customers")
			.append("firstName", "Tobias")
			.append("lastName", "Eichhorn")
			.append("address", Arrays.asList(
				new BasicDBObject()
				.append("city", "Hagen"),
				new BasicDBObject()
				.append("city", "Bochum")));

	@Test
	public void testParseXML() {
		DBObject object = DBObjectUtil.parse(XML);
		String expected = OBJECT.toString();
		String actual = object.toString();
		
		System.out.println("expected: " + expected);
		System.out.println("actual:   " + actual);
		
		assertEquals(expected, actual);
	}

	@Test
	public void testToXML() {
		Document document = DBObjectUtil.toDocument(OBJECT);
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(System.out));
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testParseJSON() {
		fail("Not yet implemented");
	}

	@Test
	public void testToJSON() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

}
