package com.evolucao.rmlibrary.database;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.evolucao.rmlibrary.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimpleRecord  {
	private List<SimpleField> fields = new ArrayList<SimpleField>();
	
	public void setFields(List<SimpleField> fields) {
		this.fields = fields;
	}
	public List<SimpleField> getFields() {
		return this.fields;
	}
	
	public SimpleRecord() {
	}
	
	public boolean fieldExists(String fieldName) {
		boolean retorno = false;
		for (SimpleField simpleField: this.getFields()) {
			if (simpleField.getFieldName().equals(fieldName)) {
				retorno = true;
				break;
			}
		}
		
		return retorno;
	}
	
	public SimpleField fieldByName(String fieldName) {
		SimpleField retorno = null;
		
		for (SimpleField simpleField: this.getFields()) {
			if (simpleField.getFieldName().equals(fieldName)) {
				retorno = simpleField;
				break;
			}
		}

		if (retorno==null) {
			throw new IllegalAccessError("Field " + fieldName + " does not exists in simpleRecord");
		}
		
		return retorno;
	}
	
	public SimpleField addField(String fieldName) {
		SimpleField retorno = null;

		SimpleField simpleField = null;
		for (SimpleField field: this.getFields()) {
			if (field.getFieldName().equals(fieldName)) {
				simpleField = field;
				break;
			}
		}
		
		if (simpleField!=null) {
			retorno = simpleField;
		}
		if (retorno==null) {
			retorno = new SimpleField(fieldName, null, null);
			this.fields.add(retorno);
		}
		
		return retorno;
	}
	
	public void setString(String fieldName, String value) {
		SimpleField field = this.addField(fieldName);
		field.setValue(value);
	}
	public String getString(String fieldName) {
		String retorno = null;
		SimpleField simpleField = this.fieldByName(fieldName);
		if (simpleField!=null) {
			retorno = simpleField.getString();
		}
		return retorno;
	}
	public String getString(String fieldName, String defaultValue) {
		String retorno = defaultValue;
		
		String conteudo=this.getString(fieldName);
		if (conteudo!=null) {
			retorno = conteudo;
		}
		
		return retorno;
	}

	public void setInteger(String fieldName, Integer value) {
		SimpleField field = this.addField(fieldName);
		field.setValue(value);
	}
	public Integer getInteger(String fieldName) {
		return this.fieldByName(fieldName).getInteger();
	}

	public void setDouble(String fieldName, Double value) {
		SimpleField field = this.addField(fieldName);
		field.setValue(value);
	}
	public Double getDouble(String fieldName) {
		return this.fieldByName(fieldName).getDouble();
	}

	public void setFloat(String fieldName, Float value) {
		SimpleField field = this.addField(fieldName);
		field.setValue(value);
	}
	public Float getFloat(String fieldName) {
		return this.fieldByName(fieldName).getFloat();
	}

	public void setDate(String fieldName, Date value) {
		SimpleField field = this.addField(fieldName);
		field.setValue(value);
	}
	public Date getDate(String fieldName) {
		return this.fieldByName(fieldName).getDate();
	}

	public void setDateTime(String fieldName, Date value) {
		SimpleField field = this.addField(fieldName);
		field.setDateTime(value);
	}
	public Date setDateTime(String fieldName) {
		return this.fieldByName(fieldName).getDate();
	}

	public void setBoolean(String fieldName, Boolean value) {
		SimpleField field = this.addField(fieldName);
		field.setValue(value);
	}
	public Boolean getBoolean(String fieldName) {
		return this.fieldByName(fieldName).getBoolean();
	}

	public void setValue(String fieldName, String value) {
		this.setString(fieldName, value);
	}

	public void setValue(String fieldName, Integer value) {
		this.setInteger(fieldName, value);
	}

	public void setValue(String fieldName, Double value) {
		this.setDouble(fieldName, value);
	}

	public void setValue(String fieldName, Float value) {
		this.setFloat(fieldName, value);
	}

	public void setValue(String fieldName, Date value) {
		this.setDate(fieldName, value);
	}

	public void setValue(String fieldName, Boolean value) {
		this.setBoolean(fieldName, value);
	}
	
	public String encode() {
		Gson gson = new Gson();
		String param = gson.toJson(this);
		String encodedString = null;
		try {
			encodedString = Base64.getUrlEncoder().encodeToString(param.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedString;
	}
	
	public static SimpleRecord decode(String content) {
		SimpleRecord simpleRecord = null;
		if (!content.isEmpty()) {
			content = Utils.decodeBase64URL(content);
			Gson gson = new Gson();
			simpleRecord =  gson.fromJson(content, SimpleRecord.class);
		}
		return simpleRecord;
	}
	
	public SimpleRecord deepClone() {
		String encoded = encode();
		SimpleRecord simpleRecord = decode(encoded);
		return simpleRecord;
	}
	
	public SimpleRecord clone() {
		String encoded = toJson();
		return this.fromJson(encoded);
	}
	
	public String toJson() {
		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
		String encoded = gson.toJson(this);
		System.out.println(encoded);
		return encoded;
	}
	
	public SimpleRecord fromJson(String content) {
		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
		SimpleRecord simpleRecord = gson.fromJson(content, SimpleRecord.class); 
		return simpleRecord;
	}
	
	public void loadFromJson(String content) {
		Gson gson = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
		SimpleRecord simpleRecord = gson.fromJson(content, SimpleRecord.class);
		this.setFields(simpleRecord.getFields());
	}
	
	public Integer getIndexOf(String fieldName) {
		Integer retorno = null;
		Integer indice = 0;
		for (SimpleField simpleField: this.getFields()) {
			if (simpleField.getFieldName().equals(fieldName)) {
				retorno = indice;
				break;
			}
			indice++;
		}
		
		if (retorno==null) {
			throw new IllegalAccessError("Field " + fieldName + " does not exists in simpleRecord");
		}
		return retorno;
	}

	/*
	public void setField0(String value) {
		
	}
	*/
	public String getField0() {
		return this.getFields().get(0).getValueAsString();
	}
	
	public String getField1() {
		return this.getFields().get(1).getValueAsString();
	}
	
	public String getField2() {
		return this.getFields().get(2).getValueAsString();
	}
	
	public String getField3() {
		return this.getFields().get(3).getValueAsString();
	}
	
	public String getField4() {
		return this.getFields().get(4).getValueAsString();
	}
	
	public String getField5() {
		return this.getFields().get(5).getValueAsString();
	}
	
	public String getField6() {
		return this.getFields().get(6).getValueAsString();
	}
	
	public String getField7() {
		return this.getFields().get(7).getValueAsString();
	}
	
	public String getField8() {
		return this.getFields().get(8).getValueAsString();
	}
	
	public String getField9() {
		return this.getFields().get(9).getValueAsString();
	}

	public String getField10() {
		return this.getFields().get(10).getValueAsString();
	}
	
	public String getField11() {
		return this.getFields().get(11).getValueAsString();
	}
	
	public String getField12() {
		return this.getFields().get(12).getValueAsString();
	}
	
	public String getField13() {
		return this.getFields().get(13).getValueAsString();
	}

	public String getField14() {
		return this.getFields().get(14).getValueAsString();
	}
	
	public String getField15() {
		return this.getFields().get(15).getValueAsString();
	}
	
	public String getField16() {
		return this.getFields().get(16).getValueAsString();
	}
	
	public String getField17() {
		return this.getFields().get(17).getValueAsString();
	}
	
	public String getField18() {
		return this.getFields().get(18).getValueAsString();
	}
	
	public String getField19() {
		return this.getFields().get(19).getValueAsString();
	}
	
	public String getField20() {
		return this.getFields().get(20).getValueAsString();
	}
}
