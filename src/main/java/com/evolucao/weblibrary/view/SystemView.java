package com.evolucao.weblibrary.view;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.LinkButton;
import com.evolucao.rmlibrary.ui.RecordButton;
import com.evolucao.rmlibrary.ui.model.OptionType;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.MyUI;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HasComponents.ComponentDetachEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;

public class SystemView extends VerticalLayout {
	public String uid = null;
    TabSheet tabSheet = new TabSheet();
    HashMap<String, Tab> tabList = new HashMap<String, Tab>();
    VerticalLayout main = new VerticalLayout();
    public String teste = "";
    
    public void setTabList(HashMap<String, Tab> tabList) {
    	this.tabList = tabList;
    }
    public HashMap<String, Tab> getTabList() {
    	return this.tabList;
    }
    
    public void setTabSheet(TabSheet tabSheet) {
    	this.tabSheet = tabSheet;
    }
    public TabSheet getTabSheet() {
    	return this.tabSheet;
    }

    public SystemView() {
    	this.uid =UUID.randomUUID().toString().toUpperCase();
    	System.out.println("Criou SystemView " + uid);
    	
    	this.removeAllComponents();
    	
    	this.getTabSheet().addComponentDetachListener(new ComponentDetachListener() {
			@Override
			public void componentDetachedFromContainer(ComponentDetachEvent event) {
				tabList.remove(event.getDetachedComponent().getCaption());
			}
		});
    	
		setResponsive(true);
		setSizeFull();
		setMargin(false);
		setSpacing(false);
		addStyleName("main-container");
		{
			HorizontalLayout header = new HorizontalLayout();
			header.addStyleName("header-menu");
			header.setWidth("100%");
			header.addStyleName("header");
			{
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				Table tblParametros = ui.getDatabase().loadTableByName("parametros");
				tblParametros.select("logofilename");
				tblParametros.loadData();
				
				Link iconic = new Link(null, new ExternalResource(""));
				iconic.addStyleName("logo");
				String logoFileName = tblParametros.getString("logofilename");
				if ((logoFileName!=null) && (!logoFileName.isEmpty())) {
					iconic.setIcon(new ThemeResource(tblParametros.getString("logofilename")));
				}
				else {
					iconic.setIcon(new ThemeResource("imagens/logo-prosaude.png"));
				}
				header.addComponent(iconic);
				header.setExpandRatio(iconic, 1);

				LinkButton logout = new LinkButton("SAIR DO SISTEMA");
				header.addComponent(logout);
				logout.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						ApplicationUI ui = (ApplicationUI) UI.getCurrent();
						ui.login = new SimpleRecord();
						UI.getCurrent().getPage().reload();
					}
				});
			}
			addComponent(header);

			VerticalLayout body = new VerticalLayout();
			body.setSizeFull();
			body.setMargin(false);
			body.addStyleName("body");
			addComponent(body);
			setExpandRatio(body, 1);
			{
				HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
				horizontalSplitPanel.addStyleName("horizontal-split-panel");
				horizontalSplitPanel.setHeight("100%");
				body.addComponent(horizontalSplitPanel);
				{
					Panel pnlMenu = new Panel();
					pnlMenu.addStyleName("pnlmenu");
					pnlMenu.setHeight("100%");
					horizontalSplitPanel.setFirstComponent(pnlMenu);
					{
						VerticalLayout menu = new VerticalLayout();
						menu.setMargin(false);
						menu.setSpacing(false);
						pnlMenu.setContent(menu);
						{
							HorizontalLayout menuHead = new HorizontalLayout();
							menuHead.addStyleName("menu-head");
							menu.addComponent(menuHead);
							{
								Label lblMenuTitle = new Label("Menu");
								lblMenuTitle.addStyleName("menu-title");
								menuHead.addComponent(lblMenuTitle);
							}
							
							Accordion menuAccordion = new Accordion();
							menuAccordion.setHeight(100.0f, Unit.PERCENTAGE);
							menu.addComponent(menuAccordion);

							//ApplicationUI ui = (ApplicationUI) UI.getCurrent();
							MyUI ui = (MyUI) UI.getCurrent();
							Database database = ui.database;
							try	{
								
								String comando = "";
								//comando +="select * ";
								//comando +="from menu ";
								//comando +="where (menu.projectName='" + ui.getProjectName() +"')";
								//comando +="order by menu.ordem";
								
								comando += "select * ";
								comando += "from sysoptions ";
								comando += "where (sysoptions.projectname='" + ui.getProjectName() + "') ";
								comando += "order by sysoptions.codiopti";
								
								database.openConnection();
								ResultSet rsSysOptions = database.executeSelect(comando);
								VerticalLayout vl = null;

								Table tblPermissions = ui.database.loadTableByName("syspermissions");
								//ui.login.getString("uidgrupo")
								
								Table tblGroups = ui.database.loadTableByName("sysgroups");
								tblGroups.select("*");
								tblGroups.setFilter("projectname", ui.getProjectName());
								tblGroups.setFilter("uid", ui.login.getString("uidgrupo"));
								tblGroups.loadData();
								
								while (rsSysOptions.next()) {
									if (rsSysOptions.getString("codiopti").equalsIgnoreCase("2.01")) {
										System.out.println("teste");
									}
									
									if (!tblGroups.getString("administrador").equalsIgnoreCase("S")) {
										tblPermissions.select("*");
										tblPermissions.setFilter("projectname", ui.getProjectName());
										tblPermissions.setFilter("uidgroup", ui.login.getString("uidgrupo"));
										tblPermissions.setFilter("codiopti", rsSysOptions.getString("codiopti"));
										tblPermissions.loadData();
									}
									
									if ((tblGroups.getString("administrador").equalsIgnoreCase("S")) || (!tblPermissions.eof())) {
										OptionType optionType = OptionType.values()[Integer.valueOf(rsSysOptions.getString("optiontype"))];
										
										if (optionType==OptionType.GROUPOPTION) {
											
										}
										else {
											if ((tblGroups.getString("administrador").equalsIgnoreCase("S")) || 
												(tblPermissions.getString("optaccess").equalsIgnoreCase("S"))) {
												if (rsSysOptions.getInt("grau")==1) {
													vl = new VerticalLayout();
													vl.setSpacing(false);
													menuAccordion.addTab(vl, rsSysOptions.getString("textoption"));
												}
												else {
													if (vl!=null) {
														RecordButton button = new RecordButton(rsSysOptions.getString("textoption"));
														button.getRecord().setValue("optioncommand", rsSysOptions.getString("optioncommand"));
														button.addStyleName("accordion-option");
														vl.addComponent(button);
														button.addClickListener(new ClickListener() {
															@Override
															public void buttonClick(ClickEvent event) {
																RecordButton recordButton = (RecordButton) event.getButton();
																ApplicationUI ui = (ApplicationUI) UI.getCurrent();
																ui.database.executeComandByName(recordButton.getString("optioncommand"));
															}
														});
													}
												}
											}
										}
									}
								}
							}
							catch (Exception e) {
								System.out.println(e.getMessage());
							}
							finally {
								database.closeConnection();
							}
						}
					}

					Panel pnlConteudo = new Panel();
					pnlConteudo.addStyleName("pnlconteudo");
					pnlConteudo.setHeight("100%");
					horizontalSplitPanel.setSecondComponent(pnlConteudo);
					{
						VerticalLayout conteudo = new VerticalLayout();
						conteudo.setMargin(false);
						{
				            tabSheet.addStyleName("tabsheet");
				            conteudo.addComponent(tabSheet);
				            pnlConteudo.setContent(tabSheet);
				            tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
				            tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
				            tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

				            VerticalLayout main = new VerticalLayout();
				            
				            addTab(main, "Principal", false);
						}
					}
				}
				horizontalSplitPanel.setSplitPosition(250, Unit.PIXELS);
			}
			
			HorizontalLayout footer = new HorizontalLayout();
			footer.addStyleName("footer");
			//footer.addComponent(new Label("footer content"));
			addComponent(footer);
		}
	}
    
    public Tab addTab(Component component, String tabTitle, boolean closable) {
    	Tab tab = this.getTabList().get(tabTitle);
    	if (tab==null) {
            final VerticalLayout layout = new VerticalLayout();
            layout.setCaption(tabTitle);
            layout.setSizeFull();
            layout.addStyleName("tabcontainer");
            layout.setMargin(true);

            tab = getTabSheet().addTab(layout, tabTitle);
            if (closable) {
                tab.setClosable(true);
            }
            
            getTabSheet().setSelectedTab(tab);
            layout.addComponent(component);
            this.getTabList().put(tabTitle, tab);
    	}
    	else {
    		getTabSheet().setSelectedTab(tab);
    	}
        
        return tab;
    }
}
