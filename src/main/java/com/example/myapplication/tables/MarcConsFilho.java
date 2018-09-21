package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
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
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class MarcConsFilho {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("marcconsfilho");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setDebugQuery(true);
				tblTabela.setTableName("marccons");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("codiasso", FieldType.INTEGER, 10);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("paciente", FieldType.VARCHAR, 50);
				tblTabela.addField("nasc", FieldType.DATE, 10);
				tblTabela.addField("cortesia", FieldType.VARCHAR, 1);
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("desccodiespe", FieldType.VARCHAR, 50);
				tblTabela.addField("codiespe_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiespe_vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("ulticons", FieldType.DATE, 10);
				tblTabela.addField("ulticonsdias", FieldType.INTEGER, 10);
				tblTabela.addField("tipoatend", FieldType.VARCHAR, 1);
				tblTabela.addField("desctipoatend", FieldType.VARCHAR, 50);
				tblTabela.addField("atendido", FieldType.VARCHAR, 1);
				tblTabela.addField("datacons", FieldType.DATE, 10);
				tblTabela.addField("diasema", FieldType.VARCHAR, 50);
				tblTabela.addField("horacons", FieldType.VARCHAR, 5);
				tblTabela.addField("codimedi", FieldType.INTEGER, 10);
				tblTabela.addField("desccodimedi", FieldType.VARCHAR, 50);
				tblTabela.addField("numeproc", FieldType.INTEGER, 10);
				
				tblTabela.addField("codiproc01", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc01_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc01_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc02", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc02_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc02_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc03", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc03_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc03_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc04", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc04_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc04_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc05", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc05_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc05_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc06", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc06_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc06_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc07", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc07_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc07_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc08", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc08_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc08_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc09", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc09_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc09_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc10", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc10_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc10_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc11", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc11_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc11_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc12", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc12_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc12_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc13", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc13_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc13_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc14", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc14_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc14_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc15", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc15_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc15_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc16", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc16_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc16_vlrcusto", FieldType.DOUBLE, 10);

				tblTabela.addField("codiproc17", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc17_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc17_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc18", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc18_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc18_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc19", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc19_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc19_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc20", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc20_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc20_vlrcusto", FieldType.DOUBLE, 10);

				tblTabela.addField("codiproc21", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc21_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc21_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc22", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc22_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc22_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc23", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc23_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc23_vlrcusto", FieldType.DOUBLE, 10);

				tblTabela.addField("codiproc24", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc24_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc24_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc25", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc25_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc25_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc26", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc26_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc26_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc27", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc27_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc27_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc28", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc28_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc28_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc29", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc29_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc29_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc30", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc30_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc30_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc31", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc31_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc31_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codiproc32", FieldType.INTEGER, 10);
				tblTabela.addField("codiproc32_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiproc32_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("vlrtotal", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrcustototal", FieldType.DOUBLE, 10);
				tblTabela.addField("obsreci", FieldType.TEXT, 250);
				tblTabela.addField("observacoes", FieldType.TEXT, 250);
				
				tblTabela.addField("uidcarrcomp", FieldType.VARCHAR, 50);
				
				tblTabela.setPrimaryKey("sequencia");
				tblTabela.setOrder("datacons desc, horacons desc, sequencia desc");
				
				tblTabela.addIndex("datacons_horacons_sequencia", "datacons, horacons, sequencia");
				tblTabela.addIndex("codiasso", "codiasso");
				tblTabela.addIndex("codiespe_datacons_horacons", "codiespe, datacons, horacons");
				tblTabela.addIndex("codimedi_datacons_horacons", "codimedi, datacons, horacons");
				tblTabela.addIndex("cortesia_datacons_horacons", "cortesia, datacons, horacons");
				tblTabela.addIndex("tipoatend_datacons_horacons",  "tipoatend, datacons, horacons" );
				
				tblTabela.addJoin("especialidades", "codiespe", "codiespe");
				tblTabela.fieldByName("desccodiespe").setExpression("especialidades.descricao");
				
				tblTabela.addJoin("medicos", "codimedi", "codimedi");
				tblTabela.fieldByName("desccodimedi").setExpression("medicos.nome");
				
				Field field = tblTabela.fieldByName("sequencia");
				field.setAutoIncrement(true);
				field.setReadOnly(true);
				
				tblTabela.fieldByName("desctipoatend").setValueOfInternalSearch("tipoatend");
				
				tblTabela.fieldByName("codiasso").setReadOnly(true);
				tblTabela.fieldByName("nome").setReadOnly(true);
				tblTabela.fieldByName("diasema").setReadOnly(true);
				tblTabela.fieldByName("datacons").setReadOnly(true);
				tblTabela.fieldByName("horacons").setReadOnly(true);
				tblTabela.fieldByName("ulticons").setReadOnly(true);
				tblTabela.fieldByName("ulticonsdias").setReadOnly(true);
				tblTabela.fieldByName("desccodiespe").setReadOnly(true);
				tblTabela.fieldByName("desctipoatend").setReadOnly(true);
				tblTabela.fieldByName("desccodimedi").setReadOnly(true);
				
				tblTabela.fieldByName("codiproc01_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc02_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc03_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc04_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc05_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc06_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc07_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc08_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc09_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc10_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc11_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc12_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc13_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc14_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc15_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc16_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc17_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc18_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc19_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc20_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc21_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc22_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc23_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc24_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc25_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc26_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc27_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc28_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc29_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc30_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc31_valor").setReadOnly(true);
				tblTabela.fieldByName("codiproc32_valor").setReadOnly(true);
				
				tblTabela.fieldByName("vlrtotal").setReadOnly(true);
				tblTabela.fieldByName("vlrcustototal").setReadOnly(true);
				
				tblTabela.fieldByName("codiasso").setRequired(true);
				tblTabela.fieldByName("nome").setRequired(true);
				tblTabela.fieldByName("paciente").setRequired(true);
				tblTabela.fieldByName("nasc").setRequired(true);
				tblTabela.fieldByName("cortesia").setRequired(true);
				tblTabela.fieldByName("codiespe").setRequired(true);
				tblTabela.fieldByName("tipoatend").setRequired(true);
				tblTabela.fieldByName("atendido").setRequired(true);
				tblTabela.fieldByName("datacons").setRequired(true);
				tblTabela.fieldByName("horacons").setRequired(true);
				tblTabela.fieldByName("codimedi").setRequired(true);
				tblTabela.fieldByName("observacoes").setRequired(true);

				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÃO");
				tblTabela.fieldByName("cortesia").setInternalSearch(internalSearch);
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("C", "CONSULTA");
				internalSearch.addItem("R", "RETORNO");
				internalSearch.addItem("E", "EXAME");
				tblTabela.fieldByName("tipoatend").setInternalSearch(internalSearch);

				internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÃO");
				tblTabela.fieldByName("atendido").setInternalSearch(internalSearch);
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("codiasso").addForeingSearch();
				foreingSearch.setTargetRmGridName("associados");
				foreingSearch.setTargetIndexName("sequencia");
				foreingSearch.setRelationship("codiasso");
				foreingSearch.setReturnFieldName("sequencia");
				foreingSearch.setTitle("Pesquisa por ASSOCIADOS:");
				foreingSearch.setOrder("nome");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");

				/**/
				foreingSearch = tblTabela.fieldByName("codiespe").addForeingSearch();
				foreingSearch.setTargetRmGridName("especialidades");
				foreingSearch.setTargetIndexName("codiespe");
				foreingSearch.setRelationship("codiespe");
				foreingSearch.setReturnFieldName("codiespe");
				foreingSearch.setTitle("Pesquisa por especialidades:");
				foreingSearch.setOrder("descricao");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiespe").addExistenceCheck("especialidades", "codiespe", "codiespe", "Especialidade informada é inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodiespe", event.getTargetTable().getString("descricao"));
						event.getSourceTable().setValue("codiespe_valor", event.getTargetTable().getDouble("valor"));
						event.getSourceTable().setValue("codiespe_vlrcusto", event.getTargetTable().getDouble("vlrcusto"));
					}
				});
				
				/**/
				
				foreingSearch = tblTabela.fieldByName("codimedi").addForeingSearch();
				foreingSearch.setTargetRmGridName("medicos");
				foreingSearch.setTargetIndexName("codimedi");
				foreingSearch.setRelationship("codimedi");
				foreingSearch.setReturnFieldName("codimedi");
				foreingSearch.setTitle("Pesquisa por MÉDICOS:");
				foreingSearch.setOrder("nome");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				foreingSearch.addBeforeForeingSearchEventListener(new BeforeForeingSearchEventListener() {
					@Override
					public void onBeforeForeingSearch(BeforeForeingSearchEvent event) {
						Table tableTarget = event.getTargetTable();
						tableTarget.setWhere("exists (select * from espemedi where (espemedi.codiespe="+event.getSourceTable().getInteger("codiespe")+") and (espemedi.codimedi=medicos.codimedi))");
					}
				});
				
				existenceCheck = tblTabela.fieldByName("codimedi").addExistenceCheck("medicos", "codimedi", "codimedi", "Médico informada é inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodimedi", event.getTargetTable().getString("nome"));
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("marcconsfilho");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("marcconsfilho");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setHeight("550px");
				rmGrid.setLimit(10);
				rmGrid.addField("sequencia", "Sequência", 100D);
				rmGrid.addField("datacons", "Data da consulta", 150d);
				rmGrid.addField("horacons", "Hora", 80d);
				rmGrid.addField("nome", "Nome do associado", 250d, 1);
				rmGrid.addField("paciente", "Paciente", 250d);
				rmGrid.addField("desccodiespe", "Especialidade", 180d);
				rmGrid.addField("desctipoatend", "Atendimento", 130d);
				rmGrid.addField("incluser", "Inclusão - Usuário", 150d);
				rmGrid.addField("incldata", "Inclusão - Data", 130d);
				rmGrid.addField("modiuser", "Modificação - Usuário", 150d);
				rmGrid.addField("modidata", "Modificação - Data", 130d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de associados:");
				controlForm.setWidth(790d);
				controlForm.setHeight(600d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados da Consulta/Exame:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 90d);
					controlForm.addField("codiasso", "Cód.Associado", 130d);
					controlForm.addField("nome", "Nome", 250d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 120d);
					controlForm.addField("cortesia", "Cortesia", 120d);
					
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Cód.Esp.", 120d);
					controlForm.addField("desccodiespe", "Especialidade", 200d, 1);
					controlForm.addField("ulticons", "Última consulta", 120d);
					controlForm.addField("ulticonsdias", "Dias", 50d);
					controlForm.addField("desctipoatend", "Atendimento", 110d);
					
					controlForm.addNewLine();
					controlForm.addField("codimedi", "Cód.Médico", 120d);
					controlForm.addField("desccodimedi", "Médico", 200d, 1);
					controlForm.addButton("Agenda");
					
					controlForm.addNewLine();
					controlForm.addField("datacons", "Data da consulta", 150d);
					controlForm.addField("diasema", "Dia da semana", 100d, 1);
					controlForm.addField("horacons", "Hora da consulta", 150d);
					controlForm.addField("atendido", "Atendido", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("observacoes", "Observacoes", 100d, 300d, 1);
					
					//apos pesquisa de existencia de especialidade atualiza
					//valor, vlrcusto
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por associados:");
				controlForm.setWidth(790d);
				controlForm.setHeight(600d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados da Consulta/Exame:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 90d);
					controlForm.addField("codiasso", "Cód.Associado", 130d);
					controlForm.addField("nome", "Nome", 250d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 120d);
					controlForm.addField("cortesia", "Cortesia", 120d);
					
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Cód.Esp.", 120d);
					controlForm.addField("desccodiespe", "Especialidade", 200d, 1);
					controlForm.addField("ulticons", "Última consulta", 120d);
					controlForm.addField("ulticonsdias", "Dias", 50d);
					controlForm.addField("desctipoatend", "Atendimento", 110d);
					
					controlForm.addNewLine();
					controlForm.addField("codimedi", "Cód.Médico", 120d);
					controlForm.addField("desccodimedi", "Médico", 200d, 1);
					controlForm.addButton("Agenda");
					
					controlForm.addNewLine();
					controlForm.addField("datacons", "Data da consulta", 150d);
					controlForm.addField("diasema", "Dia da semana", 100d, 1);
					controlForm.addField("horacons", "Hora da consulta", 150d);
					controlForm.addField("atendido", "Atendido", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("observacoes", "Observacoes", 100d, 300d, 1);
					
					//apos pesquisa de existencia de especialidade atualiza
					//valor, vlrcusto
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("marcconsfilho");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("marcconsfilho");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Consultas", true);
			}
		});
	}
}
