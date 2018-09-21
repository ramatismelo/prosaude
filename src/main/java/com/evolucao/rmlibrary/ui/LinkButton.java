package com.evolucao.rmlibrary.ui;

import com.vaadin.ui.Button;

public class LinkButton extends Button {
	public LinkButton() {
		addStyleName("link-button-system");
	}
	
	public LinkButton(String caption) {
		super(caption);
		addStyleName("link-button-system");
	}
}
