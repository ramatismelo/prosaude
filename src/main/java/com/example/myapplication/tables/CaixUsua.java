package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class CaixUsua {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("caixusua");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("caixusua");
				tblTabela.addField("uidcaixa", FieldType.VARCHAR, 50);
				tblTabela.addField("usuario", FieldType.VARCHAR, 50);
				tblTabela.setPrimaryKey("uidcaixa, usuario");
				tblTabela.setOrder("usuario");
				
				tblTabela.addIndex("uidcaixa_usuario", "uidcaixa, usuario");
				
				tblTabela.fieldByName("usuario").addComboBox("sysusers", "login", "login", "login");
				
				/*
				ForeingSearch foreingSearch = tblTabela.fieldByName("usuario").addForeingSearch();
				foreingSearch.setTargetRmGridName("sysusers");
				foreingSearch.setTargetIndexName("login");
				foreingSearch.setRelationship("usuario");
				foreingSearch.setReturnFieldName("login");
				foreingSearch.setTitle("Pesquisa por usuários:");
				foreingSearch.setOrder("login");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("usuario").addExistenceCheck("sysusers", "login", "usuario", "Usuários inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("usuario", event.getTargetTable().getString("login"));
					}
				});
				*/
				
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("caixusua");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("caixusua");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("usuario", "Usuário", 150d, 1);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de usuários:");
				controlForm.setWidth(700d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("caixusua", "Controle de usuarios.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("usuario", "Usuário", 150d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por usuários:");
				controlForm.setWidth(700d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("caixusua", "Controle de usuarios.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("usuario", "Usuário", 150d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("caixusua");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("caixusua");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Usuarios do caixas", true);
			}
		});
	}
}
