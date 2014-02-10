package de.jworks.datahub.presentation;

public interface Constants {

	public String TITLE_PREFIX = "Data Hub // ";
	
	
	public String TASKS = "tasks";

	public String PROJECTS = "projects";
	
	public String ACTIONS = "actions";
	
	public String USERS = "users";
	
	public String GROUPS = "groups";
	
	public String SYSTEMS = "systems";
	
	public String COLLECTIONS = "collections";
	
	public String TRANSFORMATIONS = "transformations";
	
	
	public String SIGN_IN = "signIn"; // "sign in", "log in"
	
	public String SIGN_OUT = "signOut"; // "sign out", "log out"
	
	public String JOIN = "join"; // "join", "register"
	
	
	public String LOGIN_VIEW = "login";
	
	public String LOGOUT_VIEW = "logout";
	
	public String USER_VIEW = "";
	
	public String USER_VIEW_CAPTION = "<b>Data Hub</b>";
	
	public String USER_VIEW_PREFIX = "/";
	
	public String USER_VIEW_TASKS = USER_VIEW_PREFIX + TASKS;
	
	public String USER_VIEW_PROJECTS = USER_VIEW_PREFIX + PROJECTS;
	
	public String USER_VIEW_PROJECT = USER_VIEW_PROJECTS + "/%s";

	public String USER_VIEW_ACTIONS = USER_VIEW_PREFIX + ACTIONS;

	public String ADMIN_VIEW = "admin";
	
	public String ADMIN_VIEW_PREFIX = "admin/";
	
	public String ADMIN_VIEW_USERS = ADMIN_VIEW_PREFIX + USERS;
	
	public String ADMIN_VIEW_GROUPS = ADMIN_VIEW_PREFIX + GROUPS;
	
	public String ADMIN_VIEW_PROJECTS = ADMIN_VIEW_PREFIX + PROJECTS;

	public String ADMIN_VIEW_SYSTEMS = ADMIN_VIEW_PREFIX + SYSTEMS;

	public String ADMIN_VIEW_COLLECTIONS = ADMIN_VIEW_PREFIX + COLLECTIONS;
	
	public String ADMIN_VIEW_TRANSFORMATIONS = ADMIN_VIEW_PREFIX + TRANSFORMATIONS;

	public String ADMIN_VIEW_ACTIONS = ADMIN_VIEW_PREFIX + ACTIONS;
	
}
