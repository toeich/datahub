package de.jworks.datahub.business.documents.boundary;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.ws.rs.DELETE;

import org.junit.BeforeClass;
import org.junit.Test;

public class CollectionsResourceTest {

	private static final String BASE_URL = "http://localhost:18080/de.jworks.connector.server/rest/collections";

	@BeforeClass
	public static void setUpClass() {
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin", "passme".toCharArray());
			}
		});
	}
	
//	@Test
	public void testGetCollections() throws Exception {
		URL url = new URL(BASE_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.addRequestProperty("Content-Type", "application/xml");
		connection.setRequestMethod("GET");
		
		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		for (char[] buffer = new char[1024]; reader.read(buffer) != -1;) {
			System.out.print(buffer);
		}
		System.out.println();

		reader.close();
	}
	
//	@Test
	public void testAddCollection() throws Exception {
		URL url = new URL(BASE_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.addRequestProperty("Content-Type", "application/xml");
		connection.setRequestMethod("POST");		
		connection.setDoOutput(true);
		
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write("<collection name='testAddCollection'/>");
		writer.flush();
		
		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		for (char[] buffer = new char[1024]; reader.read(buffer) != -1;) {
			System.out.print(buffer);
		}
		System.out.println();

		writer.close();
		reader.close();
	}
	
//	@Test
	public void testUpdateCollection() throws Exception {
		URL url = new URL(BASE_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.addRequestProperty("Content-Type", "application/xml");
		connection.setRequestMethod("PUT");		
		connection.setDoOutput(true);
		
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write("<collection id='5' name='testAddCollection2'/>");
		writer.flush();
		
		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		for (char[] buffer = new char[1024]; reader.read(buffer) != -1;) {
			System.out.print(buffer);
		}
		System.out.println();

		writer.close();
		reader.close();
	}
	
//	@Test
	public void testDeleteCollection() throws Exception {
		URL url = new URL(BASE_URL + "/10");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.addRequestProperty("Content-Type", "application/xml");
		connection.setRequestMethod("DELETE");		
		
		InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		for (char[] buffer = new char[1024]; reader.read(buffer) != -1;) {
			System.out.print(buffer);
		}
		System.out.println();

		reader.close();
	}
	
}
