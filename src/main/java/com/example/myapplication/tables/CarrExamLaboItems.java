package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent.BeforeForeingSearchEventListener;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent.DirectProcessingEventListener;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent.ReverseProcessingEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class CarrExamLaboItems {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("carrexamlaboitems");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("carrexamlaboitems");
				tblTabela.addField("uidexamlabo", FieldType.VARCHAR, 50); // uid do pai
				tblTabela.addField("uidespeforn", FieldType.VARCHAR, 50); // uid do espeforn
				tblTabela.addField("uidfornecedor", FieldType.VARCHAR, 50); // uid do fornecedor
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("desccodiespe", FieldType.VARCHAR, 50);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvenda", FieldType.DOUBLE, 10);
				tblTabela.setPrimaryKey("uid");
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("codiespe").addForeingSearch();
				foreingSearch.setTargetRmGridName("espeforn");
				foreingSearch.setTargetIndexName("uidfornecedor_codiespe");
				foreingSearch.setRelationship("uidfornecedor, codiespe");
				foreingSearch.setReturnFieldName("codiespe");
				foreingSearch.setTitle("Pesquisa por ESPECIALIDADES:");
				foreingSearch.setOrder("especialidades.descricao");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				foreingSearch.addBeforeForeingSearchEventListener(new BeforeForeingSearchEventListener() {
					@Override
					public void onBeforeForeingSearch(BeforeForeingSearchEvent event) {
						event.getTargetTable().setMainFilter("uidfornecedor", event.getSourceTable().getInteger("uidfornecedor"));
					}
				});
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setString("uidfornecedor", event.getTable().getMasterTable().getString("uidfornecedor"));
					}
				});

				/*
				tblTabela.addField("uidfornecedor", FieldType.INTEGER, 10);
				tblTabela.addField("grupo", FieldType.VARCHAR, 50);
				
				tblTabela.setOrder("especialidades.descricao");
				
				tblTabela.addJoin("especialidades", "codiespe", "codiespe");
				tblTabela.addJoin("espeforn", "foreignKey, relationship)
				
				tblTabela.fieldByName("desccodiespe").setExpression("especialidades.descricao");
				
				tblTabela.fieldByName("desccodiespe").setReadOnly(true);
				tblTabela.fieldByName("vlrvenda").setReadOnly(true);
				
				tblTabela.fieldByName("codiespe").setRequired(true);
				
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiespe").addExistenceCheck("especialidades", "codiespe", "codiespe", "Especialidade inválido!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodiespe", event.getTargetTable().getString("descricao"));
						event.getSourceTable().setValue("vlrcusto", event.getTargetTable().getDouble("vlrcusto"));
						event.getSourceTable().setValue("vlrvenda", event.getTargetTable().getDouble("valor"));
					}
				});
				
				tblTabela.addDirectProcessingEventListener(new DirectProcessingEventListener() {
					@Override
					public void onDirectProcessing(DirectProcessingEvent event) {
						// Totalizando os exames no Lancamentos no CarrExamLabo
						Table tblCarrExamLabo = event.getTable().getMasterTable();
						String uidCarrExamLabo = tblCarrExamLabo.getString("uid");
						tblCarrExamLabo.update(uidCarrExamLabo, false);
						tblCarrExamLabo.setInteger("numeexam", tblCarrExamLabo.getInteger("numeexam")+1);
						tblCarrExamLabo.setDouble("vlrcusto", tblCarrExamLabo.getDouble("vlrcusto") + event.getTable().getDouble("vlrcusto"));
						tblCarrExamLabo.setDouble("vlrvenda", tblCarrExamLabo.getDouble("vlrvenda") + event.getTable().getDouble("vlrvenda"));
						tblCarrExamLabo.execute();
						
						// Totalizando os exames no CarrComp
						Table tblCarrComp = event.getTable().getMasterTable().getMasterTable();
						String uidCarrComp = tblCarrComp.getString("uid");
						tblCarrComp.update(uidCarrComp, false);
						tblCarrComp.setDouble("vlrtotal", tblCarrComp.getDouble("vlrtotal")+event.getTable().getDouble("vlrvenda"));
						tblCarrComp.setDouble("vlrcustototal", tblCarrComp.getDouble("vlrcustototal")+event.getTable().getDouble("vlrcusto"));
						tblCarrComp.execute();
					}
				});
				
				tblTabela.addReverseProcessingEventListener(new ReverseProcessingEventListener() {
					@Override
					public void onReverseProcessing(ReverseProcessingEvent event) {
						// Totalizando os exames no Lancamentos no CarrExamLabo
						Table tblCarrExamLabo = event.getTable().getMasterTable();
						String uidCarrExamLabo = tblCarrExamLabo.getString("uid");
						tblCarrExamLabo.update(uidCarrExamLabo, false);
						tblCarrExamLabo.setInteger("numeexam", tblCarrExamLabo.getInteger("numeexam")-1);
						tblCarrExamLabo.setDouble("vlrcusto", tblCarrExamLabo.getDouble("vlrcusto") - event.getTable().getDouble("vlrcusto"));
						tblCarrExamLabo.setDouble("vlrvenda", tblCarrExamLabo.getDouble("vlrvenda") - event.getTable().getDouble("vlrvenda"));
						tblCarrExamLabo.execute();
						
						// Totalizando os exames no CarrComp
						Table tblCarrComp = event.getTable().getMasterTable().getMasterTable();
						String uidCarrComp = tblCarrComp.getString("uid");
						tblCarrComp.update(uidCarrComp, false);
						tblCarrComp.setDouble("vlrtotal", tblCarrComp.getDouble("vlrtotal")-event.getTable().getDouble("vlrvenda"));
						tblCarrComp.setDouble("vlrcustototal", tblCarrComp.getDouble("vlrcustototal")-event.getTable().getDouble("vlrcusto"));
						tblCarrComp.execute();
					}
				});
				*/
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("carrexamlaboitems");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("carrexamlaboitems");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setHeight("600px");
				rmGrid.setLimit(4);
				rmGrid.addField("codiespe", "Código", 200d);
				rmGrid.addField("desccodiespe", "Especialidade médica", 200d, 1);
				rmGrid.addField("vlrvenda", "Valor", 100d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Agendamento de exames:");
				controlForm.setWidth(790d);
				controlForm.setHeight(440d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do exame:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 120d);
					controlForm.addField("desccodiespe", "Especialidade médica", 200d, 1);
					controlForm.addField("vlrvenda", "Valor do exame", 200d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por consultas:");
				controlForm.setWidth(600d);
				controlForm.setHeight(280d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do exame:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 120d);
					controlForm.addField("desccodiespe", "Especialidade médica", 200d, 1);
					controlForm.addField("vlrvenda", "Valor do exame", 200d, 1);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("carrexamlaboitems");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("carrexamlaboitems");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Consultas programadas", true);
			}
		});
	}
}
