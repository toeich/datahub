package de.jworks.datahub.presentation.admin;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
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

import de.jworks.datahub.business.common.entity.Project;
import de.jworks.datahub.business.projects.boundary.ProjectService;
import de.jworks.datahub.presentation.AdminUI;
import de.jworks.datahub.presentation.Messages;

@CDIView(value = "project!", supportsParameters = true, uis = { AdminUI.class })
public class ProjectView extends CustomComponent implements View {

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
	private VerticalLayout projectTab;
	@AutoGenerated
	private TextArea description;
	@AutoGenerated
	private TextField name;
	@AutoGenerated
	private Label label;
	@Inject
	ProjectService projectService;

	private FieldGroup fieldGroup;
	
	private Project project;
	
	public ProjectView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		Messages.translate(this);

		fieldGroup = new FieldGroup();
		
		projectTab.setMargin(new MarginInfo(true, false, false, false));

		saveButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit();
					if (project.getId() == null) {
						projectService.addProject(project);
					} else {
						projectService.updateProject(project);
					}
					UI.getCurrent().getNavigator().navigateTo("projects!/");
				} catch (CommitException e) {
					Notification.show("Error", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
			}
		});

		cancelButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("projects!/");
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		try {
			if (StringUtils.isBlank(event.getParameters())) {
				project = new Project();
				project.setName("New Project");
			} else {
				long projectId = Long.parseLong(event.getParameters());
				project = projectService.getProject(projectId);
			}
			
			label.setValue("<li class='icon-home'></li> / Projects / " + project.getName());
			
			fieldGroup.setItemDataSource(new BeanItem<Project>(project));
			fieldGroup.bindMemberFields(this);
		} catch (Exception e) {
			UI.getCurrent().getNavigator().navigateTo("projects!/");
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
		label.setValue("Home / Projects / New Project");
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
		
		// projectTab
		projectTab = buildProjectTab();
		tabSheet.addTab(projectTab, "Project", null);
		
		return tabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildProjectTab() {
		// common part: create layout
		projectTab = new VerticalLayout();
		projectTab.setImmediate(false);
		projectTab.setWidth("100.0%");
		projectTab.setHeight("-1px");
		projectTab.setMargin(true);
		projectTab.setSpacing(true);
		
		// name
		name = new TextField();
		name.setCaption("Name");
		name.setImmediate(false);
		name.setWidth("100.0%");
		name.setHeight("-1px");
		name.setRequired(true);
		projectTab.addComponent(name);
		
		// description
		description = new TextArea();
		description.setCaption("Description");
		description.setImmediate(false);
		description.setWidth("100.0%");
		description.setHeight("-1px");
		projectTab.addComponent(description);
		
		return projectTab;
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
