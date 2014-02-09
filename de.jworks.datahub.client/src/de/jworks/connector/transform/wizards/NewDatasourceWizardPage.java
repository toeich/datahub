package de.jworks.connector.transform.wizards;

import java.util.Map;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import de.jworks.connector.transform.tools.SchemaGenerator;
import de.jworks.connector.transform.tools.SchemaGenerator.SchemaType;
import de.jworks.datahub.business.transform.entity.Datasource;

public class NewDatasourceWizardPage extends WizardPage {

	private DataBindingContext m_bindingContext;

	private Combo nameField;
	private Text schemaField;
	private Text routeSpecField;

	/**
	 * Create the wizard.
	 */
	public NewDatasourceWizardPage() {
		super("wizardPage");
		setTitle("Datasource");
		setDescription("Wizard Page description");
	}

	@Override
	public NewDatasourceWizard getWizard() {
		return (NewDatasourceWizard) super.getWizard();
	}

	public Datasource getDatasource() {
		return getWizard().getDatasource();
	}

	public Map<String, Datasource> getTemplates() {
		return getWizard().getTemplates();
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		Label lblName = new Label(container, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name:");

		nameField = new Combo(container, SWT.BORDER);
		nameField.setItems(getTemplates().keySet().toArray(new String[0]));
		nameField.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Datasource template = getTemplates().get(nameField.getText());
				if (template != null) {
					getDatasource().setName(template.getName());
					getDatasource().setSchema(template.getSchema());
					getDatasource().setRouteSpec(template.getRouteSpec());
					m_bindingContext.updateTargets();
				}
			}
		});
		nameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblSchema = new Label(container, SWT.NONE);
		lblSchema.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSchema.setText("Schema:");

		Composite schemaComposite = new Composite(container, SWT.NONE);
		GridLayout gl_schemaComposite = new GridLayout(1, false);
		gl_schemaComposite.marginHeight = 0;
		gl_schemaComposite.marginWidth = 0;
		schemaComposite.setLayout(gl_schemaComposite);
		schemaComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		schemaField = new Text(schemaComposite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		schemaField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Link generateSchemaLink = new Link(schemaComposite, SWT.NONE);
		generateSchemaLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					InputDialog dialog = new InputDialog(getShell(), "Generate Schema", "Enter the URL of the prototype.", null, null);
					if (dialog.open() == InputDialog.OK) {
						schemaField.setText(SchemaGenerator.generateSchemaFromUrl(dialog.getValue(), SchemaType.DATASOURCE));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		generateSchemaLink.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		generateSchemaLink.setText("<a>Generate Schema...</a>");

		Label lblRouteSpec = new Label(container, SWT.NONE);
		lblRouteSpec.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRouteSpec.setText("Route Spec:");

		Composite routeSpecComposite = new Composite(container, SWT.BORDER);
		GridLayout gl_routeSpecComposite = new GridLayout(1, false);
		gl_routeSpecComposite.verticalSpacing = 0;
		gl_routeSpecComposite.horizontalSpacing = 0;
		gl_routeSpecComposite.marginHeight = 0;
		gl_routeSpecComposite.marginWidth = 0;
		routeSpecComposite.setLayout(gl_routeSpecComposite);
		GridData gd_routeSpecComposite = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_routeSpecComposite.heightHint = 150;
		routeSpecComposite.setLayoutData(gd_routeSpecComposite);

		Label lblRouteSpecPrefix = new Label(routeSpecComposite, SWT.NONE);
		lblRouteSpecPrefix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblRouteSpecPrefix.setText("<route id='datasource-id'>");

		routeSpecField = new Text(routeSpecComposite, SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		routeSpecField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label lblRouteSpecPostfix = new Label(routeSpecComposite, SWT.NONE);
		lblRouteSpecPostfix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblRouteSpecPostfix.setText(" <multicast>\n  ...\n </multicast>\n</route>");

		m_bindingContext = initDataBindings();

		WizardPageSupport.create(this, m_bindingContext);
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextNameFieldObserveWidget = WidgetProperties.text().observe(nameField);
		IObservableValue nameGetDatasourceObserveValue = BeanProperties.value("name").observe(getDatasource());
		bindingContext.bindValue(observeTextNameFieldObserveWidget, nameGetDatasourceObserveValue, null, null);
		//
		IObservableValue observeTextSchemaFieldObserveWidget = WidgetProperties.text(SWT.Modify).observe(schemaField);
		IObservableValue schemaDataGetDatasourceObserveValue = BeanProperties.value("schemaData").observe(getDatasource());
		bindingContext.bindValue(observeTextSchemaFieldObserveWidget, schemaDataGetDatasourceObserveValue, null, null);
		//
		IObservableValue observeTextRouteSpecFieldObserveWidget = WidgetProperties.text(SWT.Modify).observe(routeSpecField);
		IObservableValue routeSpecGetDatasourceObserveValue = BeanProperties.value("routeSpec").observe(getDatasource());
		bindingContext.bindValue(observeTextRouteSpecFieldObserveWidget, routeSpecGetDatasourceObserveValue, null, null);
		//
		return bindingContext;
	}
}
