package de.jworks.datahub.transform.utils;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class SelectionHelper {

	public static <T> T getFirstElement(ISelection selection, Class<T> type) {
		if (selection instanceof IStructuredSelection) {
			return getFirstElement((IStructuredSelection) selection, type);
		}
		return null;
	}

	public static <T> T getFirstElement(IStructuredSelection selection, Class<T> type) {
		Object firstElement = selection.getFirstElement();
		if (type.isInstance(firstElement)) {
			return type.cast(firstElement);
		}
		return null;
	}
	
}
