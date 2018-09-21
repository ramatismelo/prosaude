package com.evolucao.rmlibrary.database.enumerators;

public enum FieldType {
	VARCHAR(1), TEXT(2), INTEGER(3), DOUBLE(4), FLOAT(5), DATE(6), DATETIME(7), BOOLEAN(8), BIT(9); 
	private final int value;
	
	FieldType(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
