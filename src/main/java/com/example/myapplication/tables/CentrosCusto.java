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

public class CentrosCusto {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("centroscusto");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table table = event.getTable();
				table.setTableName("centroscusto");
				table.addField("descricao", FieldType.VARCHAR, 50);
				table.setPrimaryKey("uid");
				table.setOrder("descricao");
				
				table.addIndex("descricao", "descricao");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("centroscusto");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("centroscusto");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("descricao", "Descricao", 100d, 1);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de centros de custos:");
				controlForm.setWidth(800d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("centocustos", "Controle de centros de custos.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("descricao", "Descrição", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por centros de custos:");
				controlForm.setWidth(800d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("centrocustos", "Controle de centros de custos.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("descricao", "Descrição", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("centroscusto");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("centroscusto");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Centros de custos", true);
			}
		});
	}
}
