package com.evolucao.rmlibrary.database;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.events.AfterForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.AfterForeingSearchEvent.AfterForeingSearchEventListener;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent.BeforeForeingSearchEventListener;
import com.evolucao.rmlibrary.ui.controller.ControlContentField;
import com.evolucao.rmlibrary.ui.production.RmGrid;

public class ForeingSearch {
	String targetTableName = null;
	String targetIndexName = null;
	String relationship = null;
	String returnFieldName = null;
	Integer limit = null;
	String order = null;
	Boolean filterReturnField = null;
	
	Boolean allowInsert = true;
	Boolean allowUpdate = true;
	Boolean allowDelete = true;
	Boolean allowFilter = true;
	Boolean allowPrint = true;
	
	String width = "600px";
	String height = "400px";
	String targetRmGridName = null;
	String title = null;
	
	Boolean autoOpenFilterForm = true;
	
	protected EventListenerList listenerList = new EventListenerList();

	public void setAutoOpenFilterForm(Boolean autoOpenFilterForm) {
		this.autoOpenFilterForm = autoOpenFilterForm;
	}
	public Boolean getAutoOpenFilterForm() {
		return this.autoOpenFilterForm;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	
	public void setTargetRmGridName(String targetRmGridName) {
		this.targetRmGridName = targetRmGridName;
	}
	public String getTargetRmGridName() {
		return this.targetRmGridName;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
	public String getWidth() {
		return this.width;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	public String getHeight() {
		return this.height;
	}
	
	public void setAllowInsert(Boolean allowInsert) {
		this.allowInsert = allowInsert;
	}
	public Boolean getAllowInsert() {
		return this.allowInsert;
	}
	
	public void setAllowUpdate(Boolean allowUpdate) {
		this.allowUpdate = allowUpdate;
	}
	public Boolean getAllowUpdate() {
		return this.allowUpdate;
	}
	
	public void setAllowDelete(Boolean allowDelete) {
		this.allowDelete = allowDelete;
	}
	public Boolean getAllowDelete() {
		return this.allowDelete;
	}
	
	public void setAllowFilter(Boolean allowFilter) {
		this.allowFilter = allowFilter;
	}
	public Boolean getAllowFilter() {
		return this.allowFilter;
	}
	
	public void setAllowPrint(Boolean allowPrint) {
		this.allowPrint = allowPrint;
	}
	public Boolean getAllowPrint() {
		return this.allowPrint;
	}
	
	public void setReturnFieldName(String returnFieldName) {
		this.returnFieldName = returnFieldName;
	}
	public String getReturnFieldName() {
		return this.returnFieldName;
	}
	
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getLimit() {
		return this.limit;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrder() {
		return this.order;
	}
	
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	public String getTargetTableName() {
		return this.targetTableName;
	}
	
	public void setTargetIndexName(String targetIndexName) {
		this.targetIndexName = targetIndexName;
	}
	public String getTargetIndexName() {
		return this.targetIndexName;
	}
	
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getRelationship() {
		return this.relationship;
	}
	

	public ForeingSearch() {
		
	}
	
	/*
	 * EVENTOS 
	 */
	public void addBeforeForeingSearchEventListener(BeforeForeingSearchEventListener listener) {
		listenerList.add(BeforeForeingSearchEventListener.class, listener);
	}

	public void removeBeforeForeingSearchEventListener(BeforeForeingSearchEventListener listener) {
		listenerList.remove(BeforeForeingSearchEventListener.class, listener);
	}

	void fireBeforeForeingSearchEvent(BeforeForeingSearchEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == BeforeForeingSearchEventListener.class) {
				((BeforeForeingSearchEventListener) listeners[i + 1]).onBeforeForeingSearch(event);
			}
		}
	}

	public void addAfterForeingSearchEventListener(AfterForeingSearchEventListener listener) {
		listenerList.add(AfterForeingSearchEventListener.class, listener);
	}

	public void removeAfterForeingSearchEventListener(AfterForeingSearchEventListener listener) {
		listenerList.remove(AfterForeingSearchEventListener.class, listener);
	}

	void fireAfterForeingSearchEvent(AfterForeingSearchEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterForeingSearchEventListener.class) {
				((AfterForeingSearchEventListener) listeners[i + 1]).onAfterForeingSearch(event);
			}
		}
	}
}
