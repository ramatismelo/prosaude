package com.example.myapplication;

import java.util.Date;
import java.util.UUID;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.WarningMessageType;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlButton;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.enumerators.ButtonType;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent;
import com.evolucao.rmlibrary.ui.controller.events.ControlButtonClickEvent.ControlButtonClickEventListener;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.tables.Login;
import com.evolucao.weblibrary.tables.Menu;
import com.evolucao.weblibrary.tables.Permissoes;
import com.evolucao.weblibrary.tables.SysGroups;
import com.evolucao.weblibrary.tables.SysOptions;
import com.evolucao.weblibrary.tables.SysPermissions;
import com.evolucao.weblibrary.tables.SysUsers;
import com.example.myapplication.reports.ProdutividadeRequisitantes.RelaProdutividadeRequisitantes;
import com.example.myapplication.reports.tables.RelaAssoPref;
import com.example.myapplication.reports.tables.RelaAssociados;
import com.example.myapplication.reports.tables.RelaAtendimentoPeriodo;
import com.example.myapplication.reports.tables.RelaCentCust;
import com.example.myapplication.reports.tables.RelaDensBair;
import com.example.myapplication.reports.tables.RelaDensMuni;
import com.example.myapplication.reports.tables.RelaEstaConsEspe;
import com.example.myapplication.reports.tables.RelaEstatisticaVendas;
import com.example.myapplication.reports.tables.RelaEstatisticaVendasPrefixo;
import com.example.myapplication.reports.tables.RelaEtiqCasa;
import com.example.myapplication.reports.tables.RelaEtiqIndi;
import com.example.myapplication.reports.tables.RelaFechCaix;
import com.example.myapplication.reports.tables.RelaFechCaix2;
import com.example.myapplication.reports.tables.RelaInscPeri;
import com.example.myapplication.reports.tables.RelaModiPeri;
import com.example.myapplication.tables.Agenda;
import com.example.myapplication.tables.Agenda2;
import com.example.myapplication.tables.AgendaDias;
import com.example.myapplication.tables.AgendaMedicos;
import com.example.myapplication.tables.AlteAgenMedi;
import com.example.myapplication.tables.Associados;
import com.example.myapplication.tables.Associados2;
import com.example.myapplication.tables.CaixUsua;
import com.example.myapplication.tables.Caixas;
import com.example.myapplication.tables.CarrComp;
import com.example.myapplication.tables.CarrComp2;
import com.example.myapplication.tables.CarrCons;
import com.example.myapplication.tables.CarrConsExam;
import com.example.myapplication.tables.CarrExamLabo;
import com.example.myapplication.tables.CentrosCusto;
import com.example.myapplication.tables.Cep;
import com.example.myapplication.tables.DevoReci;
import com.example.myapplication.tables.EspeForn2;
import com.example.myapplication.tables.EspeMedi;
import com.example.myapplication.tables.Especialidades;
import com.example.myapplication.tables.FinalizarVenda;
import com.example.myapplication.tables.Fornecedores;
import com.example.myapplication.tables.MarcCons;
import com.example.myapplication.tables.Medicos;
import com.example.myapplication.tables.Parametros;
import com.example.myapplication.tables.Prefixo;
import com.example.myapplication.tables.Recibos;
import com.example.myapplication.tables.Requisitantes;
import com.example.myapplication.tables.SaidCaix;
import com.example.myapplication.tables.SequCaixC1;
import com.example.myapplication.tables.SequCaixC2;
import com.example.myapplication.tables.SequCaixC3;
import com.example.myapplication.tables.Telefones;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

public class DatabaseApp {
	public Button btnCarrinhoCompras = null;
	//Database database = null;
	//public static DatabaseApp Instance = null;
	
	/*
	public void setDatabase(Database database) {
		this.database = database;
	}
	public Database database {
		return this.database;
	}
	*/
	
	public DatabaseApp(Database database) {
		//this.setDatabase(database);
		//this.Instance = this;
		
		TableRegistry tableRegistry = null;
		RmGridRegistry rmGridRegistry = null;
		CommandRegistry commandRegistry = null;

		Associados.configure(database);
		Associados2.configure(database);
		Prefixo.configure(database);
		Cep.configure(database);
		Medicos.configure(database);
		MarcCons.configure(database);
		Caixas.configure(database);
		CaixUsua.configure(database);
		DevoReci.configure(database);
		SaidCaix.configure(database);
		Recibos.configure(database);
		EspeMedi.configure(database);
		Fornecedores.configure(database);
		EspeForn2.configure(database);
		Especialidades.configure(database);
		Agenda.configure(database);
		Agenda2.configure(database);
		Menu.configure(database);
		Permissoes.configure(database);
		CentrosCusto.configure(database);
		AlteAgenMedi.configure(database);
		Requisitantes.configure(database);
		Telefones.configure(database);
		
		SequCaixC1.configure(database);
		SequCaixC2.configure(database);
		SequCaixC3.configure(database);
		
		CarrComp.configure(database);
		CarrCons.configure(database);
		CarrExamLabo.configure(database);
		
		CarrComp2.configure(database);
		CarrConsExam.configure(database);
		
		Parametros.configure(database);
		
		/**
		 * Relatorios
		 */
		RelaFechCaix.configure(database);
		RelaFechCaix2.configure(database);
		RelaAssociados.configure(database);
		RelaAtendimentoPeriodo.configure(database);
		RelaEstaConsEspe.configure(database);
		RelaInscPeri.configure(database);
		RelaModiPeri.configure(database);
		RelaAssoPref.configure(database);
		RelaDensBair.configure(database);
		RelaDensMuni.configure(database);
		RelaEtiqCasa.configure(database);
		RelaEtiqIndi.configure(database);
		RelaEstatisticaVendas.configure(database);
		RelaEstatisticaVendasPrefixo.configure(database);
		RelaCentCust.configure(database);
		RelaProdutividadeRequisitantes.configure(database);
		
		FinalizarVenda.configure(database);
		
		AgendaMedicos.configure(database);
		AgendaDias.configure(database);
		
		/*
		 * Tabelas do sistema
		 */
		SysGroups.configure(database);
		SysUsers.configure(database);
		Login.configure(database);
		SysOptions.configure(database);
		SysPermissions.configure(database);
		
	    /*
	     * 
	     */
		/*
		tableRegistry = database.addTableRegistry("systemParameters");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table systemParameters = event.getTable();
				systemParameters.setTableName("systemParameters");
				systemParameters.addField("requestpassword", FieldType.VARCHAR, 10);
				systemParameters.addField("logofile", FieldType.VARCHAR, 250);
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("S", "Sim");
				internalSearch.addItem("N", "Não");
				
				systemParameters.fieldByName("requestpassword").setInternalSearch(internalSearch);
			}
		});
		
		rmGridRegistry = database.addRmGridRegistry("systemParameters");
		rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				RmGrid rmGrdSystemParameters = event.getRmGrid();
				rmGrdSystemParameters.setTable(database.loadTableByName("systemparameters"));
			}
		});
		*/
		
		/**/
		
		/*
		tableRegistry = database.addTableRegistry("resumovendas");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblResumoVendas = event.getTable();
				tblResumoVendas.setTableName("resumovendas");
				tblResumoVendas.addField("sequencia", FieldType.VARCHAR, 3);
				tblResumoVendas.addField("descricao", FieldType.VARCHAR, 50);
				tblResumoVendas.addField("quantidade", FieldType.INTEGER, 10);
				tblResumoVendas.addField("valor", FieldType.DOUBLE, 10);
				tblResumoVendas.setPrimaryKey("sequencia");
			}
		});
		*/

		
		/**/

		/*
		tableRegistry = database.addTableRegistry("grupusua");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("grupusua");
				tblTabela.addField("idgrupo", FieldType.INTEGER, 10);
				tblTabela.addField("descricao", FieldType.VARCHAR, 50);
				tblTabela.addField("padrao", FieldType.BOOLEAN, 10);
				tblTabela.setPrimaryKey("idgrupo");
				tblTabela.setAutoIncrement("idgrupo", true);
			}
		});
		*/
		
		tableRegistry = database.addTableRegistry("controle1");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("controle1");
				tblTabela.addField("uid", FieldType.VARCHAR, 50);
				tblTabela.addField("codiforn", FieldType.INTEGER, 10);
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("descricao", FieldType.VARCHAR, 250);
				tblTabela.addField("data", FieldType.DATETIME, 10);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvenda", FieldType.DOUBLE, 10);
				tblTabela.addField("lastModify", FieldType.DATETIME, 10);
				tblTabela.addField("lastVlrCusto", FieldType.DOUBLE, 10);
				tblTabela.addField("lastVlrVenda", FieldType.DOUBLE, 10);
				tblTabela.addField("numeroAlteracoes", FieldType.INTEGER, 10);
				tblTabela.addField("numeultireci", FieldType.INTEGER, 10);
				
				tblTabela.setPrimaryKey("uid");
			}
		});
		
		tableRegistry = database.addTableRegistry("controle2");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("controle2");
				tblTabela.addField("uid", FieldType.VARCHAR, 50);
				tblTabela.addField("uidcontrole1", FieldType.VARCHAR, 50);
				tblTabela.addField("codiforn", FieldType.INTEGER, 10);
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("data", FieldType.DATETIME, 10);
				tblTabela.addField("alterado", FieldType.VARCHAR, 1);
				tblTabela.addField("quantidade", FieldType.INTEGER, 10);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvenda", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrcustototal", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvendatotal", FieldType.DOUBLE, 10);
				tblTabela.addField("numeultireci", FieldType.INTEGER, 10);
				tblTabela.setPrimaryKey("uid");
			}
		});
		
		tableRegistry = database.addTableRegistry("controle3");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("controle3");
				tblTabela.addField("uid", FieldType.VARCHAR, 50);
				tblTabela.addField("uidcontrole1", FieldType.VARCHAR, 50);
				tblTabela.addField("numereci", FieldType.INTEGER, 10);
				tblTabela.addField("codiforn", FieldType.INTEGER, 10);
				tblTabela.addField("codiespe", FieldType.INTEGER, 10);
				tblTabela.addField("data", FieldType.DATETIME, 10);
				tblTabela.addField("alterado", FieldType.VARCHAR, 1);
				tblTabela.addField("vlrcusto", FieldType.DOUBLE, 10);
				tblTabela.addField("vlrvenda", FieldType.DOUBLE, 10);
				tblTabela.setPrimaryKey("uid");
			}
		});
		
		
		//***************************************************************
		
		tableRegistry = database.addTableRegistry("menu");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("menu");
				tblTabela.addField("idmenu", FieldType.INTEGER, 10);
				tblTabela.addField("texto", FieldType.VARCHAR, 50);
				tblTabela.addField("ordem", FieldType.VARCHAR, 50);
				tblTabela.addField("iconcls", FieldType.VARCHAR, 50);
				tblTabela.addField("parent_idmenu", FieldType.INTEGER, 50);
				tblTabela.addField("classname", FieldType.VARCHAR, 50);
				tblTabela.addField("showform", FieldType.VARCHAR, 1);
				
				tblTabela.setPrimaryKey("idmenu");
				tblTabela.setAutoIncrement("idmenu", true);
			}
		});
		
		tableRegistry = database.addTableRegistry("permissoes");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("permissoes");
				tblTabela.addField("idmenu", FieldType.INTEGER, 10);
				tblTabela.addField("idgrupo", FieldType.INTEGER, 10);
				tblTabela.setPrimaryKey("uid");
				
				tblTabela.addIndex("idmenu_idgrupo", "idmenu, idgrupo");
				tblTabela.addIndex("idgrupo_idmenu", "idgrupo_idmenu");
			}
		});

		/*
		tableRegistry = database.addTableRegistry("usuarios");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("usuarios");
				tblTabela.addField("uid", FieldType.VARCHAR, 50);
				tblTabela.addField("login", FieldType.VARCHAR, 50);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("senha", FieldType.VARCHAR, 50);
				tblTabela.addField("uidTipoUsuario", FieldType.VARCHAR, 50);
				tblTabela.addField("ultiaces", FieldType.DATETIME, 10);
				tblTabela.addField("ipultiaces", FieldType.VARCHAR, 11);
				tblTabela.addField("passport", FieldType.VARCHAR, 50);
				tblTabela.addField("idgrupo", FieldType.INTEGER, 10);
				
				tblTabela.setPrimaryKey("login");
				
				tblTabela.setAllowLike("login", AllowLike.NONE);
				tblTabela.setAllowLike("senha", AllowLike.NONE);
			}
		});
		*/
		
		
		/**/
		
		/*
		tableRegistry = database.addTableRegistry("associados2");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblUsuarios = event.getTable();
				tblUsuarios.setTableName("usuarios");
				tblUsuarios.addField("uid", FieldType.VARCHAR, 50);
				tblUsuarios.addField("nome", FieldType.VARCHAR, 50);
				tblUsuarios.addField("email", FieldType.VARCHAR, 250);
				tblUsuarios.addField("cep", FieldType.VARCHAR, 10);
				tblUsuarios.addField("endereco", FieldType.VARCHAR, 50);
				tblUsuarios.addField("numero", FieldType.INTEGER, 10);
				tblUsuarios.addField("complemento", FieldType.VARCHAR, 50);
				tblUsuarios.addField("bairro", FieldType.VARCHAR, 50);
				tblUsuarios.addField("cidade", FieldType.VARCHAR, 50);
				tblUsuarios.addField("estado", FieldType.VARCHAR, 2);
				tblUsuarios.addField("telefone", FieldType.VARCHAR, 50);
				tblUsuarios.addField("celular", FieldType.VARCHAR, 50);
				tblUsuarios.addField("tipodocu", FieldType.VARCHAR, 1);
				tblUsuarios.addField("documento", FieldType.VARCHAR, 25);
				tblUsuarios.addField("cpf", FieldType.VARCHAR, 20);
				tblUsuarios.addField("senha", FieldType.VARCHAR, 50);
				tblUsuarios.addField("confsenh", FieldType.VARCHAR, 50);
				tblUsuarios.addField("tipousua", FieldType.VARCHAR, 50);
				tblUsuarios.addField("numepedi", FieldType.INTEGER, 10); // Numero de pedidos ja feitos pelo usuario
				tblUsuarios.addField("gastmedi", FieldType.DOUBLE, 10); // Gasto médio 
				tblUsuarios.addField("gasttota", FieldType.DOUBLE, 10); // Gasto total
				tblUsuarios.addField("doubleteste", FieldType.DOUBLE, 10);
				tblUsuarios.addField("floatteste", FieldType.FLOAT, 10);
				tblUsuarios.addField("dateteste", FieldType.DATETIME, 10);
				tblUsuarios.addField("lastlogin", FieldType.DATETIME, 10);
				tblUsuarios.addField("loginid", FieldType.VARCHAR, 50);
				
				tblUsuarios.setPrimaryKey("uid");
				tblUsuarios.setOrder("nome");
				
				tblUsuarios.addIndex("nome", "nome");
				tblUsuarios.addIndex("tipousua", "tipousua");

				tblUsuarios.setRequired("nome", true);
				tblUsuarios.setRequired("email", true);
				tblUsuarios.setRequired("cep", true);
				tblUsuarios.setRequired("endereco", true);
				//tblUsuarios.setRequired("numero", true);
				tblUsuarios.setRequired("bairro", true);
				tblUsuarios.setRequired("cidade", true);
				tblUsuarios.setRequired("estado", true);
				tblUsuarios.setRequired("cpf", true);
				tblUsuarios.setRequired("senha", true);
				tblUsuarios.setRequired("confsenh", true);
				
				tblUsuarios.setMask("cep", "99.999-999");
				tblUsuarios.setMask("numero", "#,##0");
				tblUsuarios.setMask("cpf", "999.999.999-99");
				
				tblUsuarios.setPassword("senha", true);
				tblUsuarios.setPassword("confsenh", true);
				
				InternalSearch selectEstados = new InternalSearch();
				selectEstados.fillEstados();
				selectEstados.setSelectedItem("AM");
				tblUsuarios.setInternalSearch("estado", selectEstados);
				
				tblUsuarios.setAllowLike("senha", AllowLike.NONE);
				tblUsuarios.fieldByName("senha").setCaseSensitive(true);
				
				tblUsuarios.fieldByName("email").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						try {
							database.openConnection();
							if (event.getTable().getTableStatus()==TableStatus.INSERT) {
								//Table tblUsuarios2 = Zapdoctor2UI.Instance.database.loadUsuarios();
								Table tblUsuarios2 = database.loadTableByName("usuarios");
								tblUsuarios2.select("nome");
								tblUsuarios2.setFilter("email", event.getField().getString());
								tblUsuarios2.loadData();
								if (!tblUsuarios2.eof()) {
									event.setValid(false);
									event.setValidationAdvice("Usuário já cadastrado com esse Email!");
								}
							}
							else if (event.getTable().getTableStatus()==TableStatus.UPDATE) {
								Table tblUsuarios2 = database.loadTableByName("usuarios");
								tblUsuarios2.select("nome");
								tblUsuarios2.setFilter("email", event.getField().getString());
								tblUsuarios2.setWhere("(uid<>'" + event.getTable().getString("uid")+ "')");
								tblUsuarios2.loadData();
								if (!tblUsuarios2.eof()) {
									event.setValid(false);
									event.setValidationAdvice("Usuário já cadastrado com esse Email!");
								}
							}
						}
						finally {
							database.closeConnection();
						}
					}
				});
				
				tblUsuarios.fieldByName("senha").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						if (event.getField().getString().length()<3) {
							event.setValid(false);
							event.setValidationAdvice("Necessário informar uma senha com no mínimo 3 caracteres.");
						}
					}
				});
				
				tblUsuarios.fieldByName("confsenh").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						String senha = event.getTable().fieldByName("senha").getString();
						String confsenh = event.getTable().fieldByName("confsenh").getString();
						if (!event.getTable().fieldByName("senha").getString().contentEquals(event.getTable().fieldByName("confsenh").getString())) {
							event.setValid(false);
							event.setValidationAdvice("Senha e confirmação da senha não conferem!");
						}
					}
				});
				
				tblUsuarios.fieldByName("cpf").addValidateFieldEventListener(new ValidateFieldEventListener() {
					@Override
					public void onValidate(ValidateFieldEvent event) {
						if (!Utils.isValidCPF(event.getField().getString())) {
							event.setValid(false);
							event.setValidationAdvice("CPF Inválido!");
						}
					}
				});
				
				ExistenceCheck existenceCheck = tblUsuarios.fieldByName("cep").addExistenceCheck("cep", "cep", "cep", "CEP informado é inválido!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("endereco", event.getTargetTable().getString("logradouro"));
						event.getSourceTable().setValue("bairro", event.getTargetTable().getString("bairro"));
						event.getSourceTable().setValue("cidade", event.getTargetTable().getString("cidade"));
						event.getSourceTable().setValue("estado", event.getTargetTable().getString("estado"));
					}
				});

				//ForeingSearch foreingSearch = tblUsuarios.fieldByName("cep").addForeingSearch("cep", "cep", "cep", "cep");
				ForeingSearch foreingSearch = tblUsuarios.fieldByName("cep").addForeingSearch();
				foreingSearch.setTargetRmGridName("loadGridCep");
				foreingSearch.setTargetIndexName("cep");
				foreingSearch.setRelationship("cep");
				foreingSearch.setReturnFieldName("cep");
				foreingSearch.setTitle("Pesquisa por CEP:");
				foreingSearch.setOrder("estado, cidade, bairro, logradouro, tipo, cep");
				foreingSearch.setAutoOpenFilterForm(true);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				foreingSearch.addBeforeForeingSearchEventListener(new BeforeForeingSearchEventListener() {
					@Override
					public void onBeforeForeingSearch(BeforeForeingSearchEvent event) {
						event.getTargetTable().setValue("cidade", "Manaus");
					}
				});
			}
		});
		*/
		
		rmGridRegistry = database.addRmGridRegistry("usuarios2");
		rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				RmGrid grdUsuarios = event.getRmGrid();
				grdUsuarios.setTable(database.loadTableByName("usuarios"));
				grdUsuarios.setHeight("513px");
				//grdUsuarios.setLimit(10);
				grdUsuarios.addField("nome", "Nome", 200d, 1);
				grdUsuarios.addField("email", "Email", 200d);
				grdUsuarios.addField("numero", "Numero", 100d);
				grdUsuarios.addField("doubleteste", "Double Teste", 200d);
				grdUsuarios.addField("floatteste", "Float Teste", 200d);
				grdUsuarios.addField("dateteste", "Data Teste", 130d);

				ControlForm controlForm = grdUsuarios.getForm();
				controlForm.setTitle("Manutenção de usuários:");
				controlForm.setWidth(900d);
				controlForm.setHeight(600d);
				
				//grdUsuarios.getForm().setWidth(900d);
				//grdUsuarios.getForm().setHeight(600d);
				//grdUsuarios.getForm().setTitle("Manutenção de usuários:");
				
				grdUsuarios.getForm().addNewLine();
				grdUsuarios.getForm().addSection("cadastro", "Dados do usuario.", SectionState.MAXIMIZED);
				{
					grdUsuarios.getForm().addNewLine();
					grdUsuarios.getForm().addField("nome", "Nome", 200d);
					grdUsuarios.getForm().addField("email", "Email", 200d);
					
					grdUsuarios.getForm().addNewLine();
					grdUsuarios.getForm().addField("cep", "Cep", 200d);
					/*
					ControlButton controlButton = grdUsuarios.getForm().addButton("pesquisa");
					controlButton.addControlButtonClickEventListener(new ControlButtonClickEventListener() {
						@Override
						public void onControlButtonClick(ControlButtonClickEvent event) {
							ControlButton controlButton = (ControlButton) event.getSource();
							ControlForm form = (ControlForm) controlButton.getParent(ControlForm.class);
							form.getTable().fieldByName("cep").executeForeingSearch();
							//event.getRmForm().getTable().fieldByName("cep").executeForeingSearch();
							System.out.println("executou foreingSearch");
						}
					});
					*/
					grdUsuarios.getForm().addField("endereco", "Endereço", 300d, 1);
					grdUsuarios.getForm().addField("numero", "Numero", 80d);
					grdUsuarios.getForm().addField("complemento", "Complemento", 250d);
					grdUsuarios.getForm().addButton("teste");
					
					grdUsuarios.getForm().addNewLine(true);
					grdUsuarios.getForm().addField("bairro", "Bairro", 200d);
					grdUsuarios.getForm().addField("cidade", "Cidade", 200d);
					grdUsuarios.getForm().addField("estado", "Estado", 200d);
					
					grdUsuarios.getForm().addNewLine(true);
					grdUsuarios.getForm().addField("telefone", "Telefone", 200d);
					grdUsuarios.getForm().addField("celular", "Celular", 200d);
					grdUsuarios.getForm().addField("cpf", "CPF", 200d);
					
					grdUsuarios.getForm().addNewLine(true);
					grdUsuarios.getForm().addField("tipousua", "Tipo de usuário", 200d);
					grdUsuarios.getForm().addField("senha", "Senha", 200d);
					grdUsuarios.getForm().addField("confsenh", "Confirmação da senha", 200d);
					
					grdUsuarios.getForm().addNewLine(true);
					grdUsuarios.getForm().addField("doubleteste", "Double Teste", 200d);
					grdUsuarios.getForm().addField("floatteste", "Float Teste", 200d);
					grdUsuarios.getForm().addField("dateteste", "Date Teste", 200d);
					
					grdUsuarios.getForm().addNewLine();
					grdUsuarios.getForm().addRequiredMessage();
					
					grdUsuarios.getForm().addNewLine();
					grdUsuarios.getForm().addButtonSet();
					
					ControlButton button = grdUsuarios.getForm().addButton("Continuar");
					button.addControlButtonClickEventListener(new ControlButtonClickEventListener() {
						@Override
						public void onControlButtonClick(ControlButtonClickEvent event) {
							UI.getCurrent().getPage().getJavaScript().execute("$('.numero').find('.v-textfield').val('12345')");
							//UI.getCurrent().getPage().getJavaScript().execute("$('.numero').find('.v-textfield').dispatchEvent(new Event('change')");
							
							ControlButton controlButton = (ControlButton) event.getSource();
							ControlForm form = (ControlForm) controlButton.getParent(ControlForm.class);
							System.out.println(form.getTable().fieldByName("nome").getString());
							form.getTable().fieldByName("nome").setValue("RAMATIS SOARES DE MELO");
							System.out.println(form.getTable().fieldByName("nome").getString());
							/*
							ControlSectionContainer section = (ControlSectionContainer) controlButton.getParent(ControlSectionContainer.class);
							if (section.validate()) {
								ControlForm form = (ControlForm) controlButton.getParent(ControlForm.class);
								if (form !=null) {
									if (form.getTable().execute()) {
										form.getWindow().close();
									}
								}
							}
							*/
						}
					});
					
				}
				
				ControlForm formPesquisa = grdUsuarios.getFormFilter();
				formPesquisa.setTitle("Pesquisa por usuários.");
				formPesquisa.setWidth(900d);
				formPesquisa.setHeight(600d);
				
				formPesquisa.addNewLine();
				formPesquisa.addSection("cadastro", "Dados do usuario.", SectionState.MAXIMIZED);
				
				formPesquisa.addNewLine();
				formPesquisa.addField("nome", "Nome", 200d);
				formPesquisa.addField("email", "Email", 200d);
				
				formPesquisa.addNewLine();
				formPesquisa.addField("cep", "Cep", 200d);
				formPesquisa.addField("endereco", "Endereço", 300d, 1);
				formPesquisa.addField("numero", "Numero", 80d);
				formPesquisa.addField("complemento", "Complemento", 250d);
				
				formPesquisa.addNewLine(true);
				formPesquisa.addField("bairro", "Bairro", 200d);
				formPesquisa.addField("cidade", "Cidade", 200d);
				formPesquisa.addField("estado", "Estado", 200d);
				
				formPesquisa.addNewLine(true);
				formPesquisa.addField("telefone", "Telefone", 200d);
				formPesquisa.addField("celular", "Celular", 200d);
				
				formPesquisa.addNewLine(true);
				formPesquisa.addField("cpf", "CPF", 200d);
				formPesquisa.addField("senha", "Senha", 200d);
				formPesquisa.addField("confsenh", "Confirmação da senha", 200d);
			}
		});
		
		/**/
		/*
		// Nesta tabela deve ser armazenado a programacao de atendimentos para o medico
		tableRegistry = database.addTableRegistry("programacaoagenda");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblProgramacaoAgenda = event.getTable();
				tblProgramacaoAgenda.setTableName("programacaoagenda");
				tblProgramacaoAgenda.addField("uidproduto", FieldType.VARCHAR, 50);
				tblProgramacaoAgenda.addField("diasemana", FieldType.INTEGER, 10);
				tblProgramacaoAgenda.addField("horainicatend", FieldType.VARCHAR, 5);
				tblProgramacaoAgenda.addField("numeatend", FieldType.INTEGER, 10);
				tblProgramacaoAgenda.addField("tempmedicons", FieldType.INTEGER, 10);
				tblProgramacaoAgenda.addField("tipoagenda", FieldType.INTEGER, 10);
				tblProgramacaoAgenda.setPrimaryKey("uidproduto, diasemana, horainicatend");
			}
		});
		*/
		
		// Deve existir uma rotina que gere registros nessa tabela, os registros devem ser gerados
		// montando a agenda do medico a partir dos dados que existirem na tabela programacaoAgenda
		/*
		tableRegistry = database.addTableRegistry("agenda");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblAgenda = event.getTable();
				tblAgenda.setTableName("agenda");
				tblAgenda.addField("uidproduto", FieldType.VARCHAR, 50);
				tblAgenda.addField("diasemana", FieldType.INTEGER, 10);
				tblAgenda.addField("horainicatend", FieldType.VARCHAR, 5);
				tblAgenda.addField("horario", FieldType.VARCHAR, 5);
				tblAgenda.setPrimaryKey("uidproduto, diasemana, horainicatend, horario");
			}
		});
		*/
		
		// Sempre que um agendamento para um medico for concluido, deve ser gerado um registro 
		// nessa tabela para evitar que outro paciente agende no mesmo horario, ou para 
		// viabilizar a contagem de pacientes que serao atendidos no dia pelo medico
		tableRegistry = database.addTableRegistry("agendamentos");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblAgendamentos = event.getTable();
				tblAgendamentos.setTableName("agendamentos");
				tblAgendamentos.addField("uidproduto", FieldType.VARCHAR, 50);
				tblAgendamentos.addField("dataconsulta", FieldType.DATETIME, 10);
				tblAgendamentos.addField("horainicatend", FieldType.VARCHAR, 5);
				tblAgendamentos.addField("horaconsulta", FieldType.VARCHAR, 5);
				tblAgendamentos.addField("cartcookie", FieldType.VARCHAR, 50);
				tblAgendamentos.setPrimaryKey("uidproduto, dataconsulta, horainicatend, horaconsulta");
			}
		});
		
		// Deve existir uma rotina que gera lancamentos nessa tabela, ela sera utilizada 
		// nas pesquisas de produtos
		tableRegistry = database.addTableRegistry("prodbair");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblProdBair = event.getTable();
				tblProdBair.setTableName("prodbair");
				tblProdBair.addField("uidespe", FieldType.VARCHAR, 50);
				tblProdBair.addField("ufestado", FieldType.VARCHAR, 2);
				tblProdBair.addField("cidade", FieldType.VARCHAR, 50);
				tblProdBair.addField("bairro", FieldType.VARCHAR, 50);
				tblProdBair.setPrimaryKey("uidespe, ufestado, cidade, bairro");
			}
		});

		/**/

		/*
		tableRegistry = database.addTableRegistry("cep");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblCep = event.getTable();
				tblCep.setTableName("cep");
				tblCep.addField("tipo", FieldType.VARCHAR, 50);
				tblCep.addField("logradouro", FieldType.VARCHAR, 250);
				tblCep.addField("logradouro_comacentos", FieldType.VARCHAR, 250);
				tblCep.addField("bairro", FieldType.VARCHAR, 250);
				tblCep.addField("bairro_comacentos", FieldType.VARCHAR, 250);
				tblCep.addField("cidade", FieldType.VARCHAR, 50);
				tblCep.addField("cidade_comacentos", FieldType.VARCHAR, 50);
				tblCep.addField("estado", FieldType.VARCHAR, 2);
				tblCep.addField("cep", FieldType.VARCHAR, 10);
				tblCep.addField("processado", FieldType.VARCHAR, 50);
				tblCep.setPrimaryKey("cep");
				tblCep.addIndex("cep", "cep");
				tblCep.addIndex("logradouro_cidade_estado", "logradouro, cidade, estado");
				
				tblCep.setMask("cep", "##.###-###");
			}
		});
		
	    rmGridRegistry = database.addRmGridRegistry("cep");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				RmGrid grdCep = event.getRmGrid();
				grdCep.setTable(database.loadTableByName("cep"));
				grdCep.setHeight("550px");
				grdCep.setLimit(10);
				grdCep.addField("cep", "CEP", 150D);
				grdCep.addField("tipo", "Tipo", 150d);
				grdCep.addField("logradouro", "Logradouro", 300d);
				grdCep.addField("bairro", "Bairro", 200d);
				grdCep.addField("cidade", "Cidade", 200d);
				grdCep.addField("estado", "Estado", 100d);
				
				ControlForm controlForm = grdCep.getForm();
				controlForm.setTitle("Manutenção de CEP:");
				controlForm.setWidth(600d);
				controlForm.setHeight(450d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Cadastro de CEP.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("estado", "Estado", 100d, 20d);
					controlForm.addField("cidade", "Cidade", 200d, 80d);
					
					controlForm.addNewLine();
					controlForm.addField("tipo",  "Tipo", 100d,  10d);
					controlForm.addField("logradouro", "Logradouro", 200d, 80d);
					
					controlForm.addNewLine();
					controlForm.addField("bairro",  "Bairro", 200d,  80d);
					controlForm.addField("cep", "Cep", 100d, 20d);
				}
				
				controlForm = grdCep.getFormFilter();
				controlForm.setTitle("Pesquisa por CEP:");
				controlForm.setWidth(600d);
				controlForm.setHeight(450d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa por CEP.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("estado", "Estado", 100d, 20d);
					controlForm.addField("cidade", "Cidade", 200d, 80d);
					
					controlForm.addNewLine();
					controlForm.addField("tipo",  "Tipo", 100d,  10d);
					controlForm.addField("logradouro", "Logradouro", 200d, 80d);
					
					controlForm.addNewLine();
					controlForm.addField("bairro",  "Bairro", 200d,  80d);
					controlForm.addField("cep", "Cep", 100d, 20d);
				}
			}
		});
		*/
	    
	    /**/
	    tableRegistry = database.addTableRegistry("cupomdesconto");
	    tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblCupomDesconto = event.getTable();
				tblCupomDesconto.setTableName("cupomdesconto");
				tblCupomDesconto.addField("uid", FieldType.VARCHAR, 50);
				tblCupomDesconto.addField("sequencia", FieldType.INTEGER, 10);
				tblCupomDesconto.addField("localizador", FieldType.VARCHAR, 10);
				tblCupomDesconto.addField("percdesc", FieldType.DOUBLE, 10);
				tblCupomDesconto.addField("vlrdesc", FieldType.DOUBLE, 10);
				tblCupomDesconto.addField("emissao", FieldType.DATETIME, 10);
				tblCupomDesconto.addField("utilizado", FieldType.DATETIME, 10);
				tblCupomDesconto.addField("vencimento", FieldType.DATETIME, 10);
				tblCupomDesconto.addField("emitente", FieldType.VARCHAR, 50);
				tblCupomDesconto.setPrimaryKey("sequencia");
				
				tblCupomDesconto.setAutoIncrement("sequencia", true);
				
				tblCupomDesconto.addIndex("localizador", "localizador");
			}
		});
	    
	    tableRegistry = database.addTableRegistry("carrinhocompras");
	    tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblCarrinho = event.getTable();
				tblCarrinho.setTableName("carrinhocompras");
				tblCarrinho.addField("sequencia", FieldType.INTEGER, 10);
				tblCarrinho.addField("cartcookie", FieldType.VARCHAR, 50);
				tblCarrinho.addField("localizadorcupom", FieldType.VARCHAR, 10); // Localizador do cupom
				
				tblCarrinho.addField("secao2ok", FieldType.BOOLEAN, 10);
				tblCarrinho.addField("tipofech", FieldType.INTEGER, 10); // 0 - Fechar como visitante, 1 - Fechar com cadastro, 2 - Login
				tblCarrinho.addField("nome", FieldType.VARCHAR, 50);
				tblCarrinho.addField("email", FieldType.VARCHAR, 250);
				tblCarrinho.addField("endereco", FieldType.VARCHAR, 50);
				tblCarrinho.addField("numero", FieldType.VARCHAR, 10);
				tblCarrinho.addField("bairro", FieldType.VARCHAR, 50);
			    tblCarrinho.addField("cidade", FieldType.VARCHAR, 50);
			    tblCarrinho.addField("estado", FieldType.VARCHAR, 2);
			    tblCarrinho.addField("cep", FieldType.VARCHAR, 10);
			    tblCarrinho.addField("telefonefixo", FieldType.VARCHAR, 50);
			    tblCarrinho.addField("celular", FieldType.VARCHAR, 50);
			    tblCarrinho.addField("cpf", FieldType.VARCHAR, 25);
				tblCarrinho.addField("senhacadastro", FieldType.VARCHAR, 50);
				tblCarrinho.addField("confirmarsenhacadastro", FieldType.VARCHAR, 50);
				
				tblCarrinho.addField("secao3ok", FieldType.BOOLEAN, 10);
				tblCarrinho.addField("pacienteconsulta", FieldType.VARCHAR, 1);
			    tblCarrinho.addField("nomepaciente", FieldType.VARCHAR, 50);
			    tblCarrinho.addField("nascimento", FieldType.DATETIME, 10);
			    
			    tblCarrinho.addField("secao4ok", FieldType.BOOLEAN, 10);
			    tblCarrinho.addField("formapagamento", FieldType.VARCHAR, 1);
			    tblCarrinho.addField("nomecartao", FieldType.VARCHAR, 50);
			    tblCarrinho.addField("tipocartao", FieldType.VARCHAR, 2);
			    tblCarrinho.addField("numerocartao", FieldType.VARCHAR, 20);
			    tblCarrinho.addField("dataexpiracao", FieldType.VARCHAR, 2);
			    tblCarrinho.addField("anoexpiracao", FieldType.VARCHAR, 4);
			    tblCarrinho.addField("numeroseguranca", FieldType.VARCHAR, 3);
			    
			    tblCarrinho.addField("pedido", FieldType.DATETIME, 10);
			    tblCarrinho.addField("vencimento", FieldType.DATETIME, 10);
			    tblCarrinho.addField("pagamento", FieldType.DATETIME, 10);
			    
			    tblCarrinho.addField("subtotal", FieldType.DOUBLE, 10);
			    tblCarrinho.addField("percdesccupom", FieldType.DOUBLE, 10);
			    tblCarrinho.addField("vlrdesccupom", FieldType.DOUBLE, 10);
			    tblCarrinho.addField("vlrdesconto", FieldType.DOUBLE, 10);
			    tblCarrinho.addField("totalgeral", FieldType.DOUBLE, 10);
				
				tblCarrinho.setPrimaryKey("sequencia");
				tblCarrinho.setAutoIncrement("sequencia", true);
				
				tblCarrinho.addIndex("cartcookie", "cartcookie");
			}
		});
	    
	    tableRegistry = database.addTableRegistry("cartitem");
	    tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table cartItem = event.getTable();
				cartItem.setTableName("cartitem");
				cartItem.addField("cartcookie", FieldType.VARCHAR, 50);
				cartItem.addField("uidproduto", FieldType.VARCHAR, 50);
				cartItem.addField("quantidade", FieldType.INTEGER, 10);
				cartItem.addField("agendamento", FieldType.DATETIME, 10);
				cartItem.setPrimaryKey("cartcookie, uidproduto");
			}
		});
	    
	    /*
	    tableRegistry = database.addTableRegistry("especialidades");
	    tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table especialidades = event.getTable();
				especialidades.setTableName("especialidades");
				especialidades.addField("especialidade", FieldType.VARCHAR, 100);
				especialidades.addField("tipoespe", FieldType.INTEGER, 10);
				especialidades.setPrimaryKey("especialidade");
				especialidades.addIndex("tipoespe_especialidade", "tipoespe, especialidade");
				especialidades.addIndex("tipoespe_uid", "tipoespe, uid");
				
				especialidades.setRequired("especialidade", true);
			}
		});
		*/
	    
	    tableRegistry = database.addTableRegistry("produtos");
	    tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table produtos = event.getTable();
				produtos.setTableName("produtos");
				produtos.addField("nome", FieldType.VARCHAR, 50);
				produtos.addField("nomecurto", FieldType.VARCHAR, 250);
				produtos.addField("sexo", FieldType.VARCHAR, 1);
				produtos.addField("uidespe", FieldType.VARCHAR, 50);
				produtos.addField("tipoespe", FieldType.VARCHAR, 1);
				produtos.addField("especialidade", FieldType.VARCHAR, 50);
				produtos.addField("fotogrande", FieldType.VARCHAR, 250);
				produtos.addField("avaliacao", FieldType.INTEGER, 10);
				
				produtos.addField("nomelocaatend", FieldType.VARCHAR, 50);
				produtos.addField("telefones", FieldType.VARCHAR, 250);
				produtos.addField("qualificacao", FieldType.TEXT, 250);
				produtos.addField("endereco", FieldType.VARCHAR, 50);
				produtos.addField("numero", FieldType.INTEGER, 10);
				produtos.addField("complemento", FieldType.VARCHAR, 50);
				produtos.addField("bairro", FieldType.VARCHAR, 50);
				produtos.addField("cidade", FieldType.VARCHAR, 50);
				produtos.addField("estado", FieldType.VARCHAR, 2);
				produtos.addField("cep", FieldType.VARCHAR, 10);
				produtos.addField("latitude", FieldType.FLOAT, 10);
				produtos.addField("longitude", FieldType.FLOAT, 10);
				
				produtos.addField("diasatend01", FieldType.VARCHAR, 50);
				produtos.addField("horaatend01", FieldType.VARCHAR, 50);

				produtos.addField("diasatend02", FieldType.VARCHAR, 50);
				produtos.addField("horaatend02", FieldType.VARCHAR, 50);
				
				produtos.addField("diasatend03", FieldType.VARCHAR, 50);
				produtos.addField("horaatend03", FieldType.VARCHAR, 50);
				
				produtos.addField("valor", FieldType.DOUBLE, 10);
				produtos.addField("vlrpromocional", FieldType.DOUBLE, 10);
				produtos.addField("datapromo", FieldType.DATETIME, 10);
				produtos.addField("datanovo", FieldType.DATETIME, 10);
				produtos.setPrimaryKey("uid");
				
				produtos.addIndex("uidespe_estado_cidade_bairro","uidespe, estado, cidade, bairro");
			}
		});

	    tableRegistry = database.addTableRegistry("menu");
	    tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblMenu = event.getTable();
				tblMenu.setTableName("menu");
				tblMenu.addField("descricao", FieldType.VARCHAR, 50);
				tblMenu.setPrimaryKey("descricao");
				
				tblMenu.addIndex("descricao", "descricao");
			}
		});
	    
	    tableRegistry = database.addTableRegistry("opcoes");
	    tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblOpcoes = event.getTable();
				tblOpcoes.setTableName("opcoes");
				tblOpcoes.addField("uidmenu", FieldType.VARCHAR, 50);
				tblOpcoes.addField("titulo", FieldType.VARCHAR, 50);
				tblOpcoes.addField("tituloopcoes", FieldType.VARCHAR, 50);
				tblOpcoes.addField("tituloimagem", FieldType.VARCHAR, 250);
				tblOpcoes.addField("urlimagem", FieldType.VARCHAR, 250);
				tblOpcoes.addField("ordem", FieldType.VARCHAR, 10);
				tblOpcoes.addField("opcoesporlinha", FieldType.INTEGER, 10);
				
				tblOpcoes.setPrimaryKey("uid");
				tblOpcoes.addIndex("uidmenu_ordem", "uidmenu, ordem");
			}
		});
	    
	    tableRegistry = database.addTableRegistry("subopcoes");
	    tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblSubOpcoes = event.getTable();
				tblSubOpcoes.setTableName("subopcoes");
				tblSubOpcoes.addField("uidopcao", FieldType.VARCHAR, 50);
				tblSubOpcoes.addField("titulo", FieldType.VARCHAR, 50);
				tblSubOpcoes.addField("ordem", FieldType.VARCHAR, 10);
				tblSubOpcoes.addField("targetviewname", FieldType.VARCHAR, 50); // View que deve ser chamada ao selecionar a opcao
				tblSubOpcoes.addField("commandexecutename", FieldType.VARCHAR, 50); // Comando que deve ser executado ao selecionar a opcao
				tblSubOpcoes.setPrimaryKey("uid");
				
				tblSubOpcoes.addIndex("uidopcao_ordem", "uidopcao, ordem");
			}
		});
	    
	    tableRegistry = database.addTableRegistry("clinicas");
	    tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table clinicas = event.getTable();
				clinicas.setTableName("clinicas");
				clinicas.addField("nome", FieldType.VARCHAR, 50);
				clinicas.addField("nomecurto", FieldType.VARCHAR, 50);
				clinicas.addField("logo", FieldType.VARCHAR, 250);
				clinicas.setPrimaryKey("nomecurto");
			}
		});
	    
	    rmGridRegistry = database.addRmGridRegistry("login2");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				RmGrid rmGrdLogin = event.getRmGrid();
				rmGrdLogin.setTable(database.loadTableByName("login"));
				
			    ControlForm controlForm = rmGrdLogin.getForm();
			    controlForm.setTable(rmGrdLogin.getTable());
			    controlForm.setTitle("Clientes Registrados");
			    
			    controlForm.addNewLine();
			    controlForm.addField("email", "Email", 100d, 100);
			    
			    controlForm.addNewLine();
			    controlForm.addField("senha", "Senha", 100d, 100);

			    controlForm.addNewLine();
			    controlForm.addRequiredMessage();
			    
			    controlForm.addNewLine();
			    controlForm.addButtonSet();
			    
			    ControlButton btnSenha = controlForm.addLeftButton("Esqueceu sua senha?");
			    btnSenha.setButtonType(ButtonType.LINKBUTTON);
			    
			    ControlButton button = controlForm.addButton("Entrar");
			    button.addControlButtonClickEventListener(new ControlButtonClickEventListener() {
					@Override
					public void onControlButtonClick(ControlButtonClickEvent event) {
						if (event.getRmForm().getTable().validate()) {
							Table tblUsuarios = database.loadTableByName("usuarios");
							try {
								database.openConnection();
								
								tblUsuarios.select("tipousua");
								tblUsuarios.setFilter("email", event.getRmForm().getTable().getString("email"));
								tblUsuarios.setFilter("senha", event.getRmForm().getTable().getString("senha"));
								tblUsuarios.loadData();
								if (!tblUsuarios.eof()) {
									if (event.getRmForm().getWarningMessage()!=null) {
										event.getRmForm().getWarningMessage().setVisible(false);
									}
									
									tblUsuarios.update();
									tblUsuarios.setFilter("email", event.getRmForm().getTable().getString("email"));
									tblUsuarios.setValue("lastlogin", new Date());
									tblUsuarios.setValue("loginid", UUID.randomUUID().toString().toUpperCase());
									tblUsuarios.execute();
									
									tblUsuarios.select("loginid");
									tblUsuarios.setFilter("email", event.getRmForm().getTable().getString("email"));
									tblUsuarios.loadData();
									
									Utils.saveCookie("login", tblUsuarios.getString("loginid"), 3600);
									
									System.out.println("Login efetuado com sucesso!");
								}
								else {
									if (event.getRmForm().getWarningMessage()!=null) {
										event.getRmForm().getWarningMessage().showWarningMessage("Usuário ou senha inválido(s).", WarningMessageType.ERROR);
									}
								}
							}
							finally {
								database.closeConnection();
							}
						}
					}
				});
			}
		});
	    
	    this.registrandoMenuPrincipal();
	    
	}

	public void registrandoMenuPrincipal() {
		/*
	    CommandRegistry commandExecuteRegistry = database.addCommandRegistry("medicos_cardiologista");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 1);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "5C2FE3B0-6D71-40FE-8245-976D1A56CF2D");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaProdutosView/"+encodedString);
			}
	    });
	    
	    commandExecuteRegistry = database.addCommandRegistry("medicos_clinico");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 1);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "EA2E6269-4A41-4431-AB00-9BB7293E4D26");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				//UI.getCurrent().getNavigator().navigateTo("pesquisaProdutosView/"+encodedString);
				UI.getCurrent().getNavigator().navigateTo("pesquisaProdutosView/ClinicoGeralPesquisa");
			}
		});

	    commandExecuteRegistry = database.addCommandRegistry("medicos_dentista");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				UI.getCurrent().getNavigator().navigateTo("testeView");
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("medicos_dermatologista");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 1);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "4DEB7671-F4F5-4B79-8A86-DD8B698C5BA5");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaProdutosView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("medicos_ginecologista");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 1);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "3C315E79-0435-4E4F-B821-F175B4D14947");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaProdutosView/"+encodedString);
			}
		});

	    commandExecuteRegistry = database.addCommandRegistry("medicos_ortopedia");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 1);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "D759B3EE-E00B-4B57-AA4B-7EAE5A459FD3");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaProdutosView/"+encodedString);
			}
		});

	    commandExecuteRegistry = database.addCommandRegistry("medicos_oftalmologia");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 1);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "21F06A6B-31C8-4D19-9C11-D36427428CFF");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaProdutosView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("medicos_pediatria");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				System.out.println("executou pediatria");
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 1);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "45C71B02-7F3F-4FB2-A0F3-5B9A30E15C2A");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaProdutosView/"+encodedString);
			}
		});
	    

	    commandExecuteRegistry = database.addCommandRegistry("medicos_urologia");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 1);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "5516ADD4-6F7F-496F-9FA9-3EC9B746FBC6");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaProdutosView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("medicos_outrasespecialidades");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				SelectEspecialidadesWindow.open(Zapdoctor2UI.Instance.filtroPesquisa);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("exames_laboratoriais");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 2);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "C54F37A3-FD0D-42F6-AF28-780A04B48C54");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaClinicasView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("exames_imagens");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 2);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "971088FF-14A4-4027-BD3C-34B0C32BB3C0");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaClinicasView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("pacotes_prenupciais");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 3);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "1");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaPacotesView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("pacotes_partoapartamento");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 3);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "1");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaPacotesView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("pacotes_partoaenfermaria");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 3);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "1");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaPacotesView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("pacotes_rotinacriancas");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 3);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "1");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaPacotesView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("pacotes_rotinaidosos");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 3);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "1");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaPacotesView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("pacotes_rotinageral");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 3);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "1");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaPacotesView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("pacotes_precirurgicos");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 3);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "1");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaPacotesView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("pacotes_examesadmissionais");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 3);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "1");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaPacotesView/"+encodedString);
			}
		});
	    
	    commandExecuteRegistry = database.addCommandRegistry("pacotes_examesdemissionais");
	    commandExecuteRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("tipoespe", 3);
				Zapdoctor2UI.Instance.filtroPesquisa.setValue("uidespe", "1");
				String encodedString = Zapdoctor2UI.Instance.filtroPesquisa.encode();
				UI.getCurrent().getNavigator().navigateTo("pesquisaPacotesView/"+encodedString);
			}
		});
		*/
	}
	
	/*
	public void insertClinicas() {
		try {
			database.openConnection();
			Table clinicas = database.loadTableByName("clinicas");
			
			clinicas.insert();
			clinicas.setValue("nome", "SAMEL ASSISTENCIA MEDICA");
			clinicas.setValue("nomecurto", "SAMEL");
			clinicas.setValue("logo", "imagens/clinicas/brand2.png");
			clinicas.execute();
			
			clinicas.insert();
			clinicas.setValue("nome", "SAMEL ASSISTENCIA MEDICA");
			clinicas.setValue("nomecurto", "SAMEL2");
			clinicas.setValue("logo", "imagens/clinicas/brand2.png");
			clinicas.execute();
			
			clinicas.insert();
			clinicas.setValue("nome", "SAMEL ASSISTENCIA MEDICA");
			clinicas.setValue("nomecurto", "SAMEL3");
			clinicas.setValue("logo", "imagens/clinicas/brand2.png");
			clinicas.execute();
			
			clinicas.insert();
			clinicas.setValue("nome", "SAMEL ASSISTENCIA MEDICA");
			clinicas.setValue("nomecurto", "SAMEL4");
			clinicas.setValue("logo", "imagens/clinicas/brand2.png");
			clinicas.execute();
			
			clinicas.insert();
			clinicas.setValue("nome", "SAMEL ASSISTENCIA MEDICA");
			clinicas.setValue("nomecurto", "SAMEL5");
			clinicas.setValue("logo", "imagens/clinicas/brand2.png");
			clinicas.execute();
			
			clinicas.insert();
			clinicas.setValue("nome", "SAMEL ASSISTENCIA MEDICA");
			clinicas.setValue("nomecurto", "SAMEL6");
			clinicas.setValue("logo", "imagens/clinicas/brand2.png");
			clinicas.execute();
			
			clinicas.insert();
			clinicas.setValue("nome", "SAMEL ASSISTENCIA MEDICA");
			clinicas.setValue("nomecurto", "SAMEL7");
			clinicas.setValue("logo", "imagens/clinicas/brand2.png");
			clinicas.execute();
		}
		finally {
			database.closeConnection();
		}
	}

	public void insertProdutosEspecialidade() {
		try {
			database.openConnection();
			
			try {
				database.executeCommand("truncate table produtos");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			Table tblEspecialidades = database.loadTableByName("especiliadades");
			tblEspecialidades.select("*");
			tblEspecialidades.setFilter("especialidade", "Pediatria");
			tblEspecialidades.loadData();
			if (!tblEspecialidades.eof()) {
				insertMedico(tblEspecialidades.getString("uid"));
			}
			
			tblEspecialidades.select("*");
			tblEspecialidades.setFilter("especialidade", "Exames laboratoriais");
			tblEspecialidades.loadData();
			if (!tblEspecialidades.eof()) {
				insertClinicasLaboratoriais(tblEspecialidades.getString("uid"));
			}
			
			tblEspecialidades.select("*");
			tblEspecialidades.setFilter("especialidade", "Exames de imagem");
			tblEspecialidades.loadData();
			if (!tblEspecialidades.eof()) {
				insertClinicasImagens(tblEspecialidades.getString("uid"));
			}
			
		}
		finally {
			database.closeConnection();
		}
	}
	
	public void insertClinicasImagens(String uidEspecialidade) {
		Table produtos = database.loadTableByName("produtos");
		Table tblProgramacaoAgenda = database.loadTableByName("programacaoagenda");
		
		produtos.insert();
		produtos.setValue("nome", "Grupo ProdImagem");
		produtos.setValue("nomecurto", "ProdImagem");
		produtos.setValue("especialidade", "Exames de imagem");
		produtos.setValue("fotogrande", "imagens/clinicas/prodimagem.png");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);

		produtos.setValue("nomelocaatend", "Grupo ProdImagem");
		produtos.setValue("endereco", "Rua Tapajós");
		produtos.setValue("numero", 685);
		produtos.setValue("bairro", "Centro");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.010-150");
		produtos.setValue("telefones", "(92) 2123-0300");
		produtos.setValue("qualificacao", "<b>Dados da clinica:</b><br>Data de fundação, e alguma informacao<br><br>Atua na cidade de manaus a 29 anos<br>Texto diverso...");
		produtos.setValue("latitude", -3.1237432); 
		produtos.setValue("longitude", -60.022507);
		
		produtos.setValue("diasatend01", "De segunda a sabado");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 2); // Segunda
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 3); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 4); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 5); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 6); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 7); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
	}

	
	public void insertClinicasLaboratoriais(String uidEspecialidade) {
		Table produtos = database.loadTableByName("produtos");
		Table tblProgramacaoAgenda = database.loadTableByName("programacaoagenda");

		produtos.insert();
		produtos.setValue("nome", "Hospital Samel");
		produtos.setValue("nomecurto", "Samel");
		produtos.setValue("especialidade", "Exames laboratoriais");
		produtos.setValue("fotogrande", "imagens/clinicas/samel.png");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);

		produtos.setValue("nomelocaatend", "HOSPITAL SAMEL");
		produtos.setValue("endereco", "AV Joaquim Nabuco");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Centro");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 2129-2200");
		produtos.setValue("qualificacao", "<b>Dados da clinica:</b><br>Data de fundação, e alguma informacao<br><br>Atua na cidade de manaus a 29 anos<br>Texto diverso...");
		produtos.setValue("latitude", -3.126448);
		produtos.setValue("longitude", -60.019385);
		
		produtos.setValue("diasatend01", "De segunda a sabado");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 2); // Segunda
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 1);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 3); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 1);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 4); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 1);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 5); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 1);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 6); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 1);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 7); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 1);
		tblProgramacaoAgenda.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Laboratório de Análises clinicas Biocenter");
		produtos.setValue("nomecurto", "Biocenter");
		produtos.setValue("especialidade", "Exames laboratoriais");
		produtos.setValue("fotogrande", "imagens/clinicas/biocenter.png");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);

		produtos.setValue("nomelocaatend", "LABORATÓRIO BIOCENTER");
		produtos.setValue("endereco", "Rua Luiz Antony");
		produtos.setValue("numero", 553);
		produtos.setValue("bairro", "Centro");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.010-100");
		produtos.setValue("telefones", "(92) 3633-1986");
		produtos.setValue("qualificacao", "<b>Dados da clinica:</b><br>Data de fundação, e alguma informacao<br><br>Atua na cidade de manaus a 29 anos<br>Texto diverso...");
		produtos.setValue("latitude", -3.12745);
		produtos.setValue("longitude", -60.027817);
		
		produtos.setValue("diasatend01", "De segunda a sabado");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 2); // Segunda
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 3); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 4); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 5); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 6); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 7); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Laboratórios Reunidos");
		produtos.setValue("nomecurto", "REUNIDOS");
		produtos.setValue("especialidade", "Exames laboratoriais");
		produtos.setValue("fotogrande", "imagens/clinicas/reunidos.png");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);

		produtos.setValue("nomelocaatend", "Laboratórios Reunidos");
		produtos.setValue("endereco", "Rua Monsenhor Coutinho");
		produtos.setValue("numero", 490);
		produtos.setValue("bairro", "Centro");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.010-110");
		produtos.setValue("telefones", "(92) 3584-8888");
		produtos.setValue("qualificacao", "<b>Dados da clinica:</b><br>Data de fundação, e alguma informacao<br><br>Atua na cidade de manaus a 29 anos<br>Texto diverso...");
		produtos.setValue("latitude", -3.1288997);  
		produtos.setValue("longitude", -60.0249377);
		
		produtos.setValue("diasatend01", "De segunda a sabado");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 2); // Segunda
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 3); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 4); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 5); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 6); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 7); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 19);
		tblProgramacaoAgenda.setValue("tempmedicons", 10);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
	}
	*/
	
	/*
	public void insertMedico(String uidEspecialidade) {
		Table produtos = database.loadTableByName("produtos");
		Table tblProgramacaoAgenda = database.loadTableByName("programacaoagenda");

		produtos.insert();
		produtos.setValue("nome", "Ramatis Soares de Melo");
		produtos.setValue("nomecurto", "Ramatis Melo");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/2.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);

		produtos.setValue("nomelocaatend", "HOSPITAL SAMEL");
		produtos.setValue("endereco", "AV Joaquim Nabuco");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Centro");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 2129-2200");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126448);
		produtos.setValue("longitude", -60.019385);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 2); // Segunda
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 20);
		tblProgramacaoAgenda.setValue("tempmedicons", 15);
		tblProgramacaoAgenda.setValue("tipoagenda", 1);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 4); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 20);
		tblProgramacaoAgenda.setValue("tempmedicons", 15);
		tblProgramacaoAgenda.setValue("tipoagenda", 1);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 6); 
		tblProgramacaoAgenda.setValue("horainicatend", "07:00");
		tblProgramacaoAgenda.setValue("numeatend", 20);
		tblProgramacaoAgenda.setValue("tempmedicons", 15);
		tblProgramacaoAgenda.setValue("tipoagenda", 1);
		tblProgramacaoAgenda.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Albert Samuel Melo");
		produtos.setValue("nomecurto", "Ramatis Samuel");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/3.png");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "HOSPITAL SAMEL");
		produtos.setValue("endereco", "AV Joaquim Nabuco");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Centro");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 2129-2200");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126448);
		produtos.setValue("longitude", -60.019385);
		
		produtos.setValue("diasatend01", "Terça/Quinta");
		produtos.setValue("horaatend01", "Apartir das 14hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 3); // Terca
		tblProgramacaoAgenda.setValue("horainicatend", "14:00");
		tblProgramacaoAgenda.setValue("numeatend", 20);
		tblProgramacaoAgenda.setValue("tempmedicons", 15);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 5); // Terca
		tblProgramacaoAgenda.setValue("horainicatend", "14:00");
		tblProgramacaoAgenda.setValue("numeatend", 20);
		tblProgramacaoAgenda.setValue("tempmedicons", 15);
		tblProgramacaoAgenda.setValue("tipoagenda", 2);
		tblProgramacaoAgenda.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Mery Vania Mendonca");
		produtos.setValue("nomecurto", "Mary Vania");
		produtos.setValue("sexo", "F");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/4.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);

		produtos.setValue("nomelocaatend", "SOCIEDADE PRÓ-SAUDE");
		produtos.setValue("endereco", "Rua Major Gabriel");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Praça 14");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 3232-4149");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126516);
		produtos.setValue("longitude", -60.016726);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 16hs as 20hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 2); // Segunda
		tblProgramacaoAgenda.setValue("horainicatend", "16:00");
		tblProgramacaoAgenda.setValue("numeatend", 20);
		tblProgramacaoAgenda.setValue("tempmedicons", 15);
		tblProgramacaoAgenda.setValue("tipoagenda", 3);
		tblProgramacaoAgenda.execute();

		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 4); // Segunda
		tblProgramacaoAgenda.setValue("horainicatend", "16:00");
		tblProgramacaoAgenda.setValue("numeatend", 20);
		tblProgramacaoAgenda.setValue("tempmedicons", 15);
		tblProgramacaoAgenda.setValue("tipoagenda", 3);
		tblProgramacaoAgenda.execute();

		tblProgramacaoAgenda.insert();
		tblProgramacaoAgenda.setValue("uidproduto", produtos.getLastInsertUUID());
		tblProgramacaoAgenda.setValue("diasemana", 6); // Segunda
		tblProgramacaoAgenda.setValue("horainicatend", "16:00");
		tblProgramacaoAgenda.setValue("numeatend", 20);
		tblProgramacaoAgenda.setValue("tempmedicons", 15);
		tblProgramacaoAgenda.setValue("tipoagenda", 3);
		tblProgramacaoAgenda.execute();

		produtos.insert();
		produtos.setValue("nome", "Marcos Aurelio da Silva");
		produtos.setValue("nomecurto", "Marcos Aurelio");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/6.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "SOCIEDADE PRÓ-SAUDE");
		produtos.setValue("endereco", "Rua Major Gabriel");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Praça 14");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 3232-4149");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126516);
		produtos.setValue("longitude", -60.016726);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Vanilene de Almeida Vasconcelos");
		produtos.setValue("nomecurto", "Vanilene Vasconcelos");
		produtos.setValue("sexo", "F");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/7.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "CLÍNICA UNISAÚDE");
		produtos.setValue("endereco", "Av Leonardo Malcher");
		produtos.setValue("numero", 1790);
		produtos.setValue("bairro", "Praça 14");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-070");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126195);
		produtos.setValue("longitude", -60.015185);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Clovis Machado da Costa");
		produtos.setValue("nomecurto", "Clovis Costa");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/4.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "CLÍNICA UNISAÚDE");
		produtos.setValue("endereco", "Av Leonardo Malcher");
		produtos.setValue("numero", 1790);
		produtos.setValue("bairro", "Praça 14");
		produtos.setValue("cidade", "Manaus");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-070");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126195);
		produtos.setValue("longitude", -60.015185);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		// Itacoatiara 
		produtos.insert();
		produtos.setValue("nome", "Raimundo da Silva Chavier");
		produtos.setValue("nomecurto", "Raimundo Chavier");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/2.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", "45C71B02-7F3F-4FB2-A0F3-5B9A30E15C2A");

		produtos.setValue("nomelocaatend", "HOSPITAL SAMEL");
		produtos.setValue("endereco", "AV Joaquim Nabuco");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Centro");
		produtos.setValue("cidade", "Itacoatiara");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 2129-2200");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126448);
		produtos.setValue("longitude", -60.019385);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Jorge Almeida Penedo");
		produtos.setValue("nomecurto", "Jorge Penedo");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/3.png");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "HOSPITAL SAMEL");
		produtos.setValue("endereco", "AV Joaquim Nabuco");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Centro");
		produtos.setValue("cidade", "Itacoatiara");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 2129-2200");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126448);
		produtos.setValue("longitude", -60.019385);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Mery Vania Mendonca");
		produtos.setValue("nomecurto", "Mary Vania");
		produtos.setValue("sexo", "F");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/4.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);

		produtos.setValue("nomelocaatend", "SOCIEDADE PRÓ-SAUDE");
		produtos.setValue("endereco", "Rua Major Gabriel");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Colônia");
		produtos.setValue("cidade", "Itacoatiara");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 3232-4149");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126516);
		produtos.setValue("longitude", -60.016726);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Marcos Aurelio da Silva");
		produtos.setValue("nomecurto", "Marcos Aurelio");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/6.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "SOCIEDADE PRÓ-SAUDE");
		produtos.setValue("endereco", "Rua Major Gabriel");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Colônia");
		produtos.setValue("cidade", "Itacoatiara");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 3232-4149");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126516);
		produtos.setValue("longitude", -60.016726);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		produtos.setValue("nome", "Vanilene de Almeida Vasconcelos");
		produtos.setValue("nomecurto", "Vanilene Vasconcelos");
		produtos.setValue("sexo", "F");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/7.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "CLÍNICA UNISAÚDE");
		produtos.setValue("endereco", "Av Leonardo Malcher");
		produtos.setValue("numero", 1790);
		produtos.setValue("bairro", "Colônia");
		produtos.setValue("cidade", "Itacoatiara");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-070");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126195);
		produtos.setValue("longitude", -60.015185);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		produtos.insert();
		produtos.setValue("nome", "Clovis Machado da Costa");
		produtos.setValue("nomecurto", "Clovis Costa");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/4.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "CLÍNICA UNISAÚDE");
		produtos.setValue("endereco", "Av Leonardo Malcher");
		produtos.setValue("numero", 1790);
		produtos.setValue("bairro", "Colônia");
		produtos.setValue("cidade", "Itacoatiara");
		produtos.setValue("estado", "AM");
		produtos.setValue("cep", "69.020-070");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126195);
		produtos.setValue("longitude", -60.015185);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		// Sao paulo - Sao Paulo 
		produtos.insert();
		produtos.setValue("nome", "Raimundo da Silva Chavier");
		produtos.setValue("nomecurto", "Raimundo Chavier");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/2.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", "45C71B02-7F3F-4FB2-A0F3-5B9A30E15C2A");

		produtos.setValue("nomelocaatend", "HOSPITAL SAMEL");
		produtos.setValue("endereco", "AV Joaquim Nabuco");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Cidade Jardim ");
		produtos.setValue("cidade", "São Paulo");
		produtos.setValue("estado", "SP");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 2129-2200");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126448);
		produtos.setValue("longitude", -60.019385);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		
		produtos.insert();
		produtos.setValue("nome", "Jorge Almeida Penedo");
		produtos.setValue("nomecurto", "Jorge Penedo");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/3.png");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "HOSPITAL SAMEL");
		produtos.setValue("endereco", "AV Joaquim Nabuco");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Campos Elíseos ");
		produtos.setValue("cidade", "São Paulo");
		produtos.setValue("estado", "SP");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 2129-2200");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126448);
		produtos.setValue("longitude", -60.019385);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		
		produtos.insert();
		produtos.setValue("nome", "Mery Vania Mendonca");
		produtos.setValue("nomecurto", "Mary Vania");
		produtos.setValue("sexo", "F");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/4.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);

		produtos.setValue("nomelocaatend", "SOCIEDADE PRÓ-SAUDE");
		produtos.setValue("endereco", "Rua Major Gabriel");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Cidade Jardim ");
		produtos.setValue("cidade", "São Paulo");
		produtos.setValue("estado", "SP");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 3232-4149");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126516);
		produtos.setValue("longitude", -60.016726);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		
		
		produtos.insert();
		produtos.setValue("nome", "Marcos Aurelio da Silva");
		produtos.setValue("nomecurto", "Marcos Aurelio");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/6.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "SOCIEDADE PRÓ-SAUDE");
		produtos.setValue("endereco", "Rua Major Gabriel");
		produtos.setValue("numero", 1715);
		produtos.setValue("bairro", "Cidade Jardim ");
		produtos.setValue("cidade", "São Paulo");
		produtos.setValue("estado", "SP");
		produtos.setValue("cep", "69.020-030");
		produtos.setValue("telefones", "(92) 3232-4149");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126516);
		produtos.setValue("longitude", -60.016726);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		

		produtos.insert();
		produtos.setValue("nome", "Vanilene de Almeida Vasconcelos");
		produtos.setValue("nomecurto", "Vanilene Vasconcelos");
		produtos.setValue("sexo", "F");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/7.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", "45C71B02-7F3F-4FB2-A0F3-5B9A30E15C2A");
		
		produtos.setValue("nomelocaatend", "CLÍNICA UNISAÚDE");
		produtos.setValue("endereco", "Av Leonardo Malcher");
		produtos.setValue("numero", 1790);
		produtos.setValue("bairro", "Cidade Jardim ");
		produtos.setValue("cidade", "São Paulo");
		produtos.setValue("estado", "SP");
		produtos.setValue("cep", "69.020-070");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126195);
		produtos.setValue("longitude", -60.015185);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
		

		produtos.insert();
		produtos.setValue("nome", "Clovis Machado da Costa");
		produtos.setValue("nomecurto", "Clovis Costa");
		produtos.setValue("sexo", "M");
		produtos.setValue("especialidade", "Pediatra");
		produtos.setValue("fotogrande", "imagens/produtos/4.jpg");
		produtos.setValue("avaliacao", 3);
		produtos.setValue("uidespe", uidEspecialidade);
		
		produtos.setValue("nomelocaatend", "CLÍNICA UNISAÚDE");
		produtos.setValue("endereco", "Av Leonardo Malcher");
		produtos.setValue("numero", 1790);
		produtos.setValue("bairro", "Campos Elíseos ");
		produtos.setValue("cidade", "São Paulo");
		produtos.setValue("estado", "SP");
		produtos.setValue("cep", "69.020-070");
		produtos.setValue("qualificacao", "<b>Formação:</b><br>Formado no ano de xxxx na faculdade de Ciencias Biologicas do Estado do Amazonas<br><br>Atua na cidade de manaus a 29 anos<br>Membro da sociedade norte americada de pediatria<br>Doutorado pela Faculdade Catarinence de Medicina<br>Texto diverso...");
		produtos.setValue("latitude", -3.126195);
		produtos.setValue("longitude", -60.015185);
		
		produtos.setValue("diasatend01", "Segunda/Quarta/Sexta");
		produtos.setValue("horaatend01", "Das 06hs as 10hs");
		
		produtos.setValue("valor", 270);
		produtos.execute();
	}
	*/
	
	/*
	public void insertEspecialidades() {
		try {
			database.openConnection();
			Table especialidades = database.loadTableByName("especialidades");
			
			/*
			especialidades.insert();
			especialidades.setValue("especialidade", "Alergia e Imunologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Anestesiologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Angiologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cancerologia (Oncologia)");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cardiologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cirurgia Cardiovascular");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cirurgia da Mão");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cirurgia de Cabeça e Pescoço");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cirurgia do Aparelho Digestório");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cirurgia Geral");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cirurgia Pediátrica");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cirurgia Plástica");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cirurgia Torácica");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Cirurgia Vascular");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Clínica Médica");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Coloproctologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Dermatologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Endocrinologia e Metabologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Endoscopia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Gastroenterologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Genética médica");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Geriatria");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Ginecologia e Obstetrícia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Hematologia e Hemoterapia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Homeopatia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Infectologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Mastologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Medicina de Família e Comunidade");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Medicina do Trabalho");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Medicina do Tráfego");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Medicina Esportiva");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Medicina Física e Reabilitação");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Medicina Intensiva");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Medicina Nuclear");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Medicina Preventiva e Social");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Nefrologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Neurocirurgia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Neonatologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Neurologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Nutrologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Oftalmologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Ortopedia e Traumatologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Otorrinolaringologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Patologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Pediatria");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Pneumologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Psiquiatria");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Radiologia e Diagnóstico por Imagem");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Radioterapia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Reumatologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Urologia");
			especialidades.setValue("tipoespe", 1); // Especialidade médica
			especialidades.execute();
			*/

			/*
			 * Exames
			 */
			/*
			especialidades.insert();
			especialidades.setValue("especialidade", "Exames laboratoriais");
			especialidades.setValue("uid", "C54F37A3-FD0D-42F6-AF28-780A04B48C54");
			especialidades.setValue("tipoespe", 2); // Exames 
			especialidades.execute();
			
			especialidades.insert();
			especialidades.setValue("especialidade", "Exames de imagem");
			especialidades.setValue("uid", "971088FF-14A4-4027-BD3C-34B0C32BB3C0");
			especialidades.setValue("tipoespe", 2); // Exames 
			especialidades.execute();
			*/
			
			/*
			 * Pacotes de serviços
			 */
			/*
			especialidades.insert();
			especialidades.setValue("especialidade", "Pacotes diversos");
			especialidades.setValue("tipoespe", 3); // Pacotes de servicos 
			especialidades.execute();
			*/
	/*
		}
		finally {
			database.closeConnection();
		}
	}

	/*
	public static void criarCarrinhoCompras() {
		Cookie cartCookie = Utils.getCookieByName("cartCookie");
		if (cartCookie==null) {
			cartCookie = new Cookie("cartCookie", UUID.randomUUID().toString().toUpperCase());
			cartCookie.setMaxAge(2592000); // 30 dias
			cartCookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(cartCookie);
		}
		
		Table carrinhoCompras = database.loadTableByName("carrinhocompras");
		carrinhoCompras.select("*");
		carrinhoCompras.setFilter("cartcookie", cartCookie.getValue());
		carrinhoCompras.loadData();
		if (carrinhoCompras.eof()) {
			carrinhoCompras.insert();
			carrinhoCompras.setValue("cartcookie", cartCookie.getValue());
			carrinhoCompras.execute();
		}
	}
	*/
	
	/*
	public void updateLocalizadorCupomToCart(String localizadorCupom) {
		this.criarCarrinhoCompras();
		
		Cookie cartCookie = Utils.getCookieByName("cartCookie");
		if (cartCookie==null) {
			cartCookie = new Cookie("cartCookie", UUID.randomUUID().toString().toUpperCase());
			cartCookie.setMaxAge(2592000); // 30 dias
			cartCookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(cartCookie);
		}
		
		Table carrinhoCompras = database.loadTableByName("carrinhocompras");
		carrinhoCompras.update();
		carrinhoCompras.setValue("localizadorcupom", localizadorCupom);
		carrinhoCompras.setFilter("cartcookie", cartCookie.getValue());
		carrinhoCompras.execute();
	}
	*/
	
	/*
	public static void addProductToCart(String uidProduto, Integer quantidade, Date agendamento) {
		Cookie cartCookie = Utils.getCookieByName("cartCookie");
		if (cartCookie==null) {
			cartCookie = new Cookie("cartCookie", UUID.randomUUID().toString().toUpperCase());
			cartCookie.setMaxAge(2592000); // 30 dias
			cartCookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(cartCookie);
		}
		
		DatabaseApp database = new DatabaseApp();
		Table cartItem = database.loadTableByName("cartitem");
		try {
			database.openConnection();
			cartItem.select("*");
			cartItem.setFilter("cartcookie", cartCookie.getValue());
			cartItem.setFilter("uidproduto", uidProduto);
			cartItem.loadData();
			if (cartItem.eof()) {
				cartItem.insert();
				cartItem.setValue("cartcookie", cartCookie.getValue());
				cartItem.setValue("uidproduto", uidProduto);
				cartItem.setValue("quantidade", quantidade);
				cartItem.setValue("agendamento", agendamento);
				cartItem.execute();
			}
			else {
				Integer newQuantidade = cartItem.getInteger("quantidade") + quantidade;
				
				cartItem.update();
				cartItem.setFilter("cartcookie", cartCookie.getValue());
				cartItem.setFilter("uidproduto", uidProduto);
				cartItem.setValue("quantidade", newQuantidade);
				cartItem.setValue("agendamento", agendamento);
				cartItem.execute();
			}
		}
		finally {
			database.closeConnection();
		}
	}
	*/
	
	/*
	public static boolean updateProductToCart(String uidProduto, Integer quantidade) {
		boolean retorno = false;
		
		Cookie cartCookie = Utils.getCookieByName("cartCookie");
		if (cartCookie==null) {
			cartCookie = new Cookie("cartCookie", UUID.randomUUID().toString().toUpperCase());
			cartCookie.setMaxAge(2592000); // 30 dias
			cartCookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(cartCookie);
		}
		
		Table cartItem = database.loadTableByName("cartitem");
		try {
			database.openConnection();
			cartItem.select("*");
			cartItem.setFilter("cartcookie", cartCookie.getValue());
			cartItem.setFilter("uidproduto", uidProduto);
			cartItem.loadData();
			if (cartItem.eof()) {
				cartItem.insert();
				cartItem.setValue("cartcookie", cartCookie.getValue());
				cartItem.setValue("uidproduto", uidProduto);
				cartItem.setValue("quantidade", quantidade);
				cartItem.execute();
			}
			else {
				cartItem.update();
				cartItem.setFilter("cartcookie", cartCookie.getValue());
				cartItem.setFilter("uidproduto", uidProduto);
				cartItem.setValue("quantidade", quantidade);
				cartItem.execute();
			}
			
			retorno = true;
		}
		finally {
			database.closeConnection();
		}
		
		return retorno;
	}
	*/
	
	/*
	public static boolean deleteProductFromCart(String uidProduto) {
		boolean retorno = false;
		
		Cookie cartCookie = Utils.getCookieByName("cartCookie");
		if (cartCookie==null) {
			cartCookie = new Cookie("cartCookie", UUID.randomUUID().toString().toUpperCase());
			cartCookie.setMaxAge(2592000); // 30 dias
			cartCookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(cartCookie);
		}
		
		Table cartItem = database.loadTableByName("cartitem");
		try {
			database.openConnection();
			cartItem.delete();
			cartItem.setFilter("cartcookie", cartCookie.getValue());
			cartItem.setFilter("uidproduto", uidProduto);
			cartItem.execute();
			
			retorno = true;
		}
		finally {
			database.closeConnection();
		}
		
		return retorno;
	}
	*/

	/*
	public static Double getValorProduto(SimpleRecord record) {
		Double retorno = .0;
		
		if (!(record.getDouble("vlrpromocional")==0)) {
			retorno=record.getDouble("vlrpromocional");
		}
		else {
			retorno=record.getDouble("valor");
		}
		
		return retorno;
	}
	*/
	
	/*
	// Retorna os totais do carrinho
	public SimpleRecord getTotalsCart(boolean updateCarrinhoCompras) {
		SimpleRecord retorno = new SimpleRecord();
		
		// Verifica a validade de uso do compom de desconto
		retorno.setValue("localizadorcupom", "");
		retorno.setValue("cupomvalid", 0); // 0 - Cupom nao informado, 1 - Cupom valido, 2 - Cupom invalido
		retorno.setValue("cupomerror", ""); // Mensagem de erro do cupom invalido
		retorno.setValue("subtotal", .0); // Subtotal dos produtos
		retorno.setValue("percdesc", .0);
		retorno.setValue("vlrdesc", .0);
		retorno.setValue("vlrdesconto", .0); // Valor de desconto do cupom
		retorno.setValue("totalgeral", .0); // Total geral da compra
		
		Cookie cartCookie = Utils.getCookieByName("cartCookie");
		if (cartCookie==null) {
			cartCookie = new Cookie("cartCookie", UUID.randomUUID().toString().toUpperCase());
			cartCookie.setMaxAge(2592000); // 30 dias
			cartCookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(cartCookie);
		}
		
		Table carrinhoCompras = database.loadTableByName("carrinhocompras");
		Table cartItem = database.loadTableByName("cartitem");
		Table tblProdutos = database.loadTableByName("produtos");
		try {
			database.openConnection();

			// Soma o valor de todos os produtos do carrinho e coloca em subtotal
			cartItem.select("*");
			cartItem.setFilter("cartcookie", cartCookie.getValue());
			cartItem.loadData();
			while (!cartItem.eof()) {
				tblProdutos.select("*");
				tblProdutos.setFilter("uid",cartItem.getString("uidproduto"));
				tblProdutos.loadData();
				if (!tblProdutos.eof()) {
					Integer qtd = cartItem.getInteger("quantidade");
					SimpleRecord recordProduto = tblProdutos.getCurrentSimpleRecord();
					Double vlrProduto = getValorProduto(recordProduto);
					
					retorno.setDouble("subtotal", retorno.getDouble("subtotal") + (qtd * vlrProduto));
				}
				tblProdutos.close();
				
				cartItem.next();
			}
			
			// Pega o cupom de desconto que estiver no carrinho
			String cupomDesconto="";
			carrinhoCompras.select("*");
			carrinhoCompras.setFilter("cartcookie", cartCookie.getValue());
			carrinhoCompras.loadData();
			if (!carrinhoCompras.eof()) {
				cupomDesconto = carrinhoCompras.getString("localizadorcupom");
				retorno.setValue("localizadorcupom", cupomDesconto);
			}
			carrinhoCompras.close();
			
			// Verifica o cupom de desconto
			if (!cupomDesconto.isEmpty()) {
				SimpleRecord recordCupom = checkCupomDesconto(cupomDesconto);
				
				if (recordCupom.getDouble("percdesc")>0) {
					Double vlrdesconto = ((recordCupom.getDouble("percdesc") * retorno.getDouble("subtotal")) / 100);
					vlrdesconto = Utils.roundDouble(vlrdesconto, 2);
					retorno.setValue("vlrdesconto", vlrdesconto);
				}
				else {
					retorno.setValue("vlrdesconto", recordCupom.getDouble("vlrdesc"));
				}
				
				retorno.setValue("cupomvalid", recordCupom.getInteger("cupomvalid"));
				retorno.setValue("cupomerror", recordCupom.getString("cupomerror"));
				retorno.setValue("percdesc", recordCupom.getDouble("percdesc"));
				retorno.setValue("vlrdesc", recordCupom.getDouble("vlrdesc"));
			}
			
			// Define o total geral
			retorno.setValue("totalgeral", retorno.getDouble("subtotal") - retorno.getDouble("vlrdesconto"));
			
			// Caso esteja programado para atualizar os valores no carrinho de compras, a compra esta sendo finalizada.
			if (updateCarrinhoCompras) {
				carrinhoCompras.select("*");
				carrinhoCompras.setFilter("cartcookie", cartCookie.getValue());
				carrinhoCompras.loadData();
				if (carrinhoCompras.eof()) {
					carrinhoCompras.insert();
					carrinhoCompras.setValue("cartcookie", cartCookie.getValue());
					carrinhoCompras.setValue("subtotal", retorno.getDouble("subtotal"));
					carrinhoCompras.setValue("percdesccupom", retorno.getDouble("percdesc"));
					carrinhoCompras.setValue("vlrdesccupom", retorno.getDouble("vlrdesc"));
					carrinhoCompras.setValue("vlrdesconto", retorno.getDouble("vlrdesconto"));
					carrinhoCompras.setValue("totalgeral", retorno.getDouble("totalgeral"));
					carrinhoCompras.execute();
				}
				else {
					carrinhoCompras.update();
					carrinhoCompras.setFilter("cartcookie", cartCookie.getValue());
					carrinhoCompras.setValue("subtotal", retorno.getDouble("subtotal"));
					carrinhoCompras.setValue("percdesccupom", retorno.getDouble("percdesc"));
					carrinhoCompras.setValue("vlrdesccupom", retorno.getDouble("vlrdesc"));
					carrinhoCompras.setValue("vlrdesconto", retorno.getDouble("vlrdesconto"));
					carrinhoCompras.setValue("totalgeral", retorno.getDouble("totalgeral"));
					carrinhoCompras.execute();
				}
			}
		}
		finally {
			database.closeConnection();
		}
		
		return retorno;
	}
	*/
	
	/*
	public SimpleRecord checkCupomDesconto(String cupomDesconto) {
		SimpleRecord retorno = new SimpleRecord();
		retorno.setValue("cupomvalid", 0); // 0 - Cupom nao informado, 1 - Cupom valido, 2 - Cupom invalido
		retorno.setValue("cupomerror", ""); // Mensagem de erro do cupom invalido
		retorno.setValue("percdesc", .0);
		retorno.setValue("vlrdesc", .0);
		
		// Verifica o cupom de desconto
		if (!cupomDesconto.isEmpty()) {
			retorno.setValue("cupomvalid", 1);
			
			Table tblCupomDesconto = database.loadTableByName("cupomdesconto");
			tblCupomDesconto.select("*");
			tblCupomDesconto.setFilter("localizador", cupomDesconto);
			tblCupomDesconto.loadData();
			
			// Cupom nao localizado
			if (tblCupomDesconto.eof()) {
				retorno.setValue("cupomvalid", 2); 
				retorno.setValue("cupomerror", "Cupom " + cupomDesconto + " não é valido!");
			}
			else {
				// Verifica se o cupom ja foi utilizado
				if ((retorno.getInteger("cupomvalid")==1) && (!(tblCupomDesconto.getDate("utilizado")==null))) {
					retorno.setValue("cupomvalid", 2);
					retorno.setValue("cupomerror", "Cupom " + cupomDesconto + " já foi utilizado!");
				}
				
				// Pega a data de vencimento do cupom considerando somente a parte data
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date dataAtual = new Date();
				try {
				    dataAtual = dateFormat.parse(dateFormat.format(dataAtual));
				} catch (ParseException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				}

				// Verifica se o cupom nao esta vencido
				Date vencimento = tblCupomDesconto.getDate("vencimento");
				
				if ((retorno.getInteger("cupomvalid")==1) && (dataAtual.after(vencimento))) {
					retorno.setValue("cupomvalid", 2);
					retorno.setValue("cupomerror", "Cupom " + cupomDesconto + " vencido em " + dateFormat.format(vencimento) + ".");
				}
				
				// Caso o cupom esteja ok para uso
				if (retorno.getInteger("cupomvalid")==1) {
					retorno.setValue("percdesc", tblCupomDesconto.getDouble("percdesc"));
					retorno.setValue("vlrdesc", tblCupomDesconto.getDouble("vlrdesc"));
				}
			}
			
			tblCupomDesconto.close();
		}
		
		return retorno;
	}
	*/
	
	/*
	public void criarCupomDesconto() {
		try {
			database.openConnection();

			String uuid = UUID.randomUUID().toString().toUpperCase();
			Table cupomDesconto = database.loadTableByName("cupomdesconto");
			
			cupomDesconto.insert();
			cupomDesconto.setValue("uid", uuid);
			cupomDesconto.setValue("percdesc", 10);
			cupomDesconto.execute();
			
			Integer sequencia = cupomDesconto.getLastInsertId();
			
		    String localizador = SmallEncript.encript(Utils.strZero(sequencia, 8));
			cupomDesconto.update();
			cupomDesconto.setValue("localizador", localizador);
			cupomDesconto.setFilter("uid", uuid);
			cupomDesconto.execute();
		}
		finally {
			database.closeConnection();
		}
	}
	*/
	
	/*
	public static Integer getNumberItemCart() {
		Integer retorno = 0;
		
		Cookie cartCookie = loadCookie();
		
		DatabaseApp database = new DatabaseApp();
		try {
			database.openConnection();
			try {
				ResultSet rs = database.executeSelect("select count(*) as qtd from cartitem where cartcookie='" + cartCookie.getValue() + "'");
				if (rs.next()) {
					retorno = rs.getInt("qtd");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		finally {
			database.closeConnection();
		}
		
		return retorno;
	}
	*/
	
	/*
	public static Cookie loadCookie() {
		Cookie cartCookie = Utils.getCookieByName("cartCookie");
		if (cartCookie==null) {
			cartCookie = new Cookie("cartCookie", UUID.randomUUID().toString().toUpperCase());
			cartCookie.setMaxAge(2592000); // 30 dias
			cartCookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(cartCookie);
		}
		
		return cartCookie;
	}
	*/
	
	/*
	public void updateProdBair() {
		Table tblProdutos = database.loadTableByName("produtos");
		Table tblProdBair = database.loadTableByName("prodbair");
		
		try {
			database.openConnection();
			database.executeCommand("truncate table prodbair");
			
			tblProdutos.select("*");
			tblProdutos.loadData();
			while (!tblProdutos.eof()) {
				tblProdBair.select("*");
				tblProdBair.setFilter("uidespe", tblProdutos.getString("uidespe"));
				tblProdBair.setFilter("ufestado", tblProdutos.getString("estado"));
				tblProdBair.setFilter("cidade", tblProdutos.getString("cidade"));
				tblProdBair.setFilter("bairro", tblProdutos.getString("bairro"));
				tblProdBair.loadData();
				if (tblProdBair.eof()) {
					tblProdBair.insert();
					tblProdBair.setValue("uidespe", tblProdutos.getString("uidespe"));
					tblProdBair.setValue("ufestado", tblProdutos.getString("estado"));
					tblProdBair.setValue("cidade", tblProdutos.getString("cidade"));
					tblProdBair.setValue("bairro", tblProdutos.getString("bairro"));
					tblProdBair.execute();
				}
				
				tblProdutos.next();
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			database.closeConnection();
		}
	}
	*/
	
	/*
	public void updateAgenda() {
		Table tblProgramacaoAgenda = database.loadTableByName("programacaoagenda");
		Table tblAgenda = database.loadTableByName("agenda");
		
		try {
			database.openConnection();
			database.executeCommand("truncate table agenda");
			
			tblProgramacaoAgenda.select("*");
			tblProgramacaoAgenda.loadData();
			while (!tblProgramacaoAgenda.eof()) {
				Integer numeatend = tblProgramacaoAgenda.getInteger("numeatend");
				String horario = tblProgramacaoAgenda.getString("horainicatend");
				for (int i=1; i<=numeatend; i++) {
					tblAgenda.insert();
					tblAgenda.setValue("uidproduto", tblProgramacaoAgenda.getString("uidproduto"));
					tblAgenda.setValue("diasemana", tblProgramacaoAgenda.getInteger("diasemana"));
					tblAgenda.setValue("horainicatend", tblProgramacaoAgenda.getString("horainicatend"));
					tblAgenda.setValue("horario", horario);
					tblAgenda.execute();
					
					horario = intToTime(timeToInt(horario)+tblProgramacaoAgenda.getInteger("tempmedicons"));
				}
				
				tblProgramacaoAgenda.next();
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			database.closeConnection();
		}
	}
	*/
	
	/*
    public void updateCarrinhoCompras(Integer quantidade) {
    	if (quantidade!=null) {
        	this.btnCarrinhoCompras.setCaption(quantidade+ "");
    	}
    	else {
        	this.btnCarrinhoCompras.setCaption(this.getNumberItemCart()+ "");
    	}
    }
    */
}
