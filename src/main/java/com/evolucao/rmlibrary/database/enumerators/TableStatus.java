package com.evolucao.rmlibrary.database.enumerators;

public enum TableStatus {
	NONE(0), BROWSE(1), INSERT(2), UPDATE(3), DELETE(4), FILTER(5); 
	private final int value; 
	TableStatus(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}