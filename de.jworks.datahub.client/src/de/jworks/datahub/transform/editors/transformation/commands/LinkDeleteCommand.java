package de.jworks.datahub.transform.editors.transformation.commands;

import org.eclipse.gef.commands.Command;

import de.jworks.datahub.business.transform.entity.Link;
import de.jworks.datahub.business.transform.entity.Transformation;

public class LinkDeleteCommand extends Command {

	private Transformation transformation;
	
	private Link link;

	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}
	
	public void setLink(Link link) {
		this.link = link;
	}

	@Override
	public void execute() {
		transformation.getDefinition().removeLink(link);
	}
	
}
