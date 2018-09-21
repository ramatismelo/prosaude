//'php-app/relarecibo.php?recordId=' + recordId
package com.example.myapplication.tables;

import java.util.Date;

import com.evolucao.rmlibrary.database.ComboBox;
import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.MessageWindowType;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent.BeforeForeingSearchEventListener;
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

public class Recibos {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("recibos");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("recibos");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("tiporeci", FieldType.VARCHAR, 1);
				tblTabela.addField("desctiporeci", FieldType.VARCHAR, 50);
				tblTabela.addField("emissao", FieldType.DATE, 10);
				tblTabela.addField("codiasso", FieldType.INTEGER, 10);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("paciente", FieldType.VARCHAR, 50);
				tblTabela.addField("cep", FieldType.VARCHAR, 10);
				tblTabela.addField("tipo", FieldType.VARCHAR, 50);
				tblTabela.addField("rua", FieldType.VARCHAR, 250);
				tblTabela.addField("numero", FieldType.VARCHAR, 50);
				tblTabela.addField("complemento", FieldType.VARCHAR, 250);
				tblTabela.addField("bairro", FieldType.VARCHAR, 50);
				tblTabela.addField("cidade", FieldType.VARCHAR, 50);
				tblTabela.addField("estado", FieldType.VARCHAR, 2);
				tblTabela.addField("inscesta_cartiden", FieldType.VARCHAR, 20);
				tblTabela.addField("cnpj_cpf", FieldType.VARCHAR, 20);
				tblTabela.addField("codiforn", FieldType.INTEGER, 10);
				tblTabela.addField("desccodiforn", FieldType.VARCHAR, 50);
				tblTabela.addField("codicaix", FieldType.VARCHAR, 5);
				tblTabela.addField("desccodicaix", FieldType.VARCHAR, 50);
				tblTabela.addField("referente", FieldType.TEXT, 10);
				tblTabela.addField("sequproc", FieldType.INTEGER, 10);
				tblTabela.addField("tipoatend", FieldType.VARCHAR, 1);
				tblTabela.addField("obsreci", FieldType.TEXT, 10);
				tblTabela.addField("obsreci2", FieldType.TEXT, 10);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("valor", FieldType.DOUBLE, 10);
				tblTabela.addField("sequcaix", FieldType.DOUBLE, 10);
				tblTabela.addField("uidcarrcomp", FieldType.VARCHAR, 50);
				tblTabela.addField("processado", FieldType.VARCHAR, 10);
				
				tblTabela.addField("cortesia", FieldType.VARCHAR, 1);
				tblTabela.addField("uidusualibe", FieldType.VARCHAR, 50);
				
				tblTabela.setPrimaryKey("sequencia");
				tblTabela.setOrder("recibos.codicaix, recibos.emissao desc, recibos.sequencia desc");
				
				tblTabela.addIndex("codiasso", "codiasso");
				
				tblTabela.addJoin("fornecedores", "codiforn", "codiforn");
				tblTabela.fieldByName("desccodiforn").setExpression("fornecedores.descricao");
				
				tblTabela.addJoin("caixas", "codicaix", "codicaix");
				tblTabela.fieldByName("desccodicaix").setExpression("caixas.descricao");
				
				tblTabela.fieldByName("sequencia").setReadOnly(true);
				tblTabela.fieldByName("sequencia").setAutoIncrement(true);
				
				tblTabela.fieldByName("codiasso").setReadOnly(true);
				tblTabela.fieldByName("valor").setReadOnly(true);

				tblTabela.setRequired("sequproc", true);
				tblTabela.setRequired("emissao", true);
				tblTabela.setRequired("tiporeci", true);
				tblTabela.setRequired("emissao", true);
				tblTabela.setRequired("nome", true);
				tblTabela.setRequired("valor", true);
				tblTabela.setRequired("codiforn", true);
				tblTabela.setRequired("codicaix", true);
				tblTabela.setRequired("referente", true);
				
				tblTabela.fieldByName("sequproc").setReadOnly(true);
				tblTabela.fieldByName("tiporeci").setReadOnlyUpdate(true);
				tblTabela.fieldByName("emissao").setReadOnlyUpdate(true);
				tblTabela.fieldByName("codiasso").setReadOnlyUpdate(true);
				tblTabela.fieldByName("nome").setReadOnlyUpdate(true);
				tblTabela.fieldByName("paciente").setReadOnlyUpdate(true);
				tblTabela.fieldByName("cep").setReadOnlyUpdate(true);
				tblTabela.fieldByName("tipo").setReadOnlyUpdate(true);
				tblTabela.fieldByName("rua").setReadOnlyUpdate(true);
				tblTabela.fieldByName("numero").setReadOnlyUpdate(true);
				tblTabela.fieldByName("complemento").setReadOnlyUpdate(true);
				tblTabela.fieldByName("bairro").setReadOnlyUpdate(true);
				tblTabela.fieldByName("cidade").setReadOnlyUpdate(true);
				tblTabela.fieldByName("estado").setReadOnlyUpdate(true);
				tblTabela.fieldByName("valor").setReadOnlyUpdate(true);
				tblTabela.fieldByName("vlrcusto").setReadOnlyUpdate(true);
				tblTabela.fieldByName("referente").setReadOnlyUpdate(true);
				

				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("R", "RECEBIMENTO");
				internalSearch.addItem("P", "PAGAMENTO");
				tblTabela.fieldByName("tiporeci").setInternalSearch(internalSearch);
				tblTabela.fieldByName("desctiporeci").setValueOfInternalSearch("tiporeci");
				
				InternalSearch isCortesia = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÃO");
				tblTabela.fieldByName("cortesia").setInternalSearch(internalSearch);
				
				ComboBox cbUidUsuaLibe = tblTabela.fieldByName("uidusualibe").addComboBox("sysusers", "nome", "nome", "uid");
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("codiforn").addForeingSearch();
				foreingSearch.setTargetRmGridName("fornecedores");
				foreingSearch.setTargetIndexName("codiforn");
				foreingSearch.setRelationship("codiforn");
				foreingSearch.setReturnFieldName("codiforn");
				foreingSearch.setTitle("Pesquisa por fornecedores:");
				foreingSearch.setOrder("descricao");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiforn").addExistenceCheck("fornecedores", "codiforn", "codiforn", "Fornecedor é inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodiforn", event.getTargetTable().getString("descricao"));
					}
				});
				
				foreingSearch = tblTabela.fieldByName("codicaix").addForeingSearch();
				foreingSearch.setTargetRmGridName("caixas");
				foreingSearch.setTargetIndexName("codicaix");
				foreingSearch.setRelationship("codicaix");
				foreingSearch.setReturnFieldName("codicaix");
				foreingSearch.setTitle("Pesquisa por caixas:");
				foreingSearch.setOrder("descricao");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				existenceCheck = tblTabela.fieldByName("codicaix").addExistenceCheck("caixas", "codicaix", "codicaix", "Caixa inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodicaix", event.getTargetTable().getString("descricao"));
					}
				});
				
				foreingSearch = tblTabela.fieldByName("codiasso").addForeingSearch();
				foreingSearch.setTargetRmGridName("associados");
				foreingSearch.setTargetIndexName("sequencia");
				foreingSearch.setRelationship("codiasso");
				foreingSearch.setReturnFieldName("sequencia");
				foreingSearch.setTitle("Pesquisa por associados:");
				foreingSearch.setOrder("nome");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				existenceCheck = tblTabela.fieldByName("codiasso").addExistenceCheck("associados", "sequencia", "codiasso", "Associado inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("nome", event.getTargetTable().getString("nome"));
						event.getSourceTable().setValue("cep", event.getTargetTable().getString("cep"));
						event.getSourceTable().setValue("tipo", event.getTargetTable().getString("tipo"));
						event.getSourceTable().setValue("rua", event.getTargetTable().getString("rua"));
						event.getSourceTable().setValue("numero", event.getTargetTable().getString("endenume"));
						event.getSourceTable().setValue("complemento", event.getTargetTable().getString("complemento"));
						event.getSourceTable().setValue("bairro", event.getTargetTable().getString("bai"));
						event.getSourceTable().setValue("cidade", event.getTargetTable().getString("cid"));
						event.getSourceTable().setValue("estado", event.getTargetTable().getString("uf"));
					}
				});
				
				/**/
				
				tblTabela.addIndex("sequencia", "sequencia");
				tblTabela.addIndex("uidcarrcomp", "uidcarrcomp");
				tblTabela.addIndex("codicaix_emissao_sequencia", "codicaix, emissao, sequencia");
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("emissao", Utils.getJustDate(new Date()));
						event.getTable().setValue("tiporeci", "R");
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("recibos");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("recibos");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setHeight("550px");
				rmGrid.setLimit(10);
				rmGrid.addField("codicaix", "Cód.Caixa", 90d);
				rmGrid.addField("emissao", "Emissão", 150d);
				rmGrid.addField("sequencia", "Sequência", 120D);
				rmGrid.addField("nome", "Nome do associado", 220d, 1);
				rmGrid.addField("paciente", "Paciente", 220d);
				rmGrid.addField("valor", "Valor", 100d);
				rmGrid.addField("referente", "Referente", 200d);
				rmGrid.addField("incluser", "Inclusão - Usuário", 130d);
				rmGrid.addField("incldata", "Inclusão - Data", 100d);				
				rmGrid.addField("modiuser", "Modificação - Usuário", 130d);
				rmGrid.addField("modidata", "Modificação - Data", 100d);
				
				rmGrid.setAllowInsert(false);
				rmGrid.setAllowDelete(false);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de recibos:");
				controlForm.setWidth(790d);
				controlForm.setHeight(600d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do recibo:", SectionState.MAXIMIZED);
				{
					RmFormButtonBase button = controlForm.addRmFormButton("Emitir recibo");
					button.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
						@Override
						public void onRmFormButtonClick(RmFormButtonClickEvent event) {
							if (!event.getControlForm().getTable().getString("uid").isEmpty()) {
								Table tblParametros = event.getControlForm().getTable().getDatabase().loadTableByName("parametros");
								tblParametros.select("*");
								tblParametros.loadData();
								
								String url = "http://" + tblParametros.getString("reportServer") + "/php-app/relarecibo2.php?recordId="+event.getControlForm().getTable().getInteger("sequencia");
								UI.getCurrent().getPage().open(url, "_blank", false);
							}
						}
					});
					
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 100d);
					controlForm.addField("sequproc", "No.Procedimento", 150d);
					controlForm.addField("tiporeci", "Tipo de recibo", 150d, 1);
					controlForm.addField("emissao", "Emissão", 150d);

					controlForm.addNewLine();
					controlForm.addField("codiasso", "Cód.Associado", 150d);
					controlForm.addField("nome", "Nome", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("cep", "Cep", 100d);
					controlForm.addField("tipo", "Tipo", 100d);
					controlForm.addField("rua", "Rua", 100d, 1);
					controlForm.addField("numero", "Numero", 100d);
					controlForm.addField("complemento", "Complemento", 150d);

					controlForm.addNewLine();
					controlForm.addField("bairro", "Bairro", 100d, 1);
					controlForm.addField("cidade", "Cidade", 200d);
					controlForm.addField("estado", "Estado", 80d);

					controlForm.addNewLine();
					//controlForm.addField("inscesta_cartiden", "Insc.Estadual/Cart.Identidade", 200d);
					//controlForm.addField("cnpj_cpf", "C.N.P.J/C.P.F.", 200d);
					controlForm.addField("cortesia", "Cortesia", 100d);
					controlForm.addField("uidusualibe", "Autorização", 300d);
					
					controlForm.addField("valor", "Valor do recibo", 100d, 1);
					controlForm.addField("vlrcusto", "Vlr.Custo", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("codiforn", "Código", 100d);
					controlForm.addField("desccodiforn", "Fornecedor", 100d, 1);
					controlForm.addField("codicaix", "Código", 100d);
					controlForm.addField("desccodicaix", "Caixa", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("referente", "Referente", 100d, 300d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por associados:");
				controlForm.setWidth(790d);
				controlForm.setHeight(600d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do recibo:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 100d);
					controlForm.addField("sequproc", "No.Procedimento", 150d);
					controlForm.addField("tiporeci", "Tipo de recibo", 150d, 1);
					controlForm.addField("emissao", "Emissão", 150d);

					controlForm.addNewLine();
					controlForm.addField("codiasso", "Cód.Associado", 150d);
					controlForm.addField("nome", "Nome", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("cep", "Cep", 100d);
					controlForm.addField("tipo", "Tipo", 100d);
					controlForm.addField("rua", "Rua", 100d, 1);
					controlForm.addField("numero", "Numero", 100d);
					controlForm.addField("complemento", "Complemento", 150d);

					controlForm.addNewLine();
					controlForm.addField("bairro", "Bairro", 100d, 1);
					controlForm.addField("cidade", "Cidade", 200d);
					controlForm.addField("estado", "Estado", 80d);

					controlForm.addNewLine();
					controlForm.addField("inscesta_cartiden", "Insc.Estadual/Cart.Identidade", 200d);
					controlForm.addField("cnpj_cpf", "C.N.P.J/C.P.F.", 200d);
					controlForm.addField("valor", "Valor do recibo", 100d, 1);
					controlForm.addField("vlrcusto", "Vlr.Custo", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("codiforn", "Código", 100d);
					controlForm.addField("desccodiforn", "Fornecedor", 100d, 1);
					controlForm.addField("codicaix", "Código", 100d);
					controlForm.addField("desccodicaix", "Caixa", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("referente", "Referente", 100d, 300d, 1);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("recibos");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("recibos");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Recibos", true);
			}
		});
	}
}
