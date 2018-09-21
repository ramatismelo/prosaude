package com.evolucao.rmlibrary.window;

import com.vaadin.ui.Button;

public class RmFormButton extends Button {
	RmFormButtonBase rmFormButtonBase = null;
	
	public void setRmFormButtonBase(RmFormButtonBase rmFormButtonBase) {
		this.rmFormButtonBase = rmFormButtonBase;
	}
	public RmFormButtonBase getRmFormButtonBase() {
		return this.rmFormButtonBase;
	}
	
	public RmFormButton() {
		super();
	}
	
	public RmFormButton(String caption) {
		super(caption);
	}
}
