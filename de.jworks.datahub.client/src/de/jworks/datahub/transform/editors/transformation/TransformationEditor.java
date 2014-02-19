package de.jworks.datahub.transform.editors.transformation;

import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.EventObject;

import javax.xml.bind.JAXB;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.IHandlerService;

import de.jworks.datahub.business.transform.boundary.TransformationService;
import de.jworks.datahub.business.transform.entity.Constant;
import de.jworks.datahub.business.transform.entity.Filter;
import de.jworks.datahub.business.transform.entity.Group;
import de.jworks.datahub.business.transform.entity.Library;
import de.jworks.datahub.business.transform.entity.Lookup;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationComponent;
import de.jworks.datahub.client.Activator;
import de.jworks.datahub.transform.dialogs.ConstantDialog;
import de.jworks.datahub.transform.editors.transformation.commands.TransformationComponentAddCommand;
import de.jworks.datahub.transform.editors.transformation.editparts.TransformEditPartFactory;
import de.jworks.datahub.transform.editors.transformation.requests.TransformationComponentFactory;

public class TransformationEditor extends GraphicalEditorWithFlyoutPalette {

	public static final String ID = "de.jworks.fps.transform.transformation";

	private Transformation transformation;
	
	private Point menuLocation;

	public TransformationEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		
		registerHandlers();
		
		if (input instanceof TransformationEditorInput) {
			transformation = ((TransformationEditorInput) input).getTransformation();
		}
		
		setPartName(transformation.toString());
	}

	private void registerHandlers() {
		IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);

		for (ActionFactory actionFactory : Arrays.asList(ActionFactory.SAVE, ActionFactory.PRINT, ActionFactory.UNDO, ActionFactory.REDO, ActionFactory.SELECT_ALL, ActionFactory.DELETE)) {
			handlerService.activateHandler(
					actionFactory.getCommandId(), 
					new ActionHandler(getActionRegistry().getAction(actionFactory.getId())));
		}

		handlerService.activateHandler("de.jworks.fps.transform.addConstant", new AddConstantHandler());
		handlerService.activateHandler("de.jworks.fps.transform.addFilter", new AddFilterHandler());
	}

	@Override
	public void commandStackChanged(EventObject event) {
		super.commandStackChanged(event);
		
		firePropertyChange(PROP_DIRTY);
	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		
		// create context menu
		MenuManager menuManager = new MenuManager();
		getGraphicalViewer().setContextMenu(menuManager);
		getEditorSite().registerContextMenu(menuManager, getGraphicalViewer());
		
		// save menu location
		getGraphicalControl().addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				menuLocation = new Point(getGraphicalControl().toControl(e.x, e.y));
			}
		});
	}
	
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new TransformEditPartFactory(transformation));
	}

	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(transformation.getDefinition());
	}
	
	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteRoot root = new PaletteRoot();
		
		PaletteGroup toolsGroup = new PaletteGroup("Tools");
		toolsGroup.add(new SelectionToolEntry());
		root.add(toolsGroup);
		
		PaletteGroup lookupsGroup = new PaletteGroup("Lookups");
		TransformationService transformationService = (TransformationService) PlatformUI.getWorkbench().getService(TransformationService.class);
		for (Lookup lookup : transformationService.getLookups()) {
			lookupsGroup.add(new CombinedTemplateCreationEntry(lookup.getName(), null, new TransformationComponentFactory(lookup), null, null));
		}
		
		Enumeration<URL> entries = Activator.getDefault().getBundle().findEntries("/", "library.xml", true);
		/*while*/ if (entries.hasMoreElements()) { 
			URL entry = (URL) entries.nextElement();
			try {
				Library library = JAXB.unmarshal(entry.openStream(), Library.class);
				for (Group group : library.getGroups()) {
					PaletteDrawer drawer = new PaletteDrawer(group.getName());
					for (TransformationComponent component : group.getComponents()) {
						drawer.add(new CombinedTemplateCreationEntry(component.getName(), null, new TransformationComponentFactory(component), null, null));
					}
					root.add(drawer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return root;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		transformation.updateData();
		
		TransformationService transformationService = (TransformationService) PlatformUI.getWorkbench().getService(TransformationService.class);
		transformationService.updateTransformation(transformation);
		
		getCommandStack().markSaveLocation();
	}
	
	private class AddConstantHandler extends AbstractHandler {
		
		@Override
		public Object execute(ExecutionEvent event) throws ExecutionException {
			Constant constant = new Constant();
			constant.setName("constant." + TransformationComponentFactory.nextId++);
			
			ConstantDialog dialog = new ConstantDialog(getSite().getShell());
			dialog.setTitle("Add Constant");
			dialog.setConstant(constant);
			if (dialog.open() == ConstantDialog.OK) {
				TransformationComponentAddCommand command = new TransformationComponentAddCommand();
				command.setTransformation(transformation);
				command.setComponent(constant);
				command.setLocation(menuLocation);
				getCommandStack().execute(command);
			}
			
			return null;
		}
	}
	
	private class AddFilterHandler extends AbstractHandler {
		
		@Override
		public Object execute(ExecutionEvent event) throws ExecutionException {
			Filter filter = new Filter();
			filter.setName("filter." + TransformationComponentFactory.nextId++);
			
			TransformationComponentAddCommand command = new TransformationComponentAddCommand();
			command.setTransformation(transformation);
			command.setComponent(filter);
			command.setLocation(menuLocation);
			getCommandStack().execute(command);
			
			return null;
		}
	}
}