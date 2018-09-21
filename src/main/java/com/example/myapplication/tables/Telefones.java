package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.AllowLike;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class Telefones {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("telefones");

		/*
		   $telefones = new Tabela();
		   $telefones->setTableName('telefones');
		   $telefones->addField('sequencia', 'integer', 10);
		   $telefones->addField('telefone', 'varchar', 250);
		   $telefones->addField('processo', 'varchar', 50);
		   $telefones->setChavePrimaria('sequencia');
		   
		   $telefones->AddIndex('telefone', 'telefone');
		   
		   $campo = $telefones->getCampo('sequencia');
		   $campo->setAutoIncrement(true);
		   
		   return $telefones;
		   */
		
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("telefones");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("telefone", FieldType.VARCHAR, 250);
				tblTabela.addField("processo", FieldType.VARCHAR, 50);
				tblTabela.addField("processamento", FieldType.VARCHAR, 10);
				tblTabela.setPrimaryKey("sequencia");
				tblTabela.setOrder("sequencia");
				
				tblTabela.fieldByName("sequencia").setAutoIncrement(true);
				
				tblTabela.fieldByName("telefone").setAllowLike(AllowLike.NONE);

				tblTabela.addIndex("telefone", "telefone");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("telefones");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("telefones");
				}
				
				RmGrid grdCep = event.getRmGrid();
				grdCep.setTable(table);
				grdCep.setHeight("550px");
				grdCep.setLimit(10);
				grdCep.addField("cep", "CEP", 150D);
				grdCep.addField("tipo", "Tipo", 150d);
				grdCep.addField("logradouro", "Logradouro", 300d, 1);
				grdCep.addField("bairro", "Bairro", 200d);
				grdCep.addField("cidade", "Cidade", 200d);
				grdCep.addField("estado", "Estado", 100d);

				grdCep.setAllowInsert(false);
				grdCep.setAllowUpdate(false);
				grdCep.setAllowDelete(false);
				
				ControlForm controlForm = grdCep.getForm();
				controlForm.setTitle("Manutenção de CEP:");
				controlForm.setWidth(600d);
				controlForm.setHeight(450d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Cadastro de CEP.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("estado", "Estado", 100d);
					controlForm.addField("cidade", "Cidade", 200d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("tipo",  "Tipo", 100d);
					controlForm.addField("logradouro", "Logradouro", 200d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("bairro",  "Bairro", 200d, 1);
					controlForm.addField("cep", "Cep", 100d);
				}
				
				controlForm = grdCep.getFormFilter();
				controlForm.setTitle("Pesquisa por CEP:");
				controlForm.setWidth(600d);
				controlForm.setHeight(450d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa por CEP.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("estado", "Estado", 100d);
					controlForm.addField("cidade", "Cidade", 200d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("tipo",  "Tipo", 100d);
					controlForm.addField("logradouro", "Logradouro", 200d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("bairro",  "Bairro", 200d, 1);
					controlForm.addField("cep", "Cep", 100d);
				}
			}
		});
		
		CommandRegistry commandRegistry = database.addCommandRegistry("telefones");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrdFornecedores = database.loadRmGridByName("telefones");
				rmGrdFornecedores.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrdFornecedores, "Cep", true);
			}
		});
		
	}
}
