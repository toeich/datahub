package de.jworks.datahub.presentation.admin;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.presentation.AdminUI;
import de.jworks.datahub.presentation.Messages;

@CDIView(value = "processes!", uis = { AdminUI.class })
public class ProcessesView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout processesTab;
	@AutoGenerated
	private Button addProcessButton;
	@AutoGenerated
	private Table processesTable;
	@AutoGenerated
	private Label label;
	
	public ProcessesView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		processesTab.setMargin(new MarginInfo(true, false, false, false));

		Messages.translate(this);
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
		label.setValue("Home / Processes");
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
		
		// processesTab
		processesTab = buildProcessesTab();
		tabSheet.addTab(processesTab, "Processes", null);
		
		return tabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildProcessesTab() {
		// common part: create layout
		processesTab = new VerticalLayout();
		processesTab.setImmediate(false);
		processesTab.setWidth("100.0%");
		processesTab.setHeight("100.0%");
		processesTab.setMargin(true);
		processesTab.setSpacing(true);
		
		// processesTable
		processesTable = new Table();
		processesTable.setImmediate(false);
		processesTable.setWidth("100.0%");
		processesTable.setHeight("100.0%");
		processesTab.addComponent(processesTable);
		processesTab.setExpandRatio(processesTable, 1.0f);
		
		// addProcessButton
		addProcessButton = new Button();
		addProcessButton.setStyleName("link");
		addProcessButton.setCaption("Add Process");
		addProcessButton.setImmediate(true);
		addProcessButton.setWidth("-1px");
		addProcessButton.setHeight("-1px");
		processesTab.addComponent(addProcessButton);
		
		return processesTab;
	}

}
