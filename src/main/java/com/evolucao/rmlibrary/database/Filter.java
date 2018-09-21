package com.evolucao.rmlibrary.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Filter {
	String ffieldName = null;

	private Boolean fisDefined = false;
	private Boolean fisRange = false;
	
	private String fstring = null;
	private Integer finteger = null;
	private Double fdouble = null;
	private Float ffloat = null;
	private Date fdate = null;
	private Boolean fboolean = null;

	private String fstring2 = null;
	private Integer finteger2 = null;
	private Double fdouble2 = null;
	private Float ffloat2 = null;
	private Date fdate2 = null;
	private Boolean fboolean2 = null;
	
	
	// **************************
	
	public void setString(String string) {
		this.fstring = string;
		this.setIsDefined(true);
	}
	public String getString() {
		return this.fstring;
	}
	
	public void setString2(String string) {
		this.setIsDefined(true);
		this.setIsRange(true);
		this.fstring2 = string;
	}
	public String getString2() {
		return this.fstring2;
	}
	
	//*****************************
	
	public void setInteger(Integer value) {
		this.finteger = value;
		this.fstring = String.valueOf(value);
		this.setIsDefined(true);
	}
	public Integer getInteger() {
		return this.finteger;
	}
	
	public void setInteger2(Integer value) {
		this.setIsDefined(true);
		this.setIsRange(true);
		this.finteger2 = value;
		this.fstring2 = String.valueOf(value);
	}
	public Integer getInteger2() {
		return this.finteger2;
	}

	//************************************
	
	public void setDouble(Double value) {
		this.fdouble = value;
		this.fstring = String.valueOf(value);
		this.setIsDefined(true);
	}
	public Double getDouble() {
		return this.fdouble;
	}
	
	public void setDouble2(Double value) {
		this.setIsDefined(true);
		this.setIsRange(true);
		this.fdouble2 = value;
		this.fstring2 = String.valueOf(value);
	}
	public Double getDouble2() {
		return this.fdouble2;
	}

	//*************************************
	
	public void setFloat(Float value) {
		this.ffloat = value;
		this.fstring = String.valueOf(value);
		this.setIsDefined(true);
	}
	public Float getFloat() {
		return this.ffloat;
	}
	
	public void setFloat2(Float value) {
		this.setIsDefined(true);
		this.setIsRange(true);
		this.ffloat2 = value;
		this.fstring2 = String.valueOf(value);
	}
	public Float getFloat2() {
		return this.ffloat2;
	}
	
	
	//************************************
	public void setDate(Date value) {
		this.fdate = value;
		if (value!=null) {
			//SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			//this.fstring = dateFormat.format(value);
			
			//DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
			//DateTimeFormat dateFormat = new DateTimeFormat("yyyy-MM-dd HH:mm:ss", info) {};
			//this.fstring = dateFormat.format(value);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			this.fstring = dateFormat.format(value);
		}
		else {
			this.fstring = null;
		}
		this.setIsDefined(true);
	}
	public Date getDate() {
		return this.fdate;
	}
	
	public void setDate2(Date value) throws ParseException {
		this.setIsDefined(true);
		this.setIsRange(true);

		this.fdate2 = value;
		if (value!=null) {
		   SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		   this.fstring2 = dateFormat.format(value);

			/*
			DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
			DateTimeFormat dateFormat = new DateTimeFormat("yyyy-MM-dd HH:mm:ss", info) {};
			this.fstring2 = dateFormat.format(value);
			*/
		   
			String vstring2 = this.fstring2;
			String vdata = vstring2.substring(0, vstring2.indexOf(" ")); 
			String vhora = vstring2.substring(vstring2.indexOf(" ")+1);
			if (vhora=="00:00:00") {
				vdata = vdata + " 23:59:59";
				Date data = (Date) dateFormat.parse(vdata);
				this.setDate2(data);
			}
		}
		else {
			this.fstring2 = null;
		}
	}
	public Date getDate2() {
		return this.fdate2;
	}

	//**********************************
	
	public void setDateTime(Date value) {
		this.setDate(value);
	}
	public Date getDateTime() {
		return this.getDate();
	}
	
	public void setDateTime2(Date value) throws Exception {
		this.setDate2(value);
	}
	public Date getDateTime2() {
		return this.getDate2();
	}

	//*******************************
	
	public void setBoolean(Boolean value) {
		this.fboolean = value;
		this.fstring = String.valueOf(value);
		this.setIsDefined(true);
	}
	public Boolean getBoolean() {
		return this.fboolean;
	}
	
	public void setBoolean2(Boolean value) {
		this.setIsDefined(true);
		this.setIsRange(true);

		this.fboolean2 = value;
		this.fstring2 = String.valueOf(value);
	}
	public Boolean getBoolean2() {
		return this.fboolean2;
	}
	
	//*******************************

	public void setIsDefined(Boolean isDefined) {
		this.fisDefined = isDefined ;
	}
	public Boolean isDefined() {
		return this.fisDefined;
	}
	
	public void setIsRange(Boolean isRange) {
		this.fisRange = isRange;
	}
	public Boolean isRange() {
		return this.fisRange;
	}
	
	//*****************************
	
	public void setFieldName(String fieldName) {
		this.ffieldName = fieldName;
	}
	public String getFieldName() {
		return this.ffieldName;
	}
	//*************************************
	
	public Filter(String fieldName, String value) {
		this.setFieldName(fieldName);
		this.setString(value);
	}
	
	public Filter(String fieldName, String value, String value2) {
		this.setIsRange(true);
		this.setFieldName(fieldName);
		this.setString(value);
		this.setString2(value2);
	}

	// **************************
	public Filter(String fieldName, Integer value) {
		this.setFieldName(fieldName);
		this.setInteger(value);
	}
	
	public Filter(String fieldName, Integer value, Integer value2) {
		this.setIsRange(true);
		this.setFieldName(fieldName);
		this.setInteger(value);
		this.setInteger2(value2);
	}
	
	//**********************************

	public Filter(String fieldName, Double value) {
		this.setFieldName(fieldName);
		this.setDouble(value);
	}
	
	public Filter(String fieldName, Double value, Double value2) {
		this.setIsRange(true);
		this.setFieldName(fieldName);
		this.setDouble(value);
		this.setDouble2(value2);
	}
	//********************************
	
	public Filter(String fieldName, Float value) {
		this.setFieldName(fieldName);
		this.setFloat(value);
	}
	
	public Filter(String fieldName, Float value, Float value2) {
		this.setIsRange(true);
		this.setFieldName(fieldName);
		this.setFloat(value);
		this.setFloat2(value2);
	}
	//******************************
	public Filter(String fieldName, Date value) {
		this.setFieldName(fieldName);
		this.setDate(value);
	}
	
	public Filter(String fieldName, Date value, Date value2) throws Exception {
		this.setIsRange(true);
		this.setFieldName(fieldName);
		this.setDate(value);
		this.setDate2(value2);
	}
	//***************************************
	public Filter(String fieldName, Boolean value) {
		this.setFieldName(fieldName);
		this.setBoolean(value);
	}
	
	public Filter(String fieldName, Boolean value, Boolean value2) {
		this.setIsRange(true);
		this.setFieldName(fieldName);
		this.setBoolean(value);
		this.setBoolean2(value2);
	}
}
