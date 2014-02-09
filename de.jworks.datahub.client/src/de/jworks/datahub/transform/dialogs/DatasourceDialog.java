package de.jworks.datahub.transform.dialogs;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.dialog.TitleAreaDialogSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.jworks.datahub.business.transform.entity.Datasource;

public class DatasourceDialog extends TitleAreaDialog {
	
	private DataBindingContext m_bindingContext;

	private Text nameField;
	private Text schemaField;
	private Text routeSpecField;
	
	private Datasource datasource;
	private Label lblRouteSpecPrefix;
	private Label lblRouteSpecPostfix;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DatasourceDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE);
	}

	public void setDatasource(Datasource datasource) {
		this.datasource = datasource;
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Datasource");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		GridLayout gl_container = new GridLayout(2, false);
		container.setLayout(gl_container);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblName = new Label(container, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name:");
		
		nameField = new Text(container, SWT.BORDER);
		nameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblSchema = new Label(container, SWT.NONE);
		lblSchema.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSchema.setText("Schema:");
		
		schemaField = new Text(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		schemaField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblRouteSpec = new Label(container, SWT.NONE);
		lblRouteSpec.setText("Route Spec:");
		
		Composite routeSpecComposite = new Composite(container, SWT.BORDER);
		GridLayout gl_routeSpecComposite = new GridLayout(1, false);
		gl_routeSpecComposite.verticalSpacing = 0;
		gl_routeSpecComposite.horizontalSpacing = 0;
		gl_routeSpecComposite.marginHeight = 0;
		gl_routeSpecComposite.marginWidth = 0;
		routeSpecComposite.setLayout(gl_routeSpecComposite);
		GridData gd_routeSpecComposite = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_routeSpecComposite.heightHint = 150;
		routeSpecComposite.setLayoutData(gd_routeSpecComposite);
		
		lblRouteSpecPrefix = new Label(routeSpecComposite, SWT.READ_ONLY);
		lblRouteSpecPrefix.setText("<route id='datasource-id'>");
		lblRouteSpecPrefix.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		routeSpecField = new Text(routeSpecComposite, SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		routeSpecField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		lblRouteSpecPostfix = new Label(routeSpecComposite, SWT.READ_ONLY);
		lblRouteSpecPostfix.setText(" <multicast>\n  ...\n </multicast>\n</route>");
		lblRouteSpecPostfix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		
		m_bindingContext = initDataBindings();
		
		TitleAreaDialogSupport.create(this, m_bindingContext);
	}
	
	protected Point getInitialSize() {
		return new Point(640, 480);
	}
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextNameFieldObserveWidget = WidgetProperties.text(SWT.Modify).observe(nameField);
		IObservableValue nameDatasourceObserveValue = BeanProperties.value("name").observe(datasource);
		bindingContext.bindValue(observeTextNameFieldObserveWidget, nameDatasourceObserveValue, null, null);
		//
		IObservableValue observeTextSchemaFieldObserveWidget = WidgetProperties.text(SWT.Modify).observe(schemaField);
		IObservableValue schemaDataDatasourceObserveValue = BeanProperties.value("schemaData").observe(datasource);
		bindingContext.bindValue(observeTextSchemaFieldObserveWidget, schemaDataDatasourceObserveValue, null, null);
		//
		IObservableValue observeTextRouteSpecFieldObserveWidget = WidgetProperties.text(SWT.Modify).observe(routeSpecField);
		IObservableValue routeSpecDatasourceObserveValue = BeanProperties.value("routeSpec").observe(datasource);
		bindingContext.bindValue(observeTextRouteSpecFieldObserveWidget, routeSpecDatasourceObserveValue, null, null);
		//
		return bindingContext;
	}
	
}
