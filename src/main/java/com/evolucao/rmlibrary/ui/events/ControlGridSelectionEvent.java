package com.evolucao.rmlibrary.ui.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;

public class ControlGridSelectionEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	SimpleRecord recordSelected = null;
	Table table = null;
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public void setRecordSelected(SimpleRecord recordSelected) {
		this.recordSelected = recordSelected;
	}
	public SimpleRecord getRecordSelected() {
		return this.recordSelected;
	}

	public ControlGridSelectionEvent(Object source) {
		super(source);
	}
	
	public interface ControlGridSelectionEventListener extends EventListener {
		public void onSelection(ControlGridSelectionEvent event);
	}
}

