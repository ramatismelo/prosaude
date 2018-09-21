package com.example.myapplication.reports;

import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;

public class NewDoubleType extends BigDecimalType {
	public NewDoubleType() {
		super();
	}
	
    @Override
    public String getPattern() {
         return "#,###.00";  
    }
}