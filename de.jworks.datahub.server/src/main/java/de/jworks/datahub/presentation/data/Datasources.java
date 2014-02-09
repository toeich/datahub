package de.jworks.datahub.presentation.data;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;

import de.jworks.datahub.business.common.entity.Role;
import de.jworks.datahub.business.transform.entity.ItemType;

@SuppressWarnings("unchecked")
public class Datasources {

    public static final String DISPLAY_NAME = "displayName";

	private static Container roles;
    
	private static Container inputTypes;
    private static Container outputTypes;
    
    public static Container getRoles() {
        if (roles == null) {
            roles = new IndexedContainer();
            roles.addContainerProperty(DISPLAY_NAME, String.class, null);
            roles.addItem(Role.USER).getItemProperty(DISPLAY_NAME).setValue("User");
            roles.addItem(Role.ADMIN).getItemProperty(DISPLAY_NAME).setValue("Admin");
        }
        return roles;
    }
    
    public static Container getInputTypes() {
    	if (inputTypes == null) {
    		inputTypes = new IndexedContainer();
    		inputTypes.addContainerProperty(DISPLAY_NAME, String.class, null);
    		inputTypes.addItem(ItemType.XML_ELEMENT).getItemProperty(DISPLAY_NAME).setValue("XML Element");
    		inputTypes.addItem(ItemType.XML_ATTRIBUTE).getItemProperty(DISPLAY_NAME).setValue("XML Attribute");
    	}
    	return outputTypes;
    }
    
    public static Container getOutputTypes() {
    	if (outputTypes == null) {
    		outputTypes = new IndexedContainer();
    		outputTypes.addContainerProperty(DISPLAY_NAME, String.class, null);
    		outputTypes.addItem(ItemType.XML_ELEMENT).getItemProperty(DISPLAY_NAME).setValue("XML Element");
    		outputTypes.addItem(ItemType.XML_ATTRIBUTE).getItemProperty(DISPLAY_NAME).setValue("XML Attribute");
    	}
    	return outputTypes;
    }
    
}
