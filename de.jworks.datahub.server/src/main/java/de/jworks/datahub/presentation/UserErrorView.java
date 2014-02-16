package de.jworks.datahub.presentation;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;

public class UserErrorView extends CustomComponent implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		UI.getCurrent().getNavigator().navigateTo("projects#");
	}

}
