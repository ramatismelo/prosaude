package com.evolucao.rmlibrary.ui.production;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.ui.form.enumerators.SectionState;

public class RmSectionContainer extends CssLayout {
	String name = null;
	String title = null;
	Boolean allowMinimize = true;
	Boolean allowMaximaze = true;
	SectionState sectionState = SectionState.MAXIMIZED;
	CssLayout header = new CssLayout();
	CssLayout body = new CssLayout();
	CssLayout footer = new CssLayout();
	List<Field> fields = new ArrayList<Field>();
	
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public List<Field> getFields() {
		return this.fields;
	}
	
	public void setSectionState(SectionState sectionState) {
		this.sectionState = sectionState;
		updateSectionState();
	}
	public SectionState getSectionState() { 
		return this.sectionState;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	public void setAllowMinimize(Boolean allowMinimize) {
		this.allowMinimize = allowMinimize;
	}
	public Boolean getAllowMinimize() {
		return this.allowMinimize;
	}
	
	public void setAllowMaximaze(Boolean allowMaximaze) {
		this.allowMaximaze = allowMaximaze;
	}
	public Boolean getAllowMaximaze() {
		return this.allowMaximaze;
	}
	
	public void setTitle(String title) {
		this.title = title;
		this.updateContent();
	}
	public String getTitle() {
		return this.title;
	}
	
	public RmSectionContainer() {
		addStyleName("rmsection");
		this.updateContent();
	}
	
	public void updateContent() {
		removeAllComponents();
		addComponent(this.deploySectionHeader());
		addComponent(this.deploySectionBody());
		addComponent(this.deploySectionFooter());
		updateSectionState();
	}
	
	public CssLayout deploySectionHeader() {
		header.removeAllComponents();
		
		if ((!this.getAllowMaximaze()) && (!this.getAllowMaximaze())) {
			header.addStyleName("rmsection-header-static");
		}
		
		header.addStyleName("rmsection-header");
		{
			Button btnTitle = new Button(this.getTitle());
			btnTitle.addStyleName("remove-button");
			btnTitle.addStyleName("window-title-button");
			btnTitle.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
				    if (getSectionState()==SectionState.MAXIMIZED)	 {
				    	if (getAllowMinimize()) {
					    	setSectionState(SectionState.MINIMIZED);
				    	}
				    }
				    else {
				    	if (getAllowMaximaze()) {
					    	setSectionState(SectionState.MAXIMIZED);
				    	}
				    }
				}
			});
			header.addComponent(btnTitle);
		}
		return header;
	}

	public void updateSectionState() {
	    if (getSectionState()==SectionState.MAXIMIZED)	 {
	    	header.removeStyleName("rmsection-minimized");
	    	body.removeStyleName("hide-item");
	    	this.removeStyleName("height-auto");
	    }
	    else {
	    	header.addStyleName("rmsection-minimized");
	    	body.addStyleName("hide-item");
	    	this.addStyleName("height-auto");
	    }
	}
	
	public void minimize() {
		this.setSectionState(SectionState.MINIMIZED);
		this.updateSectionState();
	}
	
	public void maximize() {
		this.setSectionState(SectionState.MAXIMIZED);
		this.updateSectionState();
	}
	
	public CssLayout deploySectionBody() {
		body.addStyleName("rmsection-body");
		return body;
	}
	
	public CssLayout deploySectionFooter() {
		footer.addStyleName("rmsection-footer");
		return footer;
	}

	public void setHeader(CssLayout header) {
		this.header = header;
		this.updateContent();
	}
	public CssLayout getHeader() {
		return this.header;
	}
	
	public void setBody(CssLayout body) {
		this.body = body;
		this.updateContent();
	}
	public CssLayout getBody() {
		return this.body;
	}
	
	public void setFooter(CssLayout footer) {
		this.footer = footer;
		this.updateContent();
	}
	public CssLayout getFooter() {
		return this.footer;
	}
	
	public boolean validate() {
		Boolean retorno = true;
		for (Field field : this.getFields()) {
			if (!field.validate()) {
				retorno = false;
			}
		}
		return retorno;
	}
}
