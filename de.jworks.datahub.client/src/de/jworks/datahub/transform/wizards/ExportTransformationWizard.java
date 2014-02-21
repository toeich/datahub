package de.jworks.datahub.transform.wizards;

import java.io.FileWriter;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.utils.SelectionHelper;

public class ExportTransformationWizard extends Wizard implements IExportWizard {
	
	private Transformation transformation;
	
	private String filePath;

	public ExportTransformationWizard() {
		setWindowTitle("Export Transformation");
	}

	public Transformation getTransformation() {
		return transformation;
	}
	
	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		transformation = SelectionHelper.getFirstElement(selection, Transformation.class);
	}
	
	@Override
	public void addPages() {
		addPage(new ExportTransformationWizardPage());
	}

	@Override
	public boolean canFinish() {
		return transformation != null && filePath != null;
	}
	
	@Override
	public boolean performFinish() {
		try {
			transformation.updateData();
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(transformation.getDefinitionData());
			fileWriter.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
