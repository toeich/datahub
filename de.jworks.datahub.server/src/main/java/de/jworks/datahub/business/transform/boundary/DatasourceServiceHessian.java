package de.jworks.datahub.business.transform.boundary;

import java.util.List;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.caucho.hessian.server.HessianServlet;

import de.jworks.datahub.business.transform.boundary.DatasourceService;
import de.jworks.datahub.business.transform.entity.Datasource;

@Alternative
public class DatasourceServiceHessian extends HessianServlet implements DatasourceService {

	private static final long serialVersionUID = 1L;

    @Inject
    DatasourceService datasourceService;

    @Override
    public List<Datasource> getDatasources() {
        return datasourceService.getDatasources();
    }

    @Override
    public Datasource findDatasourceByName(String name) {
        return datasourceService.findDatasourceByName(name);
    }

    @Override
    public void addDatasouce(Datasource datasource) {
        datasourceService.addDatasouce(datasource);
    }

    @Override
    public void updateDatasource(Datasource datasource) {
        datasourceService.updateDatasource(datasource);
    }
}
