package com.evolucao.rmlibrary.database.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.Table;

public class ValidateFieldEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private boolean valid = true;
	String validationAdvice = null;
	Field field = null;
	Table table = null;
	
	public void setField(Field field) {
		this.field = field;
	}
	public Field getField() {
		return this.field;
	}
	
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

	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Boolean isValid() {
		return this.valid;
	}
	
	public ValidateFieldEvent(Object source) {
		super(source);
	}

	public interface ValidateFieldEventListener extends EventListener {
		public void onValidate(ValidateFieldEvent event);
	}
}
