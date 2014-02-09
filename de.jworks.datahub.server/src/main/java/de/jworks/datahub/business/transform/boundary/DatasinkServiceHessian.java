package de.jworks.datahub.business.transform.boundary;

import java.util.List;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.caucho.hessian.server.HessianServlet;

import de.jworks.datahub.business.transform.boundary.DatasinkService;
import de.jworks.datahub.business.transform.entity.Datasink;

@Alternative
public class DatasinkServiceHessian extends HessianServlet implements DatasinkService {

	private static final long serialVersionUID = 1L;

    @Inject
    DatasinkService datasinkService;

    @Override
    public List<Datasink> getDatasinks() {
        return datasinkService.getDatasinks();
    }

    @Override
    public Datasink findDatasinkByName(String name) {
        return datasinkService.findDatasinkByName(name);
    }

    @Override
    public void addDatasink(Datasink datasink) {
        datasinkService.addDatasink(datasink);
    }

    @Override
    public void updateDatasink(Datasink datasink) {
        datasinkService.updateDatasink(datasink);
    }
}
