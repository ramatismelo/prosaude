package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class Caixas {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("caixas");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table table = event.getTable();
				table.setTableName("caixas");
				table.addField("codicaix", FieldType.VARCHAR, 5);
				table.addField("descricao", FieldType.VARCHAR, 50);
				table.setPrimaryKey("codicaix");
				table.setOrder("descricao");
				
				table.addIndex("codicaix", "codicaix");
				table.addIndex("descricao", "descricao");

				Table tblCaixUsua = database.loadTableByName("caixusua");
				table.addTableChild(tblCaixUsua, "uidcaixa", false);
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("caixas");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("caixas");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("codicaix", "Código", 150d);
				rmGrid.addField("descricao", "Descricao", 100d, 1);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de caixas:");
				controlForm.setWidth(800d);
				controlForm.setHeight(700d);
				
				controlForm.addNewLine();
				controlForm.addSection("caixas", "Controle de caixas.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codicaix", "Código", 150d);
					controlForm.addField("descricao", "Descrição", 100d, 1);
					
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("caixusua", "Relação de usuários do caixa:", SectionState.MAXIMIZED, 400d);
					{
						controlForm.addRmGrid("caixusua");
					}
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por caixas:");
				controlForm.setWidth(800d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("caixas", "Controle de caixas.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codicaix", "Código", 150d);
					controlForm.addField("descricao", "Descrição", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("caixas");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("caixas");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Caixas", true);
			}
		});
	}
}
