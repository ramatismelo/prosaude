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
import com.evolucao.rmlibrary.database.ForeingSearch;
import com.evolucao.rmlibrary.database.InternalSearch;
import com.evolucao.rmlibrary.database.RmGridRegistry;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.TableRegistry;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent.AfterExistenceCheckEventListener;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent.AfterValidateOnEditEventListener;
import com.evolucao.rmlibrary.database.events.CommandEvent.CommandEventListener;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent.LoadRmGridEventListener;
import com.evolucao.rmlibrary.database.events.LoadTableEvent.LoadTableEventListener;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterPostInsertEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.AfterPostInsertEvent.AfterPostInsertEventListener;
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
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.UI;

public class RelaFechCaix {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("relafechcaix");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table table = event.getTable();
				table.setManagedTable(false);
				table.setTableName("relafechcaix");

				table.addField("sequencia", FieldType.INTEGER, 10);
				table.addField("emisinic", FieldType.DATE, 10);
				table.addField("emisfina", FieldType.DATE, 10);
				table.addField("tiporela", FieldType.VARCHAR, 1);
				table.addField("codicaix", FieldType.VARCHAR, 5);
				table.addField("desccodicaix", FieldType.VARCHAR, 50);
				table.addField("codiforn", FieldType.INTEGER, 10);
				table.addField("desccodiforn", FieldType.VARCHAR, 50);
				table.addField("usuario", FieldType.VARCHAR, 50);
				table.addField("tipofech", FieldType.VARCHAR, 1);
				table.addField("relacort", FieldType.VARCHAR, 1);
				table.setPrimaryKey("sequencia");
				
				table.setAutoIncrement("sequencia", true);
				
				table.addJoin("fornecedores", "codiforn", "codiforn");
				table.fieldByName("desccodiforn").setExpression("fornecedores.descricao");
				
				table.addJoin("caixas", "codicaix", "codicaix");
				table.fieldByName("desccodicaix").setExpression("caixas.descricao");
				
				table.addInitialValueEventListener(new InitialValueEventListener() {
					@Override
					public void onInitialValue(InitialValueEvent event) {
						event.getTable().setValue("emisinic", Utils.getJustDate(new Date()));
						event.getTable().setValue("emisfina", Utils.getJustDate(new Date()));
					}
				});
				
				table.fieldByName("emisinic").setRequired(true);
				table.fieldByName("emisfina").setRequired(true);
				table.fieldByName("tiporela").setRequired(true);
				table.fieldByName("codicaix").setRequired(true);
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("1", "Fechamento do caixa");
				internalSearch.addItem("2", "Prestação de contas");
				table.fieldByName("tiporela").setInternalSearch(internalSearch);
				
				internalSearch = new InternalSearch();
				//internalSearch.addItem("1", "Imprimir relação de cortesias");
				//internalSearch.addItem("2", "Não imprimir relação de cortesias");
				internalSearch.addItem("1", "Imprimir somente vendas sem cortesia");
				internalSearch.addItem("2", "Imprimir somente cortesias");
				internalSearch.addItem("3", "Imprimir todas as vendas e cortesias");
				table.fieldByName("relacort").setInternalSearch(internalSearch);
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("1", "Valor normal");
				internalSearch.addItem("2", "Valor de custo");
				table.fieldByName("tipofech").setInternalSearch(internalSearch);
				
				/*
				ForeingSearch foreingSearch = table.fieldByName("codiforn").addForeingSearch();
				foreingSearch.setTargetRmGridName("fornecedores");
				foreingSearch.setTargetIndexName("codiforn");
				foreingSearch.setRelationship("codiforn");
				foreingSearch.setReturnFieldName("codiforn");
				foreingSearch.setTitle("Pesquisa por fornecedores:");
				foreingSearch.setOrder("descricao");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				*/
				
				table.fieldByName("codiforn").addComboBox("fornecedores", "descricao", "descricao", "codiforn");

				ExistenceCheck existenceCheck = table.fieldByName("codiforn").addExistenceCheck("fornecedores", "codiforn", "codiforn", "Fornecedor é inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodiforn", event.getTargetTable().getString("descricao"));
					}
				});
				
				
				/*
				ForeingSearch foreingSearch = null;
				foreingSearch = table.fieldByName("codicaix").addForeingSearch();
				foreingSearch.setTargetRmGridName("caixas");
				foreingSearch.setTargetIndexName("codicaix");
				foreingSearch.setRelationship("codicaix");
				foreingSearch.setReturnFieldName("codicaix");
				foreingSearch.setTitle("Pesquisa por caixas:");
				foreingSearch.setOrder("descricao");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				*/
				
				table.fieldByName("codicaix").addComboBox("caixas", "descricao", "descricao", "codicaix");
				
				existenceCheck = table.fieldByName("codicaix").addExistenceCheck("caixas", "codicaix", "codicaix", "Caixa inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
						event.getSourceTable().setValue("desccodicaix", event.getTargetTable().getString("descricao"));
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("relafechcaix");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("relafechcaix");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("tiporela", "Tipo de relatório", 80d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Relação de fechamento do caixa:");
				controlForm.setWidth(700d);
				controlForm.setHeight(680d);
				
				controlForm.addNewLine();
				controlForm.addSection("especilidades", "Emissão do fechamento do caixa.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("emisinic", "Data inicial", 150d, 1);
					controlForm.addField("emisfina", "Data inicial", 150d, 1);
					
					controlForm.addNewLine();
					controlForm.addField("tiporela", "Tipo de relatório", 100d, 1);
					controlForm.addField("tipofech", "Tipo de fechamento", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("codicaix", "Cód.Caixa", 150d, 1);
					//controlForm.addField("desccodicaix", "Caixa", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("codiforn", "Cód.Forn.", 150d, 1);
					//controlForm.addField("desccodiforn", "Fornecedor", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("usuario", "Usuário", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("relacort", "Relação de cortesias", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por especialidades:");
				controlForm.setWidth(700d);
				controlForm.setHeight(380d);
				
				controlForm.addNewLine();
				controlForm.addSection("especilidades", "Controle de especialidades.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("tiporela", "Tipo de relatório", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("tipofech", "Tipo de fechamento", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("codicaix", "Caixa", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("codiforn", "Fornecedor", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("usuario", "Usuário", 100d, 1);
					controlForm.addNewLine();
					controlForm.addField("relacort", "Relação de cortesias", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("relafechcaix");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("relafechcaix");
				//rmGrid.updateContent();
				if (rmGrid.getTable().insert()) {
					RmFormWindow formWindow = new RmFormWindow();
					rmGrid.getForm().setWindow(formWindow);
					rmGrid.getForm().setTable(rmGrid.getTable());
					formWindow.setTitle(rmGrid.getForm().getTitle());
					formWindow.setForm(rmGrid.getForm());
					formWindow.setWidth(rmGrid.getForm().getWidth()+"px");
					formWindow.setHeight(rmGrid.getForm().getHeight()+"px");

					// Inclui botoes adicionais ao formWindow
					for (RmFormButtonBase rmFormButtonBase: rmGrid.getForm().getRmFormButtonList()) {
						formWindow.getRmFormButtons().add(rmFormButtonBase);
					}
					
					// Nao atualizao o grid e nao atualiza os botoes tambem
					// deve atualizar somente o numero de registros no grid se necessario
					//updateRecords();
					rmGrid.getTable().setLoadingDataToForm(true);
					formWindow.getBody().addComponent(rmGrid.getForm().deploy());
					rmGrid.getTable().setLoadingDataToForm(false);

					// Caso a tabela possua o evento anexado, excluir ele para anexar um novo
					//AfterPostInsertEventListener afterPostInsertEventListener = (AfterPostInsertEventListener) getTable().getListenerAttached(AfterPostInsertEventListener.class);
					//if (afterPostInsertEventListener!=null) {
					//	rmGrid.getTable().removeAfterPostInsertEventListener(afterPostInsertEventListener);
					//}
					
					// Caso ainda não tenha sido anexado o evento afterPostInsert a tabela, anexa ele uma unica vez
					//if (!rmGrid.getTable().hasListenerAttached(AfterPostInsertEventListener.class)) {
					//	rmGrid.getTable().addAfterPostInsertEventListener(new AfterPostInsertEventListener() {
					//		@Override
					//		public void onAfterPostInsert(AfterPostInsertEvent event) {
					//			String uidLastRecord = event.getTable().getUidLastRecordProccessed();
					//			SimpleRecord simpleRecord = null;
					//			
					//			Table table = rmGrid.getTable().getDatabase().loadTableByName(rmGrid.getTable().getTableName());
					//			table.select(rmGrid.getTable().getFieldsInSelect());
					//			table.setFilter("uid", uidLastRecord);
					//			table.loadData();
					//			if (!table.eof()) {
					//				simpleRecord = table.getSimpleRecordList().get(0);
					//			}
					//			rmGrid.getTable().getSimpleRecordList().add(simpleRecord);
					//			
					//			rmGrid.getGrid().setItems(rmGrid.getTable().getSimpleRecordList());
					//			rmGrid.getGrid().select(simpleRecord);
					//			rmGrid.getGrid().scrollToEnd();
					//		}
					//	});
					//}
					
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
								
								String urlLink = "http://" + tblParametros.getString("reportServer") + "/php-app/relafechcaix.php";
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
										if (field.getFieldName().equalsIgnoreCase("codiforn")) {
											System.out.println("debug codiforn=" + field.getString() + " - " + field.getInteger());
										}
										
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
