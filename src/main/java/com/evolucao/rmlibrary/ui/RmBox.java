package com.evolucao.rmlibrary.ui;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class RmBox extends CssLayout {
	String title = "";
	CssLayout boxBody = new CssLayout();
	
	public void setTitle(String title) {
		this.title = title;
		this.updateContent();
	}
	public String getTitle() {
		return this.title;
	}
	
	public void setBody(CssLayout body) {
		this.boxBody = body;
	}
	public CssLayout getBody() {
		return this.boxBody;
	}
	
	public RmBox() {
		this.updateContent();
	}
	
	public RmBox(String title) {
		this.title = title;
		this.updateContent();
	}
	
	public void updateContent() {
		addStyleName("rmbox");
		addStyleName("flex-direction-col");
		{
			CssLayout boxTitle = new CssLayout();
			boxTitle.addStyleName("box-title");
			boxTitle.addStyleName("flex-direction-row");
			addComponent(boxTitle);
			{
				CssLayout title1 = new CssLayout();
				title1.addStyleName("flex-grow1");
				boxTitle.addComponent(title1);
				{
					Label lbl = new Label(this.getTitle());
					lbl.addStyleName("lbl-title");
					title1.addComponent(lbl);
				}
				
				CssLayout title2 = new CssLayout();
				boxTitle.addComponent(title2);
				{
					
				}
			}
			
			boxBody.addStyleName("box-body");
			addComponent(boxBody);
		}
	}
}
