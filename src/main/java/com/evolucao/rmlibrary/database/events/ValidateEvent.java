package com.evolucao.rmlibrary.database.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class ValidateEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private boolean valid = true;
	private String value = null;
	String validationAdvice = null;
	Table table = null;
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public void setValidationAdvice(String validationAdvice) {
		this.validationAdvice = validationAdvice;
	}
	public String getValidationAdvice() {
		return this.validationAdvice;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
	
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Boolean isValid() {
		return this.valid;
	}
	
	public ValidateEvent(Object source) {
		super(source);
	}

	public interface ValidateEventListener extends EventListener {
		public void onValidate(ValidateEvent event);
	}
}
