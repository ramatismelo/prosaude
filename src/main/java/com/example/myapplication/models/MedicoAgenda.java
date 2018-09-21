package com.example.myapplication.models;

public class MedicoAgenda {
	Integer codimedi = null;
	String uid = null;
	String nome = null;
	
	public void setCodiMedi(Integer codimedi) {
		this.codimedi = codimedi;
	}
	public Integer getCodiMedi() {
		return this.codimedi;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUid() {
		return this.uid;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return this.nome;
	}
	
	public MedicoAgenda() {
		
	}
}
