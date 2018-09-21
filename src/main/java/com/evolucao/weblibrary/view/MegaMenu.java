package com.evolucao.weblibrary.view;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.RecordButton;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MegaMenu extends HorizontalLayout {
	private static final long serialVersionUID = 1L;
	String uidOptionActive = "inicio";

	public void setUidOptionActive(String uidOptionActive) {
		this.uidOptionActive = uidOptionActive;
	}
	public String getUidOptionActive() {
		return this.uidOptionActive;
	}
	
	public MegaMenu() {
		addStyleName("megamenu");
		setWidth("100%");
		
		HorizontalLayout workArea = new HorizontalLayout();
		workArea.setWidth(Utils.getWorkAreaWidth());
		workArea.addStyleName("work-area");
		this.addComponent(workArea);
		this.setComponentAlignment(workArea, Alignment.MIDDLE_CENTER);
		{
			HorizontalLayout megaMenuContainer = new HorizontalLayout();
			megaMenuContainer.addStyleName("megamenu-container");
			megaMenuContainer.setSpacing(false);
			workArea.addComponent(megaMenuContainer);
			{
				megaMenuContainer.addComponent(deployStartButton());
				ApplicationUI applicationUI = (ApplicationUI) UI.getCurrent();
				
				try {
					applicationUI.database.openConnection();
					
					Table tblMenu = applicationUI.database.loadTableByName("menu");
					tblMenu.select("uid");
					tblMenu.setFilter("descricao", "principal");
					tblMenu.loadData();
					String uidMenu = tblMenu.getString("uid");
					tblMenu.close();
					
					Table tblOpcoes = applicationUI.database.loadTableByName("opcoes");
					tblOpcoes.select("*");
					tblOpcoes.setFilter("uidmenu", uidMenu);
					tblOpcoes.setOrder("ordem");
					tblOpcoes.loadData();
					while (!tblOpcoes.eof()) {
						// Verifica se o usuario tem acesso a opcao, caso tenha, coloca a opcao
						megaMenuContainer.addComponent(deployOption(tblOpcoes.getCurrentSimpleRecord()));
						tblOpcoes.next();
					}
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
				finally {
					applicationUI.database.closeConnection();
				}
				
				/*
				megaMenuContainer.addComponent(deployOption(null, null, "CONSULTAS"));
				megaMenuContainer.addComponent(deployOption(null, null, "EXAMES"));
				*/
			}
		}
	}
	
	public CssLayout deployStartButton() {
		CssLayout option = new CssLayout();
		option.addStyleName("option");
		option.setWidthUndefined();
		{
			Button optionHead = new Button("IN�CIO");
			optionHead.addStyleName("remove-button");
			optionHead.addStyleName("start-button");
			optionHead.addStyleName("active");
			option.addComponent(optionHead);
		}
		
		return option;
	}
	
	public CssLayout deployOption(SimpleRecord simpleRecord) {
		ApplicationUI applicationUI = (ApplicationUI) UI.getCurrent();
		
		CssLayout option = new CssLayout();
		option.addStyleName("option");
		option.setWidthUndefined();
		{
			Button optionHead = new Button(simpleRecord.getString("titulo") + " " + FontAwesome.ANGLE_DOWN.getHtml());
			optionHead.setCaptionAsHtml(true);
			optionHead.addStyleName("remove-button");
			optionHead.addStyleName("option-head");
			option.addComponent(optionHead);

			HorizontalLayout suboptionslist = new HorizontalLayout();
			suboptionslist.addStyleName("suboptionslist");
			suboptionslist.addStyleName("suboptions");
			suboptionslist.setSpacing(false);
			suboptionslist.setWidth("100%");
			option.addComponent(suboptionslist);
			{
				VerticalLayout opcoes = new VerticalLayout();
				opcoes.addStyleName("opcoes");
				opcoes.addStyleName("left-space");
				opcoes.setMargin(false);
				opcoes.setSpacing(false);
				//opcoes.setWidthUndefined();
				suboptionslist.addComponent(opcoes);
				suboptionslist.setExpandRatio(opcoes, 2);
				{
					Label lblTitulo = new Label(simpleRecord.getString("tituloopcoes"));
					lblTitulo.addStyleName("titulo");
					lblTitulo.setWidth("100%");
					opcoes.addComponent(lblTitulo);
					
					Table tblSubOpcoes = applicationUI.database.loadTableByName("subopcoes");
					tblSubOpcoes.select("*");
					tblSubOpcoes.setFilter("uidopcao", simpleRecord.getString("uid"));
					tblSubOpcoes.setOrder("ordem");
					tblSubOpcoes.loadData();
					Integer deployed = 0;
					HorizontalLayout linha = null;
					while (!tblSubOpcoes.eof()) {
						// Caso o usuario possa ter acesso a esta opcao
						if (true) {
							if (deployed==0) {
								linha = new HorizontalLayout();
								linha.setWidth("100%");
								linha.setSpacing(false);
								opcoes.addComponent(linha);
							}
							RecordButton btnOpcao = new RecordButton(tblSubOpcoes.getString("titulo"));
							btnOpcao.addStyleName("remove-button");
							btnOpcao.addStyleName("blank-button");
							btnOpcao.addStyleName("opcao-button");
							btnOpcao.setValue("commandexecutename", tblSubOpcoes.getString("commandexecutename"));
							btnOpcao.setWidth("100%");
							linha.addComponent(btnOpcao);
							btnOpcao.addClickListener(new ClickListener() {
								@Override
								public void buttonClick(ClickEvent event) {
									RecordButton btn = (RecordButton) event.getButton();
									//System.out.println(btn.getString("commandexecutename"));
									applicationUI.database.executeComandByName(btn.getString("commandexecutename"));
								}
							});
							
							deployed ++;
							
							if (deployed>=simpleRecord.getInteger("opcoesporlinha")) {
								deployed = 0;
							}
						}
						
						tblSubOpcoes.next();
					}					
				}
				
				VerticalLayout imagem = new VerticalLayout();
				imagem.addStyleName("imagem");
				imagem.addStyleName("right-space");
				//imagem.setWidthUndefined();
				imagem.setMargin(false);
				imagem.setSpacing(false);
				suboptionslist.addComponent(imagem);
				suboptionslist.setExpandRatio(imagem, 1);
				{
					Label lblTitulo = new Label(simpleRecord.getString("tituloimagem"), ContentMode.HTML);
					lblTitulo.addStyleName("titulo-imagem");
					imagem.addComponent(lblTitulo);
					
					Image img = new Image(null, new ThemeResource(simpleRecord.getString("urlimagem")));
					img.addStyleName("imagem-img");
					imagem.addComponent(img);
				}
			}
		}
		
		return option;
	}
}
