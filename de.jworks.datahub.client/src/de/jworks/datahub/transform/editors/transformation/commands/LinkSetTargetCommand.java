package de.jworks.datahub.transform.editors.transformation.commands;

import org.eclipse.gef.commands.Command;

import de.jworks.datahub.business.transform.entity.Link;

public class LinkSetTargetCommand extends Command {

	private Link link;
	
	private String target;

	public void setLink(Link link) {
		this.link = link;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public void execute() {
		link.setTarget(target);
	}
	
}
