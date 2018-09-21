package com.evolucao.rmlibrary.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.evolucao.rmlibrary.database.enumerators.FieldType;

import  java.io.Serializable;


public class Record implements Serializable  {
	private List<Field> fields = new ArrayList<Field>();
	private Table tableBase = null;
	
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public List<Field> getFields() {
		return this.fields;
	}
	
	public Record(Table tableBase) {
		this.tableBase = tableBase;
	}
	
	public void loadRecordFromResultSet(ResultSet resultSet) {
		try {
			for (Field field: tableBase.getFields()) {
				Field field2 = this.addField(field.getFieldName());
				FieldType fieldType = field.getFieldType();
				
				if (fieldType.equals(FieldType.VARCHAR)) {
					field2.setString(resultSet.getString(field.getFieldName()));
				} else if (fieldType.equals(FieldType.TEXT)) {
					field2.setString(resultSet.getString(field.getFieldName()));
				} else if (fieldType.equals(FieldType.INTEGER)) {
					field2.setInteger(resultSet.getInt(field.getFieldName()));
				} else if (fieldType.equals(FieldType.DOUBLE)) {
					field2.setDouble(resultSet.getDouble(field.getFieldName()));
				} else if (fieldType.equals(FieldType.FLOAT)) {
					field2.setFloat(resultSet.getFloat(field.getFieldName()));
				} else if (fieldType.equals(FieldType.DATE)) {
					field2.setDate(resultSet.getDate(field.getFieldName()));
				} else if (fieldType.equals(FieldType.DATETIME)) {
					field2.setDate(resultSet.getDate(field.getFieldName()));
				} else if (fieldType.equals(FieldType.BOOLEAN)) {
					field2.setBoolean(resultSet.getBoolean(field.getFieldName()));
				} else if (fieldType.equals(FieldType.BIT)) {
					field2.setBoolean(resultSet.getBoolean(field.getFieldName()));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Field fieldByName(String fieldName) {
		Field retorno = null;
		for (Field field : this.getFields()) {
			String vfieldName = field.getFieldName();
			if (vfieldName.equals(fieldName)) {
				retorno = field;
				break;
			}
		}
		
		if (retorno==null) {
			throw new IllegalAccessError("Field " + fieldName + " does not exists in Record.");
		}
		
		return retorno;
	}
	
	public Field addField(String fieldName) {
		Field retorno = null;
		for (Field field : this.getFields()) {
			String vfieldName = field.getFieldName();
			if (vfieldName.equals(fieldName)) {
				retorno = field;
				break;
			}
		}
		
		if (retorno==null) {
			retorno = new Field(fieldName, null, null);
		}
		
		return retorno;
	}
	/*
	 * 
	 */
	
	public void setString(String fieldName, String value) {
		Field field = this.addField(fieldName);
		field.setValue(value);
		this.fields.add(field);
	}
	public String getString(String fieldName) {
		String retorno = null;
		Field field = this.fieldByName(fieldName);
		if (field!=null) {
			retorno = field.getString();
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

	/*
	 * 
	 */
	public void setInteger(String fieldName, Integer value) {
		Field field = this.addField(fieldName);
		field.setValue(value);
		this.fields.add(field);
	}
	public Integer getInteger(String fieldName) {
		return this.fieldByName(fieldName).getInteger();
	}

	/*
	 * 
	 */
	public void setDouble(String fieldName, Double value) {
		Field field = this.addField(fieldName);
		field.setValue(value);
		this.fields.add(field);
	}
	public Double getDouble(String fieldName) {
		return this.fieldByName(fieldName).getDouble();
	}
	/*
	 * 
	 */

	public void setFloat(String fieldName, Float value) {
		Field field = this.addField(fieldName);
		field.setValue(value);
		this.fields.add(field);
	}
	public Float getFloat(String fieldName) {
		return this.fieldByName(fieldName).getFloat();
	}

	/*
	 * 
	 */
	public void setDate(String fieldName, Date value) {
		Field field = this.addField(fieldName);
		field.setValue(value);
		this.fields.add(field);
	}
	public Date getDate(String fieldName) {
		return this.fieldByName(fieldName).getDate();
	}

	public void setDateTime(String fieldName, Date value) {
		Field field = this.addField(fieldName);
		field.setValue(value);
		this.fields.add(field);
	}
	public Date setDateTime(String fieldName) {
		return this.fieldByName(fieldName).getDate();
	}

	/*
	 * 
	 */
	public void setBoolean(String fieldName, Boolean value) {
		Field field = this.addField(fieldName);
		field.setValue(value);
		this.fields.add(field);
	}
	public Boolean getBoolean(String fieldName) {
		return this.fieldByName(fieldName).getBoolean();
	}

	// ***********************
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
}
