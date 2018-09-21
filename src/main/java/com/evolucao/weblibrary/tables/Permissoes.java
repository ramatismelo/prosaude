package com.evolucao.weblibrary.tables;

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
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class Permissoes {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("permissoes");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("permissoes");
				tblTabela.addField("projectName", FieldType.VARCHAR, 50);
				tblTabela.addField("uidgrupo", FieldType.VARCHAR, 50);
				tblTabela.addField("uidMenu", FieldType.VARCHAR, 50);
				tblTabela.addField("idgrupo", FieldType.VARCHAR, 10);
				tblTabela.addField("idmenu", FieldType.VARCHAR, 10);
				tblTabela.addField("texto", FieldType.VARCHAR, 50);
				tblTabela.addField("ordem", FieldType.VARCHAR, 50);
				tblTabela.setPrimaryKey("uid");
				tblTabela.setOrder("menu.ordem");
				
				tblTabela.addIndex("projectName_uidgrupo_uidmenu", "projectName, uidgrupo, uidmenu");
				
				tblTabela.setMainFilter("projectName", "prosaude2");

				tblTabela.setAllowLike("projectName", AllowLike.NONE);
				tblTabela.setAllowLike("uidgrupo", AllowLike.NONE);
				tblTabela.setAllowLike("idgrupo", AllowLike.NONE);
				tblTabela.setAllowLike("idmenu", AllowLike.NONE);

				tblTabela.addJoin("menu", "projectName_idmenu", "projectName, idmenu");
				tblTabela.fieldByName("ordem").setExpression("menu.ordem");
				tblTabela.fieldByName("texto").setExpression("menu.texto");
				
				tblTabela.fieldByName("texto").setReadOnly(true);
				tblTabela.fieldByName("ordem").setReadOnly(true);
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						ApplicationUI ui = (ApplicationUI) UI.getCurrent();
						event.getTable().setString("projectName", ui.getProjectName());
					}
				});
				
				/*
				ForeingSearch foreingSearch = tblTabela.fieldByName("idmenu").addForeingSearch();
				foreingSearch.setTargetRmGridName("menu");
				foreingSearch.setTargetIndexName("idmenu");
				foreingSearch.setRelationship("idmenu");
				foreingSearch.setReturnFieldName("idmenu");
				foreingSearch.setTitle("Pesquisa por opções:");
				foreingSearch.setOrder("ordem");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				*/
				
				/*
				ExistenceCheck existenceCheck = tblTabela.fieldByName("idmenu").addExistenceCheck("menu", "idmenu", "idmenu", "Opção informada é inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("texto", event.getTargetTable().getString("texto"));
						event.getSourceTable().setValue("ordem", event.getTargetTable().getString("ordem"));
					}
				});
				*/
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("permissoes");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				// Tabelas filhas precisam ter a capacidade de receber do pai a tabela
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("permissoes");
				}

				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(100);
				rmGrid.addField("idgrupo", "IdGrupo", 100d);
				rmGrid.addField("idmenu", "IdMenu", 100d);
				rmGrid.addField("texto", "Texto", 200d, 1);
				rmGrid.addField("ordem", "Ordem", 150d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de permissões:");
				controlForm.setWidth(600d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Manutenção de permissões:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("idgrupo", "IDGrupo", 120d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("idmenu", "IdMenu", 120d);
					controlForm.addField("texto", "Texto", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("ordem", "Ordem", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por Médicos:");
				controlForm.setWidth(600d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Manutenção de permissões:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("idmenu", "IdMenu", 120d, 1);
					controlForm.addField("texto", "Texto", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("ordem", "Ordem", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("permissoes");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("permissoes");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "permissoes", true);
			}
		});
	}
}
