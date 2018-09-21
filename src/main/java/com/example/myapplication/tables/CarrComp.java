package com.example.myapplication.tables;

import java.sql.ResultSet;
import java.util.Date;

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
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent.SpecialConditionOfUpdateEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlButton;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.UI;

public class CarrComp {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("carrcomp");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("carrcomp");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("data", FieldType.DATE, 10);
				tblTabela.addField("codiasso", FieldType.INTEGER, 10);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("paciente", FieldType.VARCHAR, 50);
				tblTabela.addField("nasc", FieldType.DATE, 10);
				tblTabela.addField("processado", FieldType.DATETIME, 10);
				
				tblTabela.addField("vlrtotal", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrcustototal", FieldType.DOUBLE, 10);
				
				tblTabela.addField("codimedi", FieldType.VARCHAR, 10);
				tblTabela.addField("uidfornecedor", FieldType.VARCHAR, 50);
				tblTabela.addField("codiforn", FieldType.INTEGER, 10);
				tblTabela.addField("datacons", FieldType.DATE, 10);
				tblTabela.addField("diasema", FieldType.VARCHAR, 50);
				tblTabela.addField("horacons", FieldType.VARCHAR, 5);
				
				tblTabela.setPrimaryKey("sequencia");
				tblTabela.setOrder("sequencia desc");
				
				tblTabela.addIndex("data", "data");
				tblTabela.addIndex("codiasso", "codiasso");
				
				Field field = tblTabela.fieldByName("sequencia");
				field.setAutoIncrement(true);
				field.setReadOnly(true);
				
				tblTabela.fieldByName("sequencia").setReadOnly(true);
				tblTabela.fieldByName("data").setReadOnly(true);
				//tblTabela.fieldByName("codiasso").setReadOnly(true);
				tblTabela.fieldByName("nome").setReadOnly(true);
				tblTabela.fieldByName("vlrtotal").setReadOnly(true);
				tblTabela.fieldByName("vlrcustototal").setReadOnly(true);
				tblTabela.fieldByName("processado").setReadOnly(true);
				
				tblTabela.fieldByName("data").setRequired(true);
				tblTabela.fieldByName("codiasso").setRequired(true);
				tblTabela.fieldByName("nome").setRequired(true);
				tblTabela.fieldByName("paciente").setRequired(true);
				tblTabela.fieldByName("nasc").setRequired(true);
				
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
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("codiasso").addExistenceCheck("associados", "sequencia", "codiasso", "Associado não encontrado ou inválido!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						if ((event.getSourceTable().getString("nome")==null) || (event.getSourceTable().getString("nome").isEmpty())) {
							event.getSourceTable().setValue("nome", event.getTargetTable().getString("nome"));
							event.getSourceTable().setValue("nasc", event.getTargetTable().getDate("nasc"));
							event.getSourceTable().setValue("paciente", event.getTargetTable().getString("nome"));
						}
					}
				});
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setDate("data", Utils.getJustDate(new Date()));
						event.getTable().setDouble("vlrtotal", 0d);
						event.getTable().setDouble("vlrcustototal", 0d);
					}
				});
				
				tblTabela.addSpecialConditionOfUpdateEventListener(new SpecialConditionOfUpdateEventListener() {
					@Override
					public void onSpecialConditionOfUpdate(SpecialConditionOfUpdateEvent event) {
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
				
				Table tblCarrCons = database.loadTableByName("carrcons");
				tblTabela.addTableChild(tblCarrCons, "uidcarrcomp", false);
				
				Table tblCarrExamLabo = database.loadTableByName("carrExamLabo");
				tblTabela.addTableChild(tblCarrExamLabo, "uidcarrcomp", false);
				
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("carrcomp");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("carrcomp");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(database.loadTableByName("carrcomp"));
				rmGrid.setHeight("550px");
				rmGrid.setLimit(10);
				rmGrid.addField("sequencia", "Sequência", 100D);
				rmGrid.addField("data", "Data", 150d);
				rmGrid.addField("processado", "Processado", 100d);
				rmGrid.addField("nome", "Nome do associado", 250d, 1);
				rmGrid.addField("paciente", "Paciente", 250d);
				rmGrid.addField("vlrtotal", "Valor total", 150d);
				rmGrid.addField("incluser", "Inclusão - Usuário", 150d);
				rmGrid.addField("incldata", "Inclusão - Data", 130d);
				rmGrid.addField("modiuser", "Modificação - Usuário", 150d);
				rmGrid.addField("modidata", "Modificação - Data", 130d);

				rmGrid.setAllowDelete(false);
				rmGrid.setAllowPrint(false);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de compras:");
				controlForm.setWidth(790d);
				controlForm.setHeight(760d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do Associado/Paciente:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 90d);
					controlForm.addField("data", "Data", 110d);
					controlForm.addField("codiasso", "Cód.Associado", 130d);
					controlForm.addField("nome", "Nome", 250d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 120d);
					controlForm.addField("processado", "Processado", 120d);
					controlForm.addField("vlrtotal", "Vlr.Total", 120d);
					
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("carrcons", "Requisição de consultas:", SectionState.MAXIMIZED, 400d);
					{
						controlForm.addRmGrid("carrcons");
					}
					
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("carrexamlabo", "Requisição de exames:", SectionState.MAXIMIZED, 400d);
					{
						controlForm.addRmGrid("carrexamlabo");
					}
				}
				
				RmFormButtonBase rmFormButtonBase = controlForm.addRmFormButton("Confirmar recebimento");
				rmFormButtonBase.setIcon(new ThemeResource("imagens/library/money.png"));
				rmFormButtonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
					@Override
					public void onRmFormButtonClick(RmFormButtonClickEvent event) {
						Boolean executeProcesso = true;
						Table tblCarrComp = event.getControlForm().getTable();
						
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

									// Pega a relacao de consultas que estao agendadas
									String comando = "select * from carrcons";
									comando += " where (carrcons.uidcarrcomp='" + tblCarrComp.getString("uid") + "')";
									ResultSet resultSet = tblCarrComp.getDatabase().executeSelect(comando);
									while (resultSet.next()) {
										// Fornecedor para emissao do recibo
										tblFornecedores.select("uid, codiforn");
										tblFornecedores.setFilter("codimedi", resultSet.getInt("codimedi"));
										tblFornecedores.loadData();
										
										// Cria a guia de atendimento 
										tblMarcCons.insert();
										tblMarcCons.setValue("codiasso", tblCarrComp.getInteger("codiasso"));
										tblMarcCons.setValue("nome", tblCarrComp.getString("nome"));
										tblMarcCons.setValue("paciente", tblCarrComp.getString("paciente"));
										tblMarcCons.setValue("nasc", tblCarrComp.getDate("nasc"));
										tblMarcCons.setValue("cortesia", "N");
										tblMarcCons.setValue("codiespe", resultSet.getInt("codiespe"));
										tblMarcCons.setValue("codiespe_valor", resultSet.getDouble("vlrvenda"));
										tblMarcCons.setValue("codiespe_vlrcusto", resultSet.getDouble("vlrcusto"));
										tblMarcCons.setValue("ulticons", resultSet.getDate("ulticons"));
										tblMarcCons.setValue("ulticonsdias", resultSet.getInt("ulticonsdias"));
										tblMarcCons.setValue("tipoatend", resultSet.getString("tipoatend"));
										tblMarcCons.setValue("atendido", "N");
										tblMarcCons.setValue("datacons", resultSet.getDate("datacons"));
										tblMarcCons.setValue("diasema", resultSet.getString("diasema"));
										tblMarcCons.setValue("horacons", resultSet.getString("horacons"));
										tblMarcCons.setValue("codimedi", resultSet.getInt("codimedi"));
										tblMarcCons.setValue("numeproc", 0);
										tblMarcCons.setValue("vlrtotal", resultSet.getDouble("vlrvenda"));
										tblMarcCons.setValue("vlrcustototal", resultSet.getDouble("vlrcusto"));
										tblMarcCons.setValue("uidcarrcomp", tblCarrComp.getString("uid"));
										tblMarcCons.setValue("uidfornecedor", tblFornecedores.getString("uid"));
										tblMarcCons.execute();
										
										//tblCarrComp.getDatabase().executeCommand("update carrcons set numemarccons=" + tblMarcCons.getLastInsertId() + "  where (carrcons.uid='" + resultSet.getString("uid") + "')");
										
										String url = "http://" + tblParametros.getString("reportServer") + "/php-app/fichaconsulta.php?recordId="+tblMarcCons.getLastInsertId();
										UI.getCurrent().getPage().open(url, "_blank", false);
										
										Integer sequproc = tblMarcCons.getLastInsertId();
										tblMarcCons.select("*");
										tblMarcCons.setFilter("sequencia", sequproc);
										tblMarcCons.loadData();
										
										String descricao = tblMarcCons.getString("desccodiespe");
										
										String referente = "";
										if (tblMarcCons.getString("tipoatend").equalsIgnoreCase("C")) {
											referente = "CONSULTA ";
										}
										else if (tblMarcCons.getString("tipoatend").equalsIgnoreCase("R")) {
											referente = "RETORNO ";
										}
										else if (tblMarcCons.getString("tipoatend").equalsIgnoreCase("E")) {
											referente = "EXAME ";
										}
										
										referente += tblMarcCons.getString("desccodiespe");
										
										if (tblMarcCons.getDouble("vlrtotal")>0) {
											tblRecibos.insert();
											tblRecibos.setValue("tiporeci", "R");
											tblRecibos.setValue("emissao", Utils.getJustDate(new Date()));
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
											tblRecibos.setValue("codiforn", tblFornecedores.getInteger("codiforn"));
											tblRecibos.setValue("codicaix", vcodicaix);
											tblRecibos.setValue("referente", referente);
											tblRecibos.setValue("sequproc", sequproc);
											tblRecibos.setValue("tipoatend", tblMarcCons.getString("tipoatend"));
											//tblRecibos.setValue("obsreci", value);
											tblRecibos.setValue("vlrcusto", tblMarcCons.getDouble("vlrcustototal"));
											tblRecibos.setValue("valor", tblMarcCons.getDouble("vlrtotal"));
											tblRecibos.setValue("sequcaix", getSequenciaCaixa(vcodicaix));
											tblRecibos.setValue("uidcarrcomp", tblCarrComp.getString("uid"));
											tblRecibos.execute();
										}

										String comando2 = "update carrcons set numemarccons=" + tblMarcCons.getLastInsertId() + ", numereci=" + tblRecibos.getLastInsertId();
										comando2 += "  where (carrcons.uid='" + resultSet.getString("uid") + "')";
										tblCarrComp.getDatabase().executeCommand(comando2);
										
										url = "http://" + tblParametros.getString("reportServer") + "/php-app/relarecibo2.php?recordId="+tblRecibos.getLastInsertId();
										UI.getCurrent().getPage().open(url, "_blank", false);
									}
									
									String uidFornecedor = "";
									int codiforn = 0;
									String tipoproc = "";
									String grupo = "";
									String referente = "";
									int numeitem = 1;
									double vlrtotalcusto = 0;
									double vlrtotalvenda = 0;
									boolean emitindo = false;
									
									// Pega a relacao de consultas que estao agendadas
									comando = "select carrexamlabo.uid, carrexamlabo.uidfornecedor, especialidades.tipoproc, espeforn.grupo, fornecedores.codiforn, fornecedores.codimedi, carrexamlabo.codiespe, carrexamlabo.vlrcusto, carrexamlabo.vlrvenda, carrexamlabo.datacons, carrexamlabo.horacons, carrexamlabo.diasema, especialidades.descricao as desccodiespe  ";
									comando += " from carrexamlabo ";
									comando += " left join especialidades on (especialidades.codiespe=carrexamlabo.codiespe) ";
									comando += " left join espeforn on (espeforn.uidfornecedor=carrexamlabo.uidfornecedor) and (espeforn.codiespe=carrexamlabo.codiespe) ";
									comando += " left join fornecedores on (fornecedores.uid=carrexamlabo.uidfornecedor) ";
									comando += " where (carrexamlabo.uidcarrcomp='" + tblCarrComp.getString("uid") + "')";
									comando += " order by uidfornecedor, tipoproc, grupo";
									resultSet = tblCarrComp.getDatabase().executeSelect(comando);
									while (resultSet.next()) {
										if ((!uidFornecedor.equals(resultSet.getString("uidfornecedor"))) || (!tipoproc.equals(resultSet.getString("tipoproc"))) || (!grupo.equals(resultSet.getString("grupo")))) {
											// Caso exista um recibo em aberto, isso é identificado pelo uidFornecedor preenchido, finaliza o recibo que esta aberto.
											if (uidFornecedor.length()>0) {
												System.out.println("finalizou o recibo que estava sendo emitido");
												tblMarcCons.setValue("vlrcustototal", vlrtotalcusto);
												tblMarcCons.setValue("vlrtotal", vlrtotalvenda);
												tblMarcCons.execute();
												
												String url = "http://" + tblParametros.getString("reportServer") + "/php-app/fichaconsulta.php?recordId="+tblMarcCons.getLastInsertId();
												UI.getCurrent().getPage().open(url, "_blank", false);

												if (vlrtotalvenda>0) {
													tblRecibos.insert();
													tblRecibos.setValue("tiporeci", "R");
													tblRecibos.setValue("emissao", Utils.getJustDate(new Date()));
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
													tblRecibos.setValue("codicaix", vcodicaix);
													tblRecibos.setValue("referente", referente);
													tblRecibos.setValue("sequproc", tblMarcCons.getLastInsertId());
													tblRecibos.setValue("tipoatend", "E");
													//tblRecibos.setValue("obsreci", value);
													tblRecibos.setValue("vlrcusto", vlrtotalcusto);
													tblRecibos.setValue("valor", vlrtotalvenda);
													tblRecibos.setValue("sequcaix", getSequenciaCaixa(vcodicaix));
													tblRecibos.setValue("uidcarrcomp", tblCarrComp.getString("uid"));
													tblRecibos.execute();
												}
												
												url = "http://" + tblParametros.getString("reportServer") + "/php-app/relarecibo2.php?recordId="+tblRecibos.getLastInsertId();
												UI.getCurrent().getPage().open(url, "_blank", false);
											}

											System.out.println("------------------------------------------");
											System.out.println("iniciando um novo recibo");
											
											uidFornecedor = resultSet.getString("uidfornecedor");
											codiforn = resultSet.getInt("codiforn");
											tipoproc = resultSet.getString("tipoproc");
											grupo = resultSet.getString("grupo");
											referente = "";
											numeitem = 1;
											vlrtotalcusto = 0;
											vlrtotalvenda = 0;
											emitindo = true;
											
											Table tblEspeMedi = tblCarrComp.getDatabase().loadTableByName("espemedi");
											tblEspeMedi.select("codiespe");
											tblEspeMedi.setFilter("codimedi", resultSet.getString("codimedi"));
											tblEspeMedi.loadData();
											
											// Cria a guia de atendimento
											tblMarcCons.insert();
											tblMarcCons.setValue("codiasso", tblCarrComp.getInteger("codiasso"));
											tblMarcCons.setValue("nome", tblCarrComp.getString("nome"));
											tblMarcCons.setValue("paciente", tblCarrComp.getString("paciente"));
											tblMarcCons.setValue("nasc", tblCarrComp.getDate("nasc"));
											tblMarcCons.setValue("cortesia", "N");
											
											//tblMarcCons.setValue("codiespe", resultSet.getInt("codiespe"));
											//tblMarcCons.setValue("codiespe_valor", resultSet.getDouble("vlrvenda"));
											//tblMarcCons.setValue("codiespe_vlrcusto", resultSet.getDouble("vlrcusto"));

											tblMarcCons.setValue("codiespe", tblEspeMedi.getString("codiespe"));
											tblMarcCons.setValue("codiespe_vlrcusto", 0);
											tblMarcCons.setValue("codiespe_valor", 0);
											
											tblMarcCons.setValue("codimedi", resultSet.getInt("codimedi"));
											
											//tblMarcCons.setValue("ulticons", resultSet.getDate("ulticons"));
											//tblMarcCons.setValue("ulticonsdias", resultSet.getInt("ulticonsdias"));
											tblMarcCons.setValue("tipoatend", "E");
											tblMarcCons.setValue("atendido", "N");
											tblMarcCons.setValue("datacons", resultSet.getDate("datacons"));
											tblMarcCons.setValue("diasema", resultSet.getString("diasema"));
											tblMarcCons.setValue("horacons", resultSet.getString("horacons"));
											tblMarcCons.setValue("numeproc", 0);
											
											//tblMarcCons.setValue("vlrtotal", resultSet.getDouble("vlrvenda"));
											//tblMarcCons.setValue("vlrcustototal", resultSet.getDouble("vlrcusto"));
											
											tblMarcCons.setValue("uidcarrcomp", tblCarrComp.getString("uid"));
										}

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
										
										String comando2 = "update carrexamlabo set numemarccons=" + tblMarcCons.getLastInsertId() + ", numereci=" + tblRecibos.getLastInsertId();
										comando2 += "  where (carrexamlabo.uid='" + resultSet.getString("uid") + "')";
										tblCarrComp.getDatabase().executeCommand(comando2);
										
										System.out.println(resultSet.getString("uid") + " " + resultSet.getString("uidfornecedor") + " " + resultSet.getString("tipoproc") + " " + resultSet.getString("grupo") + resultSet.getString("codimedi"));
									}
									
									if (emitindo) {
										System.out.println("finalizou o recibo que estava sendo emitido");
										tblMarcCons.setValue("vlrcustototal", vlrtotalcusto);
										tblMarcCons.setValue("vlrtotal", vlrtotalvenda);
										tblMarcCons.execute();
										
										String url = "http://" + tblParametros.getString("reportServer") + "/php-app/fichaconsulta.php?recordId="+tblMarcCons.getLastInsertId();
										UI.getCurrent().getPage().open(url, "_blank", false);

										if (vlrtotalvenda>0) {
											tblRecibos.insert();
											tblRecibos.setValue("tiporeci", "R");
											tblRecibos.setValue("emissao", Utils.getJustDate(new Date()));
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
											tblRecibos.setValue("codicaix", vcodicaix);
											tblRecibos.setValue("referente", referente);
											tblRecibos.setValue("sequproc", tblMarcCons.getLastInsertId());
											tblRecibos.setValue("tipoatend", "E");
											//tblRecibos.setValue("obsreci", value);
											tblRecibos.setValue("vlrcusto", vlrtotalcusto);
											tblRecibos.setValue("valor", vlrtotalvenda);
											tblRecibos.setValue("sequcaix", getSequenciaCaixa(vcodicaix));
											tblRecibos.setValue("uidcarrcomp", tblCarrComp.getString("uid"));
											tblRecibos.execute();
										}
										
										url = "http://" + tblParametros.getString("reportServer") + "/php-app/relarecibo2.php?recordId="+tblRecibos.getLastInsertId();
										UI.getCurrent().getPage().open(url, "_blank", false);
									}
									
									Utils.ShowMessageWindow("Atenção!", "Processamento concluido com SUCESSO!", 500, 180, MessageWindowType.INFO);
								}
								else {
									Table tblParametros = tblCarrComp.getDatabase().loadTableByName("parametros");
									tblParametros.select("*");
									tblParametros.loadData();
									
									String comando = "select * from marccons";
									comando += " where (marccons.uidcarrcomp='" + tblCarrComp.getString("uid") + "')";
									ResultSet resultSet = tblCarrComp.getDatabase().executeSelect(comando);
									while (resultSet.next()) {
										String url = "http://" + tblParametros.getString("reportServer") + "/php-app/fichaconsulta.php?recordId="+resultSet.getInt("sequencia");
										UI.getCurrent().getPage().open(url, "_blank", false);
									}
									
									comando = "select * from recibos";
									comando += " where (recibos.uidcarrcomp='" + tblCarrComp.getString("uid") + "')";
									resultSet = tblCarrComp.getDatabase().executeSelect(comando);
									while (resultSet.next()) {
										String url = "http://" + tblParametros.getString("reportServer") + "/php-app/relarecibo2.php?recordId="+resultSet.getInt("sequencia");
										UI.getCurrent().getPage().open(url, "_blank", false);
									}
									
									Utils.ShowMessageWindow("Atenção!", "Processamento concluido com SUCESSO!", 500, 180, MessageWindowType.INFO);
								}
							}
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
						finally {
							tblCarrComp.getDatabase().closeConnection();
						}
					}
				});
				
				/**/
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por associados:");
				controlForm.setWidth(600d);
				controlForm.setHeight(280d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa por associados:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 90d);
					controlForm.addField("data", "Data", 110d);
					controlForm.addField("codiasso", "Cód.Associado", 130d);
					controlForm.addField("nome", "Nome", 250d, 1);

					controlForm.addNewLine();
					controlForm.addField("paciente", "Paciente", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 120d);
					controlForm.addField("processado", "Processado", 120d);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("carrcomp");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("carrcomp");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Carrinho de compras", true);
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
}
