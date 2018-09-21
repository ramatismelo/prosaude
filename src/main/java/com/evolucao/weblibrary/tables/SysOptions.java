package com.evolucao.weblibrary.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.InternalSearch;
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
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.enumerators.CapitalizationType;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class SysOptions {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("sysoptions");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setTableName("sysoptions");
				tblTabela.addField("projectname", FieldType.VARCHAR, 50);
				tblTabela.addField("codiopti", FieldType.VARCHAR, 50);
				tblTabela.addField("grau", FieldType.VARCHAR, 1);
				tblTabela.addField("textoption", FieldType.VARCHAR, 50);
				tblTabela.addField("optiontype", FieldType.VARCHAR, 1);
				tblTabela.addField("descoptiontype", FieldType.VARCHAR, 50);
				
				tblTabela.addField("mainoptioniconname", FieldType.VARCHAR, 250);
				tblTabela.addField("mainoptiondesc", FieldType.VARCHAR, 250);
				
				tblTabela.addField("optioncommand", FieldType.VARCHAR, 250);
				
				tblTabela.addField("shortcuticonname", FieldType.VARCHAR, 250);
				tblTabela.addField("shortcuttip", FieldType.VARCHAR, 250);
				tblTabela.addField("shortcutcommand", FieldType.VARCHAR, 250);
				
				tblTabela.setPrimaryKey("uid");
				tblTabela.setOrder("codiopti");
				
				tblTabela.fieldByName("projectname").setAllowLike(AllowLike.NONE);
				tblTabela.fieldByName("codiopti").setAllowLike(AllowLike.NONE);

				tblTabela.fieldByName("textoption").setCapitalizationType(CapitalizationType.NONE);
				tblTabela.fieldByName("optioncommand").setCapitalizationType(CapitalizationType.NONE);
				tblTabela.fieldByName("mainoptioniconname").setCapitalizationType(CapitalizationType.NONE);
				tblTabela.fieldByName("mainoptiondesc").setCapitalizationType(CapitalizationType.NONE);
				tblTabela.fieldByName("shortcuticonname").setCapitalizationType(CapitalizationType.NONE);
				tblTabela.fieldByName("shortcuttip").setCapitalizationType(CapitalizationType.NONE);
				tblTabela.fieldByName("shortcutcommand").setCapitalizationType(CapitalizationType.NONE);
				
				InternalSearch optionType = tblTabela.fieldByName("optiontype").addInternalSearch();
				optionType.addItem("0", "Opção principal");
				optionType.addItem("1", "Grupo de opções");
				optionType.addItem("2", "Opção normal");
				optionType.addItem("3", "Opção destacada");
				optionType.addItem("4", "Opção de comando");
				
				tblTabela.fieldByName("descoptiontype").setValueOfInternalSearch("optiontype");
				
				InternalSearch isGrau = tblTabela.fieldByName("grau").addInternalSearch();
				isGrau.addItem("1", "1");
				isGrau.addItem("2", "2");
				isGrau.addItem("3", "3");
				
				tblTabela.setAllowLike("projectname", AllowLike.NONE);
				
				tblTabela.addIndex("projectname_codioption", "projectname, codiopti");
				
				tblTabela.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("projectname", "clinica");
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("sysoptions");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				// Tabelas filhas precisam ter a capacidade de receber do pai a tabela
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("sysoptions");
				}

				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(100);
				rmGrid.addField("codiopti", "Código", 150d);
				rmGrid.addField("grau", "Grau", 80d);
				rmGrid.addField("textoption", "Texto", 100d, 1);
				rmGrid.addField("descoptiontype", "Tipo de opção", 200d);
				rmGrid.addField("optioncommand", "Comando", 200d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de opções:");
				controlForm.setWidth(1200d);
				controlForm.setHeight(800d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Programação de opções:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("codiopti", "Código", 100d);
					controlForm.addField("grau", "grau", 100d);
					controlForm.addField("textoption", "Texto", 100d, 1);
					controlForm.addField("optiontype", "Tipo de opção", 250d);
					
					controlForm.addNewLine();
					controlForm.addField("optioncommand", "Comando para ser executado ao clicar nesta opção", 250d, 1);
					controlForm.exitSection();
					
					controlForm.addNewLine();
					controlForm.addSection("mainoption", "Dados adicionais para opção principal", SectionState.MAXIMIZED);
					controlForm.addNewLine();
					controlForm.addField("mainoptioniconname", "Nome do icone da opção principal", 100d, 1);
					controlForm.addField("mainoptiondesc", "Descrição das subopções", 100d, 1);
					controlForm.exitSection();
					
					controlForm.addNewLine();
					controlForm.addSection("option", "Dados adicionais para atalho", SectionState.MAXIMIZED);
					controlForm.addNewLine();
					controlForm.addField("shortcuticonname", "Icone do atalho", 100d, 1);
					controlForm.addField("shortcuttip", "Dica do atalho", 100d, 1);
					controlForm.addField("shortcutcommand", "Comando do atalho", 100d, 1);
				}
				//********************************
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por Opções:");
				controlForm.setWidth(600d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("cadastro", "Pesquisa de opções:", SectionState.MAXIMIZED);
				{
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("sysoptions");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("sysoptions");
				rmGrid.updateContent();
				
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Opções do sistema", true);
			}
		});
	}
}
