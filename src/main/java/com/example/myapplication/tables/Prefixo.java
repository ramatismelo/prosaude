package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class Prefixo {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("prefixo");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("prefixo");
				tblTabela.addField("prefixo", FieldType.VARCHAR, 2);
				tblTabela.addField("descricao", FieldType.VARCHAR, 50);
				tblTabela.setPrimaryKey("prefixo");
				
				tblTabela.setOrder("prefixo");
				
				tblTabela.addIndex("prefixo", "prefixo");
				tblTabela.addIndex("descricao", "descricao");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("prefixo");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("prefixo");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setHeight("550px");
				rmGrid.setLimit(10);
				rmGrid.addField("prefixo", "prefixo", 150D);
				rmGrid.addField("descricao", "Descrição", 150d, 1);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de PREFIXO:");
				controlForm.setWidth(600d);
				controlForm.setHeight(280d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Cadastro de PREFIXOS.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("prefixo", "Prefixo", 100d);
					controlForm.addField("descricao", "Descrição", 200d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por PREFIXOS:");
				controlForm.setWidth(600d);
				controlForm.setHeight(280d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa por PREFIXOS.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("prefixo", "Prefixo", 100d);
					controlForm.addField("descricao", "Descrição", 200d, 1);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("prefixo");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrdFornecedores = database.loadRmGridByName("prefixo");
				rmGrdFornecedores.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrdFornecedores, "Prefixo", true);
			}
		});
	}
}
