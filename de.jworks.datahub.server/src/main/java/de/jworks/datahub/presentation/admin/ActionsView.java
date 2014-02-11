package de.jworks.datahub.presentation.admin;

import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@CDIView(value = "actions", uis = { AdminUI.class })
public class ActionsView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout actionsTab;
	@AutoGenerated
	private Button addActionButton;
	@AutoGenerated
	private Table actionsTable;
	@AutoGenerated
	private Label label;
	public ActionsView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		label.setContentMode(ContentMode.HTML);
		label.setValue(Icon.home + " / Actions");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setStyleName("blue");
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// label
		label = new Label();
		label.setStyleName("h2");
		label.setImmediate(false);
		label.setWidth("-1px");
		label.setHeight("-1px");
		label.setValue("Home / Actions");
		mainLayout.addComponent(label);
		
		// body
		body = buildBody();
		mainLayout.addComponent(body);
		mainLayout.setExpandRatio(body, 1.0f);
		
		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildBody() {
		// common part: create layout
		body = new VerticalLayout();
		body.setStyleName("white");
		body.setImmediate(false);
		body.setWidth("100.0%");
		body.setHeight("100.0%");
		body.setMargin(true);
		
		// tabSheet
		tabSheet = buildTabSheet();
		body.addComponent(tabSheet);
		
		return body;
	}

	@AutoGenerated
	private TabSheet buildTabSheet() {
		// common part: create layout
		tabSheet = new TabSheet();
		tabSheet.setStyleName("minimal");
		tabSheet.setImmediate(true);
		tabSheet.setWidth("100.0%");
		tabSheet.setHeight("100.0%");
		
		// actionsTab
		actionsTab = buildActionsTab();
		tabSheet.addTab(actionsTab, "Actions", null);
		
		return tabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildActionsTab() {
		// common part: create layout
		actionsTab = new VerticalLayout();
		actionsTab.setImmediate(false);
		actionsTab.setWidth("100.0%");
		actionsTab.setHeight("100.0%");
		actionsTab.setMargin(true);
		actionsTab.setSpacing(true);
		
		// actionsTable
		actionsTable = new Table();
		actionsTable.setImmediate(false);
		actionsTable.setWidth("100.0%");
		actionsTable.setHeight("100.0%");
		actionsTab.addComponent(actionsTable);
		actionsTab.setExpandRatio(actionsTable, 1.0f);
		
		// addActionButton
		addActionButton = new Button();
		addActionButton.setStyleName("link");
		addActionButton.setCaption("Add Action");
		addActionButton.setImmediate(true);
		addActionButton.setWidth("-1px");
		addActionButton.setHeight("-1px");
		actionsTab.addComponent(addActionButton);
		
		return actionsTab;
	}

}
