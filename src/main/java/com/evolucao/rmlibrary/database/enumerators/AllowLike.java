package com.evolucao.rmlibrary.database.enumerators;

public enum AllowLike {
	UNDEFINED(0), BEGIN(1), END(2), BOTH(3), NONE(4); 
	private final int value; 
	AllowLike(int value) {
		this.value = value; 
	} 
	public int getValor() {
		return this.value; 
	} 
}
