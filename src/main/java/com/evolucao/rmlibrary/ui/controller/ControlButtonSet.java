package com.evolucao.rmlibrary.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.evolucao.rmlibrary.ui.NormalButton;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent;
import com.evolucao.rmlibrary.ui.production.RmButton;
import com.evolucao.rmlibrary.ui.production.RmButtonSet;
import com.vaadin.ui.CssLayout;

public class ControlButtonSet extends ControlBase {
	List<ControlButton> buttonSetButtonLeft = new ArrayList<ControlButton>();
	List<ControlButton> buttonSetButtonRight = new ArrayList<ControlButton>();
	
	public void setButtonSetLeft(List<ControlButton> buttonSetButtonLeft) {
		this.buttonSetButtonLeft = buttonSetButtonLeft;
	}
	public List<ControlButton> getButtonSetButtonLeft() {
		return this.buttonSetButtonLeft;
	}
	
	public void setButtonSetRight(List<ControlButton> buttonSetButtonRigth) {
		this.buttonSetButtonRight = buttonSetButtonRight;
	}
	public List<ControlButton> getButtonSetButtonRigth() {
		return this.buttonSetButtonRight;
	}
	
	public void addLeftButton(ControlButton controlButton) {
		this.getButtonSetButtonLeft().add(controlButton);
		this.addComponent(controlButton);
	}
	public void addRightButton(ControlButton controlButton) {
		this.getButtonSetButtonRigth().add(controlButton);
		this.addComponent(controlButton);
	}
	
	public ControlButton addLeftButton(String caption) {
		ControlButton button = new ControlButton();
		this.getButtonSetButtonLeft().add(button);
		this.addComponent(button);
		return button;
	}
	public ControlButton addRightButton(String caption) {
		ControlButton button = new ControlButton();
		this.getButtonSetButtonRigth().add(button);
		this.addComponent(button);
		return button;
	}

	@Override
	public CssLayout deploy() {
		RmButtonSet rmButtonSet = new RmButtonSet();
		for (ControlButton controlButton: this.getButtonSetButtonLeft()) {
			rmButtonSet.addLeftButton(deployButton(controlButton));
		}
		for (ControlButton controlButton: this.getButtonSetButtonRigth()) {
			rmButtonSet.addRigthButton(deployButton(controlButton));
		}
		return rmButtonSet;
	}
	
	public RmButton deployButton(ControlButton controlButton) {
		RmButton rmButton = new RmButton(controlButton.caption);
		rmButton.setButtonType(controlButton.getButtonType());
		rmButton.setControlButton(controlButton);
		if (controlButton.getClickShortcut()!=null) {
			rmButton.setClickShortcut(controlButton.getClickShortcut());
		}
		rmButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// Pega o RmButton a partir do button
				RmButton rmButton = (RmButton) event.getButton();
				
				// Cria o evento 
				ControlButtonClickEvent event2 = new ControlButtonClickEvent(rmButton.getControlButton());

				// Pega o formulario onde o botao esta atuando e passa para o parametro
				ControlButton controlButton = rmButton.getControlButton();
				ControlForm rmForm = (ControlForm) controlButton.getParent(ControlForm.class);
				event2.setControlForm(rmForm);
				
				rmButton.getControlButton().fireButtonClickEvent(event2);
			}
		});
		return rmButton;
	}
}
