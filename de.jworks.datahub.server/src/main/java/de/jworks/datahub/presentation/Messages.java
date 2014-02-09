package de.jworks.datahub.presentation;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class Messages {

	public static String getString(String key) {
		try {
			return ResourceBundle.getBundle("de.jworks.connector.presentation.messages", VaadinSession.getCurrent().getLocale()).getString(key);
		} catch (Exception e) {
			return "!" + key + "!";
		}
	}

	public static String format(String key, Object... args) {
		return String.format(getString(key), args);
	}

	public static void translate(CustomComponent customComponent) {
		for (Field field : customComponent.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (Component.class.isAssignableFrom(field.getType())) {
				String caption = getString(customComponent, field.getName() + "_caption");
				if (caption != null && !caption.startsWith("!")) {
					try {
						Component component = Component.class.cast(field.get(customComponent));
						component.setCaption(caption);
					} catch (Exception e) {
						// ignore
					}
				}
			}
			if (Label.class.isAssignableFrom(field.getType())) {
				String value = getString(customComponent, field.getName() + "_value");
				if (value != null && !value.startsWith("!")) {
					try {
						Label label = Label.class.cast(field.get(customComponent));
						label.setValue(value);
					} catch (Exception e) {
						// ignore
					}
				}
			}
			if (AbstractTextField.class.isAssignableFrom(field.getType())) {
				String inputPrompt = getString(customComponent, field.getName() + "_inputPrompt");
				if (inputPrompt != null && !inputPrompt.startsWith("!")) {
					try {
						AbstractTextField abstractTextField = AbstractTextField.class.cast(field.get(customComponent));
						abstractTextField.setInputPrompt(inputPrompt);
						abstractTextField.setNullRepresentation(null);
					} catch (Exception e) {
						// ignore
					}
				}
			}
		}
	}
	
	public static String getString(CustomComponent customComponent, String key) {
		return getString(customComponent.getClass().getSimpleName() + "_" + key);
	}

}
