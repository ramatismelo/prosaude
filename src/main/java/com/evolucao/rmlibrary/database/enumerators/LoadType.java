package com.evolucao.rmlibrary.database.enumerators;

public enum LoadType {
	RECORDDATA(1), SIMPLERECORD(2), GENERICDATA(3); 
	private final int value;
	
	LoadType(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
