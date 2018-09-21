package com.evolucao.rmlibrary.database.events.table;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class SpecialConditionOfDeleteEvent  extends EventObject {
	private static final long serialVersionUID = 1L;
	private Boolean valid = true;
	String errorMessage = null;
	Table table = null;
	
    public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    }
    public String getErrorMessage() {
    	return this.errorMessage;
    }
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public boolean getValid() {
		return this.valid;
	}
	public Boolean isValid() {
		return this.valid;
	}
	
	public SpecialConditionOfDeleteEvent(Object source) {
		super(source);
	}

	public interface SpecialConditionOfDeleteEventListener extends EventListener {
		public void onSpecialConditionOfDelete(SpecialConditionOfDeleteEvent event);
	}
}
