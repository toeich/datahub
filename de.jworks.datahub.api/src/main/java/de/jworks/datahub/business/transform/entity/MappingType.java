package de.jworks.datahub.business.transform.entity;

public enum MappingType {

	Import,			// target: dataset, source: datasink
	Export,			// target: datasink
	Query,			// target: query
	Transformation,	// target: dataset or datasink
	
}
