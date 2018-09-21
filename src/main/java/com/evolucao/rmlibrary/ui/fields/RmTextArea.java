package com.evolucao.rmlibrary.ui.fields;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.ValidateEvent;
import com.evolucao.rmlibrary.database.events.ValidateEvent.ValidateEventListener;
import com.evolucao.rmlibrary.ui.controller.ControlContentField;
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
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class RmTextArea extends RmFieldBase {
	CssLayout fieldButtonContainer = null;
	String fieldName = null;
	TextArea textArea = new TextArea();
	protected EventListenerList listenerList = new EventListenerList();
	
	public void setReadOnly(Boolean readOnly) {
		this.textArea.setReadOnly(readOnly);
	}
	public Boolean getReadOnly() {
		return this.textArea.isReadOnly();
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return this.fieldName;
	}
	
	public void setIcon(Resource icon) {
		this.textArea.setIcon(icon);
	}
	public Resource getIcon() {
		return this.textArea.getIcon();
	}

	public RmTextArea() {
		super();
		this.InitializeRmFieldBase();
		
		addStyleName("rmtextfield");
		this.updateContent();
	}
	
	public RmTextArea(ControlContentField controlContentField) {
		super(controlContentField);
		
		addStyleName("rmtextfield");
		
		this.InitializeRmFieldBase();
		
		if (this.getField().getTable().getTableStatus()==TableStatus.INSERT) {
			if (this.getField().getReadOnlyInsert()) {
				textArea.setReadOnly(true);
			}
		}
		else if (this.getField().getTable().getTableStatus()==TableStatus.UPDATE) {
			if (this.getField().getReadOnlyUpdate()) {
				textArea.setReadOnly(true);
			}
		}
		
		this.updateContent();
	}
	
	/*
	 * Caso uma alteracao no campo seja detectada, atualiza o conteudo do campo na tabela
	 */
	public void updateContent() {
		removeAllComponents();
		this.configureTextArea();
		this.deployTextArea();
	}
	
	public void deployTextArea() {
		fieldContainer.addStyleName("field-container");
		fieldContainer.addStyleName("field-textarea-container");
		addComponent(fieldContainer);
		{
			if (this.getCaption()!=null) {
				textArea.setCaption(this.getControlContentField().getFieldTitle());
			}
			
			fieldContainer.addComponent(textArea);
		}

		lblMensagemErro.addStyleName("hide-item");
		lblMensagemErro.addStyleName("validation-advice");
		addComponent(lblMensagemErro);
	}
	
	public void configureTextArea() {
		if ((field==null) || (field.getCapitalizationType()==CapitalizationType.UPPERCASE)) {
			textArea.addStyleName("uppercase");
		}
		
		textArea.addBlurListener(new BlurListener() {
			@Override
			public void blur(BlurEvent event) {
				//field.validate();
			}
		});
		
		textArea.setValueChangeMode(ValueChangeMode.BLUR);
		textArea.addValueChangeListener(new ValueChangeListener<String>() {
			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				if (field!=null) {
					// Quando o valor de um campo estiver sendo carregado do banco de dados no inicio de uma edicao, ele nao deve 
					// ser validado. 
					if (!field.getTable().getLoadingDataToForm()) {
						switch (field.getFieldType()) {
						case VARCHAR:
							if ((field.getString()==null) || (!field.getString().equals(textArea.getValue()))) {
								String content = textArea.getValue();
								if (field.getCapitalizationType()==CapitalizationType.UPPERCASE) {
									content = content.toUpperCase();
								}
								field.setValue(content);
							}
							break;
						case TEXT:
							if ((field.getString()==null) || (field.getString()!=textArea.getValue())) {
								String content = textArea.getValue();
								if (field.getCapitalizationType()==CapitalizationType.UPPERCASE) {
									content = content.toUpperCase();
								}
								field.setValue(content);
							}
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
		textArea.setRequiredIndicatorVisible(required);
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
		if ((this.getRequired()) && (this.textArea.getValue().toString().length()==0)) {
			event.setValid(false);
			event.setValidationAdvice("Conteúdo obrigatório!");
		}
		
		// Caso ainda seja valido o conteudo do campo
		if (event.isValid()) {
			event.setValue(textArea.getValue());
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
		textArea.focus();
		textArea.selectAll();
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

		if (content!=null) {
			if (field.getCapitalizationType()==CapitalizationType.UPPERCASE) {
				content = content.toUpperCase();
			}
			textArea.setValue(content);
		}
		else {
			textArea.setValue("");
		}
	}
	@Override
	public String getString() {
		String content = textArea.getValue();
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
			textArea.setValue(numberFormat.format(value));
		}
		else {
			textArea.setValue("");
		}
	}
	
	@Override
	public Integer getInteger() {
		Integer retorno = null;
		if (!textArea.getValue().isEmpty()) {
			NumberFormat numberFormat = new DecimalFormat("#,##0");
			try {
				retorno = numberFormat.parse(textArea.getValue()).intValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return retorno;
		
		/*
		Integer retorno = null;
		
		// Converte o conteudo do texto que esta no campo para o seu valor
		String conteudoString = textField.getValue();
		conteudoString = conteudoString.replace(".","");
		
		// Conteudo é o tipo desejado (valida seu conteudo)
		if (Utils.isInteger(conteudoString)) {
			 retorno = Integer.parseInt(conteudoString);
		}
		else {
			// Conteudo nao e valido
			retorno = null;
			throw new IllegalAccessError("Invalid textField ["+ field.getFieldName() +"] content!");
		}
		return retorno;
		*/
	}
	
	@Override
	public void setValue(Double value) {
		this.setDouble(value);
	}
	@Override
	public void setDouble(Double value) {
		if (value!=null) {
			NumberFormat numberFormat = new DecimalFormat("#,##0.00");
			textArea.setValue(numberFormat.format(value));
		}
		else {
			textArea.setValue("");
		}
	}
	@Override
	public Double getDouble() {
		Double retorno = null;
		if (!textArea.getValue().isEmpty()) {
			NumberFormat numberFormat = new DecimalFormat("#,##0.00");
			try {
				retorno = numberFormat.parse(textArea.getValue()).doubleValue();
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
			textArea.setValue(numberFormat.format(value));
		}
		else {
			textArea.setValue("");
		}
		/*
		if (this.getField().getMask().isEmpty()) {
			textField.setValue(value.toString());
		}
		else {
			NumberFormat numberFormat = new DecimalFormat(field.getMask());
			textField.setValue(numberFormat.format(value));
		}
		*/
	}
	@Override
	public Float getFloat() {
		Float retorno = null;
		if (!textArea.getValue().isEmpty()) {
			NumberFormat numberFormat = new DecimalFormat("#,##0.0000");
			try {
				retorno = numberFormat.parse(textArea.getValue()).floatValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return retorno;

		/*
		Float retorno = null;
		
		String conteudoString = textField.getValue();
		conteudoString = conteudoString.replace(".", "");
		conteudoString = conteudoString.replace(",", ".");

		// Conteudo é o tipo desejado (valida seu conteudo)
		if (Utils.isFloat(conteudoString)) {
			Float conteudo = Float.parseFloat(conteudoString);
			// Conteudo do parse do camo e diferente do conteudo do field
			if (!conteudo.equals(field.getFloat())) {
				field.setFloat(conteudo);
			}
		}
		else {
			// Conteudo nao é valido
			retorno = null;
			throw new IllegalAccessError("Invalid textField ["+ field.getFieldName() +"] content!");
		}
		
		return retorno;
		*/
	}
	
	@Override
	public void setValue(Date value) {
		this.setDate(value);
	}
	@Override
	public void setDate(Date value) {
		if (value==null) {
			textArea.setValue("");
		}
		else {
			String mask = this.getField().getMask();
			if (mask.isEmpty()) {
				mask = "dd/MM/yyyy";
			}
			SimpleDateFormat simpleDate = new SimpleDateFormat(mask);
			textArea.setValue(simpleDate.format(value));
		}
	}
	@Override
	public Date getDate() {
		Date retorno = null;
		if ((textArea.getValue()!=null) && (!textArea.getValue().isEmpty())) {
			if (Utils.isDate(textArea.getValue())) {
				String mask = this.getField().getMask();
				if (mask.isEmpty()) {
					mask = "dd/MM/yyyy";
				}
				SimpleDateFormat simpleDate = new SimpleDateFormat(mask);
				try {
					retorno = simpleDate.parse(textArea.getValue());
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
