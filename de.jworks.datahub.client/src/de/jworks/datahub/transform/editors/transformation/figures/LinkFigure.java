package de.jworks.datahub.transform.editors.transformation.figures;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

public class LinkFigure extends PolylineConnection {

	public LinkFigure() {
		setConnectionRouter(new AbstractRouter() {
			
			@Override
			public void route(Connection connection) {
				PointList points = connection.getPoints();
				points.removeAllPoints();
				Point p;
				
				p = getStartPoint(connection);
				connection.translateToRelative(p);
				points.addPoint(p);
				points.addPoint(p.getTranslated(10, 0));
				
				p = getEndPoint(connection);
				connection.translateToRelative(p);
				points.addPoint(p.getTranslated(-10, 0));
				points.addPoint(p);
				
				connection.setPoints(points);
			}
		});
	}
}
