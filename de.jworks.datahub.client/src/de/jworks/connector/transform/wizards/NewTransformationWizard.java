package de.jworks.connector.transform.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import de.jworks.connector.transform.views.TransformationsView;
import de.jworks.datahub.business.transform.boundary.TransformationService;
import de.jworks.datahub.business.transform.entity.Transformation;

public class NewTransformationWizard extends Wizard implements INewWizard {

	private Transformation transformation = new Transformation();

	public NewTransformationWizard() {
		setWindowTitle("New Transformation");
	}

	public Transformation getTransformation() {
		return transformation;
	}
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public void addPages() {
		addPage(new NewTransformationWizardPage());
	}

	@Override
	public boolean performFinish() {
		try {
			transformation.updateData();
			
			TransformationService transformationService = (TransformationService) PlatformUI.getWorkbench().getService(TransformationService.class);
			transformationService.addTransformation(transformation);
			
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TransformationsView.ID);
			} catch (Exception e) {
				// ignore
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
