package com.evolucao.rmlibrary.ui.production;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.evolucao.rmlibrary.database.enumerators.WarningMessageType;

public class WarningMessage extends CssLayout {
	String message = null;
	boolean visible = false;
	Label lblMessage = new Label();
	WarningMessageType warningMessageType = WarningMessageType.SUCCESS;
	
	public void setWarningMessageType(WarningMessageType warningMessageType) {
		this.warningMessageType = warningMessageType;
		if (this.warningMessageType==WarningMessageType.SUCCESS) {
			this.addStyleName("warning-message-success");
			this.removeStyleName("warning-message-error");
		}
		else {
			this.addStyleName("warning-message-error");
			this.removeStyleName("warning-message-success");
		}
	}
	public WarningMessageType getWarningMessageType() {
		return this.warningMessageType;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		if (visible) {
			this.removeStyleName("hide-item");
		}
		else  {
			this.addStyleName("hide-item");
		}
			
	}
	public boolean getVisible() {
		return this.visible;
	}
	
	public void setMessage(String message) {
		this.message = message;
		this.lblMessage.setCaption(message);
	}
	public String getMessage() {
		return this.message;
	}
	
	public WarningMessage(String message) {
		addStyleName("warning-message");
		addStyleName("hide-item");
		
		this.message = message;
		this.lblMessage.setCaption(message);
		addComponent(lblMessage);
	}
	
	public WarningMessage() {
		addComponent(this.lblMessage);
		addStyleName("warning-message");
		addStyleName("warning-message-success");
		addStyleName("hide-item");
	}
	
	public void showWarningMessage(String message, WarningMessageType warningMessageType) {
		this.setMessage(message);
		this.setWarningMessageType(warningMessageType);
		this.setVisible(true);
	}
}
