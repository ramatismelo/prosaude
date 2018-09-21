package com.example.myapplication.reports.tables;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.ExistenceCheck;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.ForeingSearch;
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

public class RelaEtiqCasa {
	public static void configure(Database database) {
		TableRegistry tableRegistry = database.addTableRegistry("relaetiqcasa");
		tableRegistry.addLoadTableEventListener(new LoadTableEventListener() {
			@Override
			public void onLoadTable(LoadTableEvent event) {
				Table tblTabela = event.getTable();
				tblTabela.setManagedTable(false);
				tblTabela.setTableName("relaetiqcasa");
				tblTabela.addField("nome", FieldType.VARCHAR, 50);
				tblTabela.addField("nasc", FieldType.DATE, 10);
				tblTabela.addField("sequencia", FieldType.INTEGER, 10);
				tblTabela.addField("prefixo", FieldType.VARCHAR, 2);
				tblTabela.addField("mae", FieldType.VARCHAR, 50);
				tblTabela.addField("prefixos", FieldType.VARCHAR, 250);
				tblTabela.addField("cep", FieldType.VARCHAR, 10);
				tblTabela.addField("rua", FieldType.VARCHAR, 250);
				tblTabela.addField("tipo", FieldType.VARCHAR, 50);
				tblTabela.addField("bai", FieldType.VARCHAR, 50);
				tblTabela.addField("cid", FieldType.VARCHAR, 50);
				tblTabela.addField("sexo", FieldType.VARCHAR, 1);
				tblTabela.addField("filhos", FieldType.VARCHAR, 1);
				tblTabela.addField("nascinic", FieldType.DATE, 10);
				tblTabela.addField("nascfina", FieldType.DATE, 10);
				tblTabela.addField("nasc1", FieldType.DATE, 10);
				tblTabela.addField("nasc2", FieldType.DATE, 10);
				tblTabela.addField("someassotele", FieldType.VARCHAR, 1);
				tblTabela.setPrimaryKey("sequencia");
				
				InternalSearch internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÀO");
				tblTabela.fieldByName("filhos").setInternalSearch(internalSearch);
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("F", "FEMININO");
				internalSearch.addItem("M", "MASCULINO");
				tblTabela.fieldByName("sexo").setInternalSearch(internalSearch);
				
				internalSearch = new InternalSearch();
				internalSearch.addItem("S", "SIM");
				internalSearch.addItem("N", "NÀO");
				tblTabela.fieldByName("someassotele").setInternalSearch(internalSearch);
				
				ForeingSearch foreingSearch = tblTabela.fieldByName("prefixo").addForeingSearch();
				foreingSearch.setTargetRmGridName("prefixo");
				foreingSearch.setTargetIndexName("prefixo");
				foreingSearch.setRelationship("prefixo");
				foreingSearch.setReturnFieldName("prefixo");
				foreingSearch.setTitle("Pesquisa por prefixos:");
				foreingSearch.setOrder("descricao");
				foreingSearch.setAutoOpenFilterForm(false);
				foreingSearch.setWidth("800px");
				foreingSearch.setHeight("655px");
				
				ExistenceCheck existenceCheck = tblTabela.fieldByName("prefixo").addExistenceCheck("prefixo", "prefixo", "prefixo", "Prefixo é inválida!");
				existenceCheck.addAfterExistenceCheckEventListener(new AfterExistenceCheckEventListener() {
					@Override
					public void onAfterExistenceCheck(AfterExistenceCheckEvent event) {
					}
				});
			}
		});
		
	    RmGridRegistry rmGridRegistry = database.addRmGridRegistry("relaetiqcasa");
	    rmGridRegistry.addLoadRmGridEventListener(new LoadRmGridEventListener() {
			@Override
			public void onLoadRmGrid(LoadRmGridEvent event) {
				Table table = event.getTable();
				if (table==null) {
					table = database.loadTableByName("relaetiqcasa");
				}
				
				RmGrid rmGrid = event.getRmGrid();
				rmGrid.setTable(table);
				rmGrid.setLimit(10);
				rmGrid.addField("prefixo", "Prefixo", 80d);
				
				ControlForm controlForm = rmGrid.getForm();
				controlForm.setTitle("Etiqueta por casa:");
				controlForm.setWidth(700d);
				controlForm.setHeight(680d);
				
				controlForm.addNewLine();
				controlForm.addSection("associado", "Pesquisa por associado.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("nome", "Nome", 250d, 1);
					controlForm.addField("nasc", "Nascimento", 150d);
					controlForm.addField("sequencia", "Sequência", 100d);
					controlForm.addField("prefixo", "Prefixo", 100d);
					
					controlForm.addNewLine();
					controlForm.addField("mae", "Mãe", 100d, 1);
					controlForm.addField("prefixos", "Relação de prefixos", 260d);
				}
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("endereco", "Pesquisa por endereço.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("cep", "Cep", 100d);
					controlForm.addField("rua", "Logradouro", 250d, 1);
					controlForm.addField("tipo", "Tipo", 200d);
					
					controlForm.addNewLine();
					controlForm.addField("bai", "Bairro", 200d, 1);
					controlForm.addField("cid", "Municipio", 200d);
					controlForm.addField("sexo", "Sexo", 150d);
					controlForm.addField("filhos", "Filhos", 100d);
				}
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("aniversariantes", "Pesquisa por aniversariantes:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("nascinic", "Data inicial", 100d, 1);
					controlForm.addField("nascfina", "Data final", 100d, 1);
				}
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("nascimento", "Pesquisa por nascimento:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("nasc1", "Nascimento de", 100d, 1);
					controlForm.addField("nasc2", "Nascimento ate", 100d, 1);
				}
				
				controlForm.exitSection();
				controlForm.addNewLine();
				controlForm.addSection("detalhamento", "Detalhamento:", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("someassotele", "Somente associados com telefone", 100d, 1);
				}
				
				controlForm = rmGrid.getFormFilter();
				controlForm.setTitle("Pesquisa por especialidades:");
				controlForm.setWidth(700d);
				controlForm.setHeight(380d);
				
				controlForm.addNewLine();
				controlForm.addSection("especilidades", "Controle de especialidades.", SectionState.MAXIMIZED);
				{
					controlForm.addNewLine();
					controlForm.addField("nome", "Nome", 100d, 1);
				}
			}
		});
	    
		CommandRegistry commandRegistry = database.addCommandRegistry("relaetiqcasa");
		commandRegistry.addCommandEventListener(new CommandEventListener() {
			@Override
			public void onCommandExecute(CommandEvent event) {
				RmGrid rmGrid = database.loadRmGridByName("relaetiqcasa");
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
								
								String urlLink = "http://" + tblParametros.getString("reportServer") + "/php-app/relaetiqcasa.php";
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
