package com.evolucao.weblibrary;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.weblibrary.view.SystemView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public abstract class ApplicationUI extends UI {
    public SimpleRecord login = new SimpleRecord();
    public String testeUI = "";
    String projectName = "";
    
    SystemView systemView = null;
	public Database database = null;
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectName() {
		return this.projectName;
	}

    public void setSystemView(SystemView systemView) {
    	this.systemView = systemView;
    }
    public SystemView getSystemView() {
    	return this.systemView;
    }
    
    public void setDatabase(Database database) {
    	this.database = database;
    }
    public Database getDatabase() {
    	return this.database;
    }
    
    public abstract void updateContent();
    
	@Override
	protected void init(VaadinRequest request) {
		//database = new Database("127.0.0.1", "prosaude", "usuario", "rataplan");
	}
}
