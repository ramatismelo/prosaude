package com.example.myapplication.view;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.ui.events.ControlGridAfterUpdateRecordsEvent;
import com.evolucao.rmlibrary.ui.events.ControlGridAfterUpdateRecordsEvent.ControlGridAfterUpdateRecordsEventListener;
import com.evolucao.rmlibrary.ui.events.ControlGridSelectionEvent;
import com.evolucao.rmlibrary.ui.events.ControlGridSelectionEvent.ControlGridSelectionEventListener;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class AgendaView extends CssLayout {
	SimpleRecord simpleRecord = new SimpleRecord();
	RmGrid rmGridAgendaDias = null;
	Date limiteRetorno = null;
	
	public void setLimiteRetorno(Date limiteRetorno) {
		this.limiteRetorno = limiteRetorno;
	}
	public Date getLimiteRetorno() {
		return this.limiteRetorno;
	}
			
	public void setSimpleRecord(SimpleRecord simpleRecord) {
		this.simpleRecord = simpleRecord;
	}
	public SimpleRecord getSimpleRecord() {
		return this.simpleRecord;
	}
	
	public AgendaView(Integer codigoEspecialidade, Integer codigoMedico, String tipoAtendimento, Date limiteRetorno) {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		if (tipoAtendimento.equalsIgnoreCase("C") || (tipoAtendimento.equalsIgnoreCase("E"))) {
			rmGridAgendaDias = ui.database.loadRmGridByName("agendaDiasConsulta");
		}
		else {
			rmGridAgendaDias = ui.database.loadRmGridByName("agendaDiasRetorno");
		}

		this.getSimpleRecord().setInteger("codiespe", codigoEspecialidade);
		this.getSimpleRecord().setInteger("codimedi", codigoMedico);
		this.getSimpleRecord().setString("tipoatend", tipoAtendimento);
		
		this.setLimiteRetorno(limiteRetorno);

		addStyleName("flex-direction-column");
		setHeight("100%");
		
		CssLayout layoutOverlayContainer = new CssLayout();
		addComponent(layoutOverlayContainer);
		layoutOverlayContainer.addStyleName("flex-direction-row");
		layoutOverlayContainer.addStyleName("overlay-container");
		layoutOverlayContainer.setHeight("100%");
		
		CssLayout layoutMedicos = new CssLayout();
		layoutMedicos.setWidth("30%");
		layoutMedicos.setHeight("100%");
		layoutOverlayContainer.addComponent(layoutMedicos);
		{
			RmGrid rmGrid = ui.database.loadRmGridByName("agendaMedicos");
			rmGrid.getTable().setWhere("(espemedi.codiespe=" + this.getSimpleRecord().getInteger("codiespe") + ") and (medicos.situacao='A') and (not (espemedi.uid is null))");
			rmGrid.addControlGridSelectionEventListener(new ControlGridSelectionEventListener() {
				@Override
				public void onSelection(ControlGridSelectionEvent event) {
					setSimpleRecord(new SimpleRecord());
					getSimpleRecord().setInteger("codimedi", event.getRecordSelected().getInteger("codimedi"));
					
					rmGridAgendaDias.getTable().setFilter("codimedi", event.getRecordSelected().getInteger("codimedi"));
					rmGridAgendaDias.getTable().setFilter("tipoatend", tipoAtendimento);
					rmGridAgendaDias.updateRecords();
				}
			});

			// Caso exista um médico indicado para ser consultado, seleciona o medico, caso nao exista, seleciona o primeiro registro do grid
			rmGrid.addControlGridAfterUpdateRecordsEventListener(new ControlGridAfterUpdateRecordsEventListener() {
				@Override
				public void onAfterUpdateRecords(ControlGridAfterUpdateRecordsEvent event) {
					if ((codigoMedico==null) || (codigoMedico==0)) {
						event.getRmGrid().selectFirstRow();
					}
					else {
						SimpleRecord contentSelect = new SimpleRecord();
						contentSelect.setInteger("codimedi", codigoMedico);
						event.getRmGrid().selectRow(contentSelect);
					}
				}
			});
			
			rmGrid.setShowButtonSet(false);
			rmGrid.setShowButtonSetBottom(false);
			rmGrid.updateContent();
			layoutMedicos.addComponent(rmGrid);
		}
		
		CssLayout layoutDiasSemana = new CssLayout();
		layoutDiasSemana.addStyleName("overlay-div");
		layoutDiasSemana.addStyleName("flex-grow-1");
		layoutDiasSemana.addStyleName("agenda-dias");
		layoutDiasSemana.setHeight("100%");
		layoutOverlayContainer.addComponent(layoutDiasSemana);
		{
			rmGridAgendaDias.setShowButtonSet(false);
			rmGridAgendaDias.setShowButtonSetBottom(false);
			rmGridAgendaDias.updateContent();
			layoutDiasSemana.addComponent(rmGridAgendaDias);
			rmGridAgendaDias.addControlGridSelectionEventListener(new ControlGridSelectionEventListener() {
				@Override
				public void onSelection(ControlGridSelectionEvent event) {
					getSimpleRecord().setDate("data", event.getRecordSelected().getDate("data"));
					getSimpleRecord().setString("horario", event.getRecordSelected().getString("horario"));
					getSimpleRecord().setString("diasemana", event.getRecordSelected().getString("diasemana"));
					getSimpleRecord().setInteger("vagacons", event.getRecordSelected().getInteger("vagacons"));
					getSimpleRecord().setInteger("vagareto", event.getRecordSelected().getInteger("vagareto"));
				}
			});
		}
		
		if (this.getLimiteRetorno()!=null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Label lblAviso = new Label("Data limite para marcação do retorno: " + dateFormat.format(this.getLimiteRetorno()));
			addComponent(lblAviso);
		}
	}
	
}
