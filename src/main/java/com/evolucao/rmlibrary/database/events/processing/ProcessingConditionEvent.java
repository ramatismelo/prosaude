package com.evolucao.rmlibrary.database.events.processing;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.database.events.table.BeforePostEvent;

/*
 * Usado para determinar se um processamento pode ou nao ser executado
 */
public class ProcessingConditionEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private boolean process = true;

	public void setProcess(boolean process) {
		this.process = process;
	}
	public boolean getProcess() {
		return this.process;
	}
	
	public ProcessingConditionEvent(Object source) {
		super(source);
	}

	public interface ProcessingConditionEventListener extends EventListener {
		public void onProcessingCondition(ProcessingConditionEvent event);
	}
}
