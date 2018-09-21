package com.example.myapplication.view;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TesteView extends CssLayout {
	public TesteView() {
		CssLayout div0 = new CssLayout();
		div0.addStyleName("div0-teste");
		this.addComponent(div0);
		{
			CssLayout div1 = new CssLayout();
			div1.addStyleName("div1-teste");
			div1.addStyleName("flex-direction-column");
			div0.addComponent(div1);
			{
				TextField edtCampo = new TextField("Campo para pesquisar");
				div1.addComponent(edtCampo);
				edtCampo.setValueChangeMode(ValueChangeMode.LAZY);
				edtCampo.addValueChangeListener(new ValueChangeListener<String>() {
					@Override
					public void valueChange(ValueChangeEvent<String> event) {
						
					}
				});
				
				edtCampo.addShortcutListener(new ShortcutListener("nome",ShortcutAction.KeyCode.ARROW_DOWN, null) {
					@Override
					public void handleAction(Object sender, Object target) {
						System.out.println("Eu vi seta para baixo");
					}
				});
				
				edtCampo.addShortcutListener(new ShortcutListener("nome",ShortcutAction.KeyCode.ENTER, null) {
					@Override
					public void handleAction(Object sender, Object target) {
						System.out.println("Eu vi o enter");
					}
				});
			}
			
			CssLayout div2 = new CssLayout();
			div2.addStyleName("div2-teste");
			div0.addComponent(div2);
			{
				Label lbl = new Label("Conteudo 2 deste campo pode ser uma coisa bem grande mesmo");
				div2.addComponent(lbl);
			}
		}
	}
}
