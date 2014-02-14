package de.jworks.datahub.presentation.user;

import java.util.Arrays;

import javax.inject.Inject;

import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.business.datasets.boundary.DatasetService;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.presentation.Messages;
import de.jworks.datahub.presentation.UserUI;

@CDIView(value = "alldatasetgroups", uis = { UserUI.class })
public class DatasetGroupsView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
	
	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout datasetGroupsTab;
	@AutoGenerated
	private Table datasetGroupsTable;
	@AutoGenerated
	private VerticalLayout infoPanel;
	@AutoGenerated
	private Label description;
	@AutoGenerated
	private Label name;
	@AutoGenerated
	private Label label;
	@Inject
	DatasetService datasetService;
	
	private BeanItemContainer<DatasetGroup> datasetGroups = new BeanItemContainer<DatasetGroup>(DatasetGroup.class);
	
	public DatasetGroupsView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		Messages.translate(this);

		label.setContentMode(ContentMode.HTML);
		label.setValue(Icon.home + " / Dataset Groups");
		
		datasetGroupsTab.setMargin(new MarginInfo(true, false, false, false));
//		datasetGroupsTable.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		datasetGroupsTable.setContainerDataSource(datasetGroups, Arrays.asList("name", "description"));
		datasetGroupsTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				DatasetGroup  datasetGroup = datasetGroups.getItem(event.getItemId()).getBean();
				UI.getCurrent().getNavigator().navigateTo(String.format("datasetgroup/" + datasetGroup.getId()));
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refreshDatasetGroupTable();
	}
	
	private void refreshDatasetGroupTable() {
		datasetGroups.removeAllItems();
		datasetGroups.addAll(datasetService.getDatasetGroups());
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
	private HorizontalLayout buildBody() {
		// common part: create layout
		body = new HorizontalLayout();
		body.setStyleName("white");
		body.setImmediate(false);
		body.setWidth("100.0%");
		body.setHeight("100.0%");
		body.setMargin(true);
		body.setSpacing(true);
		
		// infoPanel
		infoPanel = buildInfoPanel();
		body.addComponent(infoPanel);
		
		// tabSheet
		tabSheet = buildTabSheet();
		body.addComponent(tabSheet);
		body.setExpandRatio(tabSheet, 1.0f);
		
		return body;
	}

	@AutoGenerated
	private VerticalLayout buildInfoPanel() {
		// common part: create layout
		infoPanel = new VerticalLayout();
		infoPanel.setImmediate(false);
		infoPanel.setWidth("200px");
		infoPanel.setHeight("-1px");
		infoPanel.setMargin(false);
		
		// name
		name = new Label();
		name.setStyleName("h1");
		name.setImmediate(false);
		name.setWidth("-1px");
		name.setHeight("-1px");
		name.setValue("Dataset Groups");
		infoPanel.addComponent(name);
		
		// description
		description = new Label();
		description.setImmediate(false);
		description.setWidth("-1px");
		description.setHeight("-1px");
		description.setValue("Description");
		infoPanel.addComponent(description);
		
		return infoPanel;
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
		
		return datasetGroupsTab;
	}

}