package com.evolucao.rmlibrary.ui.fields;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RmFieldSet {
	Map<String, RmFieldBase> fields2 = new HashMap<String, RmFieldBase>(); 
	//List<RmFieldBase> fields = new ArrayList<RmFieldBase>();
	
	public void setFields2(Map<String, RmFieldBase> fields) {
		this.fields2 = fields;
	}
	public Map<String, RmFieldBase> getFields2() {
		return this.fields2;
	}
	
	/*
	public void setFields(List<VerticalLayout> fields) {
		this.fields = fields;
	}
	public List<VerticalLayout> getFields() {
		return this.fields;
	}
	*/

	public RmFieldSet() {
		
	}

	public void addField(String fieldName, RmFieldBase field) {
		this.getFields2().put(fieldName, field);
	}
	
	public Boolean validate() {
		System.out.println("validate do fieldSet");
		Boolean retorno = true;
		
		/*
		for (RmFieldBase field : fields) {
			if (!(field.validate())) {
				retorno = false;
			}
		}
		*/
		//HashMap<String, HashMap> selects = new HashMap<String, HashMap>();

		for(Entry<String, RmFieldBase> entry : this.getFields2().entrySet()) {
		    String key = entry.getKey();
		    RmFieldBase field = entry.getValue();
			if (!(field.validate())) {
				retorno = false;
			}
		}
		
		return retorno;
	}

	public void setValue(String fieldName, String value) {
		RmFieldBase field = this.getFields2().get(fieldName);
		field.setValue(value);
		
		/*
		CssLayout field = this.getFields2().get(fieldName);
		if (field instanceof RmTextField) {
			((RmTextField) field).setValue(value);
		}
		else if (field instanceof RmPasswordField) {
			((RmPasswordField) field).setValue(value);
		}
		else if (field instanceof RmNativeSelect) {
			((RmNativeSelect) field).setValue(value);
		}
		*/
	}
	
	public void setValue(String fieldName, Date value) {
		RmFieldBase field = this.getFields2().get(fieldName);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String data = dateFormat.format(value);
		field.setValue(data);
		
		/*
		CssLayout field = this.getFields2().get(fieldName);
		if (field instanceof RmTextField) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String data = dateFormat.format(value);
			((RmTextField) field).setValue(data);
		}
		*/
	}

	public String getString(String fieldName) {
		String retorno = null;
		RmFieldBase rmFieldBase = this.getFields2().get(fieldName);
		retorno = rmFieldBase.getString();
		
		return retorno;
	}
	
	public void setRequired(String fieldName, boolean required) {
		RmFieldBase field = this.getFields2().get(fieldName);
		field.setRequired(required);
	}
	
	public Date getDate(String fieldName) {
		Date retorno = null;
		RmFieldBase rmFieldBase = this.getFields2().get(fieldName);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dataString = rmFieldBase.getString();
		if (!dataString.isEmpty()) {
			Date date = null;
			try {
				date = dateFormat.parse(dataString);
			} catch (ParseException e) {
				// Data invalida
				e.printStackTrace();
			}
			retorno = date;
		}
		
		return retorno;
	}
	
	public RmFieldBase getField(String fieldName) {
		RmFieldBase field = this.getFields2().get(fieldName);
		return field;
	}
	
	public void removeAllFields() {
		this.getFields2().clear();
	}
}


