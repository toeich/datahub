package de.jworks.datahub.transform.wizards;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.jworks.datahub.business.transform.boundary.TransformationService;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ExportTransformationWizardPage extends WizardPage {
	
	private DataBindingContext m_bindingContext;
	
	private ComboViewer transformationsViewer;

	private Text fileField;
	
	/**
	 * Create the wizard.
	 */
	public ExportTransformationWizardPage() {
		super("wizardPage");
		setTitle("Export Transfomation");
		setDescription("Export Transfomation");
	}

	public ExportTransformationWizard getExportTransformationWizard() {
		return (ExportTransformationWizard) getWizard();
	}
	
	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(3, false));
		
		Label transformationLabel = new Label(container, SWT.NONE);
		transformationLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		transformationLabel.setText("Transformation:");
		
		transformationsViewer = new ComboViewer(container, SWT.NONE);
		transformationsViewer.setContentProvider(new ArrayContentProvider());
		transformationsViewer.setLabelProvider(new LabelProvider());
		TransformationService transformationService = (TransformationService) PlatformUI.getWorkbench().getService(TransformationService.class);
		transformationsViewer.setInput(transformationService.getTransformations());
		
		
		Combo transformationsCombo = transformationsViewer.getCombo();
		transformationsCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		
		Label fileLabel = new Label(container, SWT.NONE);
		fileLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		fileLabel.setText("File:");
		
		fileField = new Text(container, SWT.BORDER);
		fileField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button fileBrowseButton = new Button(container, SWT.NONE);
		fileBrowseButton.setText("Browse...");
		fileBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
				dialog.setFilterNames("Transformations (*.transformation)".split("|"));
				dialog.setFilterExtensions("*.transformation".split("|"));
				String filePath = dialog.open();
				if (filePath != null) {
					fileField.setText(filePath);
				}
			}
		});
		
		m_bindingContext = initDataBindings();
		m_bindingContext.updateTargets();
		
		WizardPageSupport.create(this, m_bindingContext);
	}
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextFileFieldObserveWidget = WidgetProperties.text(SWT.Modify).observe(fileField);
		IObservableValue filePathGetExportTransformationWizardObserveValue = PojoProperties.value("filePath").observe(getExportTransformationWizard());
		bindingContext.bindValue(observeTextFileFieldObserveWidget, filePathGetExportTransformationWizardObserveValue, null, null);
		//
		IObservableValue observeSingleSelectionTransformationsViewer = ViewerProperties.singleSelection().observe(transformationsViewer);
		IObservableValue transformationGetExportTransformationWizardObserveValue = PojoProperties.value("transformation").observe(getExportTransformationWizard());
		bindingContext.bindValue(observeSingleSelectionTransformationsViewer, transformationGetExportTransformationWizardObserveValue, null, null);
		//
		return bindingContext;
	}
	
}
