package com.evolucao.weblibrary.tables;

import java.sql.ResultSet;
import java.util.List;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.AllowLike;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterCheckTableEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterCheckTableEvent.AfterCheckTableEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlBase;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.ControlPermissionControl;
import com.evolucao.rmlibrary.ui.controller.events.AfterSaveButtonClickEvent;
import com.evolucao.rmlibrary.ui.controller.events.AfterSaveButtonClickEvent.AfterSaveButtonClickEventListener;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.MyUI;
import com.vaadin.ui.UI;

public class SysGroups {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("sysgroups");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("sysgroups");
				tblTabela.addField("projectname", FieldType.VARCHAR, 50);
				//tblTabela.addField("idgrupo", FieldType.VARCHAR, 10);
				tblTabela.addField("descricao", FieldType.VARCHAR, 50);
				tblTabela.addField("padrao", FieldType.BOOLEAN, 10);
				tblTabela.addField("administrador", FieldType.VARCHAR, 1);
				tblTabela.setPrimaryKey("uid");
				
				//tblTabela.addIndex("projectname_idgrupo", "projectname, idgrupo");
				tblTabela.addIndex("projectname_uidgrupo", "projectname, uidgrupo");
				tblTabela.addIndex("uid", "uid");
				
				Table tblPermissoes = database.loadTableByName("permissoes");
				tblTabela.addTableChild(tblPermissoes, "uidgrupo", false);
				
				tblTabela.setAllowLike("projectname", AllowLike.NONE);
				//tblTabela.setAllowLike("idgrupo", AllowLike.NONE);
				
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
								
								event.getTable().insert();
								event.getTable().setValue("projectname", myUI.getProjectName());
								event.getTable().setValue("descricao", "ADMINISTRADOR");
								event.getTable().setValue("administrador", "S");
								event.getTable().execute();
							}
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("sysgroups");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("sysgroups");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				//rmGrid.addField("idgrupo", "Código", 150d);
				rmGrid.addField("descricao", "Descricao", 100d, 1);
				rmGrid.addField("padrao", "Padrão", 100d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de grupos de usuários:");
				controlForm.setWidth(1200d);
				controlForm.setHeight(720d);
				
				controlForm.addNewLine();
				controlForm.addSection("grupusua", "Controle de grupos de usuários.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					//controlForm.addField("idgrupo", "Código", 150d);
					controlForm.addField("descricao", "Descrição", 100d, 1);
					
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("permissoes", "Permissoes de acesso para o grupo:", SectionState.MAXIMIZED, 600d);
					{
						ControlPermissionControl permissionControl = new ControlPermissionControl();
						controlForm.addOnDefaultContainer(permissionControl);
						controlForm.addAfterSaveButtonClickEventListener(new AfterSaveButtonClickEventListener() {
							@Override
							public void AfterSaveButtonClick(AfterSaveButtonClickEvent event) {
								List<ControlBase> lista = event.getControlForm().getChildrensByClass(ControlPermissionControl.class);
								if (lista.size()>0) {
									ControlPermissionControl permissionControl = (ControlPermissionControl) lista.get(0); 
									permissionControl.savePerssion();
								}
							}
						});
					}
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por caixas:");
				controlForm.setWidth(800d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("grupusua", "Controle de grupos de usuários.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					//controlForm.addField("idgrupo", "Código", 150d);
					controlForm.addField("descricao", "Descrição", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("sysgroups");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("sysgroups");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Grupos de usuários", true);
			}
		});
	}
}
