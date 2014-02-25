package de.jworks.datahub.presentation.user;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.business.datasets.boundary.DatasetService;
import de.jworks.datahub.business.datasets.entity.Dataset;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.business.util.XMLUtil;
import de.jworks.datahub.presentation.Messages;
import de.jworks.datahub.presentation.UserUI;
import de.jworks.datahub.presentation.editors.DatasetEditor;

@CDIView(value = "dataset!", supportsParameters = true, uis = { UserUI.class })
public class DatasetView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout aboutTab;
	@AutoGenerated
	private Label aboutText;
	@AutoGenerated
	private VerticalLayout datasetTab;
	@AutoGenerated
	private HorizontalLayout buttons;
	@AutoGenerated
	private Button cancelButton;
	@AutoGenerated
	private Button saveButton;
	@AutoGenerated
	private DatasetEditor datasetEditor;
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
	
	private Dataset dataset;
	
	public DatasetView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		infoPanel.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				tabSheet.setSelectedTab(aboutTab);
			}
		});
		
		datasetTab.setMargin(new MarginInfo(true, false, false, false));
		
		aboutTab.setMargin(new MarginInfo(true, false, false, false));
		
		saveButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (dataset.getId() == null) {
					datasetService.addDataset(dataset);
				} else {
					datasetService.updateDataset(dataset);
				}
				UI.getCurrent().getNavigator().navigateTo("datasetgroup!/" + dataset.getGroup().getId());
			}
		});
		
		cancelButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("datasetgroup!/" + dataset.getGroup().getId());
			}
		});
		
		Messages.translate(this);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		try {
			try {
				long datasetId = Long.parseLong(event.getParameters());
				dataset = datasetService.getDataset(datasetId);
			} catch (Exception e) {
				String s = event.getParameters();
				s = s.substring(s.indexOf("@")+1);
				long datasetGroupId =  Long.parseLong(s);
				DatasetGroup datasetGroup = datasetService.getDatasetGroup(datasetGroupId);
				dataset = new Dataset();
				dataset.setGroup(datasetGroup);
			}
			
			DatasetGroup datasetGroup = dataset.getGroup();
			
			String datasetName = "";
			try {
				datasetName = XMLUtil.evaluate(datasetGroup.getColumns().get(0).getFormat(), dataset.getDocument());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			String datasetDescription = "";
			try {
				datasetDescription = XMLUtil.evaluate(datasetGroup.getColumns().get(1).getFormat(), dataset.getDocument());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			label.setValue(Messages.format(this, "label_value", datasetGroup.getName(), datasetName));

			name.setValue(datasetName);
			description.setValue(StringUtils.abbreviate(datasetDescription, 100));
			
			aboutText.setValue(datasetDescription); // TODO

			datasetEditor.setDataset(dataset);
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
		label.setValue("Home / Dataset Groups / Dataset Group / Dataset");
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
		name.setWidth("100.0%");
		name.setHeight("-1px");
		name.setValue("Name");
		infoPanel.addComponent(name);
		
		// description
		description = new Label();
		description.setImmediate(false);
		description.setWidth("100.0%");
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
		
		// datasetTab
		datasetTab = buildDatasetTab();
		tabSheet.addTab(datasetTab, "Dataset", null);
		
		// aboutTab
		aboutTab = buildAboutTab();
		tabSheet.addTab(aboutTab, "About", null);
		
		return tabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildDatasetTab() {
		// common part: create layout
		datasetTab = new VerticalLayout();
		datasetTab.setImmediate(false);
		datasetTab.setWidth("100.0%");
		datasetTab.setHeight("100.0%");
		datasetTab.setMargin(true);
		datasetTab.setSpacing(true);
		
		// datasetEditor
		datasetEditor = new DatasetEditor();
		datasetEditor.setImmediate(false);
		datasetEditor.setWidth("100.0%");
		datasetEditor.setHeight("100.0%");
		datasetTab.addComponent(datasetEditor);
		datasetTab.setExpandRatio(datasetEditor, 1.0f);
		
		// buttons
		buttons = buildButtons();
		datasetTab.addComponent(buttons);
		
		return datasetTab;
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

	@AutoGenerated
	private VerticalLayout buildAboutTab() {
		// common part: create layout
		aboutTab = new VerticalLayout();
		aboutTab.setImmediate(false);
		aboutTab.setWidth("100.0%");
		aboutTab.setHeight("100.0%");
		aboutTab.setMargin(true);
		
		// aboutText
		aboutText = new Label();
		aboutText.setImmediate(false);
		aboutText.setWidth("-1px");
		aboutText.setHeight("-1px");
		aboutText.setValue("About Text");
		aboutTab.addComponent(aboutText);
		
		return aboutTab;
	}

}
