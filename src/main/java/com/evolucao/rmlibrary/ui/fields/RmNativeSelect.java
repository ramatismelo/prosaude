package com.evolucao.rmlibrary.ui.fields;

import java.util.Date;
import java.util.List;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeSelect;
import com.evolucao.rmlibrary.database.ChaveValor;
import com.evolucao.rmlibrary.database.KeyValue;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.ui.controller.ControlContentField;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.utils.Utils;

public class RmNativeSelect extends RmFieldBase {
	List<KeyValue> items = null;
	NativeSelect<KeyValue> nativeSelect = new NativeSelect<KeyValue>();
	
	public void setItems(List<KeyValue> items) {
		this.items = items;
		this.updateContent();
	}
	public List<KeyValue> getItems() {
		return this.items;
	}
	
	@Override
	public void setRequired(boolean required) {
		this.required = required;
		nativeSelect.setRequiredIndicatorVisible(required);
	}
	@Override
	public boolean getRequired() {
		return this.required;
	}

	public RmNativeSelect() {
		super();
		addStyleName("rmtextfield");
		this.updateContent();
	}
	
	public RmNativeSelect(ControlContentField controlContentField) {
		super(controlContentField);
		
		ControlForm form = (ControlForm) this.getControlContentField().getForm();
		Table tblTable = form.getTable();
		this.setItems(tblTable.fieldByName(this.getControlContentField().getFieldName()).getInternalSearch().getItems());
		
		this.nativeSelect.setCaption(this.getCaption());
		if (this.getItems()!=null) {
			this.nativeSelect.setItems(this.getItems());
			this.nativeSelect.setItemCaptionGenerator(p->p.getValue());
		}
		this.nativeSelect.addValueChangeListener(new ValueChangeListener<KeyValue>() {
			@Override
			public void valueChange(ValueChangeEvent<KeyValue> event) {
				if (field!=null) {
					// Quando o valor de um campo estiver sendo carregado do banco de dados no inicio de uma edicao, ele nao deve 
					// ser validado. 
					if (!field.getTable().getLoadingDataToForm()) {
						if (field.getFieldType()==FieldType.VARCHAR) {
							if (event.getValue()==null) {
								field.setString(null);
							}
							else {
								if (field.getString()!=event.getValue().getKey()) {
									field.setValue(event.getValue().getKey());
								}
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
		

		this.InitializeRmFieldBase();
		
		addStyleName("rmtextfield");
		
		// A partir deste ponto configura as caracteristicas pesoais do RmNativeSelect
		this.addStyleName("nativeselect");
		this.addStyleName("textfield-grow-1");
		
		if (this.getField().getTable().getTableStatus()==TableStatus.INSERT) {
			if (this.getField().getReadOnlyInsert()) {
				nativeSelect.setReadOnly(true);
			}
		}
		else if (this.getField().getTable().getTableStatus()==TableStatus.UPDATE) {
			if (this.getField().getReadOnlyUpdate()) {
				nativeSelect.setReadOnly(true);
			}
		}
		
		
		this.updateContent();
	}
	
	public void updateContent() {
		fieldContainer.addStyleName("field-container");
		addComponent(fieldContainer);
		{
			//this.nativeSelect.setCaption(this.getCaption());
			//if (this.getItems()!=null) {
			//	this.nativeSelect.setItems(this.getItems());
			//	this.nativeSelect.setItemCaptionGenerator(p->p.getValue());
			//}
			
			//this.nativeSelect.addValidator(new RmNativeSelectValidator());
			//this.nativeSelect.setNullSelectionAllowed(false);
			fieldContainer.addComponent(this.nativeSelect);
		}
		
		lblMensagemErro.addStyleName("hide-item");
		lblMensagemErro.addStyleName("validation-advice");
		addComponent(lblMensagemErro);
	}

	@Override
	public void setValue(String value) {
		this.setString(value);
	}
	@Override
	public void setString(String value) {
		this.nativeSelect.setSelectedItem(Utils.getKeyValueItem(value, this.getItems()));
	}
	@Override
	public String getString() {
		String retorno = null;
		if (this.nativeSelect.getSelectedItem().isPresent()) {
			retorno = this.nativeSelect.getSelectedItem().get().getKey();
		}
		return retorno;
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
