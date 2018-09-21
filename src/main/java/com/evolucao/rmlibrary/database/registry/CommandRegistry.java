package com.evolucao.rmlibrary.database.registry;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;

public class CommandRegistry {
	String commandName = null;
	protected EventListenerList listenerList = new EventListenerList();
	
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	public String getCommandName() {
		return this.commandName;
	}
	
	public CommandRegistry() {
	}

	public void addCommandEventListener(CommandEventListener listener) {
		listenerList.add(CommandEventListener.class, listener);
	}

	public void removeCommandEventListener(CommandEventListener listener) {
		listenerList.remove(CommandEventListener.class, listener);
	}

	public void fireCommandEvent(CommandEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == CommandEventListener.class) {
				((CommandEventListener) listeners[i + 1]).onCommandExecute(event);
			}
		}
	}
}
