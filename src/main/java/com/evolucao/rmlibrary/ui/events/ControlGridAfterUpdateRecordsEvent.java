package com.evolucao.rmlibrary.ui.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.production.RmGrid;

public class ControlGridAfterUpdateRecordsEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	Table table = null;
	RmGrid rmGrid = null;
	
	public void setRmGrid(RmGrid rmGrid) {
		this.rmGrid = rmGrid;
	}
	public RmGrid getRmGrid() {
		return this.rmGrid;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public ControlGridAfterUpdateRecordsEvent(Object source) {
		super(source);
	}
	
	public interface ControlGridAfterUpdateRecordsEventListener extends EventListener {
		public void onAfterUpdateRecords(ControlGridAfterUpdateRecordsEvent event);
	}
}

