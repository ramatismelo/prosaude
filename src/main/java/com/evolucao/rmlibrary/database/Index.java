package com.evolucao.rmlibrary.database;

public class Index {
	private String findexTitle = null;
	private String findexName = null;
	private String findexFields = null;
	private Boolean funique = false;
	private Boolean finternal = false;
	
	public void setIndexTitle(String indexTitle) {
		this.findexTitle = indexTitle;
	}
	public String getIndexTitle() {
		return this.findexTitle;
	}
	
	public void setIndexName(String indexName) {
		this.findexName = indexName;
	}
	public String getIndexName() {
		return  this.findexName;
	}
	
	public void setIndexFields(String indexFields) {
		this.findexFields = indexFields;
	}
	public String getIndexFields() {
		return this.findexFields;
	}
	
	public void setUnique(Boolean unique) {
		this.funique = unique;
	}
	public Boolean getUnique() {
		return this.funique;
	}
	
	public void setInternal(Boolean internal) {
		this.finternal = internal;
	}
	public Boolean getInternal() {
		return this.finternal;
	}
	
	
	public Index(String indexName, String indexFields) {
		this.setIndexName(indexName);
		this.setIndexFields(indexFields);
	}
	
	public Index(String indexTitle, String indexName, String indexFields) {
		this.setIndexTitle(indexTitle);
		this.setIndexName(indexName);
		this.setIndexFields(indexFields);
	}
	
	public Index(String indexName, String indexFields, Boolean unique) {
		this.setIndexName(indexName);
		this.setIndexFields(indexFields);
		this.setUnique(unique);
	}
	
	public Index(String indexTitle, String indexName, String indexFields, Boolean unique) {
		this.setIndexTitle(indexTitle);
		this.setIndexName(indexName);
		this.setIndexFields(indexFields);
		this.setUnique(unique);
	}
	
	public Index(String indexName, String indexFields, Boolean unique, Boolean internal) {
		this.setIndexName(indexName);
		this.setIndexFields(indexFields);
		this.setUnique(unique);
		this.setInternal(internal);
	}
	
	public Index(String indexTitle, String indexName, String indexFields, Boolean unique, Boolean internal) {
		this.setIndexTitle(indexTitle);
		this.setIndexName(indexName);
		this.setIndexFields(indexFields);
		this.setUnique(unique);
		this.setInternal(internal);
	}
}
