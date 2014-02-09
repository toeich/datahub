package de.jworks.datahub.business.transform.boundary;

import de.jworks.datahub.business.transform.entity.Datasource;

import java.util.List;

public interface DatasourceService {

    public List<Datasource> getDatasources();

    public Datasource findDatasourceByName(String name);

    public void addDatasouce(Datasource datasource);

    public void updateDatasource(Datasource datasource);
    
}