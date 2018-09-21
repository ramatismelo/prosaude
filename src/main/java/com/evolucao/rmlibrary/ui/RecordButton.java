package com.evolucao.rmlibrary.ui;

import java.util.Date;

import com.evolucao.rmlibrary.database.Record;
import com.vaadin.ui.Button;

public class RecordButton extends Button {
	private Record record = new Record(null);
	
	public void setRecord(Record record) {
		this.record = record;
	}
	public Record getRecord() {
		return this.record;
	}
	
	public RecordButton() {
		super();
	}
	
	public RecordButton(String caption) {
		super(caption);
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
	
	public void setValue(String fieldName, Boolean value) {
		this.record.setValue(fieldName, value);
	}
	
	public Boolean getBoolean(String fieldName) {
		return this.record.getBoolean(fieldName);
	}
	
	public void setValue(String fieldName, Date value) {
		this.record.setValue(fieldName, value);
	}
	public Date getDate(String fieldName) {
		return this.record.getDate(fieldName);
	}
	
	public void setValue(String fieldName, Integer value) {
		this.record.setValue(fieldName, value);
	}
	public Integer getInteger(String fieldName) {
		return this.record.getInteger(fieldName);
	}
	
}
