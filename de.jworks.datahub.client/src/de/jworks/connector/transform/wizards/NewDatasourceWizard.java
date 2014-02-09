package de.jworks.connector.transform.wizards;

import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXB;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import de.jworks.datahub.business.transform.boundary.DatasourceService;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.client.Activator;

public class NewDatasourceWizard extends Wizard implements INewWizard {

	private Datasource datasource = new Datasource();
	
	private Map<String, Datasource> templates = new TreeMap<String, Datasource>();
	
	public NewDatasourceWizard() {
		setWindowTitle("New Datasource");
	}

	public Datasource getDatasource() {
		return datasource;
	}
	
	public Map<String, Datasource> getTemplates() {
		return templates;
	}
	
	@Override
	public void addPages() {
		addPage(new NewDatasourceWizardPage());
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		datasource.getSchema();

		Enumeration<URL> entries = Activator.getDefault().getBundle().findEntries("/bin", "*.datasource.xml", true); // TODO
		while (entries != null && entries.hasMoreElements()) {
			URL entry = (URL) entries.nextElement();
			try {
				Datasource datasource = JAXB.unmarshal(entry.openStream(), Datasource.class);
				if (!templates.containsKey(datasource.getName())) {
					templates.put(datasource.getName(), datasource);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean performFinish() {
		try {
			DatasourceService datasourceService = (DatasourceService) PlatformUI.getWorkbench().getService(DatasourceService.class);
			datasourceService.addDatasouce(datasource);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
