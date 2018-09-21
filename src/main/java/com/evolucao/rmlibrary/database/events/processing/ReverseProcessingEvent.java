package com.evolucao.rmlibrary.database.events.processing;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.events.table.BeforePostEvent;

/*
 * Executa um processamento reverso apos um post
 */
public class ReverseProcessingEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	Table table = null;
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public ReverseProcessingEvent(Object source) {
		super(source);
	}

	public interface ReverseProcessingEventListener extends EventListener {
		public void onReverseProcessing(ReverseProcessingEvent event);
	}
}
