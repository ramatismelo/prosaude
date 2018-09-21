package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.AllowLike;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class Associados2 {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("associados2");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setDebugQuery(true);
				tblTabela.setTableName("associados2");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("prefixo", FieldType.VARCHAR, 2);
				tblTabela.addField("codigo", FieldType.VARCHAR, 8);
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("rua", FieldType.VARCHAR, 250);
				tblTabela.addField("endenume", FieldType.VARCHAR, 5);
				tblTabela.addField("bai", FieldType.VARCHAR, 50);
				tblTabela.addField("tipo", FieldType.VARCHAR, 50);
				tblTabela.addField("refer", FieldType.VARCHAR, 250);
				tblTabela.addField("complemento", FieldType.VARCHAR, 250);
				tblTabela.addField("cep", FieldType.VARCHAR, 10);
				tblTabela.addField("nasc", FieldType.DATE, 10);
				tblTabela.addField("cid", FieldType.VARCHAR, 50);
				tblTabela.addField("uf", FieldType.VARCHAR, 2);
				tblTabela.addField("sexo", FieldType.VARCHAR, 1);
				tblTabela.addField("filhos", FieldType.VARCHAR, 1);
				tblTabela.addField("emiscart", FieldType.DATETIME, 10);
				tblTabela.addField("telefone", FieldType.VARCHAR, 20);
				tblTabela.addField("telefone02", FieldType.VARCHAR, 20);
				tblTabela.addField("telefone03", FieldType.VARCHAR, 20);
				tblTabela.addField("telefone04", FieldType.VARCHAR, 20);
				tblTabela.addField("mae", FieldType.VARCHAR, 50);
				tblTabela.addField("contcep", FieldType.VARCHAR, 10);
				tblTabela.addField("contrua", FieldType.VARCHAR, 50);
				tblTabela.addField("conttipo", FieldType.VARCHAR, 50);
				tblTabela.addField("contendenume", FieldType.VARCHAR, 5);
				tblTabela.addField("contcomplemento", FieldType.VARCHAR, 250);
				tblTabela.addField("contbai", FieldType.VARCHAR, 50);
				tblTabela.addField("contrefer", FieldType.VARCHAR, 250);
				tblTabela.addField("entrcert", FieldType.DATETIME, 10);
				tblTabela.addField("confimpr", FieldType.DATETIME, 10);
				tblTabela.addField("numefilh", FieldType.INTEGER, 10);
				tblTabela.addField("numefilhmaior", FieldType.INTEGER, 10);
				tblTabela.addField("operadora03", FieldType.VARCHAR, 20);
				tblTabela.addField("operadora04", FieldType.VARCHAR, 20);
				tblTabela.addField("processamento", FieldType.VARCHAR, 50);
				tblTabela.addField("md5ende", FieldType.VARCHAR, 50);
				tblTabela.addField("situacao", FieldType.VARCHAR, 50);
				tblTabela.addField("uid", FieldType.VARCHAR, 50);
				tblTabela.addField("conjunto", FieldType.VARCHAR, 50);
				tblTabela.addField("contconjunto", FieldType.VARCHAR, 50);
				tblTabela.addField("contcid", FieldType.VARCHAR, 50);
				tblTabela.addField("identidade", FieldType.VARCHAR, 50);
				tblTabela.setPrimaryKey("sequencia");
				
				tblTabela.addIndex("sequencia", "sequencia");
				tblTabela.addIndex("incldata", "incldata");
				tblTabela.addIndex("modidata", "modidata");
				tblTabela.addIndex("prefixo", "prefixo");
				tblTabela.addIndex("nome_nasc", "nome, nasc");
				tblTabela.addIndex("situacao_nome_nasc", "situacao, nome, nasc");
				tblTabela.addIndex("nome", "nome");
				tblTabela.addIndex("md5ende_situacao", "md5ende, situacao");
				tblTabela.addIndex("md5ende", "md5ende");
				tblTabela.addIndex("conjunto", "conjunto");
				
				tblTabela.setOrder("nome");
				
				tblTabela.setAllowLike("situacao", AllowLike.NONE);

				tblTabela.setRequired("prefixo", true);
				tblTabela.setRequired("nome", true);
				tblTabela.setRequired("mae", true);
				tblTabela.setRequired("cep", true);
				tblTabela.setRequired("rua", true);
				tblTabela.setRequired("endenume", true);
				tblTabela.setRequired("bai", true);
				tblTabela.setRequired("cid", true);
				tblTabela.setRequired("nasc", true);
				tblTabela.setRequired("sexo", true);
				tblTabela.setRequired("filhos", true);
				tblTabela.setRequired("situacao", true);
				
				tblTabela.setMask("cep", "99.999-999");
				tblTabela.setMask("contcep", "99.999-999");
				
				tblTabela.fieldByName("nome").setAllowLike(AllowLike.END);
				
				tblTabela.fieldByName("sequencia").setAutoIncrement(true);
				tblTabela.fieldByName("sequencia").setReadOnly(true);
				tblTabela.fieldByName("modidata").setReadOnly(true);
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("prefixo").addForeingSearch();
				foreingSearch.setTargetRmGridName("prefixo");
				foreingSearch.setTargetIndexName("prefixo");
				foreingSearch.setRelationship("prefixo");
				foreingSearch.setReturnFieldName("prefixo");
				foreingSearch.setTitle("Pesquisa por prefixo:");
				foreingSearch.setOrder("prefixo");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("prefixo").addExistenceCheck("prefixo", "prefixo", "prefixo", "Prefixo informado é inválido!");

				// Cep
				foreingSearch = tblTabela.fieldByName("cep").addForeingSearch();
				foreingSearch.setTargetRmGridName("cep");
				foreingSearch.setTargetIndexName("cep");
				foreingSearch.setRelationship("cep");
				foreingSearch.setReturnFieldName("cep");
				foreingSearch.setTitle("Pesquisa por CEP:");
				foreingSearch.setOrder("cep");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				foreingSearch.setAllowInsert(false);
				foreingSearch.setAllowUpdate(false);
				foreingSearch.setAllowDelete(false);
				foreingSearch.setAllowPrint(false);
				
				existenceCheck = tblTabela.fieldByName("cep").addExistenceCheck("cep", "cep", "cep", "CEP informado é inválido!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("tipo", event.getTargetTable().getString("tipo"));
						event.getSourceTable().setValue("rua", event.getTargetTable().getString("logradouro"));
						event.getSourceTable().setValue("bai", event.getTargetTable().getString("bairro"));
						event.getSourceTable().setValue("cid", event.getTargetTable().getString("cidade"));
					}
				});
				
				// Contato Cep
				foreingSearch = tblTabela.fieldByName("contcep").addForeingSearch();
				foreingSearch.setTargetRmGridName("cep");
				foreingSearch.setTargetIndexName("cep");
				foreingSearch.setRelationship("contcep");
				foreingSearch.setReturnFieldName("cep");
				foreingSearch.setTitle("Pesquisa por CEP:");
				foreingSearch.setOrder("cep");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				existenceCheck = tblTabela.fieldByName("contcep").addExistenceCheck("cep", "cep", "contcep", "CEP informado é inválido!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("conttipo", event.getTargetTable().getString("tipo"));
						event.getSourceTable().setValue("contrua", event.getTargetTable().getString("logradouro"));
						event.getSourceTable().setValue("contbai", event.getTargetTable().getString("bairro"));
						event.getSourceTable().setValue("contcid", event.getTargetTable().getString("cidade"));
					}
				});
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("VIVO", "VIVO");
				internalSearch.addItem("TIM", "TIM");
				internalSearch.addItem("CLARO", "CLARO");
				internalSearch.addItem("OI", "OI");
				tblTabela.fieldByName("operadora03").setInternalSearch(internalSearch);

				internalSearch = new InternalSearch();
				internalSearch.addItem("VIVO", "VIVO");
				internalSearch.addItem("TIM", "TIM");
				internalSearch.addItem("CLARO", "CLARO");
				internalSearch.addItem("OI", "OI");
				tblTabela.fieldByName("operadora04").setInternalSearch(internalSearch);
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("M", "MASCULINO");
				internalSearch.addItem("F", "FEMININO");
				tblTabela.fieldByName("sexo").setInternalSearch(internalSearch);
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÃO");
				tblTabela.fieldByName("filhos").setInternalSearch(internalSearch);
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("A", "ATIVO");
				internalSearch.addItem("D", "DESATIVO");
				tblTabela.fieldByName("situacao").setInternalSearch(internalSearch);

				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("situacao", "A");
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("associados2");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("associados2");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setHeight("550px");
				rmGrid.setLimit(10);
				rmGrid.addField("sequencia", "Sequência", 100D);
				rmGrid.addField("prefixo", "Prefixo", 100d);
				rmGrid.addField("nome", "Nome", 250d, 1);
				rmGrid.addField("nasc", "Nascimento", 150d);
				rmGrid.addField("mae", "Mãe", 250d);
				rmGrid.addField("incluser", "Inclusão - Usuário", 150d);
				rmGrid.addField("incldata", "Inclusão - Data", 150d);
				rmGrid.addField("modiuser", "Modificação - Usuário", 150d);
				rmGrid.addField("modidata", "Modificação - Data", 150d);
				
				rmGrid.setAllowDelete(false);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de associados:");
				controlForm.setWidth(790d);
				controlForm.setHeight(530d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do associado:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("sequencia", "Sequência", 100d);
					controlForm.addField("prefixo", "Prefixo", 140d);
					controlForm.addField("nome", "Nome", 250d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("mae", "Nome da mãe", 250d, 1);
				}
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("cadastro2", "Endereco residencial ou de contato em Manaus:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("cep", "Cep", 200d);
					controlForm.addField("rua", "Logradouro", 250d, 1);
					controlForm.addField("tipo", "Tipo", 200d);
					
					controlForm.addNewLine();
					controlForm.addField("endenume", "Numero", 100d);
					controlForm.addField("complemento", "Complemento", 250d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("conjunto", "Conjunto", 100d, 1);
					controlForm.addField("bai", "Bairro", 250d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("refer", "Referência", 250d, 1);
					controlForm.addField("cid", "Municipio", 200d);
				}
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("cadastro3", "Endereco residêncial fora de Manaus", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("contcep", "Cep", 200d);
					controlForm.addField("contrua", "Logradouro", 250d, 1);
					controlForm.addField("conttipo", "Tipo", 200d);
					
					controlForm.addNewLine();
					controlForm.addField("contendenume", "Numero", 100d);
					controlForm.addField("contcomplemento", "Complemento", 250d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("contconjunto", "Conjunto", 100d, 1);
					controlForm.addField("contbai", "Bairro", 250d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("contrefer", "Referência", 250d, 1);
					controlForm.addField("contcid", "Municipio", 200d);
				}

				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("cadastro4", "Outras informacoes:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("telefone", "Tel. Residencial", 120d);
					controlForm.addField("telefone02", "Tel. para recado", 120d);
					controlForm.addField("telefone03", "Telefone celular", 120d);
					controlForm.addField("operadora03", "Operadora", 80d);
					controlForm.addField("telefone04", "Celular para recado", 140d);
					controlForm.addField("operadora04", "Operadora", 80d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("nasc", "Nascimento", 120d);
					controlForm.addField("sexo", "Sexo", 150d);
					controlForm.addField("filhos", "Filhos", 100d);
					controlForm.addField("numefilh", "No. filhos", 100d);
					controlForm.addField("numefilhmaior", "Filhos maiores", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("identidade", "Identidade No.", 120d);
					controlForm.addField("modidata", "Ultimo contato", 120d);
					controlForm.addField("entrcert", "Entrega do certificado", 160d);
					controlForm.addField("situacao", "Situação cadastral", 100d, 1);
				}

				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por ASSOCIADOS:");
				controlForm.setWidth(790d);
				controlForm.setHeight(530d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa por associados:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("nome", "Nome", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 120d);
					controlForm.addField("sequencia", "Sequência", 100d);
					controlForm.addField("prefixo", "Prefixo", 100d);

					controlForm.addNewLine();
					controlForm.addField("mae", "Mãe", 100d, 1);
				}
				
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("cadastro2", "Pesquisa por endereço:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("cep", "Cep", 150d);
					controlForm.addField("rua", "Logradouro", 250d, 1);
					controlForm.addField("tipo", "Tipo", 200d);
					
					controlForm.addNewLine();
					controlForm.addField("conjunto", "Conjunto", 200d, 1);
					controlForm.addField("bai", "Bairro", 200d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("cid", "Municipio", 200d, 1);
					controlForm.addField("sexo", "Sexo", 150d);
					controlForm.addField("filhos", "Filhos", 100d);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("associados2");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrdFornecedores = database.loadRmGridByName("associados2");
				rmGrdFornecedores.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrdFornecedores, "Associados", true);
			}
		});
	}
}
