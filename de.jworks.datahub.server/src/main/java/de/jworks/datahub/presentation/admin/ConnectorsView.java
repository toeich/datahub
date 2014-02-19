package de.jworks.datahub.presentation.admin;

import java.util.Arrays;

import javax.inject.Inject;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.business.connectors.boundary.ConnectorService;
import de.jworks.datahub.business.connectors.entity.Connector;
import de.jworks.datahub.presentation.AdminUI;
import de.jworks.datahub.presentation.Messages;

@CDIView(value = "connectors!", uis = { AdminUI.class })
public class ConnectorsView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout connectorsTab;
	@AutoGenerated
	private Button addConnectorButton;
	@AutoGenerated
	private Table connectorsTable;
	@AutoGenerated
	private Label label;
	@Inject
	ConnectorService connectorService;
	
	private BeanItemContainer<Connector> connectors = new BeanItemContainer<Connector>(Connector.class);
	
	public ConnectorsView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		Messages.translate(this);
		
		connectorsTab.setMargin(new MarginInfo(true, false, false, false));
		
		connectorsTable.addGeneratedColumn("actions", new ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final Connector connector = connectors.getItem(itemId).getBean();
				HorizontalLayout actions = new HorizontalLayout();
				actions.setSpacing(true);
				Button editButton = new Button();
				editButton.setStyleName("link");
				editButton.setCaption(Messages.getString("edit"));
				editButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().getNavigator().navigateTo("connector!/" + connector.getId());
					}
				});
				actions.addComponent(editButton);
				Button removeButton = new Button();
				removeButton.setStyleName("link");
				removeButton.setCaption(Messages.getString("delete"));
				removeButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						connectorService.removeConnector(connector);
						refreshConnectorsTable();
					}
				});
				actions.addComponent(removeButton);
				return actions;
			}
		});
		connectorsTable.setContainerDataSource(connectors, Arrays.asList("id", "name", "actions"));
		connectorsTable.setColumnExpandRatio("name", 1.0f);
		
		addConnectorButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("connector!/");
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refreshConnectorsTable();
	}
	
	private void refreshConnectorsTable() {
		connectors.removeAllItems();
		connectors.addAll(connectorService.getConnectors());
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
		label.setValue("Home / Connectors");
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
		
		// connectorsTab
		connectorsTab = buildConnectorsTab();
		tabSheet.addTab(connectorsTab, "Connectors", null);
		
		return tabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildConnectorsTab() {
		// common part: create layout
		connectorsTab = new VerticalLayout();
		connectorsTab.setImmediate(false);
		connectorsTab.setWidth("100.0%");
		connectorsTab.setHeight("100.0%");
		connectorsTab.setMargin(true);
		connectorsTab.setSpacing(true);
		
		// connectorsTable
		connectorsTable = new Table();
		connectorsTable.setImmediate(false);
		connectorsTable.setWidth("100.0%");
		connectorsTable.setHeight("100.0%");
		connectorsTab.addComponent(connectorsTable);
		connectorsTab.setExpandRatio(connectorsTable, 1.0f);
		
		// addConnectorButton
		addConnectorButton = new Button();
		addConnectorButton.setStyleName("link");
		addConnectorButton.setCaption("Add Connector");
		addConnectorButton.setImmediate(true);
		addConnectorButton.setWidth("-1px");
		addConnectorButton.setHeight("-1px");
		connectorsTab.addComponent(addConnectorButton);
		
		return connectorsTab;
	}

}
