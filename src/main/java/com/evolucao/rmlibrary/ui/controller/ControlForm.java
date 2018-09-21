package com.evolucao.rmlibrary.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.controller.events.AfterSaveButtonClickEvent;
import com.evolucao.rmlibrary.ui.controller.events.AfterSaveButtonClickEvent.AfterSaveButtonClickEventListener;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.WarningMessage;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.rmlibrary.window.enumerators.RmFormButtonType;
import com.vaadin.ui.CssLayout;

public class ControlForm extends ControlBase {
	Table table = null;
	ControlBase defaultContainer = null;
	ControlSectionContainer defaultSection = null;
	ControlButtonSet defaultControlButtonSet = null;
	String title = "";
	RmFormWindow window = null;
	WarningMessage warningMessage = null;
	List<ControlSectionContainer> sectionList = new ArrayList<ControlSectionContainer>();
	List<RmFormButtonBase> rmFormButtonList = new ArrayList<RmFormButtonBase>();
	
	protected EventListenerList listenerList = new EventListenerList();
	
	public void setRmFormButtonList(List<RmFormButtonBase> rmFormButtonList) {
		this.rmFormButtonList = rmFormButtonList;
	}
	public List<RmFormButtonBase> getRmFormButtonList() {
		return this.rmFormButtonList;
	}
	
	public void setWarningMessage(WarningMessage warningMessage) {
		this.warningMessage = warningMessage;
	}
	public WarningMessage getWarningMessage() {
		return this.warningMessage;
	}
	
	public void setWindow(RmFormWindow window) {
		this.window = window;
	}
	public RmFormWindow getWindow() {
		return this.window;
	}
	
	public void setDefaultControlButtonSet(ControlButtonSet defaultControlButtonSet) {
		this.defaultControlButtonSet = defaultControlButtonSet;
	}
	public ControlButtonSet getDefaultControlButtonSet() {
		return this.defaultControlButtonSet;
	}
	
	public void setSectionList(List<ControlSectionContainer> sectionList) {
		this.sectionList = sectionList;
	}
	public List<ControlSectionContainer> getSectionList() {
		return this.sectionList;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public void setDefaultContainer(ControlBase defaultContainer) {
		this.defaultContainer = defaultContainer;
	}
	public ControlBase getDefaultContainer() {
		return this.defaultContainer;
	}
	
	public void setDefaultSection(ControlSectionContainer defaultSection) {
		this.defaultSection = defaultSection;
	}
	public ControlSectionContainer getDefaultSection() {
		return this.defaultSection;
	}
	
	public ControlForm() {
		this.getComponentList().clear();
		this.setWidth(600d);
		this.setHeight(200d);
	}
	
	public void addOnDefaultContainer(ControlBase component) {
		if (this.getDefaultSection()!=null) {
			this.getDefaultSection().addComponent(component);
		}
		else {
			this.defaultContainer.addComponent(component);
		}
	}
	
	public void addNewLine(boolean autoExpandRatio) {
		this.defaultContainer = new ControlHorizontalContainer(autoExpandRatio);
		// Caso exista um section, armazena a nova linha no section
		if (this.getDefaultSection()!=null) {
			this.getDefaultSection().addComponent(this.getDefaultContainer());
		}
		// Caso n�o exista um section, armazena no form
		else {
			this.addComponent(this.getDefaultContainer());
		}
	}

	public void addNewLine() {
		addNewLine(false);
	}
	
	public void addField(String fieldName, String fieldTitle, Double width, Double height, Integer expandRatio) {
		ControlContentField field = new ControlContentField();
		field.setFieldName(fieldName);
		field.setFieldTitle(fieldTitle);
		field.setWidth(width);
		field.setHeight(height);
		field.setExpandRatio(expandRatio);
		
		if (this.defaultContainer.getComponentList().size()==0) {
			field.setFirstComponentInContainer(true);
		}
		else {
			field.setFirstComponentInContainer(false);
		}
		
		this.defaultContainer.addComponent(field);
	}
	
	// Incluindo campo informando apenas a largura e altura
	public void addField(String fieldName, String fieldTitle, Double width, Double height) {
		addField(fieldName, fieldTitle, width, height, 0);
	}
	
	// Incluindo campo informando apenas a largura e o expandRatio
	public void addField(String fieldName, String fieldTitle, Double width, Integer expandRatio) {
		addField(fieldName, fieldTitle, width, null, expandRatio);
	}

	// Incluindo campo infornando apenas a largura
	public void addField(String fieldName, String fieldTitle, Double width) {
		addField(fieldName, fieldTitle, width, 0);
	}
	
	/**
	 * Adiciona um formulario com dados de uma tabela filha
	 * @param rmGridName
	 */
	public void addRmGrid(String rmGridName) {
		ControlGrid controlGrid = new ControlGrid(rmGridName);
		
		if (this.getDefaultSection()!=null) {
			this.getDefaultSection().addComponent(controlGrid);
		}
		else {
			this.defaultContainer.addComponent(controlGrid);
		}
	}

	public ControlSectionContainer addSection(String sectionName, String sectionTitle, SectionState sectionState) {
		ControlSectionContainer retorno = addSection(sectionName, sectionTitle, sectionState, null);
		return retorno;
	}
	
	public ControlSectionContainer addSection(String sectionName, String sectionTitle, SectionState sectionState, Double height) {
		ControlSectionContainer section = new ControlSectionContainer();
		section.setName(sectionName);
		section.setTitle(sectionTitle);
		section.setSectionState(sectionState);
		section.setHeight(height);
		this.defaultContainer.addComponent(section);
		this.defaultSection = section;
		
		return section;
	}
	
	public void addRequiredMessage() {
		ControlRequiredMessage message = new ControlRequiredMessage();
		this.defaultContainer.addComponent(message);
	}
	
	public ControlButtonSet addButtonSet() {
		ControlButtonSet controlButtonSet = new ControlButtonSet();
		this.setDefaultControlButtonSet(controlButtonSet);
		this.defaultContainer.addComponent(controlButtonSet);
		return controlButtonSet;
	}
	
	public ControlButton addLeftButton(String caption) {
		ControlButton controlButton = new ControlButton(caption);
		this.getDefaultControlButtonSet().addLeftButton(controlButton);
		return controlButton;
	}
	public ControlButton addRightButton(String caption) {
		ControlButton controlButton = new ControlButton(caption);
		this.getDefaultControlButtonSet().addRightButton(controlButton);
		return controlButton;
	}
	public ControlButton addButton(String caption) {
		ControlButton controlButton = null;
		
		if (this.getDefaultControlButtonSet()!=null) {
			controlButton = this.addRightButton(caption);
		}
		else {
			controlButton = new ControlButton(caption);
			
			if (this.defaultContainer.getComponentList().size()==0) {
				controlButton.setFirstComponentInContainer(true);
			}
			else {
				controlButton.setFirstComponentInContainer(false);
			}
			
			this.defaultContainer.addComponent(controlButton);
		}
		return controlButton; 
	}
	
	public RmFormButtonBase addRmFormButton(String caption) {
		RmFormButtonBase btnButton = new RmFormButtonBase();
		btnButton.setCaption(caption);
		btnButton.setRmFormButtonType(RmFormButtonType.UNDEFINED);
		this.getRmFormButtonList().add(btnButton);
		
		return btnButton;
	}
	
	public void exitSection() {
		this.setDefaultSection(null);
	}
	
	public ControlSectionContainer getSection(String sectionName) {
		ControlSectionContainer retorno = null;
		for (ControlSectionContainer section: this.getSectionList()) {
			if (section.getName().equalsIgnoreCase(sectionName)) {
				retorno = section;
				break;
			}
		}
		return retorno;
	}
	
	@Override
	public CssLayout deploy() {
		CssLayout div = new CssLayout();
		div.addStyleName("form-container");
		for (ControlBase containerBase: this.getComponentList()) {
			CssLayout container = containerBase.deploy();
			if (container!=null) {
				div.addComponent(container);
			}
		}
		return div;
	}
	
	/*
	 * EVENTOS 
	 */
	public void addAfterSaveButtonClickEventListener(AfterSaveButtonClickEventListener listener) {
		listenerList.add(AfterSaveButtonClickEventListener.class, listener);
	}

	public void removeAfterSaveButtonClickEventListener(AfterSaveButtonClickEventListener listener) {
		listenerList.remove(AfterSaveButtonClickEventListener.class, listener);
	}

	public void fireAfterSaveButtonClickEvent(AfterSaveButtonClickEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterSaveButtonClickEventListener.class) {
				((AfterSaveButtonClickEventListener) listeners[i + 1]).AfterSaveButtonClick(event);
			}
		}
	}
}
