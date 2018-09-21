package com.evolucao.rmlibrary.database;

import com.evolucao.rmlibrary.database.enumerators.OnDelete;
import com.evolucao.rmlibrary.database.enumerators.ReferentialIntegrityType;

public class ReferentialIntegrity {
	private String tableName = null;
	private String foreingKey = null;
	private OnDelete onDelete = OnDelete.NoAction;
	private ReferentialIntegrityType referentialIntegrityType = ReferentialIntegrityType.foreingKey;
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName() {
		return this.tableName;
	}
	
	public void setForeingKey(String foreingKey) {
		this.foreingKey = foreingKey;
	}
	public String getForeingKey() {
		return this.foreingKey;
	}
	
	/**
	 * Aqui é definido o que se deve fazer com os registros presentes nas outras 
	 * tabelas que se relacionam com essa tabela caso um registro seja excluido
	 * NoAction - é usado para indicar que o registro nao pode ser excluido caso haja 
	 * registros em outra tabela que se relacionam com ele, isso geralmente é usado
	 * em tabelas de relacionamento.
	 * Cascade - é usado para indicar que os registros da tabela que esta se relacionando
	 *           devem ser excluidos antes do registro da tabela atual ser excluido.
	 * @param onDelete
	 */
	public void setOnDelete(OnDelete onDelete) {
		this.onDelete = onDelete;
	}
	public OnDelete getOnDelete() {
		return this.onDelete;
	}
	
	public void setReferentialIntegrityType(ReferentialIntegrityType referentialIntegrityType) {
		this.referentialIntegrityType = referentialIntegrityType;
	}
	public ReferentialIntegrityType getReferentialIntegrityType() {
		return this.referentialIntegrityType;
	}
	
	public ReferentialIntegrity() {
		
	}
	
	public ReferentialIntegrity(String tableName, String foreingKey, ReferentialIntegrityType referentialIntegrityType, OnDelete onDelete) {
		this.tableName = tableName;
		this.foreingKey = foreingKey;
		this.onDelete = onDelete;
		this.referentialIntegrityType = referentialIntegrityType;
	}
}
