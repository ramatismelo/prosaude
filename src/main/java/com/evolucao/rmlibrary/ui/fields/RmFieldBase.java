package com.evolucao.rmlibrary.ui.fields;

import java.util.Date;

import com.vaadin.data.Validator;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.ValidateEvent;
import com.evolucao.rmlibrary.database.events.ValidateEvent.ValidateEventListener;
import com.evolucao.rmlibrary.ui.controller.ControlBase;
import com.evolucao.rmlibrary.ui.controller.ControlContentField;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.enumerators.CapitalizationType;

public abstract class RmFieldBase extends CssLayout {
	CssLayout fieldContainer = new CssLayout();
	Label lblMensagemErro = new Label("");
	Field field = null;
	boolean required = false;
	//String caption = null;
	//CapitalizationType capitalizationType = CapitalizationType.UPPERCASE;
	ControlContentField controlContentField = null;
	
	public abstract void setValue(String value);
	public abstract void setString(String value);
	public abstract String getString();

	public abstract void setValue(Integer value);
	public abstract void setInteger(Integer value);
	public abstract Integer getInteger();
	
	public abstract void setValue(Double value);
	public abstract void setDouble(Double value);
	public abstract Double getDouble();
	
	public abstract void setValue(Float value);
	public abstract void setFloat(Float value);
	public abstract Float getFloat();
	
	public abstract void setValue(Date value);
	public abstract void setDate(Date value);
	public abstract Date getDate();
	
	public abstract void setValue(Boolean value);
	public abstract void setBoolean(Boolean value);
	public abstract Boolean getBoolean();

	public abstract void setRequired(boolean required);
	public abstract boolean getRequired();
	
	public void setControlContentField(ControlContentField controlContentField) {
		this.controlContentField = controlContentField;
	}
	public ControlContentField getControlContentField() {
		return this.controlContentField;
	}
	
	/*
	public void setCapitalizationType(CapitalizationType capitalizationType) {
		this.capitalizationType = capitalizationType;
	}
	public CapitalizationType getCapitalizationType() {
		return this.capitalizationType;
	}
	*/

	public void setCaption(String caption) {
		this.getControlContentField().setFieldTitle(caption);
	}
	public String getCaption() {
		return this.getControlContentField().getFieldTitle();
	}
	
	public void setField(Field field) {
		this.field = field;
	}
	public Field getField() {
		return this.field;
	}
	
	public void setAdviceMessageError(String adviceMessageError) {
		this.lblMensagemErro.setValue(adviceMessageError);
	}
	public String getAdviceMessageError() {
		return this.lblMensagemErro.getValue();
	}
	
	public RmFieldBase() {
		
	}
	
	/*
	public RmFieldBase(String caption) {
		this.caption = caption;
	}
	*/
	
	public RmFieldBase(ControlContentField controlContentField) {
		//*************************************************************************************
		// Faz a referencia cruzada entreo o controlContentField e o RmFieldBase
		// Coloca no RmFieldBase um ponteiro para o controlContentField que esta controlando ele
		this.controlContentField = controlContentField;
		
	}

	/**
	 * Usado para configurar as caracteristicas do rmFieldBase, esses procedimentos nao sao
	 * executados no create do elemento porque necessita que a sequencia de creacao do componente
	 * esteja concluida
	 */
	public void InitializeRmFieldBase() {
		// Coloca no ControlContentField um ponteiro para o RmFieldBase que ele esta controlando
		controlContentField.setRmFieldBase(this);

		//*************************************************************************************
		// Faz a referencia cruzada entreo o RmFieldBase e o Field do Table
		// Aponta no rmFieldBase o field que ele esta atualizando
		ControlForm form = (ControlForm) this.getControlContentField().getForm();
		Table tblTable = form.getTable();
		this.setField(tblTable.fieldByName(this.getControlContentField().getFieldName()));
		
		// Atualiza no field o rmFieldBase que esta atualizando ele
		this.getField().setRmFieldBase(this);
		
		// Coloca o nome do field do rmFieldBase
		this.addStyleName(this.getControlContentField().getFieldName());
	}
	
	public void updateFieldValidationStatus(boolean valid) {
		if (valid) {
			this.lblMensagemErro.addStyleName("hide-item");
			this.fieldContainer.removeStyleName("validation-failed");
		}
		else {
			this.lblMensagemErro.removeStyleName("hide-item");
			this.fieldContainer.addStyleName("validation-failed");
		}
	}
	
	public void setValidationAdvice(String message) {
		this.lblMensagemErro.setValue(message);
	}

	public Boolean validate() {
		return true;
	}
}
