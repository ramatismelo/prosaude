package com.evolucao.rmlibrary.database;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;

public class TableRegistry {
	String tableName = null;
	protected EventListenerList listenerList = new EventListenerList();
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName() {
		return this.tableName;
	}
	
	public TableRegistry() {
	}

	public void addLoadTableEventListener(LoadTableEventListener listener) {
		listenerList.add(LoadTableEventListener.class, listener);
	}

	public void removeLoadTableEventListener(LoadTableEventListener listener) {
		listenerList.remove(LoadTableEventListener.class, listener);
	}

	public void fireLoadTableEvent(LoadTableEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == LoadTableEventListener.class) {
				((LoadTableEventListener) listeners[i + 1]).onLoadTable(event);
			}
		}
	}
}
