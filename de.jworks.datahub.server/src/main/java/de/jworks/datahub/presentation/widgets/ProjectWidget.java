package de.jworks.datahub.presentation.widgets;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;

public class ProjectWidget extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	public ProjectWidget() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private void buildMainLayout() {
		// the main layout and components will be created here
		mainLayout = new AbsoluteLayout();
	}

}
