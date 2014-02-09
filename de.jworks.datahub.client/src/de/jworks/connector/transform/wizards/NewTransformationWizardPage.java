package de.jworks.connector.transform.wizards;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

import de.jworks.datahub.business.transform.boundary.DatasinkService;
import de.jworks.datahub.business.transform.boundary.DatasourceService;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationDefinition;

public class NewTransformationWizardPage extends WizardPage {
	
	private DataBindingContext m_bindingContext;
	
	private ComboViewer datasourceViewer;
	private ComboViewer datasinkViewer;

	/**
	 * Create the wizard.
	 */
	public NewTransformationWizardPage() {
		super("wizardPage");
		setTitle("Transformation");
		setDescription("Wizard Page description");
	}

	@Override
	public NewTransformationWizard getWizard() {
		return (NewTransformationWizard) super.getWizard();
	}
	
	public Transformation getTransformation() {
		return getWizard().getTransformation();
	}
	
	public TransformationDefinition getDefinition() {
		return getTransformation().getDefinition();
	}
	
	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Label lblDatasource = new Label(container, SWT.NONE);
		lblDatasource.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDatasource.setText("Datasource:");
		
		datasourceViewer = new ComboViewer(container, SWT.READ_ONLY);
		datasourceViewer.setContentProvider(new ArrayContentProvider());
		datasourceViewer.setLabelProvider(new LabelProvider());
		datasourceViewer.setSorter(new ViewerSorter());
		Combo datasourceCombo = datasourceViewer.getCombo();
		datasourceCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDatasink = new Label(container, SWT.NONE);
		lblDatasink.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDatasink.setText("Datasink:");
		
		datasinkViewer = new ComboViewer(container, SWT.READ_ONLY);
		datasinkViewer.setContentProvider(new ArrayContentProvider());
		datasinkViewer.setLabelProvider(new LabelProvider());
		datasinkViewer.setSorter(new ViewerSorter());
		Combo datasinkCombo = datasinkViewer.getCombo();
		datasinkCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		m_bindingContext = initDataBindings();
		
		DatasourceService datasourceService = (DatasourceService) PlatformUI.getWorkbench().getService(DatasourceService.class);
		datasourceViewer.setInput(datasourceService.getDatasources());
		
		DatasinkService datasinkService = (DatasinkService) PlatformUI.getWorkbench().getService(DatasinkService.class);
		datasinkViewer.setInput(datasinkService.getDatasinks());

		WizardPageSupport.create(this, m_bindingContext);
	}
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeSingleSelectionDatasourceViewer = ViewerProperties.singleSelection().observe(datasourceViewer);
		IObservableValue datasourceGetDefinitionObserveValue = BeanProperties.value("datasource").observe(getDefinition());
		bindingContext.bindValue(observeSingleSelectionDatasourceViewer, datasourceGetDefinitionObserveValue, null, null);
		//
		IObservableValue observeSingleSelectionDatasinkViewer = ViewerProperties.singleSelection().observe(datasinkViewer);
		IObservableValue datasinkGetDefinitionObserveValue = BeanProperties.value("datasink").observe(getDefinition());
		bindingContext.bindValue(observeSingleSelectionDatasinkViewer, datasinkGetDefinitionObserveValue, null, null);
		//
		return bindingContext;
	}
}
