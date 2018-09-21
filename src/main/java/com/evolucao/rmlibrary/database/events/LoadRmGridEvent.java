package com.evolucao.rmlibrary.database.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.production.RmGrid;

public class LoadRmGridEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	RmGrid rmGrid = null;
	Table table = null;
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}

	public void setRmGrid(RmGrid rmGrid) {
		this.rmGrid = rmGrid;
	}
	public RmGrid getRmGrid() {
		return this.rmGrid;
	}
	
	public LoadRmGridEvent(Object source) {
		super(source);
	}
	
	public interface LoadRmGridEventListener extends EventListener {
		public void onLoadRmGrid(LoadRmGridEvent event);
	}
}