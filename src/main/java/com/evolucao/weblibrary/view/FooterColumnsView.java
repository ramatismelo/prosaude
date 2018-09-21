package com.evolucao.weblibrary.view;

import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.IcoPorto;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FooterColumnsView extends VerticalLayout {
	public FooterColumnsView() {
		setMargin(false);
		setSpacing(false);
		{
			addComponent(footerColumns());
			addComponent(footer());
		}
	}
	
	public VerticalLayout footer() {
		VerticalLayout footer = new VerticalLayout();
		footer.addStyleName("footer");
		footer.setMargin(false);
		footer.setSpacing(false);
		{
			HorizontalLayout workArea = new HorizontalLayout();
			workArea.addStyleName("work-area");
			workArea.setWidth(Utils.getWorkAreaWidth());
			workArea.setSpacing(false);
			footer.addComponent(workArea);
			footer.setComponentAlignment(workArea, Alignment.MIDDLE_CENTER);
			{
				HorizontalLayout horizontal = new HorizontalLayout();
				workArea.addComponent(horizontal);
				{
					Button btnLogo = new Button();
					btnLogo.addStyleName("remove-button");
					btnLogo.addStyleName("btnlogo");
					btnLogo.setIcon(new ThemeResource("imagens/logo_footer.png"));
					horizontal.addComponent(btnLogo);
					
					Button btnFaceBook = new Button();
					btnFaceBook.addStyleName("remove-button");
					btnFaceBook.addStyleName("btnfacebook");
					btnFaceBook.addStyleName("padding-top");
					horizontal.addComponent(btnFaceBook);
					
					Button btnTwiter = new Button();
					btnTwiter.addStyleName("remove-button");
					btnTwiter.addStyleName("btntwiter");
					btnTwiter.addStyleName("padding-top");
					horizontal.addComponent(btnTwiter);
					
					Button btnLinked = new Button();
					btnLinked.addStyleName("remove-button");
					btnLinked.addStyleName("btnlinked");
					btnLinked.addStyleName("padding-top");
					horizontal.addComponent(btnLinked);
					
					Image imgCartao = new Image(null, new ThemeResource("imagens/payments.png"));
					imgCartao.addStyleName("payments");
					imgCartao.addStyleName("padding-top");
					horizontal.addComponent(imgCartao);
				}
				
				Label lblMensagem = new Label("�Copyright 2016 by ZapDoctor. Todos os Direitos Reservados.");
				lblMensagem.addStyleName("lblmensagem");
				workArea.addComponent(lblMensagem);
				workArea.setComponentAlignment(lblMensagem, Alignment.MIDDLE_RIGHT);
			}
		}
		
		return footer;
	}
	
	public VerticalLayout footerColumns() {
		VerticalLayout footerColumns = new VerticalLayout();
		footerColumns.addStyleName("footer-columns");
		footerColumns.setMargin(false);
		footerColumns.setSpacing(false);
		{
			VerticalLayout workArea = new VerticalLayout();
			workArea.addStyleName("work-area");
			workArea.setWidth(Utils.getWorkAreaWidth());
			workArea.setSpacing(false);
			footerColumns.addComponent(workArea);
			footerColumns.setComponentAlignment(workArea, Alignment.MIDDLE_CENTER);
			{
				Label lblFaixa = new Label("Informa��es adicionais:");
				lblFaixa.addStyleName("faixa");
				workArea.addComponent(lblFaixa);
				
				HorizontalLayout colunasContainer = new HorizontalLayout();
				colunasContainer.addStyleName("colunas-container");
				colunasContainer.setWidth("100%");
				colunasContainer.setSpacing(false);
				workArea.addComponent(colunasContainer);
				{
					VerticalLayout coluna = new VerticalLayout();
					coluna.addStyleName("coluna");
					coluna.setMargin(false);
					coluna.setSpacing(false);
					colunasContainer.addComponent(coluna);
					{
						Label lblTitulo = new Label("Sobre o ZapDoctor");
						lblTitulo.addStyleName("titulo");
						coluna.addComponent(lblTitulo);
						
						Button btnBotao = new Button("Quem somos");
						btnBotao.addStyleName("remove-buttom");
						btnBotao.addStyleName("link-button-system");
						btnBotao.addStyleName("botao");
						coluna.addComponent(btnBotao);
						
						btnBotao = new Button("D�vidas");
						btnBotao.addStyleName("remove-buttom");
						btnBotao.addStyleName("link-button-system");
						btnBotao.addStyleName("botao");
						coluna.addComponent(btnBotao);
						
						btnBotao = new Button("Termos de uso");
						btnBotao.addStyleName("remove-buttom");
						btnBotao.addStyleName("link-button-system");
						btnBotao.addStyleName("botao");
						coluna.addComponent(btnBotao);
						
						btnBotao = new Button("Fale conosco");
						btnBotao.addStyleName("remove-buttom");
						btnBotao.addStyleName("link-button-system");
						btnBotao.addStyleName("botao");
						coluna.addComponent(btnBotao);
					}
					
					/* Segunda coluna */
					coluna = new VerticalLayout();
					coluna.addStyleName("coluna");
					coluna.setMargin(false);
					coluna.setSpacing(false);
					colunasContainer.addComponent(coluna);
					{
						Label lblTitulo = new Label("Informa��es para contato");
						lblTitulo.addStyleName("titulo");
						coluna.addComponent(lblTitulo);
						
						HorizontalLayout informacoes = new HorizontalLayout();
						informacoes.addStyleName("informacoes");
						informacoes.setWidth("100%");
						informacoes.setSpacing(false);
						coluna.addComponent(informacoes);
						{
							Label lblIcone = new Label(IcoPorto.ICONLOCATION.getHtml(), ContentMode.HTML);
							lblIcone.addStyleName("icone");
							informacoes.addComponent(lblIcone);
							
							VerticalLayout conteudo = new VerticalLayout();
							conteudo.addStyleName("conteudo");
							conteudo.setMargin(false);
							conteudo.setSpacing(false);
							informacoes.addComponent(conteudo);
							informacoes.setExpandRatio(conteudo, 1);
							{
								Label lblSubTitulo = new Label("Endere�o:");
								lblSubTitulo.addStyleName("subtitulo");
								conteudo.addComponent(lblSubTitulo);
								
								Label lblInformacao = new Label("Av. Joaquim Nabuco, 1755 - Centro");
								lblInformacao.addStyleName("lblinformacao");
								conteudo.addComponent(lblInformacao);
								
								lblInformacao = new Label("Manaus - AM, 69020-030");
								lblInformacao.addStyleName("lblinformacao");
								conteudo.addComponent(lblInformacao);
							}
						}

						/**/
						informacoes = new HorizontalLayout();
						informacoes.addStyleName("informacoes");
						informacoes.addStyleName("top-space");
						informacoes.setWidth("100%");
						informacoes.setSpacing(false);
						coluna.addComponent(informacoes);
						{
							Label lblIcone = new Label(IcoPorto.TELEPHONE.getHtml(), ContentMode.HTML);
							lblIcone.addStyleName("icone");
							informacoes.addComponent(lblIcone);
							
							VerticalLayout conteudo = new VerticalLayout();
							conteudo.addStyleName("conteudo");
							conteudo.setMargin(false);
							conteudo.setSpacing(false);
							informacoes.addComponent(conteudo);
							informacoes.setExpandRatio(conteudo, 1);
							{
								Label lblSubTitulo = new Label("Telefone:");
								lblSubTitulo.addStyleName("subtitulo");
								conteudo.addComponent(lblSubTitulo);
								
								Label lblInformacao = new Label("0800-031-0001");
								lblInformacao.addStyleName("lblinformacao");
								conteudo.addComponent(lblInformacao);
							}
						}
						
						/**/
						informacoes = new HorizontalLayout();
						informacoes.addStyleName("informacoes");
						informacoes.addStyleName("top-space");
						informacoes.setWidth("100%");
						informacoes.setSpacing(false);
						coluna.addComponent(informacoes);
						{
							Label lblIcone = new Label(IcoPorto.EMAIL.getHtml(), ContentMode.HTML);
							lblIcone.addStyleName("icone");
							informacoes.addComponent(lblIcone);
							
							VerticalLayout conteudo = new VerticalLayout();
							conteudo.addStyleName("conteudo");
							conteudo.setMargin(false);
							conteudo.setSpacing(false);
							informacoes.addComponent(conteudo);
							informacoes.setExpandRatio(conteudo, 1);
							{
								Label lblSubTitulo = new Label("Email:");
								lblSubTitulo.addStyleName("subtitulo");
								conteudo.addComponent(lblSubTitulo);
								
								Button btnEmail = new Button("atendimento@zapdoctor.com.br");
								btnEmail.addStyleName("remove-buttom");
								btnEmail.addStyleName("link-button-system");
								btnEmail.addStyleName("email");
								conteudo.addComponent(btnEmail);
							}
						}
						
						/**/
						informacoes = new HorizontalLayout();
						informacoes.addStyleName("informacoes");
						informacoes.addStyleName("top-space");
						informacoes.setWidth("100%");
						informacoes.setSpacing(false);
						coluna.addComponent(informacoes);
						{
							Label lblIcone = new Label(IcoPorto.CLOCK.getHtml(), ContentMode.HTML);
							lblIcone.addStyleName("icone");
							informacoes.addComponent(lblIcone);
							
							VerticalLayout conteudo = new VerticalLayout();
							conteudo.addStyleName("conteudo");
							conteudo.setMargin(false);
							conteudo.setSpacing(false);
							informacoes.addComponent(conteudo);
							informacoes.setExpandRatio(conteudo, 1);
							{
								Label lblSubTitulo = new Label("Atendimento Dias/Horas:");
								lblSubTitulo.addStyleName("subtitulo");
								conteudo.addComponent(lblSubTitulo);
								
								Label lblInformacao = new Label("Seg - Dom / 9:00AM - 8:00PM");
								lblInformacao.addStyleName("lblinformacao");
								conteudo.addComponent(lblInformacao);
							}
						}
					}

					coluna = new VerticalLayout();
					coluna.addStyleName("coluna");
					coluna.setMargin(false);
					coluna.setSpacing(false);
					colunasContainer.addComponent(coluna);
					{
						Label lblTitulo = new Label("Profissional da sa�de?");
						lblTitulo.addStyleName("titulo");
						coluna.addComponent(lblTitulo);
						
						Button btnBotao = new Button("Credencie-se j� e seja ZapDoctor");
						btnBotao.addStyleName("remove-button");
						btnBotao.addStyleName("link-button-system");
						btnBotao.addStyleName("botao");
						coluna.addComponent(btnBotao);
						
						btnBotao = new Button("Cadastre sua cl�nica, consult�rio e hospital");
						btnBotao.addStyleName("remove-button");
						btnBotao.addStyleName("link-button-system");
						btnBotao.addStyleName("botao");
						coluna.addComponent(btnBotao);
					}

					coluna = new VerticalLayout();
					coluna.addStyleName("coluna");
					coluna.setMargin(false);
					coluna.setSpacing(false);
					colunasContainer.addComponent(coluna);
					{
						Label lblTitulo = new Label("Seja o primeiro a saber");
						lblTitulo.addStyleName("titulo");
						coluna.addComponent(lblTitulo);
						
						Label lblInformacao = new Label("Receba no e-mail nosso informativo com novidades sobre novos m�dicos, servi�os e promo��es exclusivas.");
						lblInformacao.addStyleName("lblinformacao");
						coluna.addComponent(lblInformacao);
						
						lblInformacao = new Label("Assine o boletim hoje.");
						lblInformacao.addStyleName("lblinformacao");
						lblInformacao.addStyleName("top-space");
						coluna.addComponent(lblInformacao);
						
						lblInformacao = new Label("Insira o seu endere�o de email");
						lblInformacao.addStyleName("lblinformacao");
						lblInformacao.addStyleName("top-space");
						coluna.addComponent(lblInformacao);
						
						HorizontalLayout textFieldBoletim = new HorizontalLayout();
						textFieldBoletim.addStyleName("textfield-boletim");
						textFieldBoletim.setSpacing(false);
						coluna.addComponent(textFieldBoletim);
						{
							TextField email = new TextField();
							email.addStyleName("emailfield");
							textFieldBoletim.addComponent(email);
							
							Button btnSubmit = new Button();
							btnSubmit.addStyleName("remove-button");
							btnSubmit.addStyleName("emailsubmit");
							btnSubmit.setCaption("Enviar");
							textFieldBoletim.addComponent(btnSubmit);
						}
					}
				}
			}
		}
		return footerColumns;
	}
	
	public VerticalLayout footerColumns2() {
		VerticalLayout footerColumns = new VerticalLayout();
		footerColumns.addStyleName("footer-columns");
		footerColumns.setMargin(false);
		footerColumns.setSpacing(false);
		{
		}
		
		return footerColumns;
	}
}
