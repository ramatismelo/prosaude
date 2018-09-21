package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent.ReverseProcessingEventListener;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent.DirectProcessingEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.window.RmFormButton;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.view.AgendaView;
import com.vaadin.ui.UI;

public class Medicos {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("medicos");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("medicos");
				tblTabela.addField("codimedi", FieldType.INTEGER, 10);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("situacao", FieldType.VARCHAR, 1);
				tblTabela.addField("tipotota", FieldType.VARCHAR, 1);
				tblTabela.setPrimaryKey("codimedi");
				tblTabela.setOrder("nome");
				
				tblTabela.addIndex("codimedi", "codimedi");
				
				tblTabela.setRequired("nome", true);
				tblTabela.setRequired("situacao", true);
				tblTabela.setRequired("tipotota", true);
				
				//tblTabela.fieldByName("codimedi").setReadOnly(true);
				tblTabela.fieldByName("codimedi").setReadOnlyUpdate(true);
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("A", "Ativo");
				internalSearch.addItem("D", "Destativo");
				tblTabela.fieldByName("situacao").setInternalSearch(internalSearch);
				
				InternalSearch internalSearchTipoTota = new InternalSearch();
				internalSearchTipoTota.addItem("1", "Número de atendimentos");
				internalSearchTipoTota.addItem("2", "Número de procedimentos");
				tblTabela.fieldByName("tipotota").setInternalSearch(internalSearchTipoTota);
				
				tblTabela.addDirectProcessingEventListener(new DirectProcessingEventListener() {
					@Override
					public void onDirectProcessing(DirectProcessingEvent event) {
						if (event.getTable().getString("situacao").equalsIgnoreCase("A")) {
							Table tblEspeMedi = event.getTable().getTableChildren("espemedi");
							
							Table tblRequisitantes = event.getTable().getDatabase().loadTableByName("requisitantes");
							tblRequisitantes.select("uid");
							tblRequisitantes.setFilter("intref", "medicos-"+event.getTable().getString("uid"));
							tblRequisitantes.loadData();
							if (tblRequisitantes.eof()) {
								tblRequisitantes.insert();
								tblRequisitantes.setValue("descricao", event.getTable().getString("nome"));
								if (!tblEspeMedi.eof()) {
									tblRequisitantes.setValue("especialidade", tblEspeMedi.getString("desccodiespe"));
								}
								tblRequisitantes.setValue("intref", "medicos-"+ event.getTable().getString("uid"));
								tblRequisitantes.execute();
							}
						}
					}
				});
				
				tblTabela.addReverseProcessingEventListener(new ReverseProcessingEventListener() {
					@Override
					public void onReverseProcessing(ReverseProcessingEvent event) {
						Table tblRequisitantes = event.getTable().getDatabase().loadTableByName("requisitantes");
						tblRequisitantes.select("uid");
						tblRequisitantes.setFilter("intref", "medicos-"+event.getTable().getString("uid"));
						tblRequisitantes.loadData();
						if (!tblRequisitantes.eof()) {
							tblRequisitantes.delete();
							tblRequisitantes.setFilter("intref", "medicos-"+event.getTable().getString("uid"));
							tblRequisitantes.execute();
						}
					}
				});
				
				Table tblEspeMedi = database.loadTableByName("espemedi");
				tblTabela.addTableChild(tblEspeMedi, "uidmedico", true);
				
				Table tblAgenda = database.loadTableByName("agenda");
				tblTabela.addTableChild(tblAgenda, "uidmedico", true);
				
				Table tblAlteAgenMedi = database.loadTableByName("alteagenmedi");
				tblTabela.addTableChild(tblAlteAgenMedi, "codimedi", "codimedi", true);
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("medicos");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("medicos");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("codimedi", "Cód.Medico", 110d);
				rmGrid.addField("nome", "Nome do médico", 100d, 1);
				rmGrid.addField("situacao", "Situação", 100d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de médicos:");
				controlForm.setWidth(800d);
				controlForm.setHeight(800d);
				
				RmFormButtonBase btnAgenda = controlForm.addRmFormButton("Visualizar agenda");
				btnAgenda.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
					@Override
					public void onRmFormButtonClick(RmFormButtonClickEvent event) {
						Table tblEspeMedi = event.getControlForm().getTable().getTableChildren("espemedi");
						if (!tblEspeMedi.eof()) {
							Integer codigoEspecialidade = tblEspeMedi.getSimpleRecordList().get(0).getInteger("codiespe");
							Integer codigoMedico = event.getControlForm().getTable().getInteger("codimedi");
							String tipoAtendimento = "C";

							AgendaView agendaView = new AgendaView(codigoEspecialidade, codigoMedico, tipoAtendimento, null);
							
							RmFormWindow formWindow = new RmFormWindow();
							formWindow.setTitle("Agendamento de consultas:");
							formWindow.setWidth("1100px");
							formWindow.setHeight("600px");
							formWindow.getBody().addComponent(agendaView);
							
							RmFormButtonBase buttonBase = formWindow.addCancelButton();
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
				});
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do médico:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codimedi", "Cód.Medico", 100d);
					controlForm.addField("nome", "Nome do médico", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("situacao", "Situação", 100d, 1);
					controlForm.addField("tipotota", "Tipo de totalização", 100d, 1);
				}
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("especialidades", "Especialidades que o médico atende:", SectionState.MAXIMIZED, 400d);
				{
					controlForm.addRmGrid("espemedi");
				}
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("agenda", "Programação da agenda de atendimento:", SectionState.MAXIMIZED, 450d);
				{
					controlForm.addRmGrid("agenda");
				}
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("agenda", "Alteração na agenda médica:", SectionState.MAXIMIZED, 550d);
				{
					controlForm.addRmGrid("alteagenmedi");
				}
				//******************************************************
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por Médicos:");
				controlForm.setWidth(600d);
				controlForm.setHeight(450d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Manutenção de médicos:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codimedi", "Cód.Medico", 100d);
					controlForm.addField("nome", "Nome do médico", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("situacao", "Situação", 100d, 1);
					controlForm.addField("tipotota", "Tipo de totalização", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("medicos");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("medicos");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Médicos", true);
			}
		});
	}
}
