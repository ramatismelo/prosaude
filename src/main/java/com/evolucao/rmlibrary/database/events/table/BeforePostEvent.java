package com.evolucao.rmlibrary.database.events.table;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class BeforePostEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private boolean cancel = false;
	Table table = null;
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public void Cancel() {
		this.cancel = true;
	}
	public boolean isCanceled() {
		return this.cancel;
	}
	
	public BeforePostEvent(Object source) {
		super(source);
	}

	public interface BeforePostEventListener extends EventListener {
		public void onBeforePost(BeforePostEvent event);
	}
}
