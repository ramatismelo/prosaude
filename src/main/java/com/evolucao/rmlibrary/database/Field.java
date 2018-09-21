package com.evolucao.rmlibrary.database;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.enumerators.AllowLike;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.MessageWindowType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterExistenceCheckEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent.AfterValidateOnEditEventListener;
import com.evolucao.rmlibrary.database.events.BeforeForeingSearchEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldEvent.ValidateFieldEventListener;
import com.evolucao.rmlibrary.database.events.ValidateFieldInFilterEvent;
import com.evolucao.rmlibrary.database.events.ValidateFieldInFilterEvent.ValidateFieldInFilterEventListener;
import com.evolucao.rmlibrary.ui.controller.enumerators.CapitalizationType;
import com.evolucao.rmlibrary.ui.fields.RmFieldBase;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Grid;

//public class Field implements HasFieldBlurHandlers {
public class Field implements Serializable {
	private String alias = null;
	private String ffieldName = null;
	private FieldType ffieldType = null;
	private Integer ffieldSize = 0;
	private Boolean fautoIncrement = false;
	
	// Usado para indicar que o campo teve seu conteudo alterado pelo usuario, e por esse motivo, deve ser incluido nas querys de inclusao e atualizacao
	private Boolean fisDefined = false;

	/*
	private String fstring = null;
	private Integer integer = null;
	private Double fdouble = null;
	private Float ffloat = null;
	private Date fdate = null;
	private Boolean fboolean = null;
	*/
	
	// ValueField parece ser usado para armazenar o valor do field caso ele nao esteja anexado a uma tabela
	// o motivo desse procedimento nesse momento nao parece muito claro, mas provavelmente esse campo pode ser removido
	private SimpleField valueField = new SimpleField();
	private SimpleField valueInsertUpdate = new SimpleField();
	private SimpleField valueFilter = new SimpleField();
	private SimpleField oldValue = new SimpleField();
	
	//private boolean allowBlank = true;
	private AllowLike allowLikeIn = AllowLike.BOTH;
	boolean caseSensitive = false;
	private String expression = null;
	private ComboBox comboBox = null;
	String valueOfInternalSearch = null;
	private String toolTip = null;
	
	private ForeingSearch foreingSearch = null;
	private InternalSearch internalSearch = null;
	private ExistenceCheck existenceCheck = null;
	private ComboBoxAdvanced comboBoxAdvanced = null;
	
	private String mask = null;
	//private Boolean hasBlur = false;

	private boolean isFocus = false;
	
	private List<ChaveValor> comboBoxItems = null;
	private String comboBoxValue = null;
	
	private boolean readOnly = false;
	private boolean readOnlyInsert = false;
	private boolean readOnlyUpdate = false;
	private boolean readOnlyFilter = false;
	
	private boolean paddingLeftZeros = false;
	
	private boolean required = false;
	private boolean requiredInFilter = false;
	private boolean password = false;
	
	CapitalizationType capitalizationType = CapitalizationType.UPPERCASE;
	
	protected EventListenerList listenerList = new EventListenerList();
	private Table table = null;
	
	RmFieldBase rmFieldBase = null;

	public void setCapitalizationType(CapitalizationType capitalizationType) {
		this.capitalizationType = capitalizationType;
	}
	public CapitalizationType getCapitalizationType() {
		return this.capitalizationType;
	}
	
	public void setValueOfInternalSearch(String valueOfInternalSearch) {
		this.valueOfInternalSearch = valueOfInternalSearch;
	}
	public String getValueOfInternalSearch() {
		return this.valueOfInternalSearch;
	}
	
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	public boolean getCaseSensitive() {
		return this.caseSensitive;
	}
	
	public void setValueField(SimpleField valueField) {
		this.valueField = valueField;
	}
	public SimpleField getValueField() {
		return this.valueField;
	}
	
	public void setValueInsertUpdate(SimpleField valueInsertUpdate) {
		this.valueInsertUpdate = valueInsertUpdate;
	}
	public SimpleField getValueInsertUpdate() {
		return this.valueInsertUpdate;
	}
	
	public void setValueFilter(SimpleField valueFilter) {
		this.valueFilter = valueFilter;
	}
	public SimpleField getValueFilter() {
		return this.valueFilter;
	}
	
	public void setRmFieldBase(RmFieldBase rmFieldBase) {
		this.rmFieldBase = rmFieldBase;
		if (this.rmFieldBase!=null) {
			if ((this.getFieldType()==FieldType.DATE) || (this.getFieldType()==FieldType.DATETIME)) {
				rmFieldBase.setDate(this.getDate());
			}
			else {
				rmFieldBase.setValue(getValueAsString());
			}
		}
	}
	public RmFieldBase getRmFieldBase() {
		return this.rmFieldBase;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public void setPassword(boolean password) {
		this.password = password;
	}
	public boolean getPassword() {
		return this.password;
	}
	
	public void setRequired(boolean required) {
		this.required=required;
	}
	public boolean getRequired() {
		return this.required;
	}
	
	public void setRequiredInFilter(boolean requiredInFilter) {
		this.requiredInFilter = requiredInFilter;
	}
	public boolean getRequiredInFilter() {
		return this.requiredInFilter;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAlias() {
		return this.alias;
	}
	
	public void setPaddingLeftZeros(boolean paddingLeftZeros) {
		this.paddingLeftZeros = paddingLeftZeros;
	}
	public boolean getPaddingLeftZeros() {
		return this.paddingLeftZeros;
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		this.readOnlyInsert = readOnly;
		this.readOnlyUpdate = readOnly;
	}
	public boolean getReadOnly() {
		return this.readOnly;
	}
	
	public void setReadOnlyInsert(boolean readOnlyInsert) {
		this.readOnlyInsert = readOnlyInsert;
	}
	public boolean getReadOnlyInsert() { 
		return this.readOnlyInsert;
	}
	
	public void setReadOnlyUpdate(boolean readOnlyUpdate) {
		this.readOnlyUpdate = readOnlyUpdate;
	}
	public boolean getReadOnlyUpdate() {
		return this.readOnlyUpdate;
	}
	
	public void setReadOnlyFilter(boolean readOnlyFileter) {
		this.readOnlyFilter = readOnlyFileter;
	}
	public boolean getReadOnlyFilter() {
		return this.readOnlyFilter;
	}
	
	public void SetIsFocus(boolean isFocus) {
		this.isFocus = isFocus;
	}
	public boolean isFocus() {
		return this.isFocus;
	}
	
	public void setComboBoxItems(List<ChaveValor> comboBoxItems) {
		this.comboBoxItems = comboBoxItems;
	}
	public List<ChaveValor> getComboBoxItems() {
		return this.comboBoxItems;
	}
	public void addComboBoxItem(String uid, String valor) {
		if (this.comboBoxItems==null) {
			this.comboBoxItems = new ArrayList<ChaveValor>();
		}
		this.getComboBoxItems().add(new ChaveValor(uid, valor));
	}
	
	/*
	 * Caso exista um comboBoxInterno na tabela os valores podem ser apresentados no campo
	 * que estiver configurado o comboBoxValue 
	 */
	public void setComboBoxValue(String comboBoxFieldName) {
		this.comboBoxValue = comboBoxFieldName;
	}
	public String getComboBoxValue() {
		return this.comboBoxValue;
	}
	
	//public Boolean getHasBlur() {
	//	return this.hasBlur;
	//}
	
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getMask() {
		String retorno = this.mask;
		if (retorno==null) {
			retorno = "";
		}
		return retorno;
	}

	public ForeingSearch addForeingSearch() {
		ForeingSearch foreginSearch = new ForeingSearch();
		this.setForeingSearch(foreginSearch);
		return foreginSearch;
	}
	
	public ForeingSearch addForeingSearch(String targetTableName, String targetIndexName, String relationship, String returnFieldName) {
		return this.addForeingSearch(targetTableName, targetIndexName, relationship, returnFieldName, "750px", "600px");
	}
	
	public ForeingSearch addForeingSearch(String targetTableName, String targetIndexName, String relationship, String returnFieldName, String width, String height) {
		ForeingSearch foreingSearch = new ForeingSearch();
		foreingSearch.setTargetTableName(targetTableName);
		foreingSearch.setTargetIndexName(targetIndexName);
		foreingSearch.setRelationship(relationship);
		foreingSearch.setReturnFieldName(returnFieldName);
		foreingSearch.setWidth(width);
		foreingSearch.setHeight(height);
		this.setForeingSearch(foreingSearch);
		
		return foreingSearch;
	}
	public void setForeingSearch(ForeingSearch foreingSearch) {
		this.foreingSearch = foreingSearch;
	}
	public ForeingSearch getForeingSearch() {
		return this.foreingSearch;
	}

	public ComboBoxAdvanced addComboBoxAdvanced(String targetTableName) {
		ComboBoxAdvanced comboBoxAdvanced = new ComboBoxAdvanced();
		comboBoxAdvanced.setTargetTableName(targetTableName);
		this.setComboBoxAdvanced(comboBoxAdvanced);
		return comboBoxAdvanced;
	}
	
	public void setComboBoxAdvanced(ComboBoxAdvanced comboBoxAdvanced) {
		this.comboBoxAdvanced = comboBoxAdvanced;
	}
	public ComboBoxAdvanced getComboBoxAdvanced() {
		return this.comboBoxAdvanced;
	}
	
	public InternalSearch addInternalSearch() {
		InternalSearch internalSearch = new InternalSearch();
		this.setInternalSearch(internalSearch);
		return internalSearch;
	}
	
	public void setInternalSearch(InternalSearch internalSearch) {
		this.internalSearch = internalSearch;
	}
	public InternalSearch getInternalSearch() { 
		return this.internalSearch;
	}
	
	public ExistenceCheck addExistenceCheck(String targetTableName, String targetIndexName, String relationship, String errorMessage) {
		ExistenceCheck existenceCheck = new ExistenceCheck();
		existenceCheck.setTargetTableName(targetTableName);
		existenceCheck.setTargetIndexName(targetIndexName);
		existenceCheck.setRelationship(relationship);
		existenceCheck.setErrorMessage(errorMessage);
		this.existenceCheck = existenceCheck;
		return existenceCheck;
	}
	public void setExistenceCheck(ExistenceCheck existenceCheck) {
		this.existenceCheck = existenceCheck;
	}
	public ExistenceCheck getExistenceCheck() {
		return this.existenceCheck;
	}
	
	/*
	 * 
	 */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	public String getToolTip() {
		return this.toolTip;
	}
	
	/*
	 * 
	 */
	public ComboBox addComboBox(String targetTableName, String displayFieldName, String tagFieldName, String resultFieldName) {
		this.comboBox = new ComboBox(targetTableName, displayFieldName, tagFieldName, resultFieldName);
		return this.comboBox;
	}
	public ComboBox setComboBox(ComboBox comboBox) {
		this.comboBox = comboBox;
		return this.comboBox;
	}
	public ComboBox getComboBox() {
		return this.comboBox;
	}
	/*
	 * 
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getExpression() {
		return this.expression;
	}
	
	// **************************
	// AllowLike usado para indicar se um campo do tipo String permite o uso do Like durante as pesquisas, 
	// se for permitido o uso do like informa se o parametro % pode ser usado no inicio, final ou em ambos 
	// Para campos string em uma pesquisa where informa se pode usar o like com % no inicio no final ou em ambas posicoes
	// begin = field like "%" + conteudo; end = field
	public void setAllowLike(AllowLike allowLikeIn) {
		this.allowLikeIn = allowLikeIn;
	}
	public AllowLike getAllowLike() {
		return this.allowLikeIn;
	}
	//***************************

	public void cleanValues() {
		if (this.getTable()!=null) {
			if (this.getTable().getTableStatus()==TableStatus.BROWSE) {
				this.valueField = new SimpleField();
				this.valueInsertUpdate = new SimpleField();
			}
			else if ((this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
				this.valueInsertUpdate = new SimpleField();
			}
			//else if (this.getTable().getTableStatus()==TableStatus.FILTER) {
			//	this.valueFilter = new SimpleField();
			//}
		}

		/*
		this.fstring = null;
		this.integer = null;
		this.fdouble = null;
		this.ffloat = null;
		this.fdate = null;
		this.fboolean = null;
		*/
		this.fisDefined = false;
	}
	
	//*************
	public void setString(String value) {
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				this.oldValue.setString(value);
			}
			else {
				// Caso a tabela esteja em filtragem, armazena o conteudo em filtro, 
				// caso a tabela esteja em qualquer outro status, armazena nos outros lugares
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					this.valueFilter.setString(value);
				}
				else {
					this.valueField.setString(value);
					this.valueInsertUpdate.setString(value);
				}

				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					this.valueField.setString(value);
					this.valueInsertUpdate.setString(value);
				}
				else {
					this.valueFilter.setString(value);
				}
				*/
				
				// Caso exista um RmFieldBase anexado ao field, atualiza seu conteudo se necessario
				if (getRmFieldBase()!=null) {
					if ((getRmFieldBase().getString()==null) && (value!=null)) {
						getRmFieldBase().setValue(value);
					}
					if (getRmFieldBase().getString()!=null) {
						if (value==null) {
							getRmFieldBase().setString(null);
						}
						else {
							if (!getRmFieldBase().getString().equals(value)) {
								getRmFieldBase().setValue(value);
							}
						}
					}
				}
				
				// Indica que foi feita uma atribuição de conteudor para o campo
				this.setIsDefined(true);
			}
		}
		else {
			this.valueField.setString(value);
			
			// Indica que foi feita uma atribuição de conteudor para o campo
			this.setIsDefined(true);
		}
	}
	
	public String getString() {
		String retorno = null;
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				retorno = this.oldValue.getString();
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getString();
				}
				else {
					retorno = this.valueInsertUpdate.getString();
				}
				
				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					retorno = this.valueInsertUpdate.getString();
				}
				else if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getString();
				}
				*/
			}
		}
		else {
			retorno = this.valueField.getString();
		}
		
		return retorno;
	}
	
	/*
	 * 
	 */
	
	public void setInteger(Integer value) {
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				this.oldValue.setInteger(value);
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					this.valueFilter.setInteger(value);
				}
				else {
					this.valueInsertUpdate.setInteger(value);
					this.valueField.setInteger(value);
				}

				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					this.valueInsertUpdate.setInteger(value);
					this.valueField.setInteger(value);
				}
				else {
					this.valueFilter.setInteger(value);
				}
				*/
				
				// Caso exista um RmFieldBase anexado ao field, atualiza seu conteudo se necessario
				if (getRmFieldBase()!=null) {
					if ((getRmFieldBase().getInteger()==null) && (value!=null)) {
						getRmFieldBase().setValue(value);
					}
					if (getRmFieldBase().getInteger()!=null) {
						if (value==null) {
							getRmFieldBase().setInteger(null);
						}
						else {
							if (!getRmFieldBase().getInteger().equals(value)) {
								getRmFieldBase().setValue(value);
							}
						}
					}
				}
				
				// Indica que foi feita uma atribuição de conteudor para o campo
				this.setIsDefined(true);
			}
		}
		else {
			this.valueField.setInteger(value);
			
			// Indica que foi feita uma atribuição de conteudor para o campo
			this.setIsDefined(true);
		}
	}
	public Integer getInteger() {
		Integer retorno = null;
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				retorno = this.oldValue.getInteger();
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getInteger();
				}
				else {
					retorno = this.valueInsertUpdate.getInteger();
				}
				
				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					retorno = this.valueInsertUpdate.getInteger();
				}
				else if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getInteger();
				}
				*/
			}
		}
		else {
			retorno = this.valueField.getInteger();
		}
		
		return retorno;
	}
	
	/*
	 * 
	 */
	
	public void setDouble(Double value) {
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				this.oldValue.setDouble(value);
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					this.valueFilter.setDouble(value);
				}
				else {
					this.valueInsertUpdate.setDouble(value);
					this.valueField.setDouble(value);
				}
				
				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					this.valueInsertUpdate.setDouble(value);
					this.valueField.setDouble(value);
				}
				else {
					this.valueFilter.setDouble(value);
				}
				*/
				
				// Caso exista um RmFieldBase anexado ao field, atualiza seu conteudo se necessario
				if (getRmFieldBase()!=null) {
					if ((getRmFieldBase().getDouble()==null) && (value!=null)) {
						getRmFieldBase().setValue(value);
					}
					if (getRmFieldBase().getDouble()!=null) {
						if (value==null) {
							getRmFieldBase().setDouble(null);
						}
						else {
							if (!getRmFieldBase().getDouble().equals(value)) {
								getRmFieldBase().setValue(value);
							}
						}
					}
				}
				
				// Indica que foi feita uma atribuição de conteudor para o campo
				this.setIsDefined(true);
			}
		}
		else {
			this.valueField.setDouble(value);
			
			// Indica que foi feita uma atribuição de conteudor para o campo
			this.setIsDefined(true);
		}
	}
	public Double getDouble() {
		Double retorno = null;
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				retorno = this.oldValue.getDouble();
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getDouble();
				}
				else {
					retorno = this.valueInsertUpdate.getDouble();
				}
				
				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					retorno = this.valueInsertUpdate.getDouble();
				}
				else {
					retorno = this.valueFilter.getDouble();
				}
				*/
			}
		}
		else {
			retorno = this.valueField.getDouble();
		}
		
		return retorno;
	}
	
	/*
	 * 
	 */
	
	public void setFloat(Float value) {
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				this.oldValue.setFloat(value);
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					this.valueFilter.setFloat(value);
				}
				else {
					this.valueField.setFloat(value);
					this.valueInsertUpdate.setFloat(value);
				}
				
				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					this.valueField.setFloat(value);
					this.valueInsertUpdate.setFloat(value);
				}
				else {
					this.valueFilter.setFloat(value);
				}
				*/
				
				// Caso exista um RmFieldBase anexado ao field, atualiza seu conteudo se necessario
				if (getRmFieldBase()!=null) {
					if ((getRmFieldBase().getFloat()==null) && (value!=null)) {
						getRmFieldBase().setValue(value);
					}
					if (getRmFieldBase().getFloat()!=null) {
						if (value==null) {
							getRmFieldBase().setFloat(null);
						}
						else {
							if (!getRmFieldBase().getFloat().equals(value)) {
								getRmFieldBase().setValue(value);
							}
						}
					}
				}
				
				// Indica que foi feita uma atribuição de conteudor para o campo
				this.setIsDefined(true);
			}
		}
		else {
			this.valueField.setFloat(value);
			
			// Indica que foi feita uma atribuição de conteudor para o campo
			this.setIsDefined(true);
		}
	}
	
	public Float getFloat() {
		Float retorno = null;
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				retorno = this.oldValue.getFloat();
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getFloat();
				}
				else {
					retorno = this.valueInsertUpdate.getFloat();
				}

				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					retorno = this.valueInsertUpdate.getFloat();
				}
				else if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getFloat();
				}
				*/
			}
		}
		else {
			retorno = this.valueField.getFloat();
		}
		
		return retorno;
	}
	
	/*
	 * 
	 */
	
	public void setDate(Date value) {
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				this.oldValue.setDate(value);
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					this.valueFilter.setDate(value);
				}
				else {
					this.valueInsertUpdate.setDate(value);
					this.valueField.setDate(value);
				}

				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					this.valueInsertUpdate.setDate(value);
					this.valueField.setDate(value);
				}
				else {
					this.valueFilter.setDate(value);
				}
				*/
				
				// Caso exista um RmFieldBase anexado ao field, atualiza seu conteudo se necessario
				if (getRmFieldBase()!=null) {
					if ((getRmFieldBase().getDate()==null) && (value!=null)) {
						getRmFieldBase().setValue(value);
					}
					if (getRmFieldBase().getDate()!=null) {
						if (value==null) {
							getRmFieldBase().setDate(null);
						}
						else {
							if (!getRmFieldBase().getDate().equals(value)) {
								getRmFieldBase().setValue(value);
							}
						}
					}
				}
				
				// Indica que foi feita uma atribuição de conteudor para o campo
				this.setIsDefined(true);
			}
		}
		else {
			this.valueField.setDate(value);
			
			// Indica que foi feita uma atribuição de conteudor para o campo
			this.setIsDefined(true);
		}
	}
	
	public Date getDate() {
		Date retorno = null;
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				retorno = this.oldValue.getDate();
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getDate();
				}
				else {
					retorno = this.valueInsertUpdate.getDate();
				}

				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					retorno = this.valueInsertUpdate.getDate();
				}
				else if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getDate();
				}
				*/
			}
		}
		else {
			retorno = this.valueField.getDate();
		}
		
		return retorno;
	}

	/*
	 * 
	 */
	public void setBoolean(Boolean value) {
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				this.oldValue.setBoolean(value);
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					this.valueFilter.setBoolean(value);
				}
				else {
					this.valueInsertUpdate.setBoolean(value);
					this.valueField.setBoolean(value);
				}

				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					this.valueInsertUpdate.setBoolean(value);
					this.valueField.setBoolean(value);
				}
				else {
					this.valueFilter.setBoolean(value);
				}
				*/
				
				// Caso exista um RmFieldBase anexado ao field, atualiza seu conteudo se necessario
				if (getRmFieldBase()!=null) {
					if ((getRmFieldBase().getBoolean()==null) && (value!=null)) {
						getRmFieldBase().setValue(value);
					}
					if (getRmFieldBase().getBoolean()!=null) {
						if (value==null) {
							getRmFieldBase().setBoolean(null);
						}
						else {
							if (!getRmFieldBase().getBoolean().equals(value)) {
								getRmFieldBase().setValue(value);
							}
						}
					}
				}
				
				// Indica que foi feita uma atribuição de conteudor para o campo
				this.setIsDefined(true);
			}
		}
		else {
			this.valueField.setBoolean(value);
			
			// Indica que foi feita uma atribuição de conteudor para o campo
			this.setIsDefined(true);
		}
	}
	
	public Boolean getBoolean() {
		Boolean retorno = null;
		if (this.getTable()!=null) {
			if (this.getTable().getLoadingOldValues()) {
				retorno = this.oldValue.getBoolean();
			}
			else {
				if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getBoolean();
				}
				else {
					retorno = this.valueInsertUpdate.getBoolean();
				}

				/*
				if ((this.getTable().getTableStatus()==TableStatus.BROWSE) || (this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE) || (this.getTable().getTableStatus()==TableStatus.DELETE)) {
					retorno = this.valueInsertUpdate.getBoolean();
				}
				else if (this.getTable().getTableStatus()==TableStatus.FILTER) {
					retorno = this.valueFilter.getBoolean();
				}
				*/
			}
		}
		else {
			retorno = this.valueField.getBoolean();
		}
		
		return retorno;
	}
	
	/*
	 * 
	 */
	
	public void setIsDefined(Boolean isDefined) {
		this.fisDefined = isDefined ;
	}
	public Boolean isDefined() {
		return this.fisDefined;
	}
	
    // ************
	 
	public void setAutoIncrement(Boolean autoIncrement) {
		this.fautoIncrement = autoIncrement;
	}
	public Boolean getAutoIncrement() { 
		return this.fautoIncrement;
	}
	
	public String getFieldName() {
		return ffieldName;
	}
	public void setFieldName(String fieldName) {
		this.ffieldName = fieldName;
	}
	public String getSpecialFieldName() {
		return this.ffieldName.substring(0, 1).toUpperCase() + this.ffieldName.substring(1);
	}

	public FieldType getFieldType() {
		return ffieldType;
	}
	public void setFieldType(FieldType fieldType) {
		this.ffieldType = fieldType;
	}

	public Integer getFieldSize() {
		return ffieldSize;
	}
	public void setFieldSize(Integer fieldSize) {
		this.ffieldSize = fieldSize;
	}

	public Field(String fieldName, FieldType fieldType, Integer fieldSize) {
		this.setFieldName(fieldName);
		this.setFieldType(fieldType);
		this.setFieldSize(fieldSize);
	}

	public Field(String fieldName, FieldType fieldType, Integer fieldSize, String expression) {
		this.setFieldName(fieldName);
		this.setFieldType(fieldType);
		this.setFieldSize(fieldSize);
		this.setExpression(expression);
	}
	//***************************
	public void setValue(String value) {
		this.setString(value);
	}

	public void setValue(Integer value) {
		this.setInteger(value);
	}

	public void setValue(Double value) {
		this.setDouble(value);
	}

	public void setValue(Float value) {
		this.setFloat(value);
	}

	public void setValue(Date value) {
		this.setDate(value);
	}

	public void setValue(Boolean value) {
		this.setBoolean(value);
	}

	/****************************************************************************************
	 * Gerenciador de eventos do field
	 */
	public boolean valid() {
		return this.validate();
	}

	public boolean validate() {
		Boolean retorno = true;
		
		if (this.getTable().getTableStatus()==TableStatus.FILTER) {
			retorno = this.validateFilter();
		}
		else {
			retorno = this.validateInsertUpdate();
		}

		/*
		if ((this.getTable().getTableStatus()==TableStatus.INSERT) || (this.getTable().getTableStatus()==TableStatus.UPDATE)) {
			retorno = this.validateInsertUpdate();
		}
		else {
			retorno = this.validateFilter();
		}
		*/
		
		return retorno;
	}
	
	public boolean validateFilter() {
		ValidateFieldInFilterEvent event = new ValidateFieldInFilterEvent(this);
		event.setField(this);
		event.setTable(table);

		// Caso o campo nao possa ficar vazio
		if (this.getRequiredInFilter()) {
			switch (this.getFieldType()) {
			case VARCHAR:
				if ((this.getString()==null) || (this.getString().length()==0)) {
					event.setValid(false);
					event.setValidationAdvice("Conteúdo obrigatório!");
				}
				break;
			case INTEGER:
				if ((this.getInteger()==null) || (this.getInteger()==0)) {
					event.setValid(false);
					event.setValidationAdvice("Conteúdo obrigatório!");
				}
				break;
			case DOUBLE:
				if ((this.getDouble()==null) || (this.getDouble()==0)) {
					event.setValid(false);
					event.setValidationAdvice("Conteúdo obrigatório!");
				}
				break;
			case FLOAT:
				if ((this.getFloat()==null) || (this.getFloat()==0)) {
					event.setValid(false);
					event.setValidationAdvice("Conteúdo obrigatório!");
				}
				break;
			case TEXT:
				if ((this.getString()==null) || (this.getString().length()==0)) {
					event.setValid(false);
					event.setValidationAdvice("Conteúdo obrigatório!");
				}
				break;
			case DATE:
				if ((this.getDate()==null)) {
					event.setValid(false);
					event.setValidationAdvice("Conteúdo obrigatório!");
				}
				break;
			case DATETIME:
				if ((this.getDate()==null)) {
					event.setValid(false);
					event.setValidationAdvice("Conteúdo obrigatório!");
				}
				break;
			case BOOLEAN:
				if (this.getBoolean()==null) {
					event.setValid(false);
					event.setValidationAdvice("Conteúdo obrigatório!");
				}
				break;
			}
		}
		
		// Caso ainda seja valido o conteudo do campo
		if (event.isValid()) {
			fireValidateFieldInFilterEvent(event);
		}

		// Atualiza a aparencia do campo baseado no resultado da validacao
		if (!event.isValid()) {
			if (event.getValidationAdvice().length()==0) {
				if (this.getRmFieldBase()!=null) {
					this.getRmFieldBase().setAdviceMessageError("Conteúdo inválido!");
				}
			}
			else {
				if (this.getRmFieldBase()!=null) {
					this.getRmFieldBase().setAdviceMessageError(event.getValidationAdvice());
				}
			}
		}
		if (this.getRmFieldBase()!=null) {
			this.getRmFieldBase().updateFieldValidationStatus(event.isValid());
		}
    	
		return event.isValid();
	}
	
	public boolean validateInsertUpdate() {
		ValidateFieldEvent event = new ValidateFieldEvent(this);
		event.setField(this);
		event.setTable(table);

		// Caso o campo nao possa ficar vazio
		if (this.getRequired()) {

			// Caso o campo esteja vasio
			if (this.fieldIsEmpty()) {
				event.setValid(false);
				event.setValidationAdvice("Conteúdo obrigatório!");
			}
		}
		
		// caso ainda esteja valido, executa validacao de existencia
		if (event.isValid()) {
			if (this.getExistenceCheck()!=null) {
				boolean executaValidacao = false;
				
				// Caso o campo nao possa ficar vazio, entao executa sempre a validacao nele
				if (this.getRequired()) {
					executaValidacao = true;
				}
				else {
					// Caso o campo nao seja requerido, e nao esteja vazio, executa validacao, caso esteja vazio, nao executa validacao
					boolean fieldEmpty = false;
					
					switch (this.getFieldType()) {
					case VARCHAR:
						if ((this.getString()==null) || (this.getString().length()==0)) {
							fieldEmpty=true;
						}
						break;
					case INTEGER:
						if ((this.getInteger()==null) || (this.getInteger()==0)) {
							fieldEmpty=true;
						}
						break;
					}
					
					// Caso o campo nao esteja vazio, executa valiacao
					if (!fieldEmpty) {
						executaValidacao = true;
					}
				}

				// Executa validacao somente se o campo for requerido ou
				// caso o campo nao seja requerido, executa validacao caso ele nao esteja vazio
				if (executaValidacao) {
					Table sourceTable = this.getTable();
	        		//DatabaseApp databaseApp = (DatabaseApp) sourceTable.getDatabase();
					//Table targetTable = databaseApp.loadTableByName(this.getExistenceCheck().getTargetTableName());
					
					Database database = sourceTable.getDatabase();
					Table targetTable = database.loadTableByName(this.getExistenceCheck().getTargetTableName());

					// Cria um vetor com a lista de campos de relacionamento
					String relationship = this.getExistenceCheck().getRelationship();
					relationship = relationship.replace(" ", "");
					String relationships[] = relationship.split(",");
					
					// Verifica se o indice de relacionamento existe na tabela de destino
					Index targetIndex = targetTable.getIndexByName(this.getExistenceCheck().getTargetIndexName()); 
					if (targetIndex==null) {
						throw new IllegalAccessError("Erro on ExistenceCheck of field [" + this.getFieldName() + "], targetIndexName ["+this.getExistenceCheck().getTargetIndexName()+"] does not exist in targetTable !");
					}
					
					// Cria um vetor com a lista de campos do indice da tabela target
					String indexField = targetIndex.getIndexFields();
					indexField = indexField.replace(" ", "");
					String indexFields[] = indexField.split(",");
					
					try {
						targetTable.select("*");
						for (int vi1=0; vi1<=relationships.length-1; vi1++) {
							// Verifica se todos os campos de relacionamento existem na tabela de origem da pesquisa
							if (sourceTable.fieldExist(relationships[vi1])==null) {
								throw new IllegalAccessError("Erro on ExistenceCheck of field [" + this.getFieldName() + "], field ["+ relationships[vi1]+ "] of relationship does not exists!");
							}
							
							switch (sourceTable.fieldByName(relationships[vi1]).getFieldType()) {
							case VARCHAR:
								targetTable.setFilter(indexFields[vi1], sourceTable.getString(relationships[vi1]));
								break;
							case INTEGER:
								targetTable.setFilter(indexFields[vi1], sourceTable.getInteger(relationships[vi1]));
								break;
							case DOUBLE:
								targetTable.setFilter(indexFields[vi1], sourceTable.getDouble(relationships[vi1]));
								break;
							case FLOAT:
								targetTable.setFilter(indexFields[vi1], sourceTable.getFloat(relationships[vi1]));
								break;
							case DATE:
								targetTable.setFilter(indexFields[vi1], sourceTable.getDate(relationships[vi1]));
								break;
							case DATETIME:
								targetTable.setFilter(indexFields[vi1], sourceTable.getDate(relationships[vi1]));
								break;
							}
						}
						targetTable.loadData();
						if (targetTable.eof()) {
							event.setValid(false);
							event.setValidationAdvice(this.getExistenceCheck().getErrorMessage());
						}
						else {
							AfterExistenceCheckEvent afterExistenceCheckEvent = new AfterExistenceCheckEvent(this);
							afterExistenceCheckEvent.setSourceTable(sourceTable);
							afterExistenceCheckEvent.setTargetTable(targetTable);
							this.getExistenceCheck().fireAfterExistenceCheckEvent(afterExistenceCheckEvent);
						}
					}
					finally {
						targetTable.close();
					}
				}
			}
		}
		
		// Caso ainda seja valido o conteudo do campo
		if (event.isValid()) {
			fireValidateFieldEvent(event);
		}

		// Atualiza a aparencia do campo baseado no resultado da validacao
		if (!event.isValid()) {
			if (event.getValidationAdvice().length()==0) {
				if (this.getRmFieldBase()!=null) {
					this.getRmFieldBase().setAdviceMessageError("Conteúdo inválido!");
				}
			}
			else {
				if (this.getRmFieldBase()!=null) {
					this.getRmFieldBase().setAdviceMessageError(event.getValidationAdvice());
				}
			}
		}
		if (this.getRmFieldBase()!=null) {
			this.getRmFieldBase().updateFieldValidationStatus(event.isValid());
		}
    	
		return event.isValid();
	}

	public String getValueAsString() {
		String retorno = "";
		NumberFormat numberFormat = null;
		
		switch (getFieldType()) {
		case VARCHAR:
			if (!(getString()==null)) {
				retorno = getString();
			}
			break;
		case TEXT:
			if (!(getString()==null)) {
				retorno = getString();
			}
			break;
		case INTEGER:
			if (!(getInteger()==null)) {
				numberFormat = new DecimalFormat("#,##0");
				retorno = numberFormat.format(getInteger());
				//retorno = getInteger().toString();
			}
			break;
		case DOUBLE:
			if (!(getDouble()==null)) {
				numberFormat = new DecimalFormat("#,##0.00");
				retorno = numberFormat.format(getDouble());
			}
			break;
		case FLOAT:
			if (!(getFloat()==null)) {
				numberFormat = new DecimalFormat("#,##0.0000");
				retorno = numberFormat.format(getFloat());
			}
			break;
		case DATE:
			if (getDate()!=null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				retorno = simpleDateFormat.format(getDate());
			}
			break;
		case DATETIME:
			if (getDate()!=null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				retorno = simpleDateFormat.format(getDate());
			}
			break;
		}

		return retorno;
	}
	
	public boolean fieldIsEmpty() {
		boolean retorno = false;
		
		switch (this.getFieldType()) {
		case VARCHAR:
			if ((this.getString()==null) || (this.getString().length()==0)) {
				retorno = true;
			}
			break;
		case INTEGER:
			if ((this.getInteger()==null) || (this.getInteger()==0)) {
				retorno = true;
			}
			break;
		case DOUBLE:
			if ((this.getDouble()==null) || (this.getDouble()==0)) {
				retorno = true;
			}
			break;
		case FLOAT:
			if ((this.getFloat()==null) || (this.getFloat()==0)) {
				retorno = true;
			}
			break;
		case TEXT:
			if ((this.getString()==null) || (this.getString().length()==0)) {
				retorno = true;
			}
			break;
		case DATE:
			if ((this.getDate()==null)) {
				retorno = true;
			}
			break;
		case DATETIME:
			if ((this.getDate()==null)) {
				retorno = true;
			}
			break;
		case BOOLEAN:
			if (this.getBoolean()==null) {
				retorno = true;
			}
			break;
		}
		
		return retorno;
	}

	/**
	 * Envia dados do field para o RmFieldBase
	 */
	public void dataBind() {
		if (this.rmFieldBase!=null) {
			rmFieldBase.setValue(getValueAsString());
		}
	}
	
	public void executeForeingSearch() {
		if (this.getForeingSearch()!=null) {
			// Caso não tenha targetIndexName definido
			if (this.getForeingSearch().getTargetIndexName()==null) {
				throw new IllegalAccessError("ForeingSearch with targetIndexName undefined in field [" + this.getFieldName() + "]!");
			}
			
			// Caso não tenha returnField definido
			if (this.getForeingSearch().getReturnFieldName()==null) {
				throw new IllegalAccessError("ForeingSearch with returnField undefined in field [" + this.getFieldName() + "]!");
			}
			
			Table sourceTable = this.getTable();
			//DatabaseApp databaseApp = (DatabaseApp) sourceTable.getDatabase();
			//RmGrid targetGrid = databaseApp.loadGridByName(this.getForeingSearch().targetRmGridName);
			
			Database database  = sourceTable.getDatabase();
			RmGrid targetGrid = database.loadRmGridByName(this.getForeingSearch().targetRmGridName);
			
			if (targetGrid==null) {
				throw new IllegalAccessError("TargetGrid not found in ForeingSearch in field [" + this.getFieldName() + "]!");
			}
			
			targetGrid.setAllowInsert(this.getForeingSearch().getAllowInsert());
			targetGrid.setAllowUpdate(this.getForeingSearch().getAllowUpdate());
			targetGrid.setAllowDelete(this.getForeingSearch().getAllowDelete());
			targetGrid.setAllowFilter(this.getForeingSearch().getAllowFilter());
			targetGrid.setAllowPrint(this.getForeingSearch().getAllowPrint());
			
			Table targetTable = targetGrid.getTable();
			
			// Define o limite de registros caso esse tenha sido atribuido
			if (this.getForeingSearch().getLimit()!=null) {
				targetGrid.setLimit(this.getForeingSearch().getLimit());
			}
			
			// Define a ordem caso essa tenha sido atribuida 
			if ((this.getForeingSearch().getOrder()!=null) && (!this.getForeingSearch().getOrder().isEmpty())) {
				targetTable.setOrder(this.getForeingSearch().getOrder());
			}
			
			// Cria um vetor com a lista de campos de relacionamento
			String relationship = this.getForeingSearch().getRelationship();
			relationship = relationship.replace(" ", "");
			String relationships[] = relationship.split(",");
			
			// Verifica se o indice de relacionamento existe na tabela de destino
			Index targetIndex = targetTable.getIndexByName(this.getForeingSearch().getTargetIndexName()); 
			if (targetIndex==null) {
				throw new IllegalAccessError("Erro on ForeingSearch of field [" + this.getFieldName() + "], targetIndexName ["+this.getForeingSearch().getTargetIndexName()+"] does not exist in targetTable !");
			}
			
			// Cria um vetor com a lista de campos do targetIndex
			String indexField = targetIndex.getIndexFields();
			indexField = indexField.replace(" ", "");
			String indexFields[] = indexField.split(",");
			
			// Faz a filtragem dos campos de relacionamento
			
			// Faz a filtragem do campo que esta sendo pesquisado, caso esteja configurado para isso
			
			// Executa o evento beforeForeginSearch para ajustar algo que for necessario na filtragem
			BeforeForeingSearchEvent beforeForeingSearchEvent = new BeforeForeingSearchEvent(this);
			beforeForeingSearchEvent.setSourceTable(sourceTable);
			beforeForeingSearchEvent.setTargetTable(targetTable);
			this.foreingSearch.fireBeforeForeingSearchEvent(beforeForeingSearchEvent);
			
			// Caso tenha sido definido um filtro durante o beforeForeingSearch guarda esses filtros
			
			if (!beforeForeingSearchEvent.getCancel()) {
				RmFormWindow formWindow = new RmFormWindow();
				formWindow.setTitle(this.getForeingSearch().getTitle());
				formWindow.setWidth(this.getForeingSearch().getWidth());
				formWindow.setHeight(this.getForeingSearch().getHeight());
				formWindow.setRmGrid(targetGrid);
				
				formWindow.getBody().addComponent(targetGrid);
				/*
				VerticalLayout vl = new VerticalLayout();
				vl.addStyleName("pai-de-todos");
				vl.setResponsive(true);
				vl.setMargin(false);
				vl.setSpacing(false);
				vl.setSizeFull();
				formWindow.getBody().addComponent(vl);
				formWindow.getBody().setExpandRatio(vl, 1);
				{
					HorizontalLayout hl = new HorizontalLayout();
					hl.setWidth("100%");
					hl.addStyleName("backred");
					vl.addComponent(hl);
					{
						Label lbl = new Label("header");
						hl.addComponent(lbl);
					}
					
					VerticalLayout vl2 = new VerticalLayout();
					vl2.setMargin(false);
					vl2.setSpacing(false);
					vl2.setSizeFull();
					vl2.addStyleName("backblue");
					vl.addComponent(vl2);
					vl.setExpandRatio(vl2, 1);
					{
						Label lbl = new Label("body");
						vl2.addComponent(lbl);
					}
					
					HorizontalLayout hl2 = new HorizontalLayout();
					hl2.setWidth("100%");
					hl2.addStyleName("backgreen");
					vl.addComponent(hl2);
					{
						Label lbl = new Label("footer");
						hl2.addComponent(lbl);
					}
				}
				*/
				
				targetGrid.updateContent();
				
				
				RmFormButtonBase buttonBase = formWindow.addCancelButton();
				buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
					@Override
					public void onRmFormButtonClick(RmFormButtonClickEvent event) {
						event.getWindow().close();
					}
				});
				
				buttonBase = formWindow.addButton("Selecionar registro");
				buttonBase.setIcon(new ThemeResource("imagens/library/download.png"));
				buttonBase.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
					@Override
					public void onRmFormButtonClick(RmFormButtonClickEvent event) {
						RmFormWindow rmFormWindow = (RmFormWindow) event.getWindow();
						Grid<SimpleRecord> grid = rmFormWindow.getRmGrid().getGrid();
						if (grid.getSelectedItems().size()==0) {
							Utils.ShowMessageWindow("Atenção!", "Necessário selecionar um registro!", 500, 180, MessageWindowType.ERROR);
						}
						else
						{
							SimpleRecord simpleRecord = grid.getSelectedItems().iterator().next();
							//Table targetTable = rmFormWindow.getRmGrid().getTable();
							targetTable.close();
							targetTable.select(getForeingSearch().getReturnFieldName());
							targetTable.setFilter("uid", simpleRecord.getString("uid"));
							targetTable.setLimit(0);
							targetTable.setOrder("");
							//targetTable.setOffSet(0);
							targetTable.loadData();
							
							switch (getFieldType()) {
							case VARCHAR:
								setString(targetTable.getString(getForeingSearch().getReturnFieldName()));
								break;
							case TEXT:
								setString(targetTable.getString(getForeingSearch().getReturnFieldName()));
								break;
							case INTEGER:
								setInteger(targetTable.getInteger(getForeingSearch().getReturnFieldName()));
								break;
							case DOUBLE:
								setDouble(targetTable.getDouble(getForeingSearch().getReturnFieldName()));
								break;
							case FLOAT:
								setFloat(targetTable.getFloat(getForeingSearch().getReturnFieldName()));
								break;
							case DATE:
								setDate(targetTable.getDate(getForeingSearch().getReturnFieldName()));
								break;
							case DATETIME:
								setDate(targetTable.getDate(getForeingSearch().getReturnFieldName()));
								break;
							case BOOLEAN:
								setBoolean(targetTable.getBoolean(getForeingSearch().getReturnFieldName()));
								break;
							}
							rmFormWindow.close();
						}
					}
				});
				formWindow.show(getForeingSearch().getAutoOpenFilterForm());
			}
			else {
				Utils.ShowMessageWindow("Atenção!", beforeForeingSearchEvent.getErrorMessage(), 600, 180, MessageWindowType.ERROR);
			}
		}
		else {
			throw new IllegalAccessError("Not foreingSearch fond on field [" + this.getFieldName() + "]!");
		}
	}
	
	/*
	 * EVENTOS 
	 */
	
	public void addValidateFieldEventListener(ValidateFieldEventListener listener) {
		listenerList.add(ValidateFieldEventListener.class, listener);
	}

	public void removeValidateFieldEventListener(ValidateFieldEventListener listener) {
		listenerList.remove(ValidateFieldEventListener.class, listener);
	}

	public void fireValidateFieldEvent(ValidateFieldEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ValidateFieldEventListener.class) {
				((ValidateFieldEventListener) listeners[i + 1]).onValidate(event);
			}
		}
	}
	
	/**/
	
	public void addValidateFieldInFilterEventListener(ValidateFieldInFilterEventListener listener) {
		listenerList.add(ValidateFieldInFilterEventListener.class, listener);
	}

	public void removeValidateFieldInFilterEventListener(ValidateFieldInFilterEventListener listener) {
		listenerList.remove(ValidateFieldInFilterEventListener.class, listener);
	}

	public void fireValidateFieldInFilterEvent(ValidateFieldInFilterEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ValidateFieldInFilterEventListener.class) {
				((ValidateFieldInFilterEventListener) listeners[i + 1]).onValidate(event);
			}
		}
	}
	
	/**/
	public void addAfterValidateOnEditEventListener(AfterValidateOnEditEventListener listener) {
		listenerList.add(AfterValidateOnEditEventListener.class, listener);
	}

	public void removeAfterValidateOnEditEventListener(AfterValidateOnEditEventListener listener) {
		listenerList.remove(AfterValidateOnEditEventListener.class, listener);
	}

	public void fireAfterValidateOnEditEvent(AfterValidateOnEditEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterValidateOnEditEventListener.class) {
				((AfterValidateOnEditEventListener) listeners[i + 1]).onAfterValidateOnEdit(event);
			}
		}
	}
}
