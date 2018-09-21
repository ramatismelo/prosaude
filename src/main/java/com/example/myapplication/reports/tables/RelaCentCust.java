package com.example.myapplication.reports.tables;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
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
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.reports.ReportCentCust;
import com.example.myapplication.reports.ReportCentCust2;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.UI;

public class RelaCentCust {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("relacentcust");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table table = event.getTable();
				table.setManagedTable(false);
				table.setTableName("relacentcust");

				table.addField("sequencia", FieldType.INTEGER, 10);
				table.addField("emisinic", FieldType.DATE, 10);
				table.addField("emisfina", FieldType.DATE, 10);
				table.addField("codicaix", FieldType.VARCHAR, 5);
				table.addField("uidcentcust", FieldType.VARCHAR, 50);
				table.addField("tiporela", FieldType.VARCHAR, 1);
				table.setPrimaryKey("sequencia");
				
				table.setAutoIncrement("sequencia", true);
				
				table.addJoin("caixas", "codicaix", "codicaix");
				
				table.addJoin("centroscusto", "uid", "uidcentcust");
				
				table.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("emisinic", Utils.getJustDate(new Date()));
						event.getTable().setValue("emisfina", Utils.getJustDate(new Date()));
						event.getTable().setValue("tiporela", "2");
					}
				});
				
				table.fieldByName("emisinic").setRequired(true);
				table.fieldByName("emisfina").setRequired(true);
				table.fieldByName("codicaix").setRequired(true);
				table.fieldByName("tiporela").setRequired(true);
				
				table.fieldByName("codicaix").addComboBox("caixas", "descricao", "descricao", "codicaix");
				table.fieldByName("uidcentcust").addComboBox("centroscusto", "descricao", "descricao", "uid");
				
				InternalSearch isTipoRela = table.fieldByName("tiporela").addInternalSearch();
				isTipoRela.addItem("1", "Analitico");
				isTipoRela.addItem("2", "Sintetico");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("relacentcust");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("relacentcust");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("tiporela", "Tipo de relatório", 80d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Relação de Centros de custo:");
				controlForm.setWidth(700d);
				controlForm.setHeight(480d);
				
				controlForm.addNewLine();
				controlForm.addSection("especilidades", "Emissão de despesas por centro de custo.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("emisinic", "Data inicial", 150d, 1);
					controlForm.addField("emisfina", "Data inicial", 150d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("codicaix", "Caixa", 150d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("uidcentcust", "Centro de custo", 150d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("tiporela", "Tipo de relatório", 150d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("relacentcust");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("relacentcust");
				//rmGrid.updateContent();
				if (rmGrid.getTable().insert()) {
					RmFormWindow formWindow = new RmFormWindow();
					rmGrid.getForm().setWindow(formWindow);
					rmGrid.getForm().setTable(rmGrid.getTable());
					formWindow.setTitle(rmGrid.getForm().getTitle());
					formWindow.setForm(rmGrid.getForm());
					formWindow.setWidth(rmGrid.getForm().getWidth()+"px");
					formWindow.setHeight(rmGrid.getForm().getHeight()+"px");

					// Inclui botoes adicionaso ao formWindow
					for (RmFormButtonBase rmFormButtonBase: rmGrid.getForm().getRmFormButtonList()) {
						formWindow.getRmFormButtons().add(rmFormButtonBase);
					}
					
					// Nao atualizao o grid e nao atualiza os botoes tambem
					// deve atualizar somente o numero de registros no grid se necessario
					//updateRecords();
					rmGrid.getTable().setLoadingDataToForm(true);
					formWindow.getBody().addComponent(rmGrid.getForm().deploy());
					rmGrid.getTable().setLoadingDataToForm(false);

					RmFormButtonBase buttonBase = formWindow.addCancelButton();
					buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
						@Override
						public void onRmFormButtonClick(RmFormButtonClickEvent event) {
							RmFormWindow window = (RmFormWindow) event.getWindow();
							window.getForm().getTable().cancel();
							window.close();
						}
					});
					
					RmFormButtonBase buttonBase2 = formWindow.addButton("Imprimir relatório");
					buttonBase2.setIcon(new ThemeResource("imagens/library/print.png"));
					buttonBase2.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
						@Override
						public void onRmFormButtonClick(RmFormButtonClickEvent event) {
							if (rmGrid.getForm().getTable().validate()) {
								RmFormWindow window = (RmFormWindow) event.getWindow();
								
								ApplicationUI ui = (ApplicationUI) UI.getCurrent();
								Table tblParametros = ui.getDatabase().loadTableByName("parametros");
								tblParametros.select("*");
								tblParametros.loadData();
								
								String urlLink = "http://" + tblParametros.getString("reportServer") + "/php-app/relafechcaix2.php";
								String urlPdf = null;
								
								try {
									//UI.getCurrent().getPage().open("http://localhost:8080/myapplication/VAADIN/themes/mytheme/imagens/library/print.png", "_blank", false);
									//UI.getCurrent().getPage().open("http://localhost:8080/myapplication/reports/relatorio.pdf", "_blank", false);
									if (event.getControlForm().getTable().getString("tiporela").equalsIgnoreCase("1")) {
										ReportCentCust reportCentCust = new ReportCentCust();
										reportCentCust.setTable(event.getControlForm().getTable());
										reportCentCust.build();
										
										//UI.getCurrent().getPage().open("http://localhost:8080/myapplication/VAADIN/themes/mytheme/reports/" + reportCentCust.getFileName(), "_blank", false);
										UI.getCurrent().getPage().open(tblParametros.getString("newreportserver") + "/VAADIN/themes/mytheme/reports/" + reportCentCust.getFileName(), "_blank", false);
									}
									else {
										ReportCentCust2 reportCentCust = new ReportCentCust2();
										reportCentCust.setTable(event.getControlForm().getTable());
										reportCentCust.build();
										
										//UI.getCurrent().getPage().open("http://localhost:8080/myapplication/VAADIN/themes/mytheme/reports/" + reportCentCust.getFileName(), "_blank", false);
										UI.getCurrent().getPage().open(tblParametros.getString("newreportserver") + "/VAADIN/themes/mytheme/reports/" + reportCentCust.getFileName(), "_blank", false);
									}
								}
								catch (Exception e) {
									System.out.println(e.getMessage());
								}

								//window.getForm().getTable().cancel();
								//window.close();
							}
						}
					});
					
					formWindow.show();
				}
			}
		});
	}
}
