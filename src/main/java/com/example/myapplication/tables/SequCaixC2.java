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

public class SequCaixC2 {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("sequcaixc2");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("sequcaixc2");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.setPrimaryKey("sequencia");
				
				tblTabela.setAutoIncrement("sequencia", true);
				tblTabela.fieldByName("sequencia").setReadOnly(true);
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("sequcaixc2");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("sequcaixc2");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("sequencia", "Sequencia", 150d, 1);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de sequencia:");
				controlForm.setWidth(700d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("sequcaix", "Controle de sequencia do caixa.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequencia", 150d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Manutenção de sequencia:");
				controlForm.setWidth(700d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("sequcaix", "Controle de sequencia do caixa.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequencia", 150d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("sequcaixc2");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("sequcaixc2");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Sequencias do caixa c2", true);
			}
		});
	}
}
