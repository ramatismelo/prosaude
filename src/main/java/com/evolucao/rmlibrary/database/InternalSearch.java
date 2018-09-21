package com.evolucao.rmlibrary.database;

import java.util.ArrayList;
import java.util.List;

public class InternalSearch {
	List<KeyValue> items = new ArrayList<KeyValue>();
	String selectedItemKey = null;
	boolean nullSelectionAllowed = false;
	
	public void setSelectedItem(String Key) {
		this.selectedItemKey = Key;
	}
	public String getSelectedItem() {
		return this.selectedItemKey;
	}
	
	public String getSelectedKey() {
		return this.selectedItemKey;
	}
	
	public String getSelectedValue() {
		String retorno = null;
		for (KeyValue item: this.getItems()) {
			if (item.getKey().equalsIgnoreCase(this.getSelectedItem())) {
				retorno = item.getValue();
				break;
			}
		}
		
		return retorno;
	}
	
	public String getValueByKey(String key) {
		String retorno = null;
		for (KeyValue item: this.getItems()) {
			if (item.getKey().equalsIgnoreCase(key)) {
				retorno = item.getValue();
				break;
			}
		}
		
		return retorno;
	}
	
	public void setItems(List<KeyValue> items) {
		this.items = items;
	}
	public List<KeyValue> getItems() {
		return this.items;
	}
	
	public InternalSearch() {
		
	}
	
	public void addItem(String key, String value) {
		KeyValue item = new KeyValue(key, value);
		this.getItems().add(item);
	}
	
	public void fillEstados() {
		this.getItems().clear();
		
		this.addItem("AC", "Acre");
		this.addItem("AL", "Alagoas");
		this.addItem("AP", "Amapá");
		this.addItem("AM", "Amazonas");
		this.addItem("BA", "Bahia");
		this.addItem("CE", "Ceará");
		this.addItem("DF", "Distrito Federal");
		this.addItem("ES", "Espírito Santo");
		this.addItem("GO", "Goías");
		this.addItem("MA", "Maranhão");
		this.addItem("MS", "Mato Grosso do Sul");
		this.addItem("MT", "Mato Grosso");
		this.addItem("MG", "Minas Gerais");
		this.addItem("PR", "Paraná");
		this.addItem("PB", "Paraíba");
		this.addItem("PA", "Pará");
		this.addItem("PE", "Pernambuco");
		this.addItem("PI", "Piauí");
		this.addItem("RN", "Rio Grande do Norte");
		this.addItem("RS", "Rio Grande do Sul");
		this.addItem("RJ", "Rio de Janeiro");
		this.addItem("RO", "Rondônia");
		this.addItem("RR", "Roraima");
		this.addItem("SC", "Santa Catarina");
		this.addItem("SE", "Sergipe");
		this.addItem("SP", "São Paulo");
		this.addItem("TO", "Tocantins");
	}
}
