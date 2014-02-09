package de.jworks.datahub.business.transform.boundary;

import de.jworks.datahub.business.transform.entity.Datasink;

import java.util.List;

public interface DatasinkService {

    public List<Datasink> getDatasinks();

    public Datasink findDatasinkByName(String name);

    public void addDatasink(Datasink datasink);

    public void updateDatasink(Datasink datasink);
}