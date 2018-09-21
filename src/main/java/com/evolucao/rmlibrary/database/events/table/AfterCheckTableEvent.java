package com.evolucao.rmlibrary.database.events.table;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class AfterCheckTableEvent extends EventObject { 
	private static final long serialVersionUID = 1L;
	Table table = null;
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public AfterCheckTableEvent(Object source) {
		super(source);
	}

	public interface AfterCheckTableEventListener extends EventListener {
		public void onAfterCheckTable(AfterCheckTableEvent event);
	}
}
