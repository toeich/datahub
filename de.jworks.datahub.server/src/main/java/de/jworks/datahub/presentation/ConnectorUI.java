package de.jworks.datahub.presentation;

import javax.inject.Inject;

import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import de.jworks.datahub.presentation.views.LoginView;

@CDIUI
public class ConnectorUI extends UI {

	@Inject
	CDIViewProvider viewProvider;
	
	@Inject
	LoginView loginView;
	
	@Override
	protected void init(VaadinRequest request) {
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
		navigator.setErrorView(loginView);
	}

}
