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

@CDIView(value = "dataset-groups", uis = { AdminUI.class })
public class DatasetGroupsView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout datasetGroupsTab;
	@AutoGenerated
	private Button addDatasetGroupButton;
	@AutoGenerated
	private Table datasetGroupsTable;
	@AutoGenerated
	private Label label;
	public DatasetGroupsView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		label.setContentMode(ContentMode.HTML);
		label.setValue(Icon.home + " / Dataset Groups");
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
		label.setValue("Home / Dataset Groups");
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
		
		// datasetGroupsTab
		datasetGroupsTab = buildDatasetGroupsTab();
		tabSheet.addTab(datasetGroupsTab, "Dataset Groups", null);
		
		return tabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildDatasetGroupsTab() {
		// common part: create layout
		datasetGroupsTab = new VerticalLayout();
		datasetGroupsTab.setImmediate(false);
		datasetGroupsTab.setWidth("100.0%");
		datasetGroupsTab.setHeight("100.0%");
		datasetGroupsTab.setMargin(true);
		datasetGroupsTab.setSpacing(true);
		
		// datasetGroupsTable
		datasetGroupsTable = new Table();
		datasetGroupsTable.setImmediate(false);
		datasetGroupsTable.setWidth("100.0%");
		datasetGroupsTable.setHeight("100.0%");
		datasetGroupsTab.addComponent(datasetGroupsTable);
		datasetGroupsTab.setExpandRatio(datasetGroupsTable, 1.0f);
		
		// addDatasetGroupButton
		addDatasetGroupButton = new Button();
		addDatasetGroupButton.setStyleName("link");
		addDatasetGroupButton.setCaption("Add Dataset Group");
		addDatasetGroupButton.setImmediate(true);
		addDatasetGroupButton.setWidth("-1px");
		addDatasetGroupButton.setHeight("-1px");
		datasetGroupsTab.addComponent(addDatasetGroupButton);
		
		return datasetGroupsTab;
	}

}
