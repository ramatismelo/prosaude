package com.evolucao.rmlibrary.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Resource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.evolucao.rmlibrary.ui.controller.enumerators.ButtonType;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent.ControlButtonClickEventListener;
import com.evolucao.rmlibrary.ui.production.RmButton;

public class ControlButton extends ControlBase {
	String caption = null;
	Resource resource = null;
	List<String> styleNames = new ArrayList<String>();
	ButtonType buttonType = ButtonType.NORMALBUTTON;
	Integer clickShortcut = null;
	
	protected EventListenerList listenerList = new EventListenerList();

	public void setClickShortcut(int clickShortcut) {
		this.clickShortcut = clickShortcut;
	}
	public Integer getClickShortcut() {
		return this.clickShortcut;
	}
	
	public void setButtonType(ButtonType buttonType) {
		this.buttonType = buttonType;
	}
	public ButtonType getButtonType() {
		return this.buttonType;
	}
	
	public void setStyleNames(List<String> styleNames) {
		this.styleNames = styleNames;
	}
	public List<String> getStyleNames() {
		return this.styleNames;
	}
	
	public void setIcon(Resource resource) {
		this.resource = resource;
	}
	public Resource getIcon() {
		return this.resource;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getCaption() {
		return this.caption;
	}
	
	public ControlButton() {
		
	}
	
	public ControlButton(String caption) {
		this.caption = caption;
	}
	
	public void addStyleName(String styleName) {
		this.getStyleNames().add(styleName);
	}

	/*
	 * EVENTOS 
	 */
	
	public void addControlButtonClickEventListener(ControlButtonClickEventListener listener) {
		listenerList.add(ControlButtonClickEventListener.class, listener);
	}

	public void removeControlButtonClickEventListener(ControlButtonClickEventListener listener) {
		listenerList.remove(ControlButtonClickEventListener.class, listener);
	}

	void fireButtonClickEvent(ControlButtonClickEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ControlButtonClickEventListener.class) {
				((ControlButtonClickEventListener) listeners[i + 1]).onControlButtonClick(event);
			}
		}
	}
	
	@Override
	public CssLayout deploy() {
		CssLayout buttonContainer = new CssLayout();
		
		// Caso o botao esteja em uma linha de campos, apresenta ele adequadamente
		if (this.getOwner() instanceof ControlHorizontalContainer) {
			ControlHorizontalContainer hc = (ControlHorizontalContainer) this.getOwner();
			buttonContainer.addStyleName("horizontal-button-container");

			if (this.getFirstComponentInContainer()) {
				buttonContainer.addStyleName("first-item");
			}
			else {
				buttonContainer.addStyleName("margin-left");
			}
		}
		
		RmButton rmButton = new RmButton(this.getCaption());
		rmButton.setButtonType(this.getButtonType());
		rmButton.setControlButton(this);
		if (this.getClickShortcut()!=null) {
			rmButton.setClickShortcut(this.getClickShortcut());
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
		buttonContainer.addComponent(rmButton);
		
		// Ajustando a altura do botao para ficar igual a altura dos campos
		if (this.getOwner() instanceof ControlHorizontalContainer) {
			rmButton.addStyleName("button-line");
		}
		
		return buttonContainer;
	}
}
