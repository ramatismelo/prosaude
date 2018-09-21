package com.evolucao.rmlibrary.ui.model;

import com.vaadin.ui.CheckBox;

public class OptionModel {
	String codigoOption = null;
	String grau = null;
	String textOption = null;
	OptionType optionType = null;
	Boolean access = null;
	Boolean insert = null;
	Boolean update = null;
	Boolean delete = null;
	
	CheckBox chkAccess = null;
	CheckBox chkInsert = null;
	CheckBox chkUpdate = null;
	CheckBox chkDelete = null;
	
	public void setChkAccess(CheckBox chkAccess) {
		this.chkAccess = chkAccess;
	}
	public CheckBox getChkAccess() {
		return this.chkAccess;
	}
	
	public void setChkInsert(CheckBox chkInsert) {
		this.chkInsert = chkInsert;
	}
	public CheckBox getChkInsert() {
		return this.chkInsert;
	}
	
	public void setChkUpdate(CheckBox chkUpdate) {
		this.chkUpdate = chkUpdate;
	}
	public CheckBox getChkUpdate() {
		return this.chkUpdate;
	}
	
	public void setChckDelete(CheckBox chkDelete) {
		this.chkDelete = chkDelete;
	}
	public CheckBox getChkDelete() {
		return this.chkDelete;
	}
	
	public void setUpdate(boolean update) {
		this.update = update;
	}
	public boolean getUpdate() {
		return this.update;
	}
	
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public boolean getDelete() {
		return this.delete;
	}
	
	public void setInsert(boolean insert) {
		this.insert = insert;
	}
	public boolean getInsert() {
		return this.insert;
	}
	
	public void setAccess(boolean access) {
		this.access = access;
		
		if (this.getChkInsert()!=null) {
			this.getChkInsert().setValue(this.getAccess());
			if (!this.getAccess()) {
				this.getChkInsert().setEnabled(false);
			}
			else {
				this.getChkInsert().setEnabled(true);
			}
		}
		
		if (this.getChkUpdate()!=null) {
			this.getChkUpdate().setValue(this.getAccess());
			if (!this.getAccess()) {
				this.getChkUpdate().setEnabled(false);
			}
			else {
				this.getChkUpdate().setEnabled(true);
			}
		}
		
		if (this.getChkDelete()!=null) {
			this.getChkDelete().setValue(this.getAccess());
			if (!this.getAccess()) {
				this.getChkDelete().setEnabled(false);
			}
			else {
				this.getChkDelete().setEnabled(true);
			}
		}
	}
	public boolean getAccess() {
		return this.access;
	}
	
	public void setOptionType(OptionType optionType) {
		this.optionType = optionType;
	}
	public OptionType getOptionType() {
		return this.optionType;
	}
	
	public void setCodigoOption(String codigoOption) {
		this.codigoOption = codigoOption;
	}
	public String getCodigoOption() {
		return this.codigoOption;
	}
	
	public void setGrau(String grau) {
		this.grau = grau;
	}
	public String getGrau() {
		return this.grau;
	}
	
	public void setText(String text) {
	}
	
	public OptionModel() {
		
	}
}
