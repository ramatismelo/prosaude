/*
 * Uma tabela filha pode se relacionar com a tabela pai de duas formas
 * 1o. Indicando simplemente o masterFieldName que armazena o nome do campo na tabela filha que contem o uid do registro pai
 *     desta forma o relacionamento fica assim
 *     masterTable.uid -> tableChild.masterFieldName
 *     exemplo: 
 *     clientes.uid (tabela destinada a armazenas a lista de clientes)
 *     boletos.uidCliente (tabela com a relacao de boletos emitidos para o cliente, o campo uidCliente armazena o uid do cliente ao qual o boleto pertence)
 *      
 * 2o. Indicando masterIndexName - Nome do indice da tabela pai usada para relacionamento
 *     Relationalship - relacao de campos na tabela filho que correspondem aos campos do indice da tabela pai
 *     exemplo:
 *     consulta - codiasso_codiespe_datacons (codiasso, codiespe, datacons)   
 *     consultasDetalhes (codiasso, codiespe)
 *     
 *     No exemplo acima a tabela consulta possui um indice chamado codiasso_codiespe_datacons que esta sendo usado como masterIndexName
 *     A tabela consultasDetalhes se relaciona com usando os campos (codiasso, codiespe)
 *     
 * Obs.: Caso o usuario utilize masterFieldName não deve ser possivel usar masterIndexName e relationalship 
 */
package com.evolucao.rmlibrary.database;

public class TableChild {
	Table table = null;
	
	// Campo usado para relacionamento simples uid pai com uidMasterField
	// nome do campo na tabela child que contem o uid do registro pai
	String masterFieldName = null;
	
	// Usado para relacionamento atraves de um indice do pai com registros do child
	String masterIndexName = null;
	String relationalship = null;
	
	boolean autoInsert = true;

	/**
	 * Usado em relacionamento com o registro pai atravez de um indice presente no pai com registro do child
	 * @param masterIndexName
	 */
	public void setMasterIndexName(String masterIndexName) {
		this.masterIndexName = masterIndexName;
	}
	public String getMasterIndexName() {
		return this.masterIndexName;
	}
	
	/**
	 * Usaod para relacionar os campos do child que se relacional com os campos de um indice no registro pai
	 * @param relationalship
	 */
	public void setRelationalship(String relationalship) {
		this.relationalship = relationalship;
	}
	public String getRelationalship() {
		return this.relationalship;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	// Nome do campo no child que armazena o uid do registro pai
	/**
	 * Define o nome do campo na tabelaChild que contem o UID do registro pai, quando informado significa que o relacionamento sera feito da seguinte form:
	 * pai.uid->tableChild.masterFieldName (campo que deve conter o uid do registro pai)
	 * @param masterFieldName
	 */
	public void setMasterFieldName(String masterFieldName) {
		this.masterFieldName = masterFieldName;
	}
	/**
	 * Retorna o nome do campo no tableChild que contem o uid do registro pai do registro
	 * @return
	 */
	public String getMasterFieldName() {
		return this.masterFieldName;
	}
	
	public void setAutoInsert(boolean autoInsert) {
		this.autoInsert = autoInsert;
	}
	public boolean getAutoInsert() {
		return this.autoInsert;
	}
	
	public TableChild() {
		
	}

	/**
	 * Cria um child para ser incluido no tableMaster (pai)  
	 * @param table Nome da tabela child
	 * @param masterFieldName Nome do campo na tabela child que armazena o uid dos registros mestres no pai
	 * @param autoInsert Em um formulario ativo, inicia automaticamente a inclusao de registros no filho apos o post de uma inclusao no registro pai
	 */
	public TableChild(Table table, String masterFieldName, boolean autoInsert) {
		this.table = table;
		this.masterFieldName = masterFieldName;
		this.autoInsert = autoInsert;
	}
}
