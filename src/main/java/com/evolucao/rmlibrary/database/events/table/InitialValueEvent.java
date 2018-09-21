package com.evolucao.rmlibrary.database.events.table;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class InitialValueEvent extends EventObject { 
	private static final long serialVersionUID = 1L;
	Table table = null;
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public InitialValueEvent(Object source) {
		super(source);
	}

	public interface InitialValueEventListener extends EventListener {
		public void onInitialValue(InitialValueEvent event);
	}
}
