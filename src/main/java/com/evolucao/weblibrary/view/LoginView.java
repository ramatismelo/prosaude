package com.evolucao.weblibrary.view;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.MainBox;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.Atualizacao;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

public class LoginView extends CssLayout implements View {
	@Override
	public void enter(ViewChangeEvent event) {
	}
	
	public LoginView() {
		addStyleName("mainview");
		
		CssLayout main = new CssLayout();
		main.addStyleName("flex-direction-col");
		main.addStyleName("form-login");
		main.addStyleName("centered");
		addComponent(main);
		{
			ApplicationUI ui = (ApplicationUI) UI.getCurrent();

			Table tblParametros = ui.database.loadTableByName("parametros");
			tblParametros.checkTable();
			
			tblParametros.select("nomeEmpresa, projectName");
			tblParametros.loadData();
			
			// Verifica se existem opcoes na relacao de opcoes do sistema, caso nao exista, cria agora
			Table tblSysOptions = ui.database.loadTableByName("sysoptions");
			tblSysOptions.select("uid");
			tblSysOptions.setFilter("projectname", ui.getProjectName());
			tblSysOptions.setLimit(1);
			tblSysOptions.loadData();
			if (tblSysOptions.eof()) {
				Atualizacao.createMenu();
			}
				
			MainBox mainBox = new MainBox(tblParametros.getString("nomeEmpresa"));
			main.addComponent(mainBox);
			{
				RmGrid rmGrid = ui.database.loadRmGridByName("login");
				if (rmGrid.getTable().insert()) {
					rmGrid.getForm().setTable(rmGrid.getTable());
					mainBox.getBody().addComponent(rmGrid.getForm().deploy());
				}
			}
		}
	}
}
