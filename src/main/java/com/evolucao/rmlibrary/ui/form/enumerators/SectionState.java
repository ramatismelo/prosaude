package com.evolucao.rmlibrary.ui.form.enumerators;

public enum SectionState {
	MINIMIZED(0), MAXIMIZED(1); 
	private final int value; 
	SectionState(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
