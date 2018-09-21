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
import com.evolucao.rmlibrary.database.events.table.BeforePostEvent;
import com.evolucao.rmlibrary.database.events.table.BeforePostEvent.BeforePostEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class Agenda2 {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("agenda2");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("agenda");
				tblTabela.addField("uidmedico", FieldType.VARCHAR, 50);
				tblTabela.addField("diasemana", FieldType.VARCHAR, 10);
				tblTabela.addField("descdiasemana", FieldType.VARCHAR, 10);
				tblTabela.addField("horainic", FieldType.VARCHAR, 5);
				tblTabela.addField("horaterm", FieldType.VARCHAR, 5);
				tblTabela.addField("numeatend", FieldType.INTEGER, 10);
				tblTabela.addField("numereto", FieldType.INTEGER, 10);
				tblTabela.setPrimaryKey("uid");
				tblTabela.setOrder("diasemana");
				
				tblTabela.addIndex("uidmedico_diasemana", "uidmedico, diasemana");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("agenda2");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				// Tabelas filhas precisam ter a capacidade de receber do pai a tabela
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("agenda");
				}

				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				//rmGrid.setHeight("200px");
				rmGrid.addField("descdiasemana", "Dia da semana", 120d, 1);
				rmGrid.addField("horainic", "Início", 90d);
				rmGrid.addField("horaterm", "Término", 90d);
				rmGrid.addField("numeatend", "Atendimentos", 100d);
				rmGrid.addField("numereto", "Retornos", 100d);
				rmGrid.setShowButtonSet(false);
				rmGrid.setShowButtonSetBottom(false);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção da agenda:");
				controlForm.setWidth(600d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Programação da agenda:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("diasemana", "Dia da semana", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("horainic", "Inicio do atendimento", 100d, 1);
					controlForm.addField("horaterm", "Termino do atendimento", 100d, 1);
					controlForm.addField("numeatend", "No. de atendimento", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por Médicos:");
				controlForm.setWidth(600d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa na agenda:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("diasemana", "Dia da semana", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("horainic", "Inicio do atendimento", 100d, 1);
					controlForm.addField("horaterm", "Termino do atendimento", 100d, 1);
					controlForm.addField("numeatend", "No. de atendimento", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("agenda2");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("agenda2");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Agenda2", true);
			}
		});
	}
}
