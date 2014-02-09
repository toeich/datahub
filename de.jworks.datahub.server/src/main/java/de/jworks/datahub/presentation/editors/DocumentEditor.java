package de.jworks.datahub.presentation.editors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.util.ReflectTools;

import de.jworks.datahub.business.documents.entity.Attribute;
import de.jworks.datahub.business.documents.entity.DocumentSchema;
import de.jworks.datahub.business.documents.entity.Element;

public class DocumentEditor extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout buttons;
	@AutoGenerated
	private Button cancelButton;
	@AutoGenerated
	private Button okButton;
	@AutoGenerated
	private Panel panel;
	@AutoGenerated
	private VerticalLayout panelContent;
	
	public DocumentEditor(final DBObject document, DocumentSchema schema) {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		processSingle(document, schema.getRootElement(), panelContent);
		
		okButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				fireEvent(new SaveEvent(DocumentEditor.this, document));
			}
		});
		
		cancelButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				fireEvent(new SaveEvent(DocumentEditor.this, null));
			}
		});
	}

	private void process(DBObject parent, Element element, VerticalLayout container) {
		if (element.getMaxOccurs() == null || element.getMaxOccurs() > 1) {
			@SuppressWarnings("unchecked")
			List<DBObject> list = (List<DBObject>) parent.get(element.getName());
			if (list == null) {
				parent.put(element.getName(), list = new ArrayList<DBObject>());
			}
			processMultiple(list, element, container);
		} else {
			DBObject object = (DBObject) parent.get(element.getName());
			if (object == null) {
				parent.put(element.getName(), object = new BasicDBObject()); 
			}
			processSingle(object, element, container);
		}
	}

	private void processSingle(DBObject object, Element element, VerticalLayout container) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);

		if (element.getParent() != null) {
			HorizontalLayout header = new HorizontalLayout();
			header.setSpacing(true);

			Label name = new Label(element.getLabel());
			name.setStyleName("h2");
			header.addComponent(name);

			Button deleteButton = new Button();
			deleteButton.setCaption("Delete");
			deleteButton.setStyleName("link");
			header.addComponent(deleteButton);
			header.setComponentAlignment(deleteButton, Alignment.MIDDLE_LEFT);
			
			if (element.getMaxOccurs() == null || element.getMaxOccurs() > 1) {
				Button moveButton = new Button();
				moveButton.setCaption("Move");
				moveButton.setStyleName("link");
				header.addComponent(moveButton);
				header.setComponentAlignment(moveButton, Alignment.MIDDLE_LEFT);
			}

			layout.addComponent(header);
		}

		List<Attribute> attributes = element.getAttributes();
		if (attributes.size() > 0) {
			VerticalLayout attributesContainer = new VerticalLayout();
			attributesContainer.setSpacing(true);
			
			FieldGroup fieldGroup = new FieldGroup();
			fieldGroup.setItemDataSource(new DBObjectItem(object));
			fieldGroup.setBuffered(false);
			
			for (Attribute attribute : attributes) {
				TextField textField = new TextField();
				textField.setWidth("100%");
				textField.setCaption(attribute.getLabel());
				textField.setDescription(attribute.getDescription());
				textField.setNullRepresentation(null);
				attributesContainer.addComponent(textField);
				fieldGroup.bind(textField, attribute.getName());
			}
			
			layout.addComponent(attributesContainer);
		}

		List<Element> elements = element.getElements();
		if (elements.size() > 0) {
			VerticalLayout elementsContainer = new VerticalLayout();
			elementsContainer.setMargin(new MarginInfo(false, false, false, true));
			elementsContainer.setSpacing(true);

			for (Element e : elements) {
				process(object, e, elementsContainer);
			}
			
			layout.addComponent(elementsContainer);
		}
		
		container.addComponent(layout);
	}

	private void processMultiple(List<DBObject> list, Element element, VerticalLayout container) {
		List<Element> elements = element.getElements();
		if (elements.size() == 0) {
			processMultipleAsTable(list, element, container);
		} else {
			processMultipleAsGroup(list, element, container);
		}
	}

	private void processMultipleAsGroup(final List<DBObject> list, final Element element, final VerticalLayout container) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		
		for (DBObject object : list) {
			processSingle(object, element, layout);
		}
		
		final Button addButton = new Button();
		addButton.setStyleName("small");
		addButton.setCaption("Add " + element.getLabel());
		addButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				DBObject object = new BasicDBObject();
				list.add(object);
				processSingle(object, element, layout);
				layout.addComponent(addButton);
			}
		});
		layout.addComponent(addButton);
		
		container.addComponent(layout);
	}

	private void processMultipleAsTable(List<DBObject> list, final Element element, final VerticalLayout container) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		
		HorizontalLayout header = new HorizontalLayout();
		header.setSpacing(true);
		
		Label name = new Label(element.getPluralLabel());
		name.setStyleName("h2");
		header.addComponent(name);
		
		Button addButton = new Button();
		addButton.setCaption("Add " + element.getLabel());
		addButton.setStyleName("link");
		header.addComponent(addButton);
		header.setComponentAlignment(addButton, Alignment.MIDDLE_LEFT);
		
		layout.addComponent(header);
		
		Table table = new Table();
		table.setWidth("100%");
		table.setHeight("200px");
		for (Attribute attribute : element.getAttributes()) {
			table.addContainerProperty(attribute.getName(), String.class, null, attribute.getLabel(), null, null);
		}
		layout.addComponent(table);
		
		container.addComponent(layout);
	}
	
	@SuppressWarnings("rawtypes")
	public static class DBObjectItem implements Item {

		private DBObject dbObject;
		
		public DBObjectItem(DBObject dbObject) {
			this.dbObject = dbObject;
		}

		@Override
		public Collection<?> getItemPropertyIds() {
			return null;
		}
		
		@Override
		public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
			return false;
		}
		
		@Override
		public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
			return false;
		}
		
		@Override
		public Property getItemProperty(Object id) {
			try {
				return new MethodProperty<Object>(
						Object.class, 
						dbObject, 
						DBObject.class.getMethod("get", String.class), 
						DBObject.class.getMethod("put", String.class, Object.class), 
						new Object[] { id }, 
						new Object[] { id, null }, 
						1);
			} catch (Exception e) {
				return null;
			}
		}

	}

	public static class SaveEvent extends Event {
		
		private DBObject document;

		public SaveEvent(Component source, DBObject document) {
			super(source);
			
			this.document = document;
		}
		
		public DBObject getDocument() {
			return document;
		}
		
	}
	
	public interface SaveListener extends Serializable {
		
		public void save(SaveEvent event);
		
	}

	public void addSaveListener(SaveListener listener) {
		addListener(SaveEvent.class, listener, ReflectTools.findMethod(SaveListener.class, "save", SaveEvent.class));
	}
	
	public void removeSaveListener(SaveListener listener) {
		removeListener(SaveEvent.class, listener, ReflectTools.findMethod(SaveListener.class, "save", SaveEvent.class));
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// panel
		panel = buildPanel();
		mainLayout.addComponent(panel);
		mainLayout.setExpandRatio(panel, 1.0f);
		
		// buttons
		buttons = buildButtons();
		mainLayout.addComponent(buttons);
		mainLayout.setComponentAlignment(buttons, new Alignment(6));
		
		return mainLayout;
	}

	@AutoGenerated
	private Panel buildPanel() {
		// common part: create layout
		panel = new Panel();
		panel.setStyleName("light");
		panel.setImmediate(false);
		panel.setWidth("100.0%");
		panel.setHeight("100.0%");
		
		// panelContent
		panelContent = new VerticalLayout();
		panelContent.setImmediate(false);
		panelContent.setWidth("100.0%");
		panelContent.setHeight("100.0%");
		panelContent.setMargin(true);
		panel.setContent(panelContent);
		
		return panel;
	}

	@AutoGenerated
	private HorizontalLayout buildButtons() {
		// common part: create layout
		buttons = new HorizontalLayout();
		buttons.setImmediate(false);
		buttons.setWidth("-1px");
		buttons.setHeight("-1px");
		buttons.setMargin(true);
		buttons.setSpacing(true);
		
		// okButton
		okButton = new Button();
		okButton.setStyleName("primary");
		okButton.setCaption("OK");
		okButton.setImmediate(true);
		okButton.setWidth("-1px");
		okButton.setHeight("-1px");
		buttons.addComponent(okButton);
		
		// cancelButton
		cancelButton = new Button();
		cancelButton.setCaption("Cancel");
		cancelButton.setImmediate(true);
		cancelButton.setWidth("-1px");
		cancelButton.setHeight("-1px");
		buttons.addComponent(cancelButton);
		
		return buttons;
	}

}
