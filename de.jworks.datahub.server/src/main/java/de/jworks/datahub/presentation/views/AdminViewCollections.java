package de.jworks.datahub.presentation.views;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import de.jworks.datahub.business.datasets.boundary.DatasetService;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.business.projects.boundary.ProjectService;
import de.jworks.datahub.presentation.editors.DocumentCollectionEditor;
import de.jworks.datahub.presentation.editors.DocumentCollectionEditor.SaveEvent;
import de.jworks.datahub.presentation.editors.DocumentCollectionEditor.SaveListener;

public class AdminViewCollections extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout buttons;
	@AutoGenerated
	private Button newCollectionButton;
	@AutoGenerated
	private Table table;
	@AutoGenerated
	private Label label;
	
	@Inject
	DatasetService documentService;
	
	@Inject
	ProjectService projectService;
	
	private BeanItemContainer<DatasetGroup> collections = new BeanItemContainer<DatasetGroup>(DatasetGroup.class);
	
	public AdminViewCollections() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		table.setSelectable(true);
		table.setMultiSelect(true);
		
		table.addGeneratedColumn("actions", new ActionsColumn());
		
		table.setContainerDataSource(collections, Arrays.asList("id", "name", "project", "actions"));
		table.setColumnExpandRatio("name", 1.0f);
		table.setColumnExpandRatio("project", 1.0f);
		
		newCollectionButton.addClickListener(new NewCollectionHandler());
	}

	@PostConstruct
	public void refreshTable() {
		collections.removeAllItems();
		collections.addAll(documentService.getDatasetGroups());
	}

	private class NewCollectionHandler implements ClickListener {
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window window = new Window("New Collection");
			window.setWidth("90%");
			window.setHeight("90%");
			window.setModal(true);
			window.setDraggable(false);
			window.setResizable(false);
			window.addCloseListener(new CloseListener() {
				@Override
				public void windowClose(CloseEvent e) {
					refreshTable();
				}
			});
			
			DocumentCollectionEditor editor = new DocumentCollectionEditor(new DatasetGroup(), projectService.getProjects());
			editor.addSaveListener(new SaveListener() {
				@Override
				public void save(SaveEvent event) {
					if (event.getCollection() != null) {
						documentService.addDatasetGroup(event.getCollection());
					}
					window.close();
				}
			});
			window.setContent(editor);
			
			UI.getCurrent().addWindow(window);
		}
		
	}

	private class ActionsColumn implements ColumnGenerator {
		
		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {
			DatasetGroup collection = collections.getItem(itemId).getBean();
			HorizontalLayout actions = new HorizontalLayout();
			actions.setSpacing(true);
			actions.addComponent(new EditCollectionButton(collection));
			actions.addComponent(new DeleteCollectionButton(collection));
			return actions;
		}
	}

	private class EditCollectionButton extends Button implements ClickListener {
		
		private DatasetGroup collection;
		
		public EditCollectionButton(DatasetGroup collection) {
			this.collection = collection;
			
			setCaption("edit");
			setStyleName("link");
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window window = new Window(String.format("Edit Collection '%s'", collection.getName()));
			window.setWidth("90%");
			window.setHeight("90%");
			window.setModal(true);
			window.setDraggable(false);
			window.setResizable(false);
			window.addCloseListener(new CloseListener() {
				@Override
				public void windowClose(CloseEvent e) {
					refreshTable();
				}
			});
			
			DocumentCollectionEditor editor = new DocumentCollectionEditor(collection, projectService.getProjects());
			editor.addSaveListener(new SaveListener() {
				@Override
				public void save(SaveEvent event) {
					if (event.getCollection() != null) {
						documentService.updateDatasetGroup(event.getCollection());
					}
					window.close();
				}
			});
			window.setContent(editor);
			
			UI.getCurrent().addWindow(window);
		}
		
	}
	
	private class DeleteCollectionButton extends Button implements ClickListener {
		
		private DatasetGroup collection;
		
		public DeleteCollectionButton(DatasetGroup collection) {
			this.collection = collection;
			
			setCaption("delete");
			setStyleName("link");
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			documentService.removeDatasetGroup(collection);
		}
		
	}
	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
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
		label.setStyleName("h1");
		label.setImmediate(false);
		label.setWidth("-1px");
		label.setHeight("-1px");
		label.setValue("Collections");
		mainLayout.addComponent(label);
		
		// table
		table = new Table();
		table.setImmediate(false);
		table.setWidth("100.0%");
		table.setHeight("100.0%");
		mainLayout.addComponent(table);
		mainLayout.setExpandRatio(table, 1.0f);
		
		// buttons
		buttons = buildButtons();
		mainLayout.addComponent(buttons);
		
		return mainLayout;
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
		
		// newCollectionButton
		newCollectionButton = new Button();
		newCollectionButton.setStyleName("small");
		newCollectionButton.setCaption("New Collection...");
		newCollectionButton.setImmediate(false);
		newCollectionButton.setWidth("-1px");
		newCollectionButton.setHeight("-1px");
		buttons.addComponent(newCollectionButton);
		
		return buttons;
	}

}
