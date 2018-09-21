package com.evolucao.rmlibrary.database.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class AfterForeingSearchEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	Table sourceTable = null; // Tabela de onde esta se originando a checagem de existencia
	Table targetTable = null; // Tabela onde esta sendo executada a checagem de existencia
	
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
	
	public AfterForeingSearchEvent(Object source) {
		super(source);
	}
	
	public interface AfterForeingSearchEventListener extends EventListener {
		public void onAfterForeingSearch(AfterForeingSearchEvent event);
	}
}

