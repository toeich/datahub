package de.jworks.datahub.transform.editors.transformation.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.ConnectionDragCreationTool;

import de.jworks.datahub.business.transform.entity.Component;
import de.jworks.datahub.business.transform.entity.Input;
import de.jworks.datahub.business.transform.entity.Link;
import de.jworks.datahub.business.transform.entity.Output;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.editpolicies.ComponentGraphicalNodeEditPolicy;
import de.jworks.datahub.transform.editors.transformation.figures.ComponentFigure;

public abstract class ComponentEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener, NodeEditPart {

	protected Transformation context;

	public ComponentEditPart(Component model, Transformation context) {
		this.context = context;
		setModel(model);
	}
	
	public Transformation getContext() {
		return context;
	}
	
	@Override
	public Component getModel() {
		return (Component) super.getModel();
	}

	@Override
	protected List<?> getModelSourceConnections() {
		List<Link> links = new ArrayList<Link>();
		for (Link link : context.getDefinition().getLinks()) {
			if (link.getSource().startsWith(getModel().getName() + ":")) {
				links.add(link);
			}
		}
		return links;
	}
	
	@Override
	protected List<?> getModelTargetConnections() {
		List<Link> links = new ArrayList<Link>();
		for (Link link : context.getDefinition().getLinks()) {
			if (link.getTarget().startsWith(getModel().getName() + ":")) {
				links.add(link);
			}
		}
		return links;
	}
	
	@Override
	public ComponentFigure getFigure() {
		return (ComponentFigure) super.getFigure();
	}
	
	protected Rectangle getFigureBounds() {
		return new Rectangle(getFigureLocation(), getFigureSize());
	}
	
	protected Point getFigureLocation() {
		int[] location = getModel().getLocation();
		return new Point(location[0], location[1]);
	}
	
	protected Dimension getFigureSize() {
		return getFigure().getPreferredSize();
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ComponentGraphicalNodeEditPolicy());
	}

	@Override
	public void activate() {
		super.activate();
		getModel().addPropertyChangeListener(this);
	}
	
	@Override
	public void deactivate() {
		super.deactivate();
		getModel().removePropertyChangeListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}
	
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		((AbstractGraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), getFigureBounds());
	}
	
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		Link link = ((LinkEditPart) connection).getModel();
		return getFigure().getOutputAnchor(link.getSource());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		Link link = ((LinkEditPart) connection).getModel();
		return getFigure().getInputAnchor(link.getTarget());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		if (request instanceof DropRequest) {
			return getFigure().getOutputAnchor(((DropRequest) request).getLocation());
		}
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		if (request instanceof DropRequest) {
			return getFigure().getInputAnchor(((DropRequest) request).getLocation());
		}
		return null;
	}
	
	@Override
	public DragTracker getDragTracker(Request request) {
		if (request instanceof SelectionRequest) {
			Point location = ((SelectionRequest) request).getLocation();
			ConnectionAnchor anchor = getFigure().getOutputAnchor(location);
			if (anchor != null  && anchor.getOwner().getBounds().contains(location)) {
				return new ConnectionDragCreationTool();
			}
		}
		return super.getDragTracker(request);
	}
	
	protected List<String> getInputSpecs() {
		List<String> inputSpecs = new ArrayList<String>();
		String componentPath = getModel().getName() + ":";
		for (Input input : getModel().getSchema().getInputs()) {
			collectInputSpecs(input, componentPath, 0, inputSpecs);
		}
		return inputSpecs;
	}

	private void collectInputSpecs(Input input, String parentPath, int indent, List<String> inputSpecs) {
		String inputPath = parentPath + "/" + input.getStep();
		inputSpecs.add(inputPath + "#" + indent + "#" + input.getName());
		for (Input i : input.getInputs()) {
			collectInputSpecs(i, inputPath, indent + 1, inputSpecs);
		}
	}
	
	protected List<String> getOutputSpecs() {
		List<String> outputSpecs = new ArrayList<String>();
		String componentPath = getModel().getName() + ":";
		for (Output output : getModel().getSchema().getOutputs()) {
			collectOutputSpecs(output, componentPath, 0, outputSpecs);
		}
		return outputSpecs;
	}

	private void collectOutputSpecs(Output output, String parentPath, int indent, List<String> outputSpecs) {
		String outputPath = parentPath + "/" + output.getStep();
		outputSpecs.add(outputPath + "#" + indent + "#" + output.getName());
		for (Output o : output.getOutputs()) {
			collectOutputSpecs(o, outputPath, indent + 1, outputSpecs);
		}
	}
}