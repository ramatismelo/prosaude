package com.evolucao.rmlibrary.ui.production;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.swing.event.EventListenerList;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.SerializableComparator;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.components.grid.SortOrderProvider;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.StyleGenerator;
import com.vaadin.ui.VerticalLayout;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.Filter;
import com.evolucao.rmlibrary.database.SimpleField;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.MessageWindowType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.table.AfterPostEvent;
import com.evolucao.rmlibrary.database.events.table.AfterPostInsertEvent;
import com.evolucao.rmlibrary.database.events.table.AfterPostUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.AfterPostUpdateEvent.AfterPostUpdateEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent.AfterUpdateEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterPostInsertEvent.AfterPostInsertEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterPostEvent.AfterPostEventListener;
import com.evolucao.rmlibrary.ui.controller.ControlContentField;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.events.AfterSaveButtonClickEvent;
import com.evolucao.rmlibrary.ui.events.ControlGridAfterUpdateRecordsEvent;
import com.evolucao.rmlibrary.ui.events.ControlGridAfterUpdateRecordsEvent.ControlGridAfterUpdateRecordsEventListener;
import com.evolucao.rmlibrary.ui.events.ControlGridSelectionEvent;
import com.evolucao.rmlibrary.ui.events.ControlGridSelectionEvent.ControlGridSelectionEventListener;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;

public class RmGrid extends VerticalLayout {
	Table table = null;
	
	Grid<SimpleRecord> grid = new Grid<SimpleRecord>();
	List<ControlContentField> contentFieldList = new ArrayList<ControlContentField>();
	HorizontalLayout buttonSet = new HorizontalLayout();
	CssLayout buttonSetBottom = new CssLayout();
	boolean showButtonSet = true;
	boolean showButtonSetBottom = true;
	int limit = 10;
	ControlForm form = new ControlForm();
	ControlForm formFilter = new ControlForm();
	RmGrid rmGrid = this;
	
	Button btnFirst = new Button();
	Button btnPrior = new Button();
	Button btnNext = new Button();
	Button btnLast = new Button();
	Button btnRefresh = new Button();
	
	Label lblRecordCount = new Label();
	
	Boolean allowInsert = true;
	Boolean allowUpdate = true;
	Boolean allowDelete = true;
	Boolean allowFilter = true;
	Boolean allowPrint = true;
	
	Boolean showDeleteColumn = false;
	
	protected EventListenerList listenerList = new EventListenerList();

	public void setRmGrid(RmGrid rmGrid) {
		this.rmGrid = rmGrid;
	}
	public RmGrid getRmGrid() {
		return this.rmGrid;
	}
	
	public void setShowDeleteColumn(Boolean showDeleteColumn) {
		this.showDeleteColumn = showDeleteColumn;
	}
	public Boolean getShowDeleteColumn() {
		return this.showDeleteColumn;
	}
	
	public void setAllowInsert(Boolean allowInsert) {
		this.allowInsert = allowInsert;
	}
	public Boolean getAllowInsert() {
		return this.allowInsert;
	}
	
	public void setAllowUpdate(Boolean allowUpdate) {
		this.allowUpdate = allowUpdate;
	}
	public Boolean getAllowUpdate() {
		return this.allowUpdate;
	}
	
	public void setAllowDelete(Boolean allowDelete) {
		this.allowDelete = allowDelete;
	}
	public Boolean getAllowDelete() {
		return this.allowDelete;
	}
	
	public void setAllowFilter(Boolean allowFilter) {
		this.allowFilter = allowFilter;
	}
	public Boolean getAllowFilter() {
		return this.allowFilter;
	}
	
	public void setAllowPrint(Boolean allowPrint) {
		this.allowPrint = allowPrint;
	}
	public Boolean getAllowPrint() {
		return this.allowPrint;
	}
	
	public void setFormFilter(ControlForm formFilter) {
		this.formFilter = formFilter;
	}
	public ControlForm getFormFilter() {
		return this.formFilter;
	}
	
	public void setForm(ControlForm form) {
		this.form = form;
	}
	public ControlForm getForm() {
		return this.form;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getLimit() {
		return this.limit;
	}
	
	public void setShowButtonSet(boolean showButtonSet) {
		this.showButtonSet = showButtonSet;
	}
	public boolean getShowButtonSet() {
		return this.showButtonSet;
	}
	
	public void setShowButtonSetBottom(boolean showButtonSetBottom) {
		this.showButtonSetBottom = showButtonSetBottom;
	}
	public boolean getShowButtonSetBottom() {
		return this.showButtonSetBottom;
	}
	
	public void setGrid(Grid<SimpleRecord> grid) {
		this.grid = grid;
	}
	public Grid<SimpleRecord> getGrid() {
		return this.grid;
	}
	
	public void setTable(Table table) {
		this.table = table;
		this.getForm().setTable(table);
		this.getFormFilter().setTable(table);
	}
	public Table getTable() {
		return this.table;
	}
	
	public void setConentFieldList(List<ControlContentField> contentFieldList) {
		this.contentFieldList = contentFieldList;
	}
	public List<ControlContentField> getContentFieldList() {
		return this.contentFieldList;
	}

	public ControlContentField addField(String fieldName, String fieldTitle, Double width) {
		ControlContentField contentField = new ControlContentField();
		contentField.setFieldName(fieldName);
		contentField.setFieldTitle(fieldTitle);
		contentField.setWidth(width);
		
		this.getContentFieldList().add(contentField);
		
		return contentField;
	}

	public ControlContentField addField(String fieldName, String fieldTitle, Double minimumWidth, Integer expandRatio) {
		ControlContentField contentField = new ControlContentField();
		contentField.setFieldName(fieldName);
		contentField.setFieldTitle(fieldTitle);
		contentField.setMinimumWidth(minimumWidth);
		contentField.setExpandRatio(expandRatio);
		
		this.getContentFieldList().add(contentField);
		
		return contentField;
	}
	
	public RmGrid() {
		setResponsive(true);
		setMargin(false);
		setSpacing(false);
		//addStyleName("main-container");
		addStyleName("rmgrid");
	}
	
	public void updateContent() {
		setSizeFull();
		removeAllComponents();
		
		// Limpa os campos de filtragem
		//cleanFieldsFilter();
		
		if (this.getTable()!=null) {
			// Atualiza a tabela que o formulario estara utilizando 
			this.getForm().setTable(this.getTable());
			
			if (this.getShowButtonSet()) {
				//addComponent();
				HorizontalLayout header = new HorizontalLayout();
				header.addStyleName("header2");
				addComponent(header);
				{
					header.addComponent(this.getTopButtonSet());
				}
			}
			
			VerticalLayout body = new VerticalLayout();
			body.setMargin(false);
			body.setSpacing(false);
			body.setSizeFull();
			body.addStyleName("rmgrid");
			addComponent(body);
			setExpandRatio(body,1);
			{
				this.grid.setSizeFull();
				this.grid.setSelectionMode(SelectionMode.SINGLE);
				body.addComponent(this.grid);
			}

			if (this.getShowButtonSetBottom()) {
				HorizontalLayout footer = new HorizontalLayout();
				addComponent(footer);
				{
					btnFirst.addStyleName("button-blank");
					btnFirst.addStyleName("navigator-button");
					btnFirst.addStyleName("first-item");
					//btnFirst.setIcon(new ThemeResource("imagens/library/page-first.png"));
					btnFirst.setIcon(FontAwesome.ANGLE_DOUBLE_LEFT);
					footer.addComponent(btnFirst);
					btnFirst.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							getTable().setOffSet(0);
							updateRecords();
						}
					});
					
					btnPrior.addStyleName("button-blank");
					btnPrior.addStyleName("navigator-button");
					btnPrior.addStyleName("first-item");
					//btnPrior.setIcon(new ThemeResource("imagens/library/page-prev.png"));
					btnPrior.setIcon(FontAwesome.ANGLE_LEFT);
					footer.addComponent(btnPrior);
					btnPrior.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							getTable().setOffSet(getTable().getOffSet()-getTable().getLimit());
							updateRecords();
						}
					});
					
					/*
					Label lblPaginas = new Label("Página");
					lblPaginas.addStyleName("lblpaginas1");
					hl.addComponent(lblPaginas);
					
					RmTextField field = new RmTextField();
					hl.addComponent(field);
					
					Label lblPaginas2 = new Label("de x");
					lblPaginas2.addStyleName("lblpaginas2");
					hl.addComponent(lblPaginas2);
					*/
					
					btnNext.addStyleName("button-blank");
					btnNext.addStyleName("navigator-button");
					btnNext.addStyleName("first-item");
					//btnNext.setIcon(new ThemeResource("imagens/library/page-next.png"));
					btnNext.setIcon(FontAwesome.ANGLE_RIGHT);
					footer.addComponent(btnNext);
					btnNext.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							getTable().setOffSet(getTable().getOffSet()+getTable().getLimit());
							updateRecords();
						}
					});
					
					btnLast.addStyleName("button-blank");
					btnLast.addStyleName("navigator-button");
					btnLast.addStyleName("first-item");
					//btnLast.setIcon(new ThemeResource("imagens/library/page-last.png"));
					btnLast.setIcon(FontAwesome.ANGLE_DOUBLE_RIGHT);
					footer.addComponent(btnLast);
					btnLast.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							getTable().setOffSet((getTable().getTotalPages()-1)*getTable().getLimit());
							updateRecords();
						}
					});
					
					btnRefresh.addStyleName("button-blank");
					btnRefresh.addStyleName("navigator-button");
					btnRefresh.addStyleName("first-item");
					//btnRefresh.setIcon(new ThemeResource("imagens/library/refresh.png"));
					btnRefresh.setIcon(FontAwesome.REFRESH);
					footer.addComponent(btnRefresh);
					btnRefresh.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							updateRecords();
						}
					});
					
					footer.addComponent(lblRecordCount);
				}
			}
			
			// Atualiza o banco de dados
			String campos = "";
			for (ControlContentField contentField: this.getContentFieldList()) {
				if (!campos.isEmpty()) {
					campos+=",";
				}
				campos +=contentField.getFieldName();
			}
			
			this.getTable().select(campos, false);
			// Definindo o limite por paginas
			if (this.getLimit()!=0) {
				this.getTable().setLimit(this.getLimit());
			}

			// Variavel que vai ser usada para armazenar os campos que fazem parte da ordenacao da tabela
			List<GridSortOrder<SimpleRecord>> internalGridSortOrder = new ArrayList<GridSortOrder<SimpleRecord>>();
			
			// Variavel usada para ajudar a marcar no grid os campos que estao sendo usados na ordenacao
			String fieldsTableOrder = "";
			if ((this.getTable().getOrder()!=null) && (!this.getTable().getOrder().isEmpty())) {
				fieldsTableOrder = this.getTable().getOrder().trim() + ",";
			}

			this.getGrid().removeAllColumns();
			
			if (getShowDeleteColumn()) {
				Column<SimpleRecord, Button> btnColumn = null;
				btnColumn = this.getGrid().addComponentColumn(new ValueProvider<SimpleRecord, Button>() {
					@Override
					public Button apply(SimpleRecord source) {
						Button button = new Button();
						//button.setWidth("50px");
						button.addStyleName("remove-button");
						button.setIcon(new ThemeResource("imagens/library/delete.png"));
						button.setData(source);
						button.addClickListener(new ClickListener() {
							@Override
							public void buttonClick(ClickEvent event) {
								SimpleRecord simpleRecord = (SimpleRecord) event.getButton().getData();
								
								if (getTable().delete(simpleRecord.getString("uid"))) {
									RmFormWindow formWindow = new RmFormWindow();
									formWindow.setTitle("Atenção!");
									formWindow.setForm(getForm());
									formWindow.setWidth("500px");
									formWindow.setHeight("160px");
									formWindow.addMessage("Deseja excluir este registro?", MessageWindowType.QUESTION);
									RmFormButtonBase buttonBase = formWindow.addConfirmButton("Sim");
									buttonBase.setIcon(new ThemeResource("imagens/library/accept.png"));
									buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
										@Override
										public void onRmFormButtonClick(RmFormButtonClickEvent event) {
											if (getTable().execute()) {
												String uidLastRecord = getTable().getUidLastRecordProccessed();
												
								            	HashMap<String, SimpleRecord> simpleRecordMap = new HashMap<String, SimpleRecord>();
								            	List<SimpleRecord> simpleRecordList = new ArrayList<SimpleRecord>();

								            	boolean getRecord = false;
								            	SimpleRecord selectedSimpleRecord = null;
								            	SimpleRecord lastSimpleRecord = null;
								            	for (SimpleRecord simpleRecordItem: getTable().getSimpleRecordList()) {
								            		if (simpleRecordItem.getString("uid").equalsIgnoreCase(uidLastRecord)) {
								            			getRecord = true;
								            		}
								            		else {
								            			if (getRecord) {
								            				selectedSimpleRecord = simpleRecordItem;
								            				getRecord = false;
								            			}
								            			simpleRecordList.add(simpleRecordItem);
								            			simpleRecordMap.put(simpleRecordItem.getString("uid"), simpleRecordItem);
								            			lastSimpleRecord = simpleRecordItem;
								            		}
								            	}
								            	
								            	getTable().setSimpleRecordList(simpleRecordList);
								            	//getTable().setSimpleRecordMap(simpleRecordMap);

								            	// Adiciona o primeiro registro da proxima pagina
												if (getTable().getSimpleRecordList().size()<getTable().getLimit()) {
													Table table = getTable().getDatabase().loadTableByName(getTable().getTableName());
													table.select(getTable().getFieldsInSelect());
													
													if (getTable().getMasterTable()!=null) {
														table.setMasterTable(getTable().getMasterTable());
														if (getTable().getMasterFieldName()!=null) {
															table.setMasterFieldName(getTable().getMasterFieldName());
														}
														else {
															table.setMasterIndexName(getTable().getMasterIndexName());
															table.setRelationalship(getTable().getRelationalship());
														}
													}
													
													table.setFilters(getTable().getFilters());
													
													table.setOrder(getTable().getOrder());
													table.setOffSet(getTable().getOffSet()+getTable().getLimit()-1);
													table.setLimit(getTable().getLimit());
													table.loadData();
													if (!table.eof()) {
														getTable().getSimpleRecordList().add(table.getSimpleRecordList().get(0));
														//getTable().getSimpleRecordMap().put(table.getSimpleRecordList().get(0).getString("uid"), table.getSimpleRecordList().get(0));
													}
													table.close();
												}
												
												getGrid().setItems(getTable().getSimpleRecordList());
												
												if (selectedSimpleRecord!=null) {
													getGrid().select(selectedSimpleRecord);
												}
												else {
													if (lastSimpleRecord!=null) {
														getGrid().select(lastSimpleRecord);
													}
												}
												
												//updateRecords();
												event.getWindow().close();
											}
										}
									});
									
									buttonBase = formWindow.addCancelButton("Não");
									buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
										@Override
										public void onRmFormButtonClick(RmFormButtonClickEvent event) {
											RmFormWindow window = (RmFormWindow) event.getWindow();
											window.getForm().getTable().cancel();
											window.close();
										}
									});
									
									
									formWindow.show();
								}
							}
						});
						
						return button;
					}
				});
				
				btnColumn.setWidth(50);

				btnColumn.setStyleGenerator(new StyleGenerator<SimpleRecord>() {
					@Override
					public String apply(SimpleRecord item) {
						return "gridcell-text-align-center";
					}
				});
			}
			
			for (ControlContentField contentField: this.getContentFieldList()) {
				if (contentField.getVisible()) {
					Column<SimpleRecord, String> column = null;
					Integer columnNumber = this.getTable().getIndexOf(contentField.getFieldName()); 
					
					switch (columnNumber) {
					case 0:
						column = this.getGrid().addColumn(SimpleRecord::getField0);
						break;
					case 1:
						column = this.getGrid().addColumn(SimpleRecord::getField1);
						break;
					case 2:
						column = this.getGrid().addColumn(SimpleRecord::getField2);
						break;
					case 3:
						column = this.getGrid().addColumn(SimpleRecord::getField3);
						break;
					case 4:
						column = this.getGrid().addColumn(SimpleRecord::getField4);
						break;
					case 5:
						column = this.getGrid().addColumn(SimpleRecord::getField5);
						break;
					case 6:
						column = this.getGrid().addColumn(SimpleRecord::getField6);
						break;
					case 7:
						column = this.getGrid().addColumn(SimpleRecord::getField7);
						break;
					case 8:
						column = this.getGrid().addColumn(SimpleRecord::getField8);
						break;
					case 9:
						column = this.getGrid().addColumn(SimpleRecord::getField9);
						break;
					case 10:
						column = this.getGrid().addColumn(SimpleRecord::getField10);
						break;
					case 11:
						column = this.getGrid().addColumn(SimpleRecord::getField11);
						break;
					case 12:
						column = this.getGrid().addColumn(SimpleRecord::getField12);
						break;
					case 13:
						column = this.getGrid().addColumn(SimpleRecord::getField13);
						break;
					case 14:
						column = this.getGrid().addColumn(SimpleRecord::getField14);
						break;
					case 15:
						column = this.getGrid().addColumn(SimpleRecord::getField15);
						break;
					case 16:
						column = this.getGrid().addColumn(SimpleRecord::getField16);
						break;
					case 17:
						column = this.getGrid().addColumn(SimpleRecord::getField17);
						break;
					case 18:
						column = this.getGrid().addColumn(SimpleRecord::getField18);
						break;
					case 19:
						column = this.getGrid().addColumn(SimpleRecord::getField19);
						break;
					case 20:
						column = this.getGrid().addColumn(SimpleRecord::getField20);
						break;
					}

					column.setId(contentField.getFieldName());
					column.setCaption(contentField.getFieldTitle());
					
					if (contentField.getWidth()!=null) {
						column.setWidth(contentField.getWidth());
					}
					if (contentField.getMinimumWidth()!=null) {
						column.setMinimumWidth(contentField.getMinimumWidth());
					}
					if (contentField.getExpandRatio()!=null) {
						column.setExpandRatio(contentField.getExpandRatio());
					}

					//column.setSortProperty(contentField.getFieldName());

					// Atraves das linhas a baixo é possivel alterar o conteudo do titulo das colunas
		            //HeaderRow row = grid.getDefaultHeaderRow();
		            //row.getCell(column).setHtml(contentField.getFieldName());
		            //row.getCell(column).setComponent(new Button("teste"));
					
					Field field = getTable().fieldByName(contentField.getFieldName());
					if ((field.getFieldType()==FieldType.INTEGER) || (field.getFieldType()==FieldType.DOUBLE) || (field.getFieldType()==FieldType.FLOAT)) {
						column.setStyleGenerator(new StyleGenerator<SimpleRecord>() {
							@Override
							public String apply(SimpleRecord item) {
								return "gridcell-text-align-right";
							}
						});
					}
					else if (field.getFieldType()==FieldType.DATE) {
						column.setStyleGenerator(new StyleGenerator<SimpleRecord>() {
							@Override
							public String apply(SimpleRecord item) {
								return "gridcell-text-align-center";
							}
						});
					}
					else if (field.getFieldType()==FieldType.DATETIME) {
						column.setStyleGenerator(new StyleGenerator<SimpleRecord>() {
							@Override
							public String apply(SimpleRecord item) {
								return "gridcell-text-align-center";
							}
						});
					}
				
					/*				
					 this.getGrid().setCellStyleGenerator(new Grid.CellStyleGenerator() {
						@Override
						public String getStyle(CellReference cell) {
							Field field = getTable().fieldByName(getTable().getRealFieldName(cell.getPropertyId().toString()));
							if ((field.getFieldType()==FieldType.INTEGER) || (field.getFieldType()==FieldType.DOUBLE) || (field.getFieldType()==FieldType.FLOAT)) {
								return "gridcell-text-align-right";
							}
							else if (field.getFieldType()==FieldType.DATETIME) {
								return "gridcell-text-align-center";
							}
							return null;
						}
					});
					*/

					// O codigo a baixo é usado para forçar o grid a usar a ordem do registro da forma como ele veio do banco de dados
					// dessa forma a ordem sempre sera igual a forma como foi enviada do banco de dados evitando assim que o grid
					// ordene campos numericos usando regras de ordenacao string
					column.setComparator(new SerializableComparator<SimpleRecord>() {
						@Override
						public int compare(SimpleRecord arg0, SimpleRecord arg1) {
							
							return 0;
						}
					});

					
					// Caso ainda existam campos que foram utilizados para ordenar a tabela que ainda nao foram processados
					if (!fieldsTableOrder.isEmpty()) {
						// Pega o nome do proximo campo usado na ordenacao da tabela
						String fieldTableOrder = fieldsTableOrder.substring(0,fieldsTableOrder.indexOf(","));
						String fieldDirection = "asc";
						
						// Caso o campo possua espaços, entao o campo possui informacao sobre sua direcao na ordenacao
						if (fieldTableOrder.contains(" ")) {
							fieldDirection = fieldTableOrder.substring(fieldTableOrder.indexOf(" ")+1).trim();
							fieldTableOrder = fieldTableOrder.substring(0, fieldTableOrder.indexOf(" "));
						}
						
						if (fieldTableOrder.equalsIgnoreCase(contentField.getFieldName())) {
							GridSortOrder gridSortOrder = null;
							if (fieldDirection.equalsIgnoreCase("asc")) {
								gridSortOrder = new GridSortOrder(column, SortDirection.ASCENDING);
							}
							else {
								gridSortOrder = new GridSortOrder(column, SortDirection.DESCENDING);
							}
							
							internalGridSortOrder.add(gridSortOrder);
							
							fieldsTableOrder = fieldsTableOrder.substring(fieldsTableOrder.indexOf(",")+1);
						}
					}
					
					columnNumber++;
				}
			}
			
			// Definindo os campos que estao sendo usado na ordenacao da tabela
			grid.setSortOrder(internalGridSortOrder);
			
			// Evento disparado quando o usuario clicar o titulo de uma coluna, forcando o sistema solicitar uma nova query
			// reordenando os dados
			grid.addSortListener(new SortListener<GridSortOrder<SimpleRecord>>() {
				@Override
				public void sort(SortEvent<GridSortOrder<SimpleRecord>> event) {
					String ordem = "asc";
					if (event.getSortOrder().get(0).getDirection()==SortDirection.ASCENDING) {
						ordem = "asc";
					}
					else {
						ordem = "desc";
					}
					getTable().setOrder(event.getSortOrder().get(0).getSorted().getId() + " " + ordem);
					getTable().setOffSet(0);
					updateRecords();
				}
			});
			
			grid.addSelectionListener(new SelectionListener<SimpleRecord>() {
				@Override
				public void selectionChange(SelectionEvent<SimpleRecord> event) {
					if (event.getAllSelectedItems().size()>0) {
						SimpleRecord recordSelected = event.getAllSelectedItems().iterator().next();
						ControlGridSelectionEvent controlGridSelectionEvent = new ControlGridSelectionEvent(this);
						controlGridSelectionEvent.setRecordSelected(recordSelected);
						controlGridSelectionEvent.setTable(getTable());
						fireControlGridSelectionEvent(controlGridSelectionEvent);
					}
				}
			});
			
			this.updateRecords();
		}
	}
	
	public void updateRecords() {
		this.getTable().loadData();
		this.getGrid().setItems(this.getTable().getSimpleRecordList());
		//this.grid.setContainerDataSource(this.table.getBeanItemContainer());
		
		lblRecordCount.setCaption(this.getTable().getTotalRecordFromTable() + " registros encontrados.");
		
		this.enableAllButtons();

		// Tem registros
		if (this.getTable().getTotalRecordFromTable()>0) {
			// Caso esteja na primeira pagina
			if (this.getTable().getOffSet()==0) {
				this.disableButton(btnFirst);
				this.disableButton(btnPrior);
			}
			
			if (this.getTable().getOffSet()>=(this.getTable().getTotalPages()-1)*this.getTable().getLimit()) {
				this.disableButton(btnNext);
				this.disableButton(btnLast);
			}
		}
		else if (this.getTable().getTotalRecordFromTable()==0) {
			this.disableButton(btnFirst);
			this.disableButton(btnPrior);
			this.disableButton(btnNext);
			this.disableButton(btnLast);
		}
		
		ControlGridAfterUpdateRecordsEvent controlGridAfterUpdateRecords = new ControlGridAfterUpdateRecordsEvent(this);
		controlGridAfterUpdateRecords.setRmGrid(this);
		controlGridAfterUpdateRecords.setTable(getTable());
		fireControlGridAfterUpdateRecordsEvent(controlGridAfterUpdateRecords);
	}
	
	public void enableAllButtons() {
		this.enableButton(btnFirst);
		this.enableButton(btnPrior);
		this.enableButton(btnNext);
		this.enableButton(btnLast);
	}
	
	public void disableButton(Button button) {
		button.addStyleName("button-blank-disabled");
		button.setEnabled(false);
	}
	
	public void enableButton(Button button) {
		button.setEnabled(true);
		button.removeStyleName("button-blank-disabled");
	}
	
	public void executeFilter() {
		// Altera o status da tabela para filter sem apagar seus valores.
		if (getTable().filter(false)) {
			RmFormWindow formWindow = new RmFormWindow();
			getFormFilter().setWindow(formWindow);
			getFormFilter().setTable(getTable());
			formWindow.setTitle(getFormFilter().getTitle());
			formWindow.setForm(getFormFilter());
			formWindow.setWidth(getFormFilter().getWidth()+"px");
			formWindow.setHeight(getFormFilter().getHeight()+"px");
			formWindow.getBody().addComponent(getFormFilter().deploy());
			
			RmFormButtonBase buttonBase = formWindow.addCancelButton();
			buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
				@Override
				public void onRmFormButtonClick(RmFormButtonClickEvent event) {
					RmFormWindow window = (RmFormWindow) event.getWindow();
					// Abandona a filtragem restaurando o estado anterior da tabela
					window.getForm().getTable().cancel();
					window.close();
				}
			});

			RmFormButtonBase buttonLimpar = formWindow.addButton("Limpar pesquisa");
			buttonLimpar.setIcon(new ThemeResource("imagens/library/filter_del.png"));
			buttonLimpar.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
				@Override
				public void onRmFormButtonClick(RmFormButtonClickEvent event) {
					// Limpa os campos que sao usados para filtragem
					cleanFieldsFilter();
					
					getTable().setTableStatus(getTable().getLastTableStatus());
					getTable().setLastTableStatus(TableStatus.BROWSE);
					updateRecords();
					event.getWindow().close();
				}
			});
			
			RmFormButtonBase buttonPesquisa = formWindow.addButton("Pesquisar");
			buttonPesquisa.setIcon(new ThemeResource("imagens/library/find.png"));
			buttonPesquisa.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
				@Override
				public void onRmFormButtonClick(RmFormButtonClickEvent event) {
					if (getFormFilter().getTable().validate()) {
						// Limpa o conteudo definido na filtragem anterior
						getFormFilter().getTable().setFilters(new ArrayList<Filter>());
						getFormFilter().getTable().setOffSet(0);
						
						// Atualiza o conteudo da filtragem de acordo com os campos que foram preenchidos no formulario
						for (Field field: getTable().getFields()) {
							switch (field.getFieldType()) {
							case VARCHAR:
								if ((field.getString()!=null) && (field.getString().length()!=0)) {
									getTable().setFilter(field.getFieldName(), field.getString());
								}
								break;
							case INTEGER:
								if ((field.getInteger()!=null) && (field.getInteger()!=0)) {
									getTable().setFilter(field.getFieldName(), field.getInteger());
								}
								break;
							case DOUBLE:
								if ((field.getDouble()!=null) && (field.getDouble()!=0)) {
									getTable().setFilter(field.getFieldName(), field.getDouble());
								}
								break;
							case FLOAT:
								if ((field.getFloat()!=null) && (field.getFloat()!=0)) {
									getTable().setFilter(field.getFieldName(), field.getFloat());
								}
								break;
							case DATE:
								if ((field.getDate()!=null)) {
									getTable().setFilter(field.getFieldName(), field.getDate());
								}
								break;
							case DATETIME:
								if ((field.getDate()!=null)) {
									getTable().setFilter(field.getFieldName(), field.getDate());
								}
								break;
							case BOOLEAN:
								if (field.getBoolean()!=null) {
									getTable().setFilter(field.getFieldName(), field.getDate());
								}
								break;
							}
						}
						getTable().setTableStatus(getTable().getLastTableStatus());
						getTable().setLastTableStatus(TableStatus.BROWSE);
						updateRecords();
						event.getWindow().close();
					}
					else {
						System.out.println("não validou o conteudo do formulario");
					}
				}
			});
			formWindow.show();
		}
	}
	
	public HorizontalLayout getTopButtonSet() {
		buttonSet.addStyleName("buttonset");
		buttonSet.addStyleName("flex-direction-row");
		{
			if (this.getAllowInsert()) {
				Button btnInsert = new Button("Inclusão");
				btnInsert.addStyleName("button-blank");
				btnInsert.addStyleName("first-item");
				btnInsert.setIcon(new ThemeResource("imagens/library/insert.png"));
				buttonSet.addComponent(btnInsert);
				btnInsert.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if (getTable().insert()) {
							RmFormWindow formWindow = new RmFormWindow();
							getForm().setWindow(formWindow);
							getForm().setTable(getTable());
							formWindow.setRmGrid(getRmGrid());
							formWindow.setTitle(getForm().getTitle());
							formWindow.setForm(getForm());
							formWindow.setWidth(getForm().getWidth()+"px");
							formWindow.setHeight(getForm().getHeight()+"px");

							// Inclui botoes adicionaso ao formWindow
							for (RmFormButtonBase rmFormButtonBase: form.getRmFormButtonList()) {
								formWindow.getRmFormButtons().add(rmFormButtonBase);
							}
							
							// Nao atualizao o grid e nao atualiza os botoes tambem
							// deve atualizar somente o numero de registros no grid se necessario
							//updateRecords();
							getTable().setLoadingDataToForm(true);
							formWindow.getBody().addComponent(form.deploy());
							getTable().setLoadingDataToForm(false);

							// Caso a tabela possua o evento anexado, excluir ele para anexar um novo
							AfterPostInsertEventListener afterPostInsertEventListener = (AfterPostInsertEventListener) getTable().getListenerAttached(AfterPostInsertEventListener.class);
							if (afterPostInsertEventListener!=null) {
								getTable().removeAfterPostInsertEventListener(afterPostInsertEventListener);
							}
							
							// Caso ainda não tenha sido anexado o evento afterPostInsert a tabela, anexa ele uma unica vez
							if (!getTable().hasListenerAttached(AfterPostInsertEventListener.class)) {
								getTable().addAfterPostInsertEventListener(new AfterPostInsertEventListener() {
									@Override
									public void onAfterPostInsert(AfterPostInsertEvent event) {
										String uidLastRecord = event.getTable().getUidLastRecordProccessed();
										SimpleRecord simpleRecord = null;
										
										Table table = getTable().getDatabase().loadTableByName(getTable().getTableName());
										table.select(getTable().getFieldsInSelect());
										table.setFilter("uid", uidLastRecord);
										table.loadData();
										if (!table.eof()) {
											simpleRecord = table.getSimpleRecordList().get(0);
										}
										getTable().getSimpleRecordList().add(simpleRecord);
										
										getGrid().setItems(getTable().getSimpleRecordList());
										getGrid().select(simpleRecord);
										getGrid().scrollToEnd();
									}
								});
							}
							
							RmFormButtonBase buttonBase = formWindow.addCancelButton();
							buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
								@Override
								public void onRmFormButtonClick(RmFormButtonClickEvent event) {
									RmFormWindow window = (RmFormWindow) event.getWindow();
									window.getForm().getTable().cancel();
									window.close();
								}
							});
							
							buttonBase = formWindow.addSaveButton();
							buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
								@Override
								public void onRmFormButtonClick(RmFormButtonClickEvent event) {
									if (getForm().getTable().validate()) {
										if ((getTable().getTableStatus()!=TableStatus.INSERT) && (getTable().getTableStatus()!=TableStatus.UPDATE)) {
											getTable().update(getTable().getString("uid"), false);
										}
										
										if (getTable().execute()) {
											/*
											String uidLastRecord = getTable().getUidLastRecordProccessed();
											SimpleRecord simpleRecord = null;
											
											Table table = getTable().getDatabase().loadTableByName(getTable().getTableName());
											table.select(getTable().getFieldsInSelect());
											table.setFilter("uid", uidLastRecord);
											table.loadData();
											if (!table.eof()) {
												simpleRecord = table.getSimpleRecordList().get(0);
											}
											getTable().getSimpleRecordList().add(simpleRecord);
											//getTable().getSimpleRecordMap().put(uidLastRecord, simpleRecord);
											
											getGrid().setItems(getTable().getSimpleRecordList());
											getGrid().select(simpleRecord);
											getGrid().scrollToEnd();
											
											// Nao atualizao o grid e nao atualiza os botoes tambem
											// deve atualizar somente o numero de registros no grid se necessario
											//updateRecords();
											 */
											
											AfterSaveButtonClickEvent afterSaveButtonClickEvent = new AfterSaveButtonClickEvent(this);
											afterSaveButtonClickEvent.setControlForm(getForm());
											getForm().fireAfterSaveButtonClickEvent(afterSaveButtonClickEvent);
											
											event.getWindow().close();
										}
									}
									else {
										System.out.println("não validou o conteudo do formulario");
									}
								}
							});
							formWindow.show();
						}
					}
				});
			}

			if (this.getAllowUpdate()) {
				Button btnModificar = new Button("Modificar");
				btnModificar.addStyleName("button-blank");
				btnModificar.addStyleName("first-item");
				btnModificar.setIcon(new ThemeResource("imagens/library/update.png"));
				buttonSet.addComponent(btnModificar);
				btnModificar.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if (grid.getSelectedItems().size()==0) {
							Utils.ShowMessageWindow("Atenção!", "Necessário selecionar um registro!", 500, 180, MessageWindowType.ERROR);
						}
						else {
							SimpleRecord simpleRecord = grid.getSelectedItems().iterator().next();
							if (getTable().update(simpleRecord.getString("uid"), true)) {
								RmFormWindow formWindow = new RmFormWindow();
								getForm().setWindow(formWindow);
								getForm().setTable(getTable());
								formWindow.setRmGrid(getRmGrid());
								formWindow.setTitle(getForm().getTitle());
								formWindow.setForm(getForm());
 								formWindow.setWidth(getForm().getWidth()+"px");
								formWindow.setHeight(getForm().getHeight()+"px");
								
								// Inclui botoes adicionais ao formWindow
								for (RmFormButtonBase rmFormButtonBase: form.getRmFormButtonList()) {
									formWindow.getRmFormButtons().add(rmFormButtonBase);
								}
								
								// Antes de carregar os dados do banco de dados para o fumulario que ocorre durante o processo de deploy do formulario
								// sinaliza que esta fazendo esse procedimento, para que as validacoes nao sejam executadas durante esse processo
								// as validacoes devem ser executadas no momento em que o usuario alterar o conteudo de um campo e durante o post dos dados.
								getTable().setLoadingDataToForm(true);
								formWindow.getBody().addComponent(form.deploy());
								getTable().setLoadingDataToForm(false);

								// Caso a tabela possua o evento anexado, excluir ele para anexar um novo
								AfterPostUpdateEventListener afterPostUpdateEventListener = (AfterPostUpdateEventListener) getTable().getListenerAttached(AfterPostUpdateEventListener.class);
								if (afterPostUpdateEventListener!=null) {
									getTable().removeAfterPostUpdateEventListener(afterPostUpdateEventListener);
								}

								// Caso nao tenha sido anexado a tabela o evento afterPostUpdate, anexa ele uma unica vez
								if (!getTable().hasListenerAttached(AfterPostUpdateEventListener.class)) {
									getTable().addAfterPostUpdateEventListener(new AfterPostUpdateEventListener() {
										@Override
										public void onAfterPostUpdate(AfterPostUpdateEvent event) {
											String uidLastRecord = getTable().getUidLastRecordProccessed();
											SimpleRecord simpleRecord = null;
											
											Table table = getTable().getDatabase().loadTableByName(getTable().getTableName());
											table.select(getTable().getFieldsInSelect());
											table.setFilter("uid", uidLastRecord);
											table.loadData();
											if (!table.eof()) {
												simpleRecord = table.getSimpleRecordList().get(0);
											}
											
							            	//HashMap<String, SimpleRecord> simpleRecordMap = new HashMap<String, SimpleRecord>();
							            	List<SimpleRecord> simpleRecordList = new ArrayList<SimpleRecord>();
											
							            	for (SimpleRecord simpleRecordItem: getTable().getSimpleRecordList()) {
							            		if (simpleRecordItem.getString("uid").equalsIgnoreCase(uidLastRecord)) {
							            			simpleRecordList.add(simpleRecord);
							            			//simpleRecordMap.put(simpleRecord.getString("uid"), simpleRecord);
							            		}
							            		else {
							            			simpleRecordList.add(simpleRecordItem);
							            			//simpleRecordMap.put(simpleRecordItem.getString("uid"), simpleRecordItem);
							            		}
							            	}
							            	
							            	getTable().setSimpleRecordList(simpleRecordList);
							            	//getTable().setSimpleRecordMap(simpleRecordMap);
											
											getGrid().setItems(getTable().getSimpleRecordList());
											getGrid().select(simpleRecord);
										}
									});
								}
								
								RmFormButtonBase buttonBase = formWindow.addCancelButton();
								buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										RmFormWindow window = (RmFormWindow) event.getWindow();
										window.getForm().getTable().cancel();
										window.close();
									}
								});
								
								buttonBase = formWindow.addSaveButton();
								buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										if (getForm().getTable().validate()) {
											if ((getTable().getTableStatus()!=TableStatus.INSERT) && (getTable().getTableStatus()!=TableStatus.UPDATE)) {
												getTable().update(getTable().getString("uid"), false);
											}
											
											if ((getTable().getTableStatus()==TableStatus.INSERT) || (getTable().getTableStatus()==TableStatus.UPDATE)) {
												if (getTable().execute()) {
													/*
													String uidLastRecord = getTable().getUidLastRecordProccessed();
													SimpleRecord simpleRecord = null;
													
													Table table = getTable().getDatabase().loadTableByName(getTable().getTableName());
													table.select(getTable().getFieldsInSelect());
													table.setFilter("uid", uidLastRecord);
													table.loadData();
													if (!table.eof()) {
														simpleRecord = table.getSimpleRecordList().get(0);
													}
													
									            	//HashMap<String, SimpleRecord> simpleRecordMap = new HashMap<String, SimpleRecord>();
									            	List<SimpleRecord> simpleRecordList = new ArrayList<SimpleRecord>();
													
									            	for (SimpleRecord simpleRecordItem: getTable().getSimpleRecordList()) {
									            		if (simpleRecordItem.getString("uid").equalsIgnoreCase(uidLastRecord)) {
									            			simpleRecordList.add(simpleRecord);
									            			//simpleRecordMap.put(simpleRecord.getString("uid"), simpleRecord);
									            		}
									            		else {
									            			simpleRecordList.add(simpleRecordItem);
									            			//simpleRecordMap.put(simpleRecordItem.getString("uid"), simpleRecordItem);
									            		}
									            	}
									            	
									            	getTable().setSimpleRecordList(simpleRecordList);
									            	//getTable().setSimpleRecordMap(simpleRecordMap);
													
													getGrid().setItems(getTable().getSimpleRecordList());
													getGrid().select(simpleRecord);
													*/
													
													//updateRecords();
													
													AfterSaveButtonClickEvent afterSaveButtonClickEvent = new AfterSaveButtonClickEvent(this);
													afterSaveButtonClickEvent.setControlForm(getForm());
													getForm().fireAfterSaveButtonClickEvent(afterSaveButtonClickEvent);
													
													event.getWindow().close();
												}
											}
											else {
												event.getWindow().close();
											}
										}
									}
								});

								formWindow.show();
							}
						}

						/*
						if (grid.getSelectedRow()==null) {
							Utils.ShowMessageWindow("Atenção!", "Necessário selecionar um registro!", 500, 180, MessageWindowType.ERROR);
						}
						else {
							ModelBase modelBase = (ModelBase) grid.getSelectedRow();
							if (getTable().update(modelBase.getUid())) {
								RmFormWindow formWindow = new RmFormWindow();
								getForm().setWindow(formWindow);
								getForm().setTable(getTable());
								formWindow.setTitle(getForm().getTitle());
								formWindow.setForm(getForm());
								formWindow.setWidth(getForm().getWidth()+"px");
								formWindow.setHeight(getForm().getHeight()+"px");
								formWindow.getBody().addComponent(form.deploy());
								
								RmFormButtonBase buttonBase = formWindow.addCancelButton();
								buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										RmFormWindow window = (RmFormWindow) event.getWindow();
										window.getForm().getTable().cancel();
										window.close();
									}
								});
								
								
								buttonBase = formWindow.addSaveButton();
								buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										if (getForm().getTable().validate()) {
											if (getTable().execute()) {
												updateRecords();
												event.getWindow().close();
											}
										}
									}
								});
								formWindow.show();
							}
						}
						*/
					}
				});
			}
			
			if (this.getAllowDelete()) {
				Button btnExcluir = new Button("Excluir");
				btnExcluir.addStyleName("button-blank");
				btnExcluir.addStyleName("first-item");
				btnExcluir.setIcon(new ThemeResource("imagens/library/delete.png"));
				buttonSet.addComponent(btnExcluir);
				btnExcluir.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if (grid.getSelectedItems().size()==0) {
							Utils.ShowMessageWindow("Atenção!", "Necessário selecionar um registro!", 500, 180, MessageWindowType.ERROR);
						}
						else {
							SimpleRecord simpleRecord = grid.getSelectedItems().iterator().next();
							if (getTable().delete(simpleRecord.getString("uid"))) {
								RmFormWindow formWindow = new RmFormWindow();
								formWindow.setTitle("Atenção!");
								formWindow.setForm(getForm());
								formWindow.setWidth("500px");
								formWindow.setHeight("160px");
								formWindow.addMessage("Deseja excluir este registro?", MessageWindowType.QUESTION);
								RmFormButtonBase buttonBase = formWindow.addConfirmButton("Sim");
								buttonBase.setIcon(new ThemeResource("imagens/library/accept.png"));
								buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										if (getTable().execute()) {
											String uidLastRecord = getTable().getUidLastRecordProccessed();
											
							            	HashMap<String, SimpleRecord> simpleRecordMap = new HashMap<String, SimpleRecord>();
							            	List<SimpleRecord> simpleRecordList = new ArrayList<SimpleRecord>();

							            	boolean getRecord = false;
							            	SimpleRecord selectedSimpleRecord = null;
							            	SimpleRecord lastSimpleRecord = null;
							            	for (SimpleRecord simpleRecordItem: getTable().getSimpleRecordList()) {
							            		if (simpleRecordItem.getString("uid").equalsIgnoreCase(uidLastRecord)) {
							            			getRecord = true;
							            		}
							            		else {
							            			if (getRecord) {
							            				selectedSimpleRecord = simpleRecordItem;
							            				getRecord = false;
							            			}
							            			simpleRecordList.add(simpleRecordItem);
							            			simpleRecordMap.put(simpleRecordItem.getString("uid"), simpleRecordItem);
							            			lastSimpleRecord = simpleRecordItem;
							            		}
							            	}
							            	
							            	getTable().setSimpleRecordList(simpleRecordList);
							            	//getTable().setSimpleRecordMap(simpleRecordMap);

							            	// Adiciona o primeiro registro da proxima pagina
											if (getTable().getSimpleRecordList().size()<getTable().getLimit()) {
												Table table = getTable().getDatabase().loadTableByName(getTable().getTableName());
												table.select(getTable().getFieldsInSelect());
												
												if (getTable().getMasterTable()!=null) {
													table.setMasterTable(getTable().getMasterTable());
													if (getTable().getMasterFieldName()!=null) {
														table.setMasterFieldName(getTable().getMasterFieldName());
													}
													else {
														table.setMasterIndexName(getTable().getMasterIndexName());
														table.setRelationalship(getTable().getRelationalship());
													}
												}
												
												table.setFilters(getTable().getFilters());
												
												table.setOrder(getTable().getOrder());
												table.setOffSet(getTable().getOffSet()+getTable().getLimit()-1);
												table.setLimit(getTable().getLimit());
												table.loadData();
												if (!table.eof()) {
													getTable().getSimpleRecordList().add(table.getSimpleRecordList().get(0));
													//getTable().getSimpleRecordMap().put(table.getSimpleRecordList().get(0).getString("uid"), table.getSimpleRecordList().get(0));
												}
												table.close();
											}
											
											getGrid().setItems(getTable().getSimpleRecordList());
											
											if (selectedSimpleRecord!=null) {
												getGrid().select(selectedSimpleRecord);
											}
											else {
												if (lastSimpleRecord!=null) {
													getGrid().select(lastSimpleRecord);
												}
											}
											
											//updateRecords();
											event.getWindow().close();
										}
									}
								});
								
								buttonBase = formWindow.addCancelButton("Não");
								buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
									@Override
									public void onRmFormButtonClick(RmFormButtonClickEvent event) {
										RmFormWindow window = (RmFormWindow) event.getWindow();
										window.getForm().getTable().cancel();
										window.close();
									}
								});
								
								
								formWindow.show();
							}
						}
					}
				});
			}

			if (this.getAllowFilter()) {
				Button btnPesquisar = new Button("Pesquisar");
				btnPesquisar.addStyleName("button-blank");
				btnPesquisar.addStyleName("first-item");
				btnPesquisar.setIcon(new ThemeResource("imagens/library/find.png"));
				buttonSet.addComponent(btnPesquisar);
				btnPesquisar.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						executeFilter();
					}
				});
			}
			
			if (this.getAllowPrint()) {
				Button btnImprimir = new Button("Imprimir");
				btnImprimir.addStyleName("button-blank");
				btnImprimir.addStyleName("first-item");
				btnImprimir.setIcon(new ThemeResource("imagens/library/print.png"));
				buttonSet.addComponent(btnImprimir);
			}
		}
		
		return this.buttonSet;
	}
	
	public void cleanFieldsFilter() {
		// Guarda o status atual da tabela
		TableStatus tableStatus = getTable().getTableStatus();
		
		// Sinalisa que vai trabalhar com os campos de filtragem
		getTable().setTableStatus(TableStatus.FILTER);
		
		// Limpa o conteudo dos campos
		for (Field field: getTable().getFields()) {
			switch (field.getFieldType()) {
			case VARCHAR:
				if ((field.getString()!=null) && (field.getString().length()!=0)) {
					field.setString("");
				}
				break;
			case INTEGER:
				if ((field.getInteger()!=null) && (field.getInteger()!=0)) {
					field.setInteger(null);
				}
				break;
			case DOUBLE:
				if ((field.getDouble()!=null) && (field.getDouble()!=0)) {
					field.setDouble(null);
				}
				break;
			case FLOAT:
				if ((field.getFloat()!=null) && (field.getFloat()!=0)) {
					field.setFloat(null);
				}
				break;
			case DATE:
				if ((field.getDate()!=null)) {
					field.setDate(null);
				}
				break;
			case DATETIME:
				if ((field.getDate()!=null)) {
					field.setDate(null);
				}
				break;
			case BOOLEAN:
				if (field.getBoolean()!=null) {
					field.setBoolean(null);
				}
				break;
			}
		}
		getTable().setFilters(new ArrayList<Filter>());

		// Restaura o status da tabela antes do processo
		getTable().setTableStatus(tableStatus);
	}
	
	/**
	 * Metodo usado para selecionar o primeiro registro que estiver sendo apresentado no grid.
	 */
	public void selectFirstRow() {
		if (this.getTable().getSimpleRecordList().size()>0) {
			grid.select(this.getTable().getSimpleRecordList().get(0));
		}
	}
	
	/**
	 * Metodo utilizado para selecionar o primeiro registro que tenha os campos com conteúdo igual ao 
	 * conteudo passado em simpleRecord
	 * @param simpleRecord
	 */
	public void selectRow(SimpleRecord contentRow) {
		// Caso exista uma filtragem definida para selecionar um registro
		if (contentRow.getFields().size()>0) {
			// Procura por todos registros que estao sendo apresentados no grid um que possua os mesmos conteudos
			// de seus campos aos presentes em contentRow
			for (SimpleRecord simpleRecord: this.getTable().getSimpleRecordList()) {
				// Caso os campos presentes em selectRow tenham conteudo igual aos que estao na linha, seleciona a linha 
				boolean selectRow = true;
	
				// tem que trocar o for por while para abandonar a verificacao caso de problema logo no primeiro campo
				for (SimpleField simpleField: contentRow.getFields()) {
					Field field = getTable().fieldByName(simpleField.getFieldName());
					
					if (field.getFieldType()==FieldType.VARCHAR) {
						if (!simpleRecord.getString(field.getFieldName()).equalsIgnoreCase(simpleField.getString())) {
							selectRow = false;
							break;
						}
					}
					else if (field.getFieldType()==FieldType.INTEGER) {
						if (simpleRecord.getInteger(field.getFieldName())!=simpleField.getInteger()) {
							selectRow = false;
							break;
						}
					}
				}
				
				if (selectRow) {
					grid.select(simpleRecord);
					break;
				}
			}
		}
	}

	/**
	 * Evento disparado quando um registro é selecionado no grid
	 * @param listener
	 */
	public void addControlGridSelectionEventListener(ControlGridSelectionEventListener listener) {
		listenerList.add(ControlGridSelectionEventListener.class, listener);
	}

	public void removeControlGridSelectionEventListener(ControlGridSelectionEventListener listener) {
		listenerList.remove(ControlGridSelectionEventListener.class, listener);
	}

	void fireControlGridSelectionEvent(ControlGridSelectionEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ControlGridSelectionEventListener.class) {
				((ControlGridSelectionEventListener) listeners[i + 1]).onSelection(event);
			}
		}
	}
	
	/**
	 * Evento disparado apos a atualizacao dos registros que estao sendo apresentados no grid
	 * @param listener
	 */
	public void addControlGridAfterUpdateRecordsEventListener(ControlGridAfterUpdateRecordsEventListener listener) {
		listenerList.add(ControlGridAfterUpdateRecordsEventListener.class, listener);
	}

	public void removeControlGridAfterUpdateRecordsEventListener(ControlGridAfterUpdateRecordsEventListener listener) {
		listenerList.remove(ControlGridAfterUpdateRecordsEventListener.class, listener);
	}

	void fireControlGridAfterUpdateRecordsEvent(ControlGridAfterUpdateRecordsEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ControlGridAfterUpdateRecordsEventListener.class) {
				((ControlGridAfterUpdateRecordsEventListener) listeners[i + 1]).onAfterUpdateRecords(event);
			}
		}
	}
}
