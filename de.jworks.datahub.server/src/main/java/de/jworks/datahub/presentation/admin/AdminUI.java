package de.jworks.datahub.presentation.admin;

import javax.inject.Inject;

import com.porotype.iconfont.FontAwesome;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.access.AccessControl;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIUI("admin")
public class AdminUI extends UI {
	
	private VerticalLayout root;
	
	private VerticalLayout content;
	
	@Inject
	AccessControl accessControl;
	
	@Inject
	LoginView loginView;
	
	@Inject
	Header1 header1;
	@Inject
	Header2 header2;
	
	@Inject
	CDIViewProvider provider;

	@Override
	protected void init(VaadinRequest request) {
		FontAwesome.load();
		
		Page.getCurrent().setTitle("Data Hub // Admin");
		
		root = new VerticalLayout();
		root.setSizeFull();
		setContent(root);
		
		if (accessControl.isUserSignedIn()) {
			root.removeAllComponents();
			
			root.addComponent(header1);
			root.addComponent(header2);
			
			content = new VerticalLayout();
			content.setSizeFull();
			root.addComponent(content);
			root.setExpandRatio(content, 1.0f);
			
			Navigator navigator = new Navigator(this, content);
			navigator.addProvider(provider);
		} else {
			root.removeAllComponents();
			root.addComponent(loginView);
		}
	}

}
