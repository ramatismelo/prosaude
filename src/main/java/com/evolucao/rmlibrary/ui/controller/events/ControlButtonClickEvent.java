package com.evolucao.rmlibrary.ui.controller.events;

import java.util.EventListener;
import java.util.EventObject;

import com.evolucao.rmlibrary.ui.controller.ControlForm;

public class ControlButtonClickEvent extends EventObject {
	ControlForm controlForm = null;

	public void setControlForm(ControlForm rmForm) {
		this.controlForm = rmForm;
	}
	public ControlForm getRmForm() {
		return this.controlForm;
	}
	
	public ControlButtonClickEvent(Object source) {
		super(source);
	}
	
	public interface ControlButtonClickEventListener extends EventListener {
		public void onControlButtonClick(ControlButtonClickEvent event);
	}
}
