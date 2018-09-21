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
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent.AfterValidateOnEditEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class SaidCaix {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("saidcaix");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table table = event.getTable();
				table.setTableName("saidcaix");
				
				table.addField("sequencia", FieldType.INTEGER, 10);
				table.addField("data", FieldType.DATE, 10);
				table.addField("codicaix", FieldType.VARCHAR, 5);
				table.addField("desccodicaix", FieldType.VARCHAR, 50);
				table.addField("valor", FieldType.DOUBLE, 10);
				table.addField("tiposaid", FieldType.VARCHAR, 1);
				table.addField("desctiposaid", FieldType.VARCHAR, 50);
				table.addField("descricao", FieldType.TEXT, 250);
				table.addField("uidcentcust", FieldType.VARCHAR, 50);
				table.addField("descuidcentcust", FieldType.VARCHAR, 50);
				table.setPrimaryKey("sequencia");

				table.addJoin("caixas", "codicaix", "codicaix");
				table.fieldByName("desccodicaix").setExpression("caixas.descricao");
				
				table.addJoin("centroscusto", "uid", "uidcentcust");
				table.fieldByName("descuidcentcust").setExpression("centroscusto.descricao");
				
				table.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("data", Utils.getJustDate(new Date()));
					}
				});
				
				table.setAutoIncrement("sequencia", true);
				table.setOrder("sequencia desc");

				table.fieldByName("data").setRequired(true);
				table.fieldByName("codicaix").setRequired(true);
				table.fieldByName("valor").setRequired(true);
				table.fieldByName("tiposaid").setRequired(true);
				table.fieldByName("descricao").setRequired(true);
				
				table.fieldByName("sequencia").setReadOnly(true);
				table.fieldByName("desccodicaix").setReadOnly(true);
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SAÍDA NORMAL DO CAIXA");
				internalSearch.addItem("R", "RETIRADA DE VALORES PELA GERENCIA");
				table.fieldByName("tiposaid").setInternalSearch(internalSearch);
				table.fieldByName("desctiposaid").setValueOfInternalSearch("tiposaid");

				table.fieldByName("codicaix").addComboBox("caixas", "descricao", "descricao", "codicaix");
				
				table.fieldByName("uidcentcust").addComboBox("centroscusto", "descricao", "descricao", "uid");
				
				/*
				ForeingSearch foreingSearch = table.fieldByName("codicaix").addForeingSearch();
				foreingSearch.setTargetRmGridName("caixas");
				foreingSearch.setTargetIndexName("codicaix");
				foreingSearch.setRelationship("codicaix");
				foreingSearch.setReturnFieldName("codicaix");
				foreingSearch.setTitle("Pesquisa por CAIXAS:");
				foreingSearch.setOrder("descricao");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				ExistenceCheck existenceCheck = table.fieldByName("codicaix").addExistenceCheck("caixas", "codicaix", "codicaix", "Código do caixa informado é inválido!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodicaix", event.getTargetTable().getString("descricao"));
					}
				});
				*/
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("saidcaix");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("saidcaix");
				}

				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("sequencia", "Sequência", 100d);
				rmGrid.addField("data", "Movimento", 140d);
				rmGrid.addField("valor", "Valor", 100d);
				rmGrid.addField("desctiposaid", "Tipo movimento", 150d);
				rmGrid.addField("descuidcentcust", "Centro de custo", 200d);
				rmGrid.addField("descricao", "Descrição", 100d, 1);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Saídas do caixa:");
				controlForm.setWidth(700d);
				controlForm.setHeight(600d);
				
				controlForm.addNewLine();
				controlForm.addSection("especilidades", "Saídas do caixa.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 100d);
					controlForm.addField("data", "movimento", 150d);
					controlForm.addField("codicaix", "Caixa", 100d, 1);
					//controlForm.addField("desccodicaix", "Caixa", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("tiposaid", "Tipo de movimento", 100d, 1);
					controlForm.addField("valor", "Valor", 150d);
					
					controlForm.addNewLine();
					controlForm.addField("uidcentcust", "Centro de custo", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("descricao", "Descrição", 100d, 220d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por saídas do caixa:");
				controlForm.setWidth(700d);
				controlForm.setHeight(600d);
				
				controlForm.addNewLine();
				controlForm.addSection("especilidades", "Saídas do caixa.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 100d);
					controlForm.addField("data", "movimento", 150d);
					controlForm.addField("codicaix", "Caixa", 100d, 1);
					//controlForm.addField("desccodicaix", "Caixa", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("tiposaid", "Tipo de movimento", 100d, 1);
					controlForm.addField("valor", "Valor", 150d);
					
					controlForm.addNewLine();
					controlForm.addField("uidcentcust", "Centro de custo", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("descricao", "Descrição", 100d, 220d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("saidcaix");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("saidcaix");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Saídas do caixa", true);
			}
		});
	}
}
