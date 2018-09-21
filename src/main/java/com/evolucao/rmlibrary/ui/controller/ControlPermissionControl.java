package com.evolucao.rmlibrary.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.model.OptionModel;
import com.evolucao.rmlibrary.ui.model.OptionType;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class ControlPermissionControl extends ControlBase {
	List<OptionModel> optionModelList = new ArrayList<OptionModel>();

	public void setOptionModelList(List<OptionModel> optionModelList) {
		this.optionModelList = optionModelList;
	}
	public List<OptionModel> getOptionModelList() {
		return this.optionModelList;
	}
	
	@Override
	public CssLayout deploy() {
		ControlForm controlForm = (ControlForm) this.getForm();
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		CssLayout mainLayout = new CssLayout();
		mainLayout.addStyleName("flex-direction-column");
		
		CssLayout optionLayout = new CssLayout();
		mainLayout.addComponent(optionLayout);
		optionLayout.addStyleName("flex-direction-row");
		{
			CssLayout cellLayout = new CssLayout();
			cellLayout.addStyleName("cell-white");
			cellLayout.addStyleName("flex-grow-1");
			optionLayout.addComponent(cellLayout);
			{
				Label lblTextOption = new Label("OPÇÃO");
				cellLayout.addComponent(lblTextOption);
			}
			
			cellLayout = new CssLayout();
			cellLayout.setWidth("120px");
			cellLayout.addStyleName("cell-white");
			optionLayout.addComponent(cellLayout);
			{
				Label lblTextOption = new Label("ACESSO");
				cellLayout.addComponent(lblTextOption);
			}
			
			cellLayout = new CssLayout();
			cellLayout.addStyleName("cell-white");
			cellLayout.setWidth("120px");
			optionLayout.addComponent(cellLayout);
			{
				Label lblTextOption = new Label("ADIÇÃO");
				cellLayout.addComponent(lblTextOption);
			}
			
			cellLayout = new CssLayout();
			cellLayout.addStyleName("cell-white");
			cellLayout.setWidth("120px");
			optionLayout.addComponent(cellLayout);
			{
				Label lblTextOption = new Label("ALTERAÇÃO");
				cellLayout.addComponent(lblTextOption);
			}
			
			cellLayout = new CssLayout();
			cellLayout.addStyleName("cell-white");
			cellLayout.setWidth("120px");
			optionLayout.addComponent(cellLayout);
			{
				Label lblTextOption = new Label("EXCLUSÃO");
				cellLayout.addComponent(lblTextOption);
			}
		}
		
		Table tblSysPermissions = ui.database.loadTableByName("syspermissions");
		
		// Pega a relacao de opcoes do projeto e incluio o optionModelList
		Table tblSysOptions = ui.database.loadTableByName("sysoptions");
		tblSysOptions.select("*");
		tblSysOptions.setFilter("projectname", ui.getProjectName());
		tblSysOptions.loadData();
		while (!tblSysOptions.eof()) {
			optionLayout = new CssLayout();
			optionLayout.addStyleName("flex-direction-row");
			mainLayout.addComponent(optionLayout);
			{
				OptionModel optionModel = new OptionModel();
				optionModel.setCodigoOption(tblSysOptions.getString("codiopti"));
				optionModel.setText(tblSysOptions.getString("textoption"));
				optionModel.setOptionType(OptionType.values()[Integer.valueOf(tblSysOptions.getString("optiontype"))]);
				optionModel.setAccess(false);
				optionModel.setInsert(false);
				optionModel.setUpdate(false);
				optionModel.setDelete(false);

				tblSysPermissions.select("*");
				tblSysPermissions.setFilter("projectname", ui.getProjectName());
				tblSysPermissions.setFilter("uidgroup", controlForm.getTable().getString("uid"));
				tblSysPermissions.setFilter("codiopti", tblSysOptions.getString("codiopti"));
				tblSysPermissions.loadData();
				if (!tblSysPermissions.eof()) {
					if (tblSysPermissions.getString("optaccess").equalsIgnoreCase("S")) {
						optionModel.setAccess(true);
					}
					
					if (tblSysPermissions.getString("optinsert").equalsIgnoreCase("S")) {
						optionModel.setInsert(true);
					}
					
					if (tblSysPermissions.getString("optupdate").equalsIgnoreCase("S")) {
						optionModel.setUpdate(true);
					}
					
					if (tblSysPermissions.getString("optdelete").equalsIgnoreCase("S")) {
						optionModel.setDelete(true);
					}
				}
				
				this.getOptionModelList().add(optionModel);

				CssLayout cellLayout = new CssLayout();
				cellLayout.addStyleName("cell-white");
				cellLayout.addStyleName("flex-grow-1");
				optionLayout.addComponent(cellLayout);
				{
					if (Integer.valueOf(tblSysOptions.getString("grau"))>=2) {
						CssLayout cellLevel1 = new CssLayout();
						cellLevel1.setWidth("50px");
						cellLayout.addComponent(cellLevel1);
					}
					
					if (Integer.valueOf(tblSysOptions.getString("grau"))>=3) {
						CssLayout cellLevel1 = new CssLayout();
						cellLevel1.setWidth("50px");
						cellLayout.addComponent(cellLevel1);
					}
					
					Label lblTexto = new Label(tblSysOptions.getString("textoption"));
					cellLayout.addComponent(lblTexto);
				}
				
				cellLayout = new CssLayout();
				cellLayout.addStyleName("cell-white");
				cellLayout.addStyleName("cell-check");
				optionLayout.addComponent(cellLayout);
				cellLayout.setWidth("120px");
				{
					CheckBox chkAccess = new CheckBox();
					chkAccess.setData(optionModel);
					chkAccess.setValue(optionModel.getAccess());
					cellLayout.addComponent(chkAccess);
					chkAccess.addValueChangeListener(new ValueChangeListener<Boolean>() {
						@Override
						public void valueChange(ValueChangeEvent<Boolean> event) {
							CheckBox chkAccess = (CheckBox) event.getComponent();
							OptionModel optionModel = (OptionModel) chkAccess.getData();
							optionModel.setAccess(event.getValue());
						}
					});
					
					optionModel.setChkAccess(chkAccess);
				}
				
				cellLayout = new CssLayout();
				cellLayout.addStyleName("cell-white");
				cellLayout.addStyleName("cell-check");
				optionLayout.addComponent(cellLayout);
				cellLayout.setWidth("120px");
				{
					if (optionModel.getOptionType()==OptionType.NORMALOPTION) {
						CheckBox chkInsert = new CheckBox();
						chkInsert.setData(optionModel);
						chkInsert.setValue(optionModel.getInsert());
						cellLayout.addComponent(chkInsert);
						chkInsert.addValueChangeListener(new ValueChangeListener<Boolean>() {
							@Override
							public void valueChange(ValueChangeEvent<Boolean> event) {
								CheckBox chkInsert = (CheckBox) event.getComponent();
								OptionModel optionModel = (OptionModel) chkInsert.getData();
								optionModel.setInsert(event.getValue());
							}
						});
						
						optionModel.setChkInsert(chkInsert);
					}
				}
				
				cellLayout = new CssLayout();
				cellLayout.addStyleName("cell-white");
				cellLayout.addStyleName("cell-check");
				optionLayout.addComponent(cellLayout);
				cellLayout.setWidth("120px");
				{
					if (optionModel.getOptionType()==OptionType.NORMALOPTION) {
						CheckBox chkUpdate = new CheckBox();
						chkUpdate.setData(optionModel);
						chkUpdate.setValue(optionModel.getUpdate());
						cellLayout.addComponent(chkUpdate);
						chkUpdate.addValueChangeListener(new ValueChangeListener<Boolean>() {
							@Override
							public void valueChange(ValueChangeEvent<Boolean> event) {
								CheckBox chkUpdate = (CheckBox) event.getComponent();
								OptionModel optionModel = (OptionModel) chkUpdate.getData();
								optionModel.setUpdate(event.getValue());
							}
						});
						
						optionModel.setChkUpdate(chkUpdate);
					}
				}
				
				cellLayout = new CssLayout();
				cellLayout.addStyleName("cell-white");
				cellLayout.addStyleName("cell-check");
				optionLayout.addComponent(cellLayout);
				cellLayout.setWidth("120px");
				{
					if (optionModel.getOptionType()==OptionType.NORMALOPTION) {
						CheckBox chkDelete = new CheckBox();
						chkDelete.setData(optionModel);
						chkDelete.setValue(optionModel.getDelete());
						cellLayout.addComponent(chkDelete);
						chkDelete.addValueChangeListener(new ValueChangeListener<Boolean>() {
							@Override
							public void valueChange(ValueChangeEvent<Boolean> event) {
								CheckBox chkDelete = (CheckBox) event.getComponent();
								OptionModel optionModel = (OptionModel) chkDelete.getData();
								optionModel.setDelete(event.getValue());
							}
						});
						
						optionModel.setChckDelete(chkDelete);
					}
				}
			}
			
			tblSysOptions.next();
		}
		
		// Configura as permissoes de acordo com o que estiver no bando de dados
		// Inclui um layout que corresponde a cada opcao configurando os checkbox para alterar o conteudo dos metodos
		// em cada checkbox deve existir um ponteiro para sua respectiva entrada no optionModelList e cada checkbox
		// deve alterar a respectiva opcao do OptionModel
		
		return mainLayout;
	}
	
	public void savePerssion() {
		ControlForm controlForm = (ControlForm) this.getForm();
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		
		// Limpa as opcoes que ja estiverem salvas
		try {
			String comando = "delete from syspermissions where (syspermissions.projectname='" + controlForm.getTable().getString("projectname") + "') and (syspermissions.uidgroup='" + controlForm.getTable().getString("uid") + "')";
			ui.database.openConnection();
			ui.database.executeCommand(comando);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ui.database.closeConnection();
		}
		
		Table tblSysPermissions = ui.database.loadTableByName("sysPermissions");
		for (OptionModel optionModel: this.getOptionModelList()) {
			tblSysPermissions.select("*");
			tblSysPermissions.setFilter("projectname", ui.getProjectName());
			tblSysPermissions.setFilter("uidgroup", controlForm.getTable().getString("uid"));
			tblSysPermissions.setFilter("codiopti", optionModel.getCodigoOption());
			tblSysPermissions.loadData();
			if (tblSysPermissions.eof()) {
				tblSysPermissions.insert();
				tblSysPermissions.setValue("projectname", controlForm.getTable().getString("projectname"));
				tblSysPermissions.setValue("uidgroup", controlForm.getTable().getString("uid"));
				tblSysPermissions.setValue("codiopti", optionModel.getCodigoOption());
				
				if (optionModel.getAccess()) {
					tblSysPermissions.setValue("optaccess", "S");
				}
				else {
					tblSysPermissions.setValue("optaccess", "N");
				}
				
				if (optionModel.getInsert()) {
					tblSysPermissions.setValue("optinsert", "S");
				}
				else {
					tblSysPermissions.setValue("optinsert", "N");
				}
				
				if (optionModel.getUpdate()) {
					tblSysPermissions.setValue("optupdate", "S");
				}
				else {
					tblSysPermissions.setValue("optupdate", "N");
				}

				if (optionModel.getDelete()) {
					tblSysPermissions.setValue("optdelete", "S");
				}
				else {
					tblSysPermissions.setValue("optdelete", "N");
				}
				
				tblSysPermissions.execute();
			}
			else {
				tblSysPermissions.update();
				tblSysPermissions.setFilter("projectname", controlForm.getTable().getString("projectname"));
				tblSysPermissions.setFilter("uidgroup", controlForm.getTable().getString("uid"));
				tblSysPermissions.setFilter("codiopti", optionModel.getCodigoOption());
				
				if (optionModel.getAccess()) {
					tblSysPermissions.setValue("optaccess", "S");
				}
				else {
					tblSysPermissions.setValue("optaccess", "N");
				}
				
				if (optionModel.getInsert()) {
					tblSysPermissions.setValue("optinsert", "S");
				}
				else {
					tblSysPermissions.setValue("optinsert", "N");
				}
				
				if (optionModel.getUpdate()) {
					tblSysPermissions.setValue("optupdate", "S");
				}
				else {
					tblSysPermissions.setValue("optupdate", "N");
				}

				if (optionModel.getDelete()) {
					tblSysPermissions.setValue("optdelete", "S");
				}
				else {
					tblSysPermissions.setValue("optdelete", "N");
				}
				
				tblSysPermissions.execute();
			}
		}
	}
}
