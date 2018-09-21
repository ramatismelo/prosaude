package com.evolucao.rmlibrary.database.enumerators;

public enum ReferentialIntegrityType {
	Child(1), foreingKey(2); 
	private final int value; 
	ReferentialIntegrityType(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
