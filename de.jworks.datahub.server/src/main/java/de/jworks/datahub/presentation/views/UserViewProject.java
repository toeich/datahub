package de.jworks.datahub.presentation.views;

import java.util.List;

import javax.inject.Inject;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import de.jworks.datahub.business.documents.boundary.DatasetService;
import de.jworks.datahub.business.documents.entity.ColumnDefinition;
import de.jworks.datahub.business.documents.entity.Dataset;
import de.jworks.datahub.business.documents.entity.DatasetGroup;
import de.jworks.datahub.business.projects.entity.Project;
import de.jworks.datahub.presentation.Messages;
import de.jworks.datahub.presentation.data.DatasetContainer;
import de.jworks.datahub.presentation.editors.DocumentEditor;

public class UserViewProject extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout buttons;
	@AutoGenerated
	private Button addDocumentButton;
	@AutoGenerated
	private Table table;
	@AutoGenerated
	private MenuBar menuBar;
	@AutoGenerated
	private Label label;
	
	@Inject
	DatasetService datasetService;
	
	private DatasetGroup datasetGroup;
	
	public UserViewProject() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		menuBar.setAutoOpen(true);
		menuBar.setHtmlContentAllowed(true);
		
		table.addGeneratedColumn("actions", new ActionsColumn());
		
		addDocumentButton.addClickListener(new AddDocumentClickListener());
	}
	
	public void setProject(Project project) {
		label.setValue(String.format("%s", project));
		
		menuBar.removeItems();
		
		List<DatasetGroup> collections = datasetService.getDatasetGroups(project);
		for (DatasetGroup collection : collections) {
			menuBar.addItem(collection.getName(), new SelectCollectionCommand(collection));
		}
		
		setCollection(collections.size() > 0 ? collections.get(0) : null);
	}
	
	private void setCollection(DatasetGroup collection) {
		this.datasetGroup = collection;
		
		if (collection != null) {
			for (MenuItem menuItem : menuBar.getItems()) {
				if (menuItem.getText().equals(collection.getName())) {
					menuItem.setEnabled(false);
				} else {
					menuItem.setEnabled(true);
				}
			}
		}
		
		refreshTable();
	}

	public void refreshTable() {
		if (datasetGroup != null) {
			List<Dataset> datasets = datasetService.getDatasets(datasetGroup);

			table.setContainerDataSource(new DatasetContainer(datasets, datasetGroup.getColumns()));
			
			for (ColumnDefinition column : datasetGroup.getColumns()) {
				table.setColumnExpandRatio(column.getName(), 1.0f);
			}

			addDocumentButton.setVisible(true);
			addDocumentButton.setCaption(Messages.format("addDocument", datasetGroup.getSchema().getRootElement()));
		} else {
			table.setContainerDataSource(null);
			table.setVisibleColumns();
			
			addDocumentButton.setVisible(false);
		}
	}

	private class ActionsColumn implements ColumnGenerator {
		
		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {
			Dataset document = (Dataset) itemId;

			HorizontalLayout layout = new HorizontalLayout();
			layout.setSpacing(true);
			layout.addComponent(new EditButton(document));
			layout.addComponent(new DeleteButton(document));
			return layout;
		}
		
	}
	
	private class EditButton extends Button implements Button.ClickListener {
		
		private Dataset dataset;

		public EditButton(Dataset document) {
			this.dataset = document;
			
			setCaption(Messages.getString("edit"));
			setStyleName("link");
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window window = new Window(datasetGroup.getSchema().getRootElement().getLabel());
			window.setWidth("80%");
			window.setHeight("80%");
			window.setModal(true);
			window.setResizable(false);
			window.setDraggable(false);
			window.addCloseListener(new CloseListener() {
				@Override
				public void windowClose(CloseEvent e) {
					refreshTable();
				}
			});
			
			DocumentEditor editor = new DocumentEditor(dataset);
			editor.addSaveListener(new DocumentEditor.SaveListener() {
				@Override
				public void save(DocumentEditor.SaveEvent event) {
					if (event.getDataset() != null) {
						datasetService.updateDataset(event.getDataset());
					}
					window.close();
				}
			});
			window.setContent(editor);
			
			UI.getCurrent().addWindow(window);
		}
		
	}
	
	private class DeleteButton extends Button implements Button.ClickListener {

		private Dataset dataset;

		public DeleteButton(Dataset dataset) {
			this.dataset = dataset;
			
			setCaption(Messages.getString("delete"));
			setStyleName("link");
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			datasetService.removeDataset(dataset);
			
			refreshTable();
		}
		
	}

	private class SelectCollectionCommand implements MenuBar.Command {
		
		private DatasetGroup collection;
		
		public SelectCollectionCommand(DatasetGroup collection) {
			this.collection = collection;
		}

		@Override
		public void menuSelected(MenuBar.MenuItem selectedItem) {
			setCollection(collection);
		}
		
	}
	
	private class AddDocumentClickListener implements Button.ClickListener {

		@Override
		public void buttonClick(Button.ClickEvent event) {
			final Window window = new Window(datasetGroup.getSchema().getRootElement().getLabel());
			window.setWidth("80%");
			window.setHeight("80%");
			window.setModal(true);
			window.setResizable(false);
			window.setDraggable(false);
			window.addCloseListener(new CloseListener() {
				@Override
				public void windowClose(CloseEvent e) {
					refreshTable();
				}
			});
			
			final Dataset dataset = new Dataset();
			dataset.setGroup(datasetGroup);
			
			DocumentEditor editor = new DocumentEditor(dataset);
			editor.addSaveListener(new DocumentEditor.SaveListener() {
				@Override
				public void save(DocumentEditor.SaveEvent event) {
					if (event.getDataset() != null) {
						datasetService.addDataset(dataset);
					}
					window.close();
				}
			});
			window.setContent(editor);
			
			UI.getCurrent().addWindow(window);
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
		label.setValue("<Project>");
		mainLayout.addComponent(label);
		
		// menuBar
		menuBar = new MenuBar();
		menuBar.setImmediate(false);
		menuBar.setWidth("100.0%");
		menuBar.setHeight("-1px");
		mainLayout.addComponent(menuBar);
		
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
		
		// addDocumentButton
		addDocumentButton = new Button();
		addDocumentButton.setStyleName("small");
		addDocumentButton.setCaption("Add <Document>...");
		addDocumentButton.setImmediate(false);
		addDocumentButton.setWidth("-1px");
		addDocumentButton.setHeight("-1px");
		buttons.addComponent(addDocumentButton);
		
		return buttons;
	}
	
}
