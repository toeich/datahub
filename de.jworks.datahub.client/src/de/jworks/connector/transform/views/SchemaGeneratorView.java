package de.jworks.connector.transform.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import de.jworks.connector.transform.tools.SchemaGenerator;
import de.jworks.connector.transform.tools.SchemaGenerator.SchemaType;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SchemaGeneratorView extends ViewPart {

	public static final String ID = "de.jworks.fps.presentation.transform.views.SchemaGeneratorView"; //$NON-NLS-1$

	private Button btnDatasource;
	private Button btnDatasink;
	private Text xmlField;
	private Text schemaField;

	public SchemaGeneratorView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		
		btnDatasource = new Button(container, SWT.RADIO);
		btnDatasource.setText("Datasource");
		btnDatasource.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateSchemaField();
			}
		});
		
		btnDatasink = new Button(container, SWT.RADIO);
		btnDatasink.setText("Datasink");
		btnDatasink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateSchemaField();
			}
		});
		
		xmlField = new Text(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		xmlField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		xmlField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateSchemaField();
			}
		});
		
		schemaField = new Text(container, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		schemaField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	private void updateSchemaField() {
		try {
			SchemaType schemaType = btnDatasink.getSelection() ? SchemaType.DATASINK : SchemaType.DATASOURCE;
			schemaField.setText(SchemaGenerator.generateSchemaFromXml(xmlField.getText(), schemaType));
		} catch (Exception ex) {
			schemaField.setText("");
		}
	}
	
	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		@SuppressWarnings("unused")
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		@SuppressWarnings("unused")
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
