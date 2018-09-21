package com.evolucao.weblibrary.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.InternalSearch;
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
import com.evolucao.rmlibrary.database.events.table.BeforePostEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.events.table.BeforePostEvent.BeforePostEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.enumerators.CapitalizationType;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class Menu {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("menu");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("menu");
				tblTabela.addField("projectName", FieldType.VARCHAR, 50);
				tblTabela.addField("idmenu", FieldType.VARCHAR, 50);
				tblTabela.addField("texto", FieldType.VARCHAR, 50);
				tblTabela.addField("ordem", FieldType.VARCHAR, 50);
				tblTabela.addField("parent_idmenu", FieldType.VARCHAR, 50);
				tblTabela.addField("classname", FieldType.VARCHAR, 50);
				tblTabela.setPrimaryKey("uid");
				tblTabela.setOrder("ordem");
				
				tblTabela.fieldByName("texto").setCapitalizationType(CapitalizationType.NONE);
				tblTabela.fieldByName("classname").setCapitalizationType(CapitalizationType.NONE);
				tblTabela.fieldByName("ordem").setCapitalizationType(CapitalizationType.NONE);
				
				tblTabela.setAllowLike("projectName", AllowLike.NONE);
				tblTabela.setAllowLike("idmenu", AllowLike.NONE);
				tblTabela.setAllowLike("ordem", AllowLike.NONE);
				
				tblTabela.addIndex("projectName_ordem", "projectName, ordem");
				tblTabela.addIndex("projectName_idmenu", "projectName, idmenu");
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("projectName", "prosaude2");
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("menu");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				// Tabelas filhas precisam ter a capacidade de receber do pai a tabela
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("menu");
				}

				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(100);
				rmGrid.addField("idmenu", "IdMenu", 100d);
				rmGrid.addField("texto", "Texto", 200d, 1);
				rmGrid.addField("ordem", "Ordem", 150d);
				rmGrid.addField("parent_idmenu", "Parent_IdMenu", 100d);
				rmGrid.addField("classname", "ClassName", 180d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de opcoes:");
				controlForm.setWidth(600d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Programação de opcoes:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("idmenu", "IdMenu", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("texto", "Texto", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("ordem", "Ordem", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("parent_idmenu", "Parent_IdMenu", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("classname", "ClassName", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por Médicos:");
				controlForm.setWidth(600d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Programação de opcoes:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("idmenu", "IdMenu", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("texto", "Texto", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("ordem", "Ordem", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("parent_idmenu", "Parent_IdMenu", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("classname", "ClassName", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("menu");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("menu");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Menu", true);
			}
		});
	}
}
