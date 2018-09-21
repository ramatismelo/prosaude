package com.evolucao.rmlibrary.database.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class BeforeForeingSearchEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	Table sourceTable = null; // Tabela de onde esta se originando a checagem de existencia
	Table targetTable = null; // Tabela onde esta sendo executada a checagem de existencia
	Boolean cancel = false;
	String errorMessage = null;
	
    public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    }
    public String getErrorMessage() {
    	return this.errorMessage;
    }
	
	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}
	public boolean getCancel() {
		return this.cancel;
	}
	
	public void setSourceTable(Table sourceTable) {
		this.sourceTable = sourceTable;
	}
	public Table getSourceTable() {
		return this.sourceTable;
	}
	
	public void setTargetTable(Table targetTable) {
		this.targetTable = targetTable;
	}
	public Table getTargetTable() {
		return this.targetTable;
	}

	public BeforeForeingSearchEvent(Object source) {
		super(source);
	}
	
	public interface BeforeForeingSearchEventListener extends EventListener {
		public void onBeforeForeingSearch(BeforeForeingSearchEvent event);
	}
}

