package com.evolucao.rmlibrary.database.events;

import java.util.EventListener;
import java.util.EventObject;

public class RmNativeSelectValidateEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private boolean valid = true;
	private Object value = null;
	String validationAdvice = null;
	
	public void setValidationAdvice(String validationAdvice) {
		this.validationAdvice = validationAdvice;
	}
	public String getValidationAdvice() {
		return this.validationAdvice;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	public Object getValue() {
		return this.value;
	}
	
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Boolean isValid() {
		return this.valid;
	}
	
	public RmNativeSelectValidateEvent(Object source) {
		super(source);
	}

	public interface RmNativeSelectValidateEventListener extends EventListener {
		public void onValidate(RmNativeSelectValidateEvent event);
	}
}
