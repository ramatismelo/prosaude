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
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;
import com.evolucao.rmlibrary.database.ComboBox;

public class FinalizarVenda {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("finalizarVenda");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setManagedTable(false);
				tblTabela.setTableName("finalizarVenda");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				
				tblTabela.addField("numeitens", FieldType.INTEGER, 10);
				tblTabela.addField("vlrcustototal", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrtotal", FieldType.DOUBLE, 10);
				tblTabela.addField("cortesia", FieldType.VARCHAR, 1);
				tblTabela.addField("vlrdesconto", FieldType.DOUBLE, 10);
				tblTabela.addField("uidusualibe", FieldType.VARCHAR, 50);
				
				tblTabela.addField("vlrtotalpagar", FieldType.DOUBLE, 10);
				
				tblTabela.addField("vlrpagodinheiro", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrpagodebito", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrpagocredito", FieldType.DOUBLE, 10);
				tblTabela.addField("numeparcelas", FieldType.INTEGER, 10);
				
				tblTabela.addField("vlrtotalpago", FieldType.DOUBLE, 10);
				tblTabela.addField("troco", FieldType.DOUBLE, 10);
				
				tblTabela.setPrimaryKey("sequencia");
				tblTabela.setOrder("sequencia");
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÃO");
				tblTabela.fieldByName("cortesia").setInternalSearch(internalSearch);
				
				tblTabela.fieldByName("numeitens").setReadOnly(true);
				tblTabela.fieldByName("vlrcustototal").setReadOnly(true);
				tblTabela.fieldByName("vlrtotal").setReadOnly(true);
				tblTabela.fieldByName("vlrtotalpagar").setReadOnly(true);
				tblTabela.fieldByName("vlrtotalpago").setReadOnly(true);
				tblTabela.fieldByName("troco").setReadOnly(true);
				
				ComboBox cbUidUsuaLibe = tblTabela.fieldByName("uidusualibe").addComboBox("sysusers", "nome", "nome", "uid");
				
				tblTabela.fieldByName("uidusualibe").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						if ((event.getTable().getString("cortesia").equalsIgnoreCase("S")) || (event.getTable().getDouble("vlrdesconto")>0)) {
							if ((event.getTable().getString("uidusualibe")==null) || (event.getTable().getString("uidusualibe").isEmpty())) {
								event.setValid(false);
								event.setValidationAdvice("Necessário informar usuário que liberou a cortesia ou desconto!");
							}
						}
					}
				});
				
				tblTabela.fieldByName("cortesia").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						if (event.getTable().getString("cortesia").equalsIgnoreCase("S")) {
							event.getTable().setDouble("vlrdesconto", event.getTable().getDouble("vlrtotal"));
						}
					}
				});
				
				tblTabela.fieldByName("vlrdesconto").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						if (event.getTable().getDouble("vlrdesconto")>event.getTable().getDouble("vlrtotal")) {
							event.setValid(false);
							event.setValidationAdvice("Valor do desconto não pode ser maior que o valor total a pagar!");
						}
					}
				});
				
				tblTabela.fieldByName("vlrdesconto").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						event.getTable().setDouble("vlrtotalpagar", event.getTable().getDouble("vlrtotal")-event.getTable().getDouble("vlrdesconto"));
					}
				});
				
				tblTabela.fieldByName("vlrpagodinheiro").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						double vlrtotalpago = event.getTable().getDouble("vlrpagodinheiro");
						vlrtotalpago += event.getTable().getDouble("vlrpagodebito");
						vlrtotalpago += event.getTable().getDouble("vlrpagocredito");
						
						event.getTable().setDouble("vlrtotalpago", vlrtotalpago);
						
						if (event.getTable().getDouble("vlrtotalpagar")<=vlrtotalpago) {
							event.getTable().setDouble("troco", vlrtotalpago-event.getTable().getDouble("vlrtotalpagar"));
						}
						else {
							event.getTable().setDouble("troco", 0d);
						}
					}
				});
				
				tblTabela.fieldByName("vlrpagodebito").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						double vlrtotalpago = event.getTable().getDouble("vlrpagodinheiro");
						vlrtotalpago += event.getTable().getDouble("vlrpagodebito");
						vlrtotalpago += event.getTable().getDouble("vlrpagocredito");
						
						event.getTable().setDouble("vlrtotalpago", vlrtotalpago);
						
						if (event.getTable().getDouble("vlrtotalpagar")<=vlrtotalpago) {
							event.getTable().setDouble("troco", vlrtotalpago-event.getTable().getDouble("vlrtotalpagar"));
						}
						else {
							event.getTable().setDouble("troco", 0d);
						}
					}
				});
				
				tblTabela.fieldByName("vlrpagocredito").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						double vlrtotalpago = event.getTable().getDouble("vlrpagodinheiro");
						vlrtotalpago += event.getTable().getDouble("vlrpagodebito");
						vlrtotalpago += event.getTable().getDouble("vlrpagocredito");
						
						event.getTable().setDouble("vlrtotalpago", vlrtotalpago);
						
						if (event.getTable().getDouble("vlrtotalpagar")<=vlrtotalpago) {
							event.getTable().setDouble("troco", vlrtotalpago-event.getTable().getDouble("vlrtotalpagar"));
						}
						else {
							event.getTable().setDouble("troco", 0d);
						}
					}
				});
				
				tblTabela.fieldByName("vlrtotalpago").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						double vlrtotalpago = event.getTable().getDouble("vlrpagodinheiro");
						vlrtotalpago += event.getTable().getDouble("vlrpagodebito");
						vlrtotalpago += event.getTable().getDouble("vlrpagocredito");
						
						event.getTable().setDouble("vlrtotalpago", vlrtotalpago);
						
						if (event.getTable().getDouble("vlrtotalpagar")>vlrtotalpago) {
							event.setValid(false);
							event.setValidationAdvice("Ainda existem valores em aberto");
						}
						else {
							event.setValid(true);
							event.setValidationAdvice("");
						}
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("finalizarVenda");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("finalizarVenda");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("sequencia", "Sequencia", 150d);
				rmGrid.addField("vlrtotal", "Valor Total", 100d, 1);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Finalizando venda:");
				controlForm.setWidth(800d);
				controlForm.setHeight(710d);
				
				controlForm.addNewLine();
				controlForm.addSection("caixas", "Dados da venda.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("numeitens", "No.Itens vendidos", 100d, 1);
					controlForm.addField("vlrcustototal", "Valor total do custo", 100d, 1);
					controlForm.addField("vlrtotal", "Valor total da venda", 150d, 1);
					controlForm.addField("cortesia", "Cortesia", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("vlrdesconto", "Desconto concedido", 150d, 1);
					controlForm.addField("uidusualibe", "Autorização", 150d, 1);
					controlForm.addField("vlrtotalpagar", "Valor total a pagar", 100d, 1);
					
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("pagamento", "Pagamento", SectionState.MAXIMIZED);
					controlForm.addNewLine();
					controlForm.addField("vlrpagodinheiro", "Vlr.Pago em DINHEIRO", 100d, 1);
					controlForm.addField("vlrpagodebito", "Vlr.Pago CARTÃO DEBITO", 100d, 1);
					controlForm.addField("vlrpagocredito", "Vlr.Pago CARTÃO CREDITO", 100d, 1);
					controlForm.addField("numeparcelas", "No. Parcelas", 100d, 1);
					
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("pagamento2", "Finalização da venda", SectionState.MAXIMIZED);
					controlForm.addNewLine();
					controlForm.addField("vlrtotalpago", "Total pago", 100d, 1);
					controlForm.addField("troco", "Troco", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por finalizando venda:");
				controlForm.setWidth(800d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("caixas", "Controle de caixas.", SectionState.MAXIMIZED);
				{
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("finalizarVenda");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("finalizarVenda");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Finalizar Venda", true);
			}
		});
	}
}
