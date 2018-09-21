package com.evolucao.rmlibrary.database.enumerators;

public enum OnDelete {
	NoAction(1), Cascade(2); 
	private final int value; 
	OnDelete(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
