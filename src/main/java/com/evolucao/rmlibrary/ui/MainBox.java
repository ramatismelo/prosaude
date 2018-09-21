package com.evolucao.rmlibrary.ui;

import com.vaadin.ui.CssLayout;
import com.evolucao.rmlibrary.database.enumerators.WarningMessageType;
import com.evolucao.rmlibrary.ui.production.WarningMessage;

public class MainBox extends CssLayout {
	String title = null;
	CssLayout body = new CssLayout();
	WarningMessage warningMessage = new WarningMessage();
	
	public void setWarningMessage(WarningMessage warningMessage) {
		this.warningMessage = warningMessage;
	}
	public WarningMessage getWarningMessage() {
		return this.warningMessage;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	
	public MainBox() {
		this.updateContent();
	}
	
	public MainBox(String title) {
		this.title = title;
		this.updateContent();
	}
	
	public void updateContent() {
		this.components.clear();

		this.body = new CssLayout();
		this.body.addStyleName("main-box");
		this.body.addStyleName("flex-direction-col");
		
		if (this.getTitle()!=null) {
			this.addComponent(new LabelTitle(this.getTitle(), LabelTitleType.h1));
		}
		
		this.addComponent(warningMessage);

		this.addComponent(body);
	}
	
	public CssLayout getBody() {
		return this.body;
	}
	
	public void showWarningMessage(String message, WarningMessageType warningMessageType) {
		this.warningMessage.setMessage(message);
		this.warningMessage.setWarningMessageType(warningMessageType);
		this.warningMessage.setVisible(true);
	}
	
	public void hideWarningMessage() {
		this.warningMessage.setVisible(false);
	}
}
