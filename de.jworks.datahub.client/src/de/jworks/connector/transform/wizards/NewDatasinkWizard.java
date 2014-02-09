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

import de.jworks.datahub.business.transform.boundary.DatasinkService;
import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.client.Activator;

public class NewDatasinkWizard extends Wizard implements INewWizard {

	private Datasink datasink = new Datasink();
	
	private Map<String, Datasink> templates = new TreeMap<String, Datasink>();
	
	public NewDatasinkWizard() {
		setWindowTitle("New Datasink");
	}

	public Datasink getDatasink() {
		return datasink;
	}
	
	public Map<String, Datasink> getTemplates() {
		return templates;
	}
	
	@Override
	public void addPages() {
		addPage(new NewDatasinkWizardPage());
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		datasink.getSchema();

		Enumeration<URL> entries = Activator.getDefault().getBundle().findEntries("/bin", "*.datasink.xml", true); // TODO
		while (entries != null && entries.hasMoreElements()) {
			URL entry = (URL) entries.nextElement();
			try {
				Datasink datasink = JAXB.unmarshal(entry.openStream(), Datasink.class);
				if (!templates.containsKey(datasink.getName())) {
					templates.put(datasink.getName(), datasink);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean performFinish() {
		try {
			DatasinkService datasinkService = (DatasinkService) PlatformUI.getWorkbench().getService(DatasinkService.class);
			datasinkService.addDatasink(datasink);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
