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
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
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

public class EspeMedi {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("espemedi");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("espemedi");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("uidmedico", FieldType.VARCHAR, 50);
				tblTabela.addField("codimedi", FieldType.INTEGER, 10);
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("desccodiespe", FieldType.VARCHAR, 50);
				
				tblTabela.addField("cortesia", FieldType.VARCHAR, 1);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("valor", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvenda", FieldType.DOUBLE, 10);
				
				tblTabela.setPrimaryKey("sequencia");
				tblTabela.setOrder("codimedi, codiespe");
				
				tblTabela.addIndex("codimedi_codiespe", "codimedi, codiespe");
				tblTabela.addIndex("codiespe_codimedi", "codiespe, codimedi");
				tblTabela.addIndex("uidmedico", "uidmedico");
				
				tblTabela.addJoin("especialidades", "codiespe", "codiespe");
				tblTabela.fieldByName("desccodiespe").setExpression("especialidades.descricao");
				tblTabela.fieldByName("vlrvenda").setExpression("especialidades.valor");
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("S", "Sim");
				internalSearch.addItem("N", "Não");
				tblTabela.fieldByName("cortesia").setInternalSearch(internalSearch);
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("codiespe").addForeingSearch();
				foreingSearch.setTargetRmGridName("especialidades");
				foreingSearch.setTargetIndexName("codiespe");
				foreingSearch.setRelationship("codiespe");
				foreingSearch.setReturnFieldName("codiespe");
				foreingSearch.setTitle("Pesquisa por especialidades:");
				foreingSearch.setOrder("descricao");
				//foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				tblTabela.fieldByName("codiespe").setRequired(true);
				tblTabela.fieldByName("desccodiespe").setReadOnly(true);

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
				
				existenceCheck = tblTabela.fieldByName("uidmedico").addExistenceCheck("medicos", "uid", "uidmedico", "Medico não localizado!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("codimedi", event.getTargetTable().getString("codimedi"));
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
						String comando = "update especialidades set valor=" + event.getTable().getDouble("vlrvenda") + ", vlrcusto=" + event.getTable().getDouble("vlrcusto") + " where (especialidades.codiespe='" + event.getTable().getInteger("codiespe") + "')";
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
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("espemedi");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("espemedi");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("codiespe", "Código", 80d);
				rmGrid.addField("desccodiespe", "Especialidade", 350d, 1);
				rmGrid.addField("cortesia", "Cortesia", 90d);
				rmGrid.addField("vlrcusto", "Vlr.Custo", 100d);
				rmGrid.addField("vlrvenda", "vlr.Venda", 100d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de especialidade por médicos:");
				controlForm.setWidth(700d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Controle de especialidade por médicos.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 200d);
					controlForm.addField("desccodiespe", "Descricão", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("cortesia", "Cortesia", 100d);
					controlForm.addField("vlrcusto", "Valor custo", 100d, 1);
					controlForm.addField("vlrvenda", "Valor venda", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa de especialidade por Médicos:");
				controlForm.setWidth(700d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Manutenção de especialidade por médicos:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 200d);
					controlForm.addField("desccodiespe", "Descricão", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("cortesia", "Cortesia", 100d);
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
