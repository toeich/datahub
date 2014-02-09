package de.jworks.datahub.business.transform.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "schema")
@XmlAccessorType(XmlAccessType.NONE)
public class ComponentSchema extends Notifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElementWrapper(name = "inputs")
    @XmlElement(name = "input")
    private List<Input> inputs = new ArrayList<Input>();
    
    @XmlElementWrapper(name = "outputs")
    @XmlElement(name = "output")
    private List<Output> outputs = new ArrayList<Output>();

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

    public List<Output> getOutputs() {
        return Collections.unmodifiableList(outputs);
    }

    public Output addOutput(Output output) {
        outputs.add(output);
        firePropertyChange("outputs", null, null);
        return output;
    }

    public void removeOutput(Output output) {
        outputs.remove(output);
        firePropertyChange("outputs", null, null);
    }

	public void resolve() {
		for (Output output : outputs) {
			output.resolve();
		}
		for (Input input : inputs) {
			input.resolve();
		}
	}
	
}
