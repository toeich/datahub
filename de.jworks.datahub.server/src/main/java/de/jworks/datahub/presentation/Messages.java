package de.jworks.datahub.presentation;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

public class Messages {
	
	private static final boolean DEBUG = false;

	public static String getString(String key) {
		try {
			return ResourceBundle.getBundle("de.jworks.datahub.presentation.messages", VaadinSession.getCurrent().getLocale()).getString(key);
		} catch (Exception e) {
			return "!" + key + "!";
		}
	}

	public static String format(String key, Object... args) {
		return MessageFormat.format(getString(key), args);
	}

	public static String getString(CustomComponent customComponent, String key) {
		return getString(customComponent.getClass().getSimpleName() + "_" + key);
	}

	public static String format(CustomComponent customComponent, String key, Object... args) {
		return MessageFormat.format(getString(customComponent.getClass().getSimpleName() + "_" + key), args);
	}

	public static void translate(CustomComponent customComponent) {
		for (Field field : customComponent.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (Component.class.isAssignableFrom(field.getType())) {
				try {
					Component component = Component.class.cast(field.get(customComponent));
					String caption = getString(customComponent, field.getName() + "_caption");
					if ((caption != null && !caption.startsWith("!")) || DEBUG) {
						component.setCaption(caption);
						if (component.getParent() instanceof TabSheet) {
							TabSheet tabSheet = (TabSheet) component.getParent();
							tabSheet.getTab(component).setCaption(caption);
						}
					}
				} catch (Exception e) {
					// ignore
				}
			}
			if (Label.class.isAssignableFrom(field.getType())) {
				try {
					Label label = Label.class.cast(field.get(customComponent));
					label.setContentMode(ContentMode.HTML);
					String value = getString(customComponent, field.getName() + "_value");
					if ((value != null && !value.startsWith("!")) || DEBUG) {
						label.setValue(value);
					}
				} catch (Exception e) {
					// ignore
				}
			}
			if (AbstractTextField.class.isAssignableFrom(field.getType())) {
				try {
					AbstractTextField abstractTextField = AbstractTextField.class.cast(field.get(customComponent));
					String inputPrompt = getString(customComponent, field.getName() + "_inputPrompt");
					if ((inputPrompt != null && !inputPrompt.startsWith("!")) || DEBUG) {
						abstractTextField.setInputPrompt(inputPrompt);
						abstractTextField.setNullRepresentation(null);
					}
				} catch (Exception e) {
					// ignore
				}
			}
			if (Table.class.isAssignableFrom(field.getType())) {
				try {
					Table table = Table.class.cast(field.get(customComponent));
					for (Object propertyId : table.getVisibleColumns()) {
						String header = getString(customComponent, field.getName() + "_" + propertyId);
						if ((header != null && !header.startsWith("!")) || DEBUG) {
							table.setColumnHeader(propertyId, header);
						}
					}
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}
	
}
