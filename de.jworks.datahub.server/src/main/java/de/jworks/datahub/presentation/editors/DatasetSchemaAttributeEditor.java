package de.jworks.datahub.presentation.editors;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.business.datasets.entity.Attribute;
import de.jworks.datahub.business.datasets.entity.AttributeType;
import de.jworks.datahub.presentation.Messages;

public class DatasetSchemaAttributeEditor extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout value;
	@AutoGenerated
	private TextField constraint;
	@AutoGenerated
	private ComboBox type;
	@AutoGenerated
	private TextArea description;
	@AutoGenerated
	private TextField label;
	@AutoGenerated
	private TextField name;
	
	public DatasetSchemaAttributeEditor(Attribute attribute) {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		name.addValidator(new RegexpValidator("\\w+", "error"));
		name.setValidationVisible(true);
		
		type.setNullSelectionAllowed(false);
		type.addItem(AttributeType.STRING);
		
		FieldGroup fieldGroup = new FieldGroup();
		fieldGroup.setItemDataSource(new BeanItem<Attribute>(attribute));
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
		name.setCaption("Name:");
		name.setImmediate(false);
		name.setWidth("100.0%");
		name.setHeight("-1px");
		name.setRequired(true);
		mainLayout.addComponent(name);
		
		// label
		label = new TextField();
		label.setCaption("Label:");
		label.setImmediate(false);
		label.setWidth("100.0%");
		label.setHeight("-1px");
		label.setRequired(true);
		mainLayout.addComponent(label);
		
		// description
		description = new TextArea();
		description.setCaption("Description:");
		description.setImmediate(false);
		description.setWidth("100.0%");
		description.setHeight("-1px");
		mainLayout.addComponent(description);
		
		// value
		value = buildValue();
		mainLayout.addComponent(value);
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildValue() {
		// common part: create layout
		value = new HorizontalLayout();
		value.setImmediate(false);
		value.setWidth("100.0%");
		value.setHeight("-1px");
		value.setMargin(false);
		value.setSpacing(true);
		
		// type
		type = new ComboBox();
		type.setCaption("Type");
		type.setImmediate(false);
		type.setWidth("100.0%");
		type.setHeight("-1px");
		type.setRequired(true);
		value.addComponent(type);
		value.setExpandRatio(type, 1.0f);
		
		// constraint
		constraint = new TextField();
		constraint.setCaption("Constraint");
		constraint.setImmediate(false);
		constraint.setWidth("100.0%");
		constraint.setHeight("-1px");
		value.addComponent(constraint);
		value.setExpandRatio(constraint, 1.0f);
		
		return value;
	}

}
