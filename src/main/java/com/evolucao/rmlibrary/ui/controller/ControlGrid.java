/**
 * ControlGrid é sempre utilizado para colocar uma grade de registros filhos dentro de um formulario pai
 */
package com.evolucao.rmlibrary.ui.controller;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class ControlGrid extends ControlBase {
	String rmGridName = null;
	RmGrid rmGrid = null;
	
	public void setRmGrid(RmGrid rmGrid) {
		this.rmGrid = rmGrid;
	}
	public RmGrid getRmGrid() {
		return this.rmGrid;
	}
	
	public void setRmGridName(String rmGridName) {
		this.rmGridName = rmGridName; 
	}
	public String getRmGridName() {
		return this.rmGridName;
	}

	public ControlGrid() {
		
	}
	
	public ControlGrid(String rmGridName) {
		this.rmGridName = rmGridName;
	}
	
	@Override
	public CssLayout deploy() {
		CssLayout retorno = new CssLayout();
		retorno.setWidth("100%");
		retorno.setHeight("100%");
		{
			ControlForm form = (ControlForm) this.getForm();
			
			if ((form!=null) && (form.getTable()!=null)) {
				ApplicationUI ui = (ApplicationUI) UI.getCurrent();

				// Carrega o grid para pegar o nome da tabela
				RmGrid rmGrid = ui.getDatabase().loadRmGridByName(this.getRmGridName());
				String tableName = rmGrid.getTable().getTableName();
				
				Table tblTable = form.getTable();
				//Table tblGrid = tblTable.getTableChildren(this.getRmGridName());
				Table tblGrid = tblTable.getTableChildren(tableName);
				
				// Reseta a posicao da paginacao para a primeira posicao
				tblGrid.setOffSet(0);
				
				// Limpa os dados dos campos e das filtragens
				tblGrid.CleanValues();
				
				rmGrid = ui.getDatabase().loadRmGridByName(this.getRmGridName(), tblGrid);
				rmGrid.updateContent();
				
				// Guarda ponteiro para o rmGrid para uso posterior
				this.setRmGrid(rmGrid);
				
				retorno.addComponent(rmGrid);
			}
		}
		
		return retorno;
	}
}
