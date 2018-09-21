package com.evolucao.rmlibrary.ui.model;

public enum OptionType {
	MAINOPTION(0), GROUPOPTION(1), NORMALOPTION(2), HIGHLIGHTEDOPTION(3), COMMANDOPTION(4); 
	private final int value;
	
	OptionType(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	}
}
