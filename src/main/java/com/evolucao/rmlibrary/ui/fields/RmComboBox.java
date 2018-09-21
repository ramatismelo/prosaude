package com.evolucao.rmlibrary.ui.fields;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.dataprovider.ComboBoxDataProvider;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.ComboBoxItemCaptionGeneratorEvent;
import com.evolucao.rmlibrary.database.events.ComboBoxValueChangeEvent;
import com.evolucao.rmlibrary.database.events.ValidateEvent;
import com.evolucao.rmlibrary.database.events.ValidateEvent.ValidateEventListener;
import com.evolucao.rmlibrary.ui.controller.ControlContentField;
import com.evolucao.rmlibrary.ui.controller.enumerators.CapitalizationType;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.UI;

public class RmComboBox extends RmFieldBase {
	CssLayout fieldButtonContainer = null;
	String fieldName = null;
	ComboBox<SimpleRecord> textField = new ComboBox<SimpleRecord>();
	protected EventListenerList listenerList = new EventListenerList();
	int limit = 10;
	ComboBoxDataProvider comboBoxDataProvider = null;

	public ComboBox<SimpleRecord> getComboBox() {
		return this.textField;
	}
	
	public void setTableDataProvider(ComboBoxDataProvider comboBoxDataProvider) {
		this.comboBoxDataProvider = comboBoxDataProvider;
	}
	public ComboBoxDataProvider getTableDataProvider() {
		return this.comboBoxDataProvider;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getLimnit() {
		return this.limit;
	}
	
	public void setReadOnly(Boolean readOnly) {
		this.textField.setReadOnly(readOnly);
	}
	public Boolean getReadOnly() {
		return this.textField.isReadOnly();
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return this.fieldName;
	}
	
	public void setIcon(Resource icon) {
		this.textField.setIcon(icon);
	}
	public Resource getIcon() {
		return this.textField.getIcon();
	}

	public RmComboBox() {
		super();
		addStyleName("rmtextfield");
		this.getComboBox().beforeClientResponse(false);
	}
	
	public RmComboBox(ControlContentField controlContentField) {
		super(controlContentField);
		
		this.InitializeRmFieldBase();
		
		addStyleName("rmtextfield");
		
		// A partir deste ponto configura as caracteristicas pessoaso do RmControlBox
		if (this.getField().getTable().getTableStatus()==TableStatus.INSERT) {
			if (this.getField().getReadOnlyInsert()) {
				textField.setReadOnly(true);
			}
		}
		else if (this.getField().getTable().getTableStatus()==TableStatus.UPDATE) {
			if (this.getField().getReadOnlyUpdate()) {
				textField.setReadOnly(true);
			}
		}
		
		ComboBoxDataProvider comboBoxDataProvider = new ComboBoxDataProvider(this.getField().getComboBox().getTargetTableName());
		comboBoxDataProvider.setTableName(this.getField().getComboBox().getTargetTableName());
		comboBoxDataProvider.setDisplayFieldName(this.getField().getComboBox().getTargetDisplayFieldName());
		comboBoxDataProvider.setTagFieldName(this.getField().getComboBox().getTagFieldName());
		comboBoxDataProvider.setAdditionalFieldsToLoad(this.getField().getComboBox().getAdditionalFieldsToLoad());
		comboBoxDataProvider.setResultFieldName(this.getField().getComboBox().getResultFieldName());
		comboBoxDataProvider.setRmComboBox(this);
		this.setTableDataProvider(comboBoxDataProvider);
		
		textField.setDataProvider(comboBoxDataProvider);
		
		//textField.setItemCaptionGenerator(SimpleRecord::getField0);
		textField.setItemCaptionGenerator(new ItemCaptionGenerator<SimpleRecord>() {
			@Override
			public String apply(SimpleRecord item) {
				String itemCaption = "";
				if (!getField().getComboBox().getItemCaptionGeneratorAdded()) {
					itemCaption = item.getString(field.getComboBox().getTargetDisplayFieldName());
				}
				else {
					ComboBoxItemCaptionGeneratorEvent event = new ComboBoxItemCaptionGeneratorEvent(this);
					event.setSimpleRecord(item);
					getField().getComboBox().fireComboBoxItemCaptionGeneratorEvent(event);
					itemCaption = event.getItemCaption();
				}
				
				return itemCaption;
			}
		});
		
		// Atualiza os dados que estiverem no field para o RmFieldBase
		this.getField().dataBind();
		
		// Executa as rotinas que irao configurar o funcionamento do comboBox baseado nas informacoes
		// que foram passadas para ele
		this.updateContent();
	}
	
	/*
	 * Caso uma alteracao no campo seja detectada, atualiza o conteudo do campo na tabela
	 */
	public void updateContent() {
		removeAllComponents();
		
		// Configura o comportamento do componente
		this.configureTextField();
		
		this.deployTextField();
	}

	public void deployTextField() {
		fieldContainer.addStyleName("field-container");
		fieldContainer.addStyleName("flex-direction-column");
		addComponent(fieldContainer);
		{
			if (this.getCaption()!=null) {
				textField.setCaption(this.getControlContentField().getFieldTitle());
			}
			fieldContainer.addComponent(textField);
		}

		lblMensagemErro.addStyleName("hide-item");
		lblMensagemErro.addStyleName("validation-advice");
		addComponent(lblMensagemErro);
	}
	
	public void configureTextField() {
		/*
		List<Model10> collection = new ArrayList<Model10>();
		Model10 model = new Model10();
		model.setField0("RAMATIS SOARES DE MELO 2");
		collection.add(model);
		
        ListDataProvider<Model10> dataProvider = DataProvider.ofCollection(collection);
		textField.setDataProvider(dataProvider);
		*/
		
		if ((field==null) || (field.getCapitalizationType()==CapitalizationType.UPPERCASE)) {
			textField.addStyleName("uppercase");
		}
		
		textField.addBlurListener(new BlurListener() {
			@Override
			public void blur(BlurEvent event) {
				//field.validate();
				//System.out.println("blur");
			}
		});

		textField.addContextClickListener(new ContextClickListener() {
			@Override
			public void contextClick(ContextClickEvent event) {
				System.out.println("teste");
			}
		});
		
		textField.addValueChangeListener(new ValueChangeListener<SimpleRecord>() {
			@Override
			public void valueChange(ValueChangeEvent<SimpleRecord> event) {
				if (field!=null) {
					// Caso o item selecionado não possua uid, informa que nao existe item selecionado
					if ((event.getValue()!=null) && (event.getValue().getString("uid").isEmpty())) {
						textField.setValue(null);
					}
					
					// Quando o valor de um campo estiver sendo carregado do banco de dados no inicio de uma edicao, ele nao deve 
					// ser validado. 
					if (!field.getTable().getLoadingDataToForm()) {
						boolean processar = false;

						switch (field.getFieldType()) {
						// Caso o campo de retorno seja varchar, trata todas as verificacoes como string
						case VARCHAR:
							if ((field.getString()==null) && (event.getValue()!=null)) {
								//field.setValue(event.getValue().getString("uid"));
								field.setValue(event.getValue().getString(field.getComboBox().getResultFieldName()));

								// Caso tenha algum campo na origem onde deve ser apresentado a descricao do campo, atualiza esse campo na origem agora
								if (field.getComboBox().getSourceDisplayFieldName()!=null) {
									field.getTable().setString(field.getComboBox().getSourceDisplayFieldName(), event.getValue().getString(field.getComboBox().getTargetDisplayFieldName()));
								}
							}
							else if ((field.getString()!=null) && (event.getValue()==null)) {
								field.setString(null);
								
								// Caso tenha algum campo na origem onde deve ser apresentado a descricao do campo, atualiza esse campo na origem agora
								if (field.getComboBox().getSourceDisplayFieldName()!=null) {
									field.getTable().setString(field.getComboBox().getSourceDisplayFieldName(), null);
								}
							}
							//else if ((field.getString()!=null) && (event.getValue()!=null) && (!field.getString().equals(event.getValue().getString("uid")))) {
							else if ((field.getString()!=null) && (event.getValue()!=null) && (!field.getString().equals(event.getValue().getString(field.getComboBox().getResultFieldName())))) {
								//field.setString(event.getValue().getString("uid"));
								field.setString(event.getValue().getString(field.getComboBox().getResultFieldName()));
								
								// Caso tenha algum campo na origem onde deve ser apresentado a descricao do campo, atualiza esse campo na origem agora
								if (field.getComboBox().getSourceDisplayFieldName()!=null) {
									field.getTable().setString(field.getComboBox().getSourceDisplayFieldName(), event.getValue().getString(field.getComboBox().getTargetDisplayFieldName()));
								}
							}
							break;
						// Caso o campo de retorno seja integer, trata todas as verificacoes como integer
						case INTEGER:
							if ((field.getInteger()==null) && (event.getValue()!=null)) {
								//field.setValue(event.getValue().getString("uid"));
								field.setValue(event.getValue().getInteger(field.getComboBox().getResultFieldName()));

								// Caso tenha algum campo na origem onde deve ser apresentado a descricao do campo, atualiza esse campo na origem agora
								if (field.getComboBox().getSourceDisplayFieldName()!=null) {
									field.getTable().setInteger(field.getComboBox().getSourceDisplayFieldName(), event.getValue().getInteger(field.getComboBox().getTargetDisplayFieldName()));
								}
							}
							else if ((field.getInteger()!=null) && (event.getValue()==null)) {
								field.setInteger(null);
								
								// Caso tenha algum campo na origem onde deve ser apresentado a descricao do campo, atualiza esse campo na origem agora
								if (field.getComboBox().getSourceDisplayFieldName()!=null) {
									field.getTable().setInteger(field.getComboBox().getSourceDisplayFieldName(), null);
								}
							}
							//else if ((field.getString()!=null) && (event.getValue()!=null) && (!field.getString().equals(event.getValue().getString("uid")))) {
							else if ((field.getInteger()!=null) && (event.getValue()!=null) && (!field.getInteger().equals(event.getValue().getInteger(field.getComboBox().getResultFieldName())))) {
								//field.setString(event.getValue().getString("uid"));
								field.setInteger(event.getValue().getInteger(field.getComboBox().getResultFieldName()));
								
								// Caso tenha algum campo na origem onde deve ser apresentado a descricao do campo, atualiza esse campo na origem agora
								if (field.getComboBox().getSourceDisplayFieldName()!=null) {
									field.getTable().setInteger(field.getComboBox().getSourceDisplayFieldName(), event.getValue().getInteger(field.getComboBox().getTargetDisplayFieldName()));
								}
							}
							break;
						}
						
						if (field.validate()) {
							AfterValidateOnEditEvent afterValidateOnEditEvent = new AfterValidateOnEditEvent(this);
							afterValidateOnEditEvent.setTable(getField().getTable());
							getField().fireAfterValidateOnEditEvent(afterValidateOnEditEvent);
						}
						
						ComboBoxValueChangeEvent comboBoxValueChangeEvent = new ComboBoxValueChangeEvent(this);
						comboBoxValueChangeEvent.setSimpleRecord(event.getValue());
						comboBoxValueChangeEvent.setSourceTable(field.getTable());
						getField().getComboBox().fireComboBoxValueChangeEvent(comboBoxValueChangeEvent);
					}
				}
			}
		});
	}
	
	@Override
	public void setRequired(boolean required) {
		textField.setEmptySelectionAllowed(!required);
		//textField.setRequired(required);
		//textField.setImmediate(true);
		textField.setRequiredIndicatorVisible(required);
		this.required = required;
		this.updateContent();
	}
	
	@Override
	public boolean getRequired() {
		return this.required;
	}
	
	public Boolean valid() {
		return this.validate();
	}
	
	@Override
	public Boolean validate() {
		ValidateEvent event = new ValidateEvent(this);

		// Caso o campo nao possa ficar vazio
		if ((this.getRequired()) && (this.textField.getValue().toString().length()==0)) {
			event.setValid(false);
			event.setValidationAdvice("Conteúdo obrigatório!");
		}
		
		// Caso ainda seja valido o conteudo do campo
		if (event.isValid()) {
			event.setValue(textField.getValue().getField0());
			fireValidateEvent(event);
		}

		// Atualiza a aparencia do campo baseado no resultado da validacao
		if (!event.isValid()) {
			if (event.getValidationAdvice().length()==0) {
				lblMensagemErro.setValue("Conteúdo inválido!");
			}
			else {
				lblMensagemErro.setValue(event.getValidationAdvice());
			}
		}
    	this.updateFieldValidationStatus(event.isValid());
    	
		return event.isValid();
	}
	
	public void focus() {
		textField.focus();
		//textField.selectAll();
	}
	
	/*************************************************************************/
	public void addValidateEventListener(ValidateEventListener listener) {
		listenerList.add(ValidateEventListener.class, listener);
	}

	public void removeValidateEventListener(ValidateEventListener listener) {
		listenerList.remove(ValidateEventListener.class, listener);
	}

	void fireValidateEvent(ValidateEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ValidateEventListener.class) {
				((ValidateEventListener) listeners[i + 1]).onValidate(event);
			}
		}
	}
	
	@Override
	public void setValue(String value) {
		String fieldValue = value;
		
		if (field.getFieldType()==FieldType.INTEGER) {
			if (fieldValue.contains(".")) {
				fieldValue = fieldValue.replace(".", "");
			}
		}
		
		this.setString(fieldValue);
	}
	@Override
	public void setString(String value) {
		String content = null;
		
		if ((value!=null) && (!value.isEmpty())) {
			ApplicationUI ui = (ApplicationUI) UI.getCurrent();
			try {
				ui.database.openConnection();
				
				Table tblTabela = ui.database.loadTableByName(field.getComboBox().getTargetTableName());
				tblTabela.select(field.getComboBox().getTargetDisplayFieldName());
				//tblTabela.setFilter("uid", value);
				tblTabela.setFilter(field.getComboBox().getResultFieldName(), value);
				tblTabela.loadData();
				if (!tblTabela.eof()) {
					content = tblTabela.getString(field.getComboBox().getTargetDisplayFieldName());
				}
				
				if (field.getCapitalizationType()==CapitalizationType.UPPERCASE) {
					content = content.toUpperCase();
				}

				if (this.getTableDataProvider().getTable().getSimpleRecordList()==null) {
					this.getTableDataProvider().getTable().setSimpleRecordList(new ArrayList<SimpleRecord>());
				}
				
				SimpleRecord simpleRecord = new SimpleRecord();
				simpleRecord.setString(field.getComboBox().getTargetDisplayFieldName(), content);
				//simpleRecord.setString("uid", value);
				simpleRecord.setString(field.getComboBox().getResultFieldName(), value);
				this.getTableDataProvider().getTable().getSimpleRecordList().add(simpleRecord);
				//textField.setItems(simpleRecord);
				textField.setSelectedItem(simpleRecord);
				textField.setValue(simpleRecord);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				ui.database.closeConnection();
			}
		}
		else {
			SimpleRecord simple = textField.getValue();
			textField.setValue(null);
		}
		//textField.setValue(content);
	}
	@Override
	public String getString() {
		String content = null;
		
		if ((textField!=null) && (textField.getValue()!=null)) {
			//content = textField.getValue().getString("uid");
			content = textField.getValue().getString(field.getComboBox().getResultFieldName());
		}
		
		return content;
	}
	@Override
	public void setValue(Integer value) {
		this.setInteger(value);
	}
	@Override
	public void setInteger(Integer value) {
		String content = null;
		
		if ((value!=null)) {
			ApplicationUI ui = (ApplicationUI) UI.getCurrent();
			try {
				ui.database.openConnection();
				
				Table tblTabela = ui.database.loadTableByName(field.getComboBox().getTargetTableName());
				tblTabela.select(field.getComboBox().getTargetDisplayFieldName());
				//tblTabela.setFilter("uid", value);
				tblTabela.setFilter(field.getComboBox().getResultFieldName(), value);
				tblTabela.loadData();
				if (!tblTabela.eof()) {
					content = tblTabela.getString(field.getComboBox().getTargetDisplayFieldName());
				}
				
				if (field.getCapitalizationType()==CapitalizationType.UPPERCASE) {
					content = content.toUpperCase();
				}

				if (this.getTableDataProvider().getTable().getSimpleRecordList()==null) {
					this.getTableDataProvider().getTable().setSimpleRecordList(new ArrayList<SimpleRecord>());
				}
				
				SimpleRecord simpleRecord = new SimpleRecord();
				simpleRecord.setString(field.getComboBox().getTargetDisplayFieldName(), content);
				//simpleRecord.setString("uid", value);
				simpleRecord.setInteger(field.getComboBox().getResultFieldName(), value);
				this.getTableDataProvider().getTable().getSimpleRecordList().add(simpleRecord);
				//textField.setItems(simpleRecord);
				textField.setSelectedItem(simpleRecord);
				textField.setValue(simpleRecord);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				ui.database.closeConnection();
			}
		}
		else {
			SimpleRecord simple = textField.getValue();
			textField.setValue(null);
		}
		//textField.setValue(content);
	}
	@Override
	public Integer getInteger() {
		Integer content = null;
		
		if ((textField!=null) && (textField.getValue()!=null)) {
			SimpleRecord s;
			content = textField.getValue().getInteger(field.getComboBox().getResultFieldName());
		}
		
		return content;
	}
	@Override
	public void setValue(Double value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setDouble(Double value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Double getDouble() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setValue(Float value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFloat(Float value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Float getFloat() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setValue(Date value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setDate(Date value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setValue(Boolean value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setBoolean(Boolean value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getBoolean() {
		// TODO Auto-generated method stub
		return null;
	}
}
