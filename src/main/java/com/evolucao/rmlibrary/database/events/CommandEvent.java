package com.evolucao.rmlibrary.database.events;


import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class CommandEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public CommandEvent(Object source) {
		super(source);
	}
	
	public interface CommandEventListener extends EventListener {
		public void onCommandExecute(CommandEvent event);
	}
}