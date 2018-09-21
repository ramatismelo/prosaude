package com.evolucao.rmlibrary.ui.fields;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.enumerators.FieldType;
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
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class RmDateTimeField extends RmFieldBase {
	CssLayout fieldButtonContainer = null;
	DateTimeField textField = new DateTimeField();
	protected EventListenerList listenerList = new EventListenerList();
	
	public void setReadOnly(Boolean readOnly) {
		this.textField.setReadOnly(readOnly);
	}
	public Boolean getReadOnly() {
		return this.textField.isReadOnly();
	}

	public RmDateTimeField() {
		super();
		addStyleName("rmtextfield");
		this.updateContent();
	}
	
	public RmDateTimeField(ControlContentField controlContentField) {
		super(controlContentField);
		
		this.InitializeRmFieldBase();
		
		// A partir deste ponto configura caracteristicas pessoais ao RmTextField
		this.addStyleName("rmtextfield");
		
		if (getField().getFieldType()==FieldType.DATE) {
			textField.setDateFormat("dd/MM/yyyy");
		}
		else if (getField().getFieldType()==FieldType.DATETIME) {
			textField.setDateFormat("dd/MM/yyyy HH:mm:ss");
		}
		
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
		textField.addValueChangeListener(new ValueChangeListener<LocalDateTime>() {
			@Override
			public void valueChange(ValueChangeEvent<LocalDateTime> event) {
				if (field!=null) {
					// Quando o valor de um campo estiver sendo carregado do banco de dados no inicio de uma edicao, ele nao deve 
					// ser validado. 
					if (!field.getTable().getLoadingDataToForm()) {
						switch (field.getFieldType()) {
						case DATE:
							// Nao tem data no field e tem no TextField, atualiza o field
							if (field.getDate()==null) {
								field.setDate(getDateFromDateTimeField());
							}
							
							// Tem data definida no field e nao tem data no textField, atualiza o field com null
							if ((field.getDate()!=null) && (textField.getValue()==null)) {
								field.setDate(null);
							}
							
							// Tem data definida do field e data definida no textField, caso seja diferentes, atualiza o field
							if ((field.getDate()!=null) && (textField.getValue()!=null)) {
								Date data = getDateFromDateTimeField();
								if (!field.getDate().equals(data)) {
									field.setDate(data);
								}
							}
							break;
						case DATETIME:
							// Nao tem data no field e tem no TextField, atualiza o field
							if ((field.getDate()==null) && (textField.getValue()!=null)) {
								field.setDate(getDateFromDateTimeField());
							}
							
							// Tem data definida no field e nao tem data no textField, atualiza o field com null
							if ((field.getDate()!=null) && (textField.getValue()==null)) {
								field.setDate(null);
							}
							
							// Tem data definida do field e data definida no textField, caso seja diferentes, atualiza o field
							if ((field.getDate()!=null) && (textField.getValue()!=null)) {
								Date data = getDateFromDateTimeField();
								if (!field.getDate().equals(data)) {
									field.setDate(data);
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
			//event.setValue(textField.getValue());
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
	}
	
	@Override
	public String getString() {
		return null;
	}

	@Override
	public void setValue(Integer value) {
	}
	
	@Override
	public void setInteger(Integer value) {
	}
	
	@Override
	public Integer getInteger() {
		return null;
	}
	
	@Override
	public void setValue(Double value) {
		this.setDouble(value);
	}
	
	@Override
	public void setDouble(Double value) {
	}
	
	@Override
	public Double getDouble() {
		return null;
	}
	
	@Override
	public void setValue(Float value) {
	}
	
	@Override
	public void setFloat(Float value) {
	}
	
	@Override
	public Float getFloat() {
		return null;
	}
	
	@Override
	public void setValue(Date value) {
		this.setDate(value);
	}
	
	@Override
	public void setDate(Date value) {
		if (value==null) {
			textField.setValue(null);
		}
		else {
			setDateToDateTimeField(value);
		}
	}
	
	@Override
	public Date getDate() {
		Date retorno = null;
		if ((textField.getValue()!=null) && (textField.getValue()!=null)) {
			retorno = getDateFromDateTimeField();
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
	
	public Date getDateFromDateTimeField() {
		Date retorno = null;
		
		try {
			if (getField().getFieldType()==FieldType.DATE) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String dataString = Utils.strZero(textField.getValue().getDayOfMonth(), 2) + "/" +  Utils.strZero(textField.getValue().getMonthValue(), 2) +  "/" + textField.getValue().getYear() + " 00:00:00";
				retorno = simpleDateFormat.parse(dataString);
			}
			else if (getField().getFieldType()==FieldType.DATETIME) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String dataString = Utils.strZero(textField.getValue().getDayOfMonth(), 2) + "/" +  Utils.strZero(textField.getValue().getMonthValue(), 2) +  "/" + textField.getValue().getYear() + " " + Utils.strZero(textField.getValue().getHour(), 2)+ ":" + Utils.strZero(textField.getValue().getMinute(), 2) + ":" + Utils.strZero(textField.getValue().getSecond(), 2);
				retorno = simpleDateFormat.parse(dataString);
			}
		}
		catch (Exception e) {
		   System.out.println(e.getMessage());	
		}
		
		return retorno;
	}
	
	public void setDateToDateTimeField(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		textField.setValue(LocalDateTime.of(year, month, day, hour, minute, second));
	}
}
