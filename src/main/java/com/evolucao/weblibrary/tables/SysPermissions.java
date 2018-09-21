package com.evolucao.weblibrary.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.AllowLike;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.MyUI;
import com.vaadin.ui.UI;

public class SysPermissions {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("syspermissions");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("syspermissions");
				tblTabela.addField("projectname", FieldType.VARCHAR, 50);
				tblTabela.addField("uidgroup", FieldType.VARCHAR, 50);
				tblTabela.addField("codiopti", FieldType.VARCHAR, 50);
				tblTabela.addField("optaccess", FieldType.VARCHAR, 1);
				tblTabela.addField("optinsert", FieldType.VARCHAR, 1);
				tblTabela.addField("optupdate", FieldType.VARCHAR, 1);
				tblTabela.addField("optdelete", FieldType.VARCHAR, 1);
				
				tblTabela.setPrimaryKey("uid");
				tblTabela.setOrder("projectname, uidgroup, codiopti");
				
				tblTabela.fieldByName("projectname").setAllowLike(AllowLike.NONE);
				tblTabela.fieldByName("codiopti").setAllowLike(AllowLike.NONE);

				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						MyUI myUI = (MyUI) UI.getCurrent();
						event.getTable().setValue("projectname", myUI.getProjectName());
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("syspermisssions");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				// Tabelas filhas precisam ter a capacidade de receber do pai a tabela
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("syspermissions");
				}

				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(100);
				rmGrid.addField("uidgroup", "Uid Grupo", 150d);
				rmGrid.addField("codiopti", "Cód.Opção", 150d);
				rmGrid.addField("optaccess", "Acessar", 100d, 1);
				rmGrid.addField("optinsert", "Adicionar", 100d, 1);
				rmGrid.addField("optupdate", "Alterar", 100d, 1);
				rmGrid.addField("optdelete", "Excluir", 100d, 1);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de permissões:");
				controlForm.setWidth(1200d);
				controlForm.setHeight(800d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Programação de permissões:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("uidgroup", "Uid.Grupo", 100d);
					controlForm.addField("codiopti", "Uid.Opção", 100d);
					controlForm.addField("optaccess", "Acessar", 100d, 1);
					controlForm.addField("optinsert", "Adicionar", 100d, 1);
					controlForm.addField("optupdate", "Alterar", 100d, 1);
					controlForm.addField("optdelete", "Excluir", 100d, 1);
				}
				
				//********************************
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por permissões:");
				controlForm.setWidth(600d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa de permissões:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("uidgroup", "Uid.Grupo", 100d);
					controlForm.addField("codiopti", "Uid.Opção", 100d);
					controlForm.addField("optaccess", "Acessar", 100d, 1);
					controlForm.addField("optinsert", "Adicionar", 100d, 1);
					controlForm.addField("optupdate", "Alterar", 100d, 1);
					controlForm.addField("optdelete", "Excluir", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("syspermissions");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("syspermissions");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Programação de permissões", true);
			}
		});
	}
}
