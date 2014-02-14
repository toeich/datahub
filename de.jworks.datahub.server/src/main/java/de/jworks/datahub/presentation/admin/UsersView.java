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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.business.common.boundary.UserGroupService;
import de.jworks.datahub.business.common.boundary.UserService;
import de.jworks.datahub.business.common.entity.User;
import de.jworks.datahub.business.common.entity.UserGroup;
import de.jworks.datahub.presentation.AdminUI;
import de.jworks.datahub.presentation.Messages;

@CDIView(value = "users", uis = { AdminUI.class })
public class UsersView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout body;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private VerticalLayout userRolesTab;
	@AutoGenerated
	private Button addUserRoleButton;
	@AutoGenerated
	private Table userRolesTable;
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
	
	@Inject
	UserGroupService userGroupService;
	
	private BeanItemContainer<User> users = new BeanItemContainer<User>(User.class);

	private BeanItemContainer<UserGroup> userGroups = new BeanItemContainer<UserGroup>(UserGroup.class);

	private class UserActionsColumn implements ColumnGenerator {
		
		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {
			User user = users.getItem(itemId).getBean();
			HorizontalLayout actions = new HorizontalLayout();
			actions.setSpacing(true);
			actions.addComponent(new EditUserButton(user));
			actions.addComponent(new DeleteUserButton(user));
			return actions;
		}
		
	}

	private class AddUserHandler implements ClickListener {
		
		@Override
		public void buttonClick(ClickEvent event) {
			UI.getCurrent().getNavigator().navigateTo("user-new");
		}
		
	}
	
	private class EditUserButton extends Button implements ClickListener {
		
		private User user;
		
		public EditUserButton(User user) {
			this.user = user;
			
			setStyleName("link");
			setHtmlContentAllowed(true);
			setCaption("" + Icon.edit);
			setDescription(Messages.getString("edit"));
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			UI.getCurrent().getNavigator().navigateTo("user-edit/" + user.getId());
		}
		
	}
	
	private class DeleteUserButton extends Button implements ClickListener {
		
		private User user;
		
		public DeleteUserButton(User user) {
			this.user = user;
			
			setStyleName("link");
			setHtmlContentAllowed(true);
			setCaption("" + Icon.remove);
			setDescription(Messages.getString("delete"));
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			userService.removeUser(user);
			refreshUsersTable();
		}
		
	}
	private class UserGroupActionsColumn implements ColumnGenerator {
		
		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {
			UserGroup userGroup = userGroups.getItem(itemId).getBean();
			HorizontalLayout actions = new HorizontalLayout();
			actions.setSpacing(true);
			actions.addComponent(new EditUserGroupButton(userGroup));
			actions.addComponent(new DeleteUserGroupButton(userGroup));
			return actions;
		}
		
	}

	private class EditUserGroupButton extends Button implements ClickListener {
		
		private UserGroup userGroup;
		
		public EditUserGroupButton(UserGroup userGroup) {
			this.userGroup = userGroup;
			
			setStyleName("link");
			setHtmlContentAllowed(true);
			setCaption(Messages.getString("edit"));
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			UI.getCurrent().getNavigator().navigateTo("usergroup-edit/"+ userGroup.getId());
		}
		
	}
	
	private class DeleteUserGroupButton extends Button implements ClickListener {
		
		private UserGroup group;
		
		public DeleteUserGroupButton(UserGroup group) {
			this.group = group;
			
			setStyleName("link");
			setHtmlContentAllowed(true);
			setCaption(Messages.getString("delete"));
			
			addClickListener(this);
		}
		
		@Override
		public void buttonClick(ClickEvent event) {
			userGroupService.removeGroup(group);
			refreshUserGroupsTable();
		}
		
	}

	private class NewUserGroupHandler implements ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			UI.getCurrent().getNavigator().navigateTo("usergroup-new");
		}
		
	}

	public UsersView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		Messages.translate(this);

		// TODO add user code here
		label.setContentMode(ContentMode.HTML);
		label.setValue(Icon.home + " / Users");
		
		usersTab.setMargin(new MarginInfo(true, false, false, false));
//		usersTable.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		usersTable.addGeneratedColumn("actions", new UserActionsColumn());
		usersTable.setContainerDataSource(users, Arrays.asList("id", "name", "fullName", "email", "actions"));
		usersTable.setColumnHeader("name", Messages.getString(this, "name"));
		usersTable.setColumnHeader("fullName", Messages.getString(this, "fullName"));
		usersTable.setColumnHeader("email", Messages.getString(this, "email"));
		usersTable.setColumnHeader("actions", Messages.getString("actions"));
		usersTable.setColumnExpandRatio("name", 1.0f);
		usersTable.setColumnExpandRatio("fullName", 1.0f);
		usersTable.setColumnExpandRatio("email", 1.0f);
		addUserButton.addClickListener(new AddUserHandler());
		
		userGroupsTab.setMargin(new MarginInfo(true, false, false, false));
//		userGroupsTable.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		userGroupsTable.addGeneratedColumn("actions", new UserGroupActionsColumn());
		userGroupsTable.setContainerDataSource(userGroups, Arrays.asList("id", "name", "actions"));
		userGroupsTable.setColumnHeader("name", Messages.getString(this, "name"));
		userGroupsTable.setColumnHeader("actions", Messages.getString("actions"));
		userGroupsTable.setColumnExpandRatio("name", 1.0f);
		addUserGroupButton.addClickListener(new NewUserGroupHandler());
		
		userRolesTab.setMargin(new MarginInfo(true, false, false, false));
		userRolesTable.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		addUserRoleButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("'Add User Role' clicked");
			}
		});
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
		userGroups.addAll(userGroupService.getGroups());
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
		
		// userRolesTab
		userRolesTab = buildUserRolesTab();
		tabSheet.addTab(userRolesTab, "User Roles", null);
		
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
		addUserButton.setImmediate(false);
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

	@AutoGenerated
	private VerticalLayout buildUserRolesTab() {
		// common part: create layout
		userRolesTab = new VerticalLayout();
		userRolesTab.setImmediate(false);
		userRolesTab.setWidth("100.0%");
		userRolesTab.setHeight("100.0%");
		userRolesTab.setMargin(true);
		userRolesTab.setSpacing(true);
		
		// userRolesTable
		userRolesTable = new Table();
		userRolesTable.setImmediate(false);
		userRolesTable.setWidth("100.0%");
		userRolesTable.setHeight("100.0%");
		userRolesTab.addComponent(userRolesTable);
		userRolesTab.setExpandRatio(userRolesTable, 1.0f);
		
		// addUserRoleButton
		addUserRoleButton = new Button();
		addUserRoleButton.setStyleName("link");
		addUserRoleButton.setCaption("Add User Role");
		addUserRoleButton.setImmediate(true);
		addUserRoleButton.setWidth("-1px");
		addUserRoleButton.setHeight("-1px");
		userRolesTab.addComponent(addUserRoleButton);
		
		return userRolesTab;
	}

}
