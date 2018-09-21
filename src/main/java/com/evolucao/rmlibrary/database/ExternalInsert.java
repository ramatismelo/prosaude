package com.evolucao.rmlibrary.database;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.events.table.ExternalInsertEvent;
import com.evolucao.rmlibrary.database.events.table.ExternalInsertEvent.ExternalInsertEventListener;

public class ExternalInsert {
	private String targetTableName = null;
	private boolean performDeleteTargetIfUpdateSourceRecord = true;
	
	protected EventListenerList listenerList = new EventListenerList();
	

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	public String getTargetTableName() {
		return this.targetTableName;
	}
	
	public void setPerformDeleteTargetIfUpdateSourceRecord(boolean performDelete) {
		this.performDeleteTargetIfUpdateSourceRecord = performDelete;
	}
	public boolean getPerformDeleteTargetIfUpdateSourceRecord() {
		return this.performDeleteTargetIfUpdateSourceRecord;
	}
	
	public ExternalInsert() {
		
	}
	
	/*
	 * 
	 */
	public void addExternalInsertEventListener(ExternalInsertEventListener listener) {
		listenerList.add(ExternalInsertEventListener.class, listener);
	}

	public void removeExternalInsertEventListener(ExternalInsertEventListener listener) {
		listenerList.remove(ExternalInsertEventListener.class, listener);
	}

	void fireExternalInsertEvent(ExternalInsertEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ExternalInsertEventListener.class) {
				((ExternalInsertEventListener) listeners[i + 1]).onExternalInsert(event);
			}
		}
	}
}
