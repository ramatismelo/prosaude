package com.evolucao.rmlibrary.ui.controller;

import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.server.Resource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.evolucao.rmlibrary.database.ChaveValor;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.KeyValue;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.ui.controller.enumerators.CapitalizationType;
import com.evolucao.rmlibrary.ui.fields.RmComboBox;
import com.evolucao.rmlibrary.ui.fields.RmDateTimeField;
import com.evolucao.rmlibrary.ui.fields.RmFieldBase;
import com.evolucao.rmlibrary.ui.fields.RmNativeSelect;
import com.evolucao.rmlibrary.ui.fields.RmPasswordField;
import com.evolucao.rmlibrary.ui.fields.RmTextArea;
import com.evolucao.rmlibrary.ui.fields.RmTextField;

public class ControlContentField extends ControlBase {
	String fieldName = null;
	String fieldTitle = null;
	Integer expandRatio = null;
	Boolean minWidthStyleDeployed = false;
	Boolean visible = true;
	Resource icon = null;
	RmFieldBase rmFieldBase = null;
	
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public Boolean getVisible() {
		return this.visible;
	}

	public void setRmFieldBase(RmFieldBase rmFieldBase) {
		this.rmFieldBase = rmFieldBase;
	}
	public RmFieldBase getRmFieldBase() {
		return this.rmFieldBase;
	}
	
	public void setIcon(Resource icon) {
		this.icon = icon;
	}
	public Resource getIcon() {
		return this.icon;
	}
	
	public void setMinWidthStyleDeployed(boolean minWidthStyleDeployed) {
		this.minWidthStyleDeployed = minWidthStyleDeployed;
	}
	public boolean getMinWidthStyleDeployed() {
		return this.minWidthStyleDeployed;
	}
	
	public ControlContentField() {
		
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
		this.fieldTitle = fieldName;
	}
	public String getFieldName() {
		return this.fieldName;
	}
	
	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}
	public String getFieldTitle() {
		return this.fieldTitle;
	}
	
	public void setExpandRatio(Integer expandRatio) {
		this.expandRatio = expandRatio;
	}
	public Integer getExpandRatio() {
		return this.expandRatio;
	}
	
	@Override
	public CssLayout deploy() {
		CssLayout retorno = new CssLayout();
		
		ControlForm form = (ControlForm) this.getForm();
		
		if ((form!=null) && (form.getTable()!=null)) {
			Table tblTable = form.getTable();
			Field field  = tblTable.fieldByName(this.fieldName);
			
			if (field.getInternalSearch()!=null) {
				RmNativeSelect rmNativeSelect = new RmNativeSelect(this);
			}
			else if (field.getPassword()) {
				RmPasswordField rmPasswordField = new RmPasswordField(this);
			}
			else if (field.getFieldType()==FieldType.TEXT) {
				RmTextArea textArea = new RmTextArea(this);
			}
			else if ((field.getFieldType()==FieldType.DATE) || (field.getFieldType()==FieldType.DATETIME)) {
				RmDateTimeField rmDateTimeField = new RmDateTimeField(this);
			}
			else if (field.getComboBox()!=null) {
				RmComboBox comboBox = new RmComboBox(this);
			}
			else {
				RmTextField textField = new RmTextField(this);
			}

			if (this.getFirstComponentInContainer()) {
				//fieldContainer.addStyleName("first-item");
				this.getRmFieldBase().addStyleName("first-item");
			}
			else {
				//fieldContainer.addStyleName("margin-left");
				this.getRmFieldBase().addStyleName("margin-left");
			}
			
			if (this.getExpandRatio()>0) {
				//fieldContainer.addStyleName("flex-grow1");
				this.getRmFieldBase().addStyleName("flex-grow1");
			}

			// Caso a linha esteja programada para expandir seus elementos de forma proporcional
			if ((this.getOwner() instanceof ControlHorizontalContainer) && (((ControlHorizontalContainer)this.getOwner()).getAutoExpandRatio())) {
				ControlHorizontalContainer hc = (ControlHorizontalContainer) this.getOwner();
				Double widthTotal = 0d;
				for (ControlBase containerBase: hc.getComponentList()) {
					widthTotal += containerBase.getWidth();
				}
				
				Double perc = ((this.getWidth() * 100) / widthTotal);
				this.getRmFieldBase().setWidth(perc+"%");
				this.getRmFieldBase().addStyleName(this.getFieldName()+ "-minwidth");
				
				if (!this.getMinWidthStyleDeployed()) {
    				Styles styles = Page.getCurrent().getStyles();
    				styles.add("."+this.getFieldName()+"-minwidth { min-width:" + this.getWidth()  + "px;}");
					this.setMinWidthStyleDeployed(true);
				}
			}
			else {
				if ((this.getMinimumWidth()!=null) && (this.getMinimumWidth()>0) && (this.getWidth()!=null) && (this.getWidth()>0)) {
					this.getRmFieldBase().setWidth(this.getWidth()+"%");
					this.getRmFieldBase().addStyleName(this.getFieldName()+ "-minwidth");

					if (!this.getMinWidthStyleDeployed()) {
						Styles styles = Page.getCurrent().getStyles();
						styles.add("."+this.getFieldName()+"-minwidth { min-width:" + this.getMinimumWidth()  + "px;}");
						this.setMinWidthStyleDeployed(true);
					}
				}
				else {
					if (this.getWidth()>0) {
						this.getRmFieldBase().setWidth(this.getWidth() + "px");
					}
				}
			}
			
			if ((this.getHeight()!=null) && (this.getHeight()>0)) {
				this.getRmFieldBase().setHeight(this.getHeight() + "px");
			}
			
			if (form.getDefaultSection()!=null) {
				form.getDefaultSection().addField(field);
			}
			
			// Mask
			switch (field.getFieldType()) {
			case VARCHAR:
				if ((field.getMask()!=null) && (!field.getMask().isEmpty())) {  
					com.vaadin.ui.JavaScript.getCurrent().execute("$('."+this.getFieldName()+"').find('.v-textfield').inputmask('"+field.getMask()+"');");
					UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').change(function(){})");
					UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').focus(function() {var self = $(this);setTimeout(function() {self.select();}, 0);});");
				}
				break;
			case INTEGER:
				//com.vaadin.ui.JavaScript.getCurrent().execute("$('."+this.getFieldName()+"').find('.v-textfield').inputmask({alias: 'numeric', groupSeparator: '.', radixPoint: ',', autoGroup: true, clearMaskOnLostFocus: 1, autoUnmask: true});");
				com.vaadin.ui.JavaScript.getCurrent().execute("$('."+this.getFieldName()+"').find('.v-textfield').inputmask({alias: 'numeric', groupSeparator: '.', radixPoint: '', digits: 2, autoGroup: true, clearMaskOnLostFocus: 1, autoUnmask: true});");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').change(function(){})");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').focus(function() {var self = $(this);setTimeout(function() {self.select();}, 0);});");
				break;
			case DOUBLE:
				//com.vaadin.ui.JavaScript.getCurrent().execute("$('."+this.getFieldName()+"').find('.v-textfield').inputmask({alias: 'numeric', groupSeparator: '.', radixPoint: ',', digits: 2, digitsOptional: false, autoGroup: true, clearMaskOnLostFocus: 1, autoUnmask: true});");
				com.vaadin.ui.JavaScript.getCurrent().execute("$('."+this.getFieldName()+"').find('.v-textfield').inputmask({alias: 'numeric', groupSeparator: '.', radixPoint: ',', digits: 2, digitsOptional: false, autoGroup: true, clearMaskOnLostFocus: 1, autoUnmask: true});");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').change(function(){})");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').focus(function() {var self = $(this);setTimeout(function() {self.select();}, 0);});");
				break;
			case FLOAT:
				//com.vaadin.ui.JavaScript.getCurrent().execute("$('."+this.getFieldName()+"').find('.v-textfield').inputmask({alias: 'numeric', groupSeparator: '.', radixPoint: ',', digits: 4, digitsOptional: false, autoGroup: true, clearMaskOnLostFocus: 1, autoUnmask: true});");
				com.vaadin.ui.JavaScript.getCurrent().execute("$('."+this.getFieldName()+"').find('.v-textfield').inputmask({alias: 'numeric', groupSeparator: '.', radixPoint: ',', digits: 4, digitsOptional: false, autoGroup: true, clearMaskOnLostFocus: 1, autoUnmask: true});");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').change(function(){})");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').focus(function() {var self = $(this);setTimeout(function() {self.select();}, 0);});");
				break;
			case DATE:
				com.vaadin.ui.JavaScript.getCurrent().execute("$('."+this.getFieldName()+"').find('.v-textfield').inputmask({alias: 'dd/mm/yyyy', placeholder: '__/__/____'});");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').change(function(){})");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').focus(function() {var self = $(this);setTimeout(function() {self.select();}, 0);});");
				break;
			case DATETIME:
				com.vaadin.ui.JavaScript.getCurrent().execute("$('."+this.getFieldName()+"').find('.v-textfield').inputmask({alias: 'dd/mm/yyyy', placeholder: '__/__/____'});");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').change(function(){})");
				UI.getCurrent().getPage().getJavaScript().execute("$('."+this.getFieldName()+"').find('.v-textfield').focus(function() {var self = $(this);setTimeout(function() {self.select();}, 0);});");
				break;
			}
			
			if ((form.getTable().getTableStatus()==TableStatus.INSERT) || (form.getTable().getTableStatus()==TableStatus.UPDATE)) {
				if (field.getRequired()) {
					this.getRmFieldBase().setRequired(true);
					//fieldContainer.setImmediate(true);
				}
			}
			else if (form.getTable().getTableStatus()==TableStatus.FILTER) {
				if (field.getRequiredInFilter()) {
					this.getRmFieldBase().setRequired(true);
					//fieldContainer.setImmediate(true);
				}
			}
			
			if (this.getIcon()!=null) {
				this.getRmFieldBase().setIcon(this.getIcon());
			}
			
			//form.getFieldSet().addField(this.getFieldName(), fieldContainer);
			retorno = this.getRmFieldBase();
		}
		
		return retorno;
	}
}
