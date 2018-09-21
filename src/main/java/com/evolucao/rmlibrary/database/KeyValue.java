package com.evolucao.rmlibrary.database;

public class KeyValue {
	private String key;
	private String value;
	
	public KeyValue() {
		
	}
	
	public KeyValue(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public void setKey(String key) {
    	this.key = key;
    }
    public String getKey() {
    	return this.key;
    }
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
