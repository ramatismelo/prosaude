package com.evolucao.rmlibrary.ui.controller;

import com.vaadin.ui.CssLayout;

public class ControlHorizontalContainer extends ControlBase {
	public boolean autoExpandRatio = false;
	
	public void setAutoExpandRatio(boolean autoExpandRatio) {
		this.autoExpandRatio = autoExpandRatio;
	}
	public boolean getAutoExpandRatio() {
		return this.autoExpandRatio;
	}
	
	public ControlHorizontalContainer() {
		
	}
	
	public ControlHorizontalContainer(boolean autoExpandRatio) {
		this.autoExpandRatio = autoExpandRatio;
	}

	@Override
	public CssLayout deploy() {
		CssLayout horizontalLayout = new CssLayout();
		horizontalLayout.addStyleName("flex-direction-row");
		for (ControlBase containerBase: this.getComponentList()) {
			horizontalLayout.addComponent(containerBase.deploy());
		}
		
		return horizontalLayout;
	}
}
