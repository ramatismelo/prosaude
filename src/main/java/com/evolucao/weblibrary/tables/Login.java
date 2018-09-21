package com.evolucao.weblibrary.tables;

import java.util.Date;
import java.util.UUID;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.AllowLike;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlButton;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent.ControlButtonClickEventListener;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.Button.ClickShortcut;
import com.vaadin.ui.UI;

public class Login {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("login");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblLogin = event.getTable();
				tblLogin.setManagedTable(false);
				tblLogin.setTableName("login");
				tblLogin.addField("login", FieldType.VARCHAR, 50);
				tblLogin.addField("senha", FieldType.VARCHAR, 50);
				tblLogin.fieldByName("senha").setPassword(true);
				
				tblLogin.fieldByName("login").setRequired(true);
				tblLogin.fieldByName("senha").setRequired(true);
				
				tblLogin.fieldByName("login").setAllowLike(AllowLike.NONE);
				tblLogin.fieldByName("senha").setAllowLike(AllowLike.NONE);

				tblLogin.fieldByName("senha").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						ApplicationUI ui = (ApplicationUI) UI.getCurrent();
						Table tblSysUsers = ui.database.loadTableByName("sysusers");
						tblSysUsers.select("nome");
						tblSysUsers.setFilter("projectname", ui.getProjectName());
						tblSysUsers.setFilter("login", tblLogin.fieldByName("login").getString());
						tblSysUsers.setFilter("senha", tblLogin.fieldByName("senha").getString());
						
						try {
							ui.database.openConnection();
							
							tblSysUsers.loadData();
							if (tblSysUsers.eof()) {
								event.setValid(false);
								event.setValidationAdvice("Login ou senha inválido!");
							}
						}
						finally {
							ui.database.closeConnection();
						}
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("login");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("login");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("login", "Login", 150d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de grupos de usuários:");
				controlForm.setWidth(800d);
				controlForm.setHeight(720d);

				controlForm.addNewLine();
				controlForm.addField("login", "Login", 500d);
				
				controlForm.addNewLine();
				controlForm.addField("senha", "Senha", 500d);
				
				controlForm.addNewLine();
				controlForm.addRequiredMessage();
				
				controlForm.addNewLine();
				controlForm.addButtonSet();
				ControlButton controlButton = controlForm.addRightButton("Entrar no sistema");
				controlButton.setClickShortcut(KeyCode.ENTER);
				controlButton.addControlButtonClickEventListener(new ControlButtonClickEventListener() {
					@Override
					public void onControlButtonClick(ControlButtonClickEvent event) {
						Table tblLogin = event.getRmForm().getTable();
						
						if (tblLogin.validate()) {
							ApplicationUI ui = (ApplicationUI) UI.getCurrent();
							//Database database = (Database) VaadinSession.getCurrent().getAttribute("database");
							try {
								ui.database.openConnection();
								Table tblSysUsers = ui.database.loadTableByName("sysusers");
								tblSysUsers.select("senha, uidgrupo");
								tblSysUsers.setFilter("login", tblLogin.getString("login"));
								tblSysUsers.setFilter("projectname", ui.getProjectName());
								tblSysUsers.loadData();
								if (!tblSysUsers.eof()) {
									if (!tblSysUsers.getString("senha").equals(tblLogin.getString("senha"))) {
										System.out.println("Usuário ou senha invalidos!");
									}
									else {
										System.out.println("Login realizado com sucesso!");
										String uidUsuario = tblSysUsers.getString("uid");
										String passport = UUID.randomUUID().toString().toUpperCase();

										SimpleRecord simpleRecord = new SimpleRecord();
										simpleRecord.setValue("uid", tblSysUsers.getString("uid"));
										simpleRecord.setValue("passport", passport);
										simpleRecord.setValue("uidgrupo", tblSysUsers.getString("uidgrupo"));
										
										tblSysUsers.update();
										tblSysUsers.setValue("passport", passport);
										tblSysUsers.setValue("ultiaces", new Date());
										tblSysUsers.setFilter("uid", uidUsuario);
										tblSysUsers.execute();
										
										//Utils.saveCookie("login", simpleRecord.encode());
										//VaadinSession.getCurrent().setAttribute("login", simpleRecord);
										//Zapdoctor2UI.Instance.login = simpleRecord;
										//Zapdoctor2UI.Instance.updateContent();
										
										ui.login = simpleRecord;
										
										//SystemView systemViewNew = new SystemView();
										//ui.getSystemView() = systemViewNew;
										
										ui.getDatabase().getUserSystemLogged().setUserName(tblLogin.getString("login"));
										
										WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
										String ipAddress = webBrowser.getAddress();
										if (ipAddress != null) {
											ui.getDatabase().getUserSystemLogged().setIpAddress(ipAddress);
										}											
										
										ui.updateContent();
									}
								}
							}
							catch (Exception e) {
								System.out.println(e.getMessage());
							}
							finally {
								ui.database.closeConnection();
							}
						}
					}
				});
				
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("login");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("login");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Grupos de usuários", true);
			}
		});
	}
}
