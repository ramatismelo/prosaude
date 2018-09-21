package com.evolucao.rmlibrary.ui.controller.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.ui.controller.ControlForm;

public class AfterSaveButtonClickEvent extends EventObject {
	ControlForm controlForm = null;

	public void setControlForm(ControlForm rmForm) {
		this.controlForm = rmForm;
	}
	public ControlForm getControlForm() {
		return this.controlForm;
	}
	
	public AfterSaveButtonClickEvent(Object source) {
		super(source);
	}
	
	public interface AfterSaveButtonClickEventListener extends EventListener {
		public void AfterSaveButtonClick(AfterSaveButtonClickEvent event);
	}
}
