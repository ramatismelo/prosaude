package com.evolucao.rmlibrary.database;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.AfterForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.AfterForeingSearchEvent.AfterForeingSearchEventListener;

public class ExistenceCheck {
	String targetTableName = null;
	String targetIndexName = null;
	String relationship = null;
	String errorMessage = null;
	protected EventListenerList listenerList = new EventListenerList();
	
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	public String getTargetTableName() {
		return this.targetTableName;
	}
	
	public void setTargetIndexName(String targetIndexName) {
		this.targetIndexName = targetIndexName;
	}
	public String getTargetIndexName() {
		return this.targetIndexName;
	}
	
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getRelationship() {
		return this.relationship;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public ExistenceCheck() {
		
	}
	
	/*
	 * EVENTOS
	 */
	public void addAfterExistenceCheckEventListener(AfterExistenceCheckEventListener listener) {
		listenerList.add(AfterExistenceCheckEventListener.class, listener);
	}

	public void removeAfterExistenceCheckEventListener(AfterExistenceCheckEventListener listener) {
		listenerList.remove(AfterExistenceCheckEventListener.class, listener);
	}

	void fireAfterExistenceCheckEvent(AfterExistenceCheckEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterExistenceCheckEventListener.class) {
				((AfterExistenceCheckEventListener) listeners[i + 1]).onAfterExistenceCheck(event);
			}
		}
	}
}
