package com.example.myapplication.tables;

import java.nio.file.ClosedWatchServiceException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.Days;

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
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent.BeforeForeingSearchEventListener;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent.AfterValidateOnEditEventListener;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent.ReverseProcessingEventListener;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent.DirectProcessingEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent.SpecialConditionOfDeleteEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfInsertEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent.SpecialConditionOfUpdateEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfInsertEvent.SpecialConditionOfInsertEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
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
import com.example.myapplication.view.Agenda2View;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class CarrCons {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("carrcons");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("carrcons");
				tblTabela.addField("uidcarrcomp", FieldType.VARCHAR, 50);
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("desccodiespe", FieldType.VARCHAR, 50);
				tblTabela.addField("ulticons", FieldType.DATE, 10);
				tblTabela.addField("ulticonsdias", FieldType.INTEGER, 10);
				tblTabela.addField("tipoatend", FieldType.VARCHAR, 1);
				tblTabela.addField("desctipoatend", FieldType.VARCHAR, 10);
				tblTabela.addField("codimedi", FieldType.INTEGER, 10);
				tblTabela.addField("desccodimedi", FieldType.VARCHAR, 50);
				tblTabela.addField("datacons", FieldType.DATE, 10);
				tblTabela.addField("diasema", FieldType.VARCHAR, 50);
				tblTabela.addField("horacons", FieldType.VARCHAR, 5);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvenda", FieldType.DOUBLE, 10);
				tblTabela.addField("numemarccons", FieldType.INTEGER, 10); // numero da guia de atendimento onde foi incluido esta consulta
				tblTabela.addField("numereci", FieldType.INTEGER, 10); // numero do recibo onde foi incluida essa consulta
				tblTabela.setPrimaryKey("uid");
				tblTabela.setOrder("especialidades.descricao");
				
				tblTabela.addJoin("especialidades", "codiespe", "codiespe");
				tblTabela.addJoin("medicos", "codimedi", "codimedi");
				
				tblTabela.fieldByName("desccodiespe").setExpression("especialidades.descricao");
				tblTabela.fieldByName("desccodimedi").setExpression("medicos.nome");
				
				tblTabela.fieldByName("desccodiespe").setReadOnly(true);
				tblTabela.fieldByName("codimedi").setReadOnly(true);
				tblTabela.fieldByName("desccodimedi").setReadOnly(true);
				tblTabela.fieldByName("ulticons").setReadOnly(true);
				tblTabela.fieldByName("ulticonsdias").setReadOnly(true);
				tblTabela.fieldByName("desctipoatend").setReadOnly(true);
				tblTabela.fieldByName("datacons").setReadOnly(true);
				tblTabela.fieldByName("diasema").setReadOnly(true);
				tblTabela.fieldByName("horacons").setReadOnly(true);
				tblTabela.fieldByName("vlrvenda").setReadOnly(true);
				
				tblTabela.fieldByName("codiespe").setRequired(true);
				//tblTabela.fieldByName("codimedi").setRequired(true);
				//tblTabela.fieldByName("datacons").setRequired(true);
				//tblTabela.fieldByName("horacons").setRequired(true);
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("C", "CONSULTA");
				internalSearch.addItem("R", "RETORNO");
				internalSearch.addItem("E", "EXAME");
				tblTabela.fieldByName("tipoatend").setInternalSearch(internalSearch);
				
				tblTabela.fieldByName("desctipoatend").getValueOfInternalSearch();
				
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
						event.getTargetTable().setMainFilter("tipoprocresu", "C");
					}
				});
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiespe").addExistenceCheck("especialidades", "codiespe", "codiespe", "Especialidade inválido!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodiespe", event.getTargetTable().getString("descricao"));
						if (event.getSourceTable().getString("tipoatend").equalsIgnoreCase("C")) {
							event.getSourceTable().setValue("vlrcusto", event.getTargetTable().getDouble("vlrcusto"));
							event.getSourceTable().setValue("vlrvenda", event.getTargetTable().getDouble("valor"));
						}
						else {
							event.getSourceTable().setValue("vlrcusto", 0);
							event.getSourceTable().setValue("vlrvenda", 0);
						}
					}
				});
				
				tblTabela.fieldByName("codiespe").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						// Deve ser melhorado levando em conta a data da consulta e uma modificacao no registro
						String vcodiasso = event.getTable().getMasterTable().getString("codiasso");
						String vpaciente = event.getTable().getMasterTable().getString("paciente");
						String vcodiespe = event.getTable().getString("codiespe");
	
						// Esse codigo deve ser executado somente se o conteudo de codiespe for alterado, com a finalidade
						// de atualizar os dados da ultima consulta da especialidade selecionada
						String comando ="select marccons.datacons, marccons.tipoatend, marccons.codimedi, medicos.nome from marccons";
						comando += " left join medicos on (medicos.codimedi=marccons.codimedi)";
						comando += "where (codiasso=" + vcodiasso + ") and (paciente='" + vpaciente + "') and (codiespe="+vcodiespe+")";
						comando += "order by datacons desc, sequencia desc limit 1";
						
						try {
							event.getTable().getDatabase().openConnection();
							ResultSet resultSet = event.getTable().getDatabase().executeSelect(comando);
							if (resultSet.next()) {
								// Atualiza dados da consulta
								event.getTable().setValue("ulticons", resultSet.getDate("datacons"));
								int dias = (int) Utils.getDateDiff(resultSet.getDate("datacons"), new Date(), TimeUnit.DAYS);
								event.getTable().setValue("ulticonsdias", dias);
								if (dias<=20) {
									event.getTable().setValue("tipoatend", "R");
									event.getTable().setValue("desctipoatend", "RETORNO");
								}
								
								// Atualiza dados do médico
								Integer vcodimedi = resultSet.getInt("codimedi");
								comando = "select codimedi, nome, situacao from medicos";
								comando += " where (medicos.codimedi=" + vcodimedi + ")";
								resultSet = event.getTable().getDatabase().executeSelect(comando);
								if (resultSet.next()) {
									// Caso o medico esteja ativo
									if (resultSet.getString("situacao").equalsIgnoreCase("A")) {
										event.getTable().setValue("codimedi", vcodimedi);
										event.getTable().setValue("desccodimedi", resultSet.getString("nome"));
									}
								}
							}
							else {
								event.getTable().setLoadingDataToForm(true);
								event.getTable().setDate("ulticons", null);
								event.getTable().setInteger("ulticonsdias", null);
								event.getTable().setValue("tipoatend", "C");
								event.getTable().setValue("desctipoatend", "CONSULTA");
								event.getTable().setInteger("codimedi", 0);
								event.getTable().setString("desccodimedi", "");
								event.getTable().setDate("datacons", null);
								event.getTable().setString("horacons", "");
								event.getTable().setLoadingDataToForm(false);
							}
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
						finally {
							event.getTable().getDatabase().closeConnection();
						}
					}
				});
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("tipoatend", "C");
						event.getTable().setValue("desctipoatend", "CONSULTA");
						event.getTable().setDate("ulticons", null);
						event.getTable().setInteger("ulticonsdias", null);
						event.getTable().setInteger("codimedi", null);
						event.getTable().setString("desccodimedi", "");
					}
				});
				
				tblTabela.addDirectProcessingEventListener(new DirectProcessingEventListener() {
					@Override
					public void onDirectProcessing(DirectProcessingEvent event) {
						String uidMaster = event.getTable().getMasterTable().getString("uid");
						event.getTable().getMasterTable().update(uidMaster, false);
						event.getTable().getMasterTable().setDouble("vlrtotal", event.getTable().getMasterTable().getDouble("vlrtotal") + event.getTable().getDouble("vlrvenda"));
						event.getTable().getMasterTable().setDouble("vlrcustototal", event.getTable().getMasterTable().getDouble("vlrcustototal") + event.getTable().getDouble("vlrcusto"));
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
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("carrcons");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("carrcons");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setHeight("600px");
				rmGrid.setLimit(4);
				rmGrid.addField("desccodiespe", "Especialidade médica", 200d);
				rmGrid.addField("desctipoatend", "Tipo de atendimento", 100d);
				rmGrid.addField("vlrvenda", "Valor", 100d);
				rmGrid.addField("desccodimedi", "Médico", 200d);
				rmGrid.addField("datacons", "Data da consulta", 100d);
				rmGrid.addField("horacons", "Horário", 100d);
				rmGrid.addField("diasema", "Dia da semana", 100d);
				rmGrid.addField("numemarccons", "Guia Atendimento", 100d);
				rmGrid.addField("numereci", "Recibo", 100d);
				
				rmGrid.setAllowPrint(false);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de consultas:");
				controlForm.setWidth(790d);
				controlForm.setHeight(440d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados da consulta:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Código", 120d);
					controlForm.addField("desccodiespe", "Especialidade médica", 200d, 1);
					controlForm.addField("ulticons", "Última consulta", 120d);
					controlForm.addField("ulticonsdias", "Dias", 70d);
					controlForm.addField("desctipoatend", "Tipo de atendimento", 140d);

					controlForm.addNewLine();
					ControlButton controlButton = controlForm.addButton("Agenda");
					controlButton.addControlButtonClickEventListener(new ControlButtonClickEventListener() {
						@Override
						public void onControlButtonClick(ControlButtonClickEvent event) {
							if (event.getRmForm().getTable().fieldByName("codiespe").validate()) {
								Integer codigoEspecialidade = event.getRmForm().getTable().getInteger("codiespe");
								Integer codigoMedico = event.getRmForm().getTable().getInteger("codimedi");
								String tipoAtendimento = event.getRmForm().getTable().getString("tipoatend");
								Agenda2View agenda2 = new Agenda2View(codigoEspecialidade, codigoMedico, tipoAtendimento, true);
								
								RmFormWindow formWindow = new RmFormWindow();
								formWindow.setTitle("Agendamento de consultas:");
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
							}
						}
					});
					
					controlForm.addField("codimedi", "Cód.Médico", 120d);
					controlForm.addField("desccodimedi", "Médico", 200d, 1);
					
					//controlForm.addNewLine();
					//controlForm.addField("datacons", "Data da consulta", 130d, 1);
					//controlForm.addField("diasema", "Dia da semana", 110d, 1);
					//controlForm.addField("horacons", "Hora da consulta", 120d, 1);
					//controlForm.addField("vlrvenda", "Valor da consulta", 200d, 1);
					controlForm.addField("vlrvenda", "Valor da consulta", 150d);
				}
				
				ControlButton btnRetorno = controlForm.addButton("Gerar retorno");
				btnRetorno.addControlButtonClickEventListener(new ControlButtonClickEventListener() {
					@Override
					public void onControlButtonClick(ControlButtonClickEvent event) {
						System.out.println("Gerou o retorno ta");
					}
				});
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por consultas:");
				controlForm.setWidth(600d);
				controlForm.setHeight(280d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados da consulta:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Cód.Especialidade", 90d);
					controlForm.addField("desccodiespe", "Especialidade médica", 200d);
					controlForm.addField("ulticons", "Última consulta", 100d);
					controlForm.addField("ulticonsdias", "Dias", 100d);
					controlForm.addField("tipoatend", "Tipo de atendimento", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("codimedi", "Cód.Médico", 90d);
					controlForm.addField("desccodimedi", "Médico", 200d);
					controlForm.addField("datacons", "Data da consulta", 100d);
					controlForm.addField("diasema", "Dia da semana", 100d);
					controlForm.addField("horacons", "Hora da consulta", 100d);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("carrcons");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("carrcons");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Consultas programadas", true);
			}
		});
	}
}
