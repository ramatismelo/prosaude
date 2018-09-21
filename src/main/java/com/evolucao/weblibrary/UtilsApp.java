package com.evolucao.weblibrary;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.utils.Utils;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UtilsApp {
	public UtilsApp() {
		
	}
	
	public HorizontalLayout teste2() {
		HorizontalLayout teste = new HorizontalLayout();
		teste.setWidth("100%");
		teste.addStyleName("workarea");
		{
			Label lblTeste = new Label("Conteudo 1");
			teste.addComponent(lblTeste);
			
			lblTeste = new Label("Conteudo 2");
			teste.addComponent(lblTeste);
			
			CssLayout div = new CssLayout();
			div.addStyleName("carrinho");
			teste.addComponent(div);
			teste.setExpandRatio(div, 1);
			teste.setComponentAlignment(div, Alignment.MIDDLE_RIGHT);
			{
				lblTeste = new Label("Conteudo 3");
				div.addComponent(lblTeste);
				
				lblTeste = new Label("Conteudo 4");
				div.addComponent(lblTeste);
			}
		}
		
		return teste;
	}

	public CssLayout teste() {
		CssLayout teste = new CssLayout();
		teste.addStyleName("workarea-teste");
		{
			Label lblTeste = new Label("Conteudo 1");
			teste.addComponent(lblTeste);
			
			lblTeste = new Label("Conteudo 2");
			teste.addComponent(lblTeste);
			
			CssLayout div = new CssLayout();
			div.addStyleName("carrinho-teste");
			teste.addComponent(div);
			{
				lblTeste = new Label("Conteudo 3");
				div.addComponent(lblTeste);
				
				lblTeste = new Label("Conteudo 4");
				div.addComponent(lblTeste);
			}
		}
		return teste;
	}
	

	/*
	 * Teste que tenta colocar botoes dentro de div sem sair do div
	 */
	public HorizontalLayout teste3() {
		HorizontalLayout content = new HorizontalLayout();
		content.addStyleName("content");
		content.setWidth("100%");
		{
			HorizontalLayout workArea = new HorizontalLayout();
			workArea.addStyleName("work-area");
			workArea.setWidth(Utils.getWorkAreaWidth());
			content.addComponent(workArea);
			content.setComponentAlignment(workArea, Alignment.MIDDLE_CENTER);
			{
				VerticalLayout opcoes = new VerticalLayout();
				opcoes.addStyleName("backblue");
				opcoes.setMargin(false);
				workArea.addComponent(opcoes);
				workArea.setExpandRatio(opcoes, 3);
				{
					HorizontalLayout linha = new HorizontalLayout();
					linha.setWidth("100%");
					opcoes.addComponent(linha);
					{
						Button btnOpcao = new Button("Conteudo1");
						btnOpcao.addStyleName("blank-button");
						btnOpcao.setWidth("100%");
						linha.addComponent(btnOpcao);
						linha.setExpandRatio(btnOpcao, 1);
						
						btnOpcao = new Button("Conteudo2");
						btnOpcao.addStyleName("blank-button");
						btnOpcao.setWidth("100%");
						linha.addComponent(btnOpcao);
						linha.setExpandRatio(btnOpcao, 1);
						
						btnOpcao = new Button("Conteudo3");
						btnOpcao.addStyleName("blank-button");
						btnOpcao.setWidth("100%");
						linha.addComponent(btnOpcao);
						linha.setExpandRatio(btnOpcao, 1);
					}
				}
				
				VerticalLayout imagem = new VerticalLayout();
				imagem.addStyleName("backgreen");
				imagem.setMargin(false);
				workArea.addComponent(imagem);
				workArea.setExpandRatio(imagem, 1);
				{
					Label lblTeste = new Label("Conteudo");
					imagem.addComponent(lblTeste);
				}
			}
			
		}
		
		return content;
	}

	public void mainLayoutTeste() {
		/*
		final VerticalLayout layout = new VerticalLayout();
		layout.addStyleName("main-content-system");
		layout.setSizeFull();
		layout.setMargin(false);
		setContent(layout);
		{
			layout.addComponent(System());
			
			/*
			Panel panel = new Panel("teste");
			panel.setHeight("100%");
			panel.addStyleName("panel-ramatis");
			layout.addComponent(panel);
			
			VerticalLayout vl = new VerticalLayout();
			vl.addStyleName("ramatis");
			vl.setMargin(false);
			vl.setHeightUndefined();
			//layout.addComponent(vl);
			panel.setContent(vl);
			{
				vl.addComponent(new LoginLogoutBar());
				vl.addComponent(new HeaderBar());
				
				HorizontalLayout hl = new HorizontalLayout();
				hl.addStyleName("dummy");
				hl.setHeight("400px");
				hl.setWidth("100%");
				vl.addComponent(hl);
				
				hl = new HorizontalLayout();
				hl.addStyleName("dummy2");
				hl.setHeight("400px");
				hl.setWidth("100%");
				vl.addComponent(hl);
			}
			/*
			layout.addComponent(hl);
			
			layout.addComponent(hl);
			
			hl = new HorizontalLayout();
			hl.addStyleName("dummy");
			hl.setHeight("400px");
			hl.setWidth("100%");
			layout.addComponent(hl);
			*/
		/*}*/
	}
	
	/*
	public void createMenus() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblMenu = ui.getDatabase().loadTableByName("menu");
		Table tblOpcoes = ui.getDatabase().loadTableByName("opcoes");
		Table tblSubOpcoes = ui.getDatabase().loadTableByName("subopcoes");
		
		tblMenu.alterTable();
		tblOpcoes.alterTable();
		tblSubOpcoes.alterTable();
		
		tblMenu.insert();
		tblMenu.setValue("descricao", "principal");
		tblMenu.execute();
		String uidMenu = tblMenu.getLastInsertUUID();
		
		
		tblOpcoes.insert();
		tblOpcoes.setValue("uidmenu", uidMenu);
		tblOpcoes.setValue("titulo", "Consultas");
		tblOpcoes.setValue("tituloopcoes", "ESPECIALIDADES MAIS PROCURADAS:");
		tblOpcoes.setValue("tituloimagem", "MARQUE SUA <b style=\"font-weight: 700;\">CONSULTA</b><strong style=\"font-weight: 800;\">AGORA</strong>");
		tblOpcoes.setValue("urlimagem", "imagens/medico-005.jpg");
		tblOpcoes.setValue("ordem", "001");
		tblOpcoes.setValue("opcoesporlinha", 3);
		tblOpcoes.execute();
		{
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Cardiologista");
			tblSubOpcoes.setValue("ordem", "001");
			tblSubOpcoes.setValue("commandexecutename", "medicos_cardiologista");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Cl�nico Geral");
			tblSubOpcoes.setValue("ordem", "002");
			tblSubOpcoes.setValue("commandexecutename", "medicos_clinico");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Dentista");
			tblSubOpcoes.setValue("ordem", "003");
			tblSubOpcoes.setValue("commandexecutename", "medicos_dentista");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Ginecologia e Obstetr�cia");
			tblSubOpcoes.setValue("ordem", "004");
			tblSubOpcoes.setValue("commandexecutename", "medicos_ginecologista");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Dermatologista");
			tblSubOpcoes.setValue("ordem", "005");
			tblSubOpcoes.setValue("commandexecutename", "medicos_dermatologista");
			tblSubOpcoes.execute();

			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Ortopedia e Traumatologia");
			tblSubOpcoes.setValue("ordem", "006");
			tblSubOpcoes.setValue("commandexecutename", "medicos_ortopedia");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Oftalmologia");
			tblSubOpcoes.setValue("ordem", "007");
			tblSubOpcoes.setValue("commandexecutename", "medicos_oftalmologia");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Pediatra");
			tblSubOpcoes.setValue("ordem", "008");
			tblSubOpcoes.setValue("commandexecutename", "medicos_pediatria");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Urologia");
			tblSubOpcoes.setValue("ordem", "009");
			tblSubOpcoes.setValue("commandexecutename", "medicos_urologia");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Outras Especialidades");
			tblSubOpcoes.setValue("ordem", "010");
			tblSubOpcoes.setValue("commandexecutename", "medicos_outrasespecialidades");
			tblSubOpcoes.execute();
		}
		 
		tblOpcoes.insert();
		tblOpcoes.setValue("uidmenu", uidMenu);
		tblOpcoes.setValue("titulo", "Exames");
		tblOpcoes.setValue("tituloopcoes", "SELECIONE O TIPO DE EXAME DESEJADO:");
		tblOpcoes.setValue("tituloimagem", "AGENDE SEUS <b style=\"font-weight: 700;\">EXAMES</b><strong style=\"font-weight: 800;\">AGORA</strong>");
		tblOpcoes.setValue("urlimagem", "imagens/medico-003.jpg");
		tblOpcoes.setValue("ordem", "002");
		tblOpcoes.setValue("opcoesporlinha", 1);
		tblOpcoes.execute();
		{
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Exames laboratoriais");
			tblSubOpcoes.setValue("ordem", "001");
			tblSubOpcoes.setValue("commandexecutename", "exames_laboratoriais");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Exames de imagem");
			tblSubOpcoes.setValue("ordem", "002");
			tblSubOpcoes.setValue("commandexecutename", "exames_imagens");
			tblSubOpcoes.execute();
		}

		tblOpcoes.insert();
		tblOpcoes.setValue("uidmenu", uidMenu);
		tblOpcoes.setValue("titulo", "PACOTES DE SERVI�OS");
		tblOpcoes.setValue("tituloopcoes", "PACOTES MAIS PROCURADOS:");
		tblOpcoes.setValue("tituloimagem", "SELECIONE O MELHOR <b style=\"font-weight: 700;\">PACOTE DE SERVICOS</b><strong style=\"font-weight: 800;\">AGORA</strong>");
		tblOpcoes.setValue("urlimagem", "imagens/medico-003.jpg");
		tblOpcoes.setValue("ordem", "003");
		tblOpcoes.setValue("opcoesporlinha", 3);
		tblOpcoes.execute();
		{
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Exames pr�-nupciais");
			tblSubOpcoes.setValue("ordem", "001");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_prenupciais");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Parto apartamento");
			tblSubOpcoes.setValue("ordem", "002");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_partoapartamento");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Parto enfermaria");
			tblSubOpcoes.setValue("ordem", "003");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_partoaenfermaria");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Exames de rotina crian�as");
			tblSubOpcoes.setValue("ordem", "004");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_rotinacriancas");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Exames de rotina idosos");
			tblSubOpcoes.setValue("ordem", "005");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_rotinaidosos");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Exames de rotina em geral");
			tblSubOpcoes.setValue("ordem", "006");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_rotinageral");
			tblSubOpcoes.execute();

			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Exames pr�-cirurgicos");
			tblSubOpcoes.setValue("ordem", "007");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_precirurgicos");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Exames admissionais");
			tblSubOpcoes.setValue("ordem", "008");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_examesadmissionais");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Exames demissionais");
			tblSubOpcoes.setValue("ordem", "009");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_examesdemissionais");
			tblSubOpcoes.execute();
			
			tblSubOpcoes.insert();
			tblSubOpcoes.setValue("uidopcao", tblOpcoes.getLastInsertUUID());
			tblSubOpcoes.setValue("titulo", "Outras Pacotes de Servi�os");
			tblSubOpcoes.setValue("ordem", "010");
			tblSubOpcoes.setValue("commandexecutename", "pacotes_outrospacotes");
			tblSubOpcoes.execute();
		}
	}
	*/

}
