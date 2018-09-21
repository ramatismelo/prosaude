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

public class Parametros {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("parametros");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("parametros");
				tblTabela.addField("local", FieldType.VARCHAR, 50);
				tblTabela.addField("reportServer", FieldType.VARCHAR, 250);
				tblTabela.addField("newreportserver", FieldType.VARCHAR, 250);
				tblTabela.addField("projectName", FieldType.VARCHAR, 50);
				tblTabela.addField("nomeEmpresa", FieldType.VARCHAR, 50);
				tblTabela.addField("logofilename", FieldType.VARCHAR, 250);
				tblTabela.setPrimaryKey("uid");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("parametros");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("parametros");
				}
				
				RmGrid grdGrid = event.getRmGrid();
				grdGrid.setTable(table);
				grdGrid.setHeight("550px");
				grdGrid.setLimit(10);
				grdGrid.addField("local", "Local", 150D);
				grdGrid.addField("reportServer", "Servidor de relatorios", 250d);
				
				ControlForm controlForm = grdGrid.getForm();
				controlForm.setTitle("Manutenção de Parametros:");
				controlForm.setWidth(600d);
				controlForm.setHeight(450d);
				
				controlForm.addNewLine();
				controlForm.addSection("parametros", "Parametros do sistema.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("local", "Local", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("reposrtServer",  "Servidor de relatórios", 200d);
				}
				
				controlForm = grdGrid.getFormFilter();
				controlForm.setTitle("Pesquisa de Parametros:");
				controlForm.setWidth(600d);
				controlForm.setHeight(450d);
				
				controlForm.addNewLine();
				controlForm.addSection("parametros", "Parametros do sistema.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("local", "Local", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("reposrtServer",  "Servidor de relatórios", 200d);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("parametros");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrdGrid = database.loadRmGridByName("parametros");
				rmGrdGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrdGrid, "Parametros", true);
			}
		});
	}
}
