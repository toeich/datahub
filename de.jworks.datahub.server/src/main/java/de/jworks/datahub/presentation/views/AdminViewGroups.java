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

import de.jworks.datahub.business.common.boundary.GroupService;
import de.jworks.datahub.business.common.entity.UserGroup;
import de.jworks.datahub.presentation.Messages;
import de.jworks.datahub.presentation.editors.GroupEditor;
import de.jworks.datahub.presentation.editors.GroupEditor.SaveEvent;
import de.jworks.datahub.presentation.editors.GroupEditor.SaveListener;

public class AdminViewGroups extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout buttons;
	@AutoGenerated
	private Button newGroupButton;
	@AutoGenerated
	private Table table;
	@AutoGenerated
	private Label label;
	
	@Inject
	GroupService groupService;
	
	private BeanItemContainer<UserGroup> groups = new BeanItemContainer<UserGroup>(UserGroup.class);
	
	public AdminViewGroups() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		label.setValue(Messages.getString("groups"));
		
		table.addGeneratedColumn("actions", new ActionsColumn());
		
		table.setContainerDataSource(groups, Arrays.asList("id", "name", "roles", "actions"));
		table.setColumnHeader("name", Messages.getString("name"));
		table.setColumnHeader("roles", Messages.getString("roles"));
		table.setColumnHeader("actions", Messages.getString("actions"));
		table.setColumnExpandRatio("id", 1.0f);
        table.setColumnExpandRatio("name", 1.0f);
        table.setColumnExpandRatio("roles", 1.0f);
        
        newGroupButton.addClickListener(new NewGroupHandler());
	}

	@PostConstruct
	public void refreshTable() {
		groups.removeAllItems();
		groups.addAll(groupService.getGroups());
	}

	private class ActionsColumn implements ColumnGenerator {
		
		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {
			UserGroup group = groups.getItem(itemId).getBean();
			HorizontalLayout actions = new HorizontalLayout();
			actions.setSpacing(true);
			actions.addComponent(new EditGroupButton(group));
			actions.addComponent(new DeleteGroupButton(group));
			return actions;
		}
		
	}

	private class EditGroupButton extends Button implements ClickListener {
		
		private UserGroup group;
		
		public EditGroupButton(UserGroup group) {
			this.group = group;
			
			setCaption(Messages.getString("edit"));
			setStyleName("link");
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window window = new Window(Messages.format("Edit Group '%s'", group.getName()));
			window.setModal(true);
			window.setDraggable(false);
			window.setResizable(false);
			window.addCloseListener(new CloseListener() {
				@Override
				public void windowClose(CloseEvent e) {
					refreshTable();
				}
			});
			
			GroupEditor editor = new GroupEditor(group);
			editor.addSaveListener(new SaveListener() {
				@Override
				public void save(SaveEvent event) {
					if (event.getGroup() != null) {
						groupService.updateGroup(event.getGroup());
					}
					window.close();
				}
			});
			window.setContent(editor);

			UI.getCurrent().addWindow(window);
		}
		
	}
	
	private class DeleteGroupButton extends Button implements ClickListener {
		
		private UserGroup group;
		
		public DeleteGroupButton(UserGroup group) {
			this.group = group;
			
			setCaption(Messages.getString("delete"));
			setStyleName("link");
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			groupService.removeGroup(group);
			refreshTable();
		}
		
	}
	
	private class NewGroupHandler implements ClickListener {
		
		@Override
		public void buttonClick(ClickEvent event) {
			final Window window = new Window(Messages.getString("New Group"));
			window.setModal(true);
			window.setDraggable(false);
			window.setResizable(false);
			window.addCloseListener(new CloseListener() {
				@Override
				public void windowClose(CloseEvent e) {
					refreshTable();
				}
			});
			
			GroupEditor editor = new GroupEditor(new UserGroup());
			editor.addSaveListener(new SaveListener() {
				@Override
				public void save(SaveEvent event) {
					if (event.getGroup() != null) {
						groupService.addGroup(event.getGroup());
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
		label.setValue("Groups");
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
		
		// newGroupButton
		newGroupButton = new Button();
		newGroupButton.setStyleName("small");
		newGroupButton.setCaption("New Group...");
		newGroupButton.setImmediate(false);
		newGroupButton.setWidth("-1px");
		newGroupButton.setHeight("-1px");
		buttons.addComponent(newGroupButton);
		
		return buttons;
	}

}
