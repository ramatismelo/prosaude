package com.example.myapplication.tables;

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
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent.DirectProcessingEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent.AfterUpdateEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class EspeForn {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("espeforn");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("espeforn");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("uidfornecedor", FieldType.VARCHAR, 50);
				tblTabela.addField("codiforn", FieldType.INTEGER, 10);
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("desccodiespe", FieldType.VARCHAR, 50);
				tblTabela.addField("cortesia", FieldType.VARCHAR, 1);
				tblTabela.addField("grupo", FieldType.VARCHAR, 50);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("valor", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvenda", FieldType.DOUBLE, 10);
				
				tblTabela.setPrimaryKey("sequencia");
				tblTabela.setAutoIncrement("sequencia", true);
				tblTabela.setOrder("espeforn.codiforn, especialidades.descricao");
				
				tblTabela.addIndex("uidfornecedor_codiespe", "uidfornecedor, codiespe");
				tblTabela.addIndex("codiespe_uidfornecedor", "codiespe, uidfornecedor");
				
				tblTabela.addJoin("especialidades", "codiespe", "codiespe");
				tblTabela.fieldByName("desccodiespe").setExpression("especialidades.descricao");
				tblTabela.fieldByName("valor").setExpression("especialidades.valor");
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("codiespe").addForeingSearch();
				foreingSearch.setTargetRmGridName("especialidades");
				foreingSearch.setTargetIndexName("codiespe");
				foreingSearch.setRelationship("codiespe");
				foreingSearch.setReturnFieldName("codiespe");
				foreingSearch.setTitle("Pesquisa por especialidades:");
				foreingSearch.setOrder("especialidades.descricao");
				//foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("S", "Sim");
				internalSearch.addItem("N", "Não");
				tblTabela.fieldByName("cortesia").setInternalSearch(internalSearch);
				
				tblTabela.fieldByName("codiespe").setRequired(true);
				tblTabela.fieldByName("cortesia").setRequired(true);
				tblTabela.fieldByName("grupo").setRequired(true);
				tblTabela.fieldByName("desccodiespe").setReadOnly(true);
				
				tblTabela.fieldByName("codiespe").setMask("");
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiespe").addExistenceCheck("especialidades", "codiespe", "codiespe", "Especialidade informada é inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodiespe", event.getTargetTable().getString("descricao"));
						
						if ((event.getSourceTable().getDouble("vlrvenda") == null) || ((event.getSourceTable().getDouble("vlrvenda")==0) && (event.getTargetTable().getDouble("valor")!=0))) {
							event.getSourceTable().setValue("vlrvenda", event.getTargetTable().getDouble("valor"));
							event.getSourceTable().setValue("cortesia", event.getTargetTable().getString("cortesia"));
						}
						
						if ((event.getSourceTable().getDouble("vlrcusto") == null) || ((event.getSourceTable().getDouble("vlrcusto")==0) && (event.getTargetTable().getDouble("vlrcusto")!=0))) {
							event.getSourceTable().setValue("vlrcusto", event.getTargetTable().getDouble("vlrcusto"));
						}
					}
				});
				
				tblTabela.addAfterUpdateEventListener(new AfterUpdateEventListener() {
					@Override
					public void onAfterUpdate(AfterUpdateEvent event) {
						// Ajusta o valor de venda que reside nesta tabela com o valor de venda que esta na tabela especialidades
						// desta forma o valor de venda que o usuario viu no grid vai ser iguao ao que ele vai ver no formulario
						// apos atualizar esse valor de venda nessa tabela, ele vai atualizar o valor de venda em especialidades
						event.getTable().setDouble("vlrcusto", event.getTable().getDouble("vlrcusto"));
						event.getTable().setDouble("valor", event.getTable().getDouble("vlrvenda"));
					}
				});
				
				tblTabela.addDirectProcessingEventListener(new DirectProcessingEventListener() {
					@Override
					public void onDirectProcessing(DirectProcessingEvent event) {
						String comando = "update especialidades set codiforn=" + event.getTable().getMasterTable().getString("codiforn") + ", valor=" + event.getTable().getDouble("vlrvenda") + ", vlrcusto=" + event.getTable().getDouble("vlrcusto") + " where (especialidades.codiespe='" + event.getTable().getInteger("codiespe") + "')";
						event.getTable().getDatabase();
						try {
							event.getTable().getDatabase().openConnection();
							event.getTable().getDatabase().executeCommand(comando);
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
						finally {
							event.getTable().getDatabase().closeConnection();
						}
					}
				});
				
				tblTabela.fieldByName("vlrvenda").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						if ((event.getTable().getDouble("vlrvenda")!=null) && (event.getTable().getDouble("vlrcusto")!=null) && 	event.getTable().getDouble("vlrvenda")<event.getTable().getDouble("vlrcusto")) {
							event.setValid(false);
							event.setValidationAdvice("Valor de venda não pode ser menor que o valor de custo!");
						}
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("espeforn");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("espeforn");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("codiespe", "Código", 80d);
				rmGrid.addField("desccodiespe", "Especialidade", 260d, 1);
				rmGrid.addField("cortesia", "Cortesia", 90d);
				rmGrid.addField("grupo", "Grupo", 90d);
				rmGrid.addField("vlrcusto", "Vlr.Custo", 100d);
				rmGrid.addField("vlrvenda", "vlr.Venda", 100d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de especialidade por fornecedor:");
				controlForm.setWidth(700d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Controle de especialidade por fornecedor:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 200d);
					controlForm.addField("desccodiespe", "Descricão", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("cortesia", "Cortesia", 100d);
					controlForm.addField("grupo", "Grupo", 100d);
					controlForm.addField("vlrcusto", "Valor custo", 100d, 1);
					controlForm.addField("vlrvenda", "Valor venda", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa de especialidade por Médicos:");
				controlForm.setWidth(700d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Manutenção de especialidade por fornecedor:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 200d);
					controlForm.addField("desccodiespe", "Descricão", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("cortesia", "Cortesia", 100d);
					controlForm.addField("grupo", "Grupo", 100d);
					controlForm.addField("vlrcusto", "Valor custo", 100d, 1);
					controlForm.addField("vlrvenda", "Valor venda", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("espemedi");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("espemedi");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Especialidades por médicos", true);
			}
		});
	}
}
