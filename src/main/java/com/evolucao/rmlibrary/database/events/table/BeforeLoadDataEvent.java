package com.evolucao.rmlibrary.database.events.table;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class BeforeLoadDataEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	Table table = null;
	boolean cancel = false;
	
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
	public boolean getCancel() {
		return this.cancel;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public BeforeLoadDataEvent(Object source) {
		super(source);
	}
	
	public interface BeforeLoadDataEventListener extends EventListener {
		public void onBeforeLoadData(BeforeLoadDataEvent event);
	}
}
