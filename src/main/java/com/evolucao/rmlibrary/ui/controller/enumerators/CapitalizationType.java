package com.evolucao.rmlibrary.ui.controller.enumerators;

public enum CapitalizationType {
	NONE(0), CAPITALIZE(1), UPPERCASE(2), LOWERCASE(3); 
	private final int value; 
	CapitalizationType(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
