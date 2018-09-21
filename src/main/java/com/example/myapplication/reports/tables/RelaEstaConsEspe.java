package com.example.myapplication.reports.tables;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.Field;
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
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.UI;

public class RelaEstaConsEspe {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("relaestaconsespe");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setManagedTable(false);
				tblTabela.setTableName("relaestaconsespe");
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("datainic", FieldType.DATE, 10);
				tblTabela.addField("datafina", FieldType.DATE, 10);
				tblTabela.setPrimaryKey("sequencia");
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("relaestaconsespe");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("relaestaconsespe");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("prefixo", "Prefixo", 80d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Estatística de consultas por especialidades.");
				controlForm.setWidth(600d);
				controlForm.setHeight(400d);
				
				controlForm.addNewLine();
				controlForm.addSection("associado", "Estatística de consultas por especialidades.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("datainic", "Data Inicial", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("datafina", "Data de finalizacão", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("relaestaconsespe");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("relaestaconsespe");
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
								
								String urlLink = "http://" + tblParametros.getString("reportServer") + "/php-app/relaestaconsespe.php";
								String urlPdf = null;
								
								try {
									URL url = new URL(urlLink);
									HttpURLConnection conn = (HttpURLConnection) url.openConnection();
									conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
									conn.setRequestMethod("POST");
									conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
									String urlParameters = "";
									
									Table tblTabela = window.getForm().getTable();
									for (Field field: tblTabela.getFields()) {
										if (!urlParameters.isEmpty()) {
											urlParameters = urlParameters + "&";
										}
										
										// Caso o campo tenha seu conteudo vazio
										if (field.fieldIsEmpty()) {
											urlParameters = urlParameters + field.getFieldName() + "=";
										}
										else {
											// Caso o conteudo do campo nao seja vazio e seu tipo seja datetime
											if (field.getFieldType()==FieldType.DATE) {
												SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
												urlParameters = urlParameters + field.getFieldName() + "=" + simpleDateFormat.format(field.getDate());
											}
											else {
												// Caso o conteudo do campo nao seja vazio e seu tipo nao seja datetime
												urlParameters = urlParameters + field.getFieldName() + "=" + field.getString();
											}
										}
									}
									
									System.out.println(urlParameters);
									conn.setDoOutput(true);
									DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
									wr.writeBytes(urlParameters);
									wr.flush();
									wr.close();			
									
									int responseCode = conn.getResponseCode();
									
									BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
									String inputLine = null;
									StringBuffer response = new StringBuffer();

									while ((inputLine = in.readLine()) != null) {
										response.append(inputLine);
									}
									in.close();
									
									urlPdf = response.toString();
									urlPdf = urlPdf.substring(urlPdf.indexOf("temp")+6);
									urlPdf = urlPdf.substring(0, urlPdf.indexOf("\""));
									String urlLink2 = "http://" + tblParametros.getString("reportServer") + "/php-app/temp/" + urlPdf;
									UI.getCurrent().getPage().open(urlLink2, "_blank", false);
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
