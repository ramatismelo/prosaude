package com.example.myapplication.tables;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.table.BeforeLoadDataEvent;
import com.evolucao.rmlibrary.database.events.table.BeforeLoadDataEvent.BeforeLoadDataEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlContentField;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class AgendaDias {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("agendaDias");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setManagedTable(false);
				tblTabela.setTableName("agendadias");
				tblTabela.addField("codimedi", FieldType.INTEGER, 10);
				tblTabela.addField("tipoatend", FieldType.VARCHAR, 1);
				tblTabela.addField("data", FieldType.DATE, 10);
				tblTabela.addField("horario", FieldType.VARCHAR, 5);
				tblTabela.addField("diasemana", FieldType.VARCHAR, 50);
				
				tblTabela.addField("atencons", FieldType.INTEGER, 10);
				tblTabela.addField("agencons", FieldType.INTEGER, 10);
				tblTabela.addField("vagacons", FieldType.INTEGER, 10);
				
				tblTabela.addField("atenreto", FieldType.INTEGER, 10);
				tblTabela.addField("agenreto", FieldType.INTEGER, 10);
				tblTabela.addField("vagareto", FieldType.INTEGER, 10);
				
				tblTabela.setPrimaryKey("uid");
				tblTabela.setOrder("data, horario");
				
				tblTabela.addBeforeLoadDataEventListener(new BeforeLoadDataEventListener() {
					@Override
					public void onBeforeLoadData(BeforeLoadDataEvent event) {
						// Cancela a continuidade do loadData evitando assim que uma chamada a tabela ocorra pois os dados
						// serao carregados de forma customizada agora neste momento
						event.setCancel(true);
						
						Table tblTabela = event.getTable();
						
						// Limpa os registros da tabela
						tblTabela.setSimpleRecordList(new ArrayList<SimpleRecord>());
						
						if ((tblTabela.filterByName("codimedi")!=null) && (AgendaDias.temAgenda(tblTabela.filterByName("codimedi").getInteger()))) {
							Date data = Utils.getJustDate(new Date());
							Date dataLimite = Utils.somaDias(data, 90);
							
							while (data.before(dataLimite)) {
								SimpleRecord simpleRecord = getNumeroVagas(tblTabela.filterByName("codimedi").getInteger(), tblTabela.filterByName("tipoatend").getString(), data);
								if (simpleRecord!=null) {
									tblTabela.getSimpleRecordList().add(simpleRecord);
								}
								
								data = Utils.somaDias(data, 1);
							}
						}
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("agendaDiasConsulta");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("agendadias");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("data", "Data", 120d);
				rmGrid.addField("horario", "Horário", 100d);
				rmGrid.addField("diasemana", "Dia da Semana", 155d);
				
				rmGrid.addField("atencons", "Consultas", 130d);
				rmGrid.addField("agencons", "Cons.Agend.", 110d);
				rmGrid.addField("vagacons", "Vagas Consultas", 100d);
				
				rmGrid.addField("atenreto", "Retornos", 130d);
				rmGrid.addField("agenreto", "Ret.Agend.", 110d);
				rmGrid.addField("vagareto", "Vagas Retorno", 100d);
			}
		});
	    
	    rmGridRegistry = database.addRmGridRegistry("agendaDiasRetorno");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("agendadias");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("data", "Data", 120d);
				rmGrid.addField("horario", "Horário", 100d);
				rmGrid.addField("diasemana", "Dia da Semana", 155d);
				
				rmGrid.addField("atenreto", "Retornos", 130d);
				rmGrid.addField("agenreto", "Ret.Agend.", 110d);
				rmGrid.addField("vagareto", "Vagas Retorno", 100d);

				rmGrid.addField("atencons", "Consultas", 130d);
				rmGrid.addField("agencons", "Cons.Agend.", 110d);
				rmGrid.addField("vagacons", "Vagas Consultas", 100d);
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("agendaMedicos");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("agendaMedicos");
				rmGrid.updateContent();
			}
		});
	}

	private static boolean temAgenda(Integer codigoMedico) {
		boolean retorno = false;
		Database database = ((ApplicationUI) UI.getCurrent()).database;
		
		try {
			database.openConnection();
			
			String comando = "select diasemana from agenda";
			comando += " left join medicos on (medicos.uid=agenda.uidmedico)";
			comando += " where (medicos.codimedi='" + codigoMedico + "') limit 1";
			
			ResultSet result = database.executeSelect(comando);
			
			if (result.next()) {
				retorno = true;
			}
			result.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			database.closeConnection();
		}
		return retorno;
	}
	
	/**
	 * Retorna o numero de vagas disponiveis para o medico indicado na data indicada, caso nao tenha vagas retorna zero
	 * @param codigoMedico
	 * @param data
	 * @return
	 */
	private static SimpleRecord getNumeroVagas(Integer codigoMedico, String tipoAtendimento, Date data) {
		Integer retorno = 0;
		Database database = ((ApplicationUI) UI.getCurrent()).database;
		SimpleRecord simpleRecord = null;
		
		try {
			database.openConnection();
			Integer diasemana = Utils.getDiaSemana(data);
			Integer numevagas = 0;
			Integer numeagendamentos = 0;
			String horario = "";
			
			Integer atencons = 0;
			Integer atenreto = 0;
			Integer agencons = 0;
			Integer agenreto = 0;
			Integer vagacons = 0;
			Integer vagareto = 0;
			
			String comando = "select sum(numeatend) as qtdconsultas, sum(numereto) as qtdretornos, horainic from agenda ";
			comando += " left join medicos on (medicos.uid=agenda.uidmedico)";
			comando += " where (medicos.codimedi=" + codigoMedico + ") and (diasemana="+ diasemana +")";
			ResultSet result = database.executeSelect(comando);
			if (result.next()) {
				/*
				if ((tipoAtendimento.equalsIgnoreCase("C")) || (tipoAtendimento.equalsIgnoreCase("E"))) {
					numevagas = result.getInt("qtdconsultas");
				}
				else {
					numevagas = result.getInt("qtdretornos");
				}
				
				horario = result.getString("horainic");
				*/
				
				//simpleRecord.setDate("data", data);
				//simpleRecord.setString("horario", result.getString("horainic"));
				//simpleRecord.setInteger("atencons", result.getInt("qtdconsultas"));
				//simpleRecord.setInteger("atenreto", result.getInt("qtdretornos"));
				
				horario = result.getString("horainic");
				atencons = result.getInt("qtdconsultas");
				atenreto = result.getInt("qtdretornos");
			}
			result.close();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			comando = "select * from alteagenmedi ";
			comando += "where (alteagenmedi.codimedi=" + codigoMedico + ") and (alteagenmedi.data='"  + dateFormat.format(data) + "')";
			result = database.executeSelect(comando);
			if (result.next()) {
				if (result.getString("tipoalte").equalsIgnoreCase("E")) {
					numevagas = 0;
					atencons = 0;
					atenreto = 0;
					//simpleRecord.setInteger("atencons", 0);
					//simpleRecord.setInteger("atenreto", 0);
				}
				else if (result.getString("tipoalte").equalsIgnoreCase("I")) {
					/*
					if ((tipoAtendimento.equalsIgnoreCase("C")) || (tipoAtendimento.equalsIgnoreCase("E"))) {
						numevagas = result.getInt("numeatend");
					}
					else {
						numevagas = result.getInt("numereto");
					}
					*/
					
					//simpleRecord.setInteger("atencons", result.getInt("numeatend"));
					//simpleRecord.setInteger("atenreto", result.getInt("numereto"));
					horario = result.getString("horainic");
					atencons = result.getInt("numeatend");
					atenreto = result.getInt("numereto");
				}
			}

			if ((atencons!=0) || (atenreto!=0)) {
				// Pega a forma de totalizacao do medico
				String tipoTotalizacao = "1"; // numero de atendimentos
				comando = "select tipotota from medicos ";
				comando += "where (medicos.codimedi='" + codigoMedico + "')";
				result = database.executeSelect(comando);
				if (result.next()) {
					tipoTotalizacao = result.getString("tipotota");
				}
				
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				if (tipoTotalizacao.equalsIgnoreCase("1")) {
					//result = database.executeSelect("select count(*) as qtd from marccons where (marccons.codimedi='"+ codigoMedico +"') and (marccons.datacons='"+dateFormat.format(data)+"') and (marccons.tipoatend='" + tipoAtendimento + "')");
					
					// Totaliza as consultas e exames
					result = database.executeSelect("select count(*) as qtd from marccons where (marccons.codimedi='"+ codigoMedico +"') and (marccons.datacons='"+dateFormat.format(data)+"') and ((marccons.tipoatend='C') or (marccons.tipoatend='E'))");
					if ((result.next()) && (result.getObject("qtd")!=null)) {
						agencons = result.getInt("qtd");
					}
					
					// Totaliza os retornos
					result = database.executeSelect("select count(*) as qtd from marccons where (marccons.codimedi='"+ codigoMedico +"') and (marccons.datacons='"+dateFormat.format(data)+"') and (marccons.tipoatend='R')");
					if ((result.next()) && (result.getObject("qtd")!=null)) {
						agenreto = result.getInt("qtd");
					}
				}
				else if (tipoTotalizacao.equalsIgnoreCase("2")) {
					//result = database.executeSelect("select sum(numeproc) as qtd from marccons where (marccons.codimedi='"+ codigoMedico +"') and (marccons.datacons='"+dateFormat.format(data)+"') and (marccons.tipoatend='" + tipoAtendimento + "')");
					
					// Totaliza as consultas e exames
					result = database.executeSelect("select sum(numeproc) as qtd from marccons where (marccons.codimedi='"+ codigoMedico +"') and (marccons.datacons='"+dateFormat.format(data)+"') and ((marccons.tipoatend='C') or (marccons.tipoatend='E'))");
					if ((result.next()) && (result.getObject("qtd")!=null)) {
						agencons = result.getInt("qtd");
					}
					
					// Totaliza os retornos
					result = database.executeSelect("select sum(numeproc) as qtd from marccons where (marccons.codimedi='"+ codigoMedico +"') and (marccons.datacons='"+dateFormat.format(data)+"') and (marccons.tipoatend='R')");
					if ((result.next()) && (result.getObject("qtd")!=null)) {
						agenreto = result.getInt("qtd");
					}
				}
				
				//if (result.next()) {
				//	numeagendamentos = result.getInt("qtd");
				//}
				
				//if (numevagas>numeagendamentos) {
				//	//retorno = true;
				//	retorno = numevagas-numeagendamentos;
				simpleRecord = new SimpleRecord();
				simpleRecord.setDate("data", data);
				simpleRecord.setString("horario", horario);
				simpleRecord.setString("diasemana", Utils.getNomeDiaSemana(data).toUpperCase());
				
				if ((tipoAtendimento.equalsIgnoreCase("C")) || (tipoAtendimento.equalsIgnoreCase("E"))) {
					simpleRecord.setInteger("atencons", atencons);
					simpleRecord.setInteger("agencons", agencons);
					if (atencons-agencons>0) {
						simpleRecord.setInteger("vagacons", atencons-agencons);
					}
					else {
						simpleRecord.setInteger("vagacons", 0);
					}
					
					simpleRecord.setInteger("atenreto", atenreto);
					simpleRecord.setInteger("agenreto", agenreto);
					if (atenreto-agenreto>0) {
						simpleRecord.setInteger("vagareto", atenreto-agenreto);
					}
					else {
						simpleRecord.setInteger("vagareto", 0);
					}
				}
				else {
					simpleRecord.setInteger("atenreto", atenreto);
					simpleRecord.setInteger("agenreto", agenreto);
					if (atenreto-agenreto>0) {
						simpleRecord.setInteger("vagareto", atenreto-agenreto);
					}
					else {
						simpleRecord.setInteger("vagareto", 0);
					}
					
					simpleRecord.setInteger("atencons", atencons);
					simpleRecord.setInteger("agencons", agencons);
					if (atencons-agencons>0) {
						simpleRecord.setInteger("vagacons", atencons-agencons);
					}
					else {
						simpleRecord.setInteger("vagacons", 0);
					}
				}
				
				//	simpleRecord.setInteger("atendimentos", numevagas);
				//	simpleRecord.setInteger("agendados", numeagendamentos);
				//	simpleRecord.setInteger("vagas", numevagas-numeagendamentos);
				//	simpleRecord.setString("uid", UUID.randomUUID().toString().toUpperCase());
				//	simpleRecord.setInteger("codimedi", codigoMedico);
				//}
				
				//simpleRecord.setInteger("vagacons", simpleRecord.getInteger("atencons")-simpleRecord.getInteger("agencons"));
				//simpleRecord.setInteger("vagareto", simpleRecord.getInteger("atenreto")-simpleRecord.getInteger("agenreto"));
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			database.closeConnection();
		}
		
		return simpleRecord;
	}
}
