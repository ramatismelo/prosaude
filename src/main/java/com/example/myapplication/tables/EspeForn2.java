package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent.AfterValidateOnEditEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class EspeForn2 {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("espeforn2");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table table = event.getTable();
				table.setTableName("especialidades");
				table.addField("codiespe", FieldType.INTEGER, 10);
				table.addField("descricao", FieldType.VARCHAR, 250);
				table.addField("tipoproc", FieldType.VARCHAR, 1);
				table.addField("desctipoproc", FieldType.VARCHAR, 50);
				table.addField("tipoprocresu", FieldType.VARCHAR, 1);
				table.addField("valor", FieldType.DOUBLE, 10);
				table.addField("vlrcusto", FieldType.DOUBLE, 10);
				table.addField("codiforn", FieldType.INTEGER, 10);
				table.addField("uidfornecedor", FieldType.VARCHAR, 50);
				table.addField("grupo", FieldType.VARCHAR, 50);
				table.addField("cortesia", FieldType.VARCHAR, 10);
				table.addField("agendamento", FieldType.VARCHAR, 1);
				table.addField("descagendamento", FieldType.VARCHAR, 50);
				table.addField("imprparasaud", FieldType.VARCHAR, 1);
				table.addField("permagencons", FieldType.VARCHAR, 1);
				table.setPrimaryKey("codiespe");
				table.setAutoIncrement("codiespe", true);
				table.setOrder("especialidades.descricao");
				
				table.addIndex("descricao", "descricao");
				table.addIndex("codiforn", "codiforn");
				table.addIndex("uidfornecedor", "uidfornecedor");
				table.addIndex("codiespe", "codiespe");
				
				table.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().fieldByName("codiforn").setInteger(event.getTable().getMasterTable().getInteger("codiforn"));
						event.getTable().fieldByName("grupo").setString("1");
						event.getTable().fieldByName("cortesia").setString("N");
						event.getTable().fieldByName("imprparasaud").setString("N");
						event.getTable().fieldByName("permagencons").setString("N");
					}
				});
				
				table.fieldByName("descricao").setRequired(true);
				table.fieldByName("tipoproc").setRequired(true);
				table.fieldByName("cortesia").setRequired(true);
				
				table.fieldByName("codiespe").setReadOnlyUpdate(true);
				
				table.fieldByName("valor").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						if ((event.getTable().getDouble("valor")!=null) && (event.getTable().getDouble("vlrcusto")!=null) && event.getTable().getDouble("valor")<event.getTable().getDouble("vlrcusto")) {
							event.setValid(false);
							event.setValidationAdvice("Valor de venda não pode ser menor que o valor de custo!");
						}
					}
				});
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("S", "Sim");
				internalSearch.addItem("N", "Não");
				table.fieldByName("cortesia").setInternalSearch(internalSearch);
				
				InternalSearch isImprParaSaud = table.fieldByName("imprparasaud").addInternalSearch();
				isImprParaSaud.addItem("S", "Sim");
				isImprParaSaud.addItem("N", "Não");
				
				InternalSearch isPermAgenCons = table.fieldByName("permagencons").addInternalSearch();
				isPermAgenCons.addItem("S", "Sim");
				isPermAgenCons.addItem("N", "Não");
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("C", "CONSULTA");
				internalSearch.addItem("E", "EXAME LABORATORIAL");
				internalSearch.addItem("I", "EXAME DE IMAGEM");
				internalSearch.addItem("O", "OUTROS");
				table.fieldByName("tipoproc").setInternalSearch(internalSearch);
				table.fieldByName("desctipoproc").setValueOfInternalSearch("tipoproc");
				table.fieldByName("tipoproc").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						if (event.getTable().getString("tipoproc")!=null) {
							if (event.getTable().getString("tipoproc").equalsIgnoreCase("C")) {
								event.getTable().setValue("tipoprocresu", "C");
							}
							else {
								event.getTable().setValue("tipoprocresu", "E");
							}
						}
					}
				});
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("1", "SEMPRE SOLICITAR AGENDAMENTO");
				internalSearch.addItem("2", "SOLICITAR SOMENTE SE NECESSARIO");
				table.fieldByName("agendamento").setInternalSearch(internalSearch);
				table.fieldByName("descagendamento").setValueOfInternalSearch("agendamento");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("espeforn2");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("especialidades");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("descricao", "Procedimento", 260d, 1);
				rmGrid.addField("cortesia", "Cortesia", 90d);
				rmGrid.addField("grupo", "Grupo", 90d);
				rmGrid.addField("vlrcusto", "Vlr.Custo", 100d);
				rmGrid.addField("valor", "vlr.Venda", 100d);
				
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de especialidades:");
				controlForm.setWidth(800d);
				controlForm.setHeight(450d);
				
				controlForm.addNewLine();
				controlForm.addSection("especilidades", "Controle de especialidades.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 100d);
					controlForm.addField("descricao", "Descrição", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("tipoproc", "Procedimento", 200d, 1);
					controlForm.addField("grupo", "Grupo", 100d);
					controlForm.addField("cortesia", "Cortesia", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("permagencons", "Atendimento gratuito", 180d);
					controlForm.addField("imprparasaud", "Imprimir parametros de saúde", 200d, 1);
					controlForm.addField("vlrcusto", "Valor de custo", 150d);
					controlForm.addField("valor", "Valor de venda", 150d);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por especialidades:");
				controlForm.setWidth(800d);
				controlForm.setHeight(450d);
				
				controlForm.addNewLine();
				controlForm.addSection("especilidades", "Controle de especialidades.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 100d);
					controlForm.addField("descricao", "Descrição", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("tipoproc", "Procedimento", 200d, 1);
					controlForm.addField("grupo", "Grupo", 100d);
					controlForm.addField("cortesia", "Cortesia", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("permagencons", "Atendimento gratuito", 180d);
					controlForm.addField("imprparasaud", "Imprimir parametros de saúde", 200d, 1);
					controlForm.addField("vlrcusto", "Valor de custo", 150d);
					controlForm.addField("valor", "Valor de venda", 150d);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("espeforn2");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("espeforn2");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Especialidades médicas", true);
			}
		});
	}
}
