package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
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
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class Cep {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("cep");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("cep");
				tblTabela.addField("cep", FieldType.VARCHAR, 10);
				tblTabela.addField("tipo", FieldType.VARCHAR, 50);
				tblTabela.addField("logradouro", FieldType.VARCHAR, 250);
				tblTabela.addField("logradouro_comacentos", FieldType.VARCHAR, 250);
				tblTabela.addField("bairro", FieldType.VARCHAR, 250);
				tblTabela.addField("bairro_comacentos", FieldType.VARCHAR, 250);
				tblTabela.addField("cidade", FieldType.VARCHAR, 50);
				tblTabela.addField("cidade_comacentos", FieldType.VARCHAR, 50);
				tblTabela.addField("estado", FieldType.VARCHAR, 2);
				tblTabela.addField("processado", FieldType.VARCHAR, 50);
				tblTabela.setPrimaryKey("cep");
				
				tblTabela.setOrder("estado, cidade, bairro, tipo, logradouro");
				
				tblTabela.addIndex("cep", "cep");
				tblTabela.addIndex("estado_cidade_bairro_tipo_logradouro", "estado, cidade, bairro, tipo, logradouro");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("cep");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("cep");
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
		
		CommandRegistry commandRegistry = database.addCommandRegistry("cep");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrdFornecedores = database.loadRmGridByName("cep");
				rmGrdFornecedores.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrdFornecedores, "Cep", true);
			}
		});
		
	}
}
