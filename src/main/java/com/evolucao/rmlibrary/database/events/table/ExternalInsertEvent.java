package com.evolucao.rmlibrary.database.events.table;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.Table;

public class ExternalInsertEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	public Table sourceTable = null;
	public Table targetTable = null;
	
	private boolean cancel = false;
	
	public void Cancel() {
		this.cancel = true;
	}
	public boolean isCanceled() {
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
	
	public ExternalInsertEvent(Object source) {
		super(source);
	}
	
	public interface ExternalInsertEventListener extends EventListener {
		public void onExternalInsert(ExternalInsertEvent event);
	}
}
