package de.jworks.datahub.presentation.admin;

import java.util.Arrays;

import javax.inject.Inject;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
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

import de.jworks.datahub.business.common.boundary.UserService;
import de.jworks.datahub.business.common.entity.User;
import de.jworks.datahub.business.common.entity.UserGroup;
import de.jworks.datahub.presentation.AdminUI;
import de.jworks.datahub.presentation.Messages;

@CDIView(value = "users!", uis = { AdminUI.class })
public class UsersView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout userGroupsTab;
	@AutoGenerated
	private Button addUserGroupButton;
	@AutoGenerated
	private Table userGroupsTable;
	@AutoGenerated
	private VerticalLayout usersTab;
	@AutoGenerated
	private Button addUserButton;
	@AutoGenerated
	private Table usersTable;
	@AutoGenerated
	private Label label;

	@Inject
	UserService userService;
	
	private BeanItemContainer<User> users = new BeanItemContainer<User>(User.class);

	private BeanItemContainer<UserGroup> userGroups = new BeanItemContainer<UserGroup>(UserGroup.class);

	public UsersView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		usersTab.setMargin(new MarginInfo(true, false, false, false));
		
		usersTable.addGeneratedColumn("actions", new ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final User user = users.getItem(itemId).getBean();
				HorizontalLayout actions = new HorizontalLayout();
				actions.setSpacing(true);
				Button editButton = new Button();
				editButton.setStyleName("link");
				editButton.setCaption(Messages.getString("edit"));
				editButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().getNavigator().navigateTo("user!/" + user.getId());
					}
				});
				actions.addComponent(editButton);
				Button deleteButton = new Button();
				deleteButton.setStyleName("link");
				deleteButton.setCaption(Messages.getString("delete"));
				deleteButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						userService.removeUser(user);
						refreshUsersTable();
					}
				});
				actions.addComponent(deleteButton);
				return actions;
			}
		});
		usersTable.setContainerDataSource(users, Arrays.asList("id", "name", "fullName", "email", "actions"));
		usersTable.setColumnExpandRatio("name", 1.0f);
		usersTable.setColumnExpandRatio("fullName", 1.0f);
		usersTable.setColumnExpandRatio("email", 1.0f);
		
		addUserButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("user!/");
			}
		});
		
		userGroupsTab.setMargin(new MarginInfo(true, false, false, false));
		
		userGroupsTable.addGeneratedColumn("actions", new ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final UserGroup userGroup = userGroups.getItem(itemId).getBean();
				HorizontalLayout actions = new HorizontalLayout();
				actions.setSpacing(true);
				Button editButton = new Button();
				editButton.setStyleName("link");
				editButton.setCaption(Messages.getString("edit"));
				editButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().getNavigator().navigateTo("usergroup!/" + userGroup.getId());
					}
				});
				actions.addComponent(editButton);
				Button deleteButton = new Button();
				deleteButton.setStyleName("link");
				deleteButton.setCaption(Messages.getString("delete"));
				deleteButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						userService.removeUserGroup(userGroup);
						refreshUserGroupsTable();
					}
				});
				actions.addComponent(deleteButton);
				return actions;
			}
		});
		userGroupsTable.setContainerDataSource(userGroups, Arrays.asList("id", "name", "actions"));
		userGroupsTable.setColumnExpandRatio("name", 1.0f);
		
		addUserGroupButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("usergroup!/");
			}
		});
		
		Messages.translate(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refreshUsersTable();
		refreshUserGroupsTable();
	}
	
	private void refreshUsersTable() {
		users.removeAllItems();
		users.addAll(userService.getUsers());
	}
	
	private void refreshUserGroupsTable() {
		userGroups.removeAllItems();
		userGroups.addAll(userService.getUserGroups());
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
		label.setValue("Home / Users");
		mainLayout.addComponent(label);
		
		// body
		body = buildBody();
		mainLayout.addComponent(body);
		mainLayout.setExpandRatio(body, 1.0f);
		mainLayout.setComponentAlignment(body, new Alignment(20));
		
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
		body.setExpandRatio(tabSheet, 1.0f);
		
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
		
		// usersTab
		usersTab = buildUsersTab();
		tabSheet.addTab(usersTab, "Users", null);
		
		// userGroupsTab
		userGroupsTab = buildUserGroupsTab();
		tabSheet.addTab(userGroupsTab, "User Groups", null);
		
		return tabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildUsersTab() {
		// common part: create layout
		usersTab = new VerticalLayout();
		usersTab.setImmediate(false);
		usersTab.setWidth("100.0%");
		usersTab.setHeight("100.0%");
		usersTab.setMargin(true);
		usersTab.setSpacing(true);
		
		// usersTable
		usersTable = new Table();
		usersTable.setImmediate(false);
		usersTable.setWidth("100.0%");
		usersTable.setHeight("100.0%");
		usersTab.addComponent(usersTable);
		usersTab.setExpandRatio(usersTable, 1.0f);
		
		// addUserButton
		addUserButton = new Button();
		addUserButton.setStyleName("link");
		addUserButton.setCaption("Add User");
		addUserButton.setImmediate(true);
		addUserButton.setWidth("-1px");
		addUserButton.setHeight("-1px");
		usersTab.addComponent(addUserButton);
		
		return usersTab;
	}

	@AutoGenerated
	private VerticalLayout buildUserGroupsTab() {
		// common part: create layout
		userGroupsTab = new VerticalLayout();
		userGroupsTab.setImmediate(false);
		userGroupsTab.setWidth("100.0%");
		userGroupsTab.setHeight("100.0%");
		userGroupsTab.setMargin(true);
		userGroupsTab.setSpacing(true);
		
		// userGroupsTable
		userGroupsTable = new Table();
		userGroupsTable.setImmediate(false);
		userGroupsTable.setWidth("100.0%");
		userGroupsTable.setHeight("100.0%");
		userGroupsTab.addComponent(userGroupsTable);
		userGroupsTab.setExpandRatio(userGroupsTable, 1.0f);
		
		// addUserGroupButton
		addUserGroupButton = new Button();
		addUserGroupButton.setStyleName("link");
		addUserGroupButton.setCaption("Add User Group");
		addUserGroupButton.setImmediate(true);
		addUserGroupButton.setWidth("-1px");
		addUserGroupButton.setHeight("-1px");
		userGroupsTab.addComponent(addUserGroupButton);
		
		return userGroupsTab;
	}

}
