package de.jworks.datahub.business.documents.controller;

import java.net.UnknownHostException;

import javax.enterprise.inject.Produces;

import com.mongodb.MongoClient;

public class MongoClientProvider {

	@Produces
	public MongoClient getMongoClient() {
		try {
			return new MongoClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
}
