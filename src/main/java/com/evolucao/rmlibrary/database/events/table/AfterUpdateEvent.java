package com.evolucao.rmlibrary.database.events.table;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;

public class AfterUpdateEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	Table table = null;
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public AfterUpdateEvent(Object source) {
		super(source);
	}
	
	public interface AfterUpdateEventListener extends EventListener {
		public void onAfterUpdate(AfterUpdateEvent event);
	}
}
