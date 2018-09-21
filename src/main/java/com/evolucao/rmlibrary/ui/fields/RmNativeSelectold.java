package com.evolucao.rmlibrary.ui.fields;

import java.util.Date;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.events.RmNativeSelectValidateEvent;
import com.evolucao.rmlibrary.database.events.RmNativeSelectValidateEvent.RmNativeSelectValidateEventListener;
import com.vaadin.ui.NativeSelect;

public class RmNativeSelectold extends RmFieldBase {
	/*
	class RmNativeSelectValidator implements Validator<String> {
		@Override
		public void validate(Object value) throws InvalidValueException {
			if (field!=null) {
				field.validate();
			}
		}
		
		public void updateFieldValidationStatus(boolean valid) {
			if (valid) {
				lblMensagemErro.addStyleName("hide-item");
				fieldContainer.removeStyleName("validation-failed");
			}
			else {
				lblMensagemErro.removeStyleName("hide-item");
				fieldContainer.addStyleName("validation-failed");
			}
		}

		@Override
		public ValidationResult apply(String value, ValueContext context) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	*/	
	
	String fieldName = null;
	NativeSelect<String> nativeSelect = new NativeSelect<String>();
	protected EventListenerList listenerList = new EventListenerList();

	/*
	public void setValue(Object newValue) {
		this.nativeSelect.setValue(newValue);
	}
	*/

	/*
	@Override
	public void setValue(String newValue) {
		this.nativeSelect.setValue(newValue);
	}
	
	@Override
	public String getValue() {
		String content = this.nativeSelect.getItemCaption(this.nativeSelect.getValue());
		return content;
	}
	*/
	
	/*
	public String getKey() {
		return this.nativeSelect.getValue().toString();
	}
	
	/*
	public Object getValue() {
		String content = this.nativeSelect.getItemCaption(this.nativeSelect.getValue());
		return content;
	}
	*/
	
	/*
	public String getItemCaption(Object itemId) {
		return nativeSelect.getItemCaption(itemId);
	}
	*/

	public RmNativeSelectold() {
		
	}
	
	public RmNativeSelectold(String caption) {
		/*
		addStyleName("rmtextfield");
		
		fieldContainer.addStyleName("field-container");
		addComponent(fieldContainer);
		{
			this.nativeSelect.addValueChangeListener(new ValueChangeListener<String>() {
				@Override
				public void valueChange(ValueChangeEvent<String> event) {
					if (field!=null) {
						if (field.getFieldType()==FieldType.VARCHAR) {
							if (field.getString()!=event.getValue()) {
								field.setValue(event.getValue());
							}
						}
					}
				}
			});

			//TextField field = new TextField(caption);
			//this.nativeSelect.addValueChangeListener<String>(new ValueChangeListener<String>() {
			//	@Override
			//	public void valueChange(ValueChangeEvent event) {
			//		if (field!=null) {
			//			if (field.getFieldType()==FieldType.VARCHAR) {
			//				if (field.getString()!=getKey()) {
			//					field.setValue(getKey());
			//				}
			//			}
			//		}
			//	}
			});
			this.nativeSelect.setCaption(caption);
			//this.nativeSelect.addValidator(new RmNativeSelectValidator());
			//this.nativeSelect.setNullSelectionAllowed(false);
			fieldContainer.addComponent(this.nativeSelect);
		}
		
		lblMensagemErro.addStyleName("hide-item");
		lblMensagemErro.addStyleName("validation-advice");
		addComponent(lblMensagemErro);
		*/
	}
	
	/*
	public void setNullSelectionAllowed(boolean nullSelectionAllowed) {
		this.nativeSelect.setNullSelectionAllowed(nullSelectionAllowed);
	}
	*/
	/*
	public Item addItem(Object itemId) {
		//return this.nativeSelect.addItem(itemId);
		return null;
	}
	
	/*
	public void setItemCaption(Object itemId, String caption) {
		//this.nativeSelect.setItemCaption(itemId, caption);
	}

	/*
	@Override
	public void setRequired(boolean required) {
		//this.nativeSelect.setRequired(required);
		//this.nativeSelect.setImmediate(true);
	}
	
	@Override
	public boolean getRequired() {
		//return this.nativeSelect.isRequired();
		return true;
	}
	*/
	
	/*
	public void select(Object itemId) {
		//this.nativeSelect.select(itemId);
	}

	/*
	public Boolean valid() {
		return this.validate();
	}
	
	@Override
	public Boolean validate() {
		/*
		RmNativeSelectValidateEvent event = new RmNativeSelectValidateEvent(this);

		// Caso o campo nao possa ficar vazio
		String content = this.nativeSelect.getItemCaption(this.nativeSelect.getValue());
		
		if ((this.getRequired()) && ((content==null) || (content.length()==0))) {
			event.setValid(false);
			event.setValidationAdvice("Conteúdo obrigatório!");
		}
		
		// Caso ainda seja valido o conteudo do campo
		if (event.isValid()) {
			event.setValue(this.nativeSelect.getValue());
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
		return null;
	}
	
	public void focus() {
		this.nativeSelect.focus();
	}
	
	/*************************************************************************/
	public void addValidateEventListener(RmNativeSelectValidateEventListener listener) {
		listenerList.add(RmNativeSelectValidateEventListener.class, listener);
	}
	public void removeValidateEventListener(RmNativeSelectValidateEventListener listener) {
		listenerList.remove(RmNativeSelectValidateEventListener.class, listener);
	}

	void fireValidateEvent(RmNativeSelectValidateEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == RmNativeSelectValidateEventListener.class) {
				((RmNativeSelectValidateEventListener) listeners[i + 1]).onValidate(event);
			}
		}
	}

	@Override
	public void setValue(String value) {
		this.setString(value);
	}

	@Override
	public void setString(String value) {
		if (value==null) {
			this.nativeSelect.setValue("");
		}
		else {
			this.nativeSelect.setValue(value);
		}
	}

	@Override
	public String getString() {
		//return this.nativeSelect.getItemCaption(this.nativeSelect.getValue());
		return null;
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

	@Override
	public void setRequired(boolean required) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getRequired() {
		// TODO Auto-generated method stub
		return false;
	}
}
