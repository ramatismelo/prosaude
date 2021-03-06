package com.evolucao.rmlibrary.database.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;

public class AfterValidateOnEditEvent  extends EventObject {
	private static final long serialVersionUID = 1L;
	
	Table table = null;
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public AfterValidateOnEditEvent(Object source) {
		super(source);
	}
	
	public interface AfterValidateOnEditEventListener extends EventListener {
		public void onAfterValidateOnEdit(AfterValidateOnEditEvent event);
	}
}
