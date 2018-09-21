package com.evolucao.rmlibrary.database;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent;
import com.evolucao.rmlibrary.database.events.AfterValidateOnEditEvent.AfterValidateOnEditEventListener;
import com.evolucao.rmlibrary.database.events.ComboBoxItemCaptionGeneratorEvent;
import com.evolucao.rmlibrary.database.events.ComboBoxItemCaptionGeneratorEvent.ComboBoxItemCaptionGeneratorEventListener;
import com.evolucao.rmlibrary.database.events.ComboBoxValueChangeEvent;
import com.evolucao.rmlibrary.database.events.ComboBoxValueChangeEvent.ComboBoxValueChangeEventListener;

//public class ComboBox implements HasComboBoxSelectHandlers {
public class ComboBox {
	String targetTableName = null;
	String targetDisplayFieldName = null;
	String tagFieldName = null;
	String resultFieldName = null;
	String sourceDisplayFieldName = null;
	Boolean hasSelectHandler = false;
	String where = null;
	int minimunSizeToStartSearch = 0;
	boolean itemCaptionGeneratorAdded = false;
	String additionFieldsToLoad = null;

	/**
	 * Caso existam campos adicionais que serao utilizados na geracao do itemCaption, a lista dos campos
	 * adicionais que devem ser carregados da tabela deve ser informada. 
	 * @param additionalFieldsToLoad
	 */
	public void setAdditionalFieldsToLoad(String additionalFieldsToLoad) {
		this.additionFieldsToLoad = additionalFieldsToLoad;
	}
	public String getAdditionalFieldsToLoad() {
		return this.additionFieldsToLoad;
	}
	
	protected EventListenerList listenerList = new EventListenerList();
	
	public void setMinimunSizeToStartSearch(int minimunSizeToStartSearch) {
		this.minimunSizeToStartSearch = minimunSizeToStartSearch;
	}
	public int getMinimunSizeToStartSearch() {
		return this.minimunSizeToStartSearch;
	}

	public void setItemCaptionGeneratorAdded(boolean itemCaptionGeneratorAdded) {
		this.itemCaptionGeneratorAdded = itemCaptionGeneratorAdded;
	}
	public boolean getItemCaptionGeneratorAdded() {
		return this.itemCaptionGeneratorAdded;
	}
	
	/**
	 * Usado para incluir uma clausula where para filtragem dos items do combobox que vai usar alem do que foi digitado nele
	 * o conteudo dessa where
	 * @param where
	 */
	public void setWhere(String where) {
		this.where = where;
	}
	public String getWhere() {
		return this.where;
	}
	
	/**
	 * Nome do campo da tabela de origem da pesquisa do comboBox onde sera apresentado o displayFieldName do target do comboBox
	 * @param sourceDisplayFieldName
	 */
	public void setSourceDisplayFieldName(String sourceDisplayFieldName) {
		this.sourceDisplayFieldName = sourceDisplayFieldName;
	}
	public String getSourceDisplayFieldName() {
		return this.sourceDisplayFieldName;
	}
	
	public void setTagFieldName(String tagFieldName) {
		this.tagFieldName = tagFieldName;
	}
	public String getTagFieldName() {
		return this.tagFieldName;
	}
	
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	public String getTargetTableName() {
		return this.targetTableName;
	}
	
	public void setTargetDisplayFieldName(String targetDisplayFieldName) {
		this.targetDisplayFieldName = targetDisplayFieldName;
	}
	public String getTargetDisplayFieldName() {
		return this.targetDisplayFieldName;
	}
	
	public void setResultFieldName(String resultFieldName) {
		this.resultFieldName = resultFieldName;
	}
	public String getResultFieldName() {
		return this.resultFieldName;
	}
	
	public Boolean hasSelectHandler() {
		return this.hasSelectHandler;
	}
	
	public ComboBox() {
		
	}
	
	public ComboBox(String targetTableName, String targetDisplayFieldName, String tagFieldName, String resultFieldName) {
		this.targetTableName = targetTableName;
		this.targetDisplayFieldName = targetDisplayFieldName;
		this.tagFieldName = tagFieldName;
		this.resultFieldName = resultFieldName;
	}
	
	/**/
	public void addComboBoxValueChangeEventListener(ComboBoxValueChangeEventListener listener) {
		listenerList.add(ComboBoxValueChangeEventListener.class, listener);
	}

	public void removeComboBoxValueChangeEventListener(ComboBoxValueChangeEventListener listener) {
		listenerList.remove(ComboBoxValueChangeEventListener.class, listener);
	}

	public void fireComboBoxValueChangeEvent(ComboBoxValueChangeEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ComboBoxValueChangeEventListener.class) {
				((ComboBoxValueChangeEventListener) listeners[i + 1]).onComboBoxValueChange(event);
			}
		}
	}
	
	/**/
	public void addComboBoxItemCaptionGeneratorEventListener(ComboBoxItemCaptionGeneratorEventListener listener) {
		listenerList.add(ComboBoxItemCaptionGeneratorEventListener.class, listener);
		this.setItemCaptionGeneratorAdded(true);
	}

	public void removeComboBoxItemCaptionGeneratorEventListener(ComboBoxItemCaptionGeneratorEventListener listener) {
		listenerList.remove(ComboBoxItemCaptionGeneratorEventListener.class, listener);
		this.setItemCaptionGeneratorAdded(false);
	}

	public void fireComboBoxItemCaptionGeneratorEvent(ComboBoxItemCaptionGeneratorEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ComboBoxItemCaptionGeneratorEventListener.class) {
				((ComboBoxItemCaptionGeneratorEventListener) listeners[i + 1]).onComboBoxItemCaptionGenerator(event);
			}
		}
	}
}
