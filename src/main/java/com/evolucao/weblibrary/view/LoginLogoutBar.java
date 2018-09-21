package com.evolucao.weblibrary.view;

import com.evolucao.rmlibrary.utils.Utils;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LoginLogoutBar extends HorizontalLayout {
	public LoginLogoutBar() {
		addStyleName("login-logout-bar");
		setWidth("100%");
		{
			HorizontalLayout workAreaContainer = new HorizontalLayout();
			workAreaContainer.setWidth(Utils.getWorkAreaWidth());
			workAreaContainer.addStyleName("work-area");
			addComponent(workAreaContainer);
			setComponentAlignment(workAreaContainer, Alignment.TOP_CENTER);
			{
				Button btnLogin = new Button("ENTRAR/CADASTRAR");
				btnLogin.addStyleName("link-button-system");
				workAreaContainer.addComponent(btnLogin);
				workAreaContainer.setComponentAlignment(btnLogin, Alignment.MIDDLE_RIGHT);
			}
		}
	}
}
