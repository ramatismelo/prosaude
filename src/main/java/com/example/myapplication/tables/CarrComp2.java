package com.example.myapplication.tables;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.evolucao.rmlibrary.database.ComboBox;
import com.evolucao.rmlibrary.database.ComboBoxAdvanced;
import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.MessageWindowType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent.AfterValidateOnEditEventListener;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.ComboBoxItemCaptionGeneratorEvent;
import com.evolucao.rmlibrary.database.events.ComboBoxValueChangeEvent;
import com.evolucao.rmlibrary.database.events.ComboBoxValueChangeEvent.ComboBoxValueChangeEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent.BeforeForeingSearchEventListener;
import com.evolucao.rmlibrary.database.events.ComboBoxItemCaptionGeneratorEvent.ComboBoxItemCaptionGeneratorEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterInsertEvent;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent.AfterUpdateEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterInsertEvent.AfterInsertEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent.SpecialConditionOfUpdateEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlBase;
import com.evolucao.rmlibrary.ui.controller.ControlButton;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.ControlGrid;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent.ControlButtonClickEventListener;
import com.evolucao.rmlibrary.ui.fields.RmComboBox;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.models.UltimoAtendimento;
import com.example.myapplication.view.TesteView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.UI;

import net.sf.jasperreports.data.cache.IndexColumnValueIterator;

public class CarrComp2 {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("carrcomp2");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("carrcomp2");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("data", FieldType.DATE, 10);
				tblTabela.addField("uidassociado", FieldType.VARCHAR, 50);
				tblTabela.addField("codiasso", FieldType.INTEGER, 10);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("paciente", FieldType.VARCHAR, 50);
				tblTabela.addField("nasc", FieldType.DATE, 10);
				tblTabela.addField("processado", FieldType.DATETIME, 10);
				tblTabela.addField("codicaix", FieldType.VARCHAR, 5);
				
				tblTabela.addField("uidrequisitante", FieldType.VARCHAR, 50);
				tblTabela.addField("numeitens", FieldType.INTEGER, 10);
				tblTabela.addField("vlrtotal", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrcustototal", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codimedi", FieldType.VARCHAR, 10);
				tblTabela.addField("uidfornecedor", FieldType.VARCHAR, 50);
				tblTabela.addField("codiforn", FieldType.INTEGER, 10);
				tblTabela.addField("datacons", FieldType.DATE, 10);
				tblTabela.addField("diasema", FieldType.VARCHAR, 50);
				tblTabela.addField("horacons", FieldType.VARCHAR, 5);
				
				tblTabela.addField("uidespecialidade", FieldType.VARCHAR, 50);
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("descricao", FieldType.VARCHAR, 250);
				tblTabela.addField("tipoproc", FieldType.VARCHAR, 1);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvenda", FieldType.DOUBLE, 10);
				
				//finalizando venda
				tblTabela.addField("cortesia", FieldType.VARCHAR, 1);
				tblTabela.addField("uidusualibe", FieldType.VARCHAR, 50);
				
				tblTabela.addField("vlrdesconto", FieldType.DOUBLE, 10);
				tblTabela.addField("uidusualibe", FieldType.VARCHAR, 50);
				tblTabela.addField("vlrtotalpagar", FieldType.DOUBLE, 10);
				
				tblTabela.addField("vlrpagodinheiro", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrpagodebito", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrpagocredito", FieldType.DOUBLE, 10);
				tblTabela.addField("numeparcelas", FieldType.INTEGER, 10);
				
				tblTabela.addField("vlrtotalpago", FieldType.DOUBLE, 10);
				tblTabela.addField("troco", FieldType.DOUBLE, 10);
				//
				
				tblTabela.setPrimaryKey("sequencia");
				tblTabela.setOrder("sequencia desc");
				
				tblTabela.addIndex("data", "data");
				tblTabela.addIndex("codiasso", "codiasso");
				
				Field field = tblTabela.fieldByName("sequencia");
				field.setAutoIncrement(true);
				field.setReadOnly(true);
				
				tblTabela.fieldByName("sequencia").setReadOnly(true);
				//tblTabela.fieldByName("data").setReadOnly(true);
				//tblTabela.fieldByName("codiasso").setReadOnly(true);
				//tblTabela.fieldByName("nome").setReadOnly(true);
				tblTabela.fieldByName("vlrtotal").setReadOnly(true);
				tblTabela.fieldByName("vlrcustototal").setReadOnly(true);
				tblTabela.fieldByName("processado").setReadOnly(true);
				tblTabela.fieldByName("vlrcusto").setReadOnly(true);
				tblTabela.fieldByName("vlrvenda").setReadOnly(true);
				
				tblTabela.fieldByName("data").setRequired(true);
				tblTabela.fieldByName("uidassociado").setRequired(true);
				tblTabela.fieldByName("codiasso").setRequired(true);
				tblTabela.fieldByName("nome").setRequired(true);
				tblTabela.fieldByName("paciente").setRequired(true);
				tblTabela.fieldByName("nasc").setRequired(true);
				tblTabela.fieldByName("uidrequisitante").setRequired(true);
				
				tblTabela.fieldByName("uidassociado").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						if (event.getField().getString().isEmpty()) {
							event.setValid(false);
							event.setValidationAdvice("Necessário indicar um associado válido!");
						}
					}
				});
				
				ComboBox  cmbRequisitante = tblTabela.fieldByName("uidrequisitante").addComboBox("requisitantes", "descricao", "descricao", "uid");
				cmbRequisitante.setAdditionalFieldsToLoad("especialidade");
				cmbRequisitante.addComboBoxItemCaptionGeneratorEventListener(new ComboBoxItemCaptionGeneratorEventListener() {
					@Override
					public void onComboBoxItemCaptionGenerator(ComboBoxItemCaptionGeneratorEvent event) {
						if (event.getSimpleRecord().fieldExists("especialidade")) {
							event.setItemCaption(event.getSimpleRecord().getString("descricao") + " - " + event.getSimpleRecord().getString("especialidade"));
						}
						else {
							event.setItemCaption(event.getSimpleRecord().getString("descricao"));
						}
					}
				});
				
				//ComboBoxAdvanced comboBoxAdvanced = tblTabela.fieldByName("nome").addComboBoxAdvanced("associados");
				ComboBox cmbUidAssociado = tblTabela.fieldByName("uidassociado").addComboBox("associados", "nome", "nome", "uid");
				cmbUidAssociado.setAdditionalFieldsToLoad("nasc");
				
				cmbUidAssociado.addComboBoxItemCaptionGeneratorEventListener(new ComboBoxItemCaptionGeneratorEventListener() {
					@Override
					public void onComboBoxItemCaptionGenerator(ComboBoxItemCaptionGeneratorEvent event) {
						String retorno = "";
						
						if (!event.getSimpleRecord().getString("nome").isEmpty()) {
							retorno = event.getSimpleRecord().getString("nome");
						}
						
						if ((event.getSimpleRecord().fieldExists("nasc")) && (event.getSimpleRecord().getDate("nasc")!=null)) {
							retorno += " - Nasc: " + Utils.simpleDateFormat(event.getSimpleRecord().getDate("nasc"));
						}
						
						event.setItemCaption(retorno);
					}
				});
				
				cmbUidAssociado.addComboBoxValueChangeEventListener(new ComboBoxValueChangeEventListener() {
					@Override
					public void onComboBoxValueChange(ComboBoxValueChangeEvent event) {
						if ((event.getSimpleRecord()!=null) && (!event.getSimpleRecord().getString("uid").isEmpty())) {
							event.getSourceTable().setString("nome", event.getSimpleRecord().getString("nome"));
							ApplicationUI ui = (ApplicationUI) UI.getCurrent();
							try {
								ui.database.openConnection();
								Table tblAssociados = ui.database.loadTableByName("associados");
								tblAssociados.select("sequencia, nasc");
								tblAssociados.setFilter("uid", event.getSimpleRecord().getString("uid"));
								tblAssociados.loadData();
								if (!tblAssociados.eof()) {
									event.getSourceTable().setValue("codiasso", tblAssociados.getInteger("sequencia").toString());
									
									//if ((event.getSourceTable().getString("paciente")==null) || (event.getSourceTable().getString("paciente").isEmpty())) {
									event.getSourceTable().setValue("nome", event.getSimpleRecord().getString("nome"));
									event.getSourceTable().setValue("nasc", tblAssociados.getDate("nasc"));
									event.getSourceTable().setValue("paciente", event.getSimpleRecord().getString("nome"));
									//}
								}
							}
							catch (Exception e) {
								System.out.println(e.getMessage());
							}
							finally {
								ui.database.closeConnection();
							}
						}
						else {
							event.getSourceTable().setInteger("codiasso", null);
							event.getSourceTable().setString("nome", null);
							event.getSourceTable().setString("nasc", null);
							event.getSourceTable().setString("paciente", null);
						}
					}
				});
				
				ComboBox uidEspecialidade = tblTabela.fieldByName("uidespecialidade").addComboBox("especialidades", "descricao", "descricao", "uid");
				uidEspecialidade.setMinimunSizeToStartSearch(0);
				uidEspecialidade.setWhere("(not (especialidades.tipoproc is null)) and (not (especialidades.uidfornecedor is null))");
				uidEspecialidade.addComboBoxValueChangeEventListener(new ComboBoxValueChangeEventListener() {
					@Override
					public void onComboBoxValueChange(ComboBoxValueChangeEvent event) {
						if ((event.getSimpleRecord()!=null) && (!event.getSimpleRecord().getString("uid").isEmpty())) {
							ApplicationUI ui = (ApplicationUI) UI.getCurrent();
							
							Table tblTabela = ui.database.loadTableByName("especialidades");
							try {
								ui.database.openConnection();
								
								tblTabela.select("codiespe, tipoproc, vlrcusto, valor");
								tblTabela.setFilter("uid", event.getSimpleRecord().getString("uid"));
								tblTabela.loadData();
								if (!tblTabela.eof()) {
									event.getSourceTable().setInteger("codiespe", tblTabela.getInteger("codiespe"));
									event.getSourceTable().setString("descricao", event.getSimpleRecord().getString("descricao"));
									event.getSourceTable().setString("tipoproc", tblTabela.getString("tipoproc"));
									event.getSourceTable().setDouble("vlrcusto", tblTabela.getDouble("vlrcusto"));
									event.getSourceTable().setDouble("vlrvenda", tblTabela.getDouble("valor"));
								}
							}
							catch (Exception e) {
								System.out.println(e.getMessage());
							}
							finally {
								ui.database.closeConnection();
							}
							
							// Chama o codigo do botao de inclusao agora
							//insertItem(event.getSourceTable());
						}
						else {
							event.getSourceTable().setString("descricao", null);
							event.getSourceTable().setDouble("vlrcusto", null);
							event.getSourceTable().setDouble("vlrvenda", null);
						}
					}
				});
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("codiasso").addForeingSearch();
				foreingSearch.setTargetRmGridName("associados");
				foreingSearch.setTargetIndexName("sequencia");
				foreingSearch.setRelationship("codiasso");
				foreingSearch.setReturnFieldName("sequencia");
				foreingSearch.setTitle("Pesquisa por ASSOCIADOS:");
				foreingSearch.setOrder("nome");
				//foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				foreingSearch.addBeforeForeingSearchEventListener(new BeforeForeingSearchEventListener() {
					@Override
					public void onBeforeForeingSearch(BeforeForeingSearchEvent event) {
						if (event.getSourceTable().getDate("processado")!=null) {
							event.setCancel(true);
							event.setErrorMessage("Não é permitido alterar um carrido já fechado!");
						}
						else {
							event.getTargetTable().setMainFilter("situacao", "A");
						}
					}
				});
				
				tblTabela.fieldByName("codiasso").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						System.out.println("Trocou o codigo:" + event.getTable().getString("codiasso"));
						Table tblAssociados = event.getTable().getDatabase().loadTableByName("associados");
						tblAssociados.select("uid, nome, nasc");
						tblAssociados.setFilter("sequencia", event.getTable().getInteger("codiasso"));
						tblAssociados.loadData();
						if (!tblAssociados.eof()) {
							event.getTable().setValue("uidassociado", tblAssociados.getString("uid"));
							event.getTable().setValue("nome", tblAssociados.getString("nome"));
							event.getTable().setValue("nasc", tblAssociados.getDate("nasc"));
							event.getTable().setValue("paciente", tblAssociados.getString("nome"));
						}
					}
				});
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiasso").addExistenceCheck("associados", "sequencia", "codiasso", "Associado não encontrado ou inválido!");
				/*
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						if ((event.getSourceTable().getString("nome")==null) || (event.getSourceTable().getString("nome").isEmpty())) {
							event.getSourceTable().setValue("uidassociado", event.getTargetTable().getString("uid"));
							event.getSourceTable().setValue("nome", event.getTargetTable().getString("nome"));
							event.getSourceTable().setValue("nasc", event.getTargetTable().getDate("nasc"));
							event.getSourceTable().setValue("paciente", event.getTargetTable().getString("nome"));
						}
					}
				});
				*/
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setDate("data", Utils.getJustDate(new Date()));
						event.getTable().setDouble("vlrtotal", 0d);
						event.getTable().setDouble("vlrcustototal", 0d);
						event.getTable().setValue("cortesia", "N");
						event.getTable().setDouble("vlrtotalpagar", 0d);
					}
				});
				
				tblTabela.addAfterInsertEventListener(new AfterInsertEventListener() {
					@Override
					public void onAfterInsert(AfterInsertEvent event) {
						event.getTable().fieldByName("paciente").setReadOnly(false);
						event.getTable().fieldByName("nasc").setReadOnly(false);
					}
				});
				
				tblTabela.addAfterUpdateEventListener(new AfterUpdateEventListener() {
					@Override
					public void onAfterUpdate(AfterUpdateEvent event) {
						if (event.getTable().getDate("processado")==null) {
							event.getTable().fieldByName("paciente").setReadOnly(false);
							event.getTable().fieldByName("nasc").setReadOnly(false);
						}
						else {
							event.getTable().fieldByName("paciente").setReadOnly(true);
							event.getTable().fieldByName("nasc").setReadOnly(true);
						}
					}
				});
				
				Table tblCarrConsExam = database.loadTableByName("carrconsexam");
				tblTabela.addTableChild(tblCarrConsExam, "uidcarrcomp", false);
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÃO");
				tblTabela.fieldByName("cortesia").setInternalSearch(internalSearch);
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("carrcomp2");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("carrcomp2");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(database.loadTableByName("carrcomp2"));
				rmGrid.setHeight("550px");
				rmGrid.setLimit(10);
				rmGrid.addField("sequencia", "Sequência", 100D);
				rmGrid.addField("data", "Data", 150d);
				rmGrid.addField("processado", "Processado", 130d);
				rmGrid.addField("nome", "Nome do associado", 250d, 1);
				rmGrid.addField("paciente", "Paciente", 250d);
				rmGrid.addField("numeitens", "No.Itens", 100d);
				rmGrid.addField("vlrtotal", "Valor total", 150d);
				rmGrid.addField("incluser", "Inclusão - Usuário", 150d);
				rmGrid.addField("incldata", "Inclusão - Data", 130d);
				rmGrid.addField("modiuser", "Modificação - Usuário", 150d);
				rmGrid.addField("modidata", "Modificação - Data", 130d);

				rmGrid.setAllowDelete(false);
				rmGrid.setAllowPrint(false);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de compras:");
				controlForm.setWidth(1100d);
				controlForm.setHeight(760d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do Associado/Paciente:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 90d);
					controlForm.addField("data", "Data", 150d);
					controlForm.addField("uidassociado", "Nome", 250d, 1);
					controlForm.addField("codiasso", "Cód.Associado", 130d);
					
					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 150d);
					
					controlForm.addNewLine();
					controlForm.addField("uidrequisitante", "Requisitante", 250d, 1);
					controlForm.addField("processado", "Processado", 150d);
					controlForm.addField("numeitens", "No.Itens", 120d);
					controlForm.addField("vlrcustototal", "Vlr.Custo Total", 120d);
					controlForm.addField("vlrtotal", "Vlr.Venda Total", 120d);
					
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("inclusao", "Solicitação de consultas/exames:", SectionState.MAXIMIZED, 500d);
					{
						controlForm.addNewLine();
						controlForm.addField("uidespecialidade", "Consulta/exame:", 200d, 1);
						ControlButton controlButton = controlForm.addButton("Incluir");
						controlButton.setClickShortcut(KeyCode.ENTER);
						controlButton.addControlButtonClickEventListener(new ControlButtonClickEventListener() {
							@Override
							public void onControlButtonClick(ControlButtonClickEvent event) {
								Table tblCarrComp = event.getRmForm().getTable();
								ControlForm controlFormCarrComp = event.getRmForm();
								
								// Caso o procedimento seja uma consulta, deve analisar se a ultima consulta para esta especialidade que
								// esta sendo vendida ainda esta dentro do periodo de retorno, caso esteja, avisa e pede confirmacao da venda
								// caso contrario, apenas executa a venda
								if (tblCarrComp.getString("tipoproc").equalsIgnoreCase("C")) {
									// Insere o item
									UltimoAtendimento ultimoAtendimento = new UltimoAtendimento();
									ultimoAtendimento.updateUltimoAtendimento(tblCarrComp.getDate("data"), tblCarrComp.getInteger("codiasso"), tblCarrComp.getString("paciente"), tblCarrComp.getInteger("codiespe"));
									
									// Condicoes para uma venda normal agora
									// Caso o ultimo atendimento seja null = nao teve vendas anteriores
									// Caso o ultimo atendimento seja um retorno
									// Caso o ultimo atendimento seja consulta e esteja alem do periodo de retorno
									if ((ultimoAtendimento.getTipoAtend()==null) ||					
										(ultimoAtendimento.getTipoAtend().equalsIgnoreCase("R")) || 
										((ultimoAtendimento.getTipoAtend().equalsIgnoreCase("C")) && (ultimoAtendimento.getUltiConsDias()>20))) {
										
										insertItem(tblCarrComp, controlFormCarrComp);
									}
									else {
										// Caso o ultimo atendimento seja consulta e esteja dentro do periodo de retorno, solicita liberacao da venda
										// porque o associado tem direito a retorno
										
										RmFormWindow formWindow = new RmFormWindow();
										formWindow.setTitle("Atenção!");
										//formWindow.setForm(getForm());
										formWindow.setWidth("700px");
										formWindow.setHeight("220px");
										
										formWindow.addMessage("O paciente informado possui uma consulta na especialidade indicada dentro do perído de retorno.", "Deseja executar a venda de uma nova consulta mesmo assim?", MessageWindowType.QUESTION);
										
										RmFormButtonBase buttonBase = formWindow.addConfirmButton("Sim");
										buttonBase.setIcon(new ThemeResource("imagens/library/accept.png"));
										buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
											@Override
											public void onRmFormButtonClick(RmFormButtonClickEvent event) {
												//updateRecords();
												insertItem(tblCarrComp, controlFormCarrComp);
												event.getWindow().close();
											}
										});
										
										buttonBase = formWindow.addCancelButton("Não");
										buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
											@Override
											public void onRmFormButtonClick(RmFormButtonClickEvent event) {
												RmFormWindow window = (RmFormWindow) event.getWindow();
												window.close();
											}
										});
										
										formWindow.show();
									}
								}
								else {
									insertItem(tblCarrComp, controlFormCarrComp);
								}
							}
						});
						controlForm.addField("vlrcusto", "Vlr.Custo", 100d);
						controlForm.addField("vlrvenda", "Vlr.Venda", 100d);
						
						controlForm.addNewLine();
						controlForm.addRmGrid("carrconsexam");
					}
				}

				RmFormButtonBase btnFinalizarCompra = controlForm.addRmFormButton("Finalizar venda");
				btnFinalizarCompra.setIcon(new ThemeResource("imagens/library/money.png"));
				btnFinalizarCompra.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
					@Override
					public void onRmFormButtonClick(RmFormButtonClickEvent event) {
						Table tblCarrComp = event.getControlForm().getTable();
						RmFormWindow formCarrComp = event.getRmFormWindow();
						
						RmGrid rmGrid = database.loadRmGridByName("finalizarVenda");
						if (rmGrid.getTable().insert()) {
							RmFormWindow formWindow = new RmFormWindow();
							rmGrid.getForm().setWindow(formWindow);
							rmGrid.getForm().setTable(rmGrid.getTable());
							formWindow.setTitle(rmGrid.getForm().getTitle());
							formWindow.setForm(rmGrid.getForm());
							formWindow.setWidth(rmGrid.getForm().getWidth()+"px");
							formWindow.setHeight(rmGrid.getForm().getHeight()+"px");
							
							// Inclui botoes adicionais ao formWindow
							for (RmFormButtonBase rmFormButtonBase: rmGrid.getForm().getRmFormButtonList()) {
								formWindow.getRmFormButtons().add(rmFormButtonBase);
							}
							
							RmFormButtonBase buttonBase = formWindow.addCancelButton();
							buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
								@Override
								public void onRmFormButtonClick(RmFormButtonClickEvent event) {
									RmFormWindow window = (RmFormWindow) event.getWindow();
									window.close();
								}
							});

							buttonBase = formWindow.addButton("Confirmar o recebimento");
							buttonBase.setIcon(new ThemeResource("imagens/library/money.png"));
							buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
								@Override
								public void onRmFormButtonClick(RmFormButtonClickEvent event) {									
									RmFormWindow window = (RmFormWindow) event.getWindow();

									if (window.getForm().getTable().validate()) {
										// *********************************************************************
										Boolean executeProcesso = true;
										//Table tblCarrComp = event.getControlForm().getTable();
										
										try {
											tblCarrComp.getDatabase().openConnection();

											// Caso não tenha conseguido pegar o codigo do caixa, avisa e nao continua a execucao do programa
											String vcodicaix = getCodigoCaixa();
											if (vcodicaix==null) {
												executeProcesso = false;
												Utils.ShowMessageWindow("Erro detectado!", "Não foi localizado um caixa de trabalho válido para o usuário " + tblCarrComp.getDatabase().getUserSystemLogged().getUserName() + " !", 500, 180, MessageWindowType.ERROR);
											}
											
											if (executeProcesso) {
												if (tblCarrComp.getDate("processado")==null) {
													// Finaliza o carrinho de compras
													tblCarrComp.update(tblCarrComp.getString("uid"), false);
													tblCarrComp.setDate("processado", new Date());
													tblCarrComp.setValue("codicaix", vcodicaix);
													tblCarrComp.setValue("cortesia", window.getForm().getTable().getString("cortesia"));
													tblCarrComp.setValue("vlrdesconto", window.getForm().getTable().getDouble("vlrdesconto"));
													tblCarrComp.setValue("uidusualibe", window.getForm().getTable().getString("uidusualibe"));
													tblCarrComp.setValue("vlrtotalpagar", window.getForm().getTable().getDouble("vlrtotalpagar"));
													tblCarrComp.setValue("vlrpagodinheiro", window.getForm().getTable().getDouble("vlrpagodinheiro"));
													tblCarrComp.setValue("vlrpagodebito", window.getForm().getTable().getDouble("vlrpagodebito"));
													tblCarrComp.setValue("vlrpagocredito", window.getForm().getTable().getDouble("vlrpagocredito"));
													tblCarrComp.setValue("numeparcelas", window.getForm().getTable().getDouble("numeparcelas"));
													tblCarrComp.setValue("vlrtotalpago", window.getForm().getTable().getDouble("vlrtotalpago"));
													tblCarrComp.setValue("troco", window.getForm().getTable().getDouble("troco"));
													tblCarrComp.execute();
													
													// Pega os dados do associado para ser usado no recibo
													Table tblAssociados = tblCarrComp.getDatabase().loadTableByName("associados");
													tblAssociados.select("*");
													tblAssociados.setFilter("sequencia", tblCarrComp.getInteger("codiasso"));
													tblAssociados.loadData();
													
													Table tblFornecedores = tblCarrComp.getDatabase().loadTableByName("fornecedores");
													
													Table tblMarcCons = tblCarrComp.getDatabase().loadTableByName("marccons");
													Table tblRecibos = tblCarrComp.getDatabase().loadTableByName("recibos");
													
													Table tblParametros = tblMarcCons.getDatabase().loadTableByName("parametros");
													tblParametros.select("*");
													tblParametros.loadData();

													List<String> uidCarrConsExam = new ArrayList<String>();
													String uidFornecedor = "";
													int codiforn = 0;
													String tipoproc = "";
													String tipoatend = "";
													String grupo = "";
													String referente = "";
													int numeitem = 1;
													double vlrtotalcusto = 0;
													double vlrtotalvenda = 0;
													boolean emitindo = false;
													String vcodicaixforn = "";
													
													// Pega a relacao de consultas que estao agendadas
													String comando = "";
													comando +=	"select carrconsexam.uid, ";
													comando += "    carrconsexam.uidfornecedor, ";
													comando +="     carrconsexam.uidespecialidade,";
													comando += "    especialidades.tipoproc, ";
													comando += "    especialidades.grupo, ";
													comando += "    fornecedores.codiforn, ";
													comando += "    fornecedores.codimedi, ";
													comando += "    fornecedores.codiespepadr, ";
													comando += "    fornecedores.uidcaixa, caixas.codicaix, carrconsexam.codiespe, carrconsexam.vlrcusto, carrconsexam.vlrvenda, carrconsexam.datacons, carrconsexam.horacons, carrconsexam.diasema, especialidades.descricao as desccodiespe  ";
													comando += " from carrconsexam ";
													comando += " left join especialidades on (especialidades.codiespe=carrconsexam.codiespe) ";
													comando += " left join fornecedores on (fornecedores.uid=carrconsexam.uidfornecedor) ";
													comando += " left join caixas on (caixas.uid=fornecedores.uidcaixa) ";
													comando += " where (carrconsexam.uidcarrcomp='" + tblCarrComp.getString("uid") + "')";
													comando += " order by especialidades.uidfornecedor, especialidades.tipoproc, especialidades.grupo";
													ResultSet resultSet = tblCarrComp.getDatabase().executeSelect(comando);
													while (resultSet.next()) {
														System.out.println("tipoproc:" + resultSet.getString("tipoproc") + " - " + tipoproc);
														System.out.println("uidFornecedor:" + resultSet.getString("uidfornecedor") + " - " + uidFornecedor);
														System.out.println("grupo: " + resultSet.getString("grupo") + " - " + grupo);
														
														if ((resultSet.getString("tipoproc").equalsIgnoreCase("C")) || ((!uidFornecedor.equals(resultSet.getString("uidfornecedor"))) || (!tipoproc.equals(resultSet.getString("tipoproc"))) || (!grupo.equals(resultSet.getString("grupo"))))) {
															// Caso exista um recibo em aberto, isso é identificado pelo uidFornecedor preenchido, finaliza o recibo que esta aberto.
															if (uidFornecedor.length()>0) {
																System.out.println("finalizou o recibo que estava sendo emitido");
																tblMarcCons.setValue("vlrcustototal", vlrtotalcusto);
																tblMarcCons.setValue("vlrtotal", vlrtotalvenda);
																tblMarcCons.setValue("uidfornecedor", uidFornecedor);
																tblMarcCons.execute();
																
																String url = "http://" + tblParametros.getString("reportServer") + "/php-app/fichaconsulta2.php?recordId="+tblMarcCons.getLastInsertId();
																System.out.println(url);
																UI.getCurrent().getPage().open(url, "_blank", false);

																if (vlrtotalvenda>0) {
																	tblRecibos.insert();
																	tblRecibos.setValue("tiporeci", "R");
																	//tblRecibos.setValue("emissao", Utils.getJustDate(new Date()));
																	tblRecibos.setValue("emissao", Utils.getJustDate(tblCarrComp.getDate("data")));
																	tblRecibos.setValue("codiasso", tblCarrComp.getInteger("codiasso"));
																	tblRecibos.setValue("nome", tblCarrComp.getString("nome"));
																	tblRecibos.setValue("paciente", tblCarrComp.getString("paciente"));
																	tblRecibos.setValue("cep", tblAssociados.getString("cep"));
																	tblRecibos.setValue("tipo", tblAssociados.getString("tipo"));
																	tblRecibos.setValue("rua", tblAssociados.getString("rua"));
																	tblRecibos.setValue("numero", tblAssociados.getString("endenume"));
																	tblRecibos.setValue("complemento", tblAssociados.getString("complemento"));
																	tblRecibos.setValue("bairro", tblAssociados.getString("bai"));
																	tblRecibos.setValue("cidade", tblAssociados.getString("cid"));
																	tblRecibos.setValue("estado", tblAssociados.getString("uf"));
																	tblRecibos.setValue("codiforn", codiforn);
																	// Caso o fornecedor esteja programado para fechar em um caixa separado
																	// fecha no caixa do fornecedor, caso contrario, fecha no caixa do usuario logado
																	if ((vcodicaixforn!=null) && (!vcodicaixforn.isEmpty())) {
																		tblRecibos.setValue("codicaix", vcodicaixforn);
																	}
																	else {
																		tblRecibos.setValue("codicaix", vcodicaix);
																	}
																	tblRecibos.setValue("referente", referente);
																	tblRecibos.setValue("sequproc", tblMarcCons.getLastInsertId());
																	tblRecibos.setValue("tipoatend", tipoatend);
																	//tblRecibos.setValue("obsreci", value);
																	tblRecibos.setValue("vlrcusto", vlrtotalcusto);
																	tblRecibos.setValue("valor", vlrtotalvenda);
																	tblRecibos.setValue("sequcaix", getSequenciaCaixa(vcodicaix));
																	tblRecibos.setValue("uidcarrcomp", tblCarrComp.getString("uid"));
																	
																	tblRecibos.setValue("cortesia", tblCarrComp.getString("cortesia"));
																	tblRecibos.setValue("uidusualibe", tblCarrComp.getString("uidusualibe"));
																	
																	tblRecibos.execute();
																}

																for (String uidCarrConsExamItem: uidCarrConsExam) {
																	String comando2 = "update carrconsexam set numemarccons=" + tblMarcCons.getLastInsertId() + ", numereci=" + tblRecibos.getLastInsertId();
																	comando2 += "  where (carrconsexam.uid='" + uidCarrConsExamItem + "')";
																	tblCarrComp.getDatabase().executeCommand(comando2);
																}
																uidCarrConsExam = new ArrayList<String>();
																
																url = "http://" + tblParametros.getString("reportServer") + "/php-app/relarecibo2.php?recordId="+tblRecibos.getLastInsertId();
																System.out.println(url);
																UI.getCurrent().getPage().open(url, "_blank", false);
															}

															System.out.println("------------------------------------------");
															System.out.println("iniciando um novo recibo");
															
															uidFornecedor = resultSet.getString("uidfornecedor");
															codiforn = resultSet.getInt("codiforn");
															tipoproc = resultSet.getString("tipoproc");
															grupo = resultSet.getString("grupo");
															vcodicaixforn = resultSet.getString("codicaix");
															referente = "";
															numeitem = 1;
															vlrtotalcusto = 0;
															vlrtotalvenda = 0;
															emitindo = true;
															
															if (tipoproc.equalsIgnoreCase("C")) {
																tipoatend="C";
															}
															else {
																tipoatend="E";
															}
															
															if (uidFornecedor.isEmpty()) {
																uidFornecedor=UUID.randomUUID().toString().toUpperCase();
															}
															
															Table tblEspeMedi = tblCarrComp.getDatabase().loadTableByName("espemedi");
															tblEspeMedi.select("codiespe");
															tblEspeMedi.setFilter("codimedi", resultSet.getString("codimedi"));
															tblEspeMedi.loadData();
															
															// Cria a guia de atendimento
															tblMarcCons.insert();
															tblMarcCons.setValue("codiasso", tblCarrComp.getInteger("codiasso"));
															tblMarcCons.setValue("uidassociado", tblAssociados.getString("uid"));
															tblMarcCons.setValue("nome", tblCarrComp.getString("nome"));
															tblMarcCons.setValue("paciente", tblCarrComp.getString("paciente"));
															tblMarcCons.setValue("nasc", tblCarrComp.getDate("nasc"));
															
															//tblMarcCons.setValue("codiespe", resultSet.getInt("codiespe"));
															//tblMarcCons.setValue("codiespe_valor", resultSet.getDouble("vlrvenda"));
															//tblMarcCons.setValue("codiespe_vlrcusto", resultSet.getDouble("vlrcusto"));

															//tblMarcCons.setValue("codiespe", tblEspeMedi.getString("codiespe"));
															if (resultSet.getInt("codiespepadr")!=0) {
																tblMarcCons.setValue("codiespe", resultSet.getString("codiespepadr"));
															}
															else {
																tblMarcCons.setValue("codiespe", resultSet.getString("codiespe"));
															}
															tblMarcCons.setValue("codiespe_vlrcusto", 0);
															tblMarcCons.setValue("codiespe_valor", 0);
															
															tblMarcCons.setValue("codimedi", resultSet.getInt("codimedi"));
															
															//tblMarcCons.setValue("ulticons", resultSet.getDate("ulticons"));
															//tblMarcCons.setValue("ulticonsdias", resultSet.getInt("ulticonsdias"));
															UltimoAtendimento ultimoAtendimento = new UltimoAtendimento();
															ultimoAtendimento.updateUltimoAtendimento(tblCarrComp.getDate("data"), tblCarrComp.getInteger("codiasso"), tblCarrComp.getString("paciente"), resultSet.getInt("codiespe"));
															if (ultimoAtendimento.getDataCons()!=null) {
																tblMarcCons.setValue("ulticons", ultimoAtendimento.getDataCons());
																tblMarcCons.setValue("ulticonsdias", ultimoAtendimento.getUltiConsDias());
																tblMarcCons.setValue("ultitipoatend", ultimoAtendimento.getTipoAtend());
																tblMarcCons.setValue("ultiatendsequ", ultimoAtendimento.getSequencia());

																// Caso seja uma consulta, seleciona o me dico que atendeu o paciente pela ultima vez
																if (ultimoAtendimento.getTipoAtend().equalsIgnoreCase("C")) {
																	tblMarcCons.setValue("codimedi", ultimoAtendimento.getCodiMedi());
																}
															}
															
															tblMarcCons.setValue("tipoatend", tipoatend);
															tblMarcCons.setValue("atendido", "N");
															tblMarcCons.setValue("datacons", resultSet.getDate("datacons"));
															tblMarcCons.setValue("diasema", resultSet.getString("diasema"));
															tblMarcCons.setValue("horacons", resultSet.getString("horacons"));
															tblMarcCons.setValue("numeproc", 0);
															
															//tblMarcCons.setValue("vlrtotal", resultSet.getDouble("vlrvenda"));
															//tblMarcCons.setValue("vlrcustototal", resultSet.getDouble("vlrcusto"));
															
															tblMarcCons.setValue("uidcarrcomp", tblCarrComp.getString("uid"));
															
															tblMarcCons.setValue("cortesia", tblCarrComp.getString("cortesia"));
															tblMarcCons.setValue("uidusualibe", tblCarrComp.getString("uidusualibe"));
														}

														// Uid do item que esta sendo processado
														uidCarrConsExam.add(resultSet.getString("uid"));
														
														tblMarcCons.setValue("codiproc" + Utils.strZero(numeitem, 2), resultSet.getString("codiespe"));
														tblMarcCons.setValue("codiproc" + Utils.strZero(numeitem, 2) + "_vlrcusto", resultSet.getDouble("vlrcusto"));
														tblMarcCons.setValue("codiproc" + Utils.strZero(numeitem, 2)+ "_valor", resultSet.getDouble("vlrvenda"));
														
														if (!referente.isEmpty()) {
															referente += ", ";
														}
														
														String conteudo = resultSet.getString("desccodiespe").replace("(BIO LIDER)", "");
														conteudo = conteudo.replace("(FAL)", "");
														conteudo = conteudo.replace("(SANVIE)", "");
														conteudo = conteudo.replace("(URONORTE)", "");
														referente += conteudo;
														
														vlrtotalcusto += resultSet.getDouble("vlrcusto");
														vlrtotalvenda += resultSet.getDouble("vlrvenda");
														numeitem+=1;
														
														System.out.println(resultSet.getString("uid") + " " + resultSet.getString("uidfornecedor") + " " + resultSet.getString("tipoproc") + " " + resultSet.getString("grupo") + resultSet.getString("codimedi"));
													}
													
													if (emitindo) {
														System.out.println("finalizou o recibo que estava sendo emitido");
														tblMarcCons.setValue("vlrcustototal", vlrtotalcusto);
														tblMarcCons.setValue("vlrtotal", vlrtotalvenda);
														tblMarcCons.setValue("uidfornecedor", uidFornecedor);
														tblMarcCons.execute();
														
														String url = "http://" + tblParametros.getString("reportServer") + "/php-app/fichaconsulta2.php?recordId="+tblMarcCons.getLastInsertId();
														System.out.println(url);
														UI.getCurrent().getPage().open(url, "_blank", false);

														if (vlrtotalvenda>0) {
															tblRecibos.insert();
															tblRecibos.setValue("tiporeci", "R");
															//tblRecibos.setValue("emissao", Utils.getJustDate(new Date()));
															tblRecibos.setValue("emissao", Utils.getJustDate(tblCarrComp.getDate("data")));
															tblRecibos.setValue("codiasso", tblCarrComp.getInteger("codiasso"));
															tblRecibos.setValue("nome", tblCarrComp.getString("nome"));
															tblRecibos.setValue("paciente", tblCarrComp.getString("paciente"));
															tblRecibos.setValue("cep", tblAssociados.getString("cep"));
															tblRecibos.setValue("tipo", tblAssociados.getString("tipo"));
															tblRecibos.setValue("rua", tblAssociados.getString("rua"));
															tblRecibos.setValue("numero", tblAssociados.getString("endenume"));
															tblRecibos.setValue("complemento", tblAssociados.getString("complemento"));
															tblRecibos.setValue("bairro", tblAssociados.getString("bai"));
															tblRecibos.setValue("cidade", tblAssociados.getString("cid"));
															tblRecibos.setValue("estado", tblAssociados.getString("uf"));
															tblRecibos.setValue("codiforn", codiforn);

															// Caso o fornecedor esteja programado para fechar em um caixa separado
															// fecha no caixa do fornecedor, caso contrario, fecha no caixa do usuario logado
															if ((vcodicaixforn!=null) && (!vcodicaixforn.isEmpty())) {
																tblRecibos.setValue("codicaix", vcodicaixforn);
															}
															else {
																tblRecibos.setValue("codicaix", vcodicaix);
															}
															
															tblRecibos.setValue("referente", referente);
															tblRecibos.setValue("sequproc", tblMarcCons.getLastInsertId());
															tblRecibos.setValue("tipoatend", tipoatend);
															//tblRecibos.setValue("obsreci", value);
															tblRecibos.setValue("vlrcusto", vlrtotalcusto);
															tblRecibos.setValue("valor", vlrtotalvenda);
															tblRecibos.setValue("sequcaix", getSequenciaCaixa(vcodicaix));
															tblRecibos.setValue("uidcarrcomp", tblCarrComp.getString("uid"));
															
															tblRecibos.setValue("cortesia", tblCarrComp.getString("cortesia"));
															tblRecibos.setValue("uidusualibe", tblCarrComp.getString("uidusualibe"));
															
															tblRecibos.execute();
														}

														for (String uidCarrConsExamItem: uidCarrConsExam) {
															String comando2 = "update carrconsexam set numemarccons=" + tblMarcCons.getLastInsertId() + ", numereci=" + tblRecibos.getLastInsertId();
															comando2 += "  where (carrconsexam.uid='" + uidCarrConsExamItem + "')";
															tblCarrComp.getDatabase().executeCommand(comando2);
														}
														uidCarrConsExam = new ArrayList<String>();
														
														url = "http://" + tblParametros.getString("reportServer") + "/php-app/relarecibo2.php?recordId="+tblRecibos.getLastInsertId();
														System.out.println(url);
														UI.getCurrent().getPage().open(url, "_blank", false);
													}
													
													//Utils.ShowMessageWindow("Atenção!", "Processamento concluido com SUCESSO!", 500, 180, MessageWindowType.INFO);
													formCarrComp.getRmGrid().updateRecords();
													event.getRmFormWindow().close();
													formCarrComp.close();
													//event.getRmFormWindow().getRmGrid().updateRecords();
													//event.getRmFormWindow().close();
												}
												else {
													Table tblParametros = tblCarrComp.getDatabase().loadTableByName("parametros");
													tblParametros.select("*");
													tblParametros.loadData();
													
													String comando = "select * from marccons";
													comando += " where (marccons.uidcarrcomp='" + tblCarrComp.getString("uid") + "')";
													ResultSet resultSet = tblCarrComp.getDatabase().executeSelect(comando);
													while (resultSet.next()) {
														String url = "http://" + tblParametros.getString("reportServer") + "/php-app/fichaconsulta2.php?recordId="+resultSet.getInt("sequencia");
														System.out.println(url);
														UI.getCurrent().getPage().open(url, "_blank", false);
													}
													
													comando = "select * from recibos";
													comando += " where (recibos.uidcarrcomp='" + tblCarrComp.getString("uid") + "')";
													resultSet = tblCarrComp.getDatabase().executeSelect(comando);
													while (resultSet.next()) {
														String url = "http://" + tblParametros.getString("reportServer") + "/php-app/relarecibo2.php?recordId="+resultSet.getInt("sequencia");
														System.out.println(url);
														UI.getCurrent().getPage().open(url, "_blank", false);
													}
													
													//Utils.ShowMessageWindow("Atenção!", "Processamento concluido com SUCESSO!", 500, 180, MessageWindowType.INFO);
													event.getRmFormWindow().close();
													formCarrComp.close();
													
												}
											}
										}
										catch (Exception e) {
											System.out.println(e.getMessage());
										}
										finally {
											tblCarrComp.getDatabase().closeConnection();
										}
										
										// *********************************************************************
										
										window.close();
										//formCarrComp.close();
									}
								}
							});
							
							// Nao atualizao o grid e nao atualiza os botoes tambem
							// deve atualizar somente o numero de registros no grid se necessario
							//updateRecords();
							rmGrid.getTable().setLoadingDataToForm(true);
							formWindow.getBody().addComponent(rmGrid.getForm().deploy());
							rmGrid.getTable().setLoadingDataToForm(false);
							
							rmGrid.getTable().setValue("numeitens", event.getControlForm().getTable().getInteger("numeitens"));
							rmGrid.getTable().setValue("vlrcustototal", event.getControlForm().getTable().getDouble("vlrcustototal"));
							rmGrid.getTable().setValue("vlrtotal", event.getControlForm().getTable().getDouble("vlrtotal"));
							rmGrid.getTable().setValue("cortesia", event.getControlForm().getTable().getString("cortesia"));
							rmGrid.getTable().setValue("vlrdesconto", event.getControlForm().getTable().getDouble("vlrdesconto"));
							rmGrid.getTable().setValue("uidusualibe", event.getControlForm().getTable().getString("uidusualibe"));
							rmGrid.getTable().setValue("vlrtotalpagar", event.getControlForm().getTable().getDouble("vlrtotal")-event.getControlForm().getTable().getDouble("vlrdesconto"));
							rmGrid.getTable().setValue("vlrpagodinheiro", event.getControlForm().getTable().getDouble("vlrpagodinheiro"));
							rmGrid.getTable().setValue("vlrpagodebito", event.getControlForm().getTable().getDouble("vlrpagodebito"));
							rmGrid.getTable().setValue("vlrpagocredito", event.getControlForm().getTable().getDouble("vlrpagocredito"));
							rmGrid.getTable().setValue("numeparcelas", event.getControlForm().getTable().getDouble("numeparcelas"));
							rmGrid.getTable().setValue("vlrtotalpago", event.getControlForm().getTable().getDouble("vlrtotalpago"));
							rmGrid.getTable().setValue("troco", event.getControlForm().getTable().getDouble("troco"));
							
							formWindow.show();
						}
					}
				});
				
				
				/**/
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por associados:");
				controlForm.setWidth(1100d);
				controlForm.setHeight(280d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa por associados:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 90d);
					controlForm.addField("data", "Data", 110d);
					controlForm.addField("uidassociado", "Nome", 250d, 1);
					controlForm.addField("codiasso", "Cód.Associado", 130d);

					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 120d);
					controlForm.addField("processado", "Processado", 120d);
					controlForm.addField("cortesia", "Cortesia", 120d);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("carrcomp2");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("carrcomp2");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Carrinho de compras 2", true);
				//ui.getSystemView().addTab(new TesteView(), "Carrinho de compras 2", true);
			}
		});
	}
	
	public static String getCodigoCaixa() {
		Database database = ((ApplicationUI) UI.getCurrent()).getDatabase();
		String retorno = null;
		
		try {
			String login = database.getUserSystemLogged().getUserName();
			
			String comando2 = "select caixusua.usuario, caixas.codicaix from caixusua";
			comando2 += " left join caixas on (caixas.uid=caixusua.uidcaixa)";
			comando2 += " where (caixusua.usuario='" + login + "')";
			ResultSet resultSetCaixa = database.executeSelect(comando2);
			if (resultSetCaixa.next()) {
				retorno = resultSetCaixa.getString("codicaix");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return retorno;
	}
	
	public static Integer getSequenciaCaixa(String codicaix) {
		Database database = ((ApplicationUI) UI.getCurrent()).getDatabase();
		
		Integer retorno = null;
		
		if (codicaix.equalsIgnoreCase("C1")) {
			Table tblSequCaixC1 = database.loadTableByName("sequcaixc1");
			tblSequCaixC1.insert();
			tblSequCaixC1.execute();
			retorno = tblSequCaixC1.getLastInsertId();
		}
		else if (codicaix.equalsIgnoreCase("C2")) {
			Table tblSequCaixC2 = database.loadTableByName("sequcaixc2");
			tblSequCaixC2.insert();
			tblSequCaixC2.execute();
			retorno = tblSequCaixC2.getLastInsertId();
		}
		else if (codicaix.equalsIgnoreCase("C3")) {
			Table tblSequCaixC3 = database.loadTableByName("sequcaixc3");
			tblSequCaixC3.insert();
			tblSequCaixC3.execute();
			retorno = tblSequCaixC3.getLastInsertId();
		}
		
		return retorno;
	}
	
	public static void insertItem(Table tblCarrComp, ControlForm controlForm) {
		if ((tblCarrComp.getTableStatus()!=TableStatus.INSERT) && (tblCarrComp.getTableStatus()!=TableStatus.UPDATE)) {
			if (tblCarrComp.validate()) {
				tblCarrComp.execute();
			}
		}

		if ((tblCarrComp.getString("uidespecialidade")!=null) && (!tblCarrComp.getString("uidespecialidade").isEmpty())) {
			Table tblEspecialidades = tblCarrComp.getDatabase().loadTableByName("especialidades");
			tblEspecialidades.select("*");
			tblEspecialidades.setFilter("uid", tblCarrComp.getString("uidespecialidade"));
			tblEspecialidades.loadData();
			
			Table tblCarrConsExam = tblCarrComp.getTableChildren("carrconsexam");
			
			tblCarrConsExam.insert();
			tblCarrConsExam.setValue("uidcarrcomp", tblCarrComp.getString("uid"));
			tblCarrConsExam.setValue("uidespecialidade", tblCarrComp.getString("uidespecialidade"));
			tblCarrConsExam.setValue("codiespe", tblEspecialidades.getInteger("codiespe"));
			tblCarrConsExam.setValue("desccodiespe", tblEspecialidades.getString("descricao"));
			tblCarrConsExam.setValue("uidfornecedor", tblEspecialidades.getString("uidfornecedor"));
			tblCarrConsExam.setValue("codiforn", tblEspecialidades.getInteger("codiforn"));
			tblCarrConsExam.setValue("vlrcusto", tblEspecialidades.getDouble("vlrcusto"));
			tblCarrConsExam.setValue("vlrvenda", tblEspecialidades.getDouble("valor"));
			tblCarrConsExam.setValue("grupo", tblEspecialidades.getString("grupo"));
			tblCarrConsExam.execute();
			
			String uidLastRecord = tblCarrConsExam.getUidLastRecordProccessed();
			SimpleRecord simpleRecord = null;
			
			Table table = tblCarrComp.getDatabase().loadTableByName("carrconsexam");
			table.select(tblCarrConsExam.getFieldsInSelect());
			table.setFilter("uid", uidLastRecord);
			table.loadData();
			if (!table.eof()) {
				simpleRecord = table.getSimpleRecordList().get(0);
			}
			tblCarrConsExam.getSimpleRecordList().add(simpleRecord);
			
			List<ControlBase> lista = controlForm.getChildrensByClass(ControlGrid.class);
			for (ControlBase controlBase: lista) {
				ControlGrid controlGrid = (ControlGrid) controlBase;
				if (controlGrid.getRmGridName().equalsIgnoreCase("carrconsexam")) {
					controlGrid.getRmGrid().getGrid().setItems(tblCarrConsExam.getSimpleRecordList());
					controlGrid.getRmGrid().getGrid().select(simpleRecord);
					controlGrid.getRmGrid().getGrid().scrollToEnd();
					
					break;
				}
			}
			
			tblCarrComp.setString("uidespecialidade", null);
			tblCarrComp.setDouble("vlrcusto", null);
			tblCarrComp.setDouble("vlrvenda", null);
		}
		
		RmComboBox rmComboBox = (RmComboBox) tblCarrComp.fieldByName("uidespecialidade").getRmFieldBase();
		rmComboBox.focus();
	}
}
