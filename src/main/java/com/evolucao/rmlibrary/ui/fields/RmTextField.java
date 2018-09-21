package com.evolucao.rmlibrary.ui.fields;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.ValidateEvent;
import com.evolucao.rmlibrary.database.events.ValidateEvent.ValidateEventListener;
import com.evolucao.rmlibrary.ui.controller.ControlContentField;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.enumerators.CapitalizationType;
import com.evolucao.rmlibrary.utils.Utils;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class RmTextField extends RmFieldBase {
	Boolean showButton = false;
	CssLayout fieldButtonContainer = null;
	TextField textField = new TextField();
	CssLayout comboBoxAdvancedGridContainer = new CssLayout();
	protected EventListenerList listenerList = new EventListenerList();
	
	public void setReadOnly(Boolean readOnly) {
		this.textField.setReadOnly(readOnly);
	}
	public Boolean getReadOnly() {
		return this.textField.isReadOnly();
	}

	public void setShowButton(Boolean showButton) {
		this.showButton = showButton;
		this.updateContent();
	}
	public Boolean getShowButton() {
		return this.showButton;
	}
	
	public void setIcon(Resource icon) {
		this.textField.setIcon(icon);
	}
	public Resource getIcon() {
		return this.textField.getIcon();
	}

	public RmTextField() {
		super();
		addStyleName("rmtextfield");
		this.updateContent();
	}
	
	public RmTextField(ControlContentField controlContentField) {
		super(controlContentField);
		this.InitializeRmFieldBase();
		
		// A partir deste ponto configura caracteristicas pessoais ao RmTextField
		this.addStyleName("rmtextfield");
		
		// Configura se o componente será readOnly
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
		
		// Caso seja um campo que vai ser usado para uma pesquisa externa, adiciona um botao
		if (this.getField().getForeingSearch()!=null) {
			this.setShowButton(true);
		}
		
		this.updateContent();
	}

	/*
	 * Caso uma alteracao no campo seja detectada, atualiza o conteudo do campo na tabela
	 */
	public void updateContent() {
		removeAllComponents();
		
		// Configura o comportamento do componente
		this.configureTextField();
		
		// Renderiza o componente com suas caracteristicas
		if (this.getShowButton()) {
			this.deployTextButtonField();
		}
		else if ((field!=null) && (field.getComboBoxAdvanced()!=null)) {
			this.deployTextFieldComboBoxAdvanced();
		}
		else {
			this.deployTextField();
		}
	}

	public void deployTextFieldComboBoxAdvanced() {
		//UI.getCurrent().getPage().getJavaScript().execute("$('.field-container').focus(function(){alert('oi');})");
		//UI.getCurrent().getPage().getJavaScript().execute("$('.combobox-advanced-container').addClass('testenovo');");
		//UI.getCurrent().getPage().getJavaScript().execute("$('.combobox-advanced-container').focusout(function(){alert('oi');});");
		UI.getCurrent().getPage().getJavaScript().execute("$('.combobox-advanced-container').focusout(function(){alert('oi');});");
		
		CssLayout comboBoxAdvancedContainer = new CssLayout();
		comboBoxAdvancedContainer.addStyleName("combobox-advanced-container");
		{
			CssLayout comboBoxAdvancedFieldContainer = new CssLayout();
			comboBoxAdvancedFieldContainer.addStyleName("combobox-advanced-field-container");
			comboBoxAdvancedFieldContainer.addStyleName("flex-direction-column");
			comboBoxAdvancedContainer.addComponent(comboBoxAdvancedFieldContainer);
			{
				fieldContainer.addStyleName("field-container");
				comboBoxAdvancedFieldContainer.addComponent(fieldContainer);
				{
					if (this.getCaption()!=null) {
						//textField.setCaption(caption);
						textField.setCaption(this.getControlContentField().getFieldTitle());
					}
					
					fieldContainer.addComponent(textField);
				}

				lblMensagemErro.addStyleName("hide-item");
				lblMensagemErro.addStyleName("validation-advice");
				comboBoxAdvancedFieldContainer.addComponent(lblMensagemErro);
			}
			
			//CssLayout comboBoxAdvancedGridContainer = new CssLayout();
			comboBoxAdvancedGridContainer.addStyleName("combobox-advanced-grid-container");
			comboBoxAdvancedGridContainer.addStyleName("hide-item");
			comboBoxAdvancedContainer.addComponent(comboBoxAdvancedGridContainer);
			{
				Label lblTeste = new Label("Conteudo de teste");
				comboBoxAdvancedGridContainer.addComponent(lblTeste);
			}
		}
		addComponent(comboBoxAdvancedContainer);
	}
	
	public void deployTextField() {
		fieldContainer.addStyleName("field-container");
		addComponent(fieldContainer);
		{
			if (this.getCaption()!=null) {
				//textField.setCaption(caption);
				textField.setCaption(this.getControlContentField().getFieldTitle());
			}
			
			fieldContainer.addComponent(textField);
		}

		lblMensagemErro.addStyleName("hide-item");
		lblMensagemErro.addStyleName("validation-advice");
		addComponent(lblMensagemErro);
	}
	
	public void deployTextButtonField() {
		fieldButtonContainer = new CssLayout();
		fieldButtonContainer.addStyleName("field-button-container");
		fieldButtonContainer.addStyleName("flex-direction-col");
		{
			CssLayout div = new CssLayout();
			div.addStyleName("flex-direction-row");
			div.addStyleName("v-caption");
			fieldButtonContainer.addComponent(div);
			{
				if (this.getCaption()!=null) {
					Label lblCaption = new Label(this.getCaption());
					lblCaption.addStyleName("v-captiontext");
					lblCaption.setWidthUndefined();
					div.addComponent(lblCaption);
				}
				
				if (this.getRequired()) {
					Label lblRequired = new Label("*");
					lblRequired.addStyleName("v-required-field-indicator");
					lblRequired.setWidthUndefined();
					div.addComponent(lblRequired);
				}
			}
			
			div = new CssLayout();
			div.addStyleName("flex-direction-row");
			fieldButtonContainer.addComponent(div);
			{
				fieldContainer.addStyleName("field-container");
				fieldContainer.addComponent(textField);
				div.addComponent(fieldContainer);
				
				//Button btnButton = new Button("Pesquisar");
				Button btnButton = new Button();
				btnButton.setIcon(new ThemeResource("imagens/library/lupa.png"));
				btnButton.addStyleName("normal-button");
				div.addComponent(btnButton);
				btnButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						field.executeForeingSearch();
					}
				});
			}
		}
		
		addComponent(fieldButtonContainer);
		
		lblMensagemErro.addStyleName("hide-item");
		lblMensagemErro.addStyleName("validation-advice");
		addComponent(lblMensagemErro);
	}
	
	public void configureTextField() {
		if ((field==null) || (field.getCapitalizationType()==CapitalizationType.UPPERCASE)) {
			textField.addStyleName("uppercase");
		}
		else {
			textField.removeStyleName("uppercase");
		}
		
		textField.addBlurListener(new BlurListener() {
			@Override
			public void blur(BlurEvent event) {
				//field.validate();
				if ((field!=null) && (field.getComboBoxAdvanced()!=null)) {
					//comboBoxAdvancedGridContainer.addStyleName("hide-item");
				}
			}
		});
		
		if (field!=null) {
			if (field.getComboBoxAdvanced()==null) {
				textField.setValueChangeMode(ValueChangeMode.BLUR);
			}
			else {
				textField.setValueChangeMode(ValueChangeMode.TIMEOUT);
				textField.setValueChangeTimeout(1000);
			}
		}
		else {
			textField.setValueChangeMode(ValueChangeMode.BLUR);
		}
		
		textField.addValueChangeListener(new ValueChangeListener<String>() {
			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				if (field!=null) {
					// Quando o valor de um campo estiver sendo carregado do banco de dados no inicio de uma edicao, ele nao deve 
					// ser validado. 
					if (!field.getTable().getLoadingDataToForm()) {
						switch (field.getFieldType()) {
						case VARCHAR:
							if ((field.getString()==null) || (!field.getString().equals(textField.getValue()))) {
								String content = textField.getValue();
								if (field.getCapitalizationType()==CapitalizationType.UPPERCASE) {
									content = content.toUpperCase();
								}
								field.setValue(content);
								
								if (field.getComboBoxAdvanced()!=null) {
									if (content.length()>0) {
										comboBoxAdvancedGridContainer.removeStyleName("hide-item");
										
										Label lblTeste = new Label("Conteudo detectado: " + content + System.currentTimeMillis());
										comboBoxAdvancedGridContainer.addComponent(lblTeste);
									}
									else {
										comboBoxAdvancedGridContainer.addStyleName("hide-item");
										
										Label lblTeste = new Label("Nao tem conteudo detectado " + content + System.currentTimeMillis());
										comboBoxAdvancedGridContainer.addComponent(lblTeste);
									}
								}
							}
							break;
						case INTEGER:
							// Conteudo do campo � vazio, coloca nullo no campo
							if (textField.getValue().isEmpty()) {
								field.setInteger(null);
							}
							// Conteudo do campo n�o � vazio
							else {
								field.setInteger(getInteger());
							}
							break;
						case DOUBLE:
							// Conteudo do campo � vazio, coloca nullo no campo
							if (textField.getValue().isEmpty()) {
								field.setDouble(null);
							}
							// Conteudo do campo n�o � vazio
							else {
								field.setDouble(getDouble());
							}
							break;
						case FLOAT:
							// Conteudo do campo � vazio, coloca nullo no campo
							if (textField.getValue().isEmpty()) {
								field.setFloat(null);
							}
							// Conteudo do campo n�o � vazio
							else {
								field.setFloat(getFloat());
							}
							break;
						case TEXT:
							if ((field.getString()==null) || (field.getString()!=textField.getValue())) {
								field.setValue(textField.getValue());
							}
							break;
						case DATE:
							// Nao tem data no field e tem no TextField, atualiza o field
							if ((field.getDate()==null) && (!textField.getValue().isEmpty())) {
								SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
								try {
									field.setDate(simpleDate.parse(textField.getValue()));
								} catch (ParseException e) {
									System.out.println(e.getMessage());
								}
							}
							
							// Tem data definida no field e nao tem data no textField, atualiza o field com null
							if ((field.getDate()!=null) && (textField.getValue().isEmpty())) {
								field.setDate(null);
							}
							
							// Tem data definida do field e data definida no textField, caso seja diferentes, atualiza o field
							if ((field.getDate()!=null) && (!textField.getValue().isEmpty())) {
								SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
								try {
									Date data = simpleDate.parse(textField.getValue());
									
									if (!field.getDate().equals(data)) {
										field.setDate(data);
									}
								} catch (ParseException e) {
									System.out.println(e.getMessage());
								}
							}
							break;
						case DATETIME:
							// Nao tem data no field e tem no TextField, atualiza o field
							if ((field.getDate()==null) && (!textField.getValue().isEmpty())) {
								SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
								try {
									field.setDate(simpleDate.parse(textField.getValue()));
								} catch (ParseException e) {
									System.out.println(e.getMessage());
								}
							}
							
							// Tem data definida no field e nao tem data no textField, atualiza o field com null
							if ((field.getDate()!=null) && (textField.getValue().isEmpty())) {
								field.setDate(null);
							}
							
							// Tem data definida do field e data definida no textField, caso seja diferentes, atualiza o field
							if ((field.getDate()!=null) && (!textField.getValue().isEmpty())) {
								SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
								try {
									Date data = simpleDate.parse(textField.getValue());
									
									if (!field.getDate().equals(data)) {
										field.setDate(data);
									}
								} catch (ParseException e) {
									System.out.println(e.getMessage());
								}
							}
							break;
						case BOOLEAN:
							break;
						}
						
						if (field.validate()) {
							AfterValidateOnEditEvent afterValidateOnEditEvent = new AfterValidateOnEditEvent(this);
							afterValidateOnEditEvent.setTable(getField().getTable());
							getField().fireAfterValidateOnEditEvent(afterValidateOnEditEvent);
						}
					}
				}
			}
		});
	}
	
	@Override
	public void setRequired(boolean required) {
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
			event.setValue(textField.getValue());
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
		textField.selectAll();
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
		this.setString(value);
	}
	
	@Override
	public void setString(String value) {
		String content = value;
		if (value!=null) {
			if (field.getCapitalizationType()==CapitalizationType.UPPERCASE) {
				content = content.toUpperCase();
			}
			textField.setValue(content);
		}
		else {
			textField.setValue("");
		}
	}
	
	@Override
	public String getString() {
		String content = textField.getValue();
		if (field.getCapitalizationType()==CapitalizationType.UPPERCASE) {
			content = content.toUpperCase();
		}
		return content;
	}

	@Override
	public void setValue(Integer value) {
		this.setInteger(value);
	}
	
	@Override
	public void setInteger(Integer value) {
		if (value!=null) {
			NumberFormat numberFormat = new DecimalFormat("#,##0");
			textField.setValue(numberFormat.format(value));
		}
		else {
			textField.setValue("");
		}
	}
	
	@Override
	public Integer getInteger() {
		Integer retorno = null;
		if (!textField.getValue().isEmpty()) {
			NumberFormat numberFormat = new DecimalFormat("#,##0");
			try {
				retorno = numberFormat.parse(textField.getValue()).intValue();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retorno;
	}
	
	@Override
	public void setValue(Double value) {
		this.setDouble(value);
	}
	
	@Override
	public void setDouble(Double value) {
		if (value!=null) {
			NumberFormat numberFormat = new DecimalFormat("#,##0.00");
			textField.setValue(numberFormat.format(value));
		}
		else {
			textField.setValue("");
		}
	}
	
	@Override
	public Double getDouble() {
		Double retorno = null;
		if (!textField.getValue().isEmpty()) {
			NumberFormat numberFormat = new DecimalFormat("#,##0.00");
			try {
				retorno = numberFormat.parse(textField.getValue()).doubleValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return retorno;
	}
	
	@Override
	public void setValue(Float value) {
		this.setFloat(value);
	}
	
	@Override
	public void setFloat(Float value) {
		if (value!=null) {
			NumberFormat numberFormat = new DecimalFormat("#,##0.0000");
			textField.setValue(numberFormat.format(value));
		}
		else {
			textField.setValue("");
		}
	}
	
	@Override
	public Float getFloat() {
		Float retorno = null;
		if (!textField.getValue().isEmpty()) {
			NumberFormat numberFormat = new DecimalFormat("#,##0.0000");
			try {
				retorno = numberFormat.parse(textField.getValue()).floatValue();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retorno;
	}
	
	@Override
	public void setValue(Date value) {
		this.setDate(value);
	}
	
	@Override
	public void setDate(Date value) {
		if (value==null) {
			textField.setValue("");
		}
		else {
			String mask = this.getField().getMask();
			if (mask.isEmpty()) {
				mask = "dd/MM/yyyy";
			}
			SimpleDateFormat simpleDate = new SimpleDateFormat(mask);
			textField.setValue(simpleDate.format(value));
		}
	}
	
	@Override
	public Date getDate() {
		Date retorno = null;
		if ((textField.getValue()!=null) && (!textField.getValue().isEmpty())) {
			if (Utils.isDate(textField.getValue())) {
				String mask = this.getField().getMask();
				if (mask.isEmpty()) {
					mask = "dd/MM/yyyy";
				}
				SimpleDateFormat simpleDate = new SimpleDateFormat(mask);
				try {
					retorno = simpleDate.parse(textField.getValue());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return retorno;
	}
	
	@Override
	public void setValue(Boolean value) {
		this.setBoolean(value);
	}
	
	@Override
	public void setBoolean(Boolean value) {
	}
	
	@Override
	public Boolean getBoolean() {
		return null;
	}
}
