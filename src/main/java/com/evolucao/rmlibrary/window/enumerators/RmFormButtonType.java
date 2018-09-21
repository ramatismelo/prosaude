package com.evolucao.rmlibrary.window.enumerators;

public enum RmFormButtonType {
	UNDEFINED(0), SAVE(1), CANCEL(2), CONFIRM(3), MESSAGEOK(4); 
	private final int value;
	
	RmFormButtonType(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
