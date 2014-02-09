package de.jworks.datahub.business.transform.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public abstract class Notifier implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private transient PropertyChangeSupport propertyChangeSupport;

	public Notifier() {
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (propertyChangeSupport == null) {
			propertyChangeSupport = new PropertyChangeSupport(this);
		}
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (propertyChangeSupport != null) {
			propertyChangeSupport.removePropertyChangeListener(listener);
		}
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		if (propertyChangeSupport != null) {
			propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
}
