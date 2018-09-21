package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
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

public class Fornecedores {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("fornecedores");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("fornecedores");
				tblTabela.addField("codiforn", FieldType.INTEGER, 10);
				tblTabela.addField("descricao", FieldType.VARCHAR, 50);
				tblTabela.addField("situacao", FieldType.VARCHAR, 1);
				tblTabela.addField("codimedi", FieldType.INTEGER, 10);
				//tblTabela.addField("desccodimedi", FieldType.VARCHAR, 50);
				
				tblTabela.addField("codiespepadr", FieldType.INTEGER, 50);
				//tblTabela.addField("desccodiespepadr", FieldType.VARCHAR, 50);
				
				tblTabela.addField("uidcaixa", FieldType.VARCHAR, 50);
				
				tblTabela.addField("tipo", FieldType.VARCHAR, 50);
				tblTabela.addField("endereco", FieldType.VARCHAR, 50);
				tblTabela.addField("numero", FieldType.VARCHAR, 5);
				tblTabela.addField("complemento", FieldType.VARCHAR, 250);
				tblTabela.addField("referencia", FieldType.VARCHAR, 250);
				tblTabela.addField("conjunto", FieldType.VARCHAR, 50);
				tblTabela.addField("bairro", FieldType.VARCHAR, 50);
				tblTabela.addField("cep", FieldType.VARCHAR, 10);
				tblTabela.addField("cidade", FieldType.VARCHAR, 50);
				tblTabela.addField("estado", FieldType.VARCHAR, 2);
				tblTabela.addField("telefones", FieldType.VARCHAR, 250);
				tblTabela.addField("email", FieldType.VARCHAR, 250);
				
				tblTabela.setPrimaryKey("codiforn");
				tblTabela.setOrder("descricao");
				tblTabela.setRequired("descricao", true);
				tblTabela.setAutoIncrement("codiforn", true);
				
				tblTabela.addIndex("codiforn", "codiforn");
				tblTabela.addIndex("descricao", "descricao");

				tblTabela.addJoin("medicos", "codimedi", "codimedi");
				//tblTabela.fieldByName("desccodimedi").setExpression("medicos.nome");

				tblTabela.setMask("cep", "99.999-999");

				ForeingSearch foreingSearch = tblTabela.fieldByName("cep").addForeingSearch();
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
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("cep").addExistenceCheck("cep", "cep", "cep", "CEP informado é inválido!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("tipo", event.getTargetTable().getString("tipo"));
						event.getSourceTable().setValue("endereco", event.getTargetTable().getString("logradouro"));
						event.getSourceTable().setValue("bairro", event.getTargetTable().getString("bairro"));
						event.getSourceTable().setValue("cidade", event.getTargetTable().getString("cidade"));
						event.getSourceTable().setValue("estado", event.getTargetTable().getString("estado"));
					}
				});
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("A", "Ativo");
				internalSearch.addItem("D", "Destativo");
				tblTabela.fieldByName("situacao").setInternalSearch(internalSearch);
				
				tblTabela.fieldByName("codimedi").addComboBox("medicos", "nome", "nome", "codimedi");
				tblTabela.fieldByName("codiespepadr").addComboBox("especialidades", "descricao", "descricao", "codiespe");

				tblTabela.fieldByName("uidcaixa").addComboBox("caixas", "descricao", "descricao", "uid");
				
				Table tblEspeForn2 = database.loadTableByName("espeforn2");
				tblTabela.addTableChild(tblEspeForn2, "uidfornecedor", true);
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("fornecedores");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("fornecedores");
				}
				
				RmGrid grdFornecedores = event.getRmGrid();
				grdFornecedores.setTable(table);
				grdFornecedores.setLimit(10);
				grdFornecedores.addField("codiforn", "Código", 100d);
				grdFornecedores.addField("descricao", "Fornecedor", 100d, 1);
				grdFornecedores.addField("situacao", "Situação", 100d);
				
				ControlForm controlForm = grdFornecedores.getForm();
				controlForm.setTitle("Manutenção de fornecedores:");
				controlForm.setWidth(1100d);
				controlForm.setHeight(800d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Dados do fornecedor.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("descricao", "Nome do fornecedor", 100d, 1);
					controlForm.addField("situacao", "Situação", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("codimedi", "MÉDICO incluido automaticamente nas guias de atendimento emitidos nas vendas deste fornecedor", 150d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("codiespepadr", "ESPECIALIDADE principal incluida automaticamente nas guias de atendimento emitidos nas vendas deste fornecedor", 150d, 1);
				
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("cadastro2", "Endereço:", SectionState.MINIMIZED);
					{
						controlForm.addNewLine();
						controlForm.addField("cep", "Cep", 200d);
						controlForm.addField("endereco", "Endereço", 250d, 1);
						controlForm.addField("tipo", "Tipo", 200d);
						
						controlForm.addNewLine();
						controlForm.addField("numero", "Numero", 100d);
						controlForm.addField("complemento", "Complemento", 250d, 1);
						
						controlForm.addNewLine();
						controlForm.addField("conjunto", "Conjunto", 100d, 1);
						controlForm.addField("bairro", "Bairro", 250d, 1);
						
						controlForm.addNewLine();
						controlForm.addField("referencia", "Referência", 250d, 1);
						controlForm.addField("cidade", "Cidade", 200d);
						controlForm.addField("estado", "Estado", 100d);
						
						controlForm.addNewLine();
						controlForm.addField("telefones", "Telefones", 120d, 1);
					}
					
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("especialidades", "Especialidades que o fornecedor oferece:", SectionState.MAXIMIZED, 600d);
					{
						controlForm.addRmGrid("espeforn2");
					}
					
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("caixa", "Caixa padrão de recebimento:", SectionState.MAXIMIZED, 180d);
					{
						controlForm.addNewLine();
						controlForm.addField("uidcaixa", "Caixa de recebimento", 100d, 1);
					}
				}
				
				controlForm = grdFornecedores.getFormFilter();
				controlForm.setTitle("Pesquisa por Fornecedores:");
				controlForm.setWidth(1100d);
				controlForm.setHeight(800d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa por fornecedores:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("descricao", "Nome do fornecedor", 100d, 1);
					controlForm.addField("situacao", "Situação", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("codimedi", "MÉDICO incluido automaticamente nas guias de atendimento emitidos nas vendas deste fornecedor", 150d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("codiespepadr", "ESPECIALIDADE principal incluida automaticamente nas guias de atendimento emitidos nas vendas deste fornecedor", 150d, 1);
				
					controlForm.exitSection();
					controlForm.addNewLine();
					controlForm.addSection("cadastro2", "Endereço:", SectionState.MAXIMIZED);
					{
						controlForm.addNewLine();
						controlForm.addField("cep", "Cep", 200d);
						controlForm.addField("endereco", "Endereço", 250d, 1);
						controlForm.addField("tipo", "Tipo", 200d);
						
						controlForm.addNewLine();
						controlForm.addField("numero", "Numero", 100d);
						controlForm.addField("complemento", "Complemento", 250d, 1);
						
						controlForm.addNewLine();
						controlForm.addField("conjunto", "Conjunto", 100d, 1);
						controlForm.addField("bairro", "Bairro", 250d, 1);
						
						controlForm.addNewLine();
						controlForm.addField("referencia", "Referência", 250d, 1);
						controlForm.addField("cidade", "Cidade", 200d);
						controlForm.addField("estado", "Estado", 100d);
						
						controlForm.addNewLine();
						controlForm.addField("telefones", "Telefones", 120d, 1);
					}
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("fornecedores");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrdFornecedores = database.loadRmGridByName("fornecedores");
				rmGrdFornecedores.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrdFornecedores, "Fornecedores", true);
			}
		});
		
	}
}
