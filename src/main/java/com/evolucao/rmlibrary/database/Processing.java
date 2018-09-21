package com.evolucao.rmlibrary.database;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent.DirectProcessingEventListener;
import com.evolucao.rmlibrary.database.events.processing.ProcessingConditionEvent;
import com.evolucao.rmlibrary.database.events.processing.ProcessingConditionEvent.ProcessingConditionEventListener;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent.ReverseProcessingEventListener;
import com.evolucao.rmlibrary.database.events.table.BeforePostEvent;
import com.evolucao.rmlibrary.database.events.table.BeforePostEvent.BeforePostEventListener;

public class Processing {
	protected EventListenerList listenerList = new EventListenerList();
	
	public Processing() {
		
	}

	/*
	 * Adicionando evento ProcessingCondition que vai determinar se o processamento pode ou nao ser executado
	 * por padrao ele pode ser executado
	 */
	public void addProcessingConditionEventListener(ProcessingConditionEventListener listener) {
		listenerList.add(ProcessingConditionEventListener.class, listener);
	}

	public void removeProcessingConditionEventListener(ProcessingConditionEventListener listener) {
		listenerList.remove(ProcessingConditionEventListener.class, listener);
	}

	void firePressingConditionEvent(ProcessingConditionEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ProcessingConditionEventListener.class) {
				((ProcessingConditionEventListener) listeners[i + 1]).onProcessingCondition(event);
			}
		}
	}
	
	/*
	 * Adiciona o processamento direto que sera executado no post
	 */
	public void addDirectProcessingEventListener(DirectProcessingEventListener listener) {
		listenerList.add(DirectProcessingEventListener.class, listener);
	}

	public void removeDirectProcessingEventListener(DirectProcessingEventListener listener) {
		listenerList.remove(DirectProcessingEventListener.class, listener);
	}

	void fireDirectProcessingEvent(DirectProcessingEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == DirectProcessingEventListener.class) {
				((DirectProcessingEventListener) listeners[i + 1]).onDirectProcessing(event);
			}
		}
	}
	
	/*
	 * Adiciona o processamento inverso que sera executado no update e no delete 
	 */
	public void addReverseProcessingEventListener(ReverseProcessingEventListener listener) {
		listenerList.add(ReverseProcessingEventListener.class, listener);
	}

	public void removeReverseProcessingEventListener(ReverseProcessingEventListener listener) {
		listenerList.remove(ReverseProcessingEventListener.class, listener);
	}

	void fireReverseProcessingEvent(ReverseProcessingEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ReverseProcessingEventListener.class) {
				((ReverseProcessingEventListener) listeners[i + 1]).onReverseProcessing(event);
			}
		}
	}
}
