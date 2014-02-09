package de.jworks.datahub.business.transform.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Input extends Item {

	private static final long serialVersionUID = 1L;
	
    @XmlElement(name = "input")
    private List<Input> inputs = new ArrayList<Input>();

    public Input() {
    }

    public Input(String name, String step, ItemType type, Input... inputs) {
        this.name = name;
        this.step = step;
        this.type = type;
        this.inputs.addAll(Arrays.asList(inputs));
    }

    public List<Input> getInputs() {
        return Collections.unmodifiableList(inputs);
    }

    public Input addInput(Input input) {
        if (inputs.add(input)) {
            firePropertyChange("inputs", null, null);
        }
        return input;
    }

    public void removeInput(Input input) {
        if (inputs.remove(input)) {
            firePropertyChange("inputs", null, null);
        }
    }

	public void resolve() {
		// TODO
	}
	
}
