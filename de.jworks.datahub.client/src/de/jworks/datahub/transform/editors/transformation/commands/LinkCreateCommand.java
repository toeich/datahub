package de.jworks.datahub.transform.editors.transformation.commands;

import org.eclipse.gef.commands.Command;

import de.jworks.datahub.business.transform.entity.Link;
import de.jworks.datahub.business.transform.entity.Transformation;

public class LinkCreateCommand extends Command {

	private Transformation transformation;
	
	private String source;
	
	private String target;

	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	@Override
	public void execute() {
		Link link = new Link();
		link.setSource(source);
		link.setTarget(target);
		transformation.getDefinition().addLink(link);
	}

}
