package com.evolucao.rmlibrary.ui.controller.enumerators;

public enum ButtonType {
	NORMALBUTTON(0), BLANKBUTTON(1), LINKBUTTON(2); 
	private final int value; 
	ButtonType(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
