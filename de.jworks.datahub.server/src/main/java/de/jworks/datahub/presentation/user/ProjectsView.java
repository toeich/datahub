package de.jworks.datahub.presentation.user;

import java.util.Arrays;

import javax.inject.Inject;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.business.common.entity.Project;
import de.jworks.datahub.business.projects.boundary.ProjectService;
import de.jworks.datahub.presentation.Messages;
import de.jworks.datahub.presentation.UserUI;

@CDIView(value = "projects!", uis = { UserUI.class })
public class ProjectsView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout projectsTab;
	@AutoGenerated
	private Table projectsTable;
	@AutoGenerated
	private VerticalLayout infoPanel;
	@AutoGenerated
	private Label description;
	@AutoGenerated
	private Label name;
	@AutoGenerated
	private Label label;
	
	@Inject
	ProjectService projectService;
	
	private BeanItemContainer<Project> projects = new BeanItemContainer<Project>(Project.class);
	
	public ProjectsView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		projectsTab.setMargin(new MarginInfo(true, false, false, false));

		projectsTable.setContainerDataSource(projects, Arrays.asList("name", "description"));
		projectsTable.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				Project project = projects.getItem(event.getItemId()).getBean();
				UI.getCurrent().getNavigator().navigateTo(String.format("project!/" + project.getId()));
			}
		});
		
		Messages.translate(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refreshProjectsTable();
	}
	
	private void refreshProjectsTable() {
		projects.removeAllItems();
		projects.addAll(projectService.getProjects());
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
		label.setValue("Home / Projects");
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
		name.setValue("Projects");
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
		
		// projectsTab
		projectsTab = buildProjectsTab();
		tabSheet.addTab(projectsTab, "Projects", null);
		
		return tabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildProjectsTab() {
		// common part: create layout
		projectsTab = new VerticalLayout();
		projectsTab.setImmediate(false);
		projectsTab.setWidth("100.0%");
		projectsTab.setHeight("100.0%");
		projectsTab.setMargin(true);
		projectsTab.setSpacing(true);
		
		// projectsTable
		projectsTable = new Table();
		projectsTable.setImmediate(false);
		projectsTable.setWidth("100.0%");
		projectsTable.setHeight("100.0%");
		projectsTab.addComponent(projectsTable);
		projectsTab.setExpandRatio(projectsTable, 1.0f);
		
		return projectsTab;
	}

}
