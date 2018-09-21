package com.evolucao.rmlibrary.database.enumerators;

public enum WarningMessageType {
	SUCCESS(0), ERROR(1);
	private final int value; 
	WarningMessageType(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	}	
}
