package com.evolucao.rmlibrary.ui.production;

import java.util.Date;

import com.vaadin.ui.Button;
import com.evolucao.rmlibrary.database.Record;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.ui.controller.ControlButton;
import com.evolucao.rmlibrary.ui.controller.enumerators.ButtonType;

public class RmButton extends Button {
	ControlButton controlButton = null;
	ButtonType buttonType = ButtonType.NORMALBUTTON;
	private SimpleRecord simpleRecord = new SimpleRecord();
	
	public void setButtonType(ButtonType buttonType) {
		this.buttonType = buttonType;
		removeStyleName("normal-button-system");
		removeStyleName("link-button-system");
		removeStyleName("button-blank");
		switch (this.buttonType) {
		case NORMALBUTTON:
			addStyleName("normal-button-system");
			break;
		case BLANKBUTTON:
			addStyleName("button-blank");
			break;
		case LINKBUTTON:
			addStyleName("link-button-system");
			break;
		}
	}
	public ButtonType getButtonType() {
		return this.buttonType;
	}
	
	public void setControlButton(ControlButton controlButton) {
		this.controlButton = controlButton;
	}
	public ControlButton getControlButton() {
		return this.controlButton;
	}
	
	public void setRecord(SimpleRecord simpleRecord) {
		this.simpleRecord = simpleRecord;
	}
	public SimpleRecord getRecord() {
		return this.simpleRecord;
	}
	
	public RmButton() {
		super();
		addStyleName("normal-button-system");
	}
	
	public RmButton(String caption) {
		super(caption);
		addStyleName("normal-button-system");
	}
	
	public void setValue(String fieldName, String value) {
		this.simpleRecord.setValue(fieldName, value);	
	}
	
	public String getString(String fieldName) {
		return this.simpleRecord.getString(fieldName);
	}
	
	public void setValue(String fieldName, Double value) {
		this.simpleRecord.setValue(fieldName, value);
	}
	
	public Double getDouble(String fieldName) {
		return this.simpleRecord.getDouble(fieldName);
	}
	
	public void setValue(String fieldName, Boolean value) {
		this.simpleRecord.setValue(fieldName, value);
	}
	
	public Boolean getBoolean(String fieldName) {
		return this.simpleRecord.getBoolean(fieldName);
	}
	
	public void setValue(String fieldName, Date value) {
		this.simpleRecord.setValue(fieldName, value);
	}
	public Date getDate(String fieldName) {
		return this.simpleRecord.getDate(fieldName);
	}
	
	public void setValue(String fieldName, Integer value) {
		this.simpleRecord.setValue(fieldName, value);
	}
	public Integer getInteger(String fieldName) {
		return this.simpleRecord.getInteger(fieldName);
	}
	
}
