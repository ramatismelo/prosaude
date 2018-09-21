package com.evolucao.rmlibrary.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.CssLayout;

public abstract class ControlBase {
	List<ControlBase> componentList = new ArrayList<ControlBase>();
	ControlBase owner = null;
	Double width = null;
	Double height = null;
	Double minimumWidth = null;
	Boolean firstComponentInContainer = false;
	
	public void setFirstComponentInContainer(Boolean firstComponentInContainer) {
		this.firstComponentInContainer = firstComponentInContainer;
	}
	public Boolean getFirstComponentInContainer() {
		return this.firstComponentInContainer;
	}

	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getWidth() {
		return this.width;
	}
	
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getHeight() {
		return this.height;
	}
	
	public void setMinimumWidth(Double minimumWidth) {
		this.minimumWidth = minimumWidth;
	}
	public Double getMinimumWidth() {
		return this.minimumWidth;
	}

	public void setOwner(ControlBase owner) {
		this.owner = owner;
	}
	public ControlBase getOwner() {
		return this.owner;
	}
	
	public void setComponentList(List<ControlBase> componentList) {
		this.componentList = componentList;
	}
	public List<ControlBase> getComponentList() {
		return this.componentList;
	}
	
	public ControlBase() {
		
	}
	
	public void addComponent(ControlBase component) {
		component.setOwner(this);
		this.getComponentList().add(component);
	}
	
	public abstract CssLayout deploy();
	
	public ControlBase getForm() {
		ControlBase retorno = null;
		
		if (this instanceof ControlForm) {
			retorno = this;
		}
		else {
			if (this.getOwner()!=null) {
				retorno = this.getOwner().getForm();
			}
		}

		if (!(retorno instanceof ControlForm)) {
			retorno = null;
		}
		return retorno;
	}
	
	public ControlBase getParent(Class <? extends ControlBase> parentClass) {
		ControlBase retorno = null;
		
		if (this.getClass() == parentClass) {
			retorno = this;
		}
		else {
			if (this.getOwner()!=null) {
				retorno = this.getOwner().getParent(parentClass);
			}
		}

		if (!(retorno.getClass() == parentClass)) {
			retorno = null;
		}
		return retorno;
	}
	
	public List<ControlBase> getChildrensByClass(Class <?> classComponent) {
		List<ControlBase> retorno = new ArrayList<ControlBase>();
		
		for (ControlBase controlBase: this.getComponentList()) {
			// Caso o atual componente seja da classe que se deseja, inclui ele no retorno
			if (controlBase.getClass()==classComponent) {
				retorno.add(controlBase);
			}
			
			// Verifica os seus subcomponentes
			retorno.addAll(controlBase.getChildrensByClass(classComponent));
		}
		return retorno;
	}
}
