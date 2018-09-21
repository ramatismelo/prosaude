package com.evolucao.rmlibrary.ui;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class LabelTitle extends CssLayout {
	String title = null;
	LabelTitleType labelTitleType = LabelTitleType.h1;
	
	public void setLabelTitleType(LabelTitleType labelTitleType) {
		this.labelTitleType = labelTitleType;
	}
	public LabelTitleType getLabelTitleType() {
		return this.labelTitleType;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	
	public LabelTitle(String title) {
		this.title = title;
		this.updateContent();
	}
	
	public LabelTitle(String title, LabelTitleType labelTitleType) {
		this.title = title;
		this.labelTitleType = labelTitleType;
		this.updateContent();
	}
	
	public void updateContent() {
		this.components.clear();
		
		Label lblTitle = new Label(this.title);
		lblTitle.setWidthUndefined();
		
		if (this.getLabelTitleType()==LabelTitleType.h1) {
			lblTitle.addStyleName("label-title-h1");
		}
		else if (this.getLabelTitleType()==LabelTitleType.h2) {
			lblTitle.addStyleName("label-title-h1");
		}
		
		addComponent(lblTitle);
	}
}
