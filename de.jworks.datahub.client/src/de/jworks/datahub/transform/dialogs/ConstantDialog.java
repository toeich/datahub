package de.jworks.datahub.transform.dialogs;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.dialog.TitleAreaDialogSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.jworks.datahub.business.transform.entity.Constant;

public class ConstantDialog extends TitleAreaDialog {
	
	private DataBindingContext m_bindingContext;

	private Text valueField;
	
	private Constant constant;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ConstantDialog(Shell parentShell) {
		super(parentShell);
	}

	public void setConstant(Constant constant) {
		this.constant = constant;
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Constant");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblValue = new Label(container, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblValue.setText("Value:");
		
		valueField = new Text(container, SWT.BORDER);
		valueField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

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
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextValueFieldObserveWidget = WidgetProperties.text(SWT.Modify).observe(valueField);
		IObservableValue valueConstantObserveValue = BeanProperties.value("value").observe(constant);
		bindingContext.bindValue(observeTextValueFieldObserveWidget, valueConstantObserveValue, null, null);
		//
		return bindingContext;
	}
}
