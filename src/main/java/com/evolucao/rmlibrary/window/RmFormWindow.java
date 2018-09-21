package com.evolucao.rmlibrary.window;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.evolucao.rmlibrary.database.enumerators.MessageWindowType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.events.ApplicationEventBus;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.production.RmButtonSet;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.window.enumerators.RmFormButtonType;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.events.ApplicationEvent.CloseOpenWindowsEvent;

public class RmFormWindow extends Window {
	VerticalLayout body = new VerticalLayout();
	Label lblTitle = new Label();
	ControlForm controlForm = null;
	String title = "";
	List<RmFormButtonBase> rmFormButtons = new ArrayList<RmFormButtonBase>();
	VerticalLayout content = new VerticalLayout();
	//FormLayout content = new FormLayout();
	Window window = null;
	RmGrid rmGrid = null;
	boolean scrollBody = true;
	
	public void setScrollBody(Boolean scrollBody) {
		this.scrollBody = scrollBody;
	}
	public Boolean getScrollBody() {
		return this.scrollBody;
	}
	
	public void setRmGrid(RmGrid rmGrid) {
		this.rmGrid = rmGrid;
	}
	public RmGrid getRmGrid() {
		return this.rmGrid;
	}

	public void setRmFormButton(List<RmFormButtonBase> rmFormButtons) {
		this.rmFormButtons = rmFormButtons;
	}
	public List<RmFormButtonBase> getRmFormButtons() {
		return this.rmFormButtons;
	}
	
	public void setTitle(String title) {
		this.title = title;
		this.lblTitle.setCaption(title);
	}
	public String getTitle() {
		return this.title;
	}
	
	public void setForm(ControlForm form) {
		this.controlForm = form;
	}
	public ControlForm getForm() {
		return this.controlForm;
	}
	
	public void setBody(VerticalLayout body) {
		this.body = body;
	}
	public VerticalLayout getBody() {
		return this.body;
	}

	public RmFormWindow() {
		this.window = this;

		// addStyleName("select-especialidades-window");
		// setCloseShortcut(KeyCode.ESCAPE, null);
		// getUI().getCurrent().addAction(new Window.CloseShortcut(this,
		// KeyCode.ESCAPE));
		// setHeight(90.0f, Unit.PERCENTAGE);
		//content.setStyleName("window-content");
		//content.setSizeFull();
		
		addStyleName("rmform");
		addStyleName("box-window");
		Responsive.makeResponsive(this);
		addCloseShortcut(KeyCode.ESCAPE, null);
		setModal(true);
		setResizable(true);
		setClosable(true);
		setDraggable(true);

		content.setSizeFull();
		content.setSpacing(false);
		content.setMargin(false);
		
		body.setSpacing(false);
		body.setMargin(false);
		
		setWidth(612, Unit.PIXELS);
		setHeight(300, Unit.PIXELS);

		setContent(content);
		updateContent();
		
		addCloseListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {
				if ((getForm()!=null) && (getForm().getTable()!=null)) {
					if ((getForm().getTable().getTableStatus()==TableStatus.INSERT) || 
						(getForm().getTable().getTableStatus()==TableStatus.UPDATE) || 
						(getForm().getTable().getTableStatus()==TableStatus.DELETE) ||
						(getForm().getTable().getTableStatus()==TableStatus.FILTER)) {
						getForm().getTable().cancel();
					}
				}
			}
		});
	}

	public static void open() {
		//evolucaoUI ui = (evolucaoUI) UI.getCurrent();
		ApplicationEventBus.post(new CloseOpenWindowsEvent());
		RmFormWindow rmFormWindow = new RmFormWindow();
		Window w = (Window) rmFormWindow;
		UI.getCurrent().addWindow(w);
		w.focus();
	}

	public void updateContent() {
		setCaption(this.getTitle());
		
		this.content.removeAllComponents();
		
		//content.addComponent(this.deployHeader());
		content.addComponent(this.deployBody());
		content.setExpandRatio(this.getBody(), 1);
		content.addComponent(this.deployFooter());
		
		this.getBody().setSizeFull();
		content.setExpandRatio(this.getBody(), 1);
	}

	public void show() {
		this.show(false);
	}
	
	public void show(Boolean autoOpenFilterForm) {
		this.updateContent();
		//evolucaoUI ui = (evolucaoUI) UI.getCurrent();
		ApplicationEventBus.post(new CloseOpenWindowsEvent());
		Window w = (Window) this;
		UI.getCurrent().addWindow(w);
		w.focus();
		
		if (autoOpenFilterForm) {
			if (this.getRmGrid()!=null) {
				this.getRmGrid().executeFilter();
			}
		}
	}

	public CssLayout deployHeader() {
		CssLayout head = new CssLayout();
		head.addStyleName("flex-direction-row");
		head.addStyleName("header-window");
		head.addStyleName("v-window");
		head.addStyleName("v-widget");
		{
			CssLayout div = new CssLayout();
			div.addStyleName("window-title");
			div.addStyleName("flex-grow-1");
			head.addComponent(div);
			{
				lblTitle.setCaption(this.getTitle());
				lblTitle.addStyleName("window-title-label");
				div.addComponent(lblTitle);
			}

			div = new CssLayout();
			head.addComponent(div);
			{
				Button btn = new Button();
				btn.addStyleName("button-circle");
				btn.setIcon(FontAwesome.CLOSE);
				div.addComponent(btn);
				btn.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if ((getForm()!=null) && (getForm().getTable()!=null)) {
							getForm().getTable().cancel();
						}
						close();
					}
				});
			}
		}
		return head;
	}

	public VerticalLayout deployBody() {
		body.addStyleName("window-body");
		if (this.getScrollBody()) {
			body.addStyleName("window-scroll");
		}
		return body;
	}

	public HorizontalLayout deployFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addStyleName("window-footer");
		{
			RmButtonSet buttonSet = new RmButtonSet();
			{
				for (RmFormButtonBase buttonBase : this.getRmFormButtons()) {
					RmFormButton btnButton = new RmFormButton(buttonBase.getCaption());
					btnButton.setRmFormButtonBase(buttonBase);
					btnButton.setIcon(buttonBase.getIcon());
					btnButton.addStyleName("button-normal");
					for (String styleName: buttonBase.getStyleNames()) {
						btnButton.addStyleName(styleName);
					}
					
					btnButton.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							RmFormButton formButton = (RmFormButton) event.getButton();
							RmFormButtonBase formButtonBase = formButton.getRmFormButtonBase();
							
							RmFormButtonClickEvent event2 = new RmFormButtonClickEvent(this);
							event2.setControlForm(getForm());
							event2.setWindow(window);
							event2.setRmFormWindow((RmFormWindow) window);
							formButtonBase.fireRmFormButtonClickEvent(event2);

							/*
							if (formButtonBase.getRmFormButtonType()!=RmFormButtonType.SAVE) {
								if ((getForm()!=null) && (getForm().getTable()!=null)) {
									getForm().getTable().cancel();
								}
							}
							
							// Caso esteja configurado para fechar a janela ao clique do botao
							if (formButtonBase.getCloseWindow()) {
								close();
							}
							*/
						}
					});
					
					
					if (buttonBase.getRmFormButtonType()==RmFormButtonType.MESSAGEOK) {
						btnButton.addStyleName("content-center");
						buttonSet.addLeftButton(btnButton);
					}
					else {
						buttonSet.addRigthButton(btnButton);
					}
				}
			}
			footer.addComponent(buttonSet);
		}
		
		return footer;
	}

	public void addMessage(String message, MessageWindowType messageWindowType) {
		CssLayout body = new CssLayout();
		body.addStyleName("flex-vertical-center");
		this.getBody().addComponent(body);
		{
			CssLayout div = new CssLayout();
			div.addStyleName("flex-direction-row");
			body.addComponent(div);
			{
				CssLayout divImg = new CssLayout();
				divImg.addStyleName("message-icon");
				div.addComponent(divImg);
				{
					if (messageWindowType == MessageWindowType.ERROR) {
						Image img = new Image(null, new ThemeResource("imagens/library/icon-error.png"));
						divImg.addComponent(img);
					}
					else if (messageWindowType == MessageWindowType.QUESTION) {
						Image img = new Image(null, new ThemeResource("imagens/library/icon-question.png"));
						divImg.addComponent(img);
					}
					else if (messageWindowType == MessageWindowType.INFO) {
						Image img = new Image(null, new ThemeResource("imagens/library/icon-info.png"));
						divImg.addComponent(img);
					}
					else if (messageWindowType == MessageWindowType.WARNING) {
						Image img = new Image(null, new ThemeResource("imagens/library/icon-warning.png"));
						divImg.addComponent(img);
					}
				}
				
				Label lblMensagem = new Label(message);
				lblMensagem.addStyleName("message-label");
				div.addComponent(lblMensagem);
			}
		}
	}
	
	public void addMessage(String message1, String message2, MessageWindowType messageWindowType) {
		CssLayout body = new CssLayout();
		body.addStyleName("flex-vertical-center");
		this.getBody().addComponent(body);
		{
			CssLayout div = new CssLayout();
			div.addStyleName("flex-direction-row");
			body.addComponent(div);
			{
				CssLayout divImg = new CssLayout();
				divImg.addStyleName("message-icon");
				div.addComponent(divImg);
				{
					if (messageWindowType == MessageWindowType.ERROR) {
						Image img = new Image(null, new ThemeResource("imagens/library/icon-error.png"));
						divImg.addComponent(img);
					}
					else if (messageWindowType == MessageWindowType.QUESTION) {
						Image img = new Image(null, new ThemeResource("imagens/library/icon-question.png"));
						divImg.addComponent(img);
					}
					else if (messageWindowType == MessageWindowType.INFO) {
						Image img = new Image(null, new ThemeResource("imagens/library/icon-info.png"));
						divImg.addComponent(img);
					}
					else if (messageWindowType == MessageWindowType.WARNING) {
						Image img = new Image(null, new ThemeResource("imagens/library/icon-warning.png"));
						divImg.addComponent(img);
					}
				}

				CssLayout divTexto = new CssLayout();
				div.addComponent(divTexto);
				{
					Label lblMensagem = new Label(message1);
					lblMensagem.addStyleName("message-label");
					divTexto.addComponent(lblMensagem);
					
					Label lblMensagem2 = new Label(message2);
					lblMensagem2.addStyleName("message-label");
					divTexto.addComponent(lblMensagem2);
				}
			}
		}
	}
	
	public RmFormButtonBase addSaveButton() {
		RmFormButtonBase btnButton = new RmFormButtonBase();
		btnButton.setCaption("Gravar");
		btnButton.setIcon(new ThemeResource("imagens/library/disk.png"));
		btnButton.setCloseWindow(false);
		btnButton.setRmFormButtonType(RmFormButtonType.SAVE);
		this.getRmFormButtons().add(btnButton);
		
		this.updateContent();
		
		return btnButton;
	}
	
	public RmFormButtonBase addCancelButton() {
		return addCancelButton("Cancelar");
	}
	
	public RmFormButtonBase addCancelButton(String caption) {
		RmFormButtonBase btnButton = new RmFormButtonBase();
		btnButton.setCaption(caption);
		btnButton.setIcon(new ThemeResource("imagens/library/cancel.png"));
		btnButton.setCloseWindow(true);
		btnButton.setRmFormButtonType(RmFormButtonType.CANCEL);
		this.getRmFormButtons().add(btnButton);
		
		this.updateContent();
		
		return btnButton;
	}
	
	public RmFormButtonBase addButton(String caption) {
		RmFormButtonBase btnButton = new RmFormButtonBase();
		btnButton.setCaption(caption);
		btnButton.setRmFormButtonType(RmFormButtonType.UNDEFINED);
		this.getRmFormButtons().add(btnButton);
		
		this.updateContent();
		
		return btnButton;
	}
	
	public RmFormButtonBase addMessageOkButton() {
		RmFormButtonBase btnButton = new RmFormButtonBase();
		btnButton.setCaption("OK");
		btnButton.setCloseWindow(true);
		btnButton.setRmFormButtonType(RmFormButtonType.MESSAGEOK);
		this.getRmFormButtons().add(btnButton);
		
		this.updateContent();
		
		return btnButton;
	}

	public RmFormButtonBase addConfirmButton() {
		return addConfirmButton("Ok");
	}
	
	public RmFormButtonBase addConfirmButton(String caption) {
		RmFormButtonBase btnButton = new RmFormButtonBase();
		btnButton.setCaption(caption);
		//btnButton.setCloseWindow(true);
		btnButton.setRmFormButtonType(RmFormButtonType.CONFIRM);
		this.getRmFormButtons().add(btnButton);
		
		this.updateContent();
		
		return btnButton;
	}
}
