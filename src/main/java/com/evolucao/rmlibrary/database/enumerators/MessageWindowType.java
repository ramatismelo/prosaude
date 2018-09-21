package com.evolucao.rmlibrary.database.enumerators;

public enum MessageWindowType {
	NONE(0), ERROR(1), INFO(2), QUESTION(3), WARNING(4); 
	private final int value;
	
	MessageWindowType(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
