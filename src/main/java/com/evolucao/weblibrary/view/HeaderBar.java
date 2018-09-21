package com.evolucao.weblibrary.view;

import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.ApplicationUI;
import com.evolucao.weblibrary.IcoPorto;
import com.example.myapplication.DatabaseApp;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class HeaderBar extends HorizontalLayout {
	public HeaderBar() {
		addStyleName("header-bar");
		setWidth("100%");
		{
			/*
	    	Label lbl = new Label("'<link rel='shortcut icon' href='.VAADIN/themes/zapdoctor/favicon.ico' type='image/x-icon'/>", ContentMode.HTML);
	    	lbl.addStyleName("favicon");
	    	lbl.setWidthUndefined();
	    	addComponent(lbl);
	    	*/
			
			HorizontalLayout workArea = new HorizontalLayout();
			workArea.setWidth(Utils.getWorkAreaWidth());
			workArea.addStyleName("work-area");
			addComponent(workArea);
			this.setComponentAlignment(workArea, Alignment.MIDDLE_CENTER);
			{
				Button btnLogo = new Button();
				btnLogo.setWidth("180px");
				btnLogo.addStyleName("remove-button");
				btnLogo.addStyleName("logo-button");
				btnLogo.setIcon(new ThemeResource("imagens/logo.png"));
				workArea.addComponent(btnLogo);
				//workArea.setExpandRatio(btnLogo, 1);
				btnLogo.setResponsive(true);

				CssLayout searchContainer = new CssLayout();
				searchContainer.addStyleName("search-container");
				workArea.addComponent(searchContainer);
				workArea.setExpandRatio(searchContainer, 1);
				{
					TextField searchField = new TextField();
					searchField.addStyleName("remove-textfield");
					searchField.addStyleName("search-field");
					searchField.setPlaceholder("Busque pelo nome do m�dico, exame ou especialidade...");
					searchContainer.addComponent(searchField);
					
					Label lblSearchIcon = new Label(IcoPorto.ICONSEARCH.getHtml(), ContentMode.HTML);
					lblSearchIcon.addStyleName("search-icon");
					searchContainer.addComponent(lblSearchIcon);
				}
				
				Label lblTelefone = new Label(IcoPorto.TELEPHONE.getHtml() + " 0800 021 0001", ContentMode.HTML);
				lblTelefone.addStyleName("telefone");
				workArea.addComponent(lblTelefone);
				
				Label lblSpanLine = new Label();
				lblSpanLine.addStyleName("span-line");
				workArea.addComponent(lblSpanLine);
				
				Button btnContato = new Button("ENTRE EM CONTATO");
				btnContato.addStyleName("remove-button");
				btnContato.addStyleName("link-button-system");
				workArea.addComponent(btnContato);
				
				HorizontalLayout carrinho = new HorizontalLayout();
				carrinho.setMargin(false);
				carrinho.setSpacing(false); 
				carrinho.addStyleName("carrinho");
				workArea.addComponent(carrinho);
				workArea.setComponentAlignment(carrinho, Alignment.MIDDLE_RIGHT);
				{
					Button btnCarrinhoSacola = new Button("");
					btnCarrinhoSacola.setResponsive(true);
					btnCarrinhoSacola.addStyleName("carrinho-ico");
					btnCarrinhoSacola.addStyleName("remove-button");
					btnCarrinhoSacola.setIcon(IcoPorto.MINICART);
					carrinho.addComponent(btnCarrinhoSacola);

					
					Button btnCarrinho = new Button("0");
					btnCarrinho.addStyleName("remove-button");
					btnCarrinho.addStyleName("carrinho-lbl");
					btnCarrinho.setResponsive(true);
					carrinho.addComponent(btnCarrinho);
					//DatabaseApp.Instance.btnCarrinhoCompras = btnCarrinho;
				}
			}
		}
	}
}
