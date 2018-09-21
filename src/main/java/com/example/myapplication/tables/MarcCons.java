package com.example.myapplication.tables;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent.AfterValidateOnEditEventListener;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent.BeforeForeingSearchEventListener;
import com.evolucao.rmlibrary.database.events.ComboBoxItemCaptionGeneratorEvent;
import com.evolucao.rmlibrary.database.events.ComboBoxItemCaptionGeneratorEvent.ComboBoxItemCaptionGeneratorEventListener;
import com.evolucao.rmlibrary.database.events.ComboBoxValueChangeEvent;
import com.evolucao.rmlibrary.database.events.ComboBoxValueChangeEvent.ComboBoxValueChangeEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterFilterEvent;
import com.evolucao.rmlibrary.database.events.table.AfterFilterEvent.AfterFilterEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterInsertEvent;
import com.evolucao.rmlibrary.database.events.table.AfterInsertEvent.AfterInsertEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent.AfterUpdateEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent.SpecialConditionOfDeleteEventListener;
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
import com.example.myapplication.view.AgendaView;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class MarcCons {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("marccons");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("marccons");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("codiasso", FieldType.INTEGER, 10);
				tblTabela.addField("uidassociado", FieldType.VARCHAR, 50);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("paciente", FieldType.VARCHAR, 50);
				tblTabela.addField("nasc", FieldType.DATE, 10);
				tblTabela.addField("cortesia", FieldType.VARCHAR, 1);
				tblTabela.addField("uidusualibe", FieldType.VARCHAR, 50);
				
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("desccodiespe", FieldType.VARCHAR, 50);
				tblTabela.addField("codiespe_valor", FieldType.DOUBLE, 10);
				tblTabela.addField("codiespe_vlrcusto", FieldType.DOUBLE, 10);
				
				tblTabela.addField("ulticons", FieldType.DATE, 10);
				tblTabela.addField("ulticonsdias", FieldType.INTEGER, 10);
				tblTabela.addField("ultitipoatend", FieldType.VARCHAR, 10);
				tblTabela.addField("ultidesctipoatend", FieldType.VARCHAR, 50);
				tblTabela.addField("ultiatendsequ", FieldType.INTEGER, 10);
				
				tblTabela.addField("limireto", FieldType.DATE, 10);
				tblTabela.addField("datareto", FieldType.DATE, 10);
				tblTabela.addField("sequreto", FieldType.INTEGER, 10);
				
				tblTabela.addField("tipoatend", FieldType.VARCHAR, 1);
				tblTabela.addField("desctipoatend", FieldType.VARCHAR, 50);
				tblTabela.addField("atendido", FieldType.VARCHAR, 1);
				tblTabela.addField("datacons", FieldType.DATE, 10);
				tblTabela.addField("diasema", FieldType.VARCHAR, 50);
				tblTabela.addField("horacons", FieldType.VARCHAR, 5);
				tblTabela.addField("codimedi", FieldType.INTEGER, 10);
				tblTabela.addField("desccodimedi", FieldType.VARCHAR, 50);
				tblTabela.addField("numeproc", FieldType.INTEGER, 10);
				
				tblTabela.addField("uidfornecedor", FieldType.VARCHAR, 50);
				
				tblTabela.addField("altura", FieldType.VARCHAR, 10);
				tblTabela.addField("peso", FieldType.VARCHAR, 10);
				tblTabela.addField("pressao", FieldType.VARCHAR, 10);
				tblTabela.addField("pulsacao", FieldType.VARCHAR, 10);
				tblTabela.addField("temperatura", FieldType.VARCHAR, 10);
				
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
				//tblTabela.setOrder("datacons desc, horacons desc, sequencia desc");
				tblTabela.setOrder("sequencia desc");
				
				tblTabela.addIndex("datacons_horacons_sequencia", "datacons, horacons, sequencia");
				tblTabela.addIndex("codiasso", "codiasso");
				tblTabela.addIndex("codiespe_datacons_horacons", "codiespe, datacons, horacons");
				tblTabela.addIndex("codimedi_datacons_horacons", "codimedi, datacons, horacons");
				tblTabela.addIndex("cortesia_datacons_horacons", "cortesia, datacons, horacons");
				tblTabela.addIndex("tipoatend_datacons_horacons",  "tipoatend, datacons, horacons" );
				tblTabela.addIndex("uidcarrcomp_sequencia", "uidcarrcomp, sequencia");
				
				tblTabela.addJoin("especialidades", "codiespe", "codiespe");
				tblTabela.fieldByName("desccodiespe").setExpression("especialidades.descricao");
				
				tblTabela.addJoin("medicos", "codimedi", "codimedi");
				tblTabela.fieldByName("desccodimedi").setExpression("medicos.nome");
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("tipoatend", "C");
						event.getTable().setValue("desctipoatend", "CONSULTA");
						event.getTable().setDate("ulticons", null);
						event.getTable().setInteger("ulticonsdias", null);
						event.getTable().setInteger("codimedi", null);
						event.getTable().setString("desccodimedi", "");
						event.getTable().setString("cortesia", "N");
						event.getTable().setString("atendido", "S");
					}
				});
				
				tblTabela.addAfterInsertEventListener(new AfterInsertEventListener() {
					@Override
					public void onAfterInsert(AfterInsertEvent event) {
						// Limita o acesso as especialidades na inclusao
						ComboBox cmbCodiEspe = event.getTable().fieldByName("codiespe").getComboBox();
						cmbCodiEspe.setWhere("(especialidades.permagencons='S')");
						
						// Liebera a alteracao do campo codiespe para que o usuario possa indicar a especialidade 
						// desejada pelo usuario
						event.getTable().fieldByName("codiespe").setReadOnly(false);
					}
				});

				tblTabela.addAfterUpdateEventListener(new AfterUpdateEventListener() {
					@Override
					public void onAfterUpdate(AfterUpdateEvent event) {
						// Limita o acesso as especialidades na alteracao
						ComboBox cmbCodiEspe = event.getTable().fieldByName("codiespe").getComboBox();
						cmbCodiEspe.setWhere("(especialidades.permagencons='S')");
						
						// Nao permite alteracao do campo codiespe em retorno
						if ((event.getTable().getString("tipoatend").equalsIgnoreCase("C")) || (event.getTable().getString("tipoatend").equalsIgnoreCase("E"))) {
							// Caso a especialidade seja gratuita, libera a alteracao do campo especialidade, caso nao seja gratuita, 
							// nao pode alterar especialidade
							Table tblEspecialidades = event.getTable().getDatabase().loadTableByName("especialidades");
							tblEspecialidades.select("permagencons");
							tblEspecialidades.setFilter("codiespe", event.getTable().getInteger("codiespe"));
							tblEspecialidades.loadData();
							
							// Caso a especialidade seja paga, nao permite alterar ela, pois o usuario tem que consultar na especialidade que ele pagou.
							if (tblEspecialidades.getString("permagencons").equalsIgnoreCase("N")) {
								event.getTable().fieldByName("codiespe").setReadOnly(true);
							}
							// Caso a especialidade seja gratuita, permite alterar ela, mas somente para uma especialidade gratuita tambem
							//  a limitacao das especialidades que o usuario pode escolher foi estabelecida no afterinsert e afterupdate
							else {
								event.getTable().fieldByName("codiespe").setReadOnly(false);
							}
						}
						
						// Nao pode alterar a especialidade no retorno
						if (event.getTable().getString("tipoatend").equalsIgnoreCase("R")) {
							event.getTable().fieldByName("codiespe").setReadOnly(true);
						}
						
						if (event.getTable().getString("tipoatend").equalsIgnoreCase("C")) {
							if ((event.getTable().getDate("datacons")!=null) && (event.getTable().getDate("limireto")==null)) {
								event.getTable().setDate("limireto", Utils.addDate(event.getTable().getDate("datacons"), 21));
							}
						}
						else if (event.getTable().getString("tipoatend").equalsIgnoreCase("R")) {
							if (event.getTable().getDate("ulticons")!=null) {
								event.getTable().setDate("limireto", Utils.addDate(event.getTable().getDate("ulticons"), 21));
							}
						}
						else if (event.getTable().getString("tipoatend").equalsIgnoreCase("E")) {
							event.getTable().setDate("limireto", null);
						}
						
						updateLastMarcCons(event.getTable());
						
					}
				});
				
				
				tblTabela.addAfterFilterEventListener(new AfterFilterEventListener() {
					@Override
					public void onAfterFilter(AfterFilterEvent event) {
						ComboBox cmbCodiEspe = event.getTable().fieldByName("codiespe").getComboBox();
						cmbCodiEspe.setWhere(null);
					}
				});
				
				Field field = tblTabela.fieldByName("sequencia");
				field.setAutoIncrement(true);
				field.setReadOnly(true);
				
				InternalSearch internalSearch = tblTabela.fieldByName("tipoatend").addInternalSearch();
				internalSearch.addItem("C", "CONSULTA");
				internalSearch.addItem("R", "RETORNO");
				internalSearch.addItem("E", "EXAME");
				
				InternalSearch isUltiTipoAtend = tblTabela.fieldByName("ultitipoatend").addInternalSearch();
				isUltiTipoAtend.addItem("C", "CONSULTA");
				isUltiTipoAtend.addItem("R", "RETORNO");
				isUltiTipoAtend.addItem("E", "EXAME");
				
				tblTabela.fieldByName("desctipoatend").setValueOfInternalSearch("tipoatend");
				tblTabela.fieldByName("ultidesctipoatend").setValueOfInternalSearch("ultitipoatend");
				
				//tblTabela.fieldByName("codiasso").setReadOnly(true);
				tblTabela.fieldByName("nome").setReadOnly(true);
				tblTabela.fieldByName("diasema").setReadOnly(true);
				tblTabela.fieldByName("datacons").setReadOnly(true);
				tblTabela.fieldByName("horacons").setReadOnly(true);
				
				tblTabela.fieldByName("limireto").setReadOnly(true);
				tblTabela.fieldByName("datareto").setReadOnly(true);
				tblTabela.fieldByName("sequreto").setReadOnly(true);
				
				tblTabela.fieldByName("cortesia").setReadOnly(true);
				tblTabela.fieldByName("uidusualibe").setReadOnly(true);
				
				tblTabela.fieldByName("ulticons").setReadOnly(true);
				tblTabela.fieldByName("ulticonsdias").setReadOnly(true);
				tblTabela.fieldByName("ultitipoatend").setReadOnly(true);
				tblTabela.fieldByName("ultidesctipoatend").setReadOnly(true);
				tblTabela.fieldByName("ultiatendsequ").setReadOnly(true);
				
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
				//tblTabela.fieldByName("datacons").setRequired(true);
				//tblTabela.fieldByName("horacons").setRequired(true);
				//tblTabela.fieldByName("codimedi").setRequired(true);
				//tblTabela.fieldByName("observacoes").setRequired(true);

				internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÃO");
				tblTabela.fieldByName("cortesia").setInternalSearch(internalSearch);
				
				ComboBox cbUidUsuaLibe = tblTabela.fieldByName("uidusualibe").addComboBox("sysusers", "nome", "nome", "uid");
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("C", "CONSULTA");
				internalSearch.addItem("R", "RETORNO");
				internalSearch.addItem("E", "EXAME");
				tblTabela.fieldByName("tipoatend").setInternalSearch(internalSearch);

				internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÃO");
				tblTabela.fieldByName("atendido").setInternalSearch(internalSearch);
				
				/**/
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
						if (event.getSourceTable().getTableStatus()!=TableStatus.FILTER) {
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
										event.getSourceTable().setValue("nome", event.getSimpleRecord().getString("nome"));
										event.getSourceTable().setValue("nasc", tblAssociados.getDate("nasc"));
										event.getSourceTable().setValue("paciente", event.getSimpleRecord().getString("nome"));
									}
								}
								catch (Exception e) {
									System.out.println("MarcCons: " + e.getMessage());
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
					}
				});
				
				/**/
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
						event.getTargetTable().setMainFilter("situacao", "A");
					}
				});
				
				tblTabela.fieldByName("codiasso").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						if (event.getTable().getTableStatus()!=TableStatus.FILTER) {
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
					}
				});
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiasso").addExistenceCheck("associados", "sequencia", "codiasso", "Associado não encontrado ou inválido!");
				/**/
								
				/*
				ComboBox comboBoxAssociado = tblTabela.fieldByName("codiasso").addComboBox("associados", "nome", "nome", "sequencia");
				comboBoxAssociado.setAdditionalFieldsToLoad("nasc");
				comboBoxAssociado.addComboBoxItemCaptionGeneratorEventListener(new ComboBoxItemCaptionGeneratorEventListener() {
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
				*/

				/*
				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiasso").addExistenceCheck("associados", "sequencia", "codiasso", "Associado informado é inválidp!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("nome", event.getTargetTable().getString("nome"));
						if (event.getSourceTable().getString("paciente").isEmpty()) {
							event.getSourceTable().setValue("paciente", event.getTargetTable().getString("nome"));
							event.getSourceTable().setValue("nasc", event.getTargetTable().getDate("nasc"));
						}
					}
				});
				*/
				
				ComboBox cmbCodiEspe = tblTabela.fieldByName("codiespe").addComboBox("especialidades", "descricao", "descricao", "codiespe");
				
				existenceCheck = tblTabela.fieldByName("codiespe").addExistenceCheck("especialidades", "codiespe", "codiespe", "Especialidade informada é inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodiespe", event.getTargetTable().getString("descricao"));
						event.getSourceTable().setValue("codiespe_valor", event.getTargetTable().getDouble("valor"));
						event.getSourceTable().setValue("codiespe_vlrcusto", event.getTargetTable().getDouble("vlrcusto"));
					}
				});
				
				tblTabela.fieldByName("codiespe").addAfterValidateOnEditEventListener(new AfterValidateOnEditEventListener() {
					@Override
					public void onAfterValidateOnEdit(AfterValidateOnEditEvent event) {
						if ((event.getTable().getTableStatus()==TableStatus.INSERT) || (event.getTable().getTableStatus()==TableStatus.UPDATE)) {
							// Deve ser melhorado levando em conta a data da consulta e uma modificacao no registro
							String vcodiasso = event.getTable().getString("codiasso");
							String vpaciente = event.getTable().getString("paciente");
							String vcodiespe = event.getTable().getString("codiespe");
		
							// Esse codigo deve ser executado somente se o conteudo de codiespe for alterado, com a finalidade
							// de atualizar os dados da ultima consulta da especialidade selecionada
							String comando ="select marccons.sequencia, marccons.datacons, marccons.tipoatend, marccons.codimedi, medicos.nome from marccons";
							comando += " left join medicos on (medicos.codimedi=marccons.codimedi)";
							comando += "where (codiasso=" + vcodiasso + ") and (paciente='" + vpaciente + "') and (codiespe="+vcodiespe+")";
							//comando += "order by datacons desc, sequencia desc limit 1";
							comando += "order by sequencia desc limit 1";
							
							try {
								event.getTable().getDatabase().openConnection();
								ResultSet resultSet = event.getTable().getDatabase().executeSelect(comando);
								if (resultSet.next()) {
									// Atualiza dados da consulta
									event.getTable().setValue("ultiatendsequ", resultSet.getInt("sequencia"));
									
									event.getTable().setValue("ulticons", resultSet.getDate("datacons"));
									int dias = 0;
									if (resultSet.getDate("datacons")!=null) {
										dias = (int) Utils.getDateDiff(resultSet.getDate("datacons"), new Date(), TimeUnit.DAYS);
									}
											
									event.getTable().setValue("ulticonsdias", dias);
										
									event.getTable().setValue("ultitipoatend", resultSet.getString("tipoatend"));
									
									if (resultSet.getString("tipoatend").equalsIgnoreCase("C")) {
										event.getTable().setValue("ultidesctipoatend", "CONSULTA");
									}
									else if (resultSet.getString("tipoatend").equalsIgnoreCase("R")) {
										event.getTable().setValue("ultidesctipoatend", "RETORNO");
									}

									/*
									if ((dias<=20) && (resultSet.getString("tipoatend").equalsIgnoreCase("C"))) {
										event.getTable().setValue("tipoatend", "R");
										event.getTable().setValue("desctipoatend", "RETORNO");
									}
									*/
									
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
									event.getTable().setDate("limireto", null);
									event.getTable().setDate("datareto", null);
									event.getTable().setInteger("sequreto", null);
									event.getTable().setLoadingDataToForm(false);
								}
							}
							catch (Exception e) {
								System.out.println("MarcCons: " +e.getMessage());
							}
							finally {
								event.getTable().getDatabase().closeConnection();
							}
						}
					}
				});
				
				/**/
				
				ComboBox comboBox = tblTabela.fieldByName("codimedi").addComboBox("medicos", "nome", "nome", "codimedi");
				tblTabela.fieldByName("codimedi").setReadOnly(true);
				
				ForeingSearch foreingSearch2 = tblTabela.fieldByName("codimedi").addForeingSearch();
				foreingSearch2.setTargetRmGridName("medicos");
				foreingSearch2.setTargetIndexName("codimedi");
				foreingSearch2.setRelationship("codimedi");
				foreingSearch2.setReturnFieldName("codimedi");
				foreingSearch2.setTitle("Pesquisa por MÉDICOS:");
				foreingSearch2.setOrder("nome");
				foreingSearch2.setAutoOpenFilterForm(false);
				foreingSearch2.setWidth("800px");
				foreingSearch2.setHeight("655px");
				foreingSearch2.addBeforeForeingSearchEventListener(new BeforeForeingSearchEventListener() {
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
				
				tblTabela.addSpecialConditionOfDeleteEventListener(new SpecialConditionOfDeleteEventListener() {
					@Override
					public void onSpecialConditionOfDelete(SpecialConditionOfDeleteEvent event) {
						event.setValid(false);
						event.setErrorMessage("nao pode nao pode nao pode");
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("marccons");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("marccons");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setHeight("550px");
				rmGrid.setLimit(10);
				rmGrid.addField("sequencia", "Sequência", 100D);
				rmGrid.addField("datacons", "Data do atendimento", 150d);
				rmGrid.addField("horacons", "Hora", 80d);
				rmGrid.addField("nome", "Nome do associado", 250d, 1);
				rmGrid.addField("paciente", "Paciente", 250d);
				rmGrid.addField("desccodiespe", "Especialidade", 180d);
				rmGrid.addField("desctipoatend", "Atendimento", 130d);
				rmGrid.addField("incluser", "Inclusão - Usuário", 150d);
				rmGrid.addField("incldata", "Inclusão - Data", 130d);
				rmGrid.addField("modiuser", "Modificação - Usuário", 150d);
				rmGrid.addField("modidata", "Modificação - Data", 130d);
				
				rmGrid.setAllowDelete(false);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de guia de atendimento:");
				controlForm.setWidth(1100d);
				controlForm.setHeight(600d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Guida de atendimento de Consulta/Exames:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 90d);

					controlForm.addField("uidassociado", "Nome", 250d, 1);
					controlForm.addField("codiasso", "Cód.Associado", 130d);
					//controlForm.addField("codiasso", "Cód.Associado", 130d, 1);
					//controlForm.addField("nome", "Nome", 250d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 150d);
					
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Especialidade", 120d, 1);
					controlForm.addField("ulticons", "Último atendimento", 150d);
					controlForm.addField("ulticonsdias", "Dias", 50d);
					controlForm.addField("ultiatendsequ", "Última sequência", 150d);
					controlForm.addField("ultidesctipoatend", "Atendimento", 110d);
					
					controlForm.addNewLine();
					controlForm.addField("codimedi", "Médico", 120d, 1);
					//controlForm.addField("desccodimedi", "Médico", 200d, 1);
					
					ControlButton controlButton = controlForm.addButton("Agenda");
					controlButton.addControlButtonClickEventListener(new ControlButtonClickEventListener() {
						@Override
						public void onControlButtonClick(ControlButtonClickEvent event) {
							if (event.getRmForm().getTable().fieldByName("codiespe").validate()) {
								
								Boolean permiteAgendamento = true;

								if ((event.getRmForm().getTable().getInteger("sequreto")!=null) && (event.getRmForm().getTable().getInteger("sequreto")!=0)) {
									permiteAgendamento = false;

									RmFormWindow formWindow = new RmFormWindow();
									formWindow.setTitle("Atenção!");
									formWindow.setWidth("600px");
									formWindow.setHeight("200px");
									formWindow.addMessage("Não é permitido alterar agendamento de consulta que já possua retorno agendado!", MessageWindowType.INFO);
									RmFormButtonBase btnOk = formWindow.addMessageOkButton();
									btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
										@Override
										public void onRmFormButtonClick(RmFormButtonClickEvent event) {
											event.getWindow().close();
										}
									});
									formWindow.show();
								}
								
								if (permiteAgendamento) {
									Table tblMarcCons = event.getRmForm().getTable();
									
									Integer codigoEspecialidade = event.getRmForm().getTable().getInteger("codiespe");
									Integer codigoMedico = event.getRmForm().getTable().getInteger("codimedi");
									String tipoAtendimento = event.getRmForm().getTable().getString("tipoatend");
									Date limireto = null;
									if (tipoAtendimento.equalsIgnoreCase("R")) {
										limireto = tblMarcCons.getDate("limireto");
									}
									
									AgendaView agendaView = new AgendaView(codigoEspecialidade, codigoMedico, tipoAtendimento, limireto);
									
									RmFormWindow formWindow = new RmFormWindow();
									if (event.getRmForm().getTable().getString("tipoatend").equalsIgnoreCase("C")) {
										formWindow.setTitle("Agendamento de consultas:");
									}
									else {
										formWindow.setTitle("Agendamento de retornos:");
									}
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
									
									RmFormButtonBase buttonConfirmar = formWindow.addButton("Agendar atendimento");
									buttonConfirmar.setIcon(new ThemeResource("imagens/library/accept.png"));
									buttonConfirmar.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
										@Override
										public void onRmFormButtonClick(RmFormButtonClickEvent event) {
											if (agendaView.getSimpleRecord().fieldExists("data")) {
												// Pegas os dados da ultima consulta desta especialidade
												
												// cria a restricao com data aqui
												// ramatis
												boolean permiteAgendar = true;
												
												if (permiteAgendar) {
													// Caso esteja tentando agendar um retorno, permite esse agendamento somente se a data que foi selecionada
													// para o retorno for menor que a data limite para o retorno.
													if (tblMarcCons.getString("tipoatend").equalsIgnoreCase("R")) {
														Integer dateDiff = (int) Utils.getDateDiff(tblMarcCons.getDate("limireto"), agendaView.getSimpleRecord().getDate("data"), TimeUnit.DAYS);
														
														if (dateDiff>0) {
															permiteAgendar = false;
															
															RmFormWindow formWindow = new RmFormWindow();
															formWindow.setTitle("Atenção!");
															formWindow.setWidth("600px");
															formWindow.setHeight("200px");
															formWindow.addMessage("Não é permitido marcar retorno para consultas realizadas a mais de 21 dias!", MessageWindowType.INFO);
															RmFormButtonBase btnOk = formWindow.addMessageOkButton();
															btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
																@Override
																public void onRmFormButtonClick(RmFormButtonClickEvent event) {
																	event.getWindow().close();
																}
															});
															formWindow.show();
														}
													}
												}
												
												if (permiteAgendar) {
													// Caso esteja tentando agendar um retorno, permite esse agendamento somente se a data que foi selecionada
													// para o retorno for menor que a data limite para o retorno.
													if ((tblMarcCons.getString("tipoatend").equalsIgnoreCase("R")) && (tblMarcCons.getDate("ulticons")!=null)) {
														Integer dateDiff = (int) Utils.getDateDiff(tblMarcCons.getDate("ulticons"), agendaView.getSimpleRecord().getDate("data"), TimeUnit.DAYS);
														
														if (dateDiff<0) {
															permiteAgendar = false;
															
															RmFormWindow formWindow = new RmFormWindow();
															formWindow.setTitle("Atenção!");
															formWindow.setWidth("600px");
															formWindow.setHeight("200px");
															formWindow.addMessage("Não é permitido marcar retorno para uma data anterior a data da consulta!", MessageWindowType.INFO);
															RmFormButtonBase btnOk = formWindow.addMessageOkButton();
															btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
																@Override
																public void onRmFormButtonClick(RmFormButtonClickEvent event) {
																	event.getWindow().close();
																}
															});
															formWindow.show();
														}
													}
												}
												
												if (permiteAgendar) {
													// Caso a data selecionada possua vagas, da continuidade ao processo de agendamento
													if ( (((tipoAtendimento.equalsIgnoreCase("C")) || (tipoAtendimento.equalsIgnoreCase("E"))) && (agendaView.getSimpleRecord().getInteger("vagacons")>0)) ||
													     ((tipoAtendimento.equalsIgnoreCase("R")) && (agendaView.getSimpleRecord().getInteger("vagareto")>0)) ) {
														if ((tblMarcCons.getTableStatus()!=TableStatus.INSERT) && (tblMarcCons.getTableStatus()!=TableStatus.UPDATE)) {
															tblMarcCons.update(tblMarcCons.getString("uid"), false);
														}
														
														tblMarcCons.setValue("codimedi", agendaView.getSimpleRecord().getInteger("codimedi"));
														//tblMarcCons.setValue("desccodimedi", agendaView.getSimpleRecord().getString("desccodimedi"));
														tblMarcCons.setValue("datacons", agendaView.getSimpleRecord().getDate("data"));
														tblMarcCons.setValue("horacons", agendaView.getSimpleRecord().getString("horario"));
														tblMarcCons.setValue("diasema", agendaView.getSimpleRecord().getString("diasemana"));
														
														// Definir a data limite para o retorno da consulta, caso seja consulta
														if (tipoAtendimento.equalsIgnoreCase("C")) {
															tblMarcCons.setValue("limireto", Utils.addDate(agendaView.getSimpleRecord().getDate("data"), 21));
														}
														
														if (tblMarcCons.getDate("ulticons")!=null) {
															int dias = 0;
															if (tblMarcCons.getDate("datacons")!=null) {
																dias = (int) Utils.getDateDiff(tblMarcCons.getDate("ulticons"), tblMarcCons.getDate("datacons"), TimeUnit.DAYS);
															}
															tblMarcCons.setValue("ulticonsdias", dias);
														}
														
														tblMarcCons.execute();
														
														RmFormWindow window = (RmFormWindow) event.getWindow();
														window.close();
													}
													// Caso a data selecionada nao possua vagas disponiveis
													else {
														RmFormWindow formWindow = new RmFormWindow();
														formWindow.setTitle("Atenção!");
														formWindow.setWidth("600px");
														formWindow.setHeight("200px");
														formWindow.addMessage("Data selecionada não possui vagas disponíveis, selecione outra data!", MessageWindowType.INFO);
														RmFormButtonBase btnOk = formWindow.addMessageOkButton();
														btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
															@Override
															public void onRmFormButtonClick(RmFormButtonClickEvent event) {
																event.getWindow().close();
															}
														});
														formWindow.show();
													}
												}
											}
											else {
												RmFormWindow formWindow = new RmFormWindow();
												formWindow.setTitle("Atenção!");
												formWindow.setWidth("600px");
												formWindow.setHeight("200px");
												formWindow.addMessage("Necessário selecionar uma data para agendar o atendimento!", MessageWindowType.INFO);
												RmFormButtonBase btnOk = formWindow.addMessageOkButton();
												btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
													@Override
													public void onRmFormButtonClick(RmFormButtonClickEvent event) {
														event.getWindow().close();
													}
												});
												formWindow.show();
											}
										}
									});

									formWindow.show();
								}
							}
						}
					});

					controlForm.addField("datacons", "Data da consulta", 150d);
					controlForm.addField("diasema", "Dia da semana", 110d);
					controlForm.addField("horacons", "Hora da consulta", 120d);
					controlForm.addField("desctipoatend", "Atendimento", 110d);
					
					controlForm.addNewLine();
					controlForm.addField("atendido", "Atendido", 100d);
					
					controlForm.addField("cortesia", "Cortesia", 100d);
					controlForm.addField("uidusualibe", "Autorização", 300d, 1);
					
					controlForm.addField("limireto", "Limite para retorno", 160d);
					controlForm.addField("datareto", "Data do retorno", 160d);
					controlForm.addField("sequreto", "Sequência do retorno", 175d);
					
					controlForm.addNewLine();
					controlForm.addField("observacoes", "Observacoes", 100d, 300d, 1);
					
					//apos pesquisa de existencia de especialidade atualiza
					//valor, vlrcusto
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("medicoes", "Medições do estado de saúde do paciente:", SectionState.MAXIMIZED);
					{
						controlForm.addNewLine();
						controlForm.addField("altura", "Altura", 100d, 1);
						controlForm.addField("peso", "Peso", 100d, 1);
						controlForm.addField("pressao", "Pressão", 100d, 1);
						controlForm.addField("pulsacao", "Pulsação", 100d, 1);
						controlForm.addField("temperatura", "Temperatura", 100d, 1);
					}
				}
				
				RmFormButtonBase button = controlForm.addRmFormButton("Emitir guia de atendimento");
				button.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
					@Override
					public void onRmFormButtonClick(RmFormButtonClickEvent event) {
						if (event.getControlForm().getTable().validate()) {
							event.getControlForm().getTable().execute();
							
							if (!event.getControlForm().getTable().getString("uid").isEmpty()) {
								Table tblParametros = event.getControlForm().getTable().getDatabase().loadTableByName("parametros");
								tblParametros.select("*");
								tblParametros.loadData();
								
								String url = "http://" + tblParametros.getString("reportServer") + "/php-app/fichaconsulta2.php?recordId="+event.getControlForm().getTable().getInteger("sequencia");
								UI.getCurrent().getPage().open(url, "_blank", false);
							}
						}
						else {
							System.out.println("não validou o conteudo do formulario");
						}
					}
				});
				
				RmFormButtonBase btnGerarRetorno = controlForm.addRmFormButton("Gerar retorno");
				btnGerarRetorno.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
					@Override
					public void onRmFormButtonClick(RmFormButtonClickEvent event) {
						RmFormWindow formMarcCons = (RmFormWindow) event.getWindow();
						Table tblMarcCons = event.getControlForm().getTable();
						
						if (event.getControlForm().getTable().validate()) {
							event.getControlForm().getTable().execute();

							//ramatis2
							boolean permiteCriarRetorno = true;
							
							// Permite agendar retorno somente se o tipo de atendimento for uma consulta
							if (!event.getControlForm().getTable().getString("tipoatend").equalsIgnoreCase("C")) {
								permiteCriarRetorno = false;
								
								RmFormWindow formWindow = new RmFormWindow();
								formWindow.setTitle("Atenção!");
								formWindow.setWidth("500px");
								formWindow.setHeight("160px");
								formWindow.addMessage("Permitido gerar retorno somente para CONSULTAS!", MessageWindowType.INFO);
								RmFormButtonBase btnOk = formWindow.addMessageOkButton();
								btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										event.getWindow().close();
									}
								});
								formWindow.show();
							}
							
							// Permite agendar retorno somente se já foi agendado a data da consulta
							if ((permiteCriarRetorno) && (event.getControlForm().getTable().getDate("datacons")==null)) {
								permiteCriarRetorno = false;
								
								RmFormWindow formWindow = new RmFormWindow();
								formWindow.setTitle("Atenção!");
								formWindow.setWidth("600px");
								formWindow.setHeight("180px");
								formWindow.addMessage("Permitido gerar retorno somente para consultas que já tenham sido agendadas!", MessageWindowType.INFO);
								RmFormButtonBase btnOk = formWindow.addMessageOkButton();
								btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										event.getWindow().close();
									}
								});
								formWindow.show();
							}
							
							// Permite agendar retorno somente se foi definido uma data para retorno
							if ((permiteCriarRetorno) && (event.getControlForm().getTable().getDate("datareto")!=null)) {
								permiteCriarRetorno = false;
								
								SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
								
								RmFormWindow formWindow = new RmFormWindow();
								formWindow.setTitle("Atenção!");
								formWindow.setWidth("600px");
								formWindow.setHeight("180px");
								formWindow.addMessage("Consulta já possui um retorno gerado para o dia " + simpleDate.format(tblMarcCons.getDate("datareto")) + " sequencia da guia de atendimento: " + tblMarcCons.getInteger("sequreto"), MessageWindowType.INFO);
								RmFormButtonBase btnOk = formWindow.addMessageOkButton();
								btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										event.getWindow().close();
									}
								});
								formWindow.show();
							}
							
							/*
							// Permite agendar o retorno somente se a data que foi selecionada para retorno estiver dentro do limite de 21 dias
							int diasConsulta = (int) Utils.getDateDiff(tblMarcCons.getDate("datacons"), agendaView.getSimpleRecord().getDate("data"), TimeUnit.DAYS);
							diasConsulta = diasConsulta + 1;
									
							// Verifica se esta dentro do prazo para gerar retorno a contar da data da consulta ate hoje
							if ((permiteCriarRetorno) && (diasConsulta>21)) {
								permiteCriarRetorno = false;
								
								RmFormWindow formWindow = new RmFormWindow();
								formWindow.setTitle("Atenção!");
								formWindow.setWidth("600px");
								formWindow.setHeight("180px");
								formWindow.addMessage("Não é permitido marcar retorno para consultas realizadas a mais de 21 dias!", MessageWindowType.INFO);
								RmFormButtonBase btnOk = formWindow.addMessageOkButton();
								btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										event.getWindow().close();
									}
								});
								formWindow.show();
							}
							*/
							
							if (permiteCriarRetorno) {
								//Table tblMarcCons = event.getRmForm().getTable();
								Integer codigoEspecialidade = tblMarcCons.getInteger("codiespe");
								Integer codigoMedico = tblMarcCons.getInteger("codimedi");
								String tipoAtendimento = tblMarcCons.getString("tipoatend");
								
								AgendaView agendaView = new AgendaView(codigoEspecialidade, codigoMedico, "R", tblMarcCons.getDate("limireto"));
								
								RmFormWindow formWindow = new RmFormWindow();
								formWindow.setTitle("Agendamento de retornos:");
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
								
								RmFormButtonBase buttonConfirmar = formWindow.addButton("Agendar atendimento");
								buttonConfirmar.setIcon(new ThemeResource("imagens/library/accept.png"));
								buttonConfirmar.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										Window windowAgendamento = event.getWindow();
										Boolean permiteGerarRetorno = true;
										
										// Caso o usuario tenha selecionado uma data, executa o processo de agendamento, caso contrario
										// solicita que ele selecione uma data
										if (!agendaView.getSimpleRecord().fieldExists("data")) {
											permiteGerarRetorno = false;
											
											RmFormWindow formWindow = new RmFormWindow();
											formWindow.setTitle("Atenção!");
											formWindow.setWidth("600px");
											formWindow.setHeight("200px");
											formWindow.addMessage("Necessário selecionar uma data para agendar o atendimento!", MessageWindowType.INFO);
											RmFormButtonBase btnOk = formWindow.addMessageOkButton();
											btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
												@Override
												public void onRmFormButtonClick(RmFormButtonClickEvent event) {
													event.getWindow().close();
												}
											});
											formWindow.show();
										}

										// Caso nao tenha sido ainda cancelado a geracao do retorno,
										// Caso a data selecionada esteja dentro do periodo de retorno
										if (permiteGerarRetorno) {
											Integer dateDiff = (int) Utils.getDateDiff(tblMarcCons.getDate("datacons"), agendaView.getSimpleRecord().getDate("data"), TimeUnit.DAYS);
											
											if (dateDiff<0) {
												permiteGerarRetorno = false;
												
												RmFormWindow formWindow = new RmFormWindow();
												formWindow.setTitle("Atenção!");
												formWindow.setWidth("600px");
												formWindow.setHeight("200px");
												formWindow.addMessage("Não é permitido marcar retorno para uma data anterior a data da consulta!", MessageWindowType.INFO);
												RmFormButtonBase btnOk = formWindow.addMessageOkButton();
												btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
													@Override
													public void onRmFormButtonClick(RmFormButtonClickEvent event) {
														event.getWindow().close();
													}
												});
												formWindow.show();
											}
										}
										
										// Caso nao tenha sido ainda cancelado a geracao do retorno,
										// Caso a data selecionada esteja dentro do periodo de retorno
										if (permiteGerarRetorno) {
											Integer dateDiff = (int) Utils.getDateDiff(tblMarcCons.getDate("datacons"), agendaView.getSimpleRecord().getDate("data"), TimeUnit.DAYS);
											
											if (dateDiff>21) {
												permiteGerarRetorno = false;
												
												RmFormWindow formWindow = new RmFormWindow();
												formWindow.setTitle("Atenção!");
												formWindow.setWidth("600px");
												formWindow.setHeight("200px");
												formWindow.addMessage("Não é permitido marcar retorno para consultas realizadas a mais de 21 dias!", MessageWindowType.INFO);
												RmFormButtonBase btnOk = formWindow.addMessageOkButton();
												btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
													@Override
													public void onRmFormButtonClick(RmFormButtonClickEvent event) {
														event.getWindow().close();
													}
												});
												formWindow.show();
											}
										}
										
										// Caso a data selecionada nao possua vagas
										if (permiteGerarRetorno) {
											if (agendaView.getSimpleRecord().getInteger("vagareto")<=0) {
												permiteGerarRetorno = false;
												
												RmFormWindow formWindow = new RmFormWindow();
												formWindow.setTitle("Atenção!");
												formWindow.setWidth("600px");
												formWindow.setHeight("200px");
												formWindow.addMessage("Data selecionada não possui vagas disponíveis, selecione outra data!", MessageWindowType.INFO);
												RmFormButtonBase btnOk = formWindow.addMessageOkButton();
												btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
													@Override
													public void onRmFormButtonClick(RmFormButtonClickEvent event) {
														event.getWindow().close();
													}
												});
												formWindow.show();
											}
										}
										
										// Caso tenha passados por todas verificacoes, gera o retorno
										if (permiteGerarRetorno) {
											SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
											String dia = "";
											
											if (Utils.getJustDate(agendaView.getSimpleRecord().getDate("data")).equals(Utils.getJustDate(new Date()))) {
												dia="HOJE (" + agendaView.getSimpleRecord().getString("diasemana") + ")";
											}
											else {
												dia = agendaView.getSimpleRecord().getString("diasemana");
											}
											
											RmFormWindow formWindow = new RmFormWindow();
											formWindow.setTitle("Atenção!");
											formWindow.setWidth("600px");
											formWindow.setHeight("230px");
											formWindow.addMessage("Você confirma o agendamento do retorno para:", dia + " - " + simpleDateFormat.format(agendaView.getSimpleRecord().getDate("data")) + " as " + agendaView.getSimpleRecord().getString("horario") + " ?", MessageWindowType.QUESTION);

											RmFormButtonBase btnSim = formWindow.addConfirmButton("Sim");
											btnSim.setIcon(new ThemeResource("imagens/library/accept.png"));
											btnSim.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
												@Override
												public void onRmFormButtonClick(RmFormButtonClickEvent event) {
													ApplicationUI ui = (ApplicationUI) UI.getCurrent();
													Table tblMarcConsRetorno = ui.database.loadTableByName("marccons");
													
													tblMarcConsRetorno.insert();
													tblMarcConsRetorno.setValue("codiasso", tblMarcCons.getInteger("codiasso"));
													tblMarcConsRetorno.setValue("uidassociado", tblMarcCons.getString("uidassociado"));
													tblMarcConsRetorno.setValue("nome", tblMarcCons.getString("nome"));
													tblMarcConsRetorno.setValue("paciente", tblMarcCons.getString("paciente"));
													tblMarcConsRetorno.setValue("nasc", tblMarcCons.getDate("nasc"));
													tblMarcConsRetorno.setValue("cortesia", tblMarcCons.getString("cortesia"));
													tblMarcConsRetorno.setValue("uidusualibe", tblMarcCons.getString("uidusualibe"));
													tblMarcConsRetorno.setValue("codiespe", tblMarcCons.getString("codiespe"));
													tblMarcConsRetorno.setValue("desccodiespe", tblMarcCons.getString("desccodiespe"));
													tblMarcConsRetorno.setValue("codiespe_valor", tblMarcCons.getDouble("codiespe_valor"));
													tblMarcConsRetorno.setValue("codiespe_vlrcusto", tblMarcCons.getDouble("codiespe_vlrcusto"));
													
													//int dias = (int) Utils.getDateDiff(Utils.getJustDate(new Date()), tblMarcCons.getDate("datacons"), TimeUnit.DAYS);
													int dias = (int) Utils.getDateDiff(tblMarcCons.getDate("datacons"), agendaView.getSimpleRecord().getDate("data"), TimeUnit.DAYS);
													tblMarcConsRetorno.setValue("ulticons", tblMarcCons.getDate("datacons"));
													tblMarcConsRetorno.setValue("ulticonsdias", dias);
													tblMarcConsRetorno.setValue("ultitipoatend", tblMarcCons.getString("tipoatend"));
													tblMarcConsRetorno.setValue("ultidesctipoatend", tblMarcCons.getString("desctipoatend"));
													tblMarcConsRetorno.setValue("ultiatendsequ", tblMarcCons.getInteger("sequencia"));
													
													tblMarcConsRetorno.setValue("limireto", tblMarcCons.getDate("limireto"));
													
													tblMarcConsRetorno.setValue("tipoatend", "R");
													tblMarcConsRetorno.setValue("desctipoatend", "RETORNO");
													tblMarcConsRetorno.setValue("atendido", "S");

													tblMarcConsRetorno.setValue("codimedi", agendaView.getSimpleRecord().getInteger("codimedi"));
													//tblMarcConsRetorno.setValue("desccodimedi", agenda2View.getSimpleRecord().getString("desccodimedi"));
													tblMarcConsRetorno.setValue("datacons", agendaView.getSimpleRecord().getDate("data"));
													tblMarcConsRetorno.setValue("horacons", agendaView.getSimpleRecord().getString("horario"));
													tblMarcConsRetorno.setValue("diasema", agendaView.getSimpleRecord().getString("diasemana"));
													
													tblMarcConsRetorno.setValue("codimedi", tblMarcCons.getInteger("codimedi"));
													tblMarcConsRetorno.setValue("desccodimedi", tblMarcCons.getString("desccodimedi"));
													
													tblMarcConsRetorno.setValue("numeproc", tblMarcCons.getInteger("numeproc"));
													tblMarcConsRetorno.setValue("uidfornecedor", tblMarcCons.getString("uidfornecedor"));
													
													tblMarcConsRetorno.setValue("altura", tblMarcCons.getString("altura"));
													tblMarcConsRetorno.setValue("peso", tblMarcCons.getString("peso"));
													tblMarcConsRetorno.setValue("pressao", tblMarcCons.getString("pressao"));
													tblMarcConsRetorno.setValue("pulsacao", tblMarcCons.getString("pulsacao"));
													tblMarcConsRetorno.setValue("temperatura", tblMarcCons.getString("temperatura"));
													
													tblMarcConsRetorno.setValue("vlrtotal", tblMarcCons.getDouble("vlrtotal"));
													tblMarcConsRetorno.setValue("vlrcustototal", tblMarcCons.getDouble("vlrcustototal"));
													tblMarcConsRetorno.setValue("obsreci", tblMarcCons.getString("obsreci"));
													tblMarcConsRetorno.setValue("observacoes", tblMarcCons.getString("observacoes"));
													tblMarcConsRetorno.execute();
													
													String uid = tblMarcConsRetorno.getUidLastRecordProccessed();
													Integer sequencia = tblMarcConsRetorno.getLastInsertId();
													
													tblMarcCons.update(tblMarcCons.getString("uid"), false);
													//tblMarcCons.setDate("datareto", Utils.getJustDate(new Date()));
													tblMarcCons.setDate("datareto", agendaView.getSimpleRecord().getDate("data"));
													
													tblMarcCons.setInteger("sequreto", sequencia);
													tblMarcCons.execute();
													
													Table tblParametros = tblMarcCons.getDatabase().loadTableByName("parametros");
													tblParametros.select("*");
													tblParametros.loadData();
													
													String url = "http://" + tblParametros.getString("reportServer") + "/php-app/fichaconsulta2.php?recordId="+sequencia;
													UI.getCurrent().getPage().open(url, "_blank", false);
													
													RmFormWindow formWindow = new RmFormWindow();
													formWindow.setTitle("Atenção!");
													formWindow.setWidth("600px");
													formWindow.setHeight("230px");
													formWindow.addMessage("Retorno agendado com SUCESSO!", "Guia de atendimento [" + sequencia + "] emitida com sucesso!", MessageWindowType.INFO);
													RmFormButtonBase btnOk = formWindow.addMessageOkButton();
													btnOk.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
														@Override
														public void onRmFormButtonClick(RmFormButtonClickEvent event) {
															event.getWindow().close();
															//formMarcCons.close();
														}
													});
													formWindow.show();
													
													event.getWindow().close();
													windowAgendamento.close();
												}
											});

											RmFormButtonBase btnCancelar = formWindow.addCancelButton();
											btnCancelar.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
												@Override
												public void onRmFormButtonClick(RmFormButtonClickEvent event) {
													event.getWindow().close();
												}
											});
											
											formWindow.show();
										}
									}
								});

								formWindow.show();
							}
						}
					}
				});
				
				//*********************************************************************************************
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por guia de atendimento:");
				controlForm.setWidth(1100d);
				controlForm.setHeight(600d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Guida de atendimento de Consulta/Exames:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 90d);

					controlForm.addField("uidassociado", "Nome", 250d, 1);
					controlForm.addField("codiasso", "Cód.Associado", 130d);
					
					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 150d);
					
					controlForm.addNewLine();
					controlForm.addField("codiespe", "Especialidade", 120d, 1);
					controlForm.addField("ulticons", "Último atendimento", 150d);
					controlForm.addField("ulticonsdias", "Dias", 50d);
					controlForm.addField("ultiatendsequ", "Última sequência", 150d);
					controlForm.addField("ultidesctipoatend", "Atendimento", 110d);
					
					controlForm.addNewLine();
					controlForm.addField("codimedi", "Médico", 120d, 1);
					
					controlForm.addField("datacons", "Data da consulta", 150d);
					controlForm.addField("diasema", "Dia da semana", 110d);
					controlForm.addField("horacons", "Hora da consulta", 120d);
					controlForm.addField("desctipoatend", "Atendimento", 110d);
					
					controlForm.addNewLine();
					controlForm.addField("atendido", "Atendido", 100d);
					
					controlForm.addField("cortesia", "Cortesia", 100d);
					controlForm.addField("uidusualibe", "Autorização", 300d, 1);
					
					controlForm.addField("limireto", "Limite para retorno", 160d);
					controlForm.addField("datareto", "Data do retorno", 160d);
					controlForm.addField("sequreto", "Sequência do retorno", 175d);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("marccons");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("marccons");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Consultas", true);
			}
		});
	}
	
	public static void updateLastMarcCons(Table tblMarcCons) {
		// Recalcula os dados do ultimo atendimento considerando datas, atualiza a data do ultimo atendimento, o numero da guia, quantidade de dias passado e o tipo de atendimento
		if ((tblMarcCons.getString("tipoatend").equalsIgnoreCase("C")) || (tblMarcCons.getString("tipoatend").equalsIgnoreCase("E"))) {
			Table tblUltiMarcCons = tblMarcCons.getDatabase().loadTableByName("marccons");
			tblUltiMarcCons.select("*");
			tblUltiMarcCons.setFilter("codiasso", tblMarcCons.getInteger("codiasso"));
			tblUltiMarcCons.setFilter("paciente", tblMarcCons.getString("paciente"));
			tblUltiMarcCons.setFilter("codiespe", tblMarcCons.getInteger("codiespe"));
			tblUltiMarcCons.setOrder("marccons.datacons desc, marccons.sequencia desc");
			tblUltiMarcCons.setLimit(1);
			
			// Caso já exista uma data de agendamento definida, pega a ultima consulta anterior ou igual a data de agendamento atual
			// dentro da data caso exista mais de um agendamento, considera a ordem em que foi lancado o agendamento
			if (tblMarcCons.getDate("datacons")!=null) {
				tblUltiMarcCons.setWhere("(marccons.datacons<='" + Utils.getSimpleDateFormat(tblMarcCons.getDate("datacons"), "yyyy-MM-dd") + "') and (marccons.sequencia<>" + tblMarcCons.getInteger("sequencia")+")");
			}
			else {
				tblUltiMarcCons.setWhere("(marccons.sequencia<>" + tblMarcCons.getInteger("sequencia")+")");
			}
			
			tblUltiMarcCons.loadData();
			if (!tblUltiMarcCons.eof()) {
				tblMarcCons.setValue("ulticons", tblUltiMarcCons.getDate("datacons"));
				tblMarcCons.setValue("ultitipoatend", tblUltiMarcCons.getString("tipoatend"));
				tblMarcCons.setValue("ultidesctipoatend", tblUltiMarcCons.getString("desctipoatend"));
				tblMarcCons.setValue("ultiatendsequ", tblUltiMarcCons.getInteger("sequencia"));
				
				if ((tblMarcCons.getDate("datacons")!=null) && (tblUltiMarcCons.getDate("datacons")!=null)) {
					tblMarcCons.setValue("ulticonsdias", (int) Utils.getDateDiff(tblUltiMarcCons.getDate("datacons"), tblMarcCons.getDate("datacons"), TimeUnit.DAYS));
				}
				else if ((tblMarcCons.getDate("datacons")==null) && (tblUltiMarcCons.getDate("datacons")!=null)) {
					tblMarcCons.setValue("ulticonsdias", (int) Utils.getDateDiff(tblUltiMarcCons.getDate("datacons"), Utils.getJustDate(new Date()), TimeUnit.DAYS));
				}
			}
			else {
				tblMarcCons.setDate("ulticons", null);
				tblMarcCons.setString("ultitipoatend", null);
				tblMarcCons.setString("ultidesctipoatend", null);
				tblMarcCons.setInteger("ultiatendsequ", null);
				tblMarcCons.setInteger("ulticonsdias", null);
			}
		}
		else if (tblMarcCons.getString("tipoatend").equalsIgnoreCase("R")) {
			Table tblUltiMarcCons = tblMarcCons.getDatabase().loadTableByName("marccons");
			tblUltiMarcCons.select("*");
			tblUltiMarcCons.setFilter("sequencia", tblMarcCons.getInteger("ultiatendsequ"));
			
			// Caso já exista uma data de agendamento definida, pega a ultima consulta anterior ou igual a data de agendamento atual
			// dentro da data caso exista mais de um agendamento, considera a ordem em que foi lancado o agendamento
			if (tblMarcCons.getDate("datacons")!=null) {
				tblUltiMarcCons.setWhere("(marccons.datacons<='" + Utils.getSimpleDateFormat(tblMarcCons.getDate("datacons"), "yyyy-MM-dd") + "')");
			}
			
			tblUltiMarcCons.loadData();
			if (!tblUltiMarcCons.eof()) {
				tblMarcCons.setValue("ulticons", tblUltiMarcCons.getDate("datacons"));
				
				if (tblMarcCons.getDate("datacons")!=null) {
					tblMarcCons.setValue("ulticonsdias", (int) Utils.getDateDiff(tblUltiMarcCons.getDate("datacons"), tblMarcCons.getDate("datacons"), TimeUnit.DAYS));
				}
				else {
					tblMarcCons.setValue("ulticonsdias", (int) Utils.getDateDiff(tblUltiMarcCons.getDate("datacons"), Utils.getJustDate(new Date()), TimeUnit.DAYS));
				}
			}
		}
	}
}
