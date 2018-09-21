package com.example.myapplication.tables;

import java.util.Date;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
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
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class DevoReci {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("devoreci");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setDebugQuery(true);
				tblTabela.setTableName("devoreci");
				
				tblTabela.addField("numereci", FieldType.INTEGER, 10);
				tblTabela.addField("emissao", FieldType.DATE, 10);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("valor", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("datadevo", FieldType.DATE, 10);
				tblTabela.addField("motivo", FieldType.TEXT, 250);
				tblTabela.setPrimaryKey("numereci");
				tblTabela.setOrder("datadevo desc, numereci");
				
				tblTabela.setRequired("numereci", true);
				tblTabela.setRequired("emissao", true);
				tblTabela.setRequired("nome", true);
				tblTabela.setRequired("valor", true);
				tblTabela.setRequired("datadevo", true);
				tblTabela.setRequired("motivo", true);
				
				tblTabela.fieldByName("emissao").setReadOnly(true);
				tblTabela.fieldByName("nome").setReadOnly(true);
				tblTabela.fieldByName("valor").setReadOnly(true);
				
				tblTabela.addIndex("numereci", "numereci");
				tblTabela.addIndex("datadevo", "datadevo");
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("datadevo", Utils.getJustDate(new Date()));
					}
				});
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("numereci").addForeingSearch();
				foreingSearch.setTargetRmGridName("recibos");
				foreingSearch.setTargetIndexName("sequencia");
				foreingSearch.setRelationship("numereci");
				foreingSearch.setReturnFieldName("sequencia");
				foreingSearch.setTitle("Pesquisa por recibos:");
				foreingSearch.setOrder("sequencia desc");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("numereci").addExistenceCheck("recibos", "sequencia", "numereci", "Recibo inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("numereci", event.getTargetTable().getInteger("sequencia"));
						event.getSourceTable().setValue("emissao", event.getTargetTable().getDate("emissao"));
						event.getSourceTable().setValue("nome", event.getTargetTable().getString("nome"));
						event.getSourceTable().setValue("valor", event.getTargetTable().getDouble("valor"));
						event.getSourceTable().setValue("vlrcusto", event.getTargetTable().getDouble("vlrcusto"));
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("devoreci");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("devoreci");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setHeight("550px");
				rmGrid.setLimit(10);
				
				rmGrid.addField("datadevo", "Devolução", 150d);
				rmGrid.addField("numereci", "No. Recibo", 150D);
				rmGrid.addField("emissao", "Emissão", 150d);
				rmGrid.addField("nome", "Nome do associado", 220d, 1);
				rmGrid.addField("valor", "Valor", 100d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Devolução de recibos:");
				controlForm.setWidth(790d);
				controlForm.setHeight(600d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Devolução de recibos:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("numereci", "No. Recibo", 150d);
					controlForm.addField("emissao", "Emissão", 150d);
					controlForm.addField("nome", "Nome do associado", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("valor", "Valor do recibo", 200d);
					controlForm.addField("datadevo", "Devolução", 150d, 1);

					controlForm.addNewLine();
					controlForm.addField("motivo", "Motivo", 100d, 220d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por associados:");
				controlForm.setWidth(790d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Devolução de recibos:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("numereci", "No. Recibo", 150d);
					controlForm.addField("emissao", "Emissão", 150d);
					controlForm.addField("nome", "Nome do associado", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("valor", "Valor do recibo", 200d);
					controlForm.addField("datadevo", "Devolução", 150d, 1);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("devoreci");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("devoreci");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Devolução de recebimentos", true);
			}
		});
	}
}
