package de.jworks.datahub.transform.editors.transformation;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import de.jworks.datahub.business.transform.entity.Transformation;

@SuppressWarnings("rawtypes")
public class TransformationEditorInput implements IEditorInput {

	private Transformation transformation;
	
	public TransformationEditorInput(Transformation transformation) {
		this.transformation = transformation;
	}

	public Transformation getTransformation() {
		return transformation;
	}
	
	@Override
	public String getName() {
		return String.valueOf(transformation);
	}

	@Override
	public String getToolTipText() {
		return getName();
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}
	
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == Transformation.class) {
			return getTransformation();
		}
		return null;
	}
	
	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((transformation == null) ? 0 : transformation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TransformationEditorInput other = (TransformationEditorInput) obj;
		if (transformation == null) {
			if (other.transformation != null) return false;
		} else if (!transformation.equals(other.transformation)) return false;
		return true;
	}

}
