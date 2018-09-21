package com.evolucao.rmlibrary.ui.production;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class RmButtonSet extends CssLayout {
	Boolean showRequiredFieldMessage = false;
	Label lblRequerido = null;
	List<Button> leftButtonList = null;
	List<Button> rightButtonList = null;
	CssLayout leftButtonSet = null;
	CssLayout rightButtonSet = null;
	
	public void setShowRequiredFieldMessage(boolean showRequiredFieldMessage) {
		this.showRequiredFieldMessage = showRequiredFieldMessage;
		this.updateContent();
	}
	public boolean getShowRequiredFieldMessage() {
		return this.showRequiredFieldMessage;
	}
	
	public RmButtonSet() {
		this.addStyleName("rmbuttonset");
		this.addStyleName("flex-direction-col");
		this.updateContent();
	}
	
	public void updateContent() {
		this.removeAllComponents();
		
		if (showRequiredFieldMessage) {
			this.lblRequerido = new Label("*Campos Obrigatórios");
			this.lblRequerido.addStyleName("lbl-required");
			this.addComponent(lblRequerido);
		}

	    CssLayout buttonSet = new CssLayout();
	    buttonSet.addStyleName("flex-direction-row");
	    this.addComponent(buttonSet);
	    {
	    	if ((this.leftButtonList!=null) || (this.rightButtonList!=null)) {
				this.preparaLeftButtonSet();
				buttonSet.addComponent(this.leftButtonSet);
				if (this.leftButtonList!=null) {
					for (Button button: this.leftButtonList) {
						this.leftButtonSet.addComponent(button);
					}
				}
	    	}
			
			if (this.rightButtonList!=null) {
				this.preparaRigthButtonSet();
				buttonSet.addComponent(this.rightButtonSet);
				for (Button button: this.rightButtonList) {
					this.rightButtonSet.addComponent(button);
				}
			}
	    }
	}
	
	public void addLeftButton(Button button) {
		if (this.leftButtonList==null) {
			this.leftButtonList = new ArrayList<Button>();
		}
		this.leftButtonList.add(button);
		this.updateContent();
	}
	
	public void addRigthButton(Button button) {
		if (this.rightButtonList==null) {
			this.rightButtonList = new ArrayList<Button>();
		}
		this.rightButtonList.add(button);
		this.updateContent();
	}
	
	public CssLayout preparaLeftButtonSet() {
		if (this.leftButtonSet==null) {
			this.leftButtonSet = new CssLayout();
			this.leftButtonSet.addStyleName("flex-grow-1");
			this.leftButtonSet.addStyleName("flex-direction-row");
			this.addComponent(this.leftButtonSet);
		}
		else {
			this.leftButtonSet.removeAllComponents();
		}
		
		return this.leftButtonSet;
	}
	
	public CssLayout preparaRigthButtonSet() {
		if (this.leftButtonSet==null) {
			this.leftButtonSet = new CssLayout();
			this.leftButtonSet.addStyleName("flex-grow-1");
			this.leftButtonSet.addStyleName("flex-direction-row");
			this.addComponent(this.leftButtonSet);
		}
		
		if (this.rightButtonSet==null) {
			this.rightButtonSet = new CssLayout();
			this.rightButtonSet.addStyleName("flex-direction-row");
			this.addComponent(this.rightButtonSet);
		}
		else {
			this.rightButtonSet.removeAllComponents();
		}
		
		return this.rightButtonSet;
	}
}
