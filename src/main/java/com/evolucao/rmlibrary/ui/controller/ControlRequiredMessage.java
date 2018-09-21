package com.evolucao.rmlibrary.ui.controller;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class ControlRequiredMessage extends ControlBase {
	public ControlRequiredMessage() {
		
	}
	
	public CssLayout deploy() {
		CssLayout div = new CssLayout();
		div.addStyleName("lbl-required");
		Label lbl = new Label("*Campos Obrigatórios");
		div.addComponent(lbl);
		return div;
	}
}
