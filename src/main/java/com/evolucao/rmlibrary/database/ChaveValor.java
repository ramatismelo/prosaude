package com.evolucao.rmlibrary.database;

import java.io.Serializable;

public class ChaveValor implements Serializable {
	private static final long serialVersionUID = 1L;

	private String uid;
	private String descricao;
	
	public ChaveValor() {
		
	}
	
	public ChaveValor(String uid, String descricao) {
		this.uid = uid;
		this.descricao = descricao;
	}

	public void setUid(String uid) {
    	this.uid = uid;
    }
    public String getUid() {
    	return this.uid;
    }
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
