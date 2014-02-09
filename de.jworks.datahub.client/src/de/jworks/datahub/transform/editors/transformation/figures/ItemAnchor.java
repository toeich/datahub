package de.jworks.datahub.transform.editors.transformation.figures;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

public class ItemAnchor extends ChopboxAnchor {

	private int dx;
	
	public ItemAnchor(IFigure owner, int dx) {
		super(owner);
		this.dx = dx;
	}

	@Override
	public Point getLocation(Point reference) {
		Point p = getOwner().getBounds().getCenter();
		getOwner().translateToAbsolute(p);
		p.translate(dx, 0);
		return p;
	}

}
