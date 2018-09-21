package com.evolucao.weblibrary.tables;

import java.sql.ResultSet;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.AllowLike;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterCheckTableEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.AfterCheckTableEvent.AfterCheckTableEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.enumerators.CapitalizationType;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.MyUI;
import com.vaadin.ui.UI;

public class SysUsers {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("sysusers");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("sysusers");
				tblTabela.addField("uid", FieldType.VARCHAR, 50);
				tblTabela.addField("login", FieldType.VARCHAR, 50);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("senha", FieldType.VARCHAR, 50);
				tblTabela.addField("uidtipousuario", FieldType.VARCHAR, 50);
				tblTabela.addField("ultiaces", FieldType.DATETIME, 10);
				tblTabela.addField("ipultiaces", FieldType.VARCHAR, 11);
				tblTabela.addField("passport", FieldType.VARCHAR, 50);
				tblTabela.addField("uidgrupo", FieldType.VARCHAR, 50);
				tblTabela.addField("descuidgrupo", FieldType.VARCHAR, 50);
				tblTabela.addField("projectname", FieldType.VARCHAR, 50);
				tblTabela.setPrimaryKey("uid");
				
				tblTabela.setPassword("senha", true);
				
				tblTabela.setAllowLike("projectname", AllowLike.NONE);
				tblTabela.setAllowLike("login", AllowLike.NONE);
				tblTabela.setAllowLike("senha", AllowLike.NONE);
				
				tblTabela.addJoin("sysgroups", "uid", "uidgrupo");
				tblTabela.fieldByName("descuidgrupo").setExpression("sysgroups.descricao");
				tblTabela.fieldByName("descuidgrupo").setReadOnly(true);
				
				tblTabela.addIndex("projectname_login", "projectname, login");
				
				tblTabela.fieldByName("nome").setCapitalizationType(CapitalizationType.NONE);
				
				tblTabela.fieldByName("login").setRequired(true);
				tblTabela.fieldByName("nome").setRequired(true);
				tblTabela.fieldByName("uidgrupo").setRequired(true);
				tblTabela.fieldByName("senha").setRequired(true);
				
				MyUI myUI = (MyUI) UI.getCurrent();
				tblTabela.setMainFilter("projectname", myUI.getProjectName());
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						MyUI myUI = (MyUI) UI.getCurrent();
						event.getTable().setValue("projectname", myUI.getProjectName());
					}
				});
				
				tblTabela.addAfterCheckTableEventListener(new AfterCheckTableEventListener() {
					@Override
					public void onAfterCheckTable(AfterCheckTableEvent event) {
						try {
							ResultSet resultSet = event.getTable().getDatabase().executeSelect("select count(*) as recordcount from " + event.getTable().getTableName());
							if ((resultSet.next()) && (resultSet.getInt("recordcount")==0)) {
								MyUI myUI = (MyUI) UI.getCurrent();
								
								ResultSet rsSysGroups = event.getTable().getDatabase().executeSelect("select uid from sysgroups where (sysgroups.projectname='" + myUI.getProjectName() +"') and (sysgroups.administrador='S')");
								if (rsSysGroups.next()) {
									event.getTable().insert();
									event.getTable().setValue("projectname", myUI.getProjectName());
									event.getTable().setValue("login", "ADMINISTRADOR");
									event.getTable().setValue("nome", "Administrador do sistema");
									event.getTable().setValue("senha", "1234");
									event.getTable().setValue("uidgrupo", rsSysGroups.getString("uid"));
									event.getTable().execute();
								}
							}
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				});
				
				tblTabela.fieldByName("uidgrupo").addComboBox("sysgroups", "descricao", "descricao", "uid");

				/*
				ExistenceCheck existenceCheck = tblTabela.fieldByName("idgrupo").addExistenceCheck("grupusua", "projectName_idgrupo", "projectName, idgrupo", "Grupo de usuários inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("descidgrupo", event.getTargetTable().getString("descricao"));
						event.getSourceTable().setValue("uidGrupo", event.getTargetTable().getString("uid"));
					}
				});
				*/
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("sysusers");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("sysusers");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("login", "Login", 200d);
				rmGrid.addField("nome", "Nome", 200d, 1);
				rmGrid.addField("descuidgrupo", "Grupo do usuário", 200d);
				rmGrid.addField("ultiaces", "Último Acesso", 200d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de usuários:");
				controlForm.setWidth(600d);
				controlForm.setHeight(520d);
				
				controlForm.addNewLine();
				controlForm.addSection("usuarios", "Controle de usuários.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("login", "Login", 150d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("nome", "Nome do usuário", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("uidgrupo", "Grupo do usuário", 150d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("senha", "Senha", 100d, 1);
					
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por caixas:");
				controlForm.setWidth(600d);
				controlForm.setHeight(520d);
				
				controlForm.addNewLine();
				controlForm.addSection("usuarios", "Controle de usuários.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("login", "Login", 150d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("nome", "Nome do usuário", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("uidgrupo", "Grupo do usuário", 150d, 1);

					controlForm.addNewLine();
					controlForm.addField("senha", "Senha", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("sysusers");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("sysusers");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Usuários", true);
			}
		});
	}
}
