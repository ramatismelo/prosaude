package com.evolucao.rmlibrary.database.model;

import com.evolucao.rmlibrary.database.SimpleRecord;

public class ModelBase {
	Integer numberfields = 0;
	SimpleRecord simpleField = new SimpleRecord();
	String uid = null;

	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUid() {
		return this.uid;
	}

	public void setSimpleField(SimpleRecord simpleField) {
		this.simpleField = simpleField;
	}

	public SimpleRecord getSimpleField() {
		return this.simpleField;
	}

	public void setNumerFields(int numberFields) {
		this.numberfields = numberFields;
	}

	public Integer getNumberFields() {
		return this.numberfields;
	}

	public ModelBase() {

	}

	public void addNewFieldValue(String value) {
		this.simpleField.setValue(this.numberfields.toString(), value);
		this.numberfields++;
	}

	public void setField0(String value) {
		this.simpleField.setValue("0", value);
	}

	public String getField0() {
		return this.simpleField.getString("0");
	}
}
