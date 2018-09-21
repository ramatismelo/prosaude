package com.evolucao.rmlibrary.window.event;

import java.util.EventListener;
import java.util.EventObject;

import com.vaadin.ui.Window;
import com.evolucao.rmlibrary.database.events.AfterForeingSearchEvent;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.window.RmFormWindow;

public class RmFormButtonClickEvent extends EventObject {
	Window window = null;
	ControlForm controlForm = null;
	RmFormWindow rmFormWindow = null;
	
	
	public void setRmFormWindow(RmFormWindow rmFormWindow) {
		this.rmFormWindow = rmFormWindow;
	}
	public RmFormWindow getRmFormWindow() {
		return this.rmFormWindow;
	}
	
	public void setControlForm(ControlForm controlForm) {
		this.controlForm = controlForm;
	}
	public ControlForm getControlForm() {
		return this.controlForm;
	}
	
	public void setWindow(Window window) {
		this.window = window;
	}
	public Window getWindow() {
		return this.window;
	}
	
	public RmFormButtonClickEvent(Object source) {
		super(source);
	}
	
	public interface RmFormButtonClickEventListener extends EventListener {
		public void onRmFormButtonClick(RmFormButtonClickEvent event);
	}
}
