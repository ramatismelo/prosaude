package com.example.myapplication.tables;

import java.sql.ResultSet;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent.AfterValidateOnEditEventListener;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent.BeforeForeingSearchEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent.DirectProcessingEventListener;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent.ReverseProcessingEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfInsertEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent.SpecialConditionOfDeleteEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfInsertEvent.SpecialConditionOfInsertEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent.SpecialConditionOfUpdateEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlButton;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent.ControlButtonClickEventListener;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.view.AgendaFornecedorView;
import com.vaadin.ui.UI;

public class CarrExamLabo {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("carrexamlabo");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("carrexamlabo");
				tblTabela.addField("uidcarrcomp", FieldType.VARCHAR, 50);
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("desccodiespe", FieldType.VARCHAR, 50);
				tblTabela.addField("codimedi", FieldType.INTEGER, 10);
				tblTabela.addField("desccodimedi", FieldType.VARCHAR, 50);
				tblTabela.addField("uidfornecedor", FieldType.VARCHAR, 50);
				tblTabela.addField("codiforn", FieldType.INTEGER, 10);
				tblTabela.addField("datacons", FieldType.DATE, 10);
				tblTabela.addField("diasema", FieldType.VARCHAR, 50);
				tblTabela.addField("horacons", FieldType.VARCHAR, 5);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvenda", FieldType.DOUBLE, 10);
				tblTabela.addField("agendamento", FieldType.VARCHAR, 1);
				tblTabela.addField("numemarccons", FieldType.INTEGER, 10); // numero da guia de atendimento onde foi incluido esta consulta
				tblTabela.addField("numereci", FieldType.INTEGER, 10); // numero do recibo onde foi incluida essa consulta
				tblTabela.addField("grupo", FieldType.VARCHAR, 50); 
				tblTabela.setPrimaryKey("uid");
				tblTabela.setOrder("especialidades.descricao");
				
				tblTabela.addJoin("especialidades", "codiespe", "codiespe");
				tblTabela.addJoin("medicos", "codimedi", "codimedi");
				tblTabela.addJoin("espeforn", "uidfornecedor_codiespe", "uidfornecedor, codiespe");
				
				tblTabela.fieldByName("desccodiespe").setExpression("especialidades.descricao");
				tblTabela.fieldByName("desccodimedi").setExpression("medicos.nome");
				tblTabela.fieldByName("agendamento").setExpression("especialidades.agendamento");
				tblTabela.fieldByName("grupo").setExpression("espeforn.grupo");
				
				tblTabela.fieldByName("desccodiespe").setReadOnly(true);
				tblTabela.fieldByName("codimedi").setReadOnly(true);
				tblTabela.fieldByName("desccodimedi").setReadOnly(true);
				tblTabela.fieldByName("datacons").setReadOnly(true);
				tblTabela.fieldByName("diasema").setReadOnly(true);
				tblTabela.fieldByName("horacons").setReadOnly(true);
				tblTabela.fieldByName("vlrvenda").setReadOnly(true);
				
				tblTabela.fieldByName("codiespe").setRequired(true);
				//tblTabela.fieldByName("codimedi").setRequired(true);
				//tblTabela.fieldByName("datacons").setRequired(true);
				//tblTabela.fieldByName("horacons").setRequired(true);
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("codiespe").addForeingSearch();
				foreingSearch.setTargetRmGridName("especialidades");
				foreingSearch.setTargetIndexName("codiespe");
				foreingSearch.setRelationship("codiespe");
				foreingSearch.setReturnFieldName("codiespe");
				foreingSearch.setTitle("Pesquisa por ESPECIALIDADES:");
				foreingSearch.setOrder("descricao");
				//foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				foreingSearch.addBeforeForeingSearchEventListener(new BeforeForeingSearchEventListener() {
					@Override
					public void onBeforeForeingSearch(BeforeForeingSearchEvent event) {
						event.getTargetTable().setMainFilter("tipoprocresu", "E");
					}
				});

				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiespe").addExistenceCheck("especialidades", "codiespe", "codiespe", "Especialidade inválido!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodiespe", event.getTargetTable().getString("descricao"));
						event.getSourceTable().setValue("vlrvenda", event.getTargetTable().getDouble("valor"));
						event.getSourceTable().setValue("agendamento", event.getTargetTable().getString("agendamento"));
					}
				});
				
				tblTabela.fieldByName("codiespe").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						// Caso o agendamento não tenha que ser obrigatorio
						if ((event.getTable().getString("agendamento")!=null) && (!event.getTable().getString("agendamento").equalsIgnoreCase("1"))) {
							// erifica se ja foi utilizado um fornecedor nesse carrinho, caso tenha sido
							// verifica se o ultimo fornecedor que foi utilizado pode atender esse pedido
							if (event.getTable().getMasterTable().getString("uidfornecedor")!=null) {
								try {
									String comando = "select espeforn.vlrcusto, medicos.codimedi, medicos.nome from espeforn";
									comando += " left join fornecedores on (fornecedores.uid=espeforn.uidfornecedor)";
									comando += " left join medicos on (medicos.codimedi=fornecedores.codimedi)";
									comando += " where (espeforn.uidfornecedor='" + event.getTable().getMasterTable().getString("uidfornecedor") + "') and (espeforn.codiespe=" + event.getTable().getInteger("codiespe")+ ")";
									event.getTable().getDatabase().openConnection();
									ResultSet resultSet = event.getTable().getDatabase().executeSelect(comando);
									if (resultSet.next()) {
										event.getTable().setValue("uidfornecedor", event.getTable().getMasterTable().getString("uidfornecedor"));
										event.getTable().setValue("codiforn", event.getTable().getMasterTable().getInteger("codiforn"));
										event.getTable().setValue("codimedi", resultSet.getInt("codimedi"));
										event.getTable().setValue("desccodimedi", resultSet.getString("nome"));
										event.getTable().setValue("datacons", event.getTable().getMasterTable().getDate("datacons"));
										event.getTable().setValue("diasema", event.getTable().getMasterTable().getString("diasema"));
										event.getTable().setValue("horacons", event.getTable().getMasterTable().getString("horacons"));
										event.getTable().setValue("vlrcusto", resultSet.getDouble("vlrcusto"));
									}
								}
								catch (Exception e) {
									System.out.println(e.getMessage());
								}
								finally {
									event.getTable().getDatabase().closeConnection();
								}
							}
						}
					}
				});
				
				tblTabela.addDirectProcessingEventListener(new DirectProcessingEventListener() {
					@Override
					public void onDirectProcessing(DirectProcessingEvent event) {
						String uidMaster = event.getTable().getMasterTable().getString("uid");
						event.getTable().getMasterTable().update(uidMaster, false);
						event.getTable().getMasterTable().setDouble("vlrtotal", event.getTable().getMasterTable().getDouble("vlrtotal") + event.getTable().getDouble("vlrvenda"));
						event.getTable().getMasterTable().setDouble("vlrcustototal", event.getTable().getMasterTable().getDouble("vlrcustototal") + event.getTable().getDouble("vlrcusto"));
						
						// Dados para ser usado na inclusao do proximo exame
						if ((event.getTable().getString("agendamento")!=null) && (!event.getTable().getString("agendamento").equalsIgnoreCase("1"))) {
							event.getTable().getMasterTable().setInteger("codimedi", event.getTable().getInteger("codimedi"));
							event.getTable().getMasterTable().setString("uidfornecedor", event.getTable().getString("uidfornecedor"));
							event.getTable().getMasterTable().setInteger("codiforn", event.getTable().getInteger("codiforn"));
							event.getTable().getMasterTable().setDate("datacons", event.getTable().getDate("datacons"));
							event.getTable().getMasterTable().setString("diasema", event.getTable().getString("diasema"));
							event.getTable().getMasterTable().setString("horacons", event.getTable().getString("horacons"));
						}
						
						event.getTable().getMasterTable().execute();
					}
				});
				
				tblTabela.addReverseProcessingEventListener(new ReverseProcessingEventListener() {
					@Override
					public void onReverseProcessing(ReverseProcessingEvent event) {
						String uidMaster = event.getTable().getMasterTable().getString("uid");
						event.getTable().getMasterTable().update(uidMaster, false);
						event.getTable().getMasterTable().setDouble("vlrtotal", event.getTable().getMasterTable().getDouble("vlrtotal") - event.getTable().getDouble("vlrvenda"));
						event.getTable().getMasterTable().setDouble("vlrcustototal", event.getTable().getMasterTable().getDouble("vlrcustototal") - event.getTable().getDouble("vlrcusto"));
						event.getTable().getMasterTable().execute();
					}
				});
				
				tblTabela.addSpecialConditionOfInsertEventListener(new SpecialConditionOfInsertEventListener() {
					@Override
					public void onSpecialConditionOfInsert(SpecialConditionOfInsertEvent event) {
						if (event.getTable().getMasterTable().getDate("processado")!=null) {
							event.setValid(false);
							event.setErrorMessage("Não é possivel incluir registros em um carrinho já finalizado!");
						}
					}
				});
				
				tblTabela.addSpecialConditionOfUpdateEventListener(new SpecialConditionOfUpdateEventListener() {
					@Override
					public void onSpecialConditionOfUpdate(SpecialConditionOfUpdateEvent event) {
						if (event.getTable().getMasterTable().getDate("processado")!=null) {
							event.setValid(false);
							event.setErrorMessage("Não é possivel alterar registros em um carrinho já finalizado!");
						}
					}
				});
				
				tblTabela.addSpecialConditionOfDeleteEventListener(new SpecialConditionOfDeleteEventListener() {
					@Override
					public void onSpecialConditionOfDelete(SpecialConditionOfDeleteEvent event) {
						if (event.getTable().getMasterTable().getDate("processado")!=null) {
							event.setValid(false);
							event.setErrorMessage("Não é possivel excluir registros em um carrinho já finalizado!");
						}
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("carrexamlabo");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("carrexamlabo");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setHeight("600px");
				rmGrid.setLimit(4);
				rmGrid.addField("desccodiespe", "Exame/Procedimento", 200d);
				rmGrid.addField("vlrvenda", "Valor", 100d);
				rmGrid.addField("desccodimedi", "Clinica/Executor", 200d);
				rmGrid.addField("datacons", "Data", 100d);
				rmGrid.addField("horacons", "Horário", 100d);
				rmGrid.addField("diasema", "Dia da semana", 100d);
				rmGrid.addField("grupo", "Grupo", 100d);
				rmGrid.addField("numemarccons", "Guia Atendimento", 100d);
				rmGrid.addField("numereci", "Recibo", 100d);
				
				rmGrid.setAllowPrint(false);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Requisição de exames e procedimentos:");
				controlForm.setWidth(790d);
				controlForm.setHeight(440d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do exame/procedimento:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 120d);
					controlForm.addField("desccodiespe", "Exame/Procedimento", 200d, 1);

					controlForm.addNewLine();
					ControlButton controlButton = controlForm.addButton("Agenda");
					controlButton.addControlButtonClickEventListener(new ControlButtonClickEventListener() {
						@Override
						public void onControlButtonClick(ControlButtonClickEvent event) {
							if (event.getRmForm().getTable().fieldByName("codiespe").validate()) {
								Integer codigoEspecialidade = event.getRmForm().getTable().getInteger("codiespe");
								//Integer codigoMedico = event.getRmForm().getTable().getInteger("codimedi");
								Integer codigoMedico = null;
								
								AgendaFornecedorView agenda2 = new AgendaFornecedorView(codigoEspecialidade, codigoMedico, true);
								
								RmFormWindow formWindow = new RmFormWindow();
								formWindow.setTitle("Agendamento de exames/procedimentos:");
								formWindow.setWidth("800px");
								formWindow.setHeight("600px");
								formWindow.getBody().addComponent(agenda2);
								agenda2.setRmFormWindow(formWindow);
								agenda2.setTable(event.getRmForm().getTable());
								
								RmFormButtonBase buttonBase = formWindow.addCancelButton();
								buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										RmFormWindow window = (RmFormWindow) event.getWindow();
										window.close();
									}
								});
								
								buttonBase = formWindow.addSaveButton();
								buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										System.out.println(agenda2.getDataSelecionadaAgenda());
									}
								});

								formWindow.show();
							}
						}
					});
					
					controlForm.addField("codimedi", "Código", 120d);
					controlForm.addField("desccodimedi", "Clinica/Profissional", 200d, 1);

					/*
					controlForm.addNewLine();
					controlForm.addField("datacons", "Data do agendamento", 130d, 1);
					controlForm.addField("diasema", "Dia da semana", 110d, 1);
					controlForm.addField("horacons", "Hora do agendamento", 120d, 1);
					*/
					controlForm.addField("vlrvenda", "Valor", 200d);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Requisição de exames e procedimentos:");
				controlForm.setWidth(790d);
				controlForm.setHeight(440d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do exame/procedimento:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 120d);
					controlForm.addField("desccodiespe", "Exame/Procedimento", 200d, 1);

					controlForm.addNewLine();
					controlForm.addField("codimedi", "Código", 120d);
					controlForm.addField("desccodimedi", "Clinica/Profissional", 200d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("datacons", "Data do agendamento", 130d, 1);
					controlForm.addField("diasema", "Dia da semana", 110d, 1);
					controlForm.addField("horacons", "Hora do agendamento", 120d, 1);
					controlForm.addField("vlrvenda", "Valor", 200d, 1);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("carrexamlabo");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("carrexamlabo");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Consultas programadas", true);
			}
		});
	}
}
