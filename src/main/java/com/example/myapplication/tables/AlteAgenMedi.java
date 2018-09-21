package com.example.myapplication.tables;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.InternalSearch;
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

public class AlteAgenMedi {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("alteagenmedi");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table table = event.getTable();
				table.setTableName("alteagenmedi");
				table.addField("sequencia", FieldType.INTEGER, 10);
				table.addField("codimedi", FieldType.INTEGER, 10);
				table.addField("desccodimedi", FieldType.VARCHAR, 50);
				table.addField("data", FieldType.DATE, 10);
				table.addField("horainic", FieldType.VARCHAR, 5);
				table.addField("horaterm", FieldType.VARCHAR, 5);
				table.addField("tipoalte", FieldType.VARCHAR, 1);
				table.addField("desctipoalte", FieldType.VARCHAR, 50);
				table.addField("numeatend", FieldType.INTEGER, 10);
				table.addField("numereto", FieldType.INTEGER, 10);
				table.addField("observacoes", FieldType.TEXT, 250);
				table.setPrimaryKey("sequencia");
				table.setOrder("data desc");
				
				table.addIndex("codimedi_data", "codimedi, data");
				
				table.fieldByName("horainic").setMask("99:99");
				table.fieldByName("horaterm").setMask("99:99");
				
				table.addJoin("medicos", "codimedi", "codimedi");
				table.fieldByName("desccodimedi").setExpression("medicos.nome");
				
				table.fieldByName("codimedi").addComboBox("medicos", "nome", "nome", "codimedi");
				
				InternalSearch isTipoAlte = new InternalSearch();
				isTipoAlte.addItem("I", "Incluir dia de atendimento");
				isTipoAlte.addItem("E", "Excluir dia de atendimento");
				table.fieldByName("tipoalte").setInternalSearch(isTipoAlte);
				
				table.fieldByName("desctipoalte").setValueOfInternalSearch("tipoalte");
				
				table.fieldByName("codimedi").setRequired(true);
				table.fieldByName("data").setRequired(true);
				table.fieldByName("tipoalte").setRequired(true);
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("alteagenmedi");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("alteagenmedi");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("data", "Data", 140d);
				rmGrid.addField("desctipoalte", "Tipo de alteração", 200d, 1);
				rmGrid.addField("horainic", "Inicio do atendimento", 100d);
				rmGrid.addField("horaterm", "Termino do atendimento", 100d);
				rmGrid.addField("numeatend", "No. de atendimentos", 160d);
				rmGrid.addField("numereto", "No. de retornos", 160d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Manutenção na agenda médica:");
				controlForm.setWidth(800d);
				controlForm.setHeight(700d);
				
				controlForm.addNewLine();
				controlForm.addSection("caixas", "Controle de alterações da agenda médica.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("data", "Data da alteração", 100d, 1);
					controlForm.addField("tipoalte", "Tipo de alteração", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("horainic", "Inicio do atendimento", 100d, 1);
					controlForm.addField("horaterm", "Termino do atendimento", 100d, 1);
					controlForm.addField("numeatend", "No. de atendimentos", 100d, 1);
					controlForm.addField("numereto", "No. de retornos", 100d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("observacoes", "Observações", 100d, 200d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por alterações na agenda médica:");
				controlForm.setWidth(800d);
				controlForm.setHeight(300d);
				
				controlForm.addNewLine();
				controlForm.addSection("caixas", "Controle de alterações da agenda médica.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("data", "Data da alteração", 100d, 1);

					controlForm.addNewLine();
					controlForm.addField("tipoalte", "Tipo de alteração", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("alteagenmedi");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("alteagenmedi");
				rmGrid.updateContent();

				ApplicationUI ui = (ApplicationUI) UI.getCurrent();
				ui.getSystemView().addTab(rmGrid, "Alterações na agenda médica", true);
			}
		});
	}
}
