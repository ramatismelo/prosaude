package com.evolucao.rmlibrary.database;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.evolucao.rmlibrary.database.enumerators.FieldType;

public class SimpleField {
	private String ffieldName = null;
	private FieldType ffieldType = null;
	private Integer ffieldSize = 0;
	
	private String fstring = null;
	private Integer integer = null;
	private Double fdouble = null;
	private Float ffloat = null;
	private Date fdate = null;
	private Boolean fboolean = null;
	
	public void setString(String string) {
		this.fstring = string;
		this.ffieldType = FieldType.VARCHAR;
	}
	public String getString() {
		return this.fstring;
	}
	
	public void setInteger(Integer value) {
		this.integer = value;
		this.fstring = String.valueOf(value);
		this.ffieldType = FieldType.INTEGER;
	}
	public Integer getInteger() {
		return this.integer;
	}
	
	public void setDouble(Double value) {
		this.fdouble = value;
		this.fstring = String.valueOf(value);
		this.ffieldType = FieldType.DOUBLE;
	}
	public Double getDouble() {
		return this.fdouble;
	}
	
	public void setFloat(Float value) {
		this.ffloat = value;
		this.fstring = String.valueOf(value);
		this.ffieldType = FieldType.FLOAT;
	}
	public Float getFloat() {
		return this.ffloat;
	}
	
	public void setDate(Date value) {
		this.fdate = value;
		if (value!=null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.fstring = dateFormat.format(value);
		}
		else {
			this.fstring = null;
		}
		this.ffieldType = FieldType.DATE;
	}
	public Date getDate() {
		return this.fdate;
	}
	
	public void setDateTime(Date value) {
		this.fdate = value;
		if (value!=null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.fstring = dateFormat.format(value);
		}
		else {
			this.fstring = null;
		}
		this.ffieldType = FieldType.DATETIME;
	}
	public Date getDateTime() {
		return this.getDate();
	}
	
	public void setBoolean(Boolean value) {
		this.fboolean = value;
		this.fstring = String.valueOf(value);
		this.ffieldType = FieldType.BOOLEAN;
	}
	public Boolean getBoolean() {
		return this.fboolean;
	}
	
	public String getFieldName() {
		return ffieldName;
	}
	public void setFieldName(String fieldName) {
		this.ffieldName = fieldName;
	}
	public String getSpecialFieldName() {
		return this.ffieldName.substring(0, 1).toUpperCase() + this.ffieldName.substring(1);
	}

	public FieldType getFieldType() {
		return ffieldType;
	}
	public void setFieldType(FieldType fieldType) {
		this.ffieldType = fieldType;
	}

	public Integer getFieldSize() {
		return ffieldSize;
	}
	public void setFieldSize(Integer fieldSize) {
		this.ffieldSize = fieldSize;
	}

	public SimpleField() {
		
	}
	
	public SimpleField(String fieldName, FieldType fieldType, Integer fieldSize) {
		this.setFieldName(fieldName);
		this.setFieldType(fieldType);
		this.setFieldSize(fieldSize);
	}

	public void setValue(String value) {
		this.setString(value);
	}

	public void setValue(Integer value) {
		this.setInteger(value);
	}

	public void setValue(Double value) {
		this.setDouble(value);
	}

	public void setValue(Float value) {
		this.setFloat(value);
	}

	public void setValue(Date value) {
		this.setDate(value);
	}

	public void setValue(Boolean value) {
		this.setBoolean(value);
	}
	
	public String getValueAsString() {
		String retorno = null;
		NumberFormat numberFormat = null;
		
		switch (this.getFieldType()) {
		case VARCHAR:
			retorno = this.getString();
			break;
		case INTEGER:
			numberFormat = new DecimalFormat("#,##0");
			retorno = numberFormat.format(getInteger());
			break;
		case DOUBLE:
			numberFormat = new DecimalFormat("#,##0.00");
			retorno = numberFormat.format(this.getDouble());
			break;
		case FLOAT:
			numberFormat = new DecimalFormat("#,##0.0000");
			retorno = numberFormat.format(this.getFloat());
			break;
		case DATE:
			if (this.getDate()!=null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				retorno = simpleDateFormat.format(this.getDate());
			}
			else {
				retorno = "";
			}
			break;
		case DATETIME:
			if (this.getDate()!=null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				retorno = simpleDateFormat.format(this.getDate());
			}
			else {
				retorno = "";
			}
			break;
		}
		
		return retorno;
	}
}
