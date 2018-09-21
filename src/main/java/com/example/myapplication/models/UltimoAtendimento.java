package com.example.myapplication.models;

import java.sql.ResultSet;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.UI;

public class UltimoAtendimento {
	Integer codiasso = null;
	String paciente = null;
	Integer codiespe = null;
	Integer codimedi = null;
	Date datacons = null;
	Integer ulticonsdias = null;
	String tipoatend = null;
	Integer sequencia = null;
	
	public void setCodiEspe(Integer codiespe) {
		this.codiespe = codiespe;
	}
	public Integer getCodiEspe() {
		return this.codiespe;
	}
	
	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}
	public String getPaciente() {
		return this.paciente;
	}
	
	public void setCodiAsso(Integer codiasso) {
		this.codiasso = codiasso;
	}
	public Integer getCodiAsso() {
		return this.codiasso;
	}
	
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	public Integer getSequencia() {
		return this.sequencia;
	}
	
	public void setTipoAtend(String tipoatend) {
		this.tipoatend = tipoatend;
	}
	public String getTipoAtend() {
		return this.tipoatend;
	}
	
	public void setUltiConsDias(Integer ulticonsdias) {
		this.ulticonsdias = ulticonsdias;
	}
	public Integer getUltiConsDias() {
		return this.ulticonsdias;
	}
	
	public void setDataCons(Date datacons) {
		this.datacons = datacons;
	}
	public Date getDataCons() {
		return this.datacons;
	}
	
	public void setCodiMedi(Integer codimedi) {
		this.codimedi = codimedi;
	}
	public Integer getCodiMedi() {
		return this.codimedi;
	}
	
	public UltimoAtendimento() {
		
	}
	
	public void updateUltimoAtendimento(Date dataCompra, Integer codiasso, String paciente, Integer codiespe) {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		ui.getDatabase().openConnection();
		
		String comando ="select marccons.sequencia, marccons.datacons, marccons.tipoatend, marccons.codimedi, medicos.nome from marccons";
		comando += " left join medicos on (medicos.codimedi=marccons.codimedi)";
		comando += "where (codiasso=" + codiasso + ") and (paciente='" + paciente + "') and (codiespe="+codiespe+")";
		comando += "order by datacons desc, sequencia desc limit 1";
		
		try {
			ResultSet resultSet = ui.getDatabase().executeSelect(comando);
			if (resultSet.next()) {
				// Atualiza dados da consulta
				this.setSequencia(resultSet.getInt("sequencia"));
				this.setDataCons(resultSet.getDate("datacons"));
				int dias = (int) Utils.getDateDiff(resultSet.getDate("datacons"), dataCompra, TimeUnit.DAYS);
				this.setUltiConsDias(dias);
				this.setTipoAtend(resultSet.getString("tipoatend"));
				this.setCodiMedi(resultSet.getInt("codimedi"));
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ui.getDatabase().closeConnection();
		}
	}
	
	/*
	public void updateUltimoAtendimento(Integer codiasso, String paciente, String uidEspecialidade) {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		ui.getDatabase().openConnection();
		
		String comando ="select marccons.datacons, marccons.tipoatend, marccons.codimedi, medicos.nome from marccons";
		comando += " left join medicos on (medicos.codimedi=marccons.codimedi)";
		comando += "where (codiasso=" + codiasso + ") and (paciente='" + paciente + "') and (marccons.uidespecialidade='"+uidEspecialidade+"')";
		comando += "order by datacons desc, sequencia desc limit 1";
		
		try {
			ResultSet resultSet = ui.getDatabase().executeSelect(comando);
			if (resultSet.next()) {
				// Atualiza dados da consulta
				this.setDataCons(resultSet.getDate("datacons"));
				int dias = (int) Utils.getDateDiff(resultSet.getDate("datacons"), new Date(), TimeUnit.DAYS);
				this.setUltiConsDias(dias);
				this.setTipoAtend(resultSet.getString("tipoatend"));
				this.setCodiMedi(resultSet.getInt("codimedi"));
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ui.getDatabase().closeConnection();
		}
	}
	*/
}
