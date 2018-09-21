package com.evolucao.rmlibrary.database;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;

public class RmGridRegistry {
	String rmGridName = null;
	protected EventListenerList listenerList = new EventListenerList();

	public void setRmGridName(String rmGridName) {
		this.rmGridName = rmGridName;
	}
	public String getRmGridName() {
		return this.rmGridName;
	}
	
	public RmGridRegistry() {
	}

	public void addLoadRmGridEventListener(LoadRmGridEventListener listener) {
		listenerList.add(LoadRmGridEventListener.class, listener);
	}

	public void removeLoadRmGridEventListener(LoadRmGridEventListener listener) {
		listenerList.remove(LoadRmGridEventListener.class, listener);
	}

	public void fireLoadRmGridEvent(LoadRmGridEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == LoadRmGridEventListener.class) {
				((LoadRmGridEventListener) listeners[i + 1]).onLoadRmGrid(event);
			}
		}
	}
}
