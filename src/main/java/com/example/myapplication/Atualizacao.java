package com.example.myapplication; 

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableChild;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.ui.model.OptionType;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.ApplicationUI;
import com.evolucao.weblibrary.view.SystemView;
import com.example.myapplication.reports.MergeTable;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class Atualizacao {
	public static SystemView systemView = null;
	
	public Atualizacao() {
		
	}

	public static void execute() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		try {
			ui.database.openConnection();
			
			for (TableRegistry tableRegistry: ui.database.getTableRegistryList()) {
				Table tblTabela = ui.database.loadTableByName(tableRegistry.getTableName());
				tblTabela.checkTable();
			}
			
			//pegaTelefones();
			//gravarTelefones();

			//updateUidAssociados();
			
			////updateModiData();
			
			//updateUidMedicos();
			//updateUidFornecedores();

			//updateUidEspecialidades();
			
			///*updateUidEspeForn();*/
			//updateUidEspeMedi();
			//updateUidAlteAgenMedi();
			
			//updateUidMarcCons2();
			//updateUidPermissoes();

			//updateUidRecibos();
			//updateUidDevoReci();
			//updateUidSaidCaix();
			
			createMenu();
			
			//updateDescricaoEspecialidades();
			//updateNomeMedicos();
			
			//updateMD5Ende();
			
			//MergeTable merge = new MergeTable();
			
			// Retira da descricao da especialidade os codigos
			//updateDescricaoEspecialidades();
			
			// Retira do nome dos medicos os codigos
			//updateNomeMedicos();			
			
			/*
			Table tblSequCaixC32 = ui.database.loadTableByName("sequcaixc3");
			Table tblSequCaixC3 = ui.database.loadTableByName("sequcaixc3");
			tblSequCaixC3.alterTable();
			
			while (true) {
				tblSequCaixC3.select("*");
				tblSequCaixC3.setWhere("(sequcaixc3.uid is null)");
				tblSequCaixC3.setLimit(100);
				tblSequCaixC3.loadData();
				if (tblSequCaixC3.eof()) {
					break;
				}
					
				while (!tblSequCaixC3.eof()) {
					tblSequCaixC32.update();
					tblSequCaixC32.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblSequCaixC32.setFilter("sequencia", tblSequCaixC3.getInteger("sequencia"));
					tblSequCaixC32.execute();
					
					tblSequCaixC3.next();
				}
			}
			System.out.println("Processo concluido!");
			*/

			/*
			Table tblSequCaixC22 = ui.database.loadTableByName("sequcaixc2");
			Table tblSequCaixC2 = ui.database.loadTableByName("sequcaixc2");
			tblSequCaixC2.alterTable();
			
			while (true) {
				tblSequCaixC2.select("*");
				tblSequCaixC2.setWhere("(sequcaixc2.uid is null)");
				tblSequCaixC2.setLimit(100);
				tblSequCaixC2.loadData();
				if (tblSequCaixC2.eof()) {
					break;
				}
					
				while (!tblSequCaixC2.eof()) {
					tblSequCaixC22.update();
					tblSequCaixC22.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblSequCaixC22.setFilter("sequencia", tblSequCaixC2.getInteger("sequencia"));
					tblSequCaixC22.execute();
					
					tblSequCaixC2.next();
				}
			}
			System.out.println("Processo concluido!");
			*/
			
			/*
			Table tblSequCaixc12 = ui.database.loadTableByName("sequcaixc1");
			Table tblSequCaixc1 = ui.database.loadTableByName("sequcaixc1");
			tblSequCaixc1.alterTable();
			
			while (true) {
				tblSequCaixc1.select("*");
				tblSequCaixc1.setWhere("(sequcaixc1.uid is null)");
				tblSequCaixc1.setLimit(100);
				tblSequCaixc1.loadData();
				if (tblSequCaixc1.eof()) {
					break;
				}
					
				while (!tblSequCaixc1.eof()) {
					tblSequCaixc12.update();
					tblSequCaixc12.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblSequCaixc12.setFilter("sequencia", tblSequCaixc1.getInteger("sequencia"));
					tblSequCaixc12.execute();
					
					tblSequCaixc1.next();
				}
			}
			System.out.println("Processo concluido!");
			*/
			
			/*
			Table tblCaixUsua = ui.database.loadTableByName("caixusua");
			tblCaixUsua.createTable();
			
			Table tblRecibos2 = ui.database.loadTableByName("recibos");
			Table tblRecibos = ui.database.loadTableByName("recibos");
			tblRecibos.alterTable();
			
			while (true) {
				tblRecibos.select("*");
				tblRecibos.setWhere("(recibos.uid is null)");
				tblRecibos.setLimit(100);
				tblRecibos.loadData();
				if (tblRecibos.eof()) {
					break;
				}
				while (!tblRecibos.eof()) {
					tblRecibos2.update();
					tblRecibos2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblRecibos2.setFilter("sequencia", tblRecibos.getInteger("sequencia"));
					tblRecibos2.execute();
					
					tblRecibos.next();
				}
			}
			System.out.println("Processo Concluido!");
			
			Table tblCaixas = ui.database.loadTableByName("caixas");
			tblCaixas.alterTable();
			
			Table tblCaixas2 = ui.database.loadTableByName("caixas");
			
			tblCaixas.select("*");
			tblCaixas.loadData();
			while (!tblCaixas.eof()) {
				tblCaixas2.update();
				tblCaixas2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
				tblCaixas2.setFilter("codicaix", tblCaixas.getString("codicaix"));
				tblCaixas2.execute();
				
				tblCaixas.next();
			}
			System.out.println("Processo concluido!");
			
			Table tblEspecialidades = ui.database.loadTableByName("especialidades");
			tblEspecialidades.alterTable();
			
			Table tblFornecedores = ui.database.loadTableByName("fornecedores");
			tblFornecedores.alterTable();
			*/
			
			/*
			Table tblCarrComp = ui.database.loadTableByName("carrcomp");
			tblCarrComp.createTable();
			//tblCarrComp.alterTable();

			Table tblCarrCons = ui.database.loadTableByName("carrcons");
			tblCarrCons.createTable();
			//tblCarrCons.alterTable();

			Table tblCarrExamLabo = ui.database.loadTableByName("carrexamlabo");
			tblCarrExamLabo.createTable();
			//tblCarrExamLabo.alterTable();
			 */
			
			/*
			Table tblAssociados = ui.database.loadTableByName("associados");
			tblAssociados.alterTable();
			*/
			
			/*
			//Table tblAssociados = ui.database.loadTableByName("associados");
			Table tblAssociados2 = ui.database.loadTableByName("associados");
			
			while (true) {
				tblAssociados.select("*");
				tblAssociados.setWhere("uid is null");
				tblAssociados.setLimit(100);
				tblAssociados.loadData();
				if (tblAssociados.eof()) {
					break;
				}
				
				while (!tblAssociados.eof()) {
					tblAssociados2.update();
					tblAssociados2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblAssociados2.setFilter("sequencia", tblAssociados.getInteger("sequencia"));
					tblAssociados2.execute();
					tblAssociados.next();
				}
			}
			System.out.println("Processo concluido!");
			*/
			
			//Table tblEspeForn = Database.Instance.loadTableByName("espeforn");
			//tblEspeForn.createTable();
			//tblEspeForn.alterTable();

			/*
			System.out.println("Atualizando usuarios...");
			Table tblUsuarios = Database.Instance.loadTableByName("usuarios");
			Table tblUsuarios2 = Database.Instance.loadTableByName("usuarios");
			tblUsuarios.alterTable();
			
			while (true) {
				tblUsuarios.select("*");
    			tblUsuarios.setWhere("uid is null");
				tblUsuarios.setLimit(100);
				tblUsuarios.loadData();
				if (tblUsuarios.eof()) {
					break;
				}
				while (!tblUsuarios.eof()) {
					tblUsuarios2.update();
					tblUsuarios2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblUsuarios2.setFilter("login", tblUsuarios.getString("login"));
					tblUsuarios2.execute();
					
					System.out.println("processando " + tblUsuarios.getString("login"));
					tblUsuarios.next();
				}
			}
			*/
			
			/*
			 * Manutencao em grupo de usuarios
			 *
			 * */
			/*
			System.out.println("Atualizando grupo de usuarios...");
			Table tblGrupUsua = Database.Instance.loadTableByName("grupusua");
			tblGrupUsua.alterTable();
			tblGrupUsua.select("*");
			tblGrupUsua.loadData();
			Table tblGrupUsua2 = Database.Instance.loadTableByName("grupusua"); 
			
			while (!tblGrupUsua.eof()) {
				tblGrupUsua2.update();
				tblGrupUsua2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
				tblGrupUsua2.setFilter("idgrupo", tblGrupUsua.getInteger("idgrupo"));
				tblGrupUsua2.execute();
				
				tblGrupUsua.next();
			}
			*/
			/*
			System.out.println("Atualizando menu...");
			Table tblMenu = Database.Instance.loadTableByName("menu");
			Table tblMenu2 = Database.Instance.loadTableByName("menu");
			tblMenu.alterTable();
			tblMenu.select("*");
			tblMenu.loadData();
			while (!tblMenu.eof()) {
				tblMenu2.update();
				tblMenu2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
				tblMenu2.setFilter("idmenu", tblMenu.getInteger("idmenu"));
				tblMenu2.execute();
				tblMenu.next();
			}
			*/
			
			/*
			*/
			
			/*
			System.out.println("Atualizando fornecedores...");
			Table tblFornecedores = Database.Instance.loadTableByName("fornecedores");
			Table tblFornecedores2 = Database.Instance.loadTableByName("fornecedores");
			tblFornecedores.alterTable();
			tblFornecedores.select("*");
			tblFornecedores.loadData();
			while (!tblFornecedores.eof()) {
				tblFornecedores2.update();
				tblFornecedores2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
				tblFornecedores2.setFilter("codiforn", tblFornecedores.getInteger("codiforn"));
				tblFornecedores2.execute();
				tblFornecedores.next();
			}
			System.out.println("Processo concluido!");
			*/

			//ApplicationUI ui = (ApplicationUI) UI.getCurrent();
			

			/*
			Table tblEspeMedi = Database.Instance.loadTableByName("espemedi");
			Table tblEspeMedi2 = Database.Instance.loadTableByName("espemedi");
			Table tblMedicos = Database.Instance.loadTableByName("medicos");

			tblMedicos = Database.Instance.loadTableByName("medicos");
			//Table tblMedicos2 = Database.Instance.loadTableByName("medicos");
			//tblMedicos.alterTable();
			
			/*
			System.out.println("Atualizando medicos...");
			tblMedicos.select("*");
			tblMedicos.loadData();
			while (!tblMedicos.eof()) {
				tblMedicos2.update();
				tblMedicos2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
				tblMedicos2.setFilter("codimedi", tblMedicos.getInteger("codimedi"));
				tblMedicos2.execute();
				
				tblMedicos.next();
			}
			System.out.println("processo concluido!");
			*/
			
			/*
			Table tblAgenda = Database.Instance.loadTableByName("agenda");
			tblAgenda.createTable();
			
			System.out.println("Atualizando espemedi...");
			tblEspeMedi.alterTable();
			tblEspeMedi.select("*");
			tblEspeMedi.loadData();
			while (!tblEspeMedi.eof()) {
				tblMedicos.select("*");
				tblMedicos.setFilter("codimedi", tblEspeMedi.getInteger("codimedi"));
				tblMedicos.loadData();
				
				tblEspeMedi2.update();
				tblEspeMedi2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
				tblEspeMedi2.setValue("uidmedico", tblMedicos.getString("uid"));
				tblEspeMedi2.setFilter("sequencia", tblEspeMedi.getInteger("sequencia"));
				tblEspeMedi2.execute();
				
				tblEspeMedi.next();
			}
			System.out.println("processo concluido!");

			/*
			System.out.println("Atualizando prefixo...");
			Table tblPrefixo = Database.Instance.loadTableByName("prefixo");
			Table tblPrefixo2 = Database.Instance.loadTableByName("prefixo");
			tblPrefixo.alterTable();
			tblPrefixo.select("*");
			tblPrefixo.loadData();
			while (!tblPrefixo.eof()) {
				tblPrefixo2.update();
				tblPrefixo2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
				tblPrefixo2.setFilter("prefixo", tblPrefixo.getString("prefixo"));
				tblPrefixo2.execute();
				tblPrefixo.next();
			}
			System.out.println("Processo concluido!");
			
			System.out.println("Atualizando cep...");
			Table tblCep = Database.Instance.loadTableByName("cep");
			Table tblCep2 = Database.Instance.loadTableByName("cep");
			tblCep.alterTable();

			while (true) {
				tblCep.select("*");
				tblCep.setLimit(100);
				tblCep.setWhere("uid is null");
				tblCep.loadData();
				if (tblCep.eof()) {
					break;
				}
				else {
					while (!tblCep.eof()) {
						//System.out.println("Processando: " + tblCep.getString("cep"));
						tblCep2.update();
						tblCep2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
						tblCep2.setFilter("cep", tblCep.getString("cep"));
						tblCep2.execute();
						tblCep.next();
					}
				}
			}
			System.out.println("processo concluido!");
			*/
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ui.database.closeConnection();
		}
	}
	
	public void processaControle() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();

		Table tblControle1 = ui.database.loadTableByName("controle1");
		tblControle1.createTable();
		
		Table tblControle2 = ui.database.loadTableByName("controle2");
		tblControle2.createTable();

		Table tblControle3 = ui.database.loadTableByName("controle3");
		tblControle3.createTable();
		
		Table tblRecibos = ui.database.loadTableByName("recibos");
		Table tblRecibos2 = ui.database.loadTableByName("recibos");
		tblRecibos.alterTable();
		
		Table tblMarcCons = ui.database.loadTableByName("marccons");

		boolean continuar = true;
		while (continuar) {
			tblRecibos.select("*");
			tblRecibos.setWhere("(emissao>='2017-01-01 00:00:00') and (emissao<='2017-10-18 23:59:59') and (processado is null)");
			//tblRecibos.setOrder("emissao");
			tblRecibos.setOrder("incldata");
			tblRecibos.setLimit(1000);
			tblRecibos.loadData();
			if (tblRecibos.eof()) {
				continuar=false;
			}
			else {
				while (!tblRecibos.eof()) {
					System.out.println("Processando recigo: " + tblRecibos.getInteger("sequencia") + " de " + tblRecibos.getDate("emissao"));
					
					tblMarcCons.select("*");
					tblMarcCons.setFilter("sequencia", tblRecibos.getInteger("sequproc"));
					tblMarcCons.loadData();
					
					if (tblMarcCons.eof()) {
						System.out.println("NAO ENCONTROU GUIA DO RECIBO " + tblRecibos.getInteger("sequencia") + " - " + tblRecibos.getString("referente"));
						System.out.println("----------------------------------------------------");
						
						// Atualiza o recibo que ja foi processado
						tblRecibos2.setAuditing(false);
						tblRecibos2.update();
						tblRecibos2.setValue("processado", "nem");
						tblRecibos2.setFilter("sequencia", tblRecibos.getString("sequencia"));
						tblRecibos2.execute();
					}
					else {
						if (tblMarcCons.getInteger("sequencia")==591746) {
							System.out.println("Chegou");
						}
						
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiespe"), tblMarcCons.getDouble("codiespe_vlrcusto"), tblMarcCons.getDouble("codiespe_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc01"), tblMarcCons.getDouble("codiproc01_vlrcusto"), tblMarcCons.getDouble("codiproc01_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc02"), tblMarcCons.getDouble("codiproc02_vlrcusto"), tblMarcCons.getDouble("codiproc02_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc03"), tblMarcCons.getDouble("codiproc03_vlrcusto"), tblMarcCons.getDouble("codiproc03_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc04"), tblMarcCons.getDouble("codiproc04_vlrcusto"), tblMarcCons.getDouble("codiproc04_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc05"), tblMarcCons.getDouble("codiproc05_vlrcusto"), tblMarcCons.getDouble("codiproc05_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc06"), tblMarcCons.getDouble("codiproc06_vlrcusto"), tblMarcCons.getDouble("codiproc06_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc07"), tblMarcCons.getDouble("codiproc07_vlrcusto"), tblMarcCons.getDouble("codiproc07_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc08"), tblMarcCons.getDouble("codiproc08_vlrcusto"), tblMarcCons.getDouble("codiproc08_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc09"), tblMarcCons.getDouble("codiproc09_vlrcusto"), tblMarcCons.getDouble("codiproc09_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc10"), tblMarcCons.getDouble("codiproc10_vlrcusto"), tblMarcCons.getDouble("codiproc10_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc11"), tblMarcCons.getDouble("codiproc11_vlrcusto"), tblMarcCons.getDouble("codiproc11_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc12"), tblMarcCons.getDouble("codiproc12_vlrcusto"), tblMarcCons.getDouble("codiproc12_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc13"), tblMarcCons.getDouble("codiproc13_vlrcusto"), tblMarcCons.getDouble("codiproc13_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc14"), tblMarcCons.getDouble("codiproc14_vlrcusto"), tblMarcCons.getDouble("codiproc14_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc15"), tblMarcCons.getDouble("codiproc15_vlrcusto"), tblMarcCons.getDouble("codiproc15_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc16"), tblMarcCons.getDouble("codiproc16_vlrcusto"), tblMarcCons.getDouble("codiproc16_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc17"), tblMarcCons.getDouble("codiproc17_vlrcusto"), tblMarcCons.getDouble("codiproc17_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc18"), tblMarcCons.getDouble("codiproc18_vlrcusto"), tblMarcCons.getDouble("codiproc18_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc19"), tblMarcCons.getDouble("codiproc19_vlrcusto"), tblMarcCons.getDouble("codiproc19_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc20"), tblMarcCons.getDouble("codiproc20_vlrcusto"), tblMarcCons.getDouble("codiproc20_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc21"), tblMarcCons.getDouble("codiproc21_vlrcusto"), tblMarcCons.getDouble("codiproc21_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc22"), tblMarcCons.getDouble("codiproc22_vlrcusto"), tblMarcCons.getDouble("codiproc22_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc23"), tblMarcCons.getDouble("codiproc23_vlrcusto"), tblMarcCons.getDouble("codiproc23_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc24"), tblMarcCons.getDouble("codiproc24_vlrcusto"), tblMarcCons.getDouble("codiproc24_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc25"), tblMarcCons.getDouble("codiproc25_vlrcusto"), tblMarcCons.getDouble("codiproc25_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc26"), tblMarcCons.getDouble("codiproc26_vlrcusto"), tblMarcCons.getDouble("codiproc26_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc27"), tblMarcCons.getDouble("codiproc27_vlrcusto"), tblMarcCons.getDouble("codiproc27_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc28"), tblMarcCons.getDouble("codiproc28_vlrcusto"), tblMarcCons.getDouble("codiproc28_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc29"), tblMarcCons.getDouble("codiproc29_vlrcusto"), tblMarcCons.getDouble("codiproc29_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc30"), tblMarcCons.getDouble("codiproc30_vlrcusto"), tblMarcCons.getDouble("codiproc30_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc31"), tblMarcCons.getDouble("codiproc31_vlrcusto"), tblMarcCons.getDouble("codiproc31_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						processaItem(tblRecibos.getInteger("codiforn"), tblMarcCons.getInteger("codiproc32"), tblMarcCons.getDouble("codiproc32_vlrcusto"), tblMarcCons.getDouble("codiproc32_valor"), tblRecibos.getDate("emissao"), tblRecibos.getInteger("sequencia"));
						
						// Atualiza o recibo que ja foi processado
						tblRecibos2.setAuditing(false);
						tblRecibos2.update();
						tblRecibos2.setValue("processado", "ok");
						tblRecibos2.setFilter("sequencia", tblRecibos.getString("sequencia"));
						tblRecibos2.execute();
					}
					
					
					tblRecibos.next();
				}
			}
		}
	
		System.out.println("processo concluido!");
	}
	
	
	public static void processaItem(Integer codiforn, Integer codiespe, double vlrCusto, double vlrVenda, Date data, Integer numereci) {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		if ((codiespe!=null) && (codiespe!=0) && (vlrCusto!=0) && (vlrVenda!=0)) {
			Table tblControle1 = ui.database.loadTableByName("controle1");
			Table tblControle2 = ui.database.loadTableByName("controle2");
			Table tblControle3 = ui.database.loadTableByName("controle3");
			Table tblEspecialidades = ui.database.loadTableByName("especialidades");
			
			tblEspecialidades.select("*");
			tblEspecialidades.setFilter("codiespe", codiespe);
			tblEspecialidades.loadData();
			
			tblControle1.select("*");
			tblControle1.setFilter("codiforn", codiforn);
			tblControle1.setFilter("codiespe", codiespe);
			tblControle1.loadData();
			
			// Caso não tenha encontrado registro
			if (tblControle1.eof()) {
				// Inclui o cadastro do produto
				tblControle1.insert();
				tblControle1.setValue("codiforn", codiforn);
				tblControle1.setValue("codiespe", codiespe);
				tblControle1.setValue("descricao", tblEspecialidades.getString("descricao"));
				tblControle1.setValue("data", data); // Data em que foi registrado a primeira movimentacao do item
				tblControle1.setValue("vlrcusto", vlrCusto); // Valor custo da primeira movimentacao do item
				tblControle1.setValue("vlrvenda", vlrVenda); // Valor venda da primeira movimentacao do item
				tblControle1.setValue("lastModify", data); // Data da ultima vez que o valor de venda ou custo do item foi modificado
				tblControle1.setValue("lastVlrCusto", vlrCusto); // Valor de custo na ultima vez que o item foi modificado
				tblControle1.setValue("lastVlrVenda", vlrVenda); // Valor de venda na ultima vez que o item foi modificado
				tblControle1.setValue("numeroAlteracoes", 0);
				tblControle1.setValue("numeultireci", numereci);
				tblControle1.execute();
				
				// Inclui o primeiro registro de totalizacao
				tblControle2.insert();
				tblControle2.setValue("uidcontrole1", tblControle1.getUidLastRecordProccessed());
				tblControle2.setValue("codiforn", codiforn);
				tblControle2.setValue("codiespe", codiespe);
				tblControle2.setValue("data", data);
				tblControle2.setValue("alterado", "N");
				tblControle2.setValue("quantidade", 1);
				tblControle2.setValue("vlrcusto", vlrCusto);
				tblControle2.setValue("vlrvenda", vlrVenda);
				tblControle2.setValue("vlrcustototal", vlrCusto);
				tblControle2.setValue("vlrvendatotal", vlrVenda);
				tblControle2.setValue("numeultireci", numereci);
				tblControle2.execute();
				
				// Inclui o registro de movimentacao do item
				tblControle3.insert();
				tblControle3.setValue("uidcontrole1", tblControle1.getUidLastRecordProccessed());
				tblControle3.setValue("numereci", numereci);
				tblControle3.setValue("codiforn", codiforn);
				tblControle3.setValue("codiespe", codiespe);
				tblControle3.setValue("data", data);
				tblControle3.setValue("alterado", "N");
				tblControle3.setValue("vlrcusto", vlrCusto);
				tblControle3.setValue("vlrvenda", vlrVenda);
				tblControle3.execute();
			}
			else {
				// Caso o valor de custo ou valor de venda seja diferente da ultima movimentacao
				if ((vlrCusto!=tblControle1.getDouble("lastVlrCusto")) || (vlrVenda!=tblControle1.getDouble("lastVlrVenda"))) {
					String uid = tblControle1.getString("uid");
					Integer numeroAlteracoes = tblControle1.getInteger("numeroAlteracoes");
					
					tblControle1.update();
					tblControle1.setValue("lastModify", data); // Data da ultima vez que o valor de venda ou custo do item foi modificado
					tblControle1.setValue("lastVlrCusto", vlrCusto); // Valor de custo na ultima vez que o item foi modificado
					tblControle1.setValue("lastVlrVenda", vlrVenda); // Valor de venda na ultima vez que o item foi modificado
					tblControle1.setValue("numeroAlteracoes", numeroAlteracoes+1);
					tblControle1.setValue("numeultireci", numereci);
					tblControle1.setFilter("uid", uid);
					tblControle1.execute();
					
					tblControle2.insert();
					tblControle2.setValue("uidcontrole1", uid);
					tblControle2.setValue("codiforn", codiforn);
					tblControle2.setValue("codiespe", codiespe);
					tblControle2.setValue("data", data);
					tblControle2.setValue("alterado", "S");
					tblControle2.setValue("quantidade", 1);
					tblControle2.setValue("vlrcusto", vlrCusto);
					tblControle2.setValue("vlrvenda", vlrVenda);
					tblControle2.setValue("numeultireci", numereci);
					tblControle2.execute();
					
					tblControle3.insert();
					tblControle3.setValue("uidcontrole1", uid);
					tblControle3.setValue("numereci", numereci);
					tblControle3.setValue("codiforn", codiforn);
					tblControle3.setValue("codiespe", codiespe);
					tblControle3.setValue("data", data);
					tblControle3.setValue("alterado", "S");
					tblControle3.setValue("vlrcusto", vlrCusto);
					tblControle3.setValue("vlrvenda", vlrVenda);
					tblControle3.execute();
				}
				else {
					tblControle2.select("*");
					tblControle2.setFilter("codiforn", codiforn);
					tblControle2.setFilter("codiespe", codiespe);
					tblControle2.setOrder("data desc");
					tblControle2.setLimit(1);
					tblControle2.loadData();
					
					String uid = tblControle2.getString("uid");
					Double vlrcustototal = tblControle2.getDouble("vlrcustototal");
					Double vlrvendatotal = tblControle2.getDouble("vlrvendatotal");
					Integer quantidade = tblControle2.getInteger("quantidade");
					
					tblControle2.update();
					tblControle2.setValue("quantidade", quantidade+1);
					tblControle2.setValue("vlrcustototal", vlrcustototal+vlrCusto);
					tblControle2.setValue("vlrvendatotal", vlrvendatotal+vlrVenda);
					tblControle2.setValue("numeultireci", numereci);
					tblControle2.setFilter("uid", uid);
					tblControle2.execute();
					
					tblControle3.insert();
					tblControle3.setValue("uidcontrole1", uid);
					tblControle3.setValue("numereci", numereci);
					tblControle3.setValue("codiforn", codiforn);
					tblControle3.setValue("codiespe", codiespe);
					tblControle3.setValue("data", data);
					tblControle3.setValue("alterado", "S");
					tblControle3.setValue("vlrcusto", vlrCusto);
					tblControle3.setValue("vlrvenda", vlrVenda);
					tblControle3.execute();
				}
			}
		}
	}
	
	// Retira o codigo do nome do medico
	public static void updateNomeMedicos() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblMedicos2 = ui.database.loadTableByName("medicos");
		tblMedicos2.setAuditing(false);
		
		Table tblMedicos = ui.database.loadTableByName("medicos");
		tblMedicos.select("codimedi, nome");
		tblMedicos.loadData();
		while (!tblMedicos.eof()) {
			try {
				String nome = tblMedicos.getString("nome");
				nome = nome.substring(0, nome.indexOf(" ")).trim();
				int codimedi = tblMedicos.getInteger("codimedi");
				int codimedi2 = Integer.valueOf(nome);
				
				if (codimedi==codimedi2) {
					System.out.println(tblMedicos.getString("nome"));
					nome = tblMedicos.getString("nome");
					nome = nome.substring(nome.indexOf(" ")+1);
							
					tblMedicos2.update();
					tblMedicos2.setString("nome", nome.trim());
					tblMedicos2.setFilter("uid", tblMedicos.getString("uid"));
					tblMedicos2.execute();
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			tblMedicos.next();
		}
	}
	
	public static void updateDescricaoEspecialidades() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblEspecialidades2 = ui.database.loadTableByName("especialidades");
		tblEspecialidades2.setAuditing(false);
		
		Table tblEspecialidades = ui.database.loadTableByName("especialidades");
		tblEspecialidades.select("codiespe, descricao");
		tblEspecialidades.loadData();
		while (!tblEspecialidades.eof()) {
			try {
				String descricao = tblEspecialidades.getString("descricao");
				descricao = descricao.substring(0, descricao.indexOf(" ")).trim();
				int codiespe = tblEspecialidades.getInteger("codiespe");
				int codiespe2 = Integer.valueOf(descricao);
				
				if (codiespe==codiespe2) {
					System.out.println(tblEspecialidades.getString("descricao"));
					descricao = tblEspecialidades.getString("descricao");
					descricao = descricao.substring(descricao.indexOf(" ")+1).trim();
							
					tblEspecialidades2.update();
					tblEspecialidades2.setString("descricao", descricao.trim());
					tblEspecialidades2.setFilter("uid", tblEspecialidades.getString("uid"));
					tblEspecialidades2.execute();
				}
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
			tblEspecialidades.next();
		}
	}
	
	public void updateUidMarcCons() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();

		Table tblMarccons = ui.database.loadTableByName("marccons");
		tblMarccons.alterTable();
		
		Table tblMarccons2 = ui.database.loadTableByName("marccons");
		
		while (true) {
			tblMarccons.select("sequencia");
			tblMarccons.setWhere("(marccons.uid is null)");
			tblMarccons.setLimit(100);
			tblMarccons.loadData();
			if (tblMarccons.eof()) {
				break;
			}
			
			while (!tblMarccons.eof()) {
				tblMarccons2.update();
				tblMarccons2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
			    tblMarccons2.setFilter("sequencia", tblMarccons.getString("sequencia"));
				tblMarccons2.execute();
				
				tblMarccons.next();
			}
		}
		System.out.println("Processo concluido!");
	}

	public static void updateUidPermissoes() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblPermissoes1 = ui.database.loadTableByName("permissoes");
		tblPermissoes1.setAuditing(false);
		
		Table tblPermissoes2 = ui.database.loadTableByName("permissoes");
		tblPermissoes2.setAuditing(false);
		
		try {
			ui.database.openConnection();
			
			tblPermissoes1.select("*");
			tblPermissoes1.loadData();
			while (!tblPermissoes1.eof()) {
				if (tblPermissoes1.getString("uid").length()<10) {
					tblPermissoes2.update();
					tblPermissoes2.setString("uid", UUID.randomUUID().toString().toUpperCase());
					tblPermissoes2.setFilter("idmenu", tblPermissoes1.getString("idmenu"));
					tblPermissoes2.setFilter("idgrupo", tblPermissoes1.getString("idgrupo"));
					tblPermissoes2.execute();
				}
				
				tblPermissoes1.next();
			}
		}
		finally {
			ui.database.closeConnection();
		}
	}
	
	public static void insertUsuarios(String login, String nome, String idGrupo, String senha) {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		Table tblUsuarios = ui.database.loadTableByName("usuarios");
		
		try {
			ui.database.openConnection();

			tblUsuarios.select("*");
			tblUsuarios.setFilter("projectName", "prosaude2"); 
			tblUsuarios.setFilter("login", login);
			tblUsuarios.loadData();
			if (tblUsuarios.eof()) {
				tblUsuarios.insert();
				tblUsuarios.setString("projectName", "prosaude2");
				tblUsuarios.setString("uid", UUID.randomUUID().toString().toUpperCase());
				tblUsuarios.setString("login", login);
				tblUsuarios.setString("nome", nome);
				tblUsuarios.setString("idgrupo", idGrupo);
				tblUsuarios.setString("senha", senha);
				tblUsuarios.execute();
			}
		}
		finally {
			ui.database.closeConnection();
		}
	}
	
	public static void insertGrupo(String idGrupo, String descricao) {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		Table tblGrupUsua = ui.database.loadTableByName("grupusua");
		try {
			ui.database.openConnection();

			tblGrupUsua.select("*");
			tblGrupUsua.setFilter("projectName", "prosaude2");
			tblGrupUsua.setFilter("idgrupo", idGrupo);
			tblGrupUsua.loadData();
			if (tblGrupUsua.eof()) {
				tblGrupUsua.insert();
				tblGrupUsua.setString("uid", UUID.randomUUID().toString().toUpperCase());
				tblGrupUsua.setString("projectName", "prosaude2");
				tblGrupUsua.setString("idgrupo", idGrupo);
				tblGrupUsua.setString("descricao", descricao);
				tblGrupUsua.execute();
			}
		}
		finally {
			ui.database.closeConnection();
		}
	}

	public static void insertMenuItem(String codiopti, String grau, String texto, OptionType optionType, String mainOptionIconName, String mainOptionDescription, String optionCommand, String shortcutIconName, String shortcutTip, String shortcutCommand) {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		Table tblSysOptions = ui.database.loadTableByName("sysoptions");
		try {
			ui.database.openConnection();
			
			tblSysOptions.select("uid");
			tblSysOptions.setFilter("projectname", ui.getProjectName());
			tblSysOptions.setFilter("codiopti", codiopti);
			tblSysOptions.loadData();
			if (tblSysOptions.eof()) {
				tblSysOptions.insert();
				tblSysOptions.setValue("projectname", ui.getProjectName());
				tblSysOptions.setValue("codiopti", codiopti);
				tblSysOptions.setValue("grau", grau);
				tblSysOptions.setValue("textoption", texto);
				tblSysOptions.setValue("optiontype", optionType.getValor());
				tblSysOptions.setValue("mainoptioniconname", mainOptionIconName);
				tblSysOptions.setValue("mainoptiondesc", mainOptionDescription);
				tblSysOptions.setValue("optioncommand", optionCommand);
				tblSysOptions.setValue("shortcuticonname", shortcutIconName);
				tblSysOptions.setValue("shortcuttip", shortcutTip);
				tblSysOptions.setValue("shortcutcommand", shortcutCommand);
				tblSysOptions.execute();
			}
		}
		finally {
			ui.database.closeConnection();
		}
	}
	
	// Deve criar o menu somente se o mesmo estiver vazio
	public static void createMenu() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();

		insertMenuItem("1", "1", "CADASTROS", OptionType.MAINOPTION, "", "", "", "", "", "");
		insertMenuItem("1.01", "2", "PROCEDIMENTOS", OptionType.NORMALOPTION, "", "", "Especialidades", "", "", "");
		insertMenuItem("1.02", "2", "MÉDICOS", OptionType.NORMALOPTION, "", "", "Medicos", "", "", "");
		insertMenuItem("1.03", "2", "FORNECEDORES", OptionType.NORMALOPTION, "", "", "Fornecedores", "", "", "");
		insertMenuItem("1.04", "2", "CEP", OptionType.NORMALOPTION, "", "", "Cep", "", "", "");
		insertMenuItem("1.05", "2", "PREFIXOS", OptionType.NORMALOPTION, "", "", "Prefixo", "", "", "");
		insertMenuItem("1.06", "2", "CAIXAS", OptionType.NORMALOPTION, "", "", "Caixas", "", "", "");
		insertMenuItem("1.07", "2", "CENTROS DE CUSTO", OptionType.NORMALOPTION, "", "", "CentrosCusto", "", "", "");
		insertMenuItem("1.08", "2", "REQUISITANTES", OptionType.NORMALOPTION, "", "", "Requisitantes", "", "", "");
		
		insertMenuItem("2", "1", "ATENDIMENTO", OptionType.MAINOPTION, "", "", "", "", "", "");
		insertMenuItem("2.01", "2", "ASSOCIADOS", OptionType.NORMALOPTION, "", "", "Associados", "", "", "");
		insertMenuItem("2.02", "2", "AGENDAMENTOS", OptionType.NORMALOPTION, "", "", "MarcCons", "", "", "");
		
		insertMenuItem("3", "1", "VENDAS", OptionType.MAINOPTION, "", "", "", "", "", "");
		insertMenuItem("3.01", "2", "CARRINHO", OptionType.NORMALOPTION, "", "", "CarrComp2", "", "", "");
		insertMenuItem("3.02", "2", "RECIBOS", OptionType.NORMALOPTION, "", "", "Recibos", "", "", "");
		insertMenuItem("3.03", "2", "DEVOLUÇÕES/EXTORNOS", OptionType.NORMALOPTION, "", "", "DevoReci", "", "", "");
		insertMenuItem("3.04", "2", "RETIRADAS", OptionType.NORMALOPTION, "", "", "SaidCaix", "", "", "");
		
		insertMenuItem("4", "1", "RELATÓRIOS", OptionType.MAINOPTION, "", "", "", "", "", "");
		insertMenuItem("4.01", "2", "CADASTROS", OptionType.GROUPOPTION, "", "", "", "", "", "");
		insertMenuItem("4.01.01", "3", "RELAÇÃO DE ASSOCIADOS", OptionType.COMMANDOPTION, "", "", "RelaAssociados", "", "", "");
		insertMenuItem("4.01.02", "3", "RELAÇÃO DE DESPESAS POR CENTRO DE CUSTO", OptionType.COMMANDOPTION, "", "", "RelaCentCust", "", "", "");

		insertMenuItem("4.02", "2", "ESTATÍSTICAS", OptionType.GROUPOPTION, "", "", "", "", "", "");
		insertMenuItem("4.02.01", "3", "ATENDIMENTO POR PERÍODO", OptionType.COMMANDOPTION, "", "", "RelaAtendimentoPeriodo", "", "", "");
		insertMenuItem("4.02.02", "3", "ESTATÍSTICA DE CONSULTAS POR ESPECIALIDADES", OptionType.COMMANDOPTION, "", "", "RelaEstaConsEspe", "", "", "");
		insertMenuItem("4.02.03", "3", "ASSOCIADOS INSCRITOS POR PERÍODO", OptionType.COMMANDOPTION, "", "", "RelaInscPeri", "", "", "");
		insertMenuItem("4.02.04", "3", "ASSOCIADOS ATUALIZADOS POR PERÍODO", OptionType.COMMANDOPTION, "", "", "RelaModiPeri", "", "", "");
		insertMenuItem("4.02.05", "3", "ASSOCIADOS POR PREFIXO", OptionType.COMMANDOPTION, "", "", "RelaAssoPref", "", "", "");
		insertMenuItem("4.02.06", "3", "ESTATÍSTICA DE DENSIDADE POR BAIRRO", OptionType.COMMANDOPTION, "", "", "RelaDensBair", "", "", "");
		insertMenuItem("4.02.07", "3", "ESTATÍSTICA DE DENSIDADE POR MUNICÍPIO", OptionType.COMMANDOPTION, "", "", "RelaDensMuni", "", "", "");
		insertMenuItem("4.02.08", "3", "ESTATÍSTICA DE VENDAS POR PERÍODO", OptionType.COMMANDOPTION, "", "", "RelaEstaVend", "", "", "");
		insertMenuItem("4.02.09", "3", "PRODUTIVIDADE REQUISITANTES", OptionType.COMMANDOPTION, "", "", "RelaProdutividadeRequisitantes", "", "", "");
		
		insertMenuItem("4.03", "2", "MARKETING", OptionType.GROUPOPTION, "", "", "", "", "", "");
		insertMenuItem("4.03.01", "3", "ETIQUETAS POR CASA", OptionType.COMMANDOPTION, "", "", "RelaEtiqCasa", "", "", "");
		insertMenuItem("4.03.02", "3", "ETIQUETAS POR ASSOCIADOS", OptionType.COMMANDOPTION, "", "", "RelaEtiqIndi", "", "", "");
		insertMenuItem("4.03.03", "3", "TELEFONES PARA ENVIO SMS", OptionType.COMMANDOPTION, "", "", "", "", "", "");
		
		insertMenuItem("4.04", "2", "CAIXA", OptionType.GROUPOPTION, "", "", "", "", "", "");
		insertMenuItem("4.04.01", "3", "FECHAMENTO DO CAIXA", OptionType.COMMANDOPTION, "", "", "RelaFechCaix", "", "", "");
		insertMenuItem("4.04.02", "3", "RESUMO DO FECHAMENTO DO CAIXA", OptionType.COMMANDOPTION, "", "", "RelaFechCaix2", "", "", "");
		
		insertMenuItem("5", "1", "CONFIGURAÇÕES", OptionType.MAINOPTION, "", "", "", "", "", "");
		insertMenuItem("5.01", "2", "PERMISSÕES", OptionType.NORMALOPTION, "", "", "SysGroups", "", "", "");
		insertMenuItem("5.02", "2", "USUÁRIOS", OptionType.NORMALOPTION, "", "", "SysUsers", "", "", "");
		insertMenuItem("5.03", "2", "OPÇÕES", OptionType.NORMALOPTION, "", "", "SysOptions", "", "", "");
	}

	public static void updateUidDevoReci() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblDevoReci = ui.database.loadTableByName("devoreci");
		tblDevoReci.setAuditing(false);
		
		Table tblDevoReci2 = ui.database.loadTableByName("devoreci");
		tblDevoReci2.setAuditing(false);
		
		while (true) {
			tblDevoReci.select("*");
			tblDevoReci.setWhere("(devoreci.uid is null)");
			tblDevoReci.setLimit(1000);
			tblDevoReci.loadData();
			
			if (tblDevoReci.eof()) {
				break;
			}
			else {
				while (!tblDevoReci.eof()) {
					System.out.println(tblDevoReci.getString("numereci"));
					if (tblDevoReci.getString("uid").isEmpty()) {
						tblDevoReci2.update();
						tblDevoReci2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
						tblDevoReci2.setFilter("numereci", tblDevoReci.getInteger("numereci"));
						tblDevoReci2.execute();
					}
					
					tblDevoReci.next();
				}
			}
		}
	}
	
	public static void updateUidSaidCaix() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblDevoReci = ui.database.loadTableByName("saidcaix");
		tblDevoReci.setAuditing(false);
		
		Table tblDevoReci2 = ui.database.loadTableByName("saidcaix");
		tblDevoReci2.setAuditing(false);

		while (true) {
			tblDevoReci.select("*");
			tblDevoReci.setWhere("(saidcaix.uid is null)");
			tblDevoReci.setLimit(1000);
			tblDevoReci.loadData();
			if (tblDevoReci.eof()) {
				break;
			}
			else {
				while (!tblDevoReci.eof()) {
					System.out.println(tblDevoReci.getString("sequencia"));
					if (tblDevoReci.getString("uid").isEmpty()) {
						tblDevoReci2.update();
						tblDevoReci2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
						tblDevoReci2.setFilter("sequencia", tblDevoReci.getInteger("sequencia"));
						tblDevoReci2.execute();
					}
					
					tblDevoReci.next();
				}
			}
		}
	}

	public static void updateUidEspeMedi() {
		System.out.println("Atualizando espemedi");
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblEspeForn = ui.database.loadTableByName("espemedi");
		tblEspeForn.setAuditing(false);
		
		Table tblEspeForn2 = ui.database.loadTableByName("espemedi");
		tblEspeForn2.setAuditing(false);
		
		while (true) {
			tblEspeForn.select("*");
			tblEspeForn.setWhere("(espemedi.uid is null)");
			tblEspeForn.setLimit(1000);
			tblEspeForn.loadData();
			if (tblEspeForn.eof()) {
				break;
			}
			else {
				while (!tblEspeForn.eof()) {
					System.out.println("Espemedi: " + tblEspeForn.getString("sequencia"));
					if (tblEspeForn.getString("uid").isEmpty()) {
						tblEspeForn2.update();
						tblEspeForn2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
						tblEspeForn2.setFilter("sequencia", tblEspeForn.getInteger("sequencia"));
						tblEspeForn2.execute();
					}
					
					tblEspeForn.next();
				}
			}
		}
	}

	public static void updateUidEspeForn() {
		System.out.println("Atualizando espeforn");
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblEspeForn = ui.database.loadTableByName("espeforn2");
		tblEspeForn.setAuditing(false);
		
		Table tblEspeForn2 = ui.database.loadTableByName("espeforn2");
		tblEspeForn2.setAuditing(false);
		
		while (true) {
			tblEspeForn.select("*");
			tblEspeForn.setWhere("(espeforn2.uid is null)");
			tblEspeForn.setLimit(1000);
			tblEspeForn.loadData();
			if (tblEspeForn.eof()) {
				break;
			}
			else {
				while (!tblEspeForn.eof()) {
					System.out.println("Espeforn: " + tblEspeForn.getString("sequencia"));
					if (tblEspeForn.getString("uid").isEmpty()) {
						tblEspeForn2.update();
						tblEspeForn2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
						tblEspeForn2.setFilter("sequencia", tblEspeForn.getInteger("sequencia"));
						tblEspeForn2.execute();
					}
					
					tblEspeForn.next();
				}
			}
		}
	}
	
	public static void updateUidRecibos() {
		System.out.println("Atualizando recibos...");
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblRecibos = ui.database.loadTableByName("recibos");
		tblRecibos.setAuditing(false);
		
		Table tblRecibos2 = ui.database.loadTableByName("recibos");
		tblRecibos2.setAuditing(false);
		
		while (true) {
			tblRecibos.select("*");
			tblRecibos.setWhere("(recibos.uid is null)");
			tblRecibos.setLimit(1000);
			tblRecibos.loadData();
			if (tblRecibos.eof()) {
				break;
			}
			else {
				while (!tblRecibos.eof()) {
					System.out.println("Recibo: " + tblRecibos.getString("sequencia"));
					tblRecibos2.update();
					tblRecibos2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblRecibos2.setFilter("sequencia", tblRecibos.getInteger("sequencia"));
					tblRecibos2.execute();
					
					tblRecibos.next();
				}
			}
		}
	}
	
	public static void updateUidMarcCons2() {
		System.out.println("Atualizando marccons...");
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblMarcCons = ui.database.loadTableByName("marccons");
		tblMarcCons.setAuditing(false);
		
		Table tblMarcCons2 = ui.database.loadTableByName("marccons");
		tblMarcCons2.setAuditing(false);
		
		Table tblAssociados = ui.database.loadTableByName("associados");
		tblAssociados.setAuditing(false);

		while (true) {
			tblMarcCons.select("*");
			tblMarcCons.setWhere("(marccons.uid is null)");
			tblMarcCons.setLimit(1000);
			tblMarcCons.loadData();
			if (tblMarcCons.eof()) {
				break;
			}
			
			while (!tblMarcCons.eof()) {
				System.out.println("MarcCons: " + tblMarcCons.getString("sequencia"));
				tblMarcCons2.update();
				tblMarcCons2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
				tblMarcCons2.setFilter("sequencia", tblMarcCons.getInteger("sequencia"));
				tblMarcCons2.execute();
				
				tblMarcCons.next();
			}
		}
		
		//************************************************************
		boolean continuar = true;
		while (continuar) {
			
			tblMarcCons.select("uid, codiasso");
			tblMarcCons.setWhere("(marccons.uidassociado is null) or (length(marccons.uidassociado)<=1)");
			tblMarcCons.setLimit(1000);
			tblMarcCons.loadData();
			if (tblMarcCons.eof()) {
				continuar = false;
			}
			int numero =0;
			while (!tblMarcCons.eof()) {
				
				tblAssociados.select("uid");
				tblAssociados.setFilter("sequencia", tblMarcCons.getInteger("codiasso"));
				tblAssociados.loadData();
				
				System.out.println("Processando marccons: " + tblMarcCons.getString("uid") + " - associado:" + tblAssociados.getString("uid") + " " + numero++);
				
				if (!tblAssociados.eof()) {
					tblMarcCons2.update();
					tblMarcCons2.setValue("uidassociado", tblAssociados.getString("uid"));
					tblMarcCons2.setFilter("uid", tblMarcCons.getString("uid"));
					tblMarcCons2.execute();
				}
				else {
					tblMarcCons2.update();
					tblMarcCons2.setValue("uidassociado", "nao identificado");
					tblMarcCons2.setFilter("uid", tblMarcCons.getString("uid"));
					tblMarcCons2.execute();
				}
				
				tblMarcCons.next();
			}
		}
	}
	
	public static void updateUidAssociados() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblRecibos = ui.database.loadTableByName("associados");
		tblRecibos.setAuditing(false);
		
		Table tblRecibos2 = ui.database.loadTableByName("associados");
		tblRecibos2.setAuditing(false);
		
		boolean continuar = true;
		while (continuar) {
			tblRecibos.select("*");
			tblRecibos.setWhere("(associados.uid is null)");
			tblRecibos.setLimit(100);
			tblRecibos.loadData();
			if (tblRecibos.eof()) {
				continuar = false;
			}
			while (!tblRecibos.eof()) {
				System.out.println("Associados " + tblRecibos.getInteger("sequencia"));
				tblRecibos2.update();
				tblRecibos2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
				tblRecibos2.setFilter("sequencia", tblRecibos.getInteger("sequencia"));
				tblRecibos2.execute();
				
				tblRecibos.next();
			}
		}
	}
	
	public static void updateUidMedicos() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblRecibos = ui.database.loadTableByName("medicos");
		tblRecibos.setAuditing(false);
		
		Table tblRecibos2 = ui.database.loadTableByName("medicos");
		tblRecibos2.setAuditing(false);
		
		while (true) {
			tblRecibos.select("*");
			tblRecibos.setWhere("(medicos.uid is null)");
			tblRecibos.setLimit(1000);
			tblRecibos.loadData();
			if (tblRecibos.eof()) {
				break;
			}
			else {
				while (!tblRecibos.eof()) {
					System.out.println("codimedi: " + tblRecibos.getString("codimedi"));
					tblRecibos2.update();
					tblRecibos2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblRecibos2.setFilter("codimedi", tblRecibos.getInteger("codimedi"));
					tblRecibos2.execute();
					
					tblRecibos.next();
				}
			}
		}
	}
	
	public static void updateUidFornecedores() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblRecibos = ui.database.loadTableByName("fornecedores");
		tblRecibos.setAuditing(false);
		
		Table tblRecibos2 = ui.database.loadTableByName("fornecedores");
		tblRecibos2.setAuditing(false);
		
		while (true) {
			tblRecibos.select("*");
			tblRecibos.setWhere("(fornecedores.uid is null)");
			tblRecibos.setLimit(1000);
			tblRecibos.loadData();
			if (tblRecibos.eof()) {
				break;
			}
			else {
				while (!tblRecibos.eof()) {
					System.out.println("codiforn: " + tblRecibos.getString("codiforn"));
					tblRecibos2.update();
					tblRecibos2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblRecibos2.setFilter("codiforn", tblRecibos.getInteger("codiforn"));
					tblRecibos2.execute();
					
					tblRecibos.next();
				}
			}
		}
	}
	
	
	public static void updateUidEspecialidades() {
		System.out.println("Atualizando especialidades...");
		
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblFornecedores = ui.database.loadTableByName("fornecedores");
		tblFornecedores.setAuditing(false);
		
		Table tblEspecialidades = ui.database.loadTableByName("especialidades");
		tblEspecialidades.setAuditing(false);
		
		Table tblEspecialidades2 = ui.database.loadTableByName("especialidades");
		tblEspecialidades2.setAuditing(false);
		
		tblEspecialidades.alterTable();
		
		while (true) {
			tblEspecialidades.select("*");
			tblEspecialidades.setWhere("(especialidades.uid is null)");
			tblEspecialidades.setLimit(1000);
			tblEspecialidades.loadData();
			if (tblEspecialidades.eof()) {
				break;
			}
			else {
				while (!tblEspecialidades.eof()) {
					System.out.println("Especialidades: " + tblEspecialidades.getInteger("codiespe"));
					
					tblFornecedores.select("uid");
					tblFornecedores.setFilter("codiforn", tblEspecialidades.getInteger("codiforn"));
					tblFornecedores.loadData();
					
					tblEspecialidades2.update();
					tblEspecialidades2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblEspecialidades2.setValue("uidfornecedor", tblFornecedores.getString("uid"));
					tblEspecialidades2.setFilter("codiespe", tblEspecialidades.getInteger("codiespe"));
					tblEspecialidades2.execute();
					
					tblEspecialidades.next();
				}
			}
		}
	}
	
	public static void updateUidAlteAgenMedi() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblRecibos = ui.database.loadTableByName("alteagenmedi");
		tblRecibos.setAuditing(false);
		
		Table tblRecibos2 = ui.database.loadTableByName("alteagenmedi");
		tblRecibos2.setAuditing(false);

		while (true) {
			tblRecibos.select("*");
			tblRecibos.setWhere("(alteagenmedi.uid is null)");
			tblRecibos.setLimit(1000);
			tblRecibos.loadData();
			if (tblRecibos.eof()) {
				break;
			}
			else {
				while (!tblRecibos.eof()) {
					tblRecibos2.update();
					tblRecibos2.setValue("uid", UUID.randomUUID().toString().toUpperCase());
					tblRecibos2.setFilter("sequencia", tblRecibos.getInteger("sequencia"));
					tblRecibos2.execute();
					
					tblRecibos.next();
				}
			}
		}
	}
	
	public static void updateMD5Ende() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblAssociados = ui.database.loadTableByName("associados");
		tblAssociados.setTableChildrenList(new ArrayList<TableChild>());
		tblAssociados.setAuditing(false);
		
		Table tblAssociados2 = ui.database.loadTableByName("associados");
		tblAssociados2.setTableChildrenList(new ArrayList<TableChild>());
		tblAssociados2.setAuditing(false);
		
		try {
			ui.database.openConnection();
			
			//ui.database.executeCommand("update associados set md5ende=null where (cid<>'MANAUS')");

			Integer total = 0;
			Integer atual = 0;
			
			ResultSet rs2 = ui.database.executeSelect("select count(*) as qtd from associados where (associados.md5ende is null)");
			if (rs2.next()) {
				total = rs2.getInt("qtd");
			}
			
			while (true) {
				tblAssociados.select("*");
				tblAssociados.setWhere("(associados.md5ende is null)");
				tblAssociados.setLimit(1000);
				tblAssociados.loadData();
				if (tblAssociados.eof()) {
					break;
				}
				else {
					while (!tblAssociados.eof()) {
						System.out.println("associados: " + tblAssociados.getString("uid") + " registro: " + atual++ + "/" + total);
						
						tblAssociados2.update();
						tblAssociados2.setString("md5ende", Utils.md5(tblAssociados.getString("uf") + tblAssociados.getString("cid") + tblAssociados.getString("bai") + tblAssociados.getString("tipo") + tblAssociados.getString("rua") + tblAssociados.getString("endenume") + tblAssociados.getString("complemento") ));
						tblAssociados2.setFilter("uid", tblAssociados.getString("uid"));
						tblAssociados2.execute();
						
						tblAssociados.next();
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ui.database.closeConnection();
		}
	}
	
	public static void updateModiData() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		Table tblAssociados = ui.database.loadTableByName("associados");
		tblAssociados.setAuditing(false);
		
		Table tblAssociados2 = ui.database.loadTableByName("associados");
		tblAssociados2.setAuditing(false);
		
		Table tblMarcCons = ui.database.loadTableByName("marccons");
		Table tblRecibos = ui.database.loadTableByName("recibos");

		try {
			ui.database.openConnection();
			
			while (true) {
				tblAssociados.select("*");
				tblAssociados.setWhere("(associados.processamento is null)");
				tblAssociados.setLimit(1000);
				tblAssociados.loadData();
				if (tblAssociados.eof()) {
					break;
				}
				else {
					while (!tblAssociados.eof()) {
						Date ulticont = null;
						
						// Pega a data em que ele teve o ultimo agendamento criado
						tblMarcCons.select("incldata");
						tblMarcCons.setFilter("codiasso", tblAssociados.getInteger("sequencia"));
						tblMarcCons.setOrder("incldata desc");
						tblMarcCons.setLimit(1);
						tblMarcCons.loadData();
						if (!tblMarcCons.eof()) {
							ulticont = tblMarcCons.getDate("incldata");
						}
						
						// Pega a data em que ele teve o ultimo recibo emitido
						tblRecibos.select("incldata");
						tblRecibos.setFilter("codiasso", tblAssociados.getInteger("sequencia"));
						tblRecibos.setOrder("incldata desc");
						tblRecibos.setLimit(1);
						tblRecibos.loadData();
						if (!tblRecibos.eof()) {
							if (ulticont==null) {
								ulticont=tblRecibos.getDate("incldata");
							}
							else {
								if (ulticont.before(tblRecibos.getDate("incldata"))) {
									ulticont = tblRecibos.getDate("incldata");
								}
							}
						}

						String incldata = Utils.getSimpleDateFormat(tblAssociados.getDate("incldata"), "dd/MM/yyyy");
						String modidata = Utils.getSimpleDateFormat(tblAssociados.getDate("modidata"), "dd/MM/yyyy");

						// Caso o associado não esteja dentro do periodo com problema
						// caso a data da ultima atualizacao do cadastro seja posterior a data da ultima compra dele
						// pode ser que a ficha do associado tenha sido atualizada pelo telemarketing
						// entao considera esta data como sendo o ultimo contato
						if ((!modidata.equalsIgnoreCase("03/09/2017")) && (!modidata.equalsIgnoreCase("19/08/2018")))  {
							// Caso o usuario já tenha feito compra
							if (ulticont!=null) {
								if (ulticont.before(tblAssociados.getDate("modidata"))) {
									ulticont = tblAssociados.getDate("modidata");
								}
							}
							// Caso o usuario nunca tenha feito compra, o ultimo contato é a data da modificacao da carteira
							// caso ele nao esteja dentro do periodo de problema
							else {
								ulticont = tblAssociados.getDate("modidata");
							}
						}
						// Caso o usuario esteja dentro do periodo de problema
						else {
							// Caso o usuario já tenha feito compra, nao meche na data do ultimo contato
							if (ulticont!=null) {
							}
							// Caso o usuario nunca tenha feito compra, o ultimo contato é a data da inclusao da carteira
							// dele no sistema, pois ele esta dentro do periodo com problema
							else {
								ulticont = tblAssociados.getDate("incldata");
							}
						}
						
						// Chegou nesse ponto com ulticon nullo, entao o usuario nunca fez compra, esta dentro do perido de problema, 
						if (ulticont == null) {
							System.out.println("Nao resolveu a data do ultimo contato para o associado : " + tblAssociados.getInteger("sequencia"));
						}
						
						tblAssociados2.update();
						tblAssociados2.setValue("ulticont", ulticont);
						tblAssociados2.setValue("processamento", "1");
						tblAssociados2.setFilter("sequencia", tblAssociados.getInteger("sequencia"));
						tblAssociados2.execute();
						
						System.out.println("Processando associado: " + tblAssociados.getInteger("sequencia"));
						
						tblAssociados.next();
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ui.database.closeConnection();
		}
	}

	/*
	   function TelefoneValido($telefone) {
		      $retorno = false;
		      $conteudo = tiraCaracteres($telefone);
		      if (strlen(trim($conteudo))>0) {
		         if ((strlen($conteudo)==8) || (strlen($conteudo)==9) || (strlen($conteudo)==11) || (strlen($conteudo)==13)) {
		            $retorno = true;
		         }
		         
		         if ((substr($conteudo,0,1)!='9') && (substr($conteudo,0,1)!='8')) {
		            $retorno = false;
		         }
		      }
		      else {
		         $retorno = false;
		      }
		      return $retorno;
		   }
		   */

	
	public static boolean telefoneValido(String telefone) {
		boolean retorno = false;
		if (telefone!=null) {
			String conteudo = tiraCaracters(telefone);
			if (conteudo.length()>0) {
				if ((conteudo.length()==8) || (conteudo.length()==9) || (conteudo.length()==11) || (conteudo.length()==13)) {
					retorno = true;
				}
				
				if ((!conteudo.substring(0, 1).equalsIgnoreCase("9")) && (!conteudo.substring(0, 1).equalsIgnoreCase("8"))) {
					retorno = false;
				}
			}
		}
		
		return retorno;
	}

	public static String tiraCaracters(String telefone) {
		String conteudoValido = "1234567890"; 
		String novo = "";
		
		if (telefone!=null) {
			String antigo = telefone.trim();
			
			while (antigo.length()>0) {
				String caracter = antigo.substring(0, 1);
				if (conteudoValido.indexOf(caracter)>=0) {
					
				}
				else {
					caracter = "";
				}
				
				novo += caracter;
				antigo = antigo.substring(1);
			}
			if (novo.length()<8) {
				novo = "";
			}
		}
		
		return novo;
	}
	
	public static void incluiTelefone(String telefone) {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		if (telefone.length()==8) {
			telefone = "55929" + telefone;
		}
		
		if (telefone.length()==9) {
			telefone = "5592" + telefone;
		}
		
		if (telefone.length()==11) {
			telefone = "55" + telefone;
		}
		
		Table tblTelefones = ui.database.loadTableByName("telefones");
		tblTelefones.select("telefone");
		tblTelefones.setFilter("telefone", telefone);
		tblTelefones.loadData();
		if (tblTelefones.eof()) {
			tblTelefones.insert();
			tblTelefones.setValue("telefone", telefone);
			tblTelefones.setValue("processo", "java");
			tblTelefones.execute();
		}
	}
	
	public static void pegaTelefones() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();

		try {
			ui.database.openConnection();
			Table tblAssociados = ui.database.loadTableByName("associados");
			tblAssociados.setAuditing(false);
			
			Table tblAssociados2 = ui.database.loadTableByName("associados");
			tblAssociados2.setAuditing(false);
			
			boolean continuar = true;
			while (continuar) {
				tblAssociados.select("*");
				tblAssociados.setWhere("(processamento is null)");
				tblAssociados.setLimit(5000);
				tblAssociados.loadData();
				if (tblAssociados.eof()) {
					continuar = false;
				}
				else {
					while (!tblAssociados.eof()) {
						System.out.println("sequencia: " + tblAssociados.getString("sequencia"));
				         String telefone = tiraCaracters(tblAssociados.getString("telefone"));
				         if (telefoneValido(telefone)) {
				        	 incluiTelefone(telefone);
				         }
				         
				         telefone = tiraCaracters(tblAssociados.getString("telefone02"));
				         if (telefoneValido(telefone)) {
				        	 incluiTelefone(telefone);
				         }
				         
				         telefone = tiraCaracters(tblAssociados.getString("telefone03"));
				         if (telefoneValido(telefone)) {
				        	 incluiTelefone(telefone);
				         }
				         
				         telefone = tiraCaracters(tblAssociados.getString("telefone04"));
				         if (telefoneValido(telefone)) {
				        	 incluiTelefone(telefone);
				         }
				         
				         tblAssociados2.update();
				         tblAssociados2.setValue("processamento", "java");
				         tblAssociados2.setFilter("uid", tblAssociados.getString("uid"));
				         tblAssociados2.execute();
				         
				         tblAssociados.next();
					}
				}
			}
			System.out.println("processo concluido!");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ui.database.closeConnection();
		}
	}
	
	public static void gravarTelefones() {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		Table tblTelefones = ui.database.loadTableByName("telefones");
		Table tblTelefones2 = ui.database.loadTableByName("telefones");
		BufferedWriter buffWrite = null;
		
		try {
			ui.database.openConnection();
			
			try {
				buffWrite = new BufferedWriter(new FileWriter("c:/temp-2018/telefones.txt"));
				boolean continuar = true;
				while (continuar) {
					tblTelefones.select("*");
					tblTelefones.setWhere("(telefones.processamento is null)");
					tblTelefones.setOrder("sequencia");
					tblTelefones.setLimit(5000);
					tblTelefones.loadData();
					if (tblTelefones.eof()) {
						continuar = false;
					}
					while (!tblTelefones.eof()) {
						System.out.println(tblTelefones.getInteger("sequencia"));
						buffWrite.append(tblTelefones.getString("telefone") + "\r\n");
						buffWrite.flush();

						tblTelefones2.update();
						tblTelefones2.setValue("processamento", "s");
						tblTelefones2.setFilter("sequencia", tblTelefones.getInteger("sequencia"));
						tblTelefones2.execute();
						
						tblTelefones.next();
					}
				}				
			}
			finally {
				buffWrite.close();
			}
		}
		catch (Exception e) {
		    System.out.println(e.getMessage());
		}
		finally {
			ui.database.closeConnection();
		}
	}
}
