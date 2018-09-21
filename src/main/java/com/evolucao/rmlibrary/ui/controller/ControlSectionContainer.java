package com.evolucao.rmlibrary.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmSectionContainer;

public class ControlSectionContainer extends ControlBase {
	String name = null;
	String title = null;
	Boolean allowMinimize = true;
	Boolean allowMaximaze = true;
	SectionState sectionState = SectionState.MAXIMIZED;
	List<Field> fields = new ArrayList<Field>();
	RmSectionContainer rmSectionContainer = null;
	
	public void setRmSectionContainer(RmSectionContainer rmSectionContainer) {
		this.rmSectionContainer = rmSectionContainer;
	}
	public RmSectionContainer getRmSectionContainer() {
		return this.rmSectionContainer;
	}
	
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public List<Field> getFields() {
		return this.fields;
	}
	
	public void setSectionState(SectionState sectionState) {
		this.sectionState = sectionState;
	}
	public SectionState getSectionState() { 
		return this.sectionState;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	public void setAllowMinimize(Boolean allowMinimize) {
		this.allowMinimize = allowMinimize;
	}
	public Boolean getAllowMinimize() {
		return this.allowMinimize;
	}
	
	public void setAllowMaximaze(Boolean allowMaximaze) {
		this.allowMaximaze = allowMaximaze;
	}
	public Boolean getAllowMaximaze() {
		return this.allowMaximaze;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	
	public ControlSectionContainer() {
		
	}
	
	@Override
	public CssLayout deploy() {
		RmSectionContainer rmSectionContainer = new RmSectionContainer();
		rmSectionContainer.setTitle(this.getTitle());
		rmSectionContainer.setName(this.getName());
		rmSectionContainer.setSectionState(this.getSectionState());
		if (this.getHeight()!=null) {
			rmSectionContainer.setHeight(this.getHeight() + "px");
		}
		this.setRmSectionContainer(rmSectionContainer);
		
		for (ControlBase containerBase: this.getComponentList()) {
			rmSectionContainer.getBody().addComponent(containerBase.deploy());
		}
		
		return rmSectionContainer;
	}
	
	public void maximize() {
		if ((this.getAllowMaximaze()) && (this.getSectionState()!=SectionState.MAXIMIZED)) {
			this.getRmSectionContainer().maximize();
		}
	}
	
	public void minimize() {
		if ((this.getAllowMinimize()) && (this.getSectionState()!=SectionState.MINIMIZED)) {
			this.getRmSectionContainer().minimize();
		}
	}
	
	public void addField(Field field) {
		this.getFields().add(field);
	}
	
	public boolean validate() {
		Boolean retorno = true;
		for (Field field : this.getFields()) {
			if (!field.validate()) {
				retorno = false;
			}
		}
		return retorno;
	}
}
