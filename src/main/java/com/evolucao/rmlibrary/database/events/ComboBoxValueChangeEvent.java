package com.evolucao.rmlibrary.database.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;

public class ComboBoxValueChangeEvent extends EventObject {
	SimpleRecord simpleRecord;
	Table sourceTable = null;
	
	public void setSourceTable(Table sourceTable) {
		this.sourceTable = sourceTable;
	}
	public Table getSourceTable() {
		return this.sourceTable;
	}

	public void setSimpleRecord(SimpleRecord simpleRecord) {
		this.simpleRecord = simpleRecord;
	}
	public SimpleRecord getSimpleRecord() {
		return this.simpleRecord;
	}
	
	public ComboBoxValueChangeEvent(Object source) {
		super(source);
	}
	
	public interface ComboBoxValueChangeEventListener extends EventListener {
		public void onComboBoxValueChange(ComboBoxValueChangeEvent event);
	}
}

	

