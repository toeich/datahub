package de.jworks.datahub.presentation.editors;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.business.transform.entity.Input;
import de.jworks.datahub.business.transform.entity.ItemType;
import de.jworks.datahub.presentation.Messages;

public class DatasinkSchemaInputEditor extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private ComboBox type;
	@AutoGenerated
	private TextField name;
	
	public DatasinkSchemaInputEditor(Input input) {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		type.setNullSelectionAllowed(false);
		type.addItem(ItemType.XML_ELEMENT);
		type.addItem(ItemType.XML_ATTRIBUTE);
		
		FieldGroup fieldGroup = new FieldGroup();
		fieldGroup.setItemDataSource(new BeanItem<Input>(input));
		fieldGroup.setBuffered(false);
		fieldGroup.bindMemberFields(this);
		
		Messages.translate(this);
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");
		
		// name
		name = new TextField();
		name.setCaption("Name");
		name.setImmediate(false);
		name.setWidth("100.0%");
		name.setHeight("-1px");
		name.setRequired(true);
		name.setInputPrompt("Name");
		mainLayout.addComponent(name);
		
		// type
		type = new ComboBox();
		type.setCaption("Type");
		type.setImmediate(false);
		type.setWidth("100.0%");
		type.setHeight("-1px");
		type.setRequired(true);
		mainLayout.addComponent(type);
		
		return mainLayout;
	}

}
