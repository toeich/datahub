package de.jworks.datahub.presentation.admin;

import javax.inject.Inject;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.jworks.datahub.business.datasets.boundary.DatasetService;
import de.jworks.datahub.business.datasets.entity.Dataset;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.presentation.AdminUI;
import de.jworks.datahub.presentation.Messages;
import de.jworks.datahub.presentation.editors.DatasetEditor;
import de.jworks.datahub.presentation.editors.DatasetSchemaEditor;

@CDIView(value = "datasetgroup!", supportsParameters = true, uis = { AdminUI.class })
public class DatasetGroupView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout body;
	@AutoGenerated
	private HorizontalLayout buttons;
	@AutoGenerated
	private Button cancelButton;
	@AutoGenerated
	private Button saveButton;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout datasetSchemaTab;
	@AutoGenerated
	private Button testButton;
	@AutoGenerated
	private DatasetSchemaEditor schemaEditor;
	@AutoGenerated
	private VerticalLayout datasetGroupTab;
	@AutoGenerated
	private TextArea description;
	@AutoGenerated
	private TextField name;
	@AutoGenerated
	private Label label;
	
	@Inject
	DatasetService datasetService;
	
	private FieldGroup fieldGroup;
	
	private DatasetGroup datasetGroup;
	
	public DatasetGroupView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		Messages.translate(this);
		
		datasetGroupTab.setMargin(new MarginInfo(true, false, false, false));
		
		datasetSchemaTab.setMargin(new MarginInfo(true, false, false, false));
		
		fieldGroup = new FieldGroup();
		
		saveButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit();
					if (datasetGroup.getId() == null) {
						datasetService.addDatasetGroup(datasetGroup);
					} else {
						datasetService.updateDatasetGroup(datasetGroup);
					}
					UI.getCurrent().getNavigator().navigateTo("datasetgroups!/");
				} catch (CommitException e) {
					Notification.show("Error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
			}
		});
		
		cancelButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("datasetgroups!/");
			}
		});
		
		testButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Dataset dataset = new Dataset();
				dataset.setGroup(datasetGroup);
				Window window = new Window();
				window.setWidth("80%");
				window.setHeight("80%");
				window.setModal(true);
				DatasetEditor datasetEditor = new DatasetEditor();
				datasetEditor.setDataset(dataset);
				window.setContent(datasetEditor);
				UI.getCurrent().addWindow(window);
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		try {
			try {
				long datasetGroupId = Long.parseLong(event.getParameters());
				datasetGroup = datasetService.getDatasetGroup(datasetGroupId);
			} catch (Exception e) {
				datasetGroup = new DatasetGroup();
				datasetGroup.setName("New Dataset Group");
			}

			label.setValue("<li class='icon-home'></li> / Dataset Groups / " + datasetGroup.getName());

			tabSheet.setSelectedTab(datasetGroupTab);

			fieldGroup.setItemDataSource(new BeanItem<DatasetGroup>(datasetGroup));
			fieldGroup.bindMemberFields(this);

			schemaEditor.setDocumentSchema(datasetGroup.getSchema());
		} catch (Exception e) {
			UI.getCurrent().getNavigator().navigateTo("datasetgroups!/");
		}
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
		label.setValue("Home / Dataset Groups / Dataset Group");
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
		body.setSpacing(true);
		
		// tabSheet
		tabSheet = buildTabSheet();
		body.addComponent(tabSheet);
		body.setExpandRatio(tabSheet, 1.0f);
		
		// buttons
		buttons = buildButtons();
		body.addComponent(buttons);
		
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
		
		// datasetGroupTab
		datasetGroupTab = buildDatasetGroupTab();
		tabSheet.addTab(datasetGroupTab, "Dataset Group", null);
		
		// datasetSchemaTab
		datasetSchemaTab = buildDatasetSchemaTab();
		tabSheet.addTab(datasetSchemaTab, "Schema", null);
		
		return tabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildDatasetGroupTab() {
		// common part: create layout
		datasetGroupTab = new VerticalLayout();
		datasetGroupTab.setImmediate(false);
		datasetGroupTab.setWidth("100.0%");
		datasetGroupTab.setHeight("-1px");
		datasetGroupTab.setMargin(true);
		datasetGroupTab.setSpacing(true);
		
		// name
		name = new TextField();
		name.setCaption("Name");
		name.setImmediate(false);
		name.setWidth("100.0%");
		name.setHeight("-1px");
		name.setRequired(true);
		datasetGroupTab.addComponent(name);
		
		// description
		description = new TextArea();
		description.setCaption("Description");
		description.setImmediate(false);
		description.setWidth("100.0%");
		description.setHeight("-1px");
		datasetGroupTab.addComponent(description);
		
		return datasetGroupTab;
	}

	@AutoGenerated
	private VerticalLayout buildDatasetSchemaTab() {
		// common part: create layout
		datasetSchemaTab = new VerticalLayout();
		datasetSchemaTab.setImmediate(false);
		datasetSchemaTab.setWidth("100.0%");
		datasetSchemaTab.setHeight("100.0%");
		datasetSchemaTab.setMargin(true);
		datasetSchemaTab.setSpacing(true);
		
		// schemaEditor
		schemaEditor = new DatasetSchemaEditor();
		schemaEditor.setImmediate(false);
		schemaEditor.setWidth("100.0%");
		schemaEditor.setHeight("100.0%");
		datasetSchemaTab.addComponent(schemaEditor);
		datasetSchemaTab.setExpandRatio(schemaEditor, 1.0f);
		
		// testButton
		testButton = new Button();
		testButton.setStyleName("small");
		testButton.setCaption("Test");
		testButton.setImmediate(true);
		testButton.setWidth("-1px");
		testButton.setHeight("-1px");
		datasetSchemaTab.addComponent(testButton);
		
		return datasetSchemaTab;
	}

	@AutoGenerated
	private HorizontalLayout buildButtons() {
		// common part: create layout
		buttons = new HorizontalLayout();
		buttons.setImmediate(false);
		buttons.setWidth("-1px");
		buttons.setHeight("-1px");
		buttons.setMargin(false);
		buttons.setSpacing(true);
		
		// saveButton
		saveButton = new Button();
		saveButton.setStyleName("primary");
		saveButton.setCaption("Save");
		saveButton.setImmediate(true);
		saveButton.setWidth("-1px");
		saveButton.setHeight("-1px");
		buttons.addComponent(saveButton);
		
		// cancelButton
		cancelButton = new Button();
		cancelButton.setCaption("Cancel");
		cancelButton.setImmediate(true);
		cancelButton.setWidth("-1px");
		cancelButton.setHeight("-1px");
		buttons.addComponent(cancelButton);
		
		return buttons;
	}

}
