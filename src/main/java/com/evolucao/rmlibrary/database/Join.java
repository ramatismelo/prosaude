package com.evolucao.rmlibrary.database;

public class Join {
	private String targetTableName = null;
	private String foreignKey = null;
	private String relationship = null;
	private String alias = null;
	private Table sourceTable = null;
	
	public Join() {
		
	}
	
	public void setSourceTable(Table sourceTable) {
		this.sourceTable = sourceTable;
	}
	public Table getSourceTable() {
		return this.sourceTable;
	}
	
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	public String getTargetTableName() {
		return this.targetTableName;
	}
	
	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
	public String getForeignKey() {
		return this.foreignKey;
	}
	
	public void setRelationship(String relationship) {
		this.relationship = relationship; 
	}
	public String getRelationship() {
		return this.relationship;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAlias() {
		return this.alias;
	}

	public Join(String targetTableName, String foreignKey, String relationship) {
		this.targetTableName = targetTableName;
		this.foreignKey = foreignKey;
		this.relationship = relationship;
		this.alias = targetTableName;
	}
	
	public Join(String targetTableName, String foreignKey, String relationship, String alias) {
		this.targetTableName = targetTableName;
		this.foreignKey = foreignKey;
		this.relationship = relationship;
		this.alias = alias;
	}
}
