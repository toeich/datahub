package de.jworks.datahub.business.transform.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.NONE)
public class Output extends Item {

	private static final long serialVersionUID = 1L;
	
	@XmlTransient
	private Output parent;
	
    @XmlElement(name = "output")
    private List<Output> outputs = new ArrayList<Output>();

    public Output() {
    }

    public Output(String name, ItemType type, Output... outputs) {
        this.name = name;
        this.type = type;
        this.outputs.addAll(Arrays.asList(outputs));
    }
    
    public Output getParent() {
		return parent;
	}

    public List<Output> getOutputs() {
        return Collections.unmodifiableList(outputs);
    }

    public Output addOutput(Output output) {
    	if (output.parent != null) {
    		output.parent.removeOutput(output);
    	}
    	outputs.add(output);
        output.parent = this;
        firePropertyChange("outputs", null, null);
        return output;
    }
    
    public Output addOutput(Output output, Output after) {
    	if (output.parent != null) {
    		output.parent.removeOutput(output);
    	}
    	outputs.add(outputs.indexOf(after) + 1, output);
        output.parent = this;
        firePropertyChange("outputs", null, null);
        return output;
    }

    public void removeOutput(Output output) {
        outputs.remove(output);
        output.parent = null;
        firePropertyChange("outputs", null, null);
    }
    
    public void resolve() {
    	for (Output output : outputs) {
    		output.resolve();
    		output.parent = this;
    	}
    }

	@Override
	public String toString() {
		return "Output [name=" + name + "]";
	}
    
}
