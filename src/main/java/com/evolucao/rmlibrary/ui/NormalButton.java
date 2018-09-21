package com.evolucao.rmlibrary.ui;

import com.vaadin.ui.Button;

public class NormalButton extends Button {
	public NormalButton() {
		addStyleName("normal-button-system");
	}
	
	public NormalButton(String caption) {
		super(caption);
		addStyleName("normal-button-system");
	}
}
