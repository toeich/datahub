package de.jworks.datahub.presentation.admin;

import java.util.Arrays;

import javax.inject.Inject;

import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.business.projects.boundary.ProjectService;
import de.jworks.datahub.presentation.AdminUI;

@CDIView(value = "projects", uis = { AdminUI.class })
public class ProjectsView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@Inject
	ProjectService projectService;
	
	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout projectsTab;
	@AutoGenerated
	private Button addProjectButton;
	@AutoGenerated
	private Table projectsTable;
	@AutoGenerated
	private Label label;
	
	public ProjectsView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		label.setContentMode(ContentMode.HTML);
		label.setValue(Icon.home + " / Processes");
		
		projectsTab.setMargin(new MarginInfo(true, false, false, false));
		projectsTable.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		addProjectButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("project-new");
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		BeanItemContainer projects = new BeanItemContainer(projectService.getProjects());
		projectsTable.setContainerDataSource(projects, Arrays.asList("name", "description"));
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
		
		// addProjectButton
		addProjectButton = new Button();
		addProjectButton.setStyleName("link");
		addProjectButton.setCaption("Add Project");
		addProjectButton.setImmediate(true);
		addProjectButton.setWidth("-1px");
		addProjectButton.setHeight("-1px");
		projectsTab.addComponent(addProjectButton);
		
		return projectsTab;
	}

}
