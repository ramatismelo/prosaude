package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.InternalSearch;
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
import com.evolucao.rmlibrary.ui.controller.ControlContentField;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class AgendaMedicos {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("agendaMedicos");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setManagedTable(false);
				tblTabela.setTableName("medicos");
				tblTabela.addField("codimedi", FieldType.INTEGER, 10);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("situacao", FieldType.VARCHAR, 1);
				tblTabela.addField("tipotota", FieldType.VARCHAR, 1);
				tblTabela.setPrimaryKey("codimedi");
				tblTabela.setOrder("nome");
				
				tblTabela.addIndex("codimedi", "codimedi");
				
				tblTabela.addJoin("espemedi", "uidmedico", "uid");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("agendaMedicos");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("agendaMedicos");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("nome", "Nome do médico", 100d, 1);
				
				// Incluido para ser chamado no select porem nao sera renderizado no grid
				ControlContentField ccfCodiMedi = rmGrid.addField("codimedi", "Cód.Médico", 100d);
				ccfCodiMedi.setVisible(false);
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("agendaMedicos");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("agendaMedicos");
				rmGrid.updateContent();
			}
		});
	}
}
