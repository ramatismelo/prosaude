package com.evolucao.rmlibrary.ui;

public enum LabelTitleType {
	h1(0), h2(1);
	
	private final int labelTitleType;
	
	LabelTitleType(int labelTitleType) {
		this.labelTitleType = labelTitleType;
	}

	public int getLabelTitleType() {
		return this.labelTitleType;
	}
}
