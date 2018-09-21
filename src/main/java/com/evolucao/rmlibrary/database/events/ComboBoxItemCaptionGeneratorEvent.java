package com.evolucao.rmlibrary.database.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;

public class ComboBoxItemCaptionGeneratorEvent extends EventObject {
	SimpleRecord simpleRecord;
	Table sourceTable = null;
	String itemCaption = "";
	
	public void setItemCaption(String itemCaption) {
		this.itemCaption = itemCaption;
	}
	public String getItemCaption() {
		return this.itemCaption;
	}
	
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
	
	public ComboBoxItemCaptionGeneratorEvent(Object source) {
		super(source);
	}
	
	public interface ComboBoxItemCaptionGeneratorEventListener extends EventListener {
		public void onComboBoxItemCaptionGenerator(ComboBoxItemCaptionGeneratorEvent event);
	}
}