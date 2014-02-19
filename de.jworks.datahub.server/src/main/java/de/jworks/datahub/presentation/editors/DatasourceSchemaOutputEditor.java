package de.jworks.datahub.presentation.editors;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.jworks.datahub.business.transform.entity.Output;
import de.jworks.datahub.presentation.Messages;

public class DatasourceSchemaOutputEditor extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private TextField step;
	@AutoGenerated
	private TextField name;
	public DatasourceSchemaOutputEditor(Output output) {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		Messages.translate(this);
		
		FieldGroup fieldGroup = new FieldGroup();
		fieldGroup.setItemDataSource(new BeanItem<Output>(output));
		fieldGroup.setBuffered(false);
		fieldGroup.bindMemberFields(this);
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
		
		// step
		step = new TextField();
		step.setCaption("Step");
		step.setImmediate(false);
		step.setWidth("100.0%");
		step.setHeight("-1px");
		step.setRequired(true);
		mainLayout.addComponent(step);
		
		return mainLayout;
	}

}
