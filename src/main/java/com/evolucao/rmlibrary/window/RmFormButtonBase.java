package com.evolucao.rmlibrary.window;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.vaadin.server.Resource;
import com.evolucao.rmlibrary.window.enumerators.RmFormButtonType;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;

public class RmFormButtonBase {
	String caption = null;
	Resource resource = null;
	List<String> styleNames = new ArrayList<String>();
	Boolean closeWindow = true; 
	RmFormButtonType rmFormButtonType = RmFormButtonType.UNDEFINED;
	protected EventListenerList listenerList = new EventListenerList();
	
	public void setRmFormButtonType(RmFormButtonType rmFormButtonType) {
		this.rmFormButtonType = rmFormButtonType; 
	}
	public RmFormButtonType getRmFormButtonType() {
		return this.rmFormButtonType;
	}
	
	public void setCloseWindow(Boolean closeWindow) {
		this.closeWindow = closeWindow;
	}
	public Boolean getCloseWindow() {
		return this.closeWindow;
	}
	
	public void setStyleNames(List<String> styleNames) {
		this.styleNames = styleNames;
	}
	public List<String> getStyleNames() {
		return this.styleNames;
	}
	
	public void setIcon(Resource resource) {
		this.resource = resource;
	}
	public Resource getIcon() {
		return this.resource;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getCaption() {
		return this.caption;
	}
	
	public RmFormButtonBase() {
		
	}
	
	public RmFormButtonBase(String caption) {
		this.caption = caption;
	}
	
	public void addStyleName(String styleName) {
		this.getStyleNames().add(styleName);
	}

	/*
	 * EVENTOS 
	 */
	
	public void addRmFormButtonClickEventListener(RmFormButtonClickEventListener listener) {
		listenerList.add(RmFormButtonClickEventListener.class, listener);
	}

	public void removeRmFormButtonClickEventListener(RmFormButtonClickEventListener listener) {
		listenerList.remove(RmFormButtonClickEventListener.class, listener);
	}

	void fireRmFormButtonClickEvent(RmFormButtonClickEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == RmFormButtonClickEventListener.class) {
				((RmFormButtonClickEventListener) listeners[i + 1]).onRmFormButtonClick(event);
			}
		}
	}
}
