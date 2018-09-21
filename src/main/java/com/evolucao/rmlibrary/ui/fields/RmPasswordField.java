package com.evolucao.rmlibrary.ui.fields;

import java.util.Date;

import javax.swing.event.EventListenerList;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.PasswordField;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.ValidateEvent;
import com.evolucao.rmlibrary.database.events.ValidateEvent.ValidateEventListener;
import com.evolucao.rmlibrary.ui.controller.ControlContentField;

public class RmPasswordField extends RmFieldBase {
	String fieldName = null;
	PasswordField passwordField = new PasswordField();
	protected EventListenerList listenerList = new EventListenerList();

	/*
	@Override
	public void setValue(String value) {
		passwordField.setValue(value);
	}
	
	@Override
	public String getValue() {
		return passwordField.getValue();
	}
	*/

	public RmPasswordField() {
		
	}
	
	public RmPasswordField(ControlContentField controlContentField) {
		super(controlContentField);
		this.InitializeRmFieldBase();
		
		// A partir deste ponto configura as caracteristicas pessoais do RmPasswordField
		addStyleName("rmtextfield");
		
		fieldContainer.addStyleName("field-container");
		
		if (this.getField().getTable().getTableStatus()==TableStatus.INSERT) {
			if (this.getField().getReadOnlyInsert()) {
				passwordField.setReadOnly(true);
			}
		}
		else if (this.getField().getTable().getTableStatus()==TableStatus.UPDATE) {
			if (this.getField().getReadOnlyUpdate()) {
				passwordField.setReadOnly(true);
			}
		}
		
		addComponent(fieldContainer);
		{
			//TextField field = new TextField(caption);
			passwordField.setCaption(controlContentField.getFieldTitle());
			passwordField.setValueChangeMode(ValueChangeMode.BLUR);
			passwordField.addValueChangeListener(new ValueChangeListener<String>() {
				@Override
				public void valueChange(ValueChangeEvent<String> event) {
					if (field!=null) {
						// Quando o valor de um campo estiver sendo carregado do banco de dados no inicio de uma edicao, ele nao deve 
						// ser validado. 
						if (!field.getTable().getLoadingDataToForm()) {
							if (field.getFieldType()==FieldType.VARCHAR) {
								if (field.getString()!=passwordField.getValue()) {
									field.setValue(passwordField.getValue());
								}
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
			fieldContainer.addComponent(passwordField);
		}
		
		lblMensagemErro.addStyleName("hide-item");
		lblMensagemErro.addStyleName("validation-advice");
		addComponent(lblMensagemErro);
	}
	
	public void updateContent() {
		
	}
	
	@Override
	public void setRequired(boolean required) {
		this.passwordField.setRequiredIndicatorVisible(required);
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
		if ((this.getRequired()) && (this.passwordField.getValue().toString().length()==0)) {
			event.setValid(false);
			event.setValidationAdvice("Conteúdo obrigatório!");
		}
		
		// Caso ainda seja valido o conteudo do campo
		if (event.isValid()) {
			event.setValue(this.passwordField.getValue());
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
		passwordField.focus();
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
		passwordField.setValue(value);
	}

	@Override
	public String getString() {
		return passwordField.getValue();
	}

	@Override
	public void setValue(Integer value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInteger(Integer value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getInteger() {
		// TODO Auto-generated method stub
		return null;
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
	public void setValue(Date date) {
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
