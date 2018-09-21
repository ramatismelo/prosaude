package com.evolucao.rmlibrary.ui;

import com.vaadin.ui.TextField;
import com.evolucao.rmlibrary.database.Record;

public class RecordTextField extends TextField {
	private Record record = new Record(null);
	
	public void setRecord(Record record) {
		this.record = record;
	}
	public Record getRecord() {
		return this.record;
	}
	
	public RecordTextField() {
		super();
	}
	
	public void setValue(String fieldName, String value) {
		this.record.setValue(fieldName, value);	
	}
	
	public String getString(String fieldName) {
		return this.record.getString(fieldName);
	}
	
	public void setValue(String fieldName, Double value) {
		this.record.setValue(fieldName, value);
	}
	
	public Double getDouble(String fieldName) {
		return this.record.getDouble(fieldName);
	}	
}
