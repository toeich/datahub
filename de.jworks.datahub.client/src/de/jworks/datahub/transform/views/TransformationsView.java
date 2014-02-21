package de.jworks.datahub.transform.views;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import de.jworks.datahub.business.transform.boundary.TransformationService;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.transform.editors.transformation.TransformationEditor;
import de.jworks.datahub.transform.editors.transformation.TransformationEditorInput;
import de.jworks.datahub.transform.utils.SelectionHelper;

public class TransformationsView extends ViewPart {
	
	public static final String ID = "de.jworks.datahub.transform.transformations";
	
	private ListViewer viewer;

	public TransformationsView() {
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		
		registerHandlers();
	}
	
	private void registerHandlers() {
		IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
		handlerService.activateHandler("de.jworks.datahub.transform.open", new OpenHandler());
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new ListViewer(parent);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		getSite().setSelectionProvider(viewer);

		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, viewer);
	}

	@Override
	public void setFocus() {
		loadTransformations();
	}

	private void loadTransformations() {
		TransformationService transformationService = (TransformationService) getSite().getService(TransformationService.class);
		viewer.setInput(transformationService.getTransformations());
	}
	
	private final class OpenHandler extends AbstractHandler {
		
		@Override
		public boolean isEnabled() {
			Object firstElement = SelectionHelper.getFirstElement(viewer.getSelection(), Object.class);
			return firstElement instanceof Transformation;
		}

		@Override
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				Transformation transformation = SelectionHelper.getFirstElement(viewer.getSelection(), Transformation.class);
				if (transformation != null) {
					getSite().getPage().openEditor(new TransformationEditorInput(transformation), TransformationEditor.ID);
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}

}
