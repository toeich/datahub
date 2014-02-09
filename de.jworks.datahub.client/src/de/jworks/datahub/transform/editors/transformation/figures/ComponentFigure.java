package de.jworks.datahub.transform.editors.transformation.figures;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Triangle;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;

public abstract class ComponentFigure extends Figure {

	// uri -> anchor
	protected Map<String, ConnectionAnchor> inputAnchors = new HashMap<String, ConnectionAnchor>();

	// uri -> anchor
	protected Map<String, ConnectionAnchor> outputAnchors = new HashMap<String, ConnectionAnchor>();

	public ComponentFigure(String name, List<String> inputSpecs, List<String> outputSpecs) {
		setBorder(new LineBorder());

		int numColumns = (inputSpecs.size() > 0 ? 1 : 0) + (outputSpecs.size() > 0 ? 1 : 0);
		
		GridLayout gridLayout = new GridLayout(numColumns, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 10;
		gridLayout.verticalSpacing = 0;
		setLayoutManager(gridLayout);

		Label label = new Label(name);
		label.setBackgroundColor(ColorConstants.titleBackground);
		label.setOpaque(true);
		add(label);
		setConstraint(label, new GridData(SWT.FILL, SWT.CENTER, true, false, numColumns, 1));

		if (inputSpecs.size() > 0) {
			createInputs(name, inputSpecs);
		}
		
		if (outputSpecs.size() > 0) {
			createOutputs(name, outputSpecs);
		}
	}

	protected void createInputs(String name, List<String> inputSpecs) {
		Figure figure = new Figure();
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 5;
		gridLayout.verticalSpacing = 0;
		figure.setLayoutManager(gridLayout);
		add(figure);
		setConstraint(figure, new GridData(SWT.FILL, SWT.CENTER, true, false));

		for (String inputSpec : inputSpecs) {
			String[] tokens = inputSpec.split("#");
			inputAnchors.put(tokens[0], createInputFigure(figure, Integer.parseInt(tokens[1]), tokens[2]));
		}
	}

	protected ConnectionAnchor createInputFigure(Figure parent, int indent, String name) {
		Triangle triangle = new Triangle();
		triangle.setDirection(PositionConstants.EAST);
		triangle.setPreferredSize(9, 9);
		parent.add(triangle);
		
		Label label = new Label(name);
		parent.add(label);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalIndent = indent * 10;
		parent.setConstraint(label, gridData);
		
		ConnectionAnchor anchor = new ItemAnchor(triangle, -2);
		return anchor;
	}
	
	protected void createOutputs(String name, List<String> outputSpecs) {
		Figure figure = new Figure();
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 5;
		gridLayout.verticalSpacing = 0;
		figure.setLayoutManager(gridLayout);
		add(figure);
		setConstraint(figure, new GridData(SWT.FILL, SWT.CENTER, true, false));

		for (String outputSpec : outputSpecs) {
			String[] tokens = outputSpec.split("#");
			outputAnchors.put(tokens[0], createOutputFigure(figure, Integer.parseInt(tokens[1]), tokens[2]));
		}
	}
	
	protected ConnectionAnchor createOutputFigure(Figure parent, int indent, String name) {
		Label label = new Label(name);
		parent.add(label);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalIndent = indent * 10;
		parent.setConstraint(label, gridData);
		
		Triangle triangle = new Triangle();
		triangle.setDirection(PositionConstants.EAST);
		triangle.setPreferredSize(9, 9);
		parent.add(triangle);
		
		ConnectionAnchor anchor = new ItemAnchor(triangle, 2);
		return anchor;
	}
	
	public String getUri(ConnectionAnchor anchor) {
		String uri = getKey(anchor, inputAnchors);
		if (uri == null) {
			uri = getKey(anchor, outputAnchors);
		}
		return uri;
	}
	
	protected <K, V> K getKey(V value, Map<K, V> map) {
		for (Entry<K, V> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public ConnectionAnchor getOutputAnchor(String uri) {
		return outputAnchors.get(uri);
	}
	
	public ConnectionAnchor getInputAnchor(String uri) {
		return inputAnchors.get(uri);
	}
	
	public ConnectionAnchor getOutputAnchor(Point location) {
		return getNearestAnchor(outputAnchors.values(), location);
	}

	public ConnectionAnchor getInputAnchor(Point location) {
		return getNearestAnchor(inputAnchors.values(), location);
	}
	
	protected ConnectionAnchor getNearestAnchor(Collection<ConnectionAnchor> anchors, Point location) {
		ConnectionAnchor nearest = null;
		double minDistance = Double.POSITIVE_INFINITY;
		for (ConnectionAnchor anchor : anchors) {
			double distance = location.getDistance(anchor.getLocation(getLocation()));
			if (distance < minDistance) {
				nearest = anchor;
				minDistance = distance; 
			}
		}
		return nearest;
	}
}