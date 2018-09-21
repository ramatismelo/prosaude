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
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent.SpecialConditionOfUpdateEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent.SpecialConditionOfDeleteEventListener;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class Requisitantes {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("requisitantes");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table table = event.getTable();
				table.setTableName("requisitantes");
				table.addField("sequencia", FieldType.INTEGER, 10);
				table.addField("descricao", FieldType.VARCHAR, 50);
				table.addField("especialidade", FieldType.VARCHAR, 50);
				table.addField("intref", FieldType.VARCHAR, 100);
				table.setPrimaryKey("sequencia");
				table.setOrder("descricao");
				table.setAutoIncrement("sequencia", true);
				
				table.addIndex("sequencia", "sequencia");
				table.addIndex("descricao", "descricao");
				table.addIndex("intref", "intref");
				
				table.addSpecialConditionOfUpdateEventListener(new SpecialConditionOfUpdateEventListener() {
					@Override
					public void onSpecialConditionOfUpdate(SpecialConditionOfUpdateEvent event) {
						String debug = event.getTable().getString("intref");
						System.out.println(debug);
						if ((event.getTable().getString("intref")!=null) && (!event.getTable().getString("intref").isEmpty()))  {
							event.setValid(false);
							event.setErrorMessage("Não é permitido modificar um requisitante que seja médico!");
						}
					}
				});
				
				table.addSpecialConditionOfDeleteEventListener(new SpecialConditionOfDeleteEventListener() {
					@Override
					public void onSpecialConditionOfDelete(SpecialConditionOfDeleteEvent event) {
						if ((event.getTable().getString("intref")!=null) && (!event.getTable().getString("intref").isEmpty()))  {
							event.setValid(false);
							event.setErrorMessage("Não é permitido excluir médico enquanto ele estiver ativo!");
						}
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("requisitantes");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("requisitantes");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("descricao", "Descrição", 150d, 1);
				rmGrid.addField("especialidade", "Especialidade", 150d, 1);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção de requisitantes:");
				controlForm.setWidth(800d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("grau", "Controle de requisitantes.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("descricao", "Descrição", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("especialidade", "Especialidade (caso sejá médico)", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por requisitantes:");
				controlForm.setWidth(800d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("caixas", "Controle de caixas.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("descricao", "Descrição", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("especialidade", "Especialidade (caso sejá médico)", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("requisitantes");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("requisitantes");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Requisitantes", true);
			}
		});
	}
}
