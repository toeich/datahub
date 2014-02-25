package de.jworks.datahub.presentation;

import javax.servlet.ServletException;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AdminLoginView extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout signInDialog;
	@AutoGenerated
	private Label message;
	@AutoGenerated
	private HorizontalLayout signInForm;
	@AutoGenerated
	private Button signInButton;
	@AutoGenerated
	private PasswordField passwordField;
	@AutoGenerated
	private TextField usernameField;
	@AutoGenerated
	private HorizontalLayout header;
	@AutoGenerated
	private Label header2;
	@AutoGenerated
	private Label header1;
	
	public AdminLoginView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		signInButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					JaasAccessControl.login(usernameField.getValue(), passwordField.getValue());
					Page.getCurrent().reload();
				} catch (ServletException e) {
					message.setValue(e.getMessage());
				}
			}
		});
		signInButton.setClickShortcut(KeyCode.ENTER);
		
		message.setValue("");
		
		Messages.translate(this);
		
		usernameField.focus();
		
		usernameField.setValue("admin");
		passwordField.setValue("passme");
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// signInDialog
		signInDialog = buildSignInDialog();
		mainLayout.addComponent(signInDialog);
		mainLayout.setComponentAlignment(signInDialog, new Alignment(48));
		
		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildSignInDialog() {
		// common part: create layout
		signInDialog = new VerticalLayout();
		signInDialog.setStyleName("blue");
		signInDialog.setImmediate(false);
		signInDialog.setWidth("500px");
		signInDialog.setHeight("-1px");
		signInDialog.setMargin(true);
		signInDialog.setSpacing(true);
		
		// header
		header = buildHeader();
		signInDialog.addComponent(header);
		
		// signInForm
		signInForm = buildSignInForm();
		signInDialog.addComponent(signInForm);
		
		// message
		message = new Label();
		message.setImmediate(false);
		message.setWidth("-1px");
		message.setHeight("-1px");
		message.setValue("Message");
		signInDialog.addComponent(message);
		
		return signInDialog;
	}

	@AutoGenerated
	private HorizontalLayout buildHeader() {
		// common part: create layout
		header = new HorizontalLayout();
		header.setImmediate(false);
		header.setWidth("100.0%");
		header.setHeight("-1px");
		header.setMargin(false);
		
		// header1
		header1 = new Label();
		header1.setStyleName("h1");
		header1.setImmediate(false);
		header1.setWidth("100.0%");
		header1.setHeight("-1px");
		header1.setValue("Welcome");
		header.addComponent(header1);
		header.setExpandRatio(header1, 1.0f);
		header.setComponentAlignment(header1, new Alignment(33));
		
		// header2
		header2 = new Label();
		header2.setStyleName("h2");
		header2.setImmediate(false);
		header2.setWidth("-1px");
		header2.setHeight("-1px");
		header2.setValue("Data Hub // Admin");
		header.addComponent(header2);
		header.setComponentAlignment(header2, new Alignment(33));
		
		return header;
	}

	@AutoGenerated
	private HorizontalLayout buildSignInForm() {
		// common part: create layout
		signInForm = new HorizontalLayout();
		signInForm.setImmediate(false);
		signInForm.setWidth("100.0%");
		signInForm.setHeight("-1px");
		signInForm.setMargin(false);
		signInForm.setSpacing(true);
		
		// usernameField
		usernameField = new TextField();
		usernameField.setCaption("Username");
		usernameField.setImmediate(false);
		usernameField.setWidth("100.0%");
		usernameField.setHeight("-1px");
		signInForm.addComponent(usernameField);
		signInForm.setExpandRatio(usernameField, 1.0f);
		signInForm.setComponentAlignment(usernameField, new Alignment(9));
		
		// passwordField
		passwordField = new PasswordField();
		passwordField.setCaption("Password");
		passwordField.setImmediate(false);
		passwordField.setWidth("100.0%");
		passwordField.setHeight("-1px");
		signInForm.addComponent(passwordField);
		signInForm.setExpandRatio(passwordField, 1.0f);
		signInForm.setComponentAlignment(passwordField, new Alignment(9));
		
		// signInButton
		signInButton = new Button();
		signInButton.setCaption("Sign In");
		signInButton.setImmediate(true);
		signInButton.setWidth("-1px");
		signInButton.setHeight("-1px");
		signInForm.addComponent(signInButton);
		signInForm.setComponentAlignment(signInButton, new Alignment(9));
		
		return signInForm;
	}

}
