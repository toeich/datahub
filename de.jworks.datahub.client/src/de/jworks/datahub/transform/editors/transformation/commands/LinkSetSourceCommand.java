package de.jworks.datahub.transform.editors.transformation.commands;

import org.eclipse.gef.commands.Command;

import de.jworks.datahub.business.transform.entity.Link;

public class LinkSetSourceCommand extends Command {

	private Link link;
	
	private String source;

	public void setLink(Link link) {
		this.link = link;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public void execute() {
		link.setSource(source);
	}

}
